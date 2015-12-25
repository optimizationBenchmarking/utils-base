package test.junit.org.optimizationBenchmarking.utils.text.transformations;

import org.optimizationBenchmarking.utils.text.transformations.CharTransformer;
import org.optimizationBenchmarking.utils.text.transformations.XMLCharTransformer;

import shared.junit.org.optimizationBenchmarking.utils.text.transformations.CharTransformerTest;

/**
 * test the LaTeX char transformer
 */
public class XMLCharTransformerTest extends CharTransformerTest {

  /** create */
  public XMLCharTransformerTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final CharTransformer getInstance() {
    return XMLCharTransformer.getInstance();
  }

}
