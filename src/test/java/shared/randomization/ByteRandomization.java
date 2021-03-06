package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.parsers.BoundedLooseByteParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A {@code byte} randomization implementation. */
public final class ByteRandomization extends NumberRandomization<Byte> {

  /** the globally shared instance of the {@code byte} randomization */
  public static final ByteRandomization INSTANCE = new ByteRandomization();

  /**
   * the safe range maximum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final byte SAFE_MAX = ((byte) (Byte.MAX_VALUE >>> 1));
  /**
   * the safe range minimum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final byte SAFE_MIN = ((byte) (-ByteRandomization.SAFE_MAX));

  /** create */
  private ByteRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getType() {
    return EPrimitiveType.BYTE;
  }

  /**
   * Get a random value
   *
   * @param fullRange
   *          should the full range of the type be used, or should we
   *          restrict the range such that overflows etc. are avoided
   * @param random
   *          the random number generator
   * @return the type
   */
  public static final byte randomNumber(final boolean fullRange,
      final Random random) {
    return ByteRandomization.randomNumberBetween( //
        (fullRange ? Byte.MIN_VALUE : ByteRandomization.SAFE_MIN), //
        (fullRange ? Byte.MAX_VALUE : ByteRandomization.SAFE_MAX), //
        fullRange, random);
  }

  /**
   * Get a random value between two bounds (both inclusive)
   *
   * @param lowerBound
   *          the inclusive lower bound
   * @param upperBound
   *          the inclusive upper bound
   * @param fullRange
   *          should the full range of the type be used, or should we
   *          restrict the range such that overflows etc. are avoided
   * @param random
   *          the random number generator
   * @return the value, or {@code null} if too many trials attempting to
   *         create the value have failed
   * @throws IllegalArgumentException
   *           if the bounds are invalid
   */
  public static final byte randomNumberBetween(final byte lowerBound,
      final byte upperBound, final boolean fullRange,
      final Random random) {
    final int useLower, useUpper;

    if (lowerBound >= upperBound) {
      if (lowerBound <= upperBound) {
        return lowerBound;
      }
      throw new IllegalArgumentException(((("Lower bound " + lowerBound) + //$NON-NLS-1$
          " is higher than upper bound ") + upperBound) + '.'); //$NON-NLS-1$
    }

    if (fullRange) {
      useLower = lowerBound;
      useUpper = upperBound;
    } else {
      useLower = (((lowerBound < ByteRandomization.SAFE_MIN)
          && (ByteRandomization.SAFE_MIN <= upperBound))
              ? ByteRandomization.SAFE_MIN : lowerBound);
      useUpper = (((upperBound > ByteRandomization.SAFE_MAX)
          && (ByteRandomization.SAFE_MAX >= useLower))
              ? ByteRandomization.SAFE_MAX : upperBound);
      if (useLower >= useUpper) {
        return ((byte) useLower);
      }
    }
    return ((byte) (random.nextInt((1 + useUpper) - useLower) + useLower));
  }

  /**
   * Get a random value between two unordered bounds, which might both be
   * either inclusive or exclusive
   *
   * @param bound1
   *          the first bound
   * @param bound1Inclusive
   *          inclusive property of first bound: {@code true} for
   *          inclusive, {@code false} for exclusive
   * @param bound2
   *          the second bound
   * @param bound2Inclusive
   *          inclusive property of second bound: {@code true} for
   *          inclusive, {@code false} for exclusive
   * @param fullRange
   *          should the full range of the type be used, or should we
   *          restrict the range such that overflows etc. are avoided
   * @param random
   *          the random number generator
   * @return the value, or {@code null} if too many trials attempting to
   *         create the value have failed
   * @throws IllegalArgumentException
   *           if the bounds are invalid
   */
  public static final byte randomNumberBetween(final byte bound1,
      final boolean bound1Inclusive, final byte bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    final byte useLower, useUpper;

    if (bound1 <= bound2) {

      if (bound1Inclusive) {
        useLower = bound1;
      } else {
        useLower = (byte) (bound1 + 1);
        if ((bound1 >= Byte.MAX_VALUE) || (useLower <= bound1)) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for bytes cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }

      if (bound2Inclusive) {
        useUpper = bound2;
      } else {
        useUpper = (byte) (bound2 - 1);
        if ((bound2 <= Byte.MIN_VALUE) || (useUpper >= bound2)) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for bytes cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

    } else {

      if (bound2Inclusive) {
        useLower = bound2;
      } else {
        useLower = (byte) (bound2 + 1);
        if ((bound2 >= Byte.MAX_VALUE) || (useLower <= bound2)) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for bytes cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

      if (bound1Inclusive) {
        useUpper = bound1;
      } else {
        useUpper = (byte) (bound1 - 1);
        if ((bound1 <= Byte.MIN_VALUE) || (useUpper >= bound1)) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for bytes cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }
    }

    return ByteRandomization.randomNumberBetween(useLower, useUpper,
        fullRange, random);
  }

  /** {@inheritDoc} */
  @Override
  public final Byte randomNumberBetween(final Number bound1,
      final boolean bound1Inclusive, final Number bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    return Byte.valueOf(//
        ByteRandomization.randomNumberBetween(bound1.byteValue(),
            bound1Inclusive, bound2.byteValue(), bound2Inclusive,
            fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final Byte randomValue(final boolean fullRange,
      final Random random) {
    return Byte.valueOf(ByteRandomization.randomNumber(fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final BoundedLooseByteParser getParserWithRandomBounds(
      final boolean fullRange, final Random random) {
    byte lower, upper, temp;

    do {
      lower = ByteRandomization.randomNumber(fullRange, random);
      upper = ByteRandomization.randomNumber(fullRange, random);
      if (lower > upper) {
        temp = upper;
        upper = lower;
        lower = temp;
      }
    } while ((upper - lower) <= (fullRange ? 0 : 2));

    return new BoundedLooseByteParser(lower, upper);
  }

  /** {@inheritDoc} */
  @Override
  public final Byte randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return Byte.valueOf(
        ByteRandomization.randomNumberBetween(lowerBound.byteValue(),
            upperBound.byteValue(), fullRange, random));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final <X extends Parser<? extends Number>> Byte _randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    final NumberParser numpar;
    final boolean useInt;

    if (parser instanceof NumberParser) {
      numpar = ((NumberParser) parser);
      useInt = numpar.areBoundsInteger();

      return Byte
          .valueOf(
              ByteRandomization.randomNumberBetween(
                  (useInt
                      ? ((byte) (Math.min(Byte.MAX_VALUE,
                          Math.max(Byte.MIN_VALUE,
                              numpar.getLowerBoundLong()))))
                      : ((byte) (Math.min(Byte.MAX_VALUE,
                          Math.max(Byte.MIN_VALUE,
                              numpar.getLowerBoundDouble()))))),
                  (useInt
                      ? ((byte) (Math.min(Byte.MAX_VALUE,
                          Math.max(Byte.MIN_VALUE,
                              numpar.getUpperBoundLong()))))
                      : ((byte) (Math.min(Byte.MAX_VALUE,
                          Math.max(Byte.MIN_VALUE,
                              numpar.getUpperBoundDouble()))))),
                  fullRange, random));
    }
    return this.randomValue(fullRange, random);
  }
}
