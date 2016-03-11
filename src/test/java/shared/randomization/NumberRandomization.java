package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * A generator for random values of a specific primitive numerical type
 *
 * @param <T>
 *          the type
 */
public abstract class NumberRandomization<T extends Number>
    extends PrimitiveTypeRandomization<T> {

  /** create */
  NumberRandomization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final NumberParser<T> getBasicParser() {
    return ((NumberParser<T>) (this.getType().getLooseParser()));
  }

  /** {@inheritDoc} */
  @Override
  public NumberParser<T> getParserWithRandomBounds(final boolean fullRange,
      final Random random) {
    return this.getBasicParser();
  }

  /** {@inheritDoc} */
  @Override
  public final T randomValueBetween(final T lowerBound, final T upperBound,
      final boolean fullRange, final Random random) {
    return this.randomNumberBetween(lowerBound, upperBound, fullRange,
        random);
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
  @SuppressWarnings("unchecked")
  public T randomNumberBetween(final Number lowerBound,
      final Number upperBound, final boolean fullRange,
      final Random random) {
    return super.randomValueBetween(((T) lowerBound), ((T) upperBound),
        fullRange, random);
  }

  /** {@inheritDoc} */
  @Override
  public final <X extends Parser<? extends T>> T randomValue(
      final X parser, final boolean fullRange, final Random random) {
    return this.randomNumber(((Parser<? extends Number>) parser),
        fullRange, random);
  }

  /**
   * Get a random value adhering to the given parser's specification
   *
   * @param parser
   *          the parser
   * @param fullRange
   *          should the full range of the type be used, or should we
   *          restrict the range such that overflows etc. are avoided
   * @param random
   *          the random number generator
   * @return the value, or {@code null} if too many trials attempting to
   *         create the value have failed
   * @throws IllegalArgumentException
   *           if the parser is, somehow, invalid
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final <X extends Parser<? extends Number>> T randomNumber(
      final X parser, final boolean fullRange, final Random random) {
    int trials;
    T number;

    for (trials = 1000; (--trials) >= 0;) {
      number = this._randomNumber(parser, fullRange, random);
      try {
        ((Parser) parser).validate(number);
      } catch (@SuppressWarnings("unused") final Throwable error) {
        continue;
      }

      return number;
    }

    return null;
  }

  /**
   * Get a random value adhering to the given parser's specification
   *
   * @param parser
   *          the parser
   * @param fullRange
   *          should the full range of the type be used, or should we
   *          restrict the range such that overflows etc. are avoided
   * @param random
   *          the random number generator
   * @return the value, or {@code null} if too many trials attempting to
   *         create the value have failed
   * @throws IllegalArgumentException
   *           if the parser is, somehow, invalid
   */
  abstract <X extends Parser<? extends Number>> T _randomNumber(
      final X parser, final boolean fullRange, final Random random);
}
