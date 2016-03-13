package test.junit.shared.randomization;

import java.util.Random;

import shared.randomization.ShortRandomization;

/** the short randomization test */
public class ShortRandomizationTest
    extends IntegerRandomizationTest<Short, ShortRandomization> {

  /** create */
  public ShortRandomizationTest() {
    super(ShortRandomization.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long lower, final long upper,
      final Random random) {
    return ShortRandomization.randomNumberBetween((short) lower,
        (short) upper, true, random);
  }

  /** {@inheritDoc} */
  @Override
  protected final long lowerBound() {
    return Short.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long upperBound() {
    return Short.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long bound1,
      final boolean bound1Inclusive, final long bound2,
      final boolean bound2Inclusive, final Random random) {
    return ShortRandomization.randomNumberBetween((short) bound1,
        bound1Inclusive, (short) bound2, bound2Inclusive, true, random);
  }
}
