package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A parser for {@code short}s.
 */
public class LooseShortParser extends ShortParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the short parser */
  public static final LooseShortParser INSTANCE = new LooseShortParser();

  /** create the parser */
  LooseShortParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final short parseShort(final String string) {
    final short retVal;
    final int val;

    val = LooseIntParser.INSTANCE.parseInt(string);
    if ((val < Short.MIN_VALUE) || (val > Short.MAX_VALUE)) {
      throw new IllegalArgumentException(
          string + (((((" represents value " + val) + //$NON-NLS-1$
              " which is outside of the valid range [") + Short.MIN_VALUE) //$NON-NLS-1$
              + ',') + Short.MAX_VALUE) + " for shorts."); //$NON-NLS-1$
    }

    retVal = ((short) val);
    this.validateShort(retVal);
    return retVal;
  }

  /** {@inheritDoc} */
  @Override
  public final Short parseObject(final Object o) {
    final Short retVal;
    final short ret;

    if (o instanceof Number) {
      if (o instanceof Short) {
        retVal = ((Short) o);
        ret = retVal.shortValue();
      } else {
        ret = ((Number) o).shortValue();
        retVal = null;
      }
    } else {
      return this.parseString(String.valueOf(o));
    }

    this.validateShort(ret);
    return ((retVal != null) ? retVal : Short.valueOf(ret));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return LooseShortParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return LooseShortParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(66666983, //
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()), //
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    final LooseShortParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof LooseShortParser) {
      parser = ((LooseShortParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
          (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
