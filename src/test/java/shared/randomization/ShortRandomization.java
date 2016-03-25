package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.parsers.BoundedLooseShortParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A {@code short} randomization implementation. */
public final class ShortRandomization extends NumberRandomization<Short> {

  /** the globally shared instance of the {@code short} randomization */
  public static final ShortRandomization INSTANCE = new ShortRandomization();

  /**
   * the safe range maximum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final short SAFE_MAX = ((short) (Short.MAX_VALUE >>> 1));
  /**
   * the safe range minimum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final short SAFE_MIN = ((short) (-ShortRandomization.SAFE_MAX));

  /** create */
  private ShortRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getType() {
    return EPrimitiveType.SHORT;
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
  public static final short randomNumber(final boolean fullRange,
      final Random random) {
    return ShortRandomization.randomNumberBetween( //
        (fullRange ? Short.MIN_VALUE : ShortRandomization.SAFE_MIN), //
        (fullRange ? Short.MAX_VALUE : ShortRandomization.SAFE_MAX), //
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
  public static final short randomNumberBetween(final short lowerBound,
      final short upperBound, final boolean fullRange,
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
      useLower = (((lowerBound < ShortRandomization.SAFE_MIN)
          && (ShortRandomization.SAFE_MIN <= upperBound))
              ? ShortRandomization.SAFE_MIN : lowerBound);
      useUpper = (((upperBound > ShortRandomization.SAFE_MAX)
          && (ShortRandomization.SAFE_MAX >= useLower))
              ? ShortRandomization.SAFE_MAX : upperBound);
      if (useLower >= useUpper) {
        return ((short) useLower);
      }
    }

    return ((short) (random.nextInt((1 + useUpper) - useLower)
        + useLower));
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
  public static final short randomNumberBetween(final short bound1,
      final boolean bound1Inclusive, final short bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    final short useLower, useUpper;

    if (bound1 <= bound2) {

      if (bound1Inclusive) {
        useLower = bound1;
      } else {
        useLower = (short) (bound1 + 1);
        if ((bound1 >= Short.MAX_VALUE) || (useLower <= bound1)) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for shorts cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }

      if (bound2Inclusive) {
        useUpper = bound2;
      } else {
        useUpper = (short) (bound2 - 1);
        if ((bound2 <= Short.MIN_VALUE) || (useUpper >= bound2)) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for shorts cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

    } else {

      if (bound2Inclusive) {
        useLower = bound2;
      } else {
        useLower = (short) (bound2 + 1);
        if ((bound2 >= Short.MAX_VALUE) || (useLower <= bound2)) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for shorts cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

      if (bound1Inclusive) {
        useUpper = bound1;
      } else {
        useUpper = (short) (bound1 - 1);
        if ((bound1 <= Short.MIN_VALUE) || (useUpper >= bound1)) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for shorts cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }
    }

    return ShortRandomization.randomNumberBetween(useLower, useUpper,
        fullRange, random);
  }

  /** {@inheritDoc} */
  @Override
  public final Short randomNumberBetween(final Number bound1,
      final boolean bound1Inclusive, final Number bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    return Short.valueOf(//
        ShortRandomization.randomNumberBetween(bound1.shortValue(),
            bound1Inclusive, bound2.shortValue(), bound2Inclusive,
            fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final Short randomValue(final boolean fullRange,
      final Random random) {
    return Short
        .valueOf(ShortRandomization.randomNumber(fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final BoundedLooseShortParser getParserWithRandomBounds(
      final boolean fullRange, final Random random) {
    short lower, upper, temp;

    do {
      lower = ShortRandomization.randomNumber(fullRange, random);
      upper = ShortRandomization.randomNumber(fullRange, random);
      if (lower > upper) {
        temp = upper;
        upper = lower;
        lower = temp;
      }
    } while ((upper - lower) <= (fullRange ? 0 : 2));

    return new BoundedLooseShortParser(lower, upper);
  }

  /** {@inheritDoc} */
  @Override
  public final Short randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return Short.valueOf(
        ShortRandomization.randomNumberBetween(lowerBound.shortValue(),
            upperBound.shortValue(), fullRange, random));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final <X extends Parser<? extends Number>> Short _randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    final NumberParser numpar;
    final boolean useInt;

    if (parser instanceof NumberParser) {
      numpar = ((NumberParser) parser);
      useInt = numpar.areBoundsInteger();

      return Short
          .valueOf(
              ShortRandomization.randomNumberBetween(
                  (useInt
                      ? ((short) (Math.min(Short.MAX_VALUE,
                          Math.max(Short.MIN_VALUE,
                              numpar.getLowerBoundLong()))))
                      : ((short) (Math.min(Short.MAX_VALUE,
                          Math.max(Short.MIN_VALUE,
                              numpar.getLowerBoundDouble()))))),
                  (useInt
                      ? ((short) (Math.min(Short.MAX_VALUE,
                          Math.max(Short.MIN_VALUE,
                              numpar.getUpperBoundLong()))))
                      : ((short) (Math.min(Short.MAX_VALUE,
                          Math.max(Short.MIN_VALUE,
                              numpar.getUpperBoundDouble()))))),
                  fullRange, random));
    }
    return this.randomValue(fullRange, random);
  }
}
