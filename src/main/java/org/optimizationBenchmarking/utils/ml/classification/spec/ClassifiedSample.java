package org.optimizationBenchmarking.utils.ml.classification.spec;

/** A class for samples whose class is known. */
public class ClassifiedSample {

  /** the feature values */
  public final double[] featureValues;

  /** the class to which the sample belongs */
  public final int sampleClass;

  /**
   * Create a classified sample.
   *
   * @param _sampleClass
   *          the sample class, the first class has index {@code 0}
   * @param _featureValues
   *          the feature values
   */
  public ClassifiedSample(final int _sampleClass,
      final double... _featureValues) {
    super();

    if ((_sampleClass < 0) || (_sampleClass > 1000)) {
      throw new IllegalArgumentException((//
          "Class must be in 0...1000, but specified is " //$NON-NLS-1$
              + _sampleClass)
          + '.');
    }
    if (_featureValues == null) {
      throw new IllegalArgumentException(//
          "Feature vector must not be null."); //$NON-NLS-1$
    }
    if (_featureValues.length <= 0) {
      throw new IllegalArgumentException(//
          "Feature vector must not be empty."); //$NON-NLS-1$
    }
    this.sampleClass = _sampleClass;
    this.featureValues = _featureValues;
  }
}
