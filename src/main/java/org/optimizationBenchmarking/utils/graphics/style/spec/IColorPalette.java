package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Color;

/** A palette of colors provides a set of colors. */
public interface IColorPalette
    extends IStylePalette<IColorStyle>, IBasicColors {
  /**
   * Get the color most similar to another color
   *
   * @param color
   *          the other color
   * @return the color most similar to a given color
   */
  public abstract IColorStyle getMostSimilarColor(final Color color);

  /**
   * Get the color most similar to a given RGB value
   *
   * @param rgb
   *          the rgb value of the color
   * @return the color most similar to the RGB value
   */
  public abstract IColorStyle getMostSimilarColor(final int rgb);
}
