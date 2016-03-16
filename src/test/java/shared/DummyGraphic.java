package shared;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/**
 * This is an extremely hard-core dummy graphic: all methods do nothing,
 * getters return the same value.
 */
public class DummyGraphic extends Graphic {

  /** create */
  public DummyGraphic() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public void close() {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void draw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawImage(final BufferedImage img, final BufferedImageOp op,
      final double x, final double y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final String str, final double x,
      final double y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final AttributedCharacterIterator iterator,
      final double x, final double y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawGlyphVector(final GlyphVector g, final double x,
      final double y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public Graphics create(final double x, final double y,
      final double width, final double height) {
    // ignore
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void clipRect(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setClip(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void copyArea(final double x, final double y, final double width,
      final double height, final double dx, final double dy) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawLine(final double x1, final double y1, final double x2,
      final double y2) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillRect(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawRect(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void clearRect(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawOval(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillOval(final double x, final double y, final double width,
      final double height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawArc(final double x, final double y, final double width,
      final double height, final double startAngle,
      final double arcAngle) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillArc(final double x, final double y, final double width,
      final double height, final double startAngle,
      final double arcAngle) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawPolyline(final double[] xPoints, final double[] yPoints,
      final int nPoints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawPolygon(final double[] xPoints, final double[] yPoints,
      final int nPoints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillPolygon(final double[] xPoints, final double[] yPoints,
      final int nPoints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawChars(final char[] data, final int offset,
      final int length, final double x, final double y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x, final double y,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x, final double y,
      final double width, final double height,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x, final double y,
      final Color bgcolor, final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x, final double y,
      final double width, final double height, final Color bgcolor,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public Shape createShape(final double[] xPoints, final double[] yPoints,
      final int nPoints, final boolean close) {
    return this.getBounds();
  }

  /** {@inheritDoc} */
  @Override
  public void draw(final Shape s) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final AffineTransform xform,
      final ImageObserver obs) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public void drawImage(final BufferedImage img, final BufferedImageOp op,
      final int x, final int y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final String str, final int x, final int y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final String str, final float x, final float y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final AttributedCharacterIterator iterator,
      final int x, final int y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final AttributedCharacterIterator iterator,
      final float x, final float y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawGlyphVector(final GlyphVector g, final float x,
      final float y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fill(final Shape s) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public boolean hit(final Rectangle rect, final Shape s,
      final boolean onStroke) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public GraphicsConfiguration getDeviceConfiguration() {
    return new __InternalGraphicsConfiguration();
  }

  /** {@inheritDoc} */
  @Override
  public void setComposite(final Composite comp) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setPaint(final Paint paint) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setStroke(final Stroke s) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setRenderingHint(final Key hintKey, final Object hintValue) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public Object getRenderingHint(final Key hintKey) {
    // ignore
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void setRenderingHints(final Map<?, ?> hints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void addRenderingHints(final Map<?, ?> hints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public RenderingHints getRenderingHints() {
    return new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
  }

  /** {@inheritDoc} */
  @Override
  public void translate(final int x, final int y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void translate(final double tx, final double ty) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void rotate(final double theta) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void rotate(final double theta, final double x, final double y) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void scale(final double sx, final double sy) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void shear(final double shx, final double shy) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void transform(final AffineTransform Tx) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setTransform(final AffineTransform Tx) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public AffineTransform getTransform() {
    return this.getNormalizingTransform();
  }

  /** {@inheritDoc} */
  @Override
  public Paint getPaint() {
    return this.getColor();
  }

  /** {@inheritDoc} */
  @Override
  public Composite getComposite() {
    return AlphaComposite.Clear;
  }

  /** {@inheritDoc} */
  @Override
  public void setBackground(final Color color) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public Color getBackground() {
    return this.getColor();
  }

  /** {@inheritDoc} */
  @Override
  public Stroke getStroke() {
    return new BasicStroke();
  }

  /** {@inheritDoc} */
  @Override
  public void clip(final Shape s) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public FontRenderContext getFontRenderContext() {
    return new FontRenderContext(null, true, true);
  }

  /** {@inheritDoc} */
  @Override
  public Graphics create() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Color getColor() {
    return Color.black;
  }

  /** {@inheritDoc} */
  @Override
  public void setColor(final Color c) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setPaintMode() {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setXORMode(final Color c1) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public Font getFont() {
    return Font.getFont(Font.DIALOG);
  }

  /** {@inheritDoc} */
  @Override
  public void setFont(final Font font) {
    // ignore
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public FontMetrics getFontMetrics(final Font f) {
    return Toolkit.getDefaultToolkit().getFontMetrics(f);
  }

  /** {@inheritDoc} */
  @Override
  public Rectangle getClipBounds() {
    return this.getBounds();
  }

  /** {@inheritDoc} */
  @Override
  public void clipRect(final int x, final int y, final int width,
      final int height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setClip(final int x, final int y, final int width,
      final int height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public Shape getClip() {
    return this.getBounds();
  }

  /** {@inheritDoc} */
  @Override
  public void setClip(final Shape clip) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void copyArea(final int x, final int y, final int width,
      final int height, final int dx, final int dy) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawLine(final int x1, final int y1, final int x2,
      final int y2) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillRect(final int x, final int y, final int width,
      final int height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void clearRect(final int x, final int y, final int width,
      final int height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawRoundRect(final int x, final int y, final int width,
      final int height, final int arcWidth, final int arcHeight) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillRoundRect(final int x, final int y, final int width,
      final int height, final int arcWidth, final int arcHeight) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawOval(final int x, final int y, final int width,
      final int height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillOval(final int x, final int y, final int width,
      final int height) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawPolyline(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void drawPolygon(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void fillPolygon(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final int width, final int height, final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final Color bgcolor, final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int dx1, final int dy1,
      final int dx2, final int dy2, final int sx1, final int sy1,
      final int sx2, final int sy2, final ImageObserver observer) {
    // ignore
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int dx1, final int dy1,
      final int dx2, final int dy2, final int sx1, final int sy1,
      final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    // ignore
    return true;
  }

  /**
   * Get the normalizing transformation
   *
   * @return the normalizing transformation
   */
  protected AffineTransform getNormalizingTransform() {
    return new AffineTransform();
  }

  /** {@inheritDoc} */
  @Override
  public Rectangle getBounds() {
    return new Rectangle(0, 0, 1000, 1000);
  }

  /** the internal configuration */
  private final class __InternalGraphicsConfiguration
      extends GraphicsConfiguration {

    /** the device */
    private __InternalGraphicsDevice m_d;

    /** create */
    __InternalGraphicsConfiguration() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final GraphicsDevice getDevice() {
      if (this.m_d == null) {
        this.m_d = new __InternalGraphicsDevice();
      }
      return this.m_d;
    }

    /** {@inheritDoc} */
    @Override
    public final ColorModel getColorModel() {
      return ColorModel.getRGBdefault();
    }

    /** {@inheritDoc} */
    @Override
    public final ColorModel getColorModel(final int transparency) {
      return ColorModel.getRGBdefault();
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getDefaultTransform() {
      return this.getNormalizingTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getNormalizingTransform() {
      return DummyGraphic.this.getNormalizingTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final Rectangle getBounds() {
      return DummyGraphic.this.getBounds();
    }

    /** the internal device */
    private final class __InternalGraphicsDevice extends GraphicsDevice {

      /** create */
      __InternalGraphicsDevice() {
        super();
      }

      /** {@inheritDoc} */
      @Override
      public final int getType() {
        return GraphicsDevice.TYPE_IMAGE_BUFFER;
      }

      /** {@inheritDoc} */
      @Override
      public final String getIDstring() {
        return String.valueOf(this.hashCode());
      }

      /** {@inheritDoc} */
      @Override
      public final GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] {
            __InternalGraphicsConfiguration.this };
      }

      /** {@inheritDoc} */
      @Override
      public final GraphicsConfiguration getDefaultConfiguration() {
        return __InternalGraphicsConfiguration.this;
      }
    }
  }
}
