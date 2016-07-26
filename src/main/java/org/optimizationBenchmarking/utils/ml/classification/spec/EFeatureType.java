package org.optimizationBenchmarking.utils.ml.classification.spec;

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
  NOMINAL {
    /** {@inheritDoc} */
    @Override
    public final void checkFeatureValue(final double value,
        final int index) {
      super.checkFeatureValue(value, index);
      if (value != ((int) (value))) {
        throw new IllegalArgumentException(
            "Values of nominal features must be integer numbers, but " //$NON-NLS-1$
                + value + " at index " + index + //$NON-NLS-1$
                " is not."); //$NON-NLS-1$
      }
    }
  };

  /**
   * Check a feature value whether it is acceptable for the given type of
   * feature. If not, throw an {@link IllegalArgumentException}.
   *
   * @param value
   *          the value
   * @param index
   *          the index of this feature
   */
  public void checkFeatureValue(final double value, final int index) {
    if (value != value) {
      throw new IllegalArgumentException(//
          "Feature at index " //$NON-NLS-1$
              + index + " is NaN.");//$NON-NLS-1$
    }
  }

}
