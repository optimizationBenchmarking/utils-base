package test.junit.shared.randomization;

import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import shared.randomization.PrimitiveTypeRandomization;

/**
 * test a primitive integer type randomization
 *
 * @param <T>
 *          the primitive type wrapper class
 * @param <X>
 *          the randomization
 */
@Ignore
public abstract class IntegerRandomizationTest<T extends Number, X extends PrimitiveTypeRandomization<T>>
    extends PrimitiveTypeRandomizationTest<T, X> {

  /**
   * create
   *
   * @param instance
   *          the test
   */
  protected IntegerRandomizationTest(final X instance) {
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
  protected abstract long numberBetween(final long lower, final long upper,
      final Random random);

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
  protected abstract long numberBetween(final long bound1,
      final boolean bound1Inclusive, final long bound2,
      final boolean bound2Inclusive, final Random random);

  /**
   * get the lower bound
   *
   * @return the lower bound
   */
  protected abstract long lowerBound();

  /**
   * get the upper bound
   *
   * @return the upper bound
   */
  protected abstract long upperBound();

  /**
   * test obtaining random integers in a specific range
   *
   * @param lower
   *          the lower bound
   * @param upper
   *          the upper bound
   */
  private final void __testRandomIntegerBetween(final long lower,
      final long upper) {
    final int[] counters;
    final int range, total;
    final Random random;
    int looper;

    range = (int) ((upper - lower) + 1L);
    total = 1000 * range;
    counters = new int[range];
    random = new Random();
    for (looper = total; (--looper) >= 0;) {
      counters[(int) (this.numberBetween(lower, upper, random) - lower)]++;
    }

    for (final int counter : counters) {
      Assert.assertTrue(counter > 10);
      if (range > 1) {
        Assert.assertTrue(counter < total);
      }
    }
  }

  /** test the interval 0..100 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_100() {
    this.__testRandomIntegerBetween(0L, 100L);
  }

  /** test the interval -100..100 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_m100_100() {
    this.__testRandomIntegerBetween(-100L, 100L);
  }

  /** test the interval 0..1 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_1() {
    this.__testRandomIntegerBetween(0L, 1L);
  }

  /** test the interval 120...120 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_120_120() {
    this.__testRandomIntegerBetween(120L, 120L);
  }

  /** test the interval min..min */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_min_min() {
    this.__testRandomIntegerBetween(this.lowerBound(), this.lowerBound());
  }

  /** test the interval min..min+1 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_min_minp1() {
    this.__testRandomIntegerBetween(this.lowerBound(),
        this.lowerBound() + 1L);
  }

  /** test the interval max..max */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_max_max() {
    this.__testRandomIntegerBetween(this.upperBound(), this.upperBound());
  }

  /** test the interval max-31..max */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_maxm31_max() {
    this.__testRandomIntegerBetween(this.upperBound() - 31L,
        this.upperBound());
  }

  /**
   * test the random values between two unordered bounds, which might both
   * be either inclusive or exclusive
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
  private final void testRandomIntegerBetween(final long bound1,
      final boolean bound1Inclusive, final long bound2,
      final boolean bound2Inclusive) {
    final int[] counters;
    final int range, total;
    long upper, lower;
    final Random random;
    int looper;

    if (bound1 <= bound2) {
      lower = (bound1Inclusive ? bound1 : (bound1 + 1L));
      upper = (bound2Inclusive ? bound2 : (bound2 - 1L));
    } else {
      lower = (bound2Inclusive ? bound2 : (bound2 + 1L));
      upper = (bound1Inclusive ? bound1 : (bound1 - 1L));
    }

    range = (int) ((upper - lower) + 1L);
    total = 1000 * range;
    counters = new int[range];
    random = new Random();
    for (looper = total; (--looper) >= 0;) {
      counters[(int) (this.numberBetween(bound1, bound1Inclusive, bound2,
          bound2Inclusive, random) - lower)]++;
    }

    for (final int counter : counters) {
      Assert.assertTrue(counter > 10);
      if (range > 1) {
        Assert.assertTrue(counter < total);
      }
    }
  }

  /** test the interval 0..100 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_true_100_true() {
    this.testRandomIntegerBetween(0L, true, 100L, true);
  }

  /** test the interval 0..100 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_true_100_false() {
    this.testRandomIntegerBetween(0L, true, 100L, false);
  }

  /** test the interval 0..100 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_100_true() {
    this.testRandomIntegerBetween(0L, false, 100L, true);
  }

  /** test the interval 0..100 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_100_false() {
    this.testRandomIntegerBetween(0L, true, 100L, true);
  }

  /** test the interval 100..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_100_true_0_true() {
    this.testRandomIntegerBetween(100L, true, 0L, true);
  }

  /** test the interval 100..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_100_true_0_false() {
    this.testRandomIntegerBetween(100L, true, 0L, false);
  }

  /** test the interval 100..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_100_false_0_true() {
    this.testRandomIntegerBetween(100L, false, 0L, true);
  }

  /** test the interval 100..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_100_false_0_false() {
    this.testRandomIntegerBetween(100L, true, 0L, true);
  }

  /** test the interval 0..2 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_true_2_false() {
    this.testRandomIntegerBetween(0L, true, 2L, false);
  }

  /** test the interval 0..2 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_2_true() {
    this.testRandomIntegerBetween(0L, false, 2L, true);
  }

  /** test the interval 0..2 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_0_false_2_false() {
    this.testRandomIntegerBetween(0L, true, 2L, true);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_2_true_0_true() {
    this.testRandomIntegerBetween(2L, true, 0L, true);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_2_true_0_false() {
    this.testRandomIntegerBetween(2L, true, 0L, false);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_2_false_0_true() {
    this.testRandomIntegerBetween(2L, false, 0L, true);
  }

  /** test the interval 2..0 */
  @Test(timeout = 3600000)
  public void testRandomIntsInterval_2_false_0_false() {
    this.testRandomIntegerBetween(2L, true, 0L, true);
  }
}
