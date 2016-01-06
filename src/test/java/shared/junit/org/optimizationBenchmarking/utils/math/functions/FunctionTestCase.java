package shared.junit.org.optimizationBenchmarking.utils.math.functions;

import org.optimizationBenchmarking.utils.math.NumericalTypes;

/** a test case for numbers */
public final class FunctionTestCase {

  /** the empty test case */
  static final FunctionTestCase[] EMPTY = new FunctionTestCase[0];

  /** can all numbers be represented as bytes without loss of precision? */
  static final int ALL_AS_BYTE = 1;
  /**
   * can all numbers be represented as shorts without loss of precision?
   */
  static final int ALL_AS_SHORT = (FunctionTestCase.ALL_AS_BYTE << 1);
  /** can all numbers be represented as ints without loss of precision? */
  static final int ALL_AS_INT = (FunctionTestCase.ALL_AS_SHORT << 1);
  /** can all numbers be represented as longs without loss of precision? */
  static final int ALL_AS_LONG = (FunctionTestCase.ALL_AS_INT << 1);
  /**
   * can all numbers be represented as floats without loss of precision?
   */
  static final int ALL_AS_FLOAT = (FunctionTestCase.ALL_AS_LONG << 1);
  /**
   * can all numbers be represented as doubles without loss of precision?
   */
  static final int ALL_AS_DOUBLE = (FunctionTestCase.ALL_AS_FLOAT << 1);
  /**
   * can all input numbers be represented as ints and the output as double
   * without loss of precision?
   */
  static final int IN_INT_OUT_DOUBLE = (FunctionTestCase.ALL_AS_DOUBLE << 1);
  /**
   * can all input numbers be represented as longs and the output as double
   * without loss of precision?
   */
  static final int IN_LONG_OUT_DOUBLE = (FunctionTestCase.IN_INT_OUT_DOUBLE << 1);

  /** the input */
  final Number[] m_in;

  /** the expected output */
  final Number m_out;

  /** the choices */
  final int m_choices;

  /**
   * create a test case based on bytes
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final byte out, final byte... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on shorts
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final short out, final short... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on ints
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final int out, final int... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on longs
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final long out, final long... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on floats
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final float out, final float... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on doubles
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final double out, final double... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on long input and double output
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final double out, final long... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * create a test case based on int input and double output
   *
   * @param out
   *          the expected output
   * @param in
   *          the input
   */
  public FunctionTestCase(final double out, final int... in) {
    super();

    int i;
    this.m_in = new Number[i = in.length];
    for (; (--i) >= 0;) {
      this.m_in[i] = NumericalTypes.valueOf(in[i]);
    }
    this.m_out = NumericalTypes.valueOf(out);
    this.m_choices = FunctionTestCase.__checkChoices(this.m_out,
        this.m_in);
  }

  /**
   * check the possible choices
   *
   * @param out
   *          the output number
   * @param in
   *          the input number
   * @return the choices
   */
  private static final int __checkChoices(final Number out,
      final Number[] in) {
    final int outTypes;
    int inTypes, res, both;

    outTypes = NumericalTypes.getTypes(out);
    inTypes = (-1);
    for (final Number num : in) {
      inTypes &= NumericalTypes.getTypes(num);
    }

    res = 0;
    both = (inTypes & outTypes);
    if ((both & NumericalTypes.IS_BYTE) != 0) {
      res |= FunctionTestCase.ALL_AS_BYTE;
    }
    if ((both & NumericalTypes.IS_SHORT) != 0) {
      res |= FunctionTestCase.ALL_AS_SHORT;
    }
    if ((both & NumericalTypes.IS_INT) != 0) {
      res |= FunctionTestCase.ALL_AS_INT;
    }
    if ((both & NumericalTypes.IS_LONG) != 0) {
      res |= FunctionTestCase.ALL_AS_LONG;
    }
    if ((both & NumericalTypes.IS_FLOAT) != 0) {
      res |= FunctionTestCase.ALL_AS_FLOAT;
    }
    if ((both & NumericalTypes.IS_DOUBLE) != 0) {
      res |= FunctionTestCase.ALL_AS_DOUBLE;
    }

    if (((inTypes & NumericalTypes.IS_LONG) != 0)
        && ((outTypes & NumericalTypes.IS_DOUBLE) != 0)) {
      res |= FunctionTestCase.IN_LONG_OUT_DOUBLE;
    }

    if (((inTypes & NumericalTypes.IS_INT) != 0)
        && ((outTypes & NumericalTypes.IS_DOUBLE) != 0)) {
      res |= FunctionTestCase.IN_INT_OUT_DOUBLE;
    }

    return res;
  }
}
