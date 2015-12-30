package test.junit.org.optimizationBenchmarking.utils.math;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.MathUtils;

/**
 * A test for math utils
 */
public class MathUtilsTest {

  /** the constructor */
  public MathUtilsTest() {
    super();
  }

  /** test the is-finite method */
  @Test(timeout = 3600000)
  public void testIsFinite() {
    Assert.assertTrue(MathUtils.isFinite(1d));
    Assert.assertTrue(MathUtils.isFinite(0d));
    Assert.assertTrue(MathUtils.isFinite(-1d));
    Assert.assertTrue(MathUtils.isFinite(Double.MAX_VALUE));
    Assert.assertTrue(MathUtils.isFinite(Double.MIN_NORMAL));
    Assert.assertTrue(MathUtils.isFinite(Double.MIN_NORMAL));
    Assert.assertFalse(MathUtils.isFinite(Double.NaN));
    Assert.assertFalse(MathUtils.isFinite(Double.NEGATIVE_INFINITY));
    Assert.assertFalse(MathUtils.isFinite(Double.POSITIVE_INFINITY));
    Assert.assertFalse(MathUtils.isFinite(1d / 0d));
    Assert.assertFalse(MathUtils.isFinite(-1d / 0d));
    Assert.assertFalse(MathUtils.isFinite(0d / 0d));
  }

  /** test numbers between two values between two values */
  @Test(timeout = 3600000)
  public void testNumbersBetweenTwoValues() {
    final Random random;
    double start, end;
    int starts, iteration;

    random = new Random();

    for (starts = 333; (--starts) >= 0;) {
      end = start = -(1d / Math.log(1d - random.nextDouble()));
      for (iteration = 0; iteration < 3333; iteration++) {
        Assert.assertEquals(iteration,
            MathUtils.numbersBetween(start, end));
        Assert.assertEquals(iteration,
            MathUtils.numbersBetween(end, start));
        end = Math.nextUp(end);
      }
    }
  }

  /**
   * test the "step" numbersBetween of two values, one of which is
   * negative, the other one being positive
   */
  @Test(timeout = 3600000)
  public void testNumbersBetweenTwoValuesOfDifferentSign() {
    double start, end;
    int iteration;

    end = start = 0d;
    for (iteration = 0; iteration < 333333; iteration++) {
      Assert.assertEquals(
          (MathUtils.numbersBetween(start, 0d) + //
              MathUtils.numbersBetween(0d, end)),
          MathUtils.numbersBetween(start, end));
      Assert.assertEquals(
          (MathUtils.numbersBetween(start, 0d) + //
              MathUtils.numbersBetween(0d, end)),
          MathUtils.numbersBetween(end, start));
      start = Math.nextAfter(start, Double.NEGATIVE_INFINITY);
      end = Math.nextUp(end);
    }
  }

  /** test some basic cases of the numbers between two values */
  @Test(timeout = 3600000)
  public void testNumbersBetweenTwoValuesBasic() {
    Assert.assertEquals(MathUtils.numbersBetween(1d, Math.nextUp(1d)), 1L);
    Assert.assertEquals(MathUtils.numbersBetween(1d,
        Math.nextAfter(1d, Double.NEGATIVE_INFINITY)), 1L);
    Assert.assertEquals(MathUtils.numbersBetween(Math.nextUp(1d), 1d), 1L);
    Assert.assertEquals(MathUtils.numbersBetween(
        Math.nextAfter(1d, Double.NEGATIVE_INFINITY), 1d), 1L);
  }

