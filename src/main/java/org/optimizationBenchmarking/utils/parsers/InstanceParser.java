package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * Get an instance from a string.
 *
 * @param <T>
 *          the instance type
 */
public class InstanceParser<T> extends Parser<T> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the base class */
  private final Class<T> m_base;

  /** the package prefixes */
  private final String[] m_prefixes;

  /**
   * create
   *
   * @param baseClass
   *          the base class of the instance to return, used to check type
   *          consistency
   * @param prefixes
   *          the package prefixes
   */
  public InstanceParser(final Class<T> baseClass,
      final String[] prefixes) {
    super();

    if (baseClass == null) {
      throw new IllegalArgumentException(//
          "A base class must be provided."); //$NON-NLS-1$
    }
    this.m_base = baseClass;
    this.m_prefixes = prefixes;
  }

  /** {@inheritDoc} */
  @Override
  public T parseString(final String string)
      throws IllegalArgumentException {
    final T fieldValue;

    try {
      fieldValue = ReflectionUtils.getInstanceByName(this.m_base,
          LooseStringParser.INSTANCE.parseString(string), this.m_prefixes);
    } catch (final ReflectiveOperationException exception) {
      throw new IllegalArgumentException(//
          "Cannot parse string '" + string + //$NON-NLS-1$
              "' to an instance of " //$NON-NLS-1$
              + TextUtils.className(this.m_base),
          exception);
    }
    this.validate(fieldValue);
    return fieldValue;
  }

  /** {@inheritDoc} */
  @Override
  public T parseObject(final Object o) {
    final Class<T> base;
    final T obj;

    if (o == null) {
      return null;
    }

    base = this.m_base;
    if (base.isInstance(o)) {
      obj = base.cast(o);
      this.validate(obj);
      return obj;
    }

    return this.parseString(String.valueOf(o));
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final T instance)
      throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException("Instance must not be null."); //$NON-NLS-1$
    }
    // Check if we can cast or whether something dodgy has been done with
    // generics.
    this.m_base.cast(instance);
  }

  /** {@inheritDoc} */
  @Override
  public final Class<T> getOutputClass() {
    return this.m_base;
  }
}
