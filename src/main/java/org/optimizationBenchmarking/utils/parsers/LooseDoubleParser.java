package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * A parser for {@code double}s which can also interpret things such as
 * constants.
 */
public class LooseDoubleParser extends DoubleParser {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the negative infinity string */
  public static final String NEGATIVE_INFINITY = "negative infinity"; //$NON-NLS-1$

  /** the nan string */
  public static final String NOT_A_NUMBER = "not a number"; //$NON-NLS-1$

  /** the infinity string */
  public static final String POSITIVE_INFINITY = "infinity"; //$NON-NLS-1$

  /** the globally shared instance of the double parser */
  public static final LooseDoubleParser INSTANCE = new LooseDoubleParser();

  /** create the parser */
  protected LooseDoubleParser() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  public final double parseDouble(final String string) {
    final _PreparedString prep;
    double retVal;
    String str;
    _TextConst cnst;
    Object number;
    int base;

    checker: {

      try {
        // first we try to cast the string directly to a double
        // this will be the fast execution path for 'correctly' formatted
        // numbers
        retVal = java.lang.Double.parseDouble(string);
        break checker;

      } catch (final NumberFormatException origNumberError) {
        // ok, the string does not follow java's default number format
        // so now, we check whether it is hexadecimal/octal/binary
        // formatted, a
        // constant, or even a static member identifier

        prep = new _PreparedString(string);

        outer: while ((str = prep.next()) != null) {
          // we now iterate over the prepared strings: step-by-step, we
          // will
          // try
          // to process the string by, e.g., trimming it; removing +, -, (,
          // ),
          // etc; removing internal white spaces and underscores (to match
          // Java
          // 7's new binary syntax) and so on - until we can either parse
          // it or
          // have to give up

          if (str != string) {
            // try again to parse the string directly
            try {
              retVal = prep.getReturn(java.lang.Double.parseDouble(str));
              break checker;
            } catch (final Throwable canBeIgnored) {
              // we ignore this exception, as we will throw the original
              // one
              // on failure
            }
          }

          // let's see if it was a constant in some other base
          if ((str.length() > 2) && (str.charAt(0) == '0')) {
            if ((base = _PreparedString._getBase(str.charAt(1))) != 0) {
              try {
                retVal = prep.getReturn(
                    java.lang.Long.parseLong(str.substring(2), base));
                break checker;
              } catch (final Throwable canBeIgnored) {
                // we ignore this exception, as we will throw the original
                // one
                // on failure
              }
            }
          }

          // does the string correspond to a constant we know?
          cnst = _TextConst.findConst(str);
          if (cnst != null) {
            if (cnst.hasDouble()) {
              retVal = prep.getReturn(cnst.m_d);
              break checker;
            }
            break outer;
          }

          // ok, it is no constant, maybe it is a public static final
          // member?
          try {
            number = ReflectionUtils.getInstanceByName(Object.class, str);
            if ((number != null) && (number != string)
                && (number != str)) {
              retVal = prep.getReturn(this.__parseObjectRaw(number));
              break checker;
            }
          } catch (final Throwable canBeIgnored) {
            // ignore this exception: it will be thrown if no member fits
            // in which case we will throw the original error anyway at the
            // end
          }
        }

        // ok, all our ideas to resolve the number have failed - throw
        // original
        // error again
        throw origNumberError;
      }
    }

    this.validateDouble(retVal);
    return retVal;
  }

  /**
   * The raw parsing method for calling inside {@link #parseString(String)}
   *
   * @param o
   *          the object
   * @return the return value
   */
  private final double __parseObjectRaw(final Object o) {
    if (o instanceof java.lang.Number) {
      return ((Number) o).doubleValue();
    }

    return this.parseDouble(String.valueOf(o));
  }

  /** {@inheritDoc} */
  @Override
  public final Double parseObject(final Object o) {
    final Double retVal;
    final double ret;

    if (o instanceof Double) {
      retVal = ((Double) o);
      ret = retVal.doubleValue();
    } else {
      ret = this.__parseObjectRaw(o);
      retVal = null;
    }

    this.validateDouble(ret);
    return ((retVal != null) ? retVal : Double.valueOf(ret));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return LooseDoubleParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return LooseDoubleParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(444979, //
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBoundDouble()), //
            HashUtils.hashCode(this.getUpperBoundDouble())));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    final LooseDoubleParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof LooseDoubleParser) {
      parser = ((LooseDoubleParser) other);
      return (EComparison.EQUAL.compare(parser.getLowerBoundDouble(),
          this.getLowerBoundDouble()) && //
          EComparison.EQUAL.compare(parser.getUpperBoundDouble(),
              this.getUpperBoundDouble()));
    }
    return false;
  }
}
