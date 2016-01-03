package org.optimizationBenchmarking.utils.graphics;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/** a utility class for graphics stuff */
public final class GraphicUtils {

  /** the default rendering hints */
  private static final RenderingHintHolder[] DEFAULT_RENDERING_HINTS = //
  GraphicUtils.__createDefaultRenderingHints();

  /**
   * create the default rendering hints.
   *
   * @return the default rendering hints
   */
  private static final RenderingHintHolder[] __createDefaultRenderingHints() {
    final RenderingHintHolder[] def;

    def = new RenderingHintHolder[10];

    def[0] = new RenderingHintHolder(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);

    def[1] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    def[2] = new RenderingHintHolder(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    def[3] = new RenderingHintHolder(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);

    def[4] = new RenderingHintHolder(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);

    def[5] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    def[6] = new RenderingHintHolder(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    def[7] = new RenderingHintHolder(RenderingHints.KEY_DITHERING,
        RenderingHints.VALUE_DITHER_ENABLE);

    def[8] = new RenderingHintHolder(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    def[9] = new RenderingHintHolder(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

    return def;
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

  /**
   * Get the bounds of a graphics context
   *
   * @param graphics
   *          the context
   * @return the bounds
   */
  public static final Rectangle2D getBounds(final Graphics graphics) {
    return GraphicUtils.getBounds(graphics, true);
  }

  /**
   * Get the bounds of a graphics context
   *
   * @param graphics
   *          the context
   * @param checkIsGraphic
   *          {@code true} if we should check whether the object implements
   *          {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic}
   *          and hence provides
   *          {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#getBounds()}
   *          , {@code false} if only the basic {@code java.awt}
   *          functionality should be used.
   * @return the bounds
   */
  public static final Rectangle2D getBounds(final Graphics graphics,
      final boolean checkIsGraphic) {
    final int size, offset;
    final Graphics2D graphics2D;
    Point2D.Double topLeftCorner, bottomRightCorner, temp;
    AffineTransform transform;
    Rectangle clipRectangle;

    if (checkIsGraphic && (graphics instanceof Graphic)) {
      return ((Graphic) graphics).getBounds();
    }

    if (graphics instanceof Graphics2D) {
      graphics2D = ((Graphics2D) graphics);
      clipRectangle = graphics2D.getClipBounds();

      if (clipRectangle != null) {
        return clipRectangle;
      }

      clipRectangle = graphics2D.getDeviceConfiguration().getBounds();
      if (clipRectangle != null) {

        transform = graphics2D.getTransform();
        if ((transform == null) || (transform.isIdentity())) {
          return clipRectangle;
        }

        try {
          transform.invert();
        } catch (final RuntimeException runtimeException) {
          throw runtimeException;
        } catch (final Throwable error) {
          throw new RuntimeException((((//
          "Error while inverting transform ") + transform)//$NON-NLS-1$
              + '.'), error);
        }

        temp = new Point2D.Double(clipRectangle.getMinX(),
            clipRectangle.getMinY());

        topLeftCorner = new Point2D.Double();
        transform.transform(temp, topLeftCorner);

        temp.x += clipRectangle.getWidth();
        temp.y += clipRectangle.getHeight();
        bottomRightCorner = new Point2D.Double();
        transform.transform(temp, bottomRightCorner);
        clipRectangle.setFrameFromDiagonal(topLeftCorner,
            bottomRightCorner);
        return clipRectangle;
      }
    }

    size = ((Integer.MAX_VALUE - 4) | 1);
    offset = (-(size >>> 1));
    return new Rectangle(offset, offset, size, size);
  }

  /** the forbidden constructor */
  private GraphicUtils() {
    ErrorUtils.doNotCall();
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
