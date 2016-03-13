package test.junit.shared.randomization;

import java.util.HashSet;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import shared.randomization.PrimitiveTypeRandomization;

/**
 * test a primitive floating point type randomization
 *
 * @param <T>
 *          the primitive type wrapper class
 * @param <X>
 *          the randomization
 */
@Ignore
public abstract class FloatingRandomizationTest<T extends Number, X extends PrimitiveTypeRandomization<T>>
    extends PrimitiveTypeRandomizationTest<T, X> {

  /**
   * create
   *
   * @param instance
   *          the test
   */
  protected FloatingRandomizationTest(final X instance) {
    super(instance);
  }

  /**
   * create a random number between two bounds
   *
   * @param lower
   *          the lower bound
   * @param upper
   *          the upper bound
   * @param random
   *          the random number generator
   * @return the number
   */
  protected abstract double numberBetween(final double lower,
      final double upper, final Random random);

  /**
   * get the lower bound
   *
   * @return the lower bound
   */
  protected abstract double lowerBound();

  /**
   * get the upper bound
   *
   * @return the upper bound
   */
  protected abstract double upperBound();

  /**
   * Get a random value between two unordered bounds, which might both be
   * either inclusive or exclusive
   *
   * @param bound1
   *          the first bound
   * @param bound1Inclusive
   *          inclusive property of first bound: {@code true} for
   *          inclusive, {@code false} for exclusive
   * @param bound2
   *          the second bound
   * @param bound2Inclusive
   *          inclusive property of second bound: {@code true} for
   *          inclusive, {@code false} for exclusive
   * @param random
   *          the random number generator
   * @return the value, or {@code null} if too many trials attempting to
   *         create the value have failed
   * @throws IllegalArgumentException
   *           if the bounds are invalid
   */
  protected abstract double numberBetween(final double bound1,
      final boolean bound1Inclusive, final double bound2,
      final boolean bound2Inclusive, final Random random);

  /**
   * get the next higher number
   *
   * @param d
   *          the number
   * @return the higher number
   */
  protected double nextUp(final double d) {
    return Math.nextUp(d);
  }

  /**
   * get the next lower number
   *
   * @param d
   *          the number
   * @return the lower number
   */
  protected double nextDown(final double d) {
    return Math.nextAfter(d, Double.NEGATIVE_INFINITY);
  }

  /**
   * Get a random value between two unordered bounds, which might both be
   * either inclusive or exclusive
   *
   * @param bound1
   *          the first bound
   * @param bound1Inclusive
   *          inclusive property of first bound: {@code true} for
   *          inclusive, {@code false} for exclusive
   * @param bound2
   *          the second bound
   * @param bound2Inclusive
   *          inclusive property of second bound: {@code true} for
   *          inclusive, {@code false} for exclusive
   */
  private final void __testRandomFloatBetween(final double bound1,
      final boolean bound1Inclusive, final double bound2,
      final boolean bound2Inclusive) {
    final HashSet<Double> set;
    final Random random;
    final double lower, upper;
    double res;
    int looper;

    if (bound1 <= bound2) {
      lower = (bound1Inclusive ? bound1 : this.nextUp(bound1));
      upper = (bound2Inclusive ? bound2 : this.nextDown(bound2));
    } else {
      lower = (bound2Inclusive ? bound2 : this.nextUp(bound2));
      upper = (bound1Inclusive ? bound1 : this.nextDown(bound1));
    }

    set = new HashSet<>();
    random = new Random();
    for (looper = 100000; (--looper) >= 0;) {
      res = this.numberBetween(bound1, bound1Inclusive, bound2,
          bound2Inclusive, random);
      Assert.assertTrue(res >= lower);
      Assert.assertTrue(res <= upper);
      set.add(Double.valueOf(res));
    }

    if (lower < upper) {
      Assert.assertTrue(set.size() >= 100);
    } else {
      Assert.assertTrue(!(set.isEmpty()));
    }
  }

  /**
   * test obtaining random floats in a specific range
   *
   * @param lower
   *          the lower bound
   * @param upper
   *          the upper bound
   */
  private final void __testRandomFloatBetween(final double lower,
      final double upper) {
    final HashSet<Double> set;
    final Random random;
    double res;
    int looper;

    set = new HashSet<>();
    random = new Random();
    for (looper = 100000; (--looper) >= 0;) {
      res = this.numberBetween(lower, upper, random);
      Assert.assertTrue(res >= lower);
      Assert.assertTrue(res <= upper);
      set.add(Double.valueOf(res));
    }

    if (lower < upper) {
      Assert.assertTrue(set.size() >= 100);
    } else {
      Assert.assertTrue(!(set.isEmpty()));
    }
  }

  /** test the interval 0..100 */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_0_100() {
    this.__testRandomFloatBetween(0d, 100d);
  }

  /** test the interval -100..100 */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_m100_100() {
    this.__testRandomFloatBetween(-100d, 100d);
  }

  /** test the interval 0..1 */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_0_1() {
    this.__testRandomFloatBetween(0d, 1d);
  }

  /** test the interval 120...120 */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_120_120() {
    this.__testRandomFloatBetween(120d, 120d);
  }

  /** test the interval min..min */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_min_min() {
    this.__testRandomFloatBetween(this.lowerBound(), this.lowerBound());
  }

  /** test the interval min..min*0.9 */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_min_minm09() {
    this.__testRandomFloatBetween(this.lowerBound(),
        this.lowerBound() * 0.9d);
  }

  /** test the interval max..max */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_max_max() {
    this.__testRandomFloatBetween(this.upperBound(), this.upperBound());
  }

  /** test the interval max*0.97..max */
  @Test(timeout = 3600000)
  public void testRandomFloatsInterval_maxm097_max() {
    this.__testRandomFloatBetween(this.upperBound() * 0.97d,
        this.upperBound());
  }

  /** test the interval 0..2em6 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_true_1em8em6_true() {
    this.__testRandomFloatBetween(0d, true, 2e-6d, true);
  }

  /** test the interval 0..2em6 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_true_1em8em6_false() {
    this.__testRandomFloatBetween(0d, true, 2e-6d, false);
  }

  /** test the interval 0..2em6 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_1em8em6_true() {
    this.__testRandomFloatBetween(0d, false, 2e-6d, true);
  }

  /** test the interval 0..2em6 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_1em8em6_false() {
    this.__testRandomFloatBetween(0d, true, 2e-6d, true);
  }

  /** test the interval 2em6..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8em6_true_0_true() {
    this.__testRandomFloatBetween(2e-6d, true, 0d, true);
  }

  /** test the interval 2em6..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8em6_true_0_false() {
    this.__testRandomFloatBetween(2e-6d, true, 0d, false);
  }

  /** test the interval 2em6..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8em6_false_0_true() {
    this.__testRandomFloatBetween(2e-6d, false, 0d, true);
  }

  /** test the interval 2em6..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8em6_false_0_false() {
    this.__testRandomFloatBetween(2e-6d, true, 0d, true);
  }

  /** test the interval 0..2 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_true_1em8_false() {
    this.__testRandomFloatBetween(0d, true, 1e-8d, false);
  }

  /** test the interval 0..2 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_1em8_true() {
    this.__testRandomFloatBetween(0d, false, 1e-8d, true);
  }

  /** test the interval 0..2 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_1em8_false() {
    this.__testRandomFloatBetween(0d, true, 1e-8d, true);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8_true_0_true() {
    this.__testRandomFloatBetween(1e-8d, true, 0d, true);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8_true_0_false() {
    this.__testRandomFloatBetween(1e-8d, true, 0d, false);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8_false_0_true() {
    this.__testRandomFloatBetween(1e-8d, false, 0d, true);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_1em8_false_0_false() {
    this.__testRandomFloatBetween(1e-8d, true, 0d, true);
  }
}
