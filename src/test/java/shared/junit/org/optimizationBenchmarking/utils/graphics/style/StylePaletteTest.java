package shared.junit.org.optimizationBenchmarking.utils.graphics.style;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStylePalette;
import org.optimizationBenchmarking.utils.text.TextUtils;

import shared.junit.org.optimizationBenchmarking.utils.collections.ListTest;

/**
 * A basic test for style palettes.
 *
 * @param <PT>
 *          the palette type
 * @param <ET>
 *          the palette element type
 */
@Ignore
public class StylePaletteTest<ET extends IStyle, PT extends IStylePalette<ET>>
    extends ListTest<ET, PT> {

  /** create */
  public StylePaletteTest() {
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
  public StylePaletteTest(final PT instance, final boolean isSingleton) {
    super(null, instance, isSingleton, false);
  }

  /** {@inheritDoc} */
  @Override
  protected Object testEachElement_createContext() {
    return new HashSet<>();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected void testEachElement_testElement(final ET element,
      final Object context) {
    final String id;

    super.testEachElement_testElement(element, context);

    Assert.assertNotNull(element);

    id = element.getID();
    Assert.assertNotNull(id);
    Assert.assertTrue(id.length() > 0);
    Assert.assertTrue(((HashSet) context).add(//
        TextUtils.toComparisonCase(id)));
    Assert.assertTrue(((HashSet) context).add(element));
    Assert.assertFalse(((HashSet) context).add(element));
  }

  /** not applicable */
  @Ignore
  @Override
  public void testSubList() {
    //
  }

  /** not applicable */
  @Ignore
  @Override
  public void testEqualsToArrayList() {
    //
  }
}
