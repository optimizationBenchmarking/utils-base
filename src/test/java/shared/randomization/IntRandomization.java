package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.parsers.BoundedLooseIntParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A {@code int} randomization implementation. */
public final class IntRandomization extends NumberRandomization<Integer> {

  /** the globally shared instance of the {@code int} randomization */
  public static final IntRandomization INSTANCE = new IntRandomization();

  /**
   * the safe range maximum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final int SAFE_MAX = (Integer.MAX_VALUE >>> 1);
  /**
   * the safe range minimum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final int SAFE_MIN = (-IntRandomization.SAFE_MAX);

  /** create */
  private IntRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getType() {
    return EPrimitiveType.INT;
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
  public static final int randomNumber(final boolean fullRange,
      final Random random) {
    return IntRandomization.randomNumberBetween( //
        (fullRange ? Integer.MIN_VALUE : IntRandomization.SAFE_MIN), //
        (fullRange ? Integer.MAX_VALUE : IntRandomization.SAFE_MAX), //
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
  public static final int randomNumberBetween(final int lowerBound,
      final int upperBound, final boolean fullRange, final Random random) {
    final int useLower, useUpper;
    int difference, trial;

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
      useLower = (((lowerBound < IntRandomization.SAFE_MIN)
          && (IntRandomization.SAFE_MIN <= upperBound))
              ? IntRandomization.SAFE_MIN : lowerBound);
      useUpper = (((upperBound > IntRandomization.SAFE_MAX)
          && (IntRandomization.SAFE_MAX >= useLower))
              ? IntRandomization.SAFE_MAX : upperBound);
      if (useLower >= useUpper) {
        return useLower;
      }
    }

    difference = (useUpper - useLower);
    if ((difference >= 0) && (difference < Integer.MAX_VALUE)) {
      return (useLower + random.nextInt(difference + 1));
    }

    do {
      trial = random.nextInt();
    } while ((trial < useLower) || (trial > useUpper));

    return trial;
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
  public static final int randomNumberBetween(final int bound1,
      final boolean bound1Inclusive, final int bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    final int useLower, useUpper;

    if (bound1 <= bound2) {
      if (bound1Inclusive) {
        useLower = bound1;
      } else {
        useLower = (bound1 + 1);
        if ((bound1 >= Integer.MAX_VALUE) || (useLower <= bound1)) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for ints cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }

      if (bound2Inclusive) {
        useUpper = bound2;
      } else {
        useUpper = (bound2 - 1);
        if ((bound2 <= Integer.MIN_VALUE) || (useUpper >= bound2)) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for ints cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

    } else {

      if (bound2Inclusive) {
        useLower = bound2;
      } else {
        useLower = (bound2 + 1);
        if ((bound2 >= Integer.MAX_VALUE) || (useLower <= bound2)) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for ints cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

      if (bound1Inclusive) {
        useUpper = bound1;
      } else {
        useUpper = (bound1 - 1);
        if ((bound1 <= Integer.MIN_VALUE) || (useUpper >= bound1)) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for ints cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }
    }

    return IntRandomization.randomNumberBetween(useLower, useUpper,
        fullRange, random);
  }

  /** {@inheritDoc} */
  @Override
  public final Integer randomNumberBetween(final Number bound1,
      final boolean bound1Inclusive, final Number bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    return Integer.valueOf(//
        IntRandomization.randomNumberBetween(bound1.intValue(),
            bound1Inclusive, bound2.intValue(), bound2Inclusive, fullRange,
            random));
  }

  /** {@inheritDoc} */
  @Override
  public final Integer randomValue(final boolean fullRange,
      final Random random) {
    return Integer
        .valueOf(IntRandomization.randomNumber(fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final BoundedLooseIntParser getParserWithRandomBounds(
      final boolean fullRange, final Random random) {
    int lower, upper, temp;

    do {
      lower = IntRandomization.randomNumber(fullRange, random);
      upper = IntRandomization.randomNumber(fullRange, random);
      if (lower > upper) {
        temp = upper;
        upper = lower;
        lower = temp;
      }
    } while ((((long) upper) - lower) <= (fullRange ? 0L : 2L));

    return new BoundedLooseIntParser(lower, upper);
  }

  /** {@inheritDoc} */
  @Override
  public final Integer randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return Integer.valueOf(IntRandomization.randomNumberBetween(
        lowerBound.intValue(), upperBound.intValue(), fullRange, random));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final <X extends Parser<? extends Number>> Integer _randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    final NumberParser numpar;
    final boolean useInt;

    if (parser instanceof NumberParser) {
      numpar = ((NumberParser) parser);
      useInt = numpar.areBoundsInteger();

      return Integer.valueOf(IntRandomization.randomNumberBetween(
          (useInt
              ? ((int) (Math.min(Integer.MAX_VALUE,
                  Math.max(Integer.MIN_VALUE,
                      numpar.getLowerBoundLong()))))
              : ((int) (Math.min(Integer.MAX_VALUE,
                  Math.max(Integer.MIN_VALUE,
                      numpar.getLowerBoundDouble()))))),
          (useInt
              ? ((int) (Math.min(Integer.MAX_VALUE,
                  Math.max(Integer.MIN_VALUE,
                      numpar.getUpperBoundLong()))))
              : ((int) (Math.min(Integer.MAX_VALUE,
                  Math.max(Integer.MIN_VALUE,
                      numpar.getUpperBoundDouble()))))),
          fullRange, random));
    }
    return this.randomValue(fullRange, random);
  }
}
