package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.parsers.BoundedLooseLongParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A {@code long} randomization implementation. */
public final class LongRandomization extends NumberRandomization<Long> {

  /** the globally shared instance of the {@code long} randomization */
  public static final LongRandomization INSTANCE = new LongRandomization();

  /**
   * the safe range maximum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final long SAFE_MAX = (Long.MAX_VALUE >>> 1);
  /**
   * the safe range minimum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final long SAFE_MIN = (-LongRandomization.SAFE_MAX);

  /** create */
  private LongRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getType() {
    return EPrimitiveType.LONG;
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
  public static final long randomNumber(final boolean fullRange,
      final Random random) {
    return LongRandomization.randomNumberBetween( //
        (fullRange ? Long.MIN_VALUE : LongRandomization.SAFE_MIN), //
        (fullRange ? Long.MAX_VALUE : LongRandomization.SAFE_MAX), //
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
  public static final long randomNumberBetween(final long lowerBound,
      final long upperBound, final boolean fullRange,
      final Random random) {
    long difference, trial;

    if (lowerBound >= upperBound) {
      if (lowerBound <= upperBound) {
        return lowerBound;
      }
      throw new IllegalArgumentException(((("Lower bound " + lowerBound) + //$NON-NLS-1$
          " is higher than upper bound ") + upperBound) + '.'); //$NON-NLS-1$
    }

    difference = ((upperBound - lowerBound) + 1L);
    if ((difference <= 0L) || (difference >= Long.MAX_VALUE)) {
      do {
        trial = random.nextLong();
      } while ((trial < lowerBound) || (trial > upperBound));
      return trial;
    }

    if (difference < Integer.MAX_VALUE) {
      return lowerBound + random.nextInt((int) difference);
    }

    // warning: this is not uniform distributed
    return (lowerBound
        + ((random.nextLong() & 0x7fffffffffffffffL) % difference));
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
  public static final long randomNumberBetween(final long bound1,
      final boolean bound1Inclusive, final long bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    final long useLower, useUpper;

    if (bound1 < bound2) {

      if (bound1Inclusive) {
        useLower = bound1;
      } else {
        if (bound1 >= Long.MAX_VALUE) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for longs cannot be " //$NON-NLS-1$
                  + bound1);
        }
        useLower = (bound1 + 1L);
      }

      if (bound2Inclusive) {
        useUpper = bound2;
      } else {
        if (bound2 <= Long.MIN_VALUE) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for longs cannot be " //$NON-NLS-1$
                  + bound2);
        }
        useUpper = (bound2 - 1L);
      }

    } else {

      if (bound2Inclusive) {
        useLower = bound2;
      } else {
        if (bound2 >= Long.MAX_VALUE) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for longs cannot be " //$NON-NLS-1$
                  + bound2);
        }
        useLower = (bound2 + 1L);
      }

      if (bound1Inclusive) {
        useUpper = bound1;
      } else {
        if (bound1 <= Long.MIN_VALUE) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for longs cannot be " //$NON-NLS-1$
                  + bound1);
        }
        useUpper = (bound1 - 1L);
      }
    }

    return LongRandomization.randomNumberBetween(useLower, useUpper,
        fullRange, random);
  }

  /** {@inheritDoc} */
  @Override
  public final Long randomNumberBetween(final Number bound1,
      final boolean bound1Inclusive, final Number bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    return Long.valueOf(//
        LongRandomization.randomNumberBetween(bound1.longValue(),
            bound1Inclusive, bound2.longValue(), bound2Inclusive,
            fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final Long randomValue(final boolean fullRange,
      final Random random) {
    return Long.valueOf(LongRandomization.randomNumber(fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final BoundedLooseLongParser getParserWithRandomBounds(
      final boolean fullRange, final Random random) {
    long lower, upper, temp;

    do {
      lower = LongRandomization.randomNumber(fullRange, random);
      upper = LongRandomization.randomNumber(fullRange, random);
      if (lower > upper) {
        temp = upper;
        upper = lower;
        lower = temp;
      }
      temp = lower;
      if (fullRange) {
        temp += 2L;
      }
    } while ((temp < lower) || (temp >= upper));

    return new BoundedLooseLongParser(lower, upper);
  }

  /** {@inheritDoc} */
  @Override
  public final Long randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return Long.valueOf(
        LongRandomization.randomNumberBetween(lowerBound.longValue(),
            upperBound.longValue(), fullRange, random));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final <X extends Parser<? extends Number>> Long _randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    final NumberParser numpar;
    final boolean useLong;

    if (parser instanceof NumberParser) {
      numpar = ((NumberParser) parser);
      useLong = numpar.areBoundsInteger();

      return Long
          .valueOf(
              LongRandomization.randomNumberBetween(
                  (useLong
                      ? ((long) (Math.min(Long.MAX_VALUE,
                          Math.max(Long.MIN_VALUE,
                              numpar.getLowerBoundLong()))))
                      : ((long) (Math.min(Long.MAX_VALUE,
                          Math.max(Long.MIN_VALUE,
                              numpar.getLowerBoundDouble()))))),
                  (useLong
                      ? ((long) (Math.min(Long.MAX_VALUE,
                          Math.max(Long.MIN_VALUE,
                              numpar.getUpperBoundLong()))))
                      : ((long) (Math.min(Long.MAX_VALUE,
                          Math.max(Long.MIN_VALUE,
                              numpar.getUpperBoundDouble()))))),
                  fullRange, random));
    }
    return this.randomValue(fullRange, random);
  }
}
