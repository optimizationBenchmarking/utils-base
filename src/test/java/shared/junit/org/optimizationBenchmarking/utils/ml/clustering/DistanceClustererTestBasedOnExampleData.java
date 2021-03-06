package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusteringJobBuilder;

/** A test for distance clusterers */
@Ignore
public abstract class DistanceClustererTestBasedOnExampleData
    extends ClustererTestBasedOnExampleData<IDistanceClusterer> {

  /**
   * create the test
   *
   * @param clusterer
   *          the clusterer
   */
  protected DistanceClustererTestBasedOnExampleData(
      final IDistanceClusterer clusterer) {
    super(clusterer);
  }

  /**
   * Transform a data matrix to a distance matrix
   *
   * @param dataMatrix
   *          the data matrix
   * @return the distance matrix
   */
  protected abstract IMatrix dataMatrixToDistanceMatrix(
      final IMatrix dataMatrix);

  /** {@inheritDoc} */
  @Override
  final IClusteringResult _doDataClusterExample(
      final IDistanceClusterer clusterer,
      final ClusteringExampleDataset dataset, final boolean useNumber) {
    final IDistanceClusteringJobBuilder builder;

    builder = clusterer.use().setDistanceMatrix(//
        this.dataMatrixToDistanceMatrix(dataset.data));
    if (useNumber) {
      builder.setMinClusters(dataset.classes);
      builder.setMaxClusters(dataset.classes);
    }
    return builder.create().call();
  }
}
