package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import org.optimizationBenchmarking.utils.IScope;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * <p>
 * An abstract {@link java.awt.Graphics2D} implementation designed as
 * output context for graphics. Each graphic is set up so that 1 logical
 * unit equals {@code 1pt}.
 * </p>
 * <p>
 * Inspired by the
 * <a href="http://java.freehep.org/vectorgraphics">FreeHEP</a> library,
 * this class also provides {@code double}-precision routines which, by
 * default, try to reasonable map to the {@code int}-precision routines of
 * {@link java.awt.Graphics2D}. However, if an underlying device supports
 * high-precision output, these routines may map to something better.
 * </p>
 */
public abstract class Graphic extends Graphics2D
    implements IScope, IToolJob {

  /** instantiate */
  protected Graphic() {
    super();
  }

  /**
   * Obtain the bounds of this graphic in {@code pt}
   *
   * @return the bounds of this graphic
   */
  public Rectangle2D getBounds() {
    Rectangle2D r;
    Point2D.Double a, b;
    AffineTransform at;

    r = this.getClipBounds();
    if (r != null) {
      return r;
    }

    at = this.getTransform();
    try {
      at.invert();
    } catch (final Throwable t) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(((((//
      "Error while inverting transform ") + at)//$NON-NLS-1$
          + " in graphic ") + this.toString()), //$NON-NLS-1$
          true, t);
    }

    r = this.getDeviceConfiguration().getBounds();
    a = new Point2D.Double(r.getMinX(), r.getMinY());

    b = new Point2D.Double();
    at.transform(a, b);

    a.x += r.getWidth();
    a.y += r.getHeight();
    at.transform(a, b);
    r.setFrameFromDiagonal(b, a);

    return r;
  }

  /**
   * The {@link #dispose()} method forwards the call to the idempotent
   * {@link #close()} and has the same semantics.
   */
  @Override
  public final void dispose() {
    this.close();
  }

  /** {@inheritDoc} */
  @Override
  public abstract void close();

  /**
   * Draw a 3-d rectangle
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param raised
   *          is it raised?
   */
  public abstract void draw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised);

  /**
   * Fill a 3-d rectangle
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param raised
   *          is it raised?
   */
  public abstract void fill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised);

  /**
   * Draw a buffered image
   *
   * @param img
   *          the image
   * @param op
   *          the operation
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public abstract void drawImage(final BufferedImage img,
      final BufferedImageOp op, final double x, final double y);

  /**
   * Draw a string
   *
   * @param str
   *          the string
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public abstract void drawString(final String str, final double x,
      final double y);

  /**
   * Draw a string
   *
   * @param iterator
   *          the string
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public abstract void drawString(
      final AttributedCharacterIterator iterator, final double x,
      final double y);

  /**
   * Draw a glyph vector
   *
   * @param g
   *          the glyph vector
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public abstract void drawGlyphVector(final GlyphVector g, final double x,
      final double y);

  /**
   * create a graphics object
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @return the new graphics object
   */
  public abstract Graphics create(final double x, final double y,
      final double width, final double height);

  /**
   * clip the given rectangle
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void clipRect(final double x, final double y,
      final double width, final double height);

  /**
   * set the clip to the given rectangle
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void setClip(final double x, final double y,
      final double width, final double height);

  /**
   * Copy some area
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param dx
   *          the movement x
   * @param dy
   *          the movement y
   */
  public abstract void copyArea(final double x, final double y,
      final double width, final double height, final double dx,
      final double dy);

  /**
   * draw a line
   *
   * @param x1
   *          the start x-coordinate
   * @param y1
   *          the start y-coordinate
   * @param x2
   *          the end x-coordinate
   * @param y2
   *          the end y-coordinate
   */
  public abstract void drawLine(final double x1, final double y1,
      final double x2, final double y2);

  /**
   * fill a rectangle
   *
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void fillRect(final double x, final double y,
      final double width, final double height);

  /**
   * draw a rectangle
   *
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void drawRect(final double x, final double y,
      final double width, final double height);

  /**
   * clear a rectangle
   *
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void clearRect(final double x, final double y,
      final double width, final double height);

  /**
   * draw a round rectangle
   *
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param arcWidth
   *          the arc width
   * @param arcHeight
   *          the arc height
   */
  public abstract void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight);

  /**
   * fill a round rectangle
   *
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param arcWidth
   *          the arc width
   * @param arcHeight
   *          the arc height
   */
  public abstract void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight);

  /**
   * Draw an oval
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void drawOval(final double x, final double y,
      final double width, final double height);

  /**
   * Fill an oval
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public abstract void fillOval(final double x, final double y,
      final double width, final double height);

  /**
   * Draw an arc
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param startAngle
   *          the starting angle
   * @param arcAngle
   *          the angle
   */
  public abstract void drawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle);

  /**
   * Fill an arc
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param startAngle
   *          the starting angle
   * @param arcAngle
   *          the angle
   */
  public abstract void fillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle);

  /**
   * Draw a poly-line
   *
   * @param xPoints
   *          the x-points
   * @param yPoints
   *          the y-points
   * @param nPoints
   *          the number of points
   */
  public abstract void drawPolyline(final double[] xPoints,
      final double[] yPoints, final int nPoints);

  /**
   * Draw a polygon
   *
   * @param xPoints
   *          the x-points
   * @param yPoints
   *          the y-points
   * @param nPoints
   *          the number of points
   */
  public abstract void drawPolygon(final double xPoints[],
      final double yPoints[], final int nPoints);

  /**
   * Draw a polygon
   *
   * @param xPoints
   *          the x-points
   * @param yPoints
   *          the y-points
   * @param nPoints
   *          the number of points
   */
  public abstract void fillPolygon(final double xPoints[],
      final double yPoints[], final int nPoints);

  /**
   * draw the given characters
   *
   * @param data
   *          the character data
   * @param offset
   *          the offset
   * @param length
   *          the length
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public abstract void drawChars(final char[] data, final int offset,
      final int length, final double x, final double y);

  /**
   * draw an image
   *
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param observer
   *          the observer
   * @return the result
   */
  public abstract boolean drawImage(final Image img, final double x,
      final double y, final ImageObserver observer);

  /**
   * draw an image
   *
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param observer
   *          the observer
   * @return the result
   */
  public abstract boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer);

  /**
   * draw an image
   *
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param observer
   *          the observer
   * @param bgcolor
   *          the background color
   * @return the result
   */
  public abstract boolean drawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer);

  /**
   * draw an image
   *
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param bgcolor
   *          the background color
   * @param observer
   *          the observer
   * @return the result
   */
  public abstract boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer);

  /**
   * Draw an image
   *
   * @param img
   *          the specified image to be drawn. This method does nothing if
   *          {@code img} is null.
   * @param dx1
   *          the x coordinate of the first corner of the destination
   *          rectangle.
   * @param dy1
   *          the y coordinate of the first corner of the destination
   *          rectangle.
   * @param dx2
   *          the x coordinate of the second corner of the destination
   *          rectangle.
   * @param dy2
   *          the y coordinate of the second corner of the destination
   *          rectangle.
   * @param sx1
   *          the x coordinate of the first corner of the source rectangle.
   * @param sy1
   *          the y coordinate of the first corner of the source rectangle.
   * @param sx2
   *          the x coordinate of the second corner of the source
   *          rectangle.
   * @param sy2
   *          the y coordinate of the second corner of the source
   *          rectangle.
   * @param observer
   *          object to be notified as more of the image is scaled and
   *          converted.
   * @return {@code false} if the image pixels are still changing;
   *         {@code true} otherwise.
   */
  public abstract boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer);

  /**
   * Draw an image
   *
   * @param img
   *          the specified image to be drawn. This method does nothing if
   *          {@code img} is null.
   * @param dx1
   *          the x coordinate of the first corner of the destination
   *          rectangle.
   * @param dy1
   *          the y coordinate of the first corner of the destination
   *          rectangle.
   * @param dx2
   *          the x coordinate of the second corner of the destination
   *          rectangle.
   * @param dy2
   *          the y coordinate of the second corner of the destination
   *          rectangle.
   * @param sx1
   *          the x coordinate of the first corner of the source rectangle.
   * @param sy1
   *          the y coordinate of the first corner of the source rectangle.
   * @param sx2
   *          the x coordinate of the second corner of the source
   *          rectangle.
   * @param sy2
   *          the y coordinate of the second corner of the source
   *          rectangle.
   * @param observer
   *          object to be notified as more of the image is scaled and
   *          converted.
   * @param bgcolor
   *          the background color
   * @return {@code false} if the image pixels are still changing;
   *         {@code true} otherwise.
   */
  public abstract boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer);

  /**
   * Create a shape composed of a set of points
   *
   * @param xPoints
   *          the x points
   * @param yPoints
   *          the y points
   * @param nPoints
   *          the number of points
   * @param close
   *          should we close the shape?
   * @return the shape
   */
  public abstract Shape createShape(final double[] xPoints,
      final double[] yPoints, final int nPoints, final boolean close);
}
