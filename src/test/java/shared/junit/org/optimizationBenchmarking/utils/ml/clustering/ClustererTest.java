package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.HashSet;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJobBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * A test for clusterers
 *
 * @param <CT>
 *          the clusterer
 */
@Ignore
public abstract class ClustererTest<CT extends IClusterer>
    extends ToolTest<CT> {

  /**
   * create the test
   *
   * @param clusterer
   *          the clusterer
   */
  protected ClustererTest(final CT clusterer) {
    super(clusterer);
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
    Assert.assertNotNull(dataset);
    this.__validateResult(result, dataset.data.m(), -1);
  }

  /**
   * validate the result
   *
   * @param elementCount
   *          the number of elements to create
   * @param clusterCount
   *          the number of clusters, {@code -1} for free choice
   * @param result
   *          the result
   */
  private final void __validateResult(final IClusteringResult result,
      final int elementCount, final int clusterCount) {
    final int[] clusters;
    final HashSet<Integer> hash;

    Assert.assertNotNull(result);

    clusters = result.getClustersRef();
    Assert.assertEquals(clusters.length, elementCount);

    hash = new HashSet<>();
    for (final int i : clusters) {
      hash.add(Integer.valueOf(i));
    }
    Assert.assertTrue(hash.size() > 0);
    Assert.assertFalse(hash.size() > clusters.length);
    if (clusterCount > 0) {
      Assert.assertEquals(clusterCount, hash.size());
    }

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
    engine = this.getInstance();
    Assert.assertNotNull(engine);
    if (engine.canUse()) {
      this.__validateResult(//
          this._doDataClusterExample(engine, dataset, useNumber), //
          dataset);
    }
  }

  /**
   * Create some random data for clustering
   *
   * @param builder
   *          the clustering job builder
   * @param elementCount
   *          the number of elements to create
   * @param random
   *          the random number generator
   */
  protected abstract void setRandomData(
      final IClusteringJobBuilder builder, final int elementCount,
      final Random random);

  /**
   * cluster random data
   *
   * @param elementCount
   *          the number of elements to create
   * @param clusterCount
   *          the number of clusters, {@code -1} for free choice
   */
  private final void __testClusterRandomData(final int elementCount,
      final int clusterCount) {
    final IClusterer clusterer;
    final Random random;
    IClusteringJobBuilder builder;
    IClusteringJob job;
    IClusteringResult result;

    clusterer = this.getInstance();
    Assert.assertNotNull(clusterer);
    if (clusterer.canUse()) {
      random = new Random();

      builder = clusterer.use();
      this.setRandomData(builder, elementCount, random);
      if (clusterCount > 0) {
        builder.setClusterNumber(clusterCount);
      }
      job = builder.create();
      builder = null;
      result = job.call();
      job = null;
      this.__validateResult(result, elementCount, clusterCount);
      result = null;
    }
  }

  /** cluster one random element into one cluster */
  @Test(timeout = 3600000)
  public void testCluster1RandomElement1Cluster() {
    this.__testClusterRandomData(1, 1);
  }

  /** cluster one random element into an arbitrary number of clusters */
  @Test(timeout = 3600000)
  public void testCluster1RandomElementXCluster() {
    this.__testClusterRandomData(1, -1);
  }

  /** cluster two random elements into one cluster */
  @Test(timeout = 3600000)
  public void testCluster2RandomElementsInto1Cluster() {
    this.__testClusterRandomData(2, 1);
  }

  /** cluster two random elements into two clusters */
  @Test(timeout = 3600000)
  public void testCluster2RandomElementsInto2Clusters() {
    this.__testClusterRandomData(2, 2);
  }

  /** cluster two random elements into an arbitrary number of clusters */
  @Test(timeout = 3600000)
  public void testCluster2RandomElementsIntoArbitrarilyManyCluster() {
    this.__testClusterRandomData(2, -1);
  }

  /** cluster three random elements into one cluster */
  @Test(timeout = 3600000)
  public void testCluster3RandomElementsInto1Cluster() {
    this.__testClusterRandomData(3, 1);
  }

  /** cluster three random elements into two clusters */
  @Test(timeout = 3600000)
  public void testCluster3RandomElementsInto2Clusters() {
    this.__testClusterRandomData(3, 2);
  }

  /** cluster three random elements into three clusters */
  @Test(timeout = 3600000)
  public void testCluster3RandomElementsInto3Clusters() {
    this.__testClusterRandomData(3, 3);
  }

  /** cluster three random elements into an arbitrary number of clusters */
  @Test(timeout = 3600000)
  public void testCluster3RandomElementsIntoArbitrarilyManyCluster() {
    this.__testClusterRandomData(3, -1);
  }

  /** cluster four random elements into one cluster */
  @Test(timeout = 3600000)
  public void testCluster4RandomElementsInto1Cluster() {
    this.__testClusterRandomData(4, 1);
  }

  /** cluster four random elements into two clusters */
  @Test(timeout = 3600000)
  public void testCluster4RandomElementsInto2Clusters() {
    this.__testClusterRandomData(4, 2);
  }

  /** cluster four random elements into three clusters */
  @Test(timeout = 3600000)
  public void testCluster4RandomElementsInto3Clusters() {
    this.__testClusterRandomData(4, 3);
  }

  /** cluster four random elements into four clusters */
  @Test(timeout = 3600000)
  public void testCluster4RandomElementsInto4Clusters() {
    this.__testClusterRandomData(4, 4);
  }

  /** cluster four random elements into an arbitrary number of clusters */
  @Test(timeout = 3600000)
  public void testCluster4RandomElementsIntoArbitrarilyManyCluster() {
    this.__testClusterRandomData(4, -1);
  }

  /** cluster five random elements into one cluster */
  @Test(timeout = 3600000)
  public void testCluster5RandomElementsInto1Cluster() {
    this.__testClusterRandomData(5, 1);
  }

  /** cluster five random elements into two clusters */
  @Test(timeout = 3600000)
  public void testCluster5RandomElementsInto2Clusters() {
    this.__testClusterRandomData(5, 2);
  }

  /** cluster five random elements into three clusters */
  @Test(timeout = 3600000)
  public void testCluster5RandomElementsInto3Clusters() {
    this.__testClusterRandomData(5, 3);
  }

  /** cluster five random elements into four clusters */
  @Test(timeout = 3600000)
  public void testCluster5RandomElementsInto4Clusters() {
    this.__testClusterRandomData(5, 4);
  }

  /** cluster five random elements into five clusters */
  @Test(timeout = 3600000)
  public void testCluster5RandomElementsInto5Clusters() {
    this.__testClusterRandomData(5, 5);
  }

  /** cluster five random elements into an arbitrary number of clusters */
  @Test(timeout = 3600000)
  public void testCluster5RandomElementsIntoArbitrarilyManyCluster() {
    this.__testClusterRandomData(5, -1);
  }

  /** cluster one hundred random elements into one cluster */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto1Cluster() {
    this.__testClusterRandomData(100, 1);
  }

  /** cluster one hundred random elements into two clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto2Clusters() {
    this.__testClusterRandomData(100, 2);
  }

  /** cluster one hundred random elements into three clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto3Clusters() {
    this.__testClusterRandomData(100, 3);
  }

  /** cluster one hundred random elements into four clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto4Clusters() {
    this.__testClusterRandomData(100, 4);
  }

  /** cluster one hundred random elements into five clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto5Clusters() {
    this.__testClusterRandomData(100, 5);
  }

  /** cluster one hundred random elements into 50 clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto50Clusters() {
    this.__testClusterRandomData(100, 50);
  }

  /** cluster one hundred random elements into 99 clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto99Clusters() {
    this.__testClusterRandomData(100, 99);
  }

  /** cluster one hundred random elements into one hundred clusters */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsInto100Clusters() {
    this.__testClusterRandomData(100, 100);
  }

  /**
   * cluster one hundred random elements into an arbitrary number of
   * clusters
   */
  @Test(timeout = 3600000)
  public void testCluster100RandomElementsIntoArbitrarilyManyCluster() {
    this.__testClusterRandomData(100, -1);
  }
}
