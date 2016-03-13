package test.junit.shared.randomization;

import java.util.Random;

import shared.randomization.IntRandomization;

/** the int randomization test */
public class IntRandomizationTest
    extends IntegerRandomizationTest<Integer, IntRandomization> {

  /** create */
  public IntRandomizationTest() {
    super(IntRandomization.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long lower, final long upper,
      final Random random) {
    return IntRandomization.randomNumberBetween((int) lower, (int) upper,
        true, random);
  }

  /** {@inheritDoc} */
  @Override
  protected final long lowerBound() {
    return Integer.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long upperBound() {
    return Integer.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long bound1,
      final boolean bound1Inclusive, final long bound2,
      final boolean bound2Inclusive, final Random random) {
    return IntRandomization.randomNumberBetween((int) bound1,
        bound1Inclusive, (int) bound2, bound2Inclusive, true, random);
  }
}
