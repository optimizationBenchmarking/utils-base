package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.IScope;
import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorStyle;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.units.ELength;

/**
 * An example used to illustrate the available color palettes.
 */
public class ColorPaletteExample extends GraphicExample {

  /** the color palette */
  private final IColorPalette m_palette;

  /**
   * Create the color palette example
   *
   * @param listener
   *          the file producer listener
   * @param destDir
   *          the destination dir
   * @param driver
   *          the graphic driver to use
   * @param palette
   *          the color palette
   * @param size
   *          the physical size dimension
   * @param model
   *          the color model, {@code null} for default
   * @param dpi
   *          the dpi, {@code -1} for default
   * @param quality
   *          the quality, {@code -1} for default
   */
  public ColorPaletteExample(final IFileProducerListener listener,
      final Path destDir, final IGraphicDriver driver,
      final PhysicalDimension size, final EColorModel model, final int dpi,
      final double quality, final IColorPalette palette) {
    super(listener, destDir, driver, size, model, dpi, quality);
    if (palette == null) {
      throw new IllegalArgumentException("Color palette cannot be null."); //$NON-NLS-1$
    }
    this.m_palette = palette;
  }

  /** {@inheritDoc} */
  @Override
  protected String getMainDocumentNameSuggestionBase() {
    String name, palette;

    name = super.getMainDocumentNameSuggestionBase();
    palette = this.m_palette.getClass().getSimpleName();
    if ("ColorPalette".equalsIgnoreCase(palette)) { //$NON-NLS-1$
      return name;
    }
    return name + '_' + palette;
  }

  /** {@inheritDoc} */
  @Override
  protected PhysicalDimension getDefaultSize() {
    return new PhysicalDimension(160, 160, ELength.MM);
  }

  /** {@inheritDoc} */
  @Override
  protected final void paint(final Graphic graphic) {
    final int size, squareWidth;
    final Rectangle2D b;
    int count, i, j, k;
    final AffineTransform transform;
    IColorStyle style;
    Color use, color;

    this.m_palette.initializeWithDefaults(graphic);

    b = graphic.getBounds();
    graphic.setFont(new Font("Arial", Font.PLAIN, //$NON-NLS-1$
        ((int) (b.getHeight() * 0.25d))));

    size = this.m_palette.size();
    squareWidth = ((int) (0.5d + Math.ceil(Math.sqrt(size))));
    count = 0;
    transform = graphic.getTransform();
    for (i = 0; i < squareWidth; i++) {
      for (j = 0; j < squareWidth; j++) {

        style = this.m_palette.get(count);
        try (IScope x = style.applyTo(graphic)) {

          graphic.translate(((j * b.getWidth()) / squareWidth), //
              ((i * b.getHeight()) / squareWidth));
          graphic.scale((1d / squareWidth), (1d / squareWidth));

          graphic.fillRect(0, 0, b.getWidth(), b.getHeight());

          color = style.getColor();
          if (Math.max(color.getRed(),
              Math.max(color.getBlue(), color.getGreen())) < 120) {
            use = Color.WHITE;
          } else {
            use = Color.BLACK;
          }

          graphic.setColor(use);
          k = 1;
          for (final String ss : style.toString().split(" ")) { //$NON-NLS-1$
            graphic.drawString(ss, 1d, b.getHeight() * 0.2d * (k++));
          }

          if ((++count) >= size) {
            return;
          }

          graphic.setTransform(transform);
        }
      }
    }
  }
}
