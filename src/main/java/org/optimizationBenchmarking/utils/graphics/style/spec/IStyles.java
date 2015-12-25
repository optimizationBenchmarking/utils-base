package org.optimizationBenchmarking.utils.graphics.style.spec;

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
   * Allocate a new font. Best effort is made to return fonts different
   * from those allocated before and to prevent them from being allocated
   * again.
   *
   * @return the font style
   */
  public abstract IFontStyle allocateFont();

  /**
   * Allocate a new stroke. Best effort is made to return strokes different
   * from those allocated before and to prevent them from being allocated
   * again.
   *
   * @return the stroke style
   */
  public abstract IStrokeStyle allocateStroke();
}
