package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.BufferedWriter;
import java.io.IOException;

import org.optimizationBenchmarking.utils.error.RethrowMode;

/**
 * A version of the text output wrapped around an
 * {@link java.io.BufferedWriter}.
 */
final class _BufferedWriterWrappedTextOutput
    extends _WriterWrappedTextOutputBase<BufferedWriter> {

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _BufferedWriterWrappedTextOutput(final BufferedWriter out) {
    super(out);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendLineBreak() {
    try {
      this.m_out.newLine();
    } catch (final IOException ioe) {

      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while trying to append line break to _BufferedWriterWrappedTextOutput.", //$NON-NLS-1$
          true, ioe);
    }
  }
}
