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
    int difference, trial;

    if (lowerBound >= upperBound) {
      if (lowerBound <= upperBound) {
        return lowerBound;
      }
      throw new IllegalArgumentException(((("Lower bound " + lowerBound) + //$NON-NLS-1$
          " is higher than upper bound ") + upperBound) + '.'); //$NON-NLS-1$
    }

    difference = (upperBound - lowerBound);
    if ((difference >= 0) && (difference < Integer.MAX_VALUE)) {
      return (lowerBound + random.nextInt(difference + 1));
    }

    do {
      trial = random.nextInt();
    } while ((trial < lowerBound) || (trial > upperBound));

    return trial;
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
