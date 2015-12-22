package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for classes
 *
 * @param <T>
 *          the base type
 */
public class ClassParser<T extends Object>
    extends Parser<Class<? extends T>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** a class parser that can parse any class */
  public static final ClassParser<Object> ANY_CLASS_PARSER = new ClassParser<>(
      Object.class);

  /** the base class */
  private final Class<T> m_base;

  /**
   * create the parser
   *
   * @param base
   *          the base class
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ClassParser(final Class<T> base) {
    super();
    this.m_base = ((base != null) ? base : ((Class) (Object.class)));
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Class<Class<? extends T>> getOutputClass() {
    return (Class) (Class.class);
  }

  /**
   * Get the base class
   *
   * @return the base class
   */
  public final Class<T> getBaseClass() {
    return this.m_base;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<? extends T> parseString(final String string) {
    Class<? extends T> c;

    try {
      c = ReflectionUtils.findClass(string, this.m_base);
    } catch (LinkageError | ClassNotFoundException
        | ClassCastException error) {
      throw new IllegalArgumentException("Cannt parse string '" + string + //$NON-NLS-1$
          "' to a class of type " + //$NON-NLS-1$
          TextUtils.className(this.m_base), error);
    }
    this.validate(c);
    return c;
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final Class<? extends T> instance) {
    try {
      ReflectionUtils.validateSubClass(instance, this.m_base);
    } catch (final ClassCastException except) {
      throw new IllegalArgumentException("Invalid class: " + instance, //$NON-NLS-1$
          except);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final Class<? extends T> parseObject(final Object o) {
    final Class<? extends T> clazz;

    if (o instanceof Class) {
      clazz = ((Class) o);
    } else {
      return this.parseString(String.valueOf(o));
    }

    this.validate(clazz);
    return clazz;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.getClass()),
        HashUtils.hashCode(this.m_base.hashCode()));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ClassParser) {
      return this.m_base.equals(((ClassParser) o).m_base);
    }
    return false;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    if (this.m_base == Object.class) {
      return ClassParser.ANY_CLASS_PARSER;
    }
    return this;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    if (this.m_base == Object.class) {
      return ClassParser.ANY_CLASS_PARSER;
    }
    return this;
  }
}
