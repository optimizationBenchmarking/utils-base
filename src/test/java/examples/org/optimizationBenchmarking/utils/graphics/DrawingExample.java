package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;

import javax.swing.JList;
import javax.swing.border.BevelBorder;

import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.units.ELength;

/**
 * An example used to illustrate drawing on a graphic.
 */
public class DrawingExample extends GraphicExample {

  /**
   * Create the drawing example
   *
   * @param listener
   *          the file producer listener
   * @param destDir
   *          the destination dir
   * @param driver
   *          the graphic driver to use
   * @param size
   *          the physical size dimension
   * @param model
   *          the color model, {@code null} for default
   * @param dpi
   *          the dpi, {@code -1} for default
   * @param quality
   *          the quality, {@code -1} for default
   */
  public DrawingExample(final IFileProducerListener listener,
      final Path destDir, final IGraphicDriver driver,
      final PhysicalDimension size, final EColorModel model, final int dpi,
      final double quality) {
    super(listener, destDir, driver, size, model, dpi, quality);
  }

  /** {@inheritDoc} */
  @Override
  protected PhysicalDimension getDefaultSize() {
    return new PhysicalDimension(100d, 61.803398874989484820458683436564d,
        ELength.MM);
  }

  /**
   * paint a small component
   *
   * @param graphic
   *          the graphic
   */
  private static final void __paintComponent(final Graphic graphic) {
    JList<String> jlist;
    UnaryFunction conversionFunction;
    Rectangle2D bounds;

    jlist = new JList<>(new String[] { "item 1", //$NON-NLS-1$
        "item 2", //$NON-NLS-1$
        "item 3", //$NON-NLS-1$
        "item 4" });//$NON-NLS-1$

    jlist.setSelectedIndices(new int[] { 0, 2 });
    jlist.setBorder(new BevelBorder(BevelBorder.LOWERED));

    conversionFunction = ELength.MM.getConversionFunction(ELength.PT);

    bounds = graphic.getBounds();

    graphic.translate(
        (bounds.getX() + conversionFunction.computeAsDouble(40d)), //
        (bounds.getY() + conversionFunction.computeAsDouble(25d)));
    jlist.setBounds(0, 0, conversionFunction.computeAsInt(50),
        conversionFunction.computeAsInt(30));

    jlist.paint(graphic);
  }

  /**
   * paint the graphic
   *
   * @param graphic
   *          the graphic
   */
  private static final void __paint(final Graphic graphic) {
    final Rectangle2D bounds;
    int i;
    Rectangle2D rectangle;
    Stroke stroke;

    bounds = graphic.getBounds();
    graphic.setColor(Color.red);
    graphic.fill(bounds);
    graphic.setColor(Color.CYAN);
    graphic.draw(bounds);

    rectangle = new Rectangle2D.Double();

    for (i = 0; i < 10; i++) {
      rectangle.setRect(bounds.getX() + ((bounds.getWidth() / 10d) * i), //
          bounds.getY(), //
          (bounds.getWidth() / 10d), //
          ((bounds.getWidth() / 10d)));
      graphic.setColor(new Color(0f, ((i + 1) / 12f), 0f));
      graphic.fill(rectangle);

      rectangle.setRect(bounds.getX(), //
          bounds.getY() + ((bounds.getHeight() / 10d) * i), //
          (bounds.getHeight() / 10d), //
          ((bounds.getHeight() / 10d)));
      graphic.setColor(new Color(0f, 0f, (((9 - i) + 1) / 12f)));
      graphic.fill(rectangle);
    }

    for (i = 0; i < 10; i++) {
      rectangle.setRect(bounds.getX() + ((bounds.getWidth() / 10d) * i), //
          bounds.getY(), //
          (bounds.getWidth() / 10d), //
          ((bounds.getWidth() / 10d)));
      graphic
          .setColor(new Color(((i + 1) / 12f), (((9 - i) + 1) / 12f), 0f));
      graphic.draw(rectangle);

      rectangle.setRect(bounds.getX(), //
          bounds.getY() + ((bounds.getHeight() / 10d) * i), //
          (bounds.getHeight() / 10d), //
          ((bounds.getHeight() / 10d)));
      graphic
          .setColor(new Color((((9 - i) + 1) / 12f), 0f, ((i + 1) / 12f)));
      graphic.draw(rectangle);
    }

    graphic.setColor(Color.white);
    graphic.drawLine(bounds.getX(), bounds.getY(), //
        bounds.getMaxX(), bounds.getMaxY());

    graphic.setColor(Color.black);
    stroke = graphic.getStroke();
    if (stroke instanceof BasicStroke) {
      graphic.setStroke(
          new BasicStroke(((BasicStroke) stroke).getLineWidth() * 0.5f));
    }
    graphic.drawLine(bounds.getX(), bounds.getY(), //
        bounds.getX() + (bounds.getWidth()), //
        bounds.getY() + (bounds.getHeight()));
    graphic.setStroke(stroke);

    graphic.setFont(new Font("Courier New", //$NON-NLS-1$
        Font.PLAIN, ((int) (0.5d + ELength.MM
            .convertToAsDouble((bounds.getHeight() / 10d), ELength.MM)))));
    graphic.setColor(Color.YELLOW);
    graphic.drawString("PlainText with 1/10th of a line hight", //$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))), //
        (0.5d + (bounds.getY() + (((3d * bounds.getHeight()) / 10d)))));

    graphic.setFont(new Font("Arial", //$NON-NLS-1$
        Font.PLAIN, 18));
    graphic.setColor(Color.YELLOW);
    graphic.drawString("Font: 18pt", //$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))), //
        (0.5d + (bounds.getY() + (((5d * bounds.getHeight()) / 10d)))));

    graphic.setFont(new Font("Times New Roman", //$NON-NLS-1$
        Font.PLAIN, 16));
    graphic.setColor(Color.YELLOW);
    graphic.drawString("Font: 16pt", //$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))), //
        (0.5d + (bounds.getY() + (((7d * bounds.getHeight()) / 10d)))));

    graphic.setFont(new Font("Dialog", //$NON-NLS-1$
        Font.PLAIN, 14));
    graphic.setColor(Color.YELLOW);
    graphic.drawString("Font: 14pt", //$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))), //
        (0.5d + (bounds.getY() + (((9d * bounds.getHeight()) / 10d)))));
  }

  /** {@inheritDoc} */
  @Override
  protected final void paint(final Graphic graphic) {
    int i;

    for (i = 1; i < 50; i++) {
      DrawingExample.__paint(graphic);
      DrawingExample.__paintComponent(graphic);
      graphic.translate(-75, -25);
      graphic.scale(0.55d, 0.5d);
      graphic.shear(0.1d, 0.5d);
      graphic.rotate(1d / 3d);
      graphic.clip(graphic.getBounds());
    }
  }
}
