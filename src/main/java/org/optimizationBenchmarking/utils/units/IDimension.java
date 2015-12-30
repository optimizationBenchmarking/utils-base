package org.optimizationBenchmarking.utils.units;

import java.util.Set;

/**
 * The dimension interface
 */
public interface IDimension {

  /**
   * Return the name of the dimension
   *
   * @return the name of the dimension
   */
  public abstract String getName();

  /**
   * Get the set of units
   *
   * @return the set of units
   */
  public abstract Set<? extends IUnit> getUnits();

}
