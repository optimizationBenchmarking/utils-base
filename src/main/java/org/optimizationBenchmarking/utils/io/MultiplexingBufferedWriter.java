package org.optimizationBenchmarking.utils.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A buffered writer writing its output to a given stream, mainly used for
 * debug purposes.
 */
public final class MultiplexingBufferedWriter extends BufferedWriter {

  /** the destination writer */
  private final BufferedWriter m_writer;

  /** the debug output */
  private final ITextOutput m_textOut;

  /**
   * another auto-closeable to close when the writer is closed,
   * {@code null} if no such action is required
   */
  private final AutoCloseable m_closeOnClose;

  /**
   * Create the multiplexing buffered writer
   *
   * @param writer
   *          the writer
   * @param textOutput
   *          the text output
   * @param closeOnClose
   *          another auto-closeable to close when the writer is closed,
   *          {@code null} if no such action is required: this can be used
   *          to close another writer backing {@code textOutput}
   */
  public MultiplexingBufferedWriter(final BufferedWriter writer,
      final ITextOutput textOutput, final AutoCloseable closeOnClose) {
    super(writer);
    this.m_writer = writer;
    this.m_textOut = textOutput;
    this.m_closeOnClose = closeOnClose;
  }

  /**
   * Create the multiplexing buffered writer
   *
   * @param writer
   *          the writer
   * @param textOutput
   *          the text output
   */
  public MultiplexingBufferedWriter(final BufferedWriter writer,
      final ITextOutput textOutput) {
    this(writer, textOutput, null);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int c) throws IOException {
    try {
      this.m_writer.write(c);
    } finally {
      this.m_textOut.append((char)c);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char[] cbuf, final int off, final int len)
      throws IOException {
    try {
      this.m_writer.write(cbuf, off, len);
    } finally {
      this.m_textOut.append(cbuf, off, len);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char[] cbuf) throws IOException {
    try {
      this.m_writer.write(cbuf);
    } finally {
      this.m_textOut.append(cbuf);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String s, final int off, final int len)
      throws IOException {
    try {
      this.m_writer.write(s, off, len);
    } finally {
      this.m_textOut.append(s, off, len);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq) throws IOException {
    try {
      this.m_writer.append(csq);
    } finally {
      this.m_textOut.append(csq);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq, final int start,
      final int end) throws IOException {

    try {
      this.m_writer.append(csq, start, end);
    } finally {
      this.m_textOut.append(csq, start, end);
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final char c) throws IOException {
    try {
      this.m_writer.append(c);
    } finally {
      this.m_textOut.append(c);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String s) throws IOException {
    try {
      this.m_writer.write(s);
    } finally {
      this.m_textOut.append(s);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void newLine() throws IOException {
    try {
      this.m_writer.newLine();
    } finally {
      this.m_textOut.appendLineBreak();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() throws IOException {
    try {
      this.m_writer.flush();
    } finally {
      this.m_textOut.flush();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    try {
      try {
        super.close();
      } finally {
        this.m_writer.close();
      }
    } finally {
      if (this.m_closeOnClose != null) {
        try {
          this.m_closeOnClose.close();
        } catch (final IOException ioe) {
          throw ioe;
        } catch (final Throwable error) {
          throw new IOException(//
              "Error while closing internal text output.", //$NON-NLS-1$
              error);
        }
      }
    }
  }

}
