package org.optimizationBenchmarking.utils.ml.classification.spec;

import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;

/** The interface for classifiers. */
public interface IClassifier extends ISemanticComponent {

  /**
   * Classify the given feature vector. Features are always represented as
   * {@code double}: If they are numerical, that will do anyway. Otherwise,
   * i.e., for nominal features, the number represents the 0-based index of
   * the feature value in the sorted list of nominal features.
   *
   * @param features
   *          the feature vector
   * @return the 0-based class index
   */
  public abstract int classify(final double[] features);
}
