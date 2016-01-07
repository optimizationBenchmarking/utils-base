package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusteringJobBuilder;

/** A test for data clusterers */
@Ignore
public abstract class DataClustererTest
    extends ClustererTest<IDataClusterer> {

  /** create the test */
  protected DataClustererTest() {
    super();
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
