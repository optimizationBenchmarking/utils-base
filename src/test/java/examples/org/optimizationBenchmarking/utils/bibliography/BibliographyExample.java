package examples.org.optimizationBenchmarking.utils.bibliography;

import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

/**
 * A class to test generating some of bibliographic data records with the
 * {@link org.optimizationBenchmarking.utils.bibliography.data bibliography
 * API}.
 */
public abstract class BibliographyExample
    implements Callable<Bibliography> {

  /** the constructor */
  protected BibliographyExample() {
    super();
  }

  /**
   * create my bibliography
   *
   * @return the bibliography
   */
  @Override
  public abstract Bibliography call();
}
