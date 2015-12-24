package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Color;

/** A color style. */
public interface IColorStyle extends IStyle {

  /**
   * Get the {@link java.awt.Color} instance associated to this style.
   *
   * @return the {@link java.awt.Color} instance associated to this style.
   */
  public abstract Color getColor();

}
