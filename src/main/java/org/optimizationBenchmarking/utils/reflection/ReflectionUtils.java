package org.optimizationBenchmarking.utils.reflection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Pattern;

import org.optimizationBenchmarking.utils.IImmutable;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.ByteArrayIOStream;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * Some utilities for reflection access.
 */
public final class ReflectionUtils {

  /** the default name of the singleton constant: {@value} */
  private static final String DEFAULT_SINGLETON_CONSTANT_NAME = "INSTANCE"; //$NON-NLS-1$

  /** the default name of the singleton instance getter method: {@value} */
  private static final String DEFAULT_SINGLETON_GETTER_NAME = "getInstance"; //$NON-NLS-1$

  /**
   * the modifiers required to access a field or method from reflection: *
   * * {@value}
   */
  private static final int REQUIRED_MODIFIERS_FOR_ACCESS = (Modifier.PUBLIC
      | Modifier.FINAL | Modifier.STATIC);

  /** The default package name prefixes */
  private static final String[] DEFAULT_PACKAGE_PREFIXES = new String[] { //
      "java.lang."//$NON-NLS-1$
  };

  /**
   * Make sure that a class is loaded. This method can be used as some kind
   * of static way to check whether some classes are present in the class
   * path. If it throws a {@link java.lang.ClassNotFoundException}, the
   * class is not present, which could mean that a certain jar or other
   * dependency is missing.
   *
   * @param clazz
   *          the class to be loaded
   * @throws ClassNotFoundException
   *           if the class could not be loaded
   */
  public static final void ensureClassIsLoaded(final String clazz)
      throws ClassNotFoundException {
    String a, b;

    try {
      Class.forName(clazz);
    } catch (final Throwable t) {
      a = t.getMessage();
      b = ((clazz != null) ? clazz : TextUtils.NULL_STRING);
      if ((!(t instanceof ClassNotFoundException))
          || ((a == null) || (!(a.contains(b))))) {
        throw new ClassNotFoundException(("Could not load class '"//$NON-NLS-1$
            + b + '\''), t);
      }
      throw ((ClassNotFoundException) t);
    }
  }

  /**
   * Ensure that a set of classes is loaded. This method can be used as
   * some kind of static way to check whether some classes are present in
   * the class path. If it throws a
   * {@link java.lang.ClassNotFoundException}, the class is not present,
   * which could mean that a certain jar or other dependency is missing.
   *
   * @param classes
   *          the classes
   * @throws ClassNotFoundException
   *           if at least one of the class could not be loaded
   */
  public static final void ensureClassesAreLoaded(final String... classes)
      throws ClassNotFoundException {
    if (classes != null) {
      for (final String clazz : classes) {
        ReflectionUtils.ensureClassIsLoaded(clazz);
      }
    }
  }

