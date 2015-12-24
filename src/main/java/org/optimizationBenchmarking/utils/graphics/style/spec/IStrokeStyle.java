package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Stroke;

/**
 * A style associated with a stroke.
 */
public interface IStrokeStyle extends IStyle {

  /**
   * Get the {@link java.awt.Stroke stroke} associated with this style.
   *
   * @return the {@link java.awt.Stroke stroke} associated with this style.
   */
  public abstract Stroke getStroke();

}
