package shared.junit.org.optimizationBenchmarking.utils.text.numbers;

import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.NumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import shared.junit.InstanceTest;

/**
 * A test for number appenders.
 */
@Ignore
public class NumberAppenderTest extends InstanceTest<NumberAppender> {

  /** can we parse? */
  private final boolean m_canParse;

  /**
   * create the number appender test
   *
   * @param instance
   *          the instance
   * @param canParse
   *          can we parse?
   */
  protected NumberAppenderTest(final NumberAppender instance,
      final boolean canParse) {
    this(instance, canParse, true);
  }

  /**
   * create the number appender test
   *
   * @param instance
   *          the instance
   * @param canParse
   *          can we parse?
   * @param isSingleton
   *          is this a singleton
   */
  protected NumberAppenderTest(final NumberAppender instance,
      final boolean canParse, final boolean isSingleton) {
    super(null, instance, isSingleton, false);
    this.m_canParse = canParse;
  }

  /**
   * parse a string to a double
   *
   * @param s
   *          the string
   * @return the double
   */
  protected double parseDouble(final String s) {
    return Double.parseDouble(s);
  }

  /**
   * parse a string to a long
   *
   * @param s
   *          the string
   * @return the long
   */
  protected long parseLong(final String s) {
    return Long.parseLong(s);
  }