  /**
   * Check if the given object can safely be assumed to be immutable.
   *
   * @param object
   *          the object
   * @return {@code true} if the object will definitely never change
   *         (unless something underhanded like reflective access to
   *         private members is going on), {@code false} if it cannot be
   *         assumed that the object won't change
   */
  public static final boolean isKnownImmutable(final Object object) {
    final Class<?> clazz;
    String str;

    if (object == null) {
      return true;
    }

    clazz = object.getClass();

    if (
    // wrappers for primitive types
    (clazz == Integer.class) || //
        (clazz == Double.class) || //
        (clazz == Long.class) || //
        (clazz == Boolean.class) || //
        (clazz == Character.class) || //
        (clazz == Byte.class) || //
        (clazz == Float.class) || //
        (clazz == Short.class) || //
        (clazz == Void.class) || //

    // basic, immutable objects
    (clazz == String.class) || //
        (clazz == Object.class) || //
        (clazz == URI.class) || //
        (clazz == URL.class) || //
        (clazz == BigDecimal.class) || //
        (clazz == BigInteger.class) || //
        (clazz == Pattern.class) || //
        (clazz == Currency.class) || //
        (clazz == Locale.class) || //

    // reflective objects
    (clazz == Class.class) || //
        (clazz == Field.class) || //
        (clazz == Method.class) || //
        (clazz == Constructor.class) || //
        (clazz == Field.class) || //
        (clazz == Package.class)) {
      return true;
    }

    // checks with the more costly instanceof operator
    if ((object instanceof Enum) || //
        (object instanceof IImmutable) || //
        (object instanceof File) || //
        (object instanceof Path)) {
      return true;
    }

    // double-check, to be on the save side
    if (clazz.isEnum()) {
      return true;
    }
    if (clazz.isPrimitive()) {
      return true;
    }

    // empty arrays are immutable, too
    if (clazz.isArray()) {
      return (Array.getLength(object) <= 0);
    }

    // is it an empty, immutable collection?
    if (clazz.isMemberClass()) {
      if (java.util.Collections.class.equals(clazz.getEnclosingClass())) {
        str = clazz.getSimpleName();
        if ((str != null) && (str.startsWith("Empty"))) {//$NON-NLS-1$
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Deep clone an object
   *
   * @param instance
   *          the object
   * @return the deep-cloned object
   * @param <T>
   *          the class of the object to be cloned
   * @throws IllegalArgumentException
   *           if the object cannot be cloned
   * @throws OutOfMemoryError
   *           the we are out of memory
   */
  @SuppressWarnings({ "unchecked", "unused" })
  public static final <T> T deepClone(final T instance)
      throws IllegalArgumentException, OutOfMemoryError {
    final Class<T> clazz;
    final Method m;

    // immutable objects don't need to be cloned
    if (ReflectionUtils.isKnownImmutable(instance)) {
      return instance;
    }

    clazz = (Class<T>) (instance.getClass());
    if (clazz.isArray()) {
      // First, we check for arrays. Arrays can be cloned efficiently.

      if (instance instanceof int[]) {
        return ((T) (((int[]) instance).clone()));
      }
      if (instance instanceof double[]) {
        return ((T) (((double[]) instance).clone()));
      }
      if (instance instanceof long[]) {
        return ((T) (((long[]) instance).clone()));
      }
      if (instance instanceof boolean[]) {
        return ((T) (((boolean[]) instance).clone()));
      }
      if (instance instanceof char[]) {
        return ((T) (((char[]) instance).clone()));
      }
      if (instance instanceof byte[]) {
        return ((T) (((byte[]) instance).clone()));
      }
      if (instance instanceof float[]) {
        return ((T) (((float[]) instance).clone()));
      }
      if (instance instanceof short[]) {
        return ((T) (((short[]) instance).clone()));
      }

      Object[] array;
      array = ((Object[]) instance).clone();
      for (int i = array.length; (--i) >= 0;) {
        array[i] = ReflectionUtils.deepClone(array[i]);
      }
      return ((T) array);
    }

    // maybe we can clone the object directly?
    if (instance instanceof Cloneable) {
      try {// let's see if it has a public clone method
        m = clazz.getMethod("clone"); //$NON-NLS-1$
        if (m != null) {
          try {
            return ((T) (m.invoke(instance)));
          } catch (final OutOfMemoryError ome) {
            throw ome;
          } catch (final Throwable t) {
            throw new IllegalArgumentException(t);
          }
        }
      } catch (final NoSuchMethodException nsme) {
        // this may happen and would be OK
      }
    }

    // if the object is serializable, we can try to serialize and
    // deserialize it
    if (instance instanceof Serializable) {
      try {
        try (final ByteArrayIOStream bos = new ByteArrayIOStream()) {
          try (
              final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(instance);
          }
          try (final ByteArrayInputStream ibs = bos.asInput()) {
            try (
                final ObjectInputStream ois = new ObjectInputStream(ibs)) {
              return ((T) (ois.readObject()));
            }
          }
        }
      } catch (final OutOfMemoryError ome) {
        throw ome;
      } catch (final Throwable t) {
        throw new IllegalArgumentException(t);
      }
    }

    // OK, if we get here, its finito: The object is not a primitive array
    // nor is it immutable. It is also not public cloneable and cannot be
    // serialized. This means we are out of "clean" and "official" ways to
    // copy the object. Thus, let's simply fail with an exception at the
    // bottom of this method.
    throw new IllegalArgumentException("Object cannot be cloned."); //$NON-NLS-1$
  }

  /**
   * Check whether {@code subClass} is really a sub-class of
   * {@code baseClass} and throw an {@link java.lang.ClassCastException}
   * otherwise.
   *
   * @param subClass
   *          the sub class
   * @param baseClass
   *          the base class
   * @throws ClassCastException
   *           if {@code subClass} is not a sub class of {@code baseClass}
   * @throws IllegalArgumentException
   *           if {@code subClass} or {@code baseClass} is {@code null}
   */
  public static final void validateSubClass(final Class<?> subClass,
      final Class<?> baseClass) throws ClassCastException {
    final String a, b;

    if (subClass == null) {
      throw new IllegalArgumentException("Subclass must not be null."); //$NON-NLS-1$
    }
    if (baseClass == null) {
      throw new IllegalArgumentException(//
          "Must provide valid base class, but provided null.");//$NON-NLS-1$
    }

    if (baseClass.isAssignableFrom(subClass)) {
      return;
    }

    a = TextUtils.className(subClass);
    b = TextUtils.className(baseClass);
    throw new ClassCastException((((((((//
    "Cannot assign an instance of " + //$NON-NLS-1$
        a) + " to a variable of type ") + //$NON-NLS-1$
        b) + ", i.e., ") + a) + //$NON-NLS-1$
        " is not a sub-class of ") + b) + '.'); //$NON-NLS-1$
  }

  /**
   * the internal find-class method
   *
   * @param clazzName
   *          the class name
   * @param prefixes
   *          a list of the allowed class name or package prefixes,
   *          searched if the {@code clazzName} cannot be found as is,
   *          usually of the form "{@code package.subpackage.}"
   * @return the class
   * @throws LinkageError
   *           on linkage error
   * @throws ClassCastException
   *           on class cast error
   * @throws ClassNotFoundException
   *           if the class has not been found
   */
  @SuppressWarnings("unused")
  private static final Class<?> __findClassInternal(final String clazzName,
      final String[] prefixes)
          throws LinkageError, ClassCastException, ClassNotFoundException {
    ClassNotFoundException error;

    try {
      return Class.forName(clazzName);
    } catch (LinkageError | ClassCastException except) {
      throw except;
    } catch (final ClassNotFoundException except) {
      error = except;
      for (final String prefix : ((prefixes != null) ? prefixes
          : ReflectionUtils.DEFAULT_PACKAGE_PREFIXES)) {
        try {
          return Class.forName(prefix + clazzName);
        } catch (LinkageError | ClassCastException except2) {
          throw except;
        } catch (final ClassNotFoundException except2) {
          error.addSuppressed(except2);
        }
      }
    }

    throw error;
  }

  /**
   * Find a class of the given {@code name} which must be a sub-class of
   * {@code base}. We first consider {@code name} as fully-qualified class
   * name. If no class of that name exists, we search in package
   * {@code java.lang}.
   *
   * @param name
   *          the class name
   * @param base
   *          the base class
   * @param <C>
   *          the class type
   * @return the discovered class
   * @throws IllegalArgumentException
   *           if the class name {@code clazz} or {@code base} are invalid
   * @throws LinkageError
   *           if linkage fails
   * @throws ExceptionInInitializerError
   *           if class initialization fails
   * @throws ClassNotFoundException
   *           if the class was not found
   * @throws ClassCastException
   *           if the class is not of type {@code C}
   */
  public static final <C> Class<? extends C> findClass(final String name,
      final Class<C> base)
          throws LinkageError, ExceptionInInitializerError,
          ClassNotFoundException, ClassCastException {
    return ReflectionUtils.findClass(name, base,
        ReflectionUtils.DEFAULT_PACKAGE_PREFIXES);
  }

  /**
   * Find a class of the given {@code name} which must be a sub-class of
   * {@code base}. We first consider {@code name} as fully-qualified class
   * name. If no class of that name exists, we search in package
   * {@code java.lang}.
   *
   * @param name
   *          the class name
   * @param base
   *          the base class
   * @param prefixes
   *          a list of the allowed class name or package prefixes,
   *          searched if the {@code clazzName} cannot be found as is,
   *          usually of the form "{@code package.subpackage.}"
   * @param <C>
   *          the class type
   * @return the discovered class
   * @throws IllegalArgumentException
   *           if the class name {@code clazz} or {@code base} are invalid
   * @throws LinkageError
   *           if linkage fails
   * @throws ExceptionInInitializerError
   *           if class initialization fails
   * @throws ClassNotFoundException
   *           if the class was not found
   * @throws ClassCastException
   *           if the class is not of type {@code C}
   */
  @SuppressWarnings("unchecked")
  public static final <C> Class<? extends C> findClass(final String name,
      final Class<C> base, final String[] prefixes)
          throws LinkageError, ExceptionInInitializerError,
          ClassNotFoundException, ClassCastException {
    final String clazzName;
    Class<?> found;

    if (base == null) {
      throw new IllegalArgumentException(//
          "Must provide proper base class for a class to discover, but provided null."); //$NON-NLS-1$
    }

    clazzName = TextUtils.prepare(name);
    if (clazzName == null) {
      throw new ClassNotFoundException(((//
      "Cannot get class with null or empty name '" + //$NON-NLS-1$
          name) + '\'') + '.');
    }

    found = ReflectionUtils.__findClassInternal(clazzName, prefixes);

    if (found == null) {
      throw new ClassNotFoundException(//
          ("Could not find class '" + clazzName)//$NON-NLS-1$
              + '\'');
    }

    ReflectionUtils.validateSubClass(found, base);
    return ((Class<C>) found);
  }

  /**
   * Throw an {@link java.lang.IllegalArgumentException}
   *
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the provided name string
   * @param reasonString
   *          the reason string
   * @param reasonException
   *          the causing exception
   * @return the exception
   */
  private static final ReflectiveOperationException __makeFindInstanceError(
      final Class<?> base, final Class<?> container, final String name,
      final String reasonString, final Throwable reasonException) {
    final String string;
    MemoryTextOutput text;

    text = new MemoryTextOutput();
    text.append("Cannot find an instance of the base class "); //$NON-NLS-1$
    if (base != null) {
      text.append(TextUtils.className(base));
    } else {
      text.append(TextUtils.NULL_STRING);
    }
    text.append(" within the container class "); //$NON-NLS-1$
    if (container != null) {
      text.append(TextUtils.className(container));
    } else {
      text.append(TextUtils.NULL_STRING);
    }
    if (name != null) {
      text.append(" under name '");//$NON-NLS-1$
      text.append(name);
      text.append('\'');
    }

    if (reasonString != null) {
      text.append(" because "); //$NON-NLS-1$
      text.append(reasonString);
    }
    text.append('.');

    string = text.toString();
    text = null;
    if (reasonException != null) {
      return new ReflectiveOperationException(string, reasonException);
    }
    return new ReflectiveOperationException(string);
  }

  /**
   * Check whether a class {@code base} is assignable by the class
   * {@code result}.
   *
   * @param base
   *          the class to assign to
   * @param result
   *          the class to assign from
   * @return {@code true} if variables of type {@code base} are assignable
   *         from {@code result}
   */
  private static final boolean __isAssignable(final Class<?> base,
      final Class<?> result) {
    final EPrimitiveType primitive;
    if (base.isAssignableFrom(result)) {
      return true;
    }
    if (result.isPrimitive()) {
      primitive = EPrimitiveType.getPrimitiveType(result);
      if (primitive != null) {
        return base.isAssignableFrom(primitive.getWrapperClass());
      }
    }
    return false;
  }

  /**
   * return a value from a field
   *
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the field name
   * @param field
   *          the field
   * @return the value
   * @param <T>
   *          the class of the object to be obtained
   * @throws IllegalArgumentException
   *           the the argument is illegal
   * @throws ReflectiveOperationException
   *           if the operation fails
   */
  private static final <T> T __field(final Class<T> base,
      final Class<?> container, final String name, final Field field)
          throws IllegalArgumentException, ReflectiveOperationException {
    final int mod;
    final Class<?> retClass;

    mod = field.getModifiers();
    if ((mod
        & ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) != ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("field " + field + " is not " + //$NON-NLS-1$//$NON-NLS-2$
              Modifier.toString(
                  ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS & (~mod))),
          null);
    }

    retClass = field.getType();
    if (!(ReflectionUtils.__isAssignable(base, retClass))) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("field " + field + //$NON-NLS-1$
              " of type " + TextUtils.className(retClass) + //$NON-NLS-1$
              " is incompatible to base class"), //$NON-NLS-1$
          null);
    }

    return base.cast(field.get(null));
  }

  /**
   * return a value from a getter method
   *
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the getter method name
   * @param method
   *          the method
   * @return the value
   * @param <T>
   *          the class of the object to be obtained
   * @throws IllegalArgumentException
   *           the the argument is illegal
   * @throws ReflectiveOperationException
   *           if the operation fails
   */
  private static final <T> T __method(final Class<T> base,
      final Class<?> container, final String name, final Method method)
          throws IllegalArgumentException, ReflectiveOperationException {
    final int mod;
    final Class<?> retClass;
    final Class<?>[] params;

    mod = method.getModifiers();
    if ((mod
        & ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) != ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("method " + method + " is not " + //$NON-NLS-1$//$NON-NLS-2$
              Modifier.toString(
                  ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS & (~mod))),
          null);
    }

    retClass = method.getReturnType();
    if (!(ReflectionUtils.__isAssignable(base, retClass))) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("return type " + TextUtils.className(retClass) + //$NON-NLS-1$
              " of method " + method + //$NON-NLS-1$
              " is incompatible to base class"), //$NON-NLS-1$
          null);
    }

    params = method.getParameterTypes();
    if ((params != null) && (params.length != 0)) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("method " + method + //$NON-NLS-1$
              " has more than 0 parameters: " + //$NON-NLS-1$
              Arrays.toString(params)),
          null);
    }

    return base.cast(method.invoke(null));
  }

  /**
   * return a value from a constructor
   *
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the getter method name
   * @param constructor
   *          the constructor
   * @return the value
   * @param <T>
   *          the class of the object to be obtained
   * @throws IllegalArgumentException
   *           the the argument is illegal
   * @throws ReflectiveOperationException
   *           if the operation fails
   */
  private static final <T> T __constructor(final Class<T> base,
      final Class<?> container, final String name,
      final Constructor<?> constructor)
          throws IllegalArgumentException, ReflectiveOperationException {
    final int mod;
    final Class<?> retClass;
    final Class<?>[] params;

    mod = constructor.getModifiers();
    if ((mod & Modifier.PUBLIC) != Modifier.PUBLIC) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("constructor " + constructor + " is not " + //$NON-NLS-1$//$NON-NLS-2$
              Modifier.toString(Modifier.PUBLIC)),
          null);
    }

    retClass = constructor.getDeclaringClass();
    if (!(base.isAssignableFrom(retClass))) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("return type " + TextUtils.className(retClass) + //$NON-NLS-1$
              " of constructor " + constructor + //$NON-NLS-1$
              " is incompatible to base class"), //$NON-NLS-1$
          null);
    }

    params = constructor.getParameterTypes();
    if ((params != null) && (params.length != 0)) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("constructor " + constructor + //$NON-NLS-1$
              " has more than 0 parameters: " + //$NON-NLS-1$
              Arrays.toString(params)),
          null);
    }

    return base.cast(constructor.newInstance());
  }

  /**
   * <p>
   * Obtain an instance of a given {@code base} class hosted within a
   * {@code container} class in a best effort approach. Optionally, a
   * {@code name} for a {@code public static final} constant field or
   * getter method may be provided. This method proceeds as follows:
   * </p>
   * <ol>
   * <li>If {@code name} is neither {@code null} nor an empty string nor
   * only consists just of white space, we store a
   * {@link java.lang.String#trim() trimmed} version of this name in the
   * local variable {@code useName}.
   * <ol>
   * <li>A field with name {@code useName} is looked for. If the field is
   * not {@code public static final} or has a wrong type, we fail with an
   * exception. We access the field and return its value.</li>
   * <li>A method with name {@code useName} is looked for. If the method is
   * not parameterless and {@code public static final} or has a wrong type,
   * we fail with an exception. We invoke the method and return its value.
   * </li>
   * <li>If {@code base} is an {@link java.lang.Enum} and
   * {@code container==base}, then we try to find if there is any instance
   * of {@code base} whose {@link java.lang.Enum#name() name} or
   * {@link java.lang.Enum#toString()} equals to {@code useName} while
   * {@link java.lang.String#equalsIgnoreCase(String) ignoring the case}.
   * If so, that instance is returned.</li>
   * <li>If {@code useName} is the same as the class name of
   * {@code container} and {@code base} is assignable from
   * {@code container}, then we check if {@code container} has a
   * parameterless public constructor. If so, we invoke it and return its
   * result.</li>
   * <li>If neither exists, we fail with an exception.</li>
   * </ol>
   * <li>We check if a {@code public static final} field with the name
   * {@value #DEFAULT_SINGLETON_CONSTANT_NAME} and an appropriate return
   * type exists. If so, we access the field and return its value.</li>
   * <li>We check if a parameterless {@code public static final} method
   * with the name {@value #DEFAULT_SINGLETON_GETTER_NAME} and an
   * appropriate type exists. If so, we invoke the method and return its
   * return value.</li>
   * <li>We check if {@code base} is assignable from {@code container} and
   * {@code container} has a parameterless {@code public} constructor. If
   * so, we invoke the constructor and return the new instance.</li>
   * <li>If {@code base} is assignable from {@code container}, then we
   * check if {@code container} has a parameterless public constructor. If
   * so, we invoke it and return its result.</li>
   * <li>We fail with an error.</li>
   * </ol>
   *
   * @param base
   *          the base class
   * @param container
   *          the container
   * @param name
   *          the name
   * @return the instance
   * @param <T>
   *          the expected return type
   * @throws ReflectiveOperationException
   *           if we fail to obtain the instance
   */
  @SuppressWarnings("rawtypes")
  public static final <T> T getInstance(final Class<T> base,
      final Class<?> container, final String name)
          throws ReflectiveOperationException {
    final Class<?> useContainer;
    String useName;
    Throwable errorA, errorB, errorC;
    Field field;
    Method method;
    Constructor<?> constructor;
    ReflectiveOperationException bottom;
    T[] constants;

    if (base == null) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          "base class cannot be null", null); //$NON-NLS-1$
    }

    if (container == null) {
      if (base.isEnum()) {
        useContainer = base;
      } else {
        throw ReflectionUtils.__makeFindInstanceError(base, container,
            name, "container class cannot be null", null); //$NON-NLS-1$
      }
    } else {
      useContainer = container;
    }

    if (useContainer.isPrimitive()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "container class cannot be a primitive type", null); //$NON-NLS-1$
    }
    if (useContainer.isArray()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "base class cannot be an array type", null); //$NON-NLS-1$
    }
    if (base.isAnnotation()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "base class cannot be an annotation type", null); //$NON-NLS-1$
    }
    if (useContainer.isAnnotation()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "container class cannot be an annotation type", null); //$NON-NLS-1$
    }

    errorA = errorB = errorC = null;
    useName = null;

    checkNamed: {
      if (name != null) {
        if (name.indexOf('.') >= 0) {
          throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
              name, "name must not contain a '.'", null); //$NON-NLS-1$
        }
        useName = TextUtils.prepare(name);
        if (useName != null) {
          try {
            field = useContainer.getField(useName);
          } catch (final Throwable t) {
            field = null;
            errorA = t;
          }

          if (field != null) {
            return ReflectionUtils.__field(base, useContainer, useName,
                field);
          }

          try {
            method = useContainer.getMethod(useName);
          } catch (final Throwable t) {
            method = null;
            errorB = t;
          }

          if (method != null) {
            return ReflectionUtils.__method(base, useContainer, useName,
                method);
          }

          if (base.isEnum() && ((base == container)
              || base.isAssignableFrom(container))) {
            constants = base.getEnumConstants();
            if (constants != null) {
              for (final T constx : constants) {
                if (useName.equalsIgnoreCase(((Enum) constx).name())) {
                  return constx;
                }
              }
              for (final T constx : constants) {
                if (useName.equalsIgnoreCase(constx.toString())) {
                  return constx;
                }
              }
            }
          }

          break checkNamed;
        }
      }

      try {
        field = useContainer
            .getField(ReflectionUtils.DEFAULT_SINGLETON_CONSTANT_NAME);
      } catch (final Throwable t) {
        field = null;
        errorA = t;
      }

      if (field != null) {
        return ReflectionUtils.__field(base, useContainer, useName, field);
      }

      try {
        method = useContainer
            .getMethod(ReflectionUtils.DEFAULT_SINGLETON_GETTER_NAME);
      } catch (final Throwable t) {
        method = null;
        errorB = t;
      }

      if (method != null) {
        return ReflectionUtils.__method(base, useContainer, useName,
            method);
      }
    }

    if (!(base.isEnum())) {
      if (base.isAssignableFrom(useContainer)) {
        if ((useName == null)
            || (useContainer.getSimpleName().equals(useName))) {
          try {
            constructor = useContainer.getConstructor();
          } catch (final Throwable t) {
            constructor = null;
            errorC = t;
          }

          if (constructor != null) {
            return ReflectionUtils.__constructor(base, useContainer,
                useName, constructor);
          }
        }
      }
    }

    bottom = ReflectionUtils.__makeFindInstanceError(base, useContainer,
        useName,
        "no appropriate public static final field, parameterless public static final method, or public constructor found to get an instance of base class"//$NON-NLS-1$
        , null);
    if (errorA != null) {
      bottom.addSuppressed(errorA);
    }
    if (errorB != null) {
      bottom.addSuppressed(errorB);
    }
    if (errorC != null) {
      bottom.addSuppressed(errorC);
    }
    throw bottom;
  }

  /**
   * Get an instance by a identifier string
   *
   * @param identifier
   *          the identifier, of form
   *          {@code packageA.packageB.className#constantName}
   * @param base
   *          the base class, i.e., the return type
   * @return the value of the constant
   * @param <T>
   *          the return type
   * @throws ReflectiveOperationException
   *           if the reflective operation fails
   */
  public static final <T> T getInstanceByName(final Class<T> base,
      final String identifier) throws ReflectiveOperationException {
    return ReflectionUtils.getInstanceByName(base, identifier,
        ReflectionUtils.DEFAULT_PACKAGE_PREFIXES);
  }

  /**
   * Get an instance by a identifier string
   *
   * @param identifier
   *          the identifier, of form
   *          {@code packageA.packageB.className#constantName}
   * @param base
   *          the base class, i.e., the return type
   * @param prefixes
   *          a list of the allowed class name or package prefixes,
   *          searched if the {@code clazzName} cannot be found as is,
   *          usually of the form "{@code package.subpackage.}"
   * @return the value of the constant
   * @param <T>
   *          the return type
   * @throws ReflectiveOperationException
   *           if the reflective operation fails
   */
  public static final <T> T getInstanceByName(final Class<T> base,
      final String identifier, final String[] prefixes)
          throws ReflectiveOperationException {
    final String idString, fieldName;
    String className;
    Class<? extends Object> container;
    Throwable cause;
    final int index;

    idString = TextUtils.prepare(identifier);
    if (idString == null) {
      throw new IllegalArgumentException(//
          "Class+constant identifier must not be null or empty, but is '"//$NON-NLS-1$
              + identifier + '\'');
    }

    index = idString.lastIndexOf('#');
    checkClass: {
      if ((index <= 0) || (index >= (idString.length() - 1))) {
        if ((base != null) && (base != Enum.class)
            && (Enum.class.isAssignableFrom(base))) {
          className = null;
          fieldName = idString;
          container = base;
          break checkClass;
        }
        className = idString;
        fieldName = null;
      } else {
        className = idString.substring(0, index);
        fieldName = idString.substring(index + 1);
      }

      className = TextUtils.prepare(className);
      if (className == null) {
        throw new IllegalArgumentException(//
            "Class name to get an instance from within must neither be null, empty, or consisting only of white spaces, but identifier '"//$NON-NLS-1$
                + identifier + "' provides such a name.");//$NON-NLS-1$
      }

      cause = null;
      try {
        container = ReflectionUtils.__findClassInternal(className,
            prefixes);
      } catch (final Throwable t) {
        cause = t;
        container = null;
      }
      if (container == null) {
        throw ReflectionUtils.__makeFindInstanceError(base, null,
            fieldName,
            ("could not discover class '" + className//$NON-NLS-1$
                + "' based on string '" + idString + '\''), //$NON-NLS-1$
            cause);
      }
    }

    return ReflectionUtils.getInstance(base, container, fieldName);
  }

  /**
   * Get a resource as stream. {@code resource} identifies a class and a
   * resource file according to the schema {@code className#resourceFile}.
   *
   * @param resource
   *          the resource
   * @return the stream, or {@code null} if the resource file does not
   *         exist
   * @throws IllegalArgumentException
   *           if the class identified by the {@code resource} does not
   *           exist
   */
  public static final InputStream getResourceAsStream(
      final String resource) {
    return ReflectionUtils.getResourceAsStream(resource,
        ReflectionUtils.DEFAULT_PACKAGE_PREFIXES);
  }

  /**
   * Get a resource as stream. {@code resource} identifies a class and a
   * resource file according to the schema {@code className#resourceFile}.
   *
   * @param resource
   *          the resource
   * @param prefixes
   *          a list of the allowed class name or package prefixes,
   *          searched if the {@code clazzName} cannot be found as is,
   *          usually of the form "{@code package.subpackage.}"
   * @return the stream, or {@code null} if the resource file does not
   *         exist
   * @throws IllegalArgumentException
   *           if the class identified by the {@code resource} does not
   *           exist
   */
  public static final InputStream getResourceAsStream(
      final String resource, final String[] prefixes) {
    final String string, clazzName, file;
    final int index;
    final Class<?> clazz;

    if (resource == null) {
      throw new IllegalArgumentException(//
          "Resource name must not be null.");//$NON-NLS-1$
    }

    string = TextUtils.prepare(resource);
    if (string == null) {
      throw new IllegalArgumentException(//
          "Resource name must not be empty or consisting only of white space, but '"//$NON-NLS-1$
              + resource + "' is.");//$NON-NLS-1$
    }

    index = resource.indexOf('#');
    if (index < 0) {
      throw new IllegalArgumentException(//
          "Resource name must contain a '#', but '"//$NON-NLS-1$
              + resource + "' does not.");//$NON-NLS-1$
    }

    clazzName = TextUtils.prepare(resource.substring(0, index));
    if (clazzName == null) {
      throw new IllegalArgumentException(//
          "Name of class hosting a resource must not be empty or consisting only of white space, but '"//$NON-NLS-1$
              + resource + "' has such a class name.");//$NON-NLS-1$
    }

    file = TextUtils.prepare(resource.substring(index + 1));
    if (file == null) {
      throw new IllegalArgumentException(//
          "Name of resource file must not be empty or consisting only of white space, but '"//$NON-NLS-1$
              + resource + "' has such a resource name.");//$NON-NLS-1$
    }

    try {
      clazz = ReflectionUtils.__findClassInternal(clazzName, prefixes);
    } catch (final Throwable t) {
      throw new IllegalArgumentException(((((((//
      "Cannot load class '") + clazzName) + //$NON-NLS-1$
          "' needed to access resource '") + resource)//$NON-NLS-1$
          + '\'') + '.'), t);
    }

    return clazz.getResourceAsStream(file);
  }

  /**
   * Add the default package search path to a list of package prefixes as
   * used by {@link #getInstanceByName(Class, String, String[])},
   * {@link #getResourceAsStream(String, String[])}, and
   * {@link #findClass(String, Class, String[])}.
   *
   * @param list
   *          the list to add the package name to, should normally be an
   *          instance of {@link java.util.LinkedHashSet}
   */
  public static final void addDefaultPackagePath(
      final Collection<String> list) {
    for (final String str : ReflectionUtils.DEFAULT_PACKAGE_PREFIXES) {
      list.add(str);
    }
  }

  /**
   * Get the canonical package name
   *
   * @param clazz
   *          the class
   * @return the canonical package name, or {@code null} if none could be
   *         found
   */
  public static final String getPackagePrefix(final Class<?> clazz) {
    final Package pack;
    String string;
    int index;

    if (clazz == null) {
      return null;
    }
    pack = clazz.getPackage();
    if (pack != null) {
      string = pack.getName();
      if (string != null) {
        return (string + '.');
      }
    }

    string = clazz.getCanonicalName();
    if (string != null) {
      index = string.lastIndexOf('.');
      if ((index > 0) && (index < (string.length() - 1))) {
        return (string.substring(0, (index + 1)) + '.');
      }
    }

    return null;
  }

  /**
   * Add the package of a given class to a list of package prefixes as used
   * by {@link #getInstanceByName(Class, String, String[])},
   * {@link #getResourceAsStream(String, String[])}, and
   * {@link #findClass(String, Class, String[])}.
   *
   * @param clazz
   *          the class
   * @param list
   *          the list to add the package name to, should normally be an
   *          instance of {@link java.util.LinkedHashSet}
   */
  public static final void addPackageOfClassToPrefixList(
      final Class<?> clazz, final Collection<String> list) {
    final String string;

    if (clazz != null) {
      string = ReflectionUtils.getPackagePrefix(clazz);
      if (string != null) {
        list.add(string);
      }
    }
  }

  /** the forbidden constructor */
  private ReflectionUtils() {
    ErrorUtils.doNotCall();
  }

}
