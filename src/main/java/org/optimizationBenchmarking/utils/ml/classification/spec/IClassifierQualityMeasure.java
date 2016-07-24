package org.optimizationBenchmarking.utils.ml.classification.spec;

/**
 * A classification quality measure allows us to judge the quality of a
 * classifier.
 */
public interface IClassifierQualityMeasure {

  /**
   * Compute the quality of the given {@code classifier}.
   *
   * @param classifier
   *          the classifier
   * @return its quality: the lower, the better; {@code 0} is best
   */
  public abstract double evaluate(final IClassifier classifier);
}
