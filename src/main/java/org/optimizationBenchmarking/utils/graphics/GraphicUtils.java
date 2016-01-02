package org.optimizationBenchmarking.utils.graphics;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.font.TextAttribute;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** a utility class for graphics stuff */
public final class GraphicUtils {

  /** the default rendering hints */
  private static final RenderingHintHolder[] DEFAULT_RENDERING_HINTS;

  static {
    DEFAULT_RENDERING_HINTS = new RenderingHintHolder[10];

    GraphicUtils.DEFAULT_RENDERING_HINTS[0] = new RenderingHintHolder(
        RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);

    GraphicUtils.DEFAULT_RENDERING_HINTS[1] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[2] = new RenderingHintHolder(
        RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[3] = new RenderingHintHolder(
        RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[4] = new RenderingHintHolder(
        RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);

    GraphicUtils.DEFAULT_RENDERING_HINTS[5] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[6] = new RenderingHintHolder(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    GraphicUtils.DEFAULT_RENDERING_HINTS[7] = new RenderingHintHolder(
        RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

    GraphicUtils.DEFAULT_RENDERING_HINTS[8] = new RenderingHintHolder(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    GraphicUtils.DEFAULT_RENDERING_HINTS[9] = new RenderingHintHolder(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

  }

  /** the forbidden constructor */
  private GraphicUtils() {
    ErrorUtils.doNotCall();
  }

  /**
   * Set the default rendering hints
   *
   * @param graphics
   *          the graphic to initialize
   */
  public static final void applyDefaultRenderingHints(
      final Graphics2D graphics) {
    for (final RenderingHintHolder hint : GraphicUtils.DEFAULT_RENDERING_HINTS) {
      graphics.setRenderingHint(hint.m_key, hint.m_value);
    }
  }

  /**
   * Create the default rendering hints
   *
   * @return a default set of rendering hints
   */
  public static final RenderingHints createDefaultRenderingHints() {
    RenderingHintHolder hint;
    RenderingHints h;
    int i;

    i = GraphicUtils.DEFAULT_RENDERING_HINTS.length;
    hint = GraphicUtils.DEFAULT_RENDERING_HINTS[--i];
    h = new RenderingHints(hint.m_key, hint.m_value);
    for (; (--i) >= 0;) {
      hint = GraphicUtils.DEFAULT_RENDERING_HINTS[i];
      h.put(hint.m_key, hint.m_value);
    }

    return h;
  }

  /**
   * Obtain the default color model for graphics
   *
   * @return the default color model for graphics
   */
  public static final EColorModel getDefaultColorModel() {
    return EColorModel.RGB_24_BIT;
  }

  /**
   * Get the default value for dots per inch for (raster) graphics. See
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setDotsPerInch(int)}
   * .
   *
   * @return the default value for dots per inch for (raster) graphics
   */
  public static final int getDefaultDPI() {
    return 300;
  }

  /**
   * Get the default image encoding quality for (lossy raster) graphics.
   * See
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setQuality(double)}
   * .
   *
   * @return the default image encoding quality for (lossy raster) graphics
   */
  public static final double getDefaultQuality() {
    return 0.8d;
  }

  /**
   * Get the default width for strokes
   *
   * @return the default width for strokes
   * @see java.awt.BasicStroke
   */
  public static final float getDefaultStrokeWidth() {
    return 1f;
  }

  /**
   * Get the default width for thick strokes
   *
   * @return the default width for thick strokes
   * @see java.awt.BasicStroke
   */
  public static final float getDefaultThickStrokeWidth() {
    return 2.5f;
  }

  /**
   * Get the default width for thin strokes
   *
   * @return the default width for thin strokes
   * @see java.awt.BasicStroke
   */
  public static final float getDefaultThinStrokeWidth() {
    return 0f;
  }

  /**
   * Get the default miter limit for a given stroke width
   *
   * @param strokeWidth
   *          the stroke width
   * @return the corresponding miter limit
   * @see java.awt.BasicStroke
   */
  public static final float getDefaultStrokeMiterLimit(
      final float strokeWidth) {
    return Math.max(1f, (3f * strokeWidth));
  }

  /**
   * Get the default stroke end decoration
   *
   * @return the stroke end cap
   * @see java.awt.BasicStroke
   */
  public static final int getDefaultStrokeEndCap() {
    return BasicStroke.CAP_ROUND;
  }

  /**
   * Get the default way to join strokes
   *
   * @return the default stroke join
   * @see java.awt.BasicStroke
   */
  public static final int getDefaultStrokeJoin() {
    return BasicStroke.JOIN_ROUND;
  }

  /**
   * Check whether a font is underlined or not. This method bases its
   * result solely on the properties of the font, it does not perform a
   * resource-based lookup.
   *
   * @param font
   *          the font
   * @return {@code true} if the font is underlined, {@code false}
   *         otherwise
   */
  public static final boolean isFontUnderlined(final Font font) {
    final Number underlineAtt;

    underlineAtt = ((Number) (font.getAttributes()
        .get(TextAttribute.UNDERLINE)));
    return ((underlineAtt != null) && (underlineAtt.intValue() >= 0));
  }

  /** the internal rendering hint holder */
  private static final class RenderingHintHolder {
    /** the key */
    final Key m_key;
    /** the value */
    final Object m_value;

    /**
     * create the hint holder
     *
     * @param key
     *          the key
     * @param value
     *          the value
     */
    RenderingHintHolder(final Key key, final Object value) {
      super();
      this.m_key = key;
      this.m_value = value;
    }
  }
}
