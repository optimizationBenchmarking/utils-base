package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A {@code double} randomization implementation. */
public final class DoubleRandomization
    extends NumberRandomization<Double> {

  /** the globally shared instance of the {@code double} randomization */
  public static final DoubleRandomization INSTANCE = new DoubleRandomization();

  /**
   * the safe range maximum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final double SAFE_MAX = Math.sqrt(Double.MAX_VALUE);
  /**
   * the safe range minimum: adding and subtracting two values in the safe
   * range can never lead to overflow
   */
  private static final double SAFE_MIN = (-DoubleRandomization.SAFE_MAX);

  /** the minimal exponent */
  private static final int MIN_EXP = (1
      + ((int) (Math.log(Double.MIN_VALUE))));
  /** the maximal exponent */
  private static final int MAX_EXP = (((int) (Math.log(Double.MAX_VALUE)))
      - 1);

  /** create */
  private DoubleRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getType() {
    return EPrimitiveType.DOUBLE;
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
  public static final double randomNumber(final boolean fullRange,
      final Random random) {
    return DoubleRandomization.randomNumberBetween( //
        (fullRange ? -Double.MAX_VALUE : DoubleRandomization.SAFE_MIN), //
        (fullRange ? Double.MAX_VALUE : DoubleRandomization.SAFE_MAX), //
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
  public static final double randomNumberBetween(final double lowerBound,
      final double upperBound, final boolean fullRange,
      final Random random) {
    final double useLower, useUpper, difference;
    final boolean negative;
    final long steps, bits;
    double trial;
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

    if (fullRange) {
      if (lowerBound <= Double.NEGATIVE_INFINITY) {
        if (random.nextInt(1000) <= 0) {
          return Double.NEGATIVE_INFINITY;
        }
        useLower = (-Double.MAX_VALUE);
      } else {
        useLower = lowerBound + 0d;
      }

      if (upperBound >= Double.POSITIVE_INFINITY) {
        if (random.nextInt(1000) <= 0) {
          return Double.POSITIVE_INFINITY;
        }
        useUpper = Double.MAX_VALUE;
      } else {
        useUpper = upperBound + 0d;
      }
    } else {
      useLower = (((lowerBound < DoubleRandomization.SAFE_MIN)
          && (DoubleRandomization.SAFE_MIN <= upperBound))
              ? DoubleRandomization.SAFE_MIN : (lowerBound + 0d));
      useUpper = (((upperBound > DoubleRandomization.SAFE_MAX)
          && (DoubleRandomization.SAFE_MAX >= useLower))
              ? DoubleRandomization.SAFE_MAX : (upperBound + 0d));
    }
    if (useLower >= useUpper) {
      return useLower;
    }

    steps = MathUtils.numbersBetween(useLower, useUpper);
    if (steps < Long.MAX_VALUE) {
      // attempt approximately uniform distribution over all possible
      // values
      negative = (useLower < 0d);
      bits = Double.doubleToRawLongBits(useLower);

      for (looper = PrimitiveTypeRandomization.MAX_TRIALS; (--looper) >= 0;) {
        offset = LongRandomization.randomNumberBetween(0, steps, true,
            random);

        if (negative) {
          trial = Double.longBitsToDouble(bits - offset);
        } else {
          trial = Double.longBitsToDouble(bits + offset);
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
      for (looper = PrimitiveTypeRandomization.MAX_TRIALS; (--looper) >= 0;) {
        trial = (useLower + (random.nextDouble() * difference));
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
    for (looper = PrimitiveTypeRandomization.MAX_TRIALS; (--looper) >= 0;) {
      trial = random.nextDouble()
          * Math.exp(random
              .nextInt(DoubleRandomization.MAX_EXP
                  - DoubleRandomization.MIN_EXP)
          + DoubleRandomization.MIN_EXP);
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
  public static final double randomNumberBetween(final double bound1,
      final boolean bound1Inclusive, final double bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    final double useLower, useUpper;

    if (bound1 <= bound2) {

      if (bound1Inclusive) {
        useLower = bound1;
      } else {
        useLower = Math.nextUp(bound1);
        if ((useLower <= bound1) || //
            ((bound1 >= Double.MAX_VALUE) && //
                ((!(fullRange))
                    || (bound1 >= Double.POSITIVE_INFINITY)))) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for floats cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }

      if (bound2Inclusive) {
        useUpper = bound2;
      } else {
        useUpper = Math.nextAfter(bound2, Double.NEGATIVE_INFINITY);
        if ((useUpper >= bound2) || //
            ((bound2 <= (-Double.MAX_VALUE)) && //
                ((!(fullRange))
                    || (bound2 <= Double.NEGATIVE_INFINITY)))) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for floats cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

    } else {

      if (bound2Inclusive) {
        useLower = bound2;
      } else {
        useLower = Math.nextUp(bound2);
        if ((useLower <= bound2) || //
            ((bound2 >= Double.MAX_VALUE) && //
                ((!(fullRange))
                    || (bound2 >= Double.POSITIVE_INFINITY)))) {
          throw new IllegalArgumentException(//
              "Exclusive lower bound for floats cannot be " //$NON-NLS-1$
                  + bound2);
        }
      }

      if (bound1Inclusive) {
        useUpper = bound1;
      } else {
        useUpper = Math.nextAfter(bound1, Double.NEGATIVE_INFINITY);
        if ((useUpper >= bound1) || //
            ((bound1 <= (-Double.MAX_VALUE)) && //
                ((!(fullRange))
                    || (bound1 <= Double.NEGATIVE_INFINITY)))) {
          throw new IllegalArgumentException(//
              "Exclusive upper bound for floats cannot be " //$NON-NLS-1$
                  + bound1);
        }
      }
    }

    return DoubleRandomization.randomNumberBetween(useLower, useUpper,
        fullRange, random);
  }

  /** {@inheritDoc} */
  @Override
  public final Double randomNumberBetween(final Number bound1,
      final boolean bound1Inclusive, final Number bound2,
      final boolean bound2Inclusive, final boolean fullRange,
      final Random random) {
    return Double.valueOf(//
        DoubleRandomization.randomNumberBetween(bound1.doubleValue(),
            bound1Inclusive, bound2.doubleValue(), bound2Inclusive,
            fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final Double randomValue(final boolean fullRange,
      final Random random) {
    return Double.valueOf(//
        DoubleRandomization.randomNumber(fullRange, random));
  }

  /** {@inheritDoc} */
  @Override
  public final BoundedLooseDoubleParser getParserWithRandomBounds(
      final boolean fullRange, final Random random) {
    double lower, upper, temp;
    int exp;

    for (;;) {

      if (random.nextInt(fullRange ? 7 : 10) <= 0) {
        lower = DoubleRandomization.randomNumber(fullRange, random);
        upper = DoubleRandomization.randomNumber(fullRange, random);
      } else {

        exp = (DoubleRandomization.MIN_EXP + random.nextInt(
            DoubleRandomization.MAX_EXP - DoubleRandomization.MIN_EXP));

        do {
          lower = Math.exp(exp + (3d * random.nextGaussian()));
          if (random.nextBoolean()) {
            lower = -lower;
          }
        } while ((lower != lower) || (lower <= Double.NEGATIVE_INFINITY)
            || (lower >= Double.POSITIVE_INFINITY));

        do {
          upper = Math.exp(exp + (3d * random.nextGaussian()));
          if (random.nextBoolean()) {
            upper = -upper;
          }
        } while ((upper != upper) || (upper <= Double.NEGATIVE_INFINITY)
            || (upper >= Double.POSITIVE_INFINITY));
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
      if (temp >= Double.POSITIVE_INFINITY) {
        if (!fullRange) {
          continue;
        }
      }
      if (!fullRange) {
        if ((temp / Math.max(Math.abs(lower), Math.abs(upper))) < 1e-5d) {
          continue;
        }
      }
      break;
    }

    return new BoundedLooseDoubleParser(lower, upper);
  }

  /** {@inheritDoc} */
  @Override
  public final Double randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return Double.valueOf(
        DoubleRandomization.randomNumberBetween(lowerBound.doubleValue(),
            upperBound.doubleValue(), fullRange, random));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final <X extends Parser<? extends Number>> Double _randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    final NumberParser numpar;
    final boolean useDouble;

    if (parser instanceof NumberParser) {
      numpar = ((NumberParser) parser);
      useDouble = numpar.areBoundsInteger();

      return Double.valueOf(DoubleRandomization.randomNumberBetween(
          (useDouble
              ? ((double) (Math.min(Double.MAX_VALUE,
                  Math.max(-Double.MAX_VALUE,
                      numpar.getLowerBoundDouble()))))
              : ((double) (Math.min(Double.MAX_VALUE,
                  Math.max(-Double.MAX_VALUE,
                      numpar.getLowerBoundDouble()))))),
          (useDouble
              ? ((double) (Math.min(Double.MAX_VALUE,
                  Math.max(-Double.MAX_VALUE,
                      numpar.getUpperBoundDouble()))))
              : ((double) (Math.min(Double.MAX_VALUE,
                  Math.max(-Double.MAX_VALUE,
                      numpar.getUpperBoundDouble()))))),
          fullRange, random));
    }
    return this.randomValue(fullRange, random);
  }
}
