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
}
