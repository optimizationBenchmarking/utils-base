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

    if (lowerBound >= upperBound) {
      if (lowerBound <= upperBound) {
        return lowerBound;
      }
      throw new IllegalArgumentException(((("Lower bound " + lowerBound) + //$NON-NLS-1$
          " is higher than upper bound ") + upperBound) + '.'); //$NON-NLS-1$
    }

    return ((short) (random.nextInt((1 + upperBound) - lowerBound)
        + lowerBound));
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
