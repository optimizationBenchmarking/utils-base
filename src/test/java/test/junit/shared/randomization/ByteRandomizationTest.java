package test.junit.shared.randomization;

import java.util.Random;

import shared.randomization.ByteRandomization;

/** the byte randomization test */
public class ByteRandomizationTest
    extends IntegerRandomizationTest<Byte, ByteRandomization> {

  /** create */
  public ByteRandomizationTest() {
    super(ByteRandomization.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final long numberBetween(final long lower, final long upper,
      final Random random) {
    return ByteRandomization.randomNumberBetween((byte) lower,
        (byte) upper, true, random);
  }

  /** {@inheritDoc} */
  @Override
  protected final long lowerBound() {
    return Byte.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  protected final long upperBound() {
    return Byte.MAX_VALUE;
  }
}
