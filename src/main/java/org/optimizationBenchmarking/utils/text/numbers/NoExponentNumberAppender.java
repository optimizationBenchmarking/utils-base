package org.optimizationBenchmarking.utils.text.numbers;

import java.math.BigDecimal;

import org.optimizationBenchmarking.utils.comparison.Compare;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender which appends the numbers directly to the output, but
 * tries to simplify {@code double} numbers via the heuristic specified by
 * {@link org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender}
 * and finally removes all exponents.
 */
public final class NoExponentNumberAppender
    extends _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the no exponent number appender */
  public static final NoExponentNumberAppender INSTANCE = new NoExponentNumberAppender();

  /** create */
  private NoExponentNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final double value,
      final ETextCase textCase, final ITextOutput textOut) {
    if ((!(MathUtils.isFinite(value)))
        && NumberAppender.shouldTextOutputHandleNonFiniteValues(textOut)) {
      textOut.append(value);
    } else {
      textOut.append(this.toString(value, textCase));
    }
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double value,
      final ETextCase textCase) {
    final long l;
    int compareChoices;
    String string;
    _NumberString numberString1, numberString2;
    BigDecimal choice1, choice2;

    if ((value >= Long.MIN_VALUE) && (value <= Long.MAX_VALUE)) {
      l = ((long) value);
      if (l == value) {
        string = Long.toString(l);
        if (Double.parseDouble(string) == value) {// this should always be
                                                  // true
          return string; // but let'string better check it out
        }
      }
    }

    string = Double.toString(value);
    if ((value != value) || (value <= Double.NEGATIVE_INFINITY)
        || (value >= Double.POSITIVE_INFINITY)) {
      return string;
    }

    numberString1 = SimpleNumberAppender._simplify(value, string);
    if (!(numberString1.m_hasE)) {
      return numberString1.m_string;
    }

    choice1 = new BigDecimal(value);
    choice2 = new BigDecimal(numberString1.m_string);

    compareChoices = Compare.compare(
        Math.abs(choice1.doubleValue() - value),
        Math.abs(choice2.doubleValue() - value));

    if (compareChoices < 0) {
      return choice1.toPlainString();
    }
    if (compareChoices > 0) {
      return choice2.toPlainString();
    }

    numberString1 = new _NumberString(choice1.toPlainString());
    numberString2 = new _NumberString(choice2.toPlainString());

    if (numberString1.compareTo(numberString2) <= 0) {
      return numberString1.m_string;
    }
    return numberString2.m_string;
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return NoExponentNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return NoExponentNumberAppender.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(//
        "numbers are printed in normal form, but never in scientific notation", //$NON-NLS-1$
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "normal without exponent";//$NON-NLS-1$
  }
}
