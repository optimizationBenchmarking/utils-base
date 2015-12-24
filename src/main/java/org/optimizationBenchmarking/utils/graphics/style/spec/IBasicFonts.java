package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Graphics;

/** A set of basic fonts. */
public interface IBasicFonts {

  /**
   * Get the default font style
   *
   * @return the default font style
   */
  public abstract IFontStyle getDefaultFont();

  /**
   * Get the emphasize font style
   *
   * @return the emphasize font style
   */
  public abstract IFontStyle getEmphFont();

  /**
   * Get the code font style
   *
   * @return the code font style
   */
  public abstract IFontStyle getCodeFont();

  /**
   * Initialize a graphics object with the default
   * {@link #getDefaultFont()} font} .
   *
   * @param graphics
   *          the graphics context to initialize
   */
  public abstract void initializeWithDefaults(final Graphics graphics);
}
