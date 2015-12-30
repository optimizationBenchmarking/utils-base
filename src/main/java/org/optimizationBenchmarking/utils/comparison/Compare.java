package org.optimizationBenchmarking.utils.comparison;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * The basic methods to compare objects.
 */
public final class Compare {

  /**
   * Does one object equal another one.
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return {@code true} if they are equal, {@code false} otherwise
   */
  public static final boolean equals(final Object a, final Object b) {
    if (a == b) {
      return true;
    }
    if (a == null) {
      return false;
    }
    if (b == null) {
      return false;
    }
    return a.equals(b);
  }

  /**
   * Compare whether two comparable objects
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return the comparison result
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final int compare(final Comparable a, final Comparable b) {
    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return (-1);
    }
    return a.compareTo(b);
  }

  /**
   * Compare one object to another one
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return the comparison result
   */
  @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
  public static final int compare(final Object a, final Object b) {
    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return (-1);
    }

    if (a instanceof Comparable) {
      try {
        return ((Comparable) a).compareTo(b);
      } catch (final Throwable error) {
        // ignore
      }
    }

    if (b instanceof Comparable) {
      try {
        return (-(((Comparable) b).compareTo(a)));
      } catch (final Throwable error) {
        // ignore
      }
    }

    if ((a instanceof Number) && (b instanceof Number)) {
      return Compare.compare(((Number) a), ((Number) b));
    }

    return 0;
  }

  /**
   * Compare one {@code double} to another one. This method sets
   * {@code 0d == -0d}, although
   * <code>{@link java.lang.Double#doubleToLongBits(double) Double.doubleToLongBits}(0d)==0</code>
   * while
   * <code>{@link java.lang.Double#doubleToLongBits(double) Double.doubleToLongBits}(-0d)==-9223372036854775808}</code>
   * . <code>equals(0d, -0d) == true</code>. {@link java.lang.Double#NaN}
   * values are treated as equal to each other.
   *
   * @param a
   *          the first {@code double}
   * @param b
   *          the second {@code double}
   * @return {@code -1} if {@code a<b}, {@code 0} if {@code a equals b},
   *         {@code 1} if {@code a>b}
   */
  public static final int compare(final double a, final double b) {
    final boolean aNaN, bNaN;

    if (a < b) {
      return (-1);
    }
    if (a > b) {
      return 1;
    }
    if (a == b) {
      return 0;
    }

    aNaN = (a != a);
    bNaN = (b != b);
    if (aNaN && bNaN) {
      return 0;
    }
    if (aNaN) {
      return 1;
    }
    if (bNaN) {
      return (-1);
    }

    throw new IllegalStateException(//
        ("Impossible error occured: compare " //$NON-NLS-1$
            + (a + " (" //$NON-NLS-1$
                + (Double.doubleToRawLongBits(a) + (") with " + (b + //$NON-NLS-1$
                    (" (" + Double.doubleToRawLongBits(b))))))) //$NON-NLS-1$
            + ')');
  }

  /**
   * Compare one {@code float} to another one. This method sets
   * {@code 0d == -0d}, although
   * <code>{@link java.lang.Float#floatToIntBits(float) Float.floatToLongBits}(0d)==0</code>
   * while
   * <code>{@link java.lang.Float#floatToIntBits(float) Float.floatToLongBits}(-0d)==-9223372036854775808</code>
   * . <code>equals(0d, -0d) == true</code>. {@link java.lang.Float#NaN}
   * values are treated as equal to each other.
   *
   * @param a
   *          the first {@code float}
   * @param b
   *          the second {@code float}
   * @return {@code -1} if {@code a<b}, {@code 0} if {@code a equals b},
   *         {@code 1} if {@code a>b}
   */
  public static final int compare(final float a, final float b) {
    final boolean aNaN, bNaN;

    if (a < b) {
      return (-1);
    }
    if (a > b) {
      return 1;
    }
    if (a == b) {
      return 0;
    }

    aNaN = (a != a);
    bNaN = (b != b);
    if (aNaN && bNaN) {
      return 0;
    }
    if (aNaN) {
      return 1;
    }
    if (bNaN) {
      return (-1);
    }

    throw new IllegalStateException(//
        ("Impossible error occured: compare " //$NON-NLS-1$
            + (a + " (" //$NON-NLS-1$
                + (Float.floatToRawIntBits(a) + (") with " + (b + //$NON-NLS-1$
                    (" (" + Float.floatToRawIntBits(b))))))) //$NON-NLS-1$
            + ')');
  }

  /**
   * Compare two numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return the result
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final int compare(final Number a, final Number b) {

    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return (-1);
    }

    if ((a.getClass() == b.getClass()) && (a instanceof Comparable)) {
      return ((Comparable) a).compareTo(b);
    }

    if (((a instanceof Byte) || //
        (a instanceof Short) || //
        (a instanceof Integer) || //
        (a instanceof Long))//
        && ((b instanceof Byte) || //
            (b instanceof Short) || //
            (b instanceof Integer) || //
            (b instanceof Long))) {
      return Long.compare(a.longValue(), b.longValue());
    }

    return Compare.compare(a.doubleValue(), b.doubleValue());
  }

  /**
   * Check if two {@code double} values are equal. {@code 0} is equal to
   * {@code -0}.
   *
   * @param a
   *          the first value
   * @param b
   *          the second value
   * @return {@code true} if they are equal, {@code false} otherwise
   */
  public static final boolean equals(final double a, final double b) {
    return (Compare.compare(a, b) == 0);
  }

  /**
   * Check if two {@code float} values are equal. {@code 0} is equal to
   * {@code -0}.
   *
   * @param a
   *          the first value
   * @param b
   *          the second value
   * @return {@code true} if they are equal, {@code false} otherwise
   */
  public static final boolean equals(final float a, final float b) {
    return (Compare.compare(a, b) == 0);
  }

  /** the forbidden constructor */
  private Compare() {
    ErrorUtils.doNotCall();
  }
}
