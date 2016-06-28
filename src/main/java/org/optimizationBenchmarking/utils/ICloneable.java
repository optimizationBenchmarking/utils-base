package org.optimizationBenchmarking.utils;

/** An interface for publically cloneable objects */
public interface ICloneable extends Cloneable {

  /**
   * Create a copy of this object-
   *
   * @return the copy
   */
  public abstract Object clone();

}
