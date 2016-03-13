package test.junit.shared.randomization;

import java.util.Random;

import shared.randomization.LongRandomization;

/** the long randomization test */
public class LongRandomizationTest
    extends IntegerRandomizationTest<Long, LongRandomization> {

  /** create */
  public LongRandomizationTest() {
    super(LongRandomization.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long lower, final long upper,
      final Random random) {
    return LongRandomization.randomNumberBetween(lower, upper, true,
        random);
  }

  /** {@inheritDoc} */
  @Override
  protected final long lowerBound() {
    return Long.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long upperBound() {
    return Long.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long bound1,
      final boolean bound1Inclusive, final long bound2,
      final boolean bound2Inclusive, final Random random) {
    return LongRandomization.randomNumberBetween(bound1, bound1Inclusive,
        bound2, bound2Inclusive, true, random);
  }
}
