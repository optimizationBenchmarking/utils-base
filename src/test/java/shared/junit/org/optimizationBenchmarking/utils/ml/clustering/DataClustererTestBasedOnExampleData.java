package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusteringJobBuilder;

/** A test for data clusterers based on example data */
@Ignore
public abstract class DataClustererTestBasedOnExampleData
    extends ClustererTestBasedOnExampleData<IDataClusterer> {

  /**
   * create the test
   *
   * @param clusterer
   *          the clusterer
   */
  protected DataClustererTestBasedOnExampleData(
      final IDataClusterer clusterer) {
    super(clusterer);
  }

  /** {@inheritDoc} */
  @Override
  final IClusteringResult _doDataClusterExample(
      final IDataClusterer clusterer,
      final ClusteringExampleDataset dataset, final boolean useNumber) {
    final IDataClusteringJobBuilder builder;

    builder = clusterer.use().setData(dataset.data);
    if (useNumber) {
      builder.setClusterNumber(dataset.classes);
    }
    return builder.create().call();
  }
}
