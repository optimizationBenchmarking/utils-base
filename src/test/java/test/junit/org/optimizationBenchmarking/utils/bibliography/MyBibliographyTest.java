package test.junit.org.optimizationBenchmarking.utils.bibliography;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

import examples.org.optimizationBenchmarking.utils.bibliography.MyBibliography;
import shared.junit.org.optimizationBenchmarking.utils.bibliography.BibliographyTest;

/**
 * a test case to test generating some of my own papers with the
 * bibliography api
 */
public class MyBibliographyTest extends BibliographyTest {

  /** create */
  public MyBibliographyTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography createBibliography() {
    return new MyBibliography().call();
  }
}
