package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJobBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

import shared.junit.CategorySlowTests;

/**
 * A test for clusterers based on random data
 *
 * @param <CT>
 *          the clusterer
 */
@Ignore
public abstract class ClustererTestBasedOnRandomData<CT extends IClusterer>
    extends BasicClustererTest<CT> {

  /**
   * create the test
   *
   * @param clusterer
   *          the clusterer
   */
  protected ClustererTestBasedOnRandomData(final CT clusterer) {
    super(clusterer);
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
        builder.setMinClusters(clusterCount);
        builder.setMaxClusters(clusterCount);
      }
      job = builder.create();
      builder = null;
      result = job.call();
      job = null;
      this._validateResult(result, elementCount, clusterCount);
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
  @Category(CategorySlowTests.class)
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
  @Category(CategorySlowTests.class)
  public void testCluster5RandomElementsInto1Cluster() {
    this.__testClusterRandomData(5, 1);
  }

  /** cluster five random elements into two clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testCluster5RandomElementsInto2Clusters() {
    this.__testClusterRandomData(5, 2);
  }

  /** cluster five random elements into three clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testCluster5RandomElementsInto3Clusters() {
    this.__testClusterRandomData(5, 3);
  }

  /** cluster five random elements into four clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testCluster5RandomElementsInto4Clusters() {
    this.__testClusterRandomData(5, 4);
  }

  /** cluster five random elements into five clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testCluster5RandomElementsInto5Clusters() {
    this.__testClusterRandomData(5, 5);
  }

  /** cluster five random elements into an arbitrary number of clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
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
  @Category(CategorySlowTests.class)
  public void testCluster100RandomElementsInto3Clusters() {
    this.__testClusterRandomData(100, 3);
  }

  /** cluster one hundred random elements into four clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testCluster100RandomElementsInto4Clusters() {
    this.__testClusterRandomData(100, 4);
  }

  /** cluster one hundred random elements into five clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testCluster100RandomElementsInto5Clusters() {
    this.__testClusterRandomData(100, 5);
  }

  /** cluster one hundred random elements into 50 clusters */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
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
