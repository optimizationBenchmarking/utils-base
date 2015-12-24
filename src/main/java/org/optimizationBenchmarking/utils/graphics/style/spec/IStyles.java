package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Color;
import java.awt.Graphics;

import org.optimizationBenchmarking.utils.graphics.EFontFamily;

/**
 * The style manager allows to allocate and use colors. Style managers may
 * be scoped.
 */
public interface IStyles extends IBasicColors, IBasicFonts, IBasicStrokes {
  /**
   * Allocate a color. Best effort is made to return colors different from
   * those allocated before and to prevent them from being allocated again.
   *
   * @return the color
   */
  public abstract IColorStyle allocateColor();

  /**
   * Allocate the color most similar to another color. Best effort is made
   * to return colors different from those allocated before and to prevent
   * them from being allocated again.
   *
   * @param color
   *          the other color
   * @return the color
   */
  public abstract IColorStyle allocateMostSimilarColor(final Color color);

  /**
   * Allocate the color most similar to another color. Best effort is made
   * to return colors different from those allocated before and to prevent
   * them from being allocated again.
   *
   * @param rgb
   *          the rgb value of the other color
   * @return the color
   */
  public abstract IColorStyle allocateMostSimilarColor(final int rgb);

  /**
   * Allocate a new font. Best effort is made to return fonts different
   * from those allocated before and to prevent them from being allocated
   * again.
   *
   * @return the font style
   */
  public abstract IFontStyle allocateFont();

  /**
   * Find the font style most similar to a given setup and allocate it.
   * Best effort is made to return fonts different from those allocated
   * before and to prevent them from being allocated again.
   *
   * @param family
   *          the font family
   * @param bold
   *          is the font bold?
   * @param italic
   *          is the font italic?
   * @param underlined
   *          is the font underlined?
   * @param size
   *          the size of the font
   * @return the font style
   */
  public abstract IFontStyle allocateMostSimilarFont(
      final EFontFamily family, final boolean bold, final boolean italic,
      final boolean underlined, final float size);

  /**
   * Allocate a new stroke. Best effort is made to return strokes different
   * from those allocated before and to prevent them from being allocated
   * again.
   *
   * @return the stroke style
   */
  public abstract IStrokeStyle allocateStroke();

  /**
   * Initialize a graphics object with the default
   * {@link #getDefaultFont()} font} and {@link #getDefaultStroke()}, as
   * well as with {@link #getWhite() white} background and
   * {@link #getBlack() black} foreground color.
   *
   * @param graphics
   *          the graphics context to initialize
   */
  @Override
  public abstract void initializeWithDefaults(final Graphics graphics);
}
