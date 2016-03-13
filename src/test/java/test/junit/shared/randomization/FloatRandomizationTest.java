package test.junit.shared.randomization;

import java.util.Random;

import shared.randomization.FloatRandomization;

/** the float randomization test */
public class FloatRandomizationTest
    extends FloatingRandomizationTest<Float, FloatRandomization> {

  /** create */
  public FloatRandomizationTest() {
    super(FloatRandomization.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final double numberBetween(final double lower,
      final double upper, final Random random) {
    return FloatRandomization.randomNumberBetween((float) lower,
        (float) upper, true, random);
  }

  /** {@inheritDoc} */
  @Override
  protected final double lowerBound() {
    return -Float.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final double upperBound() {
    return Float.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final double numberBetween(final double bound1,
      final boolean bound1Inclusive, final double bound2,
      final boolean bound2Inclusive, final Random random) {
    return FloatRandomization.randomNumberBetween((float) bound1,
        bound1Inclusive, (float) bound2, bound2Inclusive, true, random);
  }

  /** {@inheritDoc} */
  @Override
  protected final double nextUp(final double d) {
    return Math.nextUp((float) d);
  }

  /** {@inheritDoc} */
  @Override
  protected final double nextDown(final double d) {
    return Math.nextAfter((float) d, Double.NEGATIVE_INFINITY);
  }
}
