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
}