  /** test the long output */
  @Test(timeout = 3600000)
  public void testLongDet() {
    final MemoryTextOutput m;
    final NumberAppender n;
    long l;
    String s;

    n = this.getInstance();
    m = new MemoryTextOutput();

    for (final ETextCase c : ETextCase.values()) {

      m.clear();
      l = Long.MIN_VALUE;
      n.appendTo(l, c, m);
      Assert.assertEquals(n.toString(l, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(l, this.parseLong(s));
      }

      m.clear();
      l = Long.MAX_VALUE;
      n.appendTo(l, c, m);
      Assert.assertEquals(n.toString(l, c), s = m.toString());
      if (this.m_canParse) {
        Assert.assertEquals(l, this.parseLong(s));
      }

      for (long ll = -1000L; ll <= 1000L; ll++) {
        m.clear();
        n.appendTo(ll, c, m);
        Assert.assertEquals(n.toString(ll, c), s = m.toString());
        if (this.m_canParse) {
          Assert.assertEquals(ll, this.parseLong(s));
        }
      }
    }
  }

  /** test the int output */
  @Test(timeout = 3600000)
  public void testIntDet() {
    final MemoryTextOutput memoryTextOutput;
    final NumberAppender numberAppender;
    String string;
    int integer;

    numberAppender = this.getInstance();
    memoryTextOutput = new MemoryTextOutput();

    for (final ETextCase textCase : ETextCase.values()) {

      memoryTextOutput.clear();
      integer = Integer.MIN_VALUE;
      numberAppender.appendTo(integer, textCase, memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(integer, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseLong(string), integer);
      }

      memoryTextOutput.clear();
      integer = Integer.MAX_VALUE;
      numberAppender.appendTo(integer, textCase, memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(integer, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseLong(string), integer);
      }

      for (int longValue = -1000; longValue <= 1000; longValue++) {
        memoryTextOutput.clear();
        numberAppender.appendTo(longValue, textCase, memoryTextOutput);
        Assert.assertEquals(numberAppender.toString(longValue, textCase),
            string = memoryTextOutput.toString());
        if (this.m_canParse) {
          Assert.assertEquals(longValue, this.parseLong(string));
        }
      }
    }
  }

  /** test the double output */
  @Test(timeout = 3600000)
  public void testDoubleDet() {
    final MemoryTextOutput memoryTextOutput;
    final NumberAppender numberAppender;
    double value;
    String string;

    numberAppender = this.getInstance();
    memoryTextOutput = new MemoryTextOutput();

    for (final ETextCase textCase : ETextCase.values()) {

      memoryTextOutput.clear();
      numberAppender.appendTo(value = Double.MIN_VALUE, textCase,
          memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(value, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
      }

      memoryTextOutput.clear();
      numberAppender.appendTo(value = Double.MAX_VALUE, textCase,
          memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(value, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
      }

      memoryTextOutput.clear();
      numberAppender.appendTo(value = Double.POSITIVE_INFINITY, textCase,
          memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(value, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
      }

      memoryTextOutput.clear();
      numberAppender.appendTo(value = Double.NEGATIVE_INFINITY, textCase,
          memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(value, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
      }

      memoryTextOutput.clear();
      numberAppender.appendTo(value = Double.NaN, textCase,
          memoryTextOutput);
      Assert.assertEquals(numberAppender.toString(value, textCase),
          string = memoryTextOutput.toString());
      if (this.m_canParse) {
        value = this.parseDouble(string);
        Assert.assertTrue(value != value);
      }

      for (double longValue = -100; longValue <= 100; longValue++) {
        if (longValue >= 0) {
          memoryTextOutput.clear();
          value = Math.sqrt(longValue);
          numberAppender.appendTo(value, textCase, memoryTextOutput);
          Assert.assertEquals(numberAppender.toString(value, textCase),
              string = memoryTextOutput.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
          }

          memoryTextOutput.clear();
          value = Math.log(longValue);
          numberAppender.appendTo(value, textCase, memoryTextOutput);
          Assert.assertEquals(numberAppender.toString(value, textCase),
              string = memoryTextOutput.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
          }
        }

        memoryTextOutput.clear();
        value = Math.exp(longValue);
        numberAppender.appendTo(value, textCase, memoryTextOutput);
        Assert.assertEquals(numberAppender.toString(value, textCase),
            string = memoryTextOutput.toString());
        if (this.m_canParse) {
          Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
        }

        for (double z = -100; z <= 100; z++) {
          memoryTextOutput.clear();
          value = (longValue / z);
          numberAppender.appendTo(value, textCase, memoryTextOutput);
          Assert.assertEquals(numberAppender.toString(value, textCase),
              string = memoryTextOutput.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
          }

          memoryTextOutput.clear();
          value = Math.exp(longValue);
          numberAppender.appendTo(value, textCase, memoryTextOutput);
          Assert.assertEquals(numberAppender.toString(value, textCase),
              string = memoryTextOutput.toString());
          if (this.m_canParse) {
            Assert.assertEquals(this.parseDouble(string), value, 1e-15d);
          }
        }
      }
    }
  }

  /**
   * Some {@code double}s have an overly long string representation in
   * Java. &quot;{@code -7.66eE22}&quot;, for instance, will be represented
   * as &quot;{@code -7.664000000000001E22}&quot; by
   * {@link java.lang.Double#toString(double)}. This method tests what
   * happens if we generate such {@code double}s and feed them to the
   * {@link org.optimizationBenchmarking.utils.text.numbers.NumberAppender#toString(double, ETextCase)}
   * method of the
   * {@link org.optimizationBenchmarking.utils.text.numbers.NumberAppender}
   * .
   */
  @Test(timeout = 3600000)
  public void testOverlyLongDoubleToText() {
    final Random r;
    final NumberAppender ap;
    int i;
    double a, b;

    if (!(this.m_canParse)) {
      return;
    }

    ap = this.getInstance();
    r = new Random();
    for (i = 1; i <= 1000; i++) {
      a = NumberAppenderTest.__makeDouble(r);
      b = Double.parseDouble(ap.toString(a, ETextCase.IN_SENTENCE));
      Assert.assertEquals(a, b, 1e-15d);
    }
  }

  /**
   * Create a double which has a long representation
   *
   * @param r
   *          the random number generator
   * @return the double number
   */
  private static final double __makeDouble(final Random r) {
    final MemoryTextOutput sb;
    String s, t;
    double d;
    int dotIdx, i, l1, l2, i1, i2;

    sb = new MemoryTextOutput(32);

    for (i = 0; i < 100000; i++) {
      if (r.nextBoolean()) {
        sb.append('-');
      }
      while ((sb.length() <= 7) && r.nextBoolean()) {
        sb.append(r.nextInt(10));
      }

      sb.append(1 + r.nextInt(9));
      if (sb.length() < 10) {
        dotIdx = sb.length();
        sb.append('.');
        while ((sb.length() <= 10) && r.nextBoolean()) {
          sb.append(r.nextInt(10));
        }
        sb.append(1 + r.nextInt(9));
      } else {
        dotIdx = -1;
      }

      sb.append('E');
      if ((dotIdx == 1) && r.nextBoolean()) {
        sb.append('-');
      }
      sb.append(r.nextInt(300));

      s = sb.toString();
      sb.clear();
      d = Double.parseDouble(s);
      t = Double.toString(d);
      l1 = s.length();
      l2 = t.length();
      if ((l1 < l2) && (s.indexOf('.') == t.indexOf('.'))) {
        i1 = s.indexOf('E');
        i2 = t.indexOf('E');
        if (((i1 < 0) && (i2 < 0)) || //
            ((i2 > 0) && (i2 > 0) && ((l1 - i1) == (l2 - i2)))) {
          return d;
        }
      }
    }

    return r.nextDouble();
  }
}
