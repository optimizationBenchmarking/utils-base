package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

/**
 * A test for clusterers
 *
 * @param <CT>
 *          the clusterer
 */
@Ignore
public abstract class ClustererTestBasedOnExampleData<CT extends IClusterer>
    extends BasicClustererTest<CT> {

  /**
   * create the test
   *
   * @param clusterer
   *          the clusterer
   */
  protected ClustererTestBasedOnExampleData(final CT clusterer) {
    super(clusterer);
  }

  /**
   * validate the result
   *
   * @param result
   *          the result
   * @param dataset
   *          the data set
   */
  private final void __validateResult(final IClusteringResult result,
      final ClusteringExampleDataset dataset) {
    Assert.assertNotNull(dataset);
    this._validateResult(result, dataset.data.m(), -1);
  }

  /**
   * cluster a data example
   *
   * @param clusterer
   *          the clusterer
   * @param dataset
   *          the data set
   * @param useNumber
   *          should the cluster number be used ({@code true}) or
   *          automatically detected ({@code false})
   * @return the clustering result
   */
  abstract IClusteringResult _doDataClusterExample(final CT clusterer,
      final ClusteringExampleDataset dataset, final boolean useNumber);

  /**
   * cluster a data example
   *
   * @param dataset
   *          the data set
   * @param useNumber
   *          should the cluster number be used ({@code true}) or
   *          automatically detected ({@code false})
   */
  protected final void dataClusterExample(
      final ClusteringExampleDataset dataset, final boolean useNumber) {
    final CT engine;

    Assert.assertNotNull(dataset);
    engine = this.getInstance();
    Assert.assertNotNull(engine);
    if (engine.canUse()) {
      this.__validateResult(//
          this._doDataClusterExample(engine, dataset, useNumber), //
          dataset);
    }
  }
}
