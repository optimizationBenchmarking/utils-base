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
  NOMINAL,

  /**
   * The feature values represent the {@code boolean} values {@code true}
   * and {@code false}, where {@code true} is encoded as {@code 1.0d} and
   * {@code false} and {@code 0.0d}.
   */
  BOOLEAN;

  /**
   * the maximum value a nominal feature is allowed to take on:
   * {@value} (the minimum is {@code 0})
   */
  public static final int MAX_NOMINAL = ClassifiedSample.MAX_CLASS;

  /** the unspecified encoded value value */
  public static final double UNSPECIFIED_DOUBLE = Double.NaN;
  /** the unspecified numerical value */
  public static final double UNSPECIFIED_NUMERICAL = EFeatureType.UNSPECIFIED_DOUBLE;

  /** the unspecified nominal value */
  public static final int UNSPECIFIED_NOMINAL = -1;

  /** the unspecified boolean value */
  public static final Boolean UNSPECIFIED_BOOLEAN = null;

  /**
   * Check whether a value of an encoded feature is unspecified.
   *
   * @param feature
   *          the feature feature
   * @return {@code true} if the feature value is unspecified,
   *         {@code false} otherwise
   */
  public static final boolean featureDoubleIsUnspecified(
      final double feature) {
    return (feature != feature);
  }

  /**
   * convert a {@code double} value to {@code int}
   *
   * @param value
   *          the value
   * @return the integer
   */
  private static final int __toInt(final double value) {
    return ((int) (0.5d + value));
  }

  /**
   * Check whether a value of a numerical feature is unspecified.
   *
   * @param numerical
   *          the numerical feature
   * @return {@code true} if the feature value is unspecified,
   *         {@code false} otherwise
   */
  public static final boolean featureNumericalIsUnspecified(
      final double numerical) {
    return ((numerical != numerical) ? true : false);
  }

  /**
   * Convert the numerical value to a {@code double}
   *
   * @param value
   *          the numerical value
   * @return the {@code double}
   */
  public static final double featureNumericalToDouble(final double value) {
    return EFeatureType.featureNumericalIsUnspecified(value)
        ? EFeatureType.UNSPECIFIED_DOUBLE : value;
  }

  /**
   * Convert a {@code double} to a numerical value
   *
   * @param numerical
   *          the {@code double} value
   * @return the numerical value index
   */
  public static final double featureDoubleToNumerical(
      final double numerical) {
    if (EFeatureType.featureDoubleIsUnspecified(numerical)) {
      return EFeatureType.UNSPECIFIED_NUMERICAL;
    }
    return numerical;
  }

  /**
   * Check whether a value of a nominal feature is unspecified.
   *
   * @param nominal
   *          the nominal feature
   * @return {@code true} if the feature value is unspecified,
   *         {@code false} otherwise
   */
  public static final boolean featureNominalIsUnspecified(
      final int nominal) {
    return (nominal == (-1));
  }

  /**
   * Convert the index of a nominal value in the sorted list of values to a
   * {@code double}
   *
   * @param nominal
   *          the nominal value
   * @return the {@code double}
   */
  public static final double featureNominalToDouble(final int nominal) {
    if (EFeatureType.featureNominalIsUnspecified(nominal)) {
      return EFeatureType.UNSPECIFIED_DOUBLE;
    }
    if ((nominal < 0) || (nominal > EFeatureType.MAX_NOMINAL)) {
      throw new IllegalArgumentException(//
          "Integer representation of value of nominal feature cannot be "//$NON-NLS-1$
              + nominal);
    }
    return nominal;
  }

  /**
   * Convert a {@code double} to the index of a nominal value in the sorted
   * list of values
   *
   * @param nominal
   *          the {@code double} value
   * @return the nominal value index
   */
  public static final int featureDoubleToNominal(final double nominal) {
    if (EFeatureType.featureDoubleIsUnspecified(nominal)) {
      return EFeatureType.UNSPECIFIED_NOMINAL;
    }
    if ((nominal < -0.49d) || (nominal > EFeatureType.MAX_NOMINAL)) {
      throw new IllegalArgumentException(//
          "Double representation of value of nominal feature cannot be " //$NON-NLS-1$
              + nominal);
    }
    return EFeatureType.__toInt(nominal);
  }

  /**
   * Convert the index of a class to a {@code double}
   *
   * @param clazz
   *          the class value
   * @return the {@code double}
   */
  public static final double classToDouble(final int clazz) {
    if ((clazz < 0) || (clazz > ClassifiedSample.MAX_CLASS)) {
      throw new IllegalArgumentException(//
          "Integer representation of class value cannot be " //$NON-NLS-1$
              + clazz);
    }
    return clazz;
  }

  /**
   * Convert the index of a class to a {@code double}
   *
   * @param clazz
   *          the class value
   * @return the {@code double}
   */
  public static final int doubleToClass(final double clazz) {
    if ((clazz != clazz) || (clazz < -0.49d)
        || (clazz > ClassifiedSample.MAX_CLASS)) {
      throw new IllegalArgumentException(//
          "Double representation of Class value cannot be " //$NON-NLS-1$
              + clazz);
    }

    return EFeatureType.__toInt(clazz);
  }

  /**
   * Check whether a value of a boolean feature is unspecified.
   *
   * @param feature
   *          the boolean feature
   * @return {@code true} if the feature value is unspecified,
   *         {@code false} otherwise
   */
  public static final boolean featureBooleanIsUnspecified(
      final Boolean feature) {
    return (feature == null) ? true : false;
  }

  /**
   * Convert the {@code boolean} value to a {@code double}
   *
   * @param bool
   *          the {@code boolean} value
   * @return the {@code double}
   */
  public static final double featureBooleanToDouble(final Boolean bool) {
    return EFeatureType.featureBooleanIsUnspecified(bool)
        ? EFeatureType.UNSPECIFIED_DOUBLE
        : (bool.booleanValue() ? 1d : 0d);
  }

  /**
   * Convert a {@code double} to the {@code boolean} value
   *
   * @param bool
   *          the {@code double} value
   * @return the {@code boolean} value index
   */
  public static final Boolean featureDoubleToBoolean(final double bool) {
    if (EFeatureType.featureDoubleIsUnspecified(bool)) {
      return EFeatureType.UNSPECIFIED_BOOLEAN;
    }
    return (Math.abs(bool) >= 0.5d) ? Boolean.TRUE : Boolean.FALSE;
  }
}
