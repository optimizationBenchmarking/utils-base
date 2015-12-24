package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Graphics;

/** A basic set of strokes. */
public interface IBasicStrokes {

  /**
   * Get the default stroke
   *
   * @return the default stroke
   */
  public abstract IStrokeStyle getDefaultStroke();

  /**
   * Get the thin stroke
   *
   * @return the thin stroke
   */
  public abstract IStrokeStyle getThinStroke();

  /**
   * Get the thick stroke
   *
   * @return the thick stroke
   */
  public abstract IStrokeStyle getThickStroke();

  /**
   * Initialize a graphics object with the default
   * {@link #getDefaultStroke()}.
   *
   * @param graphics
   *          the graphics context to initialize
   */
  public abstract void initializeWithDefaults(final Graphics graphics);

}
