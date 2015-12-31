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
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontPalette;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontStyle;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.units.ELength;

/**
 * An example used to illustrate the available font palettes.
 */
public class FontPaletteExample extends GraphicExample {

  /** the font palette */
  private final IFontPalette m_palette;

  /**
   * Create the font palette example
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
  public FontPaletteExample(final IFileProducerListener listener,
      final Path destDir, final IGraphicDriver driver,
      final PhysicalDimension size, final EColorModel model, final int dpi,
      final double quality, final IFontPalette palette) {
    super(listener, destDir, driver, size, model, dpi, quality);
    if (palette == null) {
      throw new IllegalArgumentException("Font palette cannot be null."); //$NON-NLS-1$
    }
    this.m_palette = palette;
  }

  /** {@inheritDoc} */
  @Override
  protected String getMainDocumentNameSuggestionBase() {
    String name, palette;

    name = super.getMainDocumentNameSuggestionBase();
    palette = this.m_palette.getClass().getSimpleName();
    if ("FontPalette".equalsIgnoreCase(palette)) { //$NON-NLS-1$
      return name;
    }
    return name + '_' + palette;
  }

  /** {@inheritDoc} */
  @Override
  protected PhysicalDimension getDefaultSize() {
    return new PhysicalDimension(320, 160, ELength.MM);
  }

  /** {@inheritDoc} */
  @Override
  protected final void paint(final Graphic graphic) {
    final Rectangle2D bounds;
    final int size;
    final double width;
    final ArrayList<IFontStyle> styles;
    final MemoryTextOutput textBuffer;
    double currentLineY, lineHeight;
    int index;
    Font font;
    String string;
    IFontStyle style;
    boolean q;

    styles = new ArrayList<>(this.m_palette.size() + 1);
    styles.add(this.m_palette.getDefaultFont());
    styles.add(this.m_palette.getCodeFont());
    styles.add(this.m_palette.getEmphFont());
    styles.addAll(this.m_palette);

    textBuffer = new MemoryTextOutput();

    size = styles.size();

    bounds = graphic.getBounds();
    graphic.setColor(Color.white);
    graphic.setBackground(Color.white);
    graphic.fill(bounds);
    graphic.setColor(Color.black);

    width = bounds.getWidth();
    currentLineY = 0;
    for (index = 0; index < size; index++) {
      style = styles.get(index);
      font = style.getFont();
      q = style.appendDescription(ETextCase.IN_SENTENCE, textBuffer, true);
      if (q) {
        textBuffer.append(' ');
        textBuffer.append('[');
      }
      style.appendDescription(ETextCase.IN_SENTENCE, textBuffer, false);
      if (q) {
        textBuffer.append(']');
      }

      textBuffer.append(' ');
      textBuffer.append('-');
      textBuffer.append(' ');
      textBuffer.append(style.getFaceChoices());

      string = textBuffer.toString();
      textBuffer.clear();
      lineHeight = font
          .getLineMetrics(string, graphic.getFontRenderContext())
          .getHeight();
      currentLineY += lineHeight * 1.5d;

      try (final IScope fa = style.applyTo(graphic)) {
        graphic.drawString(string, 0.05 * width, currentLineY);
        currentLineY += (0.4d * lineHeight);
      }
    }
  }
}
