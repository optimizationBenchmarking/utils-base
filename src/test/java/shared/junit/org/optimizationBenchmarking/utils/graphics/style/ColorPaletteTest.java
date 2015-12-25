package shared.junit.org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Color;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorStyle;

/** The test of the color palette. */
@Ignore
public class ColorPaletteTest
    extends StylePaletteTest<IColorStyle, IColorPalette> {

  /** create */
  public ColorPaletteTest() {
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
  public ColorPaletteTest(final IColorPalette instance,
      final boolean isSingleton) {
    super(instance, isSingleton);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected void testEachElement_testElement(final IColorStyle element,
      final Object context) {
    final Color color;

    super.testEachElement_testElement(element, context);

    Assert.assertNotNull(element);

    color = element.getColor();
    Assert.assertNotNull(color);

    if (color != element) {
      Assert.assertTrue(((HashSet) context).add(color));
    }
    Assert.assertTrue(((HashSet) context).add(//
        Integer.valueOf(color.getRGB() & 0xffffff)));
  }

  /** {@inheritDoc} */
  @Override
  protected void testEachElement_finalize(final IColorPalette collection,
      final Object context) {
    super.testEachElement_finalize(collection, context);
    this.testEachElement_testElement(collection.getBlack(), context);
    this.testEachElement_testElement(collection.getWhite(), context);
  }

  /**
   * Test finding similar color.
   */
  @Test(timeout = 3600000)
  public void testGetMostSimilarColor() {
    final IColorPalette palette;

    palette = this.getInstance();

    for (final IColorStyle style : palette) {
      Assert.assertSame(style, //
          palette.getMostSimilarColor(style.getColor()));
      Assert.assertSame(style, palette.getMostSimilarColor(//
          style.getColor().getRGB() & 0xffffff));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testGetMostSimilarColor();
  }
}
