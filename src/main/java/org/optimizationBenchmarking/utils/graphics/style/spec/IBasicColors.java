package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Graphics;

/** A set of basic colors. */
public interface IBasicColors {

  /**
   * Get the black color
   *
   * @return the black color
   */
  public abstract IColorStyle getBlack();

  /**
   * Get the white color
   *
   * @return the white color
   */
  public abstract IColorStyle getWhite();

  /**
   * Initialize a graphics object with {@link #getWhite() white} background
   * and {@link #getBlack() black} foreground color.
   *
   * @param graphics
   *          the graphics context to initialize
   */
  public abstract void initializeWithDefaults(final Graphics graphics);
}
