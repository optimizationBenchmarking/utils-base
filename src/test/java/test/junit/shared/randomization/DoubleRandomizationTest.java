package test.junit.shared.randomization;

import java.util.Random;

import shared.randomization.DoubleRandomization;

/** the double randomization test */
public class DoubleRandomizationTest
    extends FloatingRandomizationTest<Double, DoubleRandomization> {

  /** create */
  public DoubleRandomizationTest() {
    super(DoubleRandomization.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final double numberBetween(final double lower,
      final double upper, final Random random) {
    return DoubleRandomization.randomNumberBetween(lower, upper, true,
        random);
  }

  /** {@inheritDoc} */
  @Override
  protected final double lowerBound() {
    return -Double.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final double upperBound() {
    return Double.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final double numberBetween(final double bound1,
      final boolean bound1Inclusive, final double bound2,
      final boolean bound2Inclusive, final Random random) {
    return DoubleRandomization.randomNumberBetween(bound1, bound1Inclusive,
        bound2, bound2Inclusive, true, random);
  }
}
