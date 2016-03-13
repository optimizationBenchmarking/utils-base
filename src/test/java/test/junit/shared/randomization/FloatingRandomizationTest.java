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
}
