package shared.randomization;

import java.util.Random;

import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

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

  /**
   * Get the primitive type randomization for the specified primitive type.
   *
   * @param type
   *          the type
   * @return the randomization
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  static final PrimitiveTypeRandomization<Number> _forNumericalPrimitiveType(
      final EPrimitiveType type) {
    switch (type) {
      case BYTE: {
        return ((PrimitiveTypeRandomization) (ByteRandomization.INSTANCE));
      }
      case SHORT: {
        return ((PrimitiveTypeRandomization) (ShortRandomization.INSTANCE));
      }
      case INT: {
        return ((PrimitiveTypeRandomization) (IntRandomization.INSTANCE));
      }
      case LONG: {
        return ((PrimitiveTypeRandomization) (LongRandomization.INSTANCE));
      }
      case FLOAT: {
        return ((PrimitiveTypeRandomization) (FloatRandomization.INSTANCE));
      }
      case DOUBLE: {
        return ((PrimitiveTypeRandomization) (DoubleRandomization.INSTANCE));
      }
      default: {
        return null;
      }
    }
  }

  /**
   * Get the primitive type randomization for the specified class.
   *
   * @param clazz
   *          the class
   * @return the randomization
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  static final PrimitiveTypeRandomization<Number> _forNumericalClass(
      final Class<? extends Number> clazz) {
    if ((clazz == int.class) || (clazz == Integer.class)) {
      return ((PrimitiveTypeRandomization) (IntRandomization.INSTANCE));
    }
    if ((clazz == long.class) || (clazz == Long.class)) {
      return ((PrimitiveTypeRandomization) (LongRandomization.INSTANCE));
    }
    if ((clazz == double.class) || (clazz == Double.class)) {
      return ((PrimitiveTypeRandomization) (DoubleRandomization.INSTANCE));
    }
    if ((clazz == byte.class) || (clazz == Byte.class)) {
      return ((PrimitiveTypeRandomization) (ByteRandomization.INSTANCE));
    }
    if ((clazz == short.class) || (clazz == Short.class)) {
      return ((PrimitiveTypeRandomization) (ShortRandomization.INSTANCE));
    }
    if ((clazz == float.class) || (clazz == Float.class)) {
      return ((PrimitiveTypeRandomization) (FloatRandomization.INSTANCE));
    }
    if (Number.class.isAssignableFrom(clazz)) {
      return ((PrimitiveTypeRandomization) (ByteRandomization.INSTANCE));
    }
    return null;
  }

  /**
   * Get the primitive type randomization for the specified parser.
   *
   * @param parser
   *          the parser
   * @return the randomization
   */
  static final PrimitiveTypeRandomization<Number> _forNumberParser(
      final Parser<? extends Number> parser) {
    return NumberRandomization._forNumericalClass(parser.getOutputClass());
  }

  /**
   * Get the primitive type randomization for the specified primitive type.
   *
   * @param type
   *          the type
   * @return the randomization
   */
  public static final PrimitiveTypeRandomization<Number> forNumericalPrimitiveType(
      final EPrimitiveType type) {
    final PrimitiveTypeRandomization<Number> result;

    result = NumberRandomization._forNumericalPrimitiveType(type);
    if (result != null) {
      return result;
    }
    throw new IllegalArgumentException(//
        "There is no numerical primitive type randomizer for type " //$NON-NLS-1$
            + type);
  }

  /**
   * Get the primitive type randomization for the specified class.
   *
   * @param clazz
   *          the class
   * @return the randomization
   */
  public static final PrimitiveTypeRandomization<Number> forNumericalClass(
      final Class<? extends Number> clazz) {
    final PrimitiveTypeRandomization<Number> result;

    result = NumberRandomization._forNumericalClass(clazz);
    if (result != null) {
      return result;
    }

    throw new IllegalArgumentException(//
        "There is no numerical primitive type randomizer for class " //$NON-NLS-1$
            + clazz);
  }

  /**
   * Get the primitive type randomization for the specified parser.
   *
   * @param parser
   *          the parser
   * @return the randomization
   */
  public static final PrimitiveTypeRandomization<Number> forNumberParser(
      final Parser<? extends Number> parser) {
    return NumberRandomization.forNumericalClass(parser.getOutputClass());
  }
}
