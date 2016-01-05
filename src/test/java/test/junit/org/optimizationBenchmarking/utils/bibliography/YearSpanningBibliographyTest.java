package test.junit.org.optimizationBenchmarking.utils.bibliography;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

import examples.org.optimizationBenchmarking.utils.bibliography.YearSpanningTestBibliography;
import shared.junit.org.optimizationBenchmarking.utils.bibliography.BibliographyTest;

/**
 * a test case to test generating a paper whose date spans multiple years
 */
public class YearSpanningBibliographyTest extends BibliographyTest {

  /** create */
  public YearSpanningBibliographyTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography createBibliography() {
    return new YearSpanningTestBibliography().call();
  }
}
