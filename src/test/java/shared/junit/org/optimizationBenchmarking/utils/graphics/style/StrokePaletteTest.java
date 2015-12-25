package shared.junit.org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Stroke;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokeStyle;

/** The test of the stroke palette. */
@Ignore
public class StrokePaletteTest
    extends StylePaletteTest<IStrokeStyle, IStrokePalette> {

  /** create */
  public StrokePaletteTest() {
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
  public StrokePaletteTest(final IStrokePalette instance,
      final boolean isSingleton) {
    super(instance, isSingleton);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected void testEachElement_testElement(final IStrokeStyle element,
      final Object context) {
    final Stroke stroke;

    super.testEachElement_testElement(element, context);

    Assert.assertNotNull(element);

    stroke = element.getStroke();
    Assert.assertNotNull(stroke);

    if (stroke != element) {
      Assert.assertTrue(((HashSet) context).add(stroke));
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void testEachElement_finalize(final IStrokePalette collection,
      final Object context) {
    super.testEachElement_finalize(collection, context);
    this.testEachElement_testElement(collection.getDefaultStroke(),
        context);
    this.testEachElement_testElement(collection.getThickStroke(), context);
    this.testEachElement_testElement(collection.getThinStroke(), context);
  }
}
