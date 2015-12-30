package org.optimizationBenchmarking.utils.math.functions.basic;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.Rational;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** a function which multiplies its inputs with a rational number */
public final class MultiplyWithConstantRational extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the multiplier */
  private final Rational m_multiplier;

  /**
   * create
   *
   * @param multiplier
   *          the multiplier
   */
  public MultiplyWithConstantRational(final Rational multiplier) {
    super();
    this.m_multiplier = multiplier;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    final Rational res;
    res = this.m_multiplier.multiply(x0);
    if (res.isReal()) {
      return res.doubleValue();
    }
    return (this.m_multiplier.doubleValue() * x0);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    final Rational res;
    res = this.m_multiplier.multiply(x0);
    if (res.isReal()) {
      return res.doubleValue();
    }
    return (this.m_multiplier.doubleValue() * x0);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    final Rational res;

    res = this.m_multiplier.multiply(x0);
    if (res.isReal()) {
      return res.longValue();
    }
    return ((long) (Math.min(Long.MAX_VALUE,
        Math.max(Long.MIN_VALUE, this.computeAsDouble(x0)))));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(9183457, //
        this.m_multiplier.hashCode());
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o instanceof MultiplyWithConstantRational) && //
        (this.m_multiplier.equals(//
            ((MultiplyWithConstantRational) o).m_multiplier)));
  }
}
