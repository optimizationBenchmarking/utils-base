package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

import shared.junit.TestBase;

/**
 * A test for clusterers
 *
 * @param <CT>
 *          the clusterer
 */
@Ignore
public abstract class ClustererTest<CT extends IClusterer>
    extends TestBase {

  /** create the test */
  protected ClustererTest() {
    super();
  }

  /**
   * Get the clustering tool
   *
   * @return the clustering tool
   */
  protected abstract CT getTool();

  /** test whether the clusterer engine tool can be constructed */
  @Test(timeout = 3600000)
  public void testToolNotNull() {
    Assert.assertNotNull(this.getTool());
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
   * validate the result
   *
   * @param result
   *          the result
   * @param dataset
   *          the data set
   */
  private final void __validateResult(final IClusteringResult result,
      final ClusteringExampleDataset dataset) {
    final int[] clusters;
    final HashSet<Integer> hash;

    Assert.assertNotNull(dataset);
    Assert.assertNotNull(result);

    clusters = result.getClustersRef();
    Assert.assertEquals(clusters.length, dataset.data.m());

    hash = new HashSet<>();
    for (final int i : clusters) {
      hash.add(Integer.valueOf(i));
    }
    Assert.assertTrue(hash.size() > 0);
    Assert.assertFalse(hash.size() > clusters.length);

    Assert.assertTrue(result.getQuality() >= 0d);
    Assert.assertTrue(MathUtils.isFinite(result.getQuality()));
  }

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
    engine = this.getTool();
    Assert.assertNotNull(engine);
    if (engine.canUse()) {
      this.__validateResult(//
          this._doDataClusterExample(engine, dataset, useNumber), //
          dataset);
    }
  }
}
