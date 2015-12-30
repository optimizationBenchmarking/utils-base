package org.optimizationBenchmarking.utils.math.functions.basic;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** a function which multiplies its inputs with a {@code double} value */
public final class MultiplyWithConstantDouble extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the multiplier */
  private final double m_multiplier;

  /**
   * create
   *
   * @param multiplier
   *          the multiplier
   */
  public MultiplyWithConstantDouble(final double multiplier) {
    super();
    this.m_multiplier = multiplier;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(42123461, //
        HashUtils.hashCode(this.m_multiplier));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o instanceof MultiplyWithConstantDouble) && //
        (this.m_multiplier == (((MultiplyWithConstantDouble) o).m_multiplier)));
  }
}
