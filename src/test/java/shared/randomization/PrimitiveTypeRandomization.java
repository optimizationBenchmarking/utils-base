package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.comparison.Compare;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A generator for random values of a specific primitive type
 *
 * @param <T>
 *          the type
 */
public abstract class PrimitiveTypeRandomization<T> {

  /** the maximum attempts for any loop */
  static final int MAX_TRIALS = 100;

  /** create */
  PrimitiveTypeRandomization() {
    super();
  }

  /**
   * Get the primitive type for which this randomization handler is good
   * for.
   *
   * @return the primitive type for which this randomization handler is
   *         good for.
   */
  public abstract EPrimitiveType getType();

  /**
   * Get the parser for the type.
   *
   * @return the parser for the type.
   */
  @SuppressWarnings("unchecked")
  public Parser<T> getBasicParser() {
    return ((Parser<T>) (this.getType().getLooseParser()));
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
  public abstract T randomValue(final boolean fullRange,
      final Random random);

  /**
   * Get a bounded parser for the given type.
   *
   * @param fullRange
   *          should the full range of the type be used, or should we
   *          restrict the range such that overflows etc. are avoided
   * @param random
   *          the random number generator
   * @return a random parser
   */
  public Parser<T> getParserWithRandomBounds(final boolean fullRange,
      final Random random) {
    return this.getBasicParser();
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
  public T randomValueBetween(final T lowerBound, final T upperBound,
      final boolean fullRange, final Random random) {
    int i;
    T v;

    for (i = 100; (--i) >= 0;) {
      v = this.randomValue(fullRange, random);
      if ((Compare.compare(lowerBound, v) <= 0) && //
          (Compare.compare(v, upperBound) <= 0)) {
        return v;
      }
    }

    return (random.nextBoolean() ? lowerBound : upperBound);
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
  public abstract <X extends Parser<? extends T>> T randomValue(
      final X parser, final boolean fullRange, final Random random);

  /**
   * Get the primitive type randomization for the specified primitive type.
   *
   * @param type
   *          the type
   * @return the randomization
   */
  public static final PrimitiveTypeRandomization<?> forPrimitiveType(
      final EPrimitiveType type) {
    final PrimitiveTypeRandomization<?> result;
    result = NumberRandomization._forNumericalPrimitiveType(type);
    if (result != null) {
      return result;
    }
    throw new IllegalArgumentException(//
        "There is no primitive type randomizer for type " + type); //$NON-NLS-1$
  }

  /**
   * Get the primitive type randomization for the specified class.
   *
   * @param clazz
   *          the class
   * @return the randomization
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final <T> PrimitiveTypeRandomization<T> forClass(
      final Class<T> clazz) {
    final PrimitiveTypeRandomization result;

    result = NumberRandomization._forNumericalClass((Class) clazz);
    if (result != null) {
      return result;
    }

    throw new IllegalArgumentException(//
        "There is no primitive type randomizer for class " + clazz); //$NON-NLS-1$

  }

  /**
   * Get the primitive type randomization for the specified parser.
   *
   * @param parser
   *          the parser
   * @return the randomization
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final <T> PrimitiveTypeRandomization<T> forParser(
      final Parser<T> parser) {
    final PrimitiveTypeRandomization result;

    result = NumberRandomization._forNumberParser((Parser) parser);
    if (result != null) {
      return result;
    }
    return PrimitiveTypeRandomization.forClass(parser.getOutputClass());
  }
}
