package org.optimizationBenchmarking.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * Some small utilities for performing I/O
 */
public final class IOUtils {

  /**
   * Copy all data from an input stream to an output stream.
   *
   * @param is
   *          the input stream
   * @param os
   *          the output stream
   * @throws IOException
   *           if i/o fails
   */
  public static final void copy(final InputStream is,
      final OutputStream os) throws IOException {
    IOUtils.copy(is, os, new byte[8192]);
  }

  /**
   * Copy all data from an input stream to an output stream.
   *
   * @param is
   *          the input stream
   * @param os
   *          the output stream
   * @param buffer
   *          the buffer to be used for intermediate reads
   * @throws IOException
   *           if i/o fails
   */
  public static final void copy(final InputStream is,
      final OutputStream os, final byte[] buffer) throws IOException {
    int read;
    while ((read = is.read(buffer)) > 0) {
      os.write(buffer, 0, read);
    }
  }

  /**
   * Copy all data from reader to a writer
   *
   * @param ir
   *          the input reader
   * @param ow
   *          the output writer
   * @throws IOException
   *           if i/o fails
   */
  public static final void copy(final Reader ir, final Writer ow)
      throws IOException {
    IOUtils.copy(ir, ow, new char[8192]);
  }

  /**
   * Copy all data from reader to a writer
   *
   * @param ir
   *          the input reader
   * @param ow
   *          the output writer
   * @param buffer
   *          the buffer to be used for intermediate reads
   * @throws IOException
   *           if i/o fails
   */
  public static final void copy(final Reader ir, final Writer ow,
      final char[] buffer) throws IOException {
    int read;

    while ((read = ir.read(buffer)) > 0) {
      ow.write(buffer, 0, read);
    }
  }

  /** the forbidden constructor */
  private IOUtils() {
    ErrorUtils.doNotCall();
  }
}
