package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * A base class for tests for clusterers
 *
 * @param <CT>
 *          the clusterer
 */
@Ignore
public abstract class BasicClustererTest<CT extends IClusterer>
    extends ToolTest<CT> {

  /**
   * create the test
   *
   * @param clusterer
   *          the clusterer
   */
  protected BasicClustererTest(final CT clusterer) {
    super(clusterer);
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
  final void _validateResult(final IClusteringResult result,
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
}
