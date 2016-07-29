package org.optimizationBenchmarking.utils.ml.classification.spec;

/** The different types of features. */
public enum EFeatureType {

  /**
   * The feature values are numbers and are stored as {@code double}. The
   * magnitude and order of numbers is expected to hold meaningful
   * information.
   */
  NUMERICAL {

    /** {@inheritDoc} */
    @Override
    public final void checkFeatureValue(final double value) {
      if (value != value) {
        throw new IllegalArgumentException(//
            "Numerical feature value cannot be NaN."); //$NON-NLS-1$
      }
    }

  },

  /**
   * The feature values represent names or other choices from a finite set.
   * They will still be encoded as {@code double}, where each value is an
   * integer number. This integer numbers should represent the index of the
   * feature value in the ordered list of feature values. The order and
   * magnitude of the numbers (i.e., encoded, feature values) is assumed to
   * not hold any information.
   */
  NOMINAL {
    /** {@inheritDoc} */
    @Override
    public final void checkFeatureValue(final double value) {
      final int featureValue;

      featureValue = ((int) value);
      if ((value != featureValue) || (featureValue < 0)
          || (featureValue > 1000)) {
        throw new IllegalArgumentException(//
            "Nominal feature values must an integer from 0...1000, but is " //$NON-NLS-1$
                + value);
      }
    }
  },

  /**
   * The feature values represent the {@code boolean} values {@code true}
   * and {@code false}, where {@code true} is encoded as {@code 1.0d} and
   * {@code false} and {@code 0.0d}.
   */
  BOOLEAN {
    /** {@inheritDoc} */
    @Override
    public final void checkFeatureValue(final double value) {

      if ((value != 0d) && (value != 1d)) {
        throw new IllegalArgumentException(//
            "Boolean (binary) feature values must be either 0 or 1, but is " //$NON-NLS-1$
                + value);
      }
    }
  };

  /**
   * Check whether the given feature value is valid.
   *
   * @param value
   *          the feature value
   * @throws IllegalArgumentException
   *           if the feature value is not value
   */
  public abstract void checkFeatureValue(final double value);
}
