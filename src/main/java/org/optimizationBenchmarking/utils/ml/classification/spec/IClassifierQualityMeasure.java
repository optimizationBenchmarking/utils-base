package org.optimizationBenchmarking.utils.ml.classification.spec;

import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;

/**
 * A classification quality measure allows us to judge the quality of a
 * classifier.
 *
 * @param <T>
 *          the token type
 */
public interface IClassifierQualityMeasure<T> extends ISemanticComponent {

  /**
   * Compute the quality of the given {@code classifier}.
   *
   * @param trainingSamples
   *          the data samples used for checking the classifier
   * @param classifier
   *          the classifier
   * @param token
   *          the token to be used to ensure efficient computation
   * @return its quality: the lower, the better; {@code 0} is best
   */
  public abstract double evaluate(final IClassifier classifier,
      final T token, final ClassifiedSample[] trainingSamples);

  /**
   * Create a computation token that can be used to compute the
   * classification quality for the given set of training samples as well
   * as for any of its subsets.
   *
   * @param trainingSamples
   *          the training samples
   * @return the token
   */
  public abstract T createToken(final ClassifiedSample[] trainingSamples);
}
