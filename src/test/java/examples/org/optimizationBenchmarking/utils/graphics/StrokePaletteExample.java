package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.IScope;
import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokeStyle;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.units.ELength;

/**
 * An example used to illustrate the available stroke palettes.
 */
public class StrokePaletteExample extends GraphicExample {

  /** the color palette */
  private final IStrokePalette m_palette;

  /**
   * Create the stroke palette example
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
  public StrokePaletteExample(final IFileProducerListener listener,
      final Path destDir, final IGraphicDriver driver,
      final PhysicalDimension size, final EColorModel model, final int dpi,
      final double quality, final IStrokePalette palette) {
    super(listener, destDir, driver, size, model, dpi, quality);
    if (palette == null) {
      throw new IllegalArgumentException("Stroke palette cannot be null."); //$NON-NLS-1$
    }
    this.m_palette = palette;
  }

  /** {@inheritDoc} */
  @Override
  protected String getMainDocumentNameSuggestionBase() {
    String name, palette;

    name = super.getMainDocumentNameSuggestionBase();
    palette = this.m_palette.getClass().getSimpleName();
    if ("StrokePalette".equalsIgnoreCase(palette)) { //$NON-NLS-1$
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
    final Rectangle2D bounds;
    final int size;
    final double height, width;
    final ArrayList<IStrokeStyle> styles;
    int i;
    IStrokeStyle style;

    styles = new ArrayList<>(this.m_palette.size() + 3);
    styles.add(this.m_palette.getDefaultStroke());
    styles.add(this.m_palette.getThinStroke());
    styles.add(this.m_palette.getThickStroke());
    styles.addAll(this.m_palette);

    size = styles.size();

    bounds = graphic.getBounds();
    graphic.setColor(Color.white);
    graphic.setBackground(Color.white);
    graphic.fill(bounds);
    graphic.setColor(Color.black);

    height = (bounds.getHeight() / size);
    width = bounds.getWidth();
    graphic.setFont(new Font("Arial", Font.PLAIN, //$NON-NLS-1$
        ((int) (0.5d * height))));

    for (i = 0; i < size; i++) {
      style = styles.get(i);
      try (final IScope x = style.applyTo(graphic)) {
        graphic.drawLine(0.05 * width, (i + 0.5) * height, 0.45 * width,
            (i + 0.5) * height);
        graphic.drawString(style.toString(), 0.55 * width,
            (i + 0.7) * height);
      }
    }
  }
}
