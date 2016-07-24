package org.optimizationBenchmarking.utils.ml.classification.spec;

/** The result record of a classification job. */
public interface IClassifierTrainingResult {

  /**
   * Get the classifier that was created
   *
   * @return the created classifier
   */
  public abstract IClassifier getClassifier();

  /**
   * Get the quality of the classifier.
   *
   * @return the quality, the smaller the better
   */
  public abstract double getQuality();
}
