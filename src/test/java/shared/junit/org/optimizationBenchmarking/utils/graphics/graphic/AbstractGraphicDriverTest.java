package shared.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorPalette;
import org.optimizationBenchmarking.utils.io.paths.TempDir;

import examples.org.optimizationBenchmarking.utils.graphics.ColorPaletteExample;
import shared.FileProducerCollector;
import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/** The test of a graphic driver. */
@Ignore
public abstract class AbstractGraphicDriverTest
    extends ToolTest<IGraphicDriver> {

  /** create */
  protected AbstractGraphicDriverTest() {
    super();
  }

  /**
   * Get the expected output format
   *
   * @return the expected output format, or {@code null} if no output is
   *         expected
   */
  protected IGraphicFormat getExpectedOutputFormat() {
    return this.getInstance().getFileType();
  }

  /**
   * test whether the tool can be used for a given color palette
   *
   * @param palette
   *          the color palette
   * @param model
   *          the color model
   */
  protected void testColorPalette(final IColorPalette palette,
      final EColorModel model) {
    final FileProducerCollector collector;
    final IGraphicFormat expected;

    expected = this.getExpectedOutputFormat();

    collector = ((expected != null) ? new FileProducerCollector() : null);
    try (final TempDir temp = new TempDir()) {
      new ColorPaletteExample(//
          collector, //
          temp.getPath(), //
          this.getInstance(), //
          null, model, -1, -1d, palette).run();
      if (collector != null) {
        collector.assertFilesOfType(expected);
      }
    } catch (final IOException ioe) {
      throw new RuntimeException("Error during IO.", ioe); //$NON-NLS-1$
    }
  }

  /**
   * Get a gray-scaled color palette
   *
   * @return a gray-scaled color palette
   */
  protected abstract IColorPalette getGrayPalette();

  /** test whether the graphics works with a gray palette */
  @Test(timeout = 3600000)
  public void testGrayColorPaletteGray8() {
    this.testColorPalette(this.getGrayPalette(), EColorModel.GRAY_8_BIT);
  }

  /** test whether the graphics works with a gray palette */
  @Test(timeout = 3600000)
  public void testGrayColorPaletteGray16() {
    this.testColorPalette(this.getGrayPalette(), EColorModel.GRAY_16_BIT);
  }

  /** test whether the graphics works with a gray palette */
  @Test(timeout = 3600000)
  public void testGrayColorPaletteRGB15() {
    this.testColorPalette(this.getGrayPalette(), EColorModel.RGB_15_BIT);
  }

  /** test whether the graphics works with a gray palette */
  @Test(timeout = 3600000)
  public void testGrayColorPaletteARGB32() {
    this.testColorPalette(this.getGrayPalette(), EColorModel.ARGB_32_BIT);
  }

  /**
   * Get a default color palette
   *
   * @return a default color palette
   */
  protected abstract IColorPalette getDefaultColorPalette();

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testDefaultColorPaletteARGB32() {
    this.testColorPalette(this.getDefaultColorPalette(),
        EColorModel.ARGB_32_BIT);
  }

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testDefaultColorPaletteRGB24() {
    this.testColorPalette(this.getDefaultColorPalette(),
        EColorModel.RGB_24_BIT);
  }

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testDefaultColorPaletteRGB16() {
    this.testColorPalette(this.getDefaultColorPalette(),
        EColorModel.RGB_16_BIT);
  }

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testDefaultColorPaletteRGB15() {
    this.testColorPalette(this.getDefaultColorPalette(),
        EColorModel.RGB_15_BIT);
  }

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testDefaultColorPaletteGray16() {
    this.testColorPalette(this.getDefaultColorPalette(),
        EColorModel.GRAY_16_BIT);
  }

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testDefaultColorPaletteGray8() {
    this.testColorPalette(this.getDefaultColorPalette(),
        EColorModel.GRAY_8_BIT);
  }

  /**
   * Get a small color palette
   *
   * @return a small color palette
   */
  protected abstract IColorPalette getSmallColorPalette();

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testSmallColorPaletteRGB16() {
    this.testColorPalette(this.getSmallColorPalette(),
        EColorModel.RGB_16_BIT);
  }

  /** test whether the graphics works with the default palette */
  @Test(timeout = 3600000)
  public void testSmallColorPaletteRGB15() {
    this.testColorPalette(this.getSmallColorPalette(),
        EColorModel.RGB_15_BIT);
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();

    this.testDefaultColorPaletteARGB32();
    this.testDefaultColorPaletteRGB24();
    this.testDefaultColorPaletteRGB16();
    this.testDefaultColorPaletteRGB15();
    this.testDefaultColorPaletteGray16();
    this.testDefaultColorPaletteGray8();
    this.testGrayColorPaletteGray8();
    this.testGrayColorPaletteGray16();
    this.testGrayColorPaletteRGB15();
    this.testSmallColorPaletteRGB15();
    this.testSmallColorPaletteRGB16();
  }
}
