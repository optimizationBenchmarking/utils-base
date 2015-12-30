package org.optimizationBenchmarking.utils.math.functions.basic;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** a function which divides its inputs by a {@code long} value */
public final class DivideByConstantLong extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the divisor */
  private final long m_divisor;

  /**
   * create
   *
   * @param divisor
   *          the divisor
   */
  public DivideByConstantLong(final long divisor) {
    super();
    this.m_divisor = divisor;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long value) {
    final long gcd;

    gcd = MathUtils.gcd(this.m_divisor, value);
    if (gcd > 0L) {
      return (((double) (value / gcd))
          / ((double) (this.m_divisor / gcd)));
    }
    return (value / this.m_divisor);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    return (x0 / this.m_divisor);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    long l;
    if ((x0 > Long.MIN_VALUE) && (x0 < Long.MAX_VALUE)) {
      l = ((long) x0);
      if (l == x0) {
        if ((l % this.m_divisor) == 0L) {
          return (l / this.m_divisor);
        }
      }
    }

    return (x0 / this.m_divisor);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(1992183463, //
        HashUtils.hashCode(this.m_divisor));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o instanceof DivideByConstantLong) && //
        (this.m_divisor == (((DivideByConstantLong) o).m_divisor)));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("/" + this.m_divisor); //$NON-NLS-1$
  }
}
