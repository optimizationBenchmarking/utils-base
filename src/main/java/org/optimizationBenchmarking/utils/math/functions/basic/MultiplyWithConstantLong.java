package org.optimizationBenchmarking.utils.math.functions.basic;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** a function which multiplies its inputs with a {@code long} value */
public final class MultiplyWithConstantLong extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the multiplier */
  private final long m_multiplier;

  /** the maximally allowed value */
  private final long m_max;

  /** the minimally allowed value */
  private final long m_min;

  /**
   * create
   *
   * @param multiplier
   *          the multiplier
   */
  public MultiplyWithConstantLong(final long multiplier) {
    super();
    this.m_multiplier = multiplier;
    if (multiplier > 0L) {
      this.m_max = (Long.MAX_VALUE / multiplier);
      this.m_min = (Long.MIN_VALUE / multiplier);
    } else {
      if (multiplier == 0L) {
        this.m_max = Long.MIN_VALUE;
        this.m_min = Long.MAX_VALUE;
      } else {
        this.m_max = (Long.MIN_VALUE / multiplier);
        this.m_min = (Long.MAX_VALUE / multiplier);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    if (x0 > this.m_max) {
      if (this.m_multiplier > 0L) {
        return Long.MAX_VALUE;
      }
      return Long.MIN_VALUE;
    }
    if (x0 < this.m_min) {
      if (this.m_multiplier > 0L) {
        return Long.MIN_VALUE;
      }
      return Long.MAX_VALUE;
    }
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    if ((x0 >= this.m_min) && (x0 <= this.m_max)) {
      return (this.m_multiplier * x0);
    }

    return (((double) x0) * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(2002183493, //
        HashUtils.hashCode(this.m_multiplier));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o instanceof MultiplyWithConstantLong) && //
        (this.m_multiplier == (((MultiplyWithConstantLong) o).m_multiplier)));
  }
}
