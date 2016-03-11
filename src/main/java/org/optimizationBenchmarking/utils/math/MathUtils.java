package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** Some utility functions to be used in mathematics code. */
public final class MathUtils {

  /**
   * check whether a number is finite
   *
   * @param d
   *          the number
   * @return {@code true} if it is finite, {@code false} otherwise
   */
  public static final boolean isFinite(final double d) {
    return ((d > Double.NEGATIVE_INFINITY) && //
        (d < Double.POSITIVE_INFINITY) && //
        (d == d));
  }

  /**
   * The number of unique {@code double} values between {@code a} and
   * {@code b}. This method saturates at {@link java.lang.Long#MAX_VALUE},
   * although there may be more steps.
   *
   * @param a
   *          the first {@code double}
   * @param b
   *          the second {@code double}
   * @return the steps between them, or {@code -1} if either value is
   *         {@link Double#NaN} or both are infinities of different signs
   */
  public static final long numbersBetween(final double a, final double b) {
    final long bitsA, res;
    double useA, useB, temp;

    if ((a != a) || (b != b)) { // take are of NaN
      return -1L;
    }
    useA = (a + 0d);
    useB = (b + 0d);
    if (useA > useB) {
      temp = useB;
      useB = useA;
      useA = temp;
    }
    if (useA == useB) {
      return 0L;
    }
    if (useA <= Double.NEGATIVE_INFINITY) {
      return -1L;
    }
    if (useB >= Double.POSITIVE_INFINITY) {
      return -1L;
    }

    if (useA < 0d) {
      bitsA = Double.doubleToRawLongBits(-useA);
      if (useB < 0d) {
        res = (bitsA - Double.doubleToRawLongBits(-useB));
      } else {
        res = (bitsA + Double.doubleToRawLongBits(useB));
      }
    } else {
      res = (Double.doubleToRawLongBits(useB)
          - Double.doubleToRawLongBits(useA));
    }
    if (res < 0L) {
      return Long.MAX_VALUE;
    }
    return res;
  }

  /**
   * The number of unique {@code float} values between {@code a} and
   * {@code b}.
   *
   * @param a
   *          the first {@code float}
   * @param b
   *          the second {@code float}
   * @return the steps between them, or {@code -1} if either value is
   *         {@link Float#NaN} or both are infinities of different signs
   */
  public static final long numbersBetween(final float a, final float b) {
    final long bitsA;
    float useA, useB, temp;

    if ((a != a) || (b != b)) { // take are of NaN
      return -1L;
    }
    useA = (a + 0f);
    useB = (b + 0f);
    if (useA > useB) {
      temp = useB;
      useB = useA;
      useA = temp;
    }
    if (useA == useB) {
      return 0L;
    }
    if (useA <= Float.NEGATIVE_INFINITY) {
      return -1L;
    }
    if (useB >= Float.POSITIVE_INFINITY) {
      return -1L;
    }

    if (useA < 0d) {
      bitsA = Float.floatToRawIntBits(-useA);
      if (useB < 0d) {
        return (bitsA - Float.floatToRawIntBits(-useB));
      }
      return (bitsA + Float.floatToRawIntBits(useB));

    }
    return (((long) (Float.floatToRawIntBits(useB)))
        - Float.floatToRawIntBits(useA));
  }

  /**
   * <p>
   * Compute the greatest common divisor of the absolute value of two
   * numbers, using the "binary gcd" method which avoids division and
   * modulo operations. See Knuth 4.5.2 algorithm B. This algorithm is due
   * to Josef Stein (1961).
   * </p>
   * <p>
   * <em>The implementation is adapted from Apache Commons Math 3.</em> The
   * reason why we don't use their library code here is that I don't want
   * to create a dependency on a whole library for a single function.
   * </p>
   * Special cases:
   * <ul>
   * <li>The invocations {@code gcd(Long.MIN_VALUE, Long.MIN_VALUE)},
   * {@code gcd(Long.MIN_VALUE, 0L)} and {@code gcd(0L, Long.MIN_VALUE)}
   * return {@code -1}, because the result would be 2^63, which is too
   * large for a long value.</li>
   * <li>The result of {@code gcd(x, x)}, {@code gcd(0L, x)} and
   * {@code gcd(x, 0L)} is the absolute value of {@code x}, except for the
   * special cases above.
   * <li>The invocation {@code gcd(0L, 0L)} is the only one which returns
   * {@code 0L}.</li>
   * </ul>
   *
   * @param p
   *          the first number
   * @param q
   *          the second number
   * @return the greatest common divisor, never negative.
   */
  public static final long gcd(final long p, final long q) {
    long u, v, t;
    int k;

    u = p;
    v = q;
    if ((u == 0L) || (v == 0L)) {
      if ((u <= Long.MIN_VALUE) || (v <= Long.MIN_VALUE)) {
        return (-1L);
      }
      return (Math.abs(u) + Math.abs(v));
    }

    // Keep u and v negative, as negative integers range down to -2^63,
    // while positive numbers can only be as large as 2^63-1 (i.e. we can't
    // necessarily negate a negative number without overflow)

    if (u > 0L) {
      u = (-u);
    }
    if (v > 0L) {
      v = (-v);
    }

    // B1. [Find power of 2]
    k = 0;
    while (((u & 1L) == 0L) && ((v & 1L) == 0L) && (k < 63)) {
      // while u and v are both even...
      u /= 2L;
      v /= 2L;
      k++; // cast out twos.
    }
    if (k == 63) {
      return (-1L);
    }

    // B2. Initialize: u and v have been divided by 2^k and at least
    // one is odd.
    t = ((u & 1L) == 1L) ? v : (-(u / 2L));/* B3 */
    // t negative: u was odd, v may be even (t replaces v)
    // t positive: u was even, v is odd (t replaces u)
    do {
      // B4/B3: cast out twos from t.
      while ((t & 1L) == 0L) { // while t is even..
        t /= 2L; // cast out twos
      }
      // B5 [reset max(u,v)]
      if (t > 0L) {
        u = (-t);
      } else {
        v = t;
      }
      // B6/B3. at this point both u and v should be odd.
      t = ((v - u) / 2L);
      // |u| larger: t positive (replace u)
      // |v| larger: t negative (replace v)
    } while (t != 0L);

    return ((-u) * (1L << k)); // gcd is u*2^k
  }

  /** the forbidden constructor */
  private MathUtils() {
    ErrorUtils.doNotCall();
  }
}
