package shared.junit.org.optimizationBenchmarking.utils.ml.classification;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.ml.classification.spec.ClassifiedSample;
import org.optimizationBenchmarking.utils.ml.classification.spec.EFeatureType;

import shared.junit.org.optimizationBenchmarking.utils.ml.clustering.ClusteringExampleDataset;

/** The example data set. */
public final class ClassifierExampleDataset {

  /** the features */
  public final EFeatureType[] features;

  /** the data samples */
  public final ClassifiedSample[] data;

  /**
   * create the classifier example data set
   *
   * @param _features
   *          the features
   * @param _samples
   *          the samples
   */
  public ClassifierExampleDataset(final EFeatureType[] _features,
      final ClassifiedSample[] _samples) {
    super();
    if (_features == null) {
      throw new IllegalArgumentException("Feature array cannot be null."); //$NON-NLS-1$
    }
    if (_features.length <= 0) {
      throw new IllegalArgumentException("Feature array cannot be empty."); //$NON-NLS-1$
    }
    if (_samples == null) {
      throw new IllegalArgumentException("Sample array cannot be null."); //$NON-NLS-1$
    }
    if (_samples.length <= 0) {
      throw new IllegalArgumentException("Sample array cannot be empty."); //$NON-NLS-1$
    }
    this.features = _features;
    this.data = _samples;
  }

  /**
   * Actually, we can turn a problem with known clusters into one for
   * classification.
   *
   * @param clusters
   *          the cluster problem
   */
  public ClassifierExampleDataset(
      final ClusteringExampleDataset clusters) {
    super();

    final int featureCount;
    final ClassifiedSample[] samples;
    double[] vector;
    int index, index2;

    featureCount = clusters.data.n();
    this.features = new EFeatureType[featureCount];
    Arrays.fill(this.features, EFeatureType.NUMERICAL);
    this.data = samples = new ClassifiedSample[clusters.data.m()];
    for (index = samples.length; (--index) >= 0;) {
      vector = new double[featureCount];
      for (index2 = featureCount; (--index2) >= 0;) {
        vector[index2] = clusters.data.getDouble(index, index2);
      }
      samples[index] = new ClassifiedSample(clusters.clusters[index],
          vector);
    }
  }
}
