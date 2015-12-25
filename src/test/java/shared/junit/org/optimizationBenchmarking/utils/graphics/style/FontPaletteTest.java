package shared.junit.org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Font;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontPalette;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontStyle;

/** The test of the font palette. */
@Ignore
public class FontPaletteTest
    extends StylePaletteTest<IFontStyle, IFontPalette> {

  /** create */
  public FontPaletteTest() {
    this(null, true);
  }

  /**
   * create
   *
   * @param isSingleton
   *          is this a singleton-based tests?
   * @param instance
   *          the instance, or {@code null} if unspecified
   */
  public FontPaletteTest(final IFontPalette instance,
      final boolean isSingleton) {
    super(instance, isSingleton);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected void testEachElement_testElement(final IFontStyle element,
      final Object context) {
    final Font font;

    super.testEachElement_testElement(element, context);

    Assert.assertNotNull(element);

    font = element.getFont();
    Assert.assertNotNull(font);

    if (font != element) {
      Assert.assertTrue(((HashSet) context).add(font));
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void testEachElement_finalize(final IFontPalette collection,
      final Object context) {
    super.testEachElement_finalize(collection, context);
    this.testEachElement_testElement(collection.getDefaultFont(), context);
    this.testEachElement_testElement(collection.getCodeFont(), context);
    this.testEachElement_testElement(collection.getEmphFont(), context);
  }

  /**
   * Test finding similar font.
   */
  @Test(timeout = 3600000)
  public void testGetMostSimilarFont() {
    final IFontPalette palette;

    palette = this.getInstance();

    for (final IFontStyle style : palette) {
      Assert.assertSame(style, //
          palette.getMostSimilarFont(style.getFamily(), style.isBold(),
              style.isItalic(), style.isUnderlined(), style.getSize()));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testGetMostSimilarFont();
  }
}