package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A {@code float} randomization implementation. */
public final class FloatRandomization extends NumberRandomization<Float> {

  /** the globally shared instance of the {@code float} randomization */
  public static final FloatRandomization INSTANCE = new FloatRandomization();

  /**
   * the safe range maximum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final float SAFE_MAX = ((float) (Math
      .sqrt(Float.MAX_VALUE)));
  /**
   * the safe range minimum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final float SAFE_MIN = (-FloatRandomization.SAFE_MAX);

  /** the minimal exponent */
  private static final int MIN_EXP = (1
      + ((int) (Math.log(Float.MIN_VALUE))));
  /** the maximal exponent */
  private static final int MAX_EXP = (((int) (Math.log(Float.MAX_VALUE)))
      - 1);

  /** create */
  private FloatRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getType() {
    return EPrimitiveType.FLOAT;
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
  public static final float randomNumber(final boolean fullRange,
      final Random random) {
    return FloatRandomization.randomNumberBetween( //
        (fullRange ? -Float.MAX_VALUE : FloatRandomization.SAFE_MIN), //
        (fullRange ? Float.MAX_VALUE : FloatRandomization.SAFE_MAX), //
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
  public static final float randomNumberBetween(final float lowerBound,
      final float upperBound, final boolean fullRange,
      final Random random) {
    final float useLower, useUpper;
    final boolean negative;
    final long steps;
    final int bits;
    final double difference;
    float trial;
    long offset;
    int looper;

    if (lowerBound >= upperBound) {
      if (lowerBound <= upperBound) {
        return lowerBound;
      }
      throw new IllegalArgumentException(((("Lower bound " + lowerBound) + //$NON-NLS-1$
          " is higher than upper bound ") + upperBound) + '.'); //$NON-NLS-1$
    }
    if ((lowerBound != lowerBound) || (upperBound != upperBound)) {
      throw new IllegalArgumentException(
          ((("Bounds must be finite, but [" + lowerBound) + //$NON-NLS-1$
              ',') + ' ') + upperBound + "] is not."); //$NON-NLS-1$
    }

    if (lowerBound <= Double.NEGATIVE_INFINITY) {
      if (fullRange && (random.nextInt(1000) <= 0)) {
        return Float.NEGATIVE_INFINITY;
      }
      useLower = (-Float.MAX_VALUE);
    } else {
      useLower = (lowerBound + 0f);
    }

    if (upperBound >= Float.POSITIVE_INFINITY) {
      if (fullRange && (random.nextInt(1000) <= 0)) {
        return Float.POSITIVE_INFINITY;
      }
      useUpper = Float.MAX_VALUE;
    } else {
      useUpper = (upperBound + 0f);
    }

    steps = MathUtils.numbersBetween(useLower, useUpper);
    if (steps < Integer.MAX_VALUE) {
      // attempt approximately uniform distribution over all possible
      // values
      negative = (useLower < 0d);
      bits = Float.floatToRawIntBits(useLower);

      for (looper = 1000; (--looper) >= 0;) {
        offset = LongRandomization.randomNumberBetween(0, steps, true,
            random);

        if (negative) {
          trial = Float.intBitsToFloat((int) (bits - offset));
        } else {
          trial = Float.intBitsToFloat((int) (bits + offset));
        }
        if ((trial >= useLower) && (trial <= useUpper)) {
          if (!fullRange) {
            if (((useLower + trial) == useLower)
                || ((useUpper + trial) == useUpper)) {
              continue;
            }
          }
          return trial;
        }
      }
    }

    // ok, scale-uniformity not possible, attempt range uniformity
    difference = (useUpper - useLower);
    if ((difference > 0d) && (difference < Double.POSITIVE_INFINITY)) {
      for (looper = 1000; (--looper) >= 0;) {
        trial = (float) (useLower + (random.nextDouble() * difference));
        if ((trial >= useLower) && (trial <= useUpper)) {
          if (!fullRange) {
            if (((useLower + trial) == useLower)
                || ((useUpper + trial) == useUpper)) {
              continue;
            }
          }
          return trial;
        }
      }
    }

    // range too wide
    for (looper = 1000; (--looper) >= 0;) {
      trial = (float) (random.nextDouble() * Math.exp(random
          .nextInt(FloatRandomization.MAX_EXP - FloatRandomization.MIN_EXP)
          + FloatRandomization.MIN_EXP));
      if ((trial >= useLower) && (trial <= useUpper)) {
        if (!fullRange) {
          if (((useLower + trial) == useLower)
              || ((useUpper + trial) == useUpper)) {
            continue;
          }
        }
        return trial;
      }
    }

    return (random.nextBoolean() ? useLower : useUpper);
  }

  /** {@inheritDoc} */
  @Override
  public final Float randomValue(final boolean fullRange,
      final Random random) {
    return Float
        .valueOf(FloatRandomization.randomNumber(fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final BoundedLooseFloatParser getParserWithRandomBounds(
      final boolean fullRange, final Random random) {
    float lower, upper, temp;
    int exp;

    for (;;) {

      if (random.nextInt(fullRange ? 7 : 10) <= 0) {
        lower = FloatRandomization.randomNumber(fullRange, random);
        upper = FloatRandomization.randomNumber(fullRange, random);
      } else {

        exp = (FloatRandomization.MIN_EXP + random.nextInt(
            FloatRandomization.MAX_EXP - FloatRandomization.MIN_EXP));

        do {
          lower = ((float) (Math.exp(exp + (3d * random.nextGaussian()))));
          if (random.nextBoolean()) {
            lower = -lower;
          }
        } while ((lower != lower) || (lower <= Float.NEGATIVE_INFINITY)
            || (lower >= Float.POSITIVE_INFINITY));

        do {
          upper = ((float) (Math.exp(exp + (3d * random.nextGaussian()))));
          if (random.nextBoolean()) {
            upper = -upper;
          }
        } while ((upper != upper) || (upper <= Float.NEGATIVE_INFINITY)
            || (upper >= Float.POSITIVE_INFINITY));
      }

      if (lower > upper) {
        temp = upper;
        upper = lower;
        lower = temp;
      }
      temp = upper - lower;
      if (temp <= 0d) {
        continue;
      }
      if (temp >= Float.POSITIVE_INFINITY) {
        if (!fullRange) {
          continue;
        }
      }
      if (!fullRange) {
        if ((temp / Math.max(Math.abs(lower), Math.abs(upper))) < 1e-3f) {
          continue;
        }
      }
      break;
    }

    return new BoundedLooseFloatParser(lower, upper);
  }

  /** {@inheritDoc} */
  @Override
  public final Float randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return Float.valueOf(
        FloatRandomization.randomNumberBetween(lowerBound.floatValue(),
            upperBound.floatValue(), fullRange, random));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final <X extends Parser<? extends Number>> Float _randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    final NumberParser numpar;
    final boolean useLong;

    if (parser instanceof NumberParser) {
      numpar = ((NumberParser) parser);
      useLong = numpar.areBoundsInteger();

      return Float
          .valueOf(
              FloatRandomization.randomNumberBetween(
                  (useLong
                      ? ((float) (Math.min(Float.MAX_VALUE,
                          Math.max(-Float.MAX_VALUE,
                              numpar.getLowerBoundLong()))))
                      : ((float) (Math.min(Float.MAX_VALUE,
                          Math.max(-Float.MAX_VALUE,
                              numpar.getLowerBoundDouble()))))),
                  (useLong
                      ? ((float) (Math.min(Float.MAX_VALUE,
                          Math.max(-Float.MAX_VALUE,
                              numpar.getUpperBoundLong()))))
                      : ((float) (Math.min(Float.MAX_VALUE,
                          Math.max(-Float.MAX_VALUE,
                              numpar.getUpperBoundDouble()))))),
                  fullRange, random));
    }
    return this.randomValue(fullRange, random);
  }
}
