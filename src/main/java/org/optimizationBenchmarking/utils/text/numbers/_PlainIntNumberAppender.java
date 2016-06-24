package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.IImmutable;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An internal number appender which, well, directly appends the integer
 * numbers to the output.
 */
abstract class _PlainIntNumberAppender extends NumberAppender
    implements IImmutable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _PlainIntNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final long value,
      final ETextCase textCase, final ITextOutput textOut) {
    textOut.append(value);
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final long value,
      final ETextCase textCase) {
    return Long.toString(value);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final int value,
      final ETextCase textCase, final ITextOutput textOut) {
    textOut.append(value);
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final int value, final ETextCase textCase) {
    return Integer.toString(value);
  }
}
