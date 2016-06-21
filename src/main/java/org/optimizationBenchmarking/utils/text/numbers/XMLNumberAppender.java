package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender designed for XML data.
 */
public final class XMLNumberAppender extends _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the simple number appender */
  public static final XMLNumberAppender INSTANCE = new XMLNumberAppender();

  /** create */
  private XMLNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    if (v != v) {
      return "NaN"; //$NON-NLS-1$
    }
    if (v <= Double.NEGATIVE_INFINITY) {
      return "-INF"; //$NON-NLS-1$
    }
    if (v >= Double.POSITIVE_INFINITY) {
      return "+INF"; //$NON-NLS-1$
    }

    return SimpleNumberAppender._simplify(v, String.valueOf(v)).m_string;
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(//
        "numbers are printed in an XML-compatible form", //$NON-NLS-1$
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "xml";//$NON-NLS-1$
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return XMLNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return XMLNumberAppender.INSTANCE;
  }
}
