package org.optimizationBenchmarking.utils.ml.classifcation.spec;

/** The different types of features. */
public enum EFeatureType {

  /**
   * The feature values are numbers and are stored as {@code double}. The
   * magnitude and order of numbers is expected to hold meaningful
   * information.
   */
  NUMERICAL,

  /**
   * The feature values represent names or other choices from a finite set.
   * They will still be encoded as {@code double}, where each value is an
   * integer number. This integer numbers should represent the index of the
   * feature value in the ordered list of feature values. The order and
   * magnitude of the numbers (i.e., encoded, feature values) is assumed to
   * not hold any information.
   */
  NOMINAL;

}