  /** test the border cases of the numbers between two values */
  @Test(timeout = 3600000)
  public void testNumbersBetweenTwoValuesBorderCases() {
    Assert.assertEquals(0L, MathUtils.numbersBetween(0d, 0d));
    Assert.assertEquals(0L, MathUtils.numbersBetween(0d, -0d));
    Assert.assertEquals(0L, MathUtils.numbersBetween(-0d, 0d));
    Assert.assertEquals(0L, MathUtils.numbersBetween(-0d, -0d));

    Assert.assertEquals(1L,
        MathUtils.numbersBetween(0d, Double.MIN_VALUE));
    Assert.assertEquals(1L,
        MathUtils.numbersBetween(Double.MIN_VALUE, 0d));
    Assert.assertEquals(1L,
        MathUtils.numbersBetween(-0d, Double.MIN_VALUE));
    Assert.assertEquals(1L,
        MathUtils.numbersBetween(Double.MIN_VALUE, -0d));

    Assert.assertEquals(1L,
        MathUtils.numbersBetween(0d, -Double.MIN_VALUE));
    Assert.assertEquals(1L,
        MathUtils.numbersBetween(-Double.MIN_VALUE, 0d));
    Assert.assertEquals(1L,
        MathUtils.numbersBetween(-0d, -Double.MIN_VALUE));
    Assert.assertEquals(1L,
        MathUtils.numbersBetween(-Double.MIN_VALUE, -0d));

    Assert.assertEquals(2L,
        MathUtils.numbersBetween(Double.MIN_VALUE, -Double.MIN_VALUE));
    Assert.assertEquals(2L,
        MathUtils.numbersBetween(-Double.MIN_VALUE, Double.MIN_VALUE));

    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(0d, Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(Double.MIN_NORMAL, 0d));
    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(-0d, Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(Double.MIN_NORMAL, -0d));

    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(0d, -Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(-Double.MIN_NORMAL, 0d));
    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(-0d, -Double.MIN_NORMAL));
    Assert.assertEquals((1L << 52L),
        MathUtils.numbersBetween(-Double.MIN_NORMAL, -0d));

    Assert.assertEquals((2L << 52L),
        MathUtils.numbersBetween(Double.MIN_NORMAL, -Double.MIN_NORMAL));
    Assert.assertEquals((2L << 52L),
        MathUtils.numbersBetween(-Double.MIN_NORMAL, Double.MIN_NORMAL));

    Assert.assertEquals(0L, MathUtils.numbersBetween(
        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    Assert.assertEquals(0L, MathUtils.numbersBetween(
        Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(
        Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(
        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NaN, Double.NaN));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.POSITIVE_INFINITY, Double.NaN));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NEGATIVE_INFINITY, Double.NaN));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NaN, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NaN, Double.NEGATIVE_INFINITY));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(0d, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(0d, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(0d, Double.NaN));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.POSITIVE_INFINITY, 0d));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NEGATIVE_INFINITY, 0d));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(Double.NaN, 0d));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(1d, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(1d, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(1d, Double.NaN));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.POSITIVE_INFINITY, 1d));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NEGATIVE_INFINITY, 1d));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(Double.NaN, 1d));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(-1d, Double.NEGATIVE_INFINITY));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(-1d, Double.POSITIVE_INFINITY));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(-1d, Double.NaN));

    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.POSITIVE_INFINITY, -1d));
    Assert.assertEquals(-1L,
        MathUtils.numbersBetween(Double.NEGATIVE_INFINITY, -1d));
    Assert.assertEquals(-1L, MathUtils.numbersBetween(Double.NaN, -1d));
  }

  /** test some basic cases of the GCD */
  @Test(timeout = 3600000)
  public void testGCD() {
    Assert.assertEquals(1L, MathUtils.gcd(1L, 1L));
    Assert.assertEquals(1L, MathUtils.gcd(1L, -1L));
    Assert.assertEquals(1L, MathUtils.gcd(1L, 10L));
    Assert.assertEquals(1L, MathUtils.gcd(-10L, 1L));

    Assert.assertEquals(1L, MathUtils.gcd(3L, 2L));
    Assert.assertEquals(1L, MathUtils.gcd(3L, -2L));
    Assert.assertEquals(1L, MathUtils.gcd(-3L, 2L));
    Assert.assertEquals(1L, MathUtils.gcd(-3L, -2L));

    Assert.assertEquals(5L, MathUtils.gcd(2345345L, -300L));
    Assert.assertEquals(1L, MathUtils.gcd(2345345L, -3333102L));
    Assert.assertEquals(6L, MathUtils.gcd(-2345322L, 3333102L));
    Assert.assertEquals(42L, MathUtils.gcd(-2345322L, 222222L));

    Assert.assertEquals(Long.MAX_VALUE,
        MathUtils.gcd(Long.MAX_VALUE, Long.MAX_VALUE));
    Assert.assertEquals(Long.MAX_VALUE - 1L,
        MathUtils.gcd(Long.MAX_VALUE - 1L, Long.MAX_VALUE - 1L));
  }
}
