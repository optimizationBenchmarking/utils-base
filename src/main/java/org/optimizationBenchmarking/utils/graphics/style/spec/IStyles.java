package org.optimizationBenchmarking.utils.graphics.style.spec;

/**
 * The style manager allows to allocate and use colors. Style managers may
 * be scoped.
 */
public interface IStyles extends IBasicStyles {
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

  /**
   * Obtain the color of a given name. If {@code allocateIfUndefined} is
   * {@code true} and no color is defined for the given name yet, then one
   * will be allocated. If {@code allocateIfUndefined} is {@code false} and
   * no color is defined for the given name yet, {@code null} will be
   * returned.
   *
   * @param name
   *          the name
   * @param allocateIfUndefined
   *          should the color be allocated if it is still undefined?
   * @return the color for the given name, or {@code null} if none was
   *         allocated for the name and {@code allocateIfUndefined} is
   *         {@code false}
   */
  public abstract IColorStyle getColor(final String name,
      final boolean allocateIfUndefined);

  /**
   * Obtain the stroke of a given name. If {@code allocateIfUndefined} is
   * {@code true} and no stroke is defined for the given name yet, then one
   * will be allocated. If {@code allocateIfUndefined} is {@code false} and
   * no stroke is defined for the given name yet, {@code null} will be
   * returned.
   *
   * @param name
   *          the name
   * @param allocateIfUndefined
   *          should the stroke be allocated if it is still undefined?
   * @return the stroke for the given name, or {@code null} if none was
   *         allocated for the name and {@code allocateIfUndefined} is
   *         {@code false}
   */
  public abstract IStrokeStyle getStroke(final String name,
      final boolean allocateIfUndefined);

  /**
   * Obtain the font of a given name. If {@code allocateIfUndefined} is
   * {@code true} and no font is defined for the given name yet, then one
   * will be allocated. If {@code allocateIfUndefined} is {@code false} and
   * no font is defined for the given name yet, {@code null} will be
   * returned.
   *
   * @param name
   *          the name
   * @param allocateIfUndefined
   *          should the font be allocated if it is still undefined?
   * @return the font for the given name, or {@code null} if none was
   *         allocated for the name and {@code allocateIfUndefined} is
   *         {@code false}
   */
  public abstract IFontStyle getFont(final String name,
      final boolean allocateIfUndefined);
}
