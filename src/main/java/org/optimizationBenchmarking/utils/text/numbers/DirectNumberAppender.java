package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender which, well, directly appends the numbers to the
 * output.
 */
public final class DirectNumberAppender extends _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the direct number appender */
  public static final DirectNumberAppender INSTANCE = new DirectNumberAppender();

  /** create */
  private DirectNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(//
        "numbers are printed in their raw form", //$NON-NLS-1$
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "direct";//$NON-NLS-1$
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return DirectNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return DirectNumberAppender.INSTANCE;
  }
}
