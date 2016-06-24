package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * Appends numbers in text according to some rule.
 */
public final class InTextNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the small numbers */
  static final String[] SMALL_NUMBERS = { "zero", //$NON-NLS-1$
      "one", //$NON-NLS-1$
      "two", //$NON-NLS-1$
      "three", //$NON-NLS-1$
      "four", //$NON-NLS-1$
      "five", //$NON-NLS-1$
      "six", //$NON-NLS-1$
      "seven", //$NON-NLS-1$
      "eight", //$NON-NLS-1$
      "nine", //$NON-NLS-1$
      "ten", //$NON-NLS-1$
      "eleven", //$NON-NLS-1$
      "twelve"//$NON-NLS-1$
  };

  /** the globally shared instance of the in-text number appender */
  public static final InTextNumberAppender INSTANCE = new InTextNumberAppender();

  /** create */
  private InTextNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final long value,
      final ETextCase textCase, final ITextOutput textOut) {
    if ((value >= 0)
        && (value < InTextNumberAppender.SMALL_NUMBERS.length)) {
      return textCase.appendWord(
          InTextNumberAppender.SMALL_NUMBERS[(int) value], textOut);
    }
    textOut.append(value);
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final long value,
      final ETextCase textCase) {
    final String str;
    if ((value >= 0)
        && (value < InTextNumberAppender.SMALL_NUMBERS.length)) {
      str = InTextNumberAppender.SMALL_NUMBERS[(int) value];
      if (textCase == ETextCase.IN_SENTENCE) {
        return str;
      }
      return (textCase.adjustCaseOfFirstCharInWord(str.charAt(0))//
          + str.substring(1));
    }
    return Long.toString(value);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final int value,
      final ETextCase textCase, final ITextOutput textOut) {
    if ((value >= 0)
        && (value < InTextNumberAppender.SMALL_NUMBERS.length)) {
      return textCase.appendWord(//
          InTextNumberAppender.SMALL_NUMBERS[value], textOut);
    }
    textOut.append(value);
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final int value, final ETextCase textCase) {
    final String str;
    if ((value >= 0)
        && (value < InTextNumberAppender.SMALL_NUMBERS.length)) {
      str = InTextNumberAppender.SMALL_NUMBERS[value];
      if (textCase == ETextCase.IN_SENTENCE) {
        return str;
      }
      return (textCase.adjustCaseOfFirstCharInWord(str.charAt(0))//
          + str.substring(1));
    }
    return Integer.toString(value);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double value,
      final ETextCase textCase) {
    final int l;

    if ((value >= 0)
        && (value < InTextNumberAppender.SMALL_NUMBERS.length)) {
      l = ((int) value);
      if (l == value) {
        return this.toString(l, textCase);
      }
    }

    return SimpleNumberAppender.INSTANCE.toString(value, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final double value,
      final ETextCase textCase, final ITextOutput textOut) {
    final int l;

    if ((value >= 0)
        && (value < InTextNumberAppender.SMALL_NUMBERS.length)) {
      l = ((int) value);
      if (l == value) {
        return this.appendTo(l, textCase, textOut);
      }
    }

    return SimpleNumberAppender.INSTANCE.appendTo(value, textCase,
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(//
        "numbers between 0 and 12 (inclusively) are printed as text, the rest are printed normally", //$NON-NLS-1$
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "in-text";//$NON-NLS-1$
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return InTextNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return InTextNumberAppender.INSTANCE;
  }
}