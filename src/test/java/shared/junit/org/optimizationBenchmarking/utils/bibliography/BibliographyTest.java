package shared.junit.org.optimizationBenchmarking.utils.bibliography;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArrayListViewTest;

/**
 * a test case to test generating, reading, and writing some bibliography
 */
@Ignore
public abstract class BibliographyTest extends ArrayListViewTest {

  /** the bibliography */
  private Bibliography m_bib;

  /** create */
  public BibliographyTest() {
    super();
  }

  /**
   * Create a bibliography
   *
   * @return the bibliography
   */
  protected abstract Bibliography createBibliography();

  /**
   * create my bibliography
   *
   * @return the bibliography
   */
  public synchronized Bibliography getBibliography() {
    if (this.m_bib == null) {
      this.m_bib = this.createBibliography();
    }
    return this.m_bib;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<Object> getInstance() {
    return ((ArrayListView) (this.getBibliography()));
  }
}
