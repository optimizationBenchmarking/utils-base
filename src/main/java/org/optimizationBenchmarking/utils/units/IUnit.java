package org.optimizationBenchmarking.utils.units;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** The interface common to all units. */
public interface IUnit {
  /**
   * Obtain the conversion function to a given unit, return {@code null} if
   * no conversion is known.
   *
   * @param other
   *          the other unit
   * @return the conversion function to that function
   * @throws IllegalArgumentException
   *           if no conversion function exists
   */
  public abstract UnaryFunction getConversionFunction(final IUnit other);

  /**
   * Convert a value from this unit to another one
   *
   * @param value
   *          the value
   * @param other
   *          the other unit
   * @return the conversion result, too large values saturate at
   *         Long.MAX_VALUE, too small values at Long.MIN_VALUE
   * @throws IllegalArgumentException
   *           if conversion not possible
   */
  public abstract double convertToAsDouble(final long value,
      final IUnit other);

  /**
   * Convert a value from this unit to another one
   *
   * @param value
   *          the value
   * @param other
   *          the other unit
   * @return the conversion result,
   * @throws IllegalArgumentException
   *           if conversion not possible
   */
  public abstract double convertToAsDouble(final double value,
      final IUnit other);

  /**
   * Convert a value from this unit to another one
   *
   * @param value
   *          the value
   * @param other
   *          the other unit
   * @return the conversion result, too large values saturate at
   *         Long.MAX_VALUE, too small values at Long.MIN_VALUE
   * @throws IllegalArgumentException
   *           if conversion not possible
   */
  public abstract long convertToAsLong(final long value,
      final IUnit other);

  /**
   * Obtain the long name
   *
   * @return the long name
   */
  public abstract String getLongName();

  /**
   * Obtain the name
   *
   * @return the name
   */
  public abstract String getName();

  /**
   * Obtain the shortcut
   *
   * @return the shortcut
   */
  public abstract String getShortcut();

  /**
   * Get the dimension to which the unit belongs
   *
   * @return the dimension to which the unit belongs
   */
  public abstract IDimension getDimension();
}
