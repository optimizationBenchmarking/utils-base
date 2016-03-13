package test.junit.shared.randomization;

import java.util.HashSet;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.comparison.Compare;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

import shared.junit.InstanceTest;
import shared.randomization.PrimitiveTypeRandomization;

/**
 * test a primitive type randomization
 *
 * @param <T>
 *          the primitive type wrapper class
 * @param <X>
 *          the randomization
 */
@Ignore
public class PrimitiveTypeRandomizationTest<T, X extends PrimitiveTypeRandomization<T>>
    extends InstanceTest<X> {

  /**
   * create
   *
   * @param instance
   *          the test
   */
  protected PrimitiveTypeRandomizationTest(final X instance) {
    super(null, instance, true, false);
  }

  /** test whether we can obtain the basic parser */
  @Test(timeout = 3600000)
  public void testGetBasicParser() {
    Assert.assertNotNull(this.getInstance().getBasicParser());
  }

  /**
   * test whether we can obtain parsers with random bounds
   *
   * @param fullRange
   *          should we use the full range?
   */
  private final void __testGetParserWithRandomBounds(
      final boolean fullRange) {
    final Random random;
    final X instance;
    final HashSet<Object> resSet;
    Object res;

    random = new Random();
    instance = this.getInstance();
    resSet = new HashSet<>();

    for (int i = 10000; (--i) >= 0;) {
      res = instance.getParserWithRandomBounds(fullRange, random);
      Assert.assertNotNull(res);
      resSet.add(res);
    }

    if (instance.getType() == EPrimitiveType.BOOLEAN) {
      Assert.assertFalse(resSet.isEmpty());
    } else {
      Assert.assertTrue(resSet.size() > 100);
    }
  }

  /**
   * test whether we can obtain parsers with random bounds for the full
   * range
   */
  @Test(timeout = 3600000)
  public void testGetParserWithRandomBoundsFullRange() {
    this.__testGetParserWithRandomBounds(true);
  }

  /**
   * test whether we can obtain parsers with random bounds for not the full
   * range
   */
  @Test(timeout = 3600000)
  public void testGetParserWithRandomBoundsNotFullRange() {
    this.__testGetParserWithRandomBounds(false);
  }

  /**
   * test whether we can obtain random values
   *
   * @param fullRange
   *          should we explore the full range?
   */
  private final void __testRandomValue(final boolean fullRange) {
    final Random random;
    final HashSet<T> resSet;
    final X instance;
    T res;

    instance = this.getInstance();
    random = new Random();
    resSet = new HashSet<>();

    for (int i = 10000; (--i) >= 0;) {

      res = instance.randomValue(fullRange, random);
      Assert.assertNotNull(res);
      resSet.add(res);
    }

    Assert.assertTrue(resSet.size() > //
    ((instance.getType() != EPrimitiveType.BOOLEAN) ? 100 : 1));
  }

  /**
   * test whether we can obtain random values from the full range
   */
  @Test(timeout = 3600000)
  public void testRandomValueFullRange() {
    this.__testRandomValue(true);
  }

  /**
   * test whether we can obtain random values not from the full range
   */
  @Test(timeout = 3600000)
  public void testRandomValueNotFullRange() {
    this.__testRandomValue(false);
  }

  /**
   * test whether we can obtain random values from between two other random
   * values
   *
   * @param fullRange
   *          should we use the full range?
   */
  private final void __testRandomValueBetweenTwoOtherRandomValues(
      final boolean fullRange) {
    final Random random;
    final X instance;
    final HashSet<Object> resSet, lowerSet, upperSet;
    int compRes;
    T lower, upper, temp;

    random = new Random();
    instance = this.getInstance();
    resSet = new HashSet<>();
    lowerSet = new HashSet<>();
    upperSet = new HashSet<>();

    for (int i = 10000; (--i) >= 0;) {

      do {
        lower = instance.randomValue(true, random);
        Assert.assertNotNull(lower);
        upper = instance.randomValue(true, random);
        Assert.assertNotNull(upper);
      } while (Compare.equals(lower, upper)
          || ((compRes = Compare.compare(lower, upper)) == 0));

      if (compRes > 0) {
        temp = lower;
        lower = upper;
        upper = temp;
      }
      lowerSet.add(lower);
      upperSet.add(upper);

      temp = instance.randomValueBetween(lower, upper, true, random);
      Assert.assertNotNull(temp);
      Assert.assertTrue(Compare.compare(lower, temp) <= 0);
      Assert.assertTrue(Compare.compare(temp, upper) <= 0);
      if ((!(Compare.equals(lower, temp))) && //
          (!(Compare.equals(upper, temp))) && //
          ((Compare.compare(lower, temp) < 0) || //
              (Compare.compare(temp, upper) < 0))) {
        resSet.add(temp);
      }
    }

    if (instance.getType() != EPrimitiveType.BOOLEAN) {
      if (instance.getType() != EPrimitiveType.BYTE) {
        Assert.assertTrue(resSet.size() > 200);
        Assert.assertTrue(lowerSet.size() > 200);
        Assert.assertTrue(upperSet.size() > 200);
      } else {
        Assert.assertTrue(resSet.size() > 100);
        Assert.assertTrue(lowerSet.size() > 100);
        Assert.assertTrue(upperSet.size() > 100);
      }
    } else {
      Assert.assertFalse(resSet.isEmpty());
      Assert.assertFalse(lowerSet.isEmpty());
      Assert.assertFalse(upperSet.isEmpty());
    }
  }

  /**
   * test whether we can obtain random values from between two other random
   * values for the full range
   */
  @Test(timeout = 3600000)
  public void testRandomValueBetweenTwoOtherRandomValuesFullRange() {
    this.__testRandomValueBetweenTwoOtherRandomValues(true);
  }

  /**
   * test whether we can obtain random values from between two other random
   * values for not the full range
   */
  @Test(timeout = 3600000)
  public void testRandomValueBetweenTwoOtherRandomValuesNotFullRange() {
    this.__testRandomValueBetweenTwoOtherRandomValues(false);
  }

  /**
   * test whether we can obtain random values from between two other random
   * values
   *
   * @param fullRange
   *          should we use the full range?
   */
  private final void __testRandomValueForParser(final boolean fullRange) {
    final Random random;
    final X instance;
    final HashSet<Object> resSet, parserSet;
    Parser<T> parser;
    T temp;

    random = new Random();
    instance = this.getInstance();
    resSet = new HashSet<>();
    parserSet = new HashSet<>();

    for (int i = 1000; (--i) >= 0;) {

      parser = instance.getParserWithRandomBounds(fullRange, random);
      temp = instance.randomValue(parser, fullRange, random);
      Assert.assertNotNull(temp);
      parser.validate(temp);
      resSet.add(temp);
      parserSet.add(parser);
    }

    if (instance.getType() != EPrimitiveType.BOOLEAN) {
      if (instance.getType() != EPrimitiveType.BYTE) {
        Assert.assertTrue(resSet.size() > 200);
        Assert.assertTrue(parserSet.size() > 200);
      } else {
        Assert.assertTrue(resSet.size() > 100);
        Assert.assertTrue(parserSet.size() > 100);
      }
    } else {
      Assert.assertFalse(resSet.isEmpty());
      Assert.assertFalse(parserSet.isEmpty());
    }
  }

  /**
   * test whether we can obtain random values from a parser values for the
   * full range
   */
  @Test(timeout = 3600000)
  public void testRandomValueForParserFullRange() {
    this.__testRandomValueForParser(true);
  }

  /**
   * test whether we can obtain random values from a parser values for not
   * the full range
   */
  @Test(timeout = 3600000)
  public void testRandomValueForParserNotFullRange() {
    this.__testRandomValueForParser(false);
  }
}
