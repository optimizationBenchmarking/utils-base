package examples.org.optimizationBenchmarking.utils.tools.impl;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.AbstractFileProducerListener;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerTool;

/**
 * A base class for examples with file producer tools.
 *
 * @param <FP>
 *          the file producer
 */
public abstract class FileProducerExample<FP extends IFileProducerTool>
    implements Runnable {

  /** the unique ids */
  private static final AtomicLong UNIQUE_IDS = new AtomicLong(0);

  /** the file producer listener, never {@code null} */
  private final IFileProducerListener m_listener;

  /** the destination directory, never {@code null} */
  private final Path m_destDir;

  /** the file producer, may be {@code null} */
  private final FP m_producer;

  /**
   * Create the file producer example
   *
   * @param listener
   *          the file producer listener, can be {@code null}
   * @param destDir
   *          the destination directory
   * @param producer
   *          the file producer
   */
  protected FileProducerExample(final IFileProducerListener listener,
      final Path destDir, final FP producer) {
    super();

    if (destDir == null) {
      throw new IllegalArgumentException(//
          "Destination directory cannot be null."); //$NON-NLS-1$
    }

    if (producer == null) {
      throw new IllegalArgumentException(//
          "File producer tool cannot be null."); //$NON-NLS-1$
    }

    this.m_listener = listener;
    this.m_destDir = destDir;
    this.m_producer = producer;
  }

  /**
   * Create the basic name for the graphic
   *
   * @return the basic name for the graphic
   */
  protected String getMainDocumentNameSuggestionBase() {
    return this.getClass().getSimpleName() + '_' + //
        this.m_producer.getClass().getSimpleName();
  }

  /**
   * Create the basic name for the graphic, this will be the
   * {@linkplain #getMainDocumentNameSuggestionBase() base name} plus a
   * unique ID
   *
   * @return the basic name for the graphic, this will be the
   *         {@linkplain #getMainDocumentNameSuggestionBase() base name}
   *         plus a unique ID
   */
  protected final String getMainDocumentNameSuggestion() {
    return PathUtils
        .sanitizePathComponent(//
            ((this.getMainDocumentNameSuggestionBase() + '_')//
                + FileProducerExample.UNIQUE_IDS.incrementAndGet()),
            false);
  }

  /**
   * Get the file producer tool
   *
   * @return the file producer tool
   */
  protected FP getFileProducerTool() {
    return this.m_producer;
  }

  /**
   * Get the file producer listener
   *
   * @return the file producer listener, never {@code null}
   */
  protected final IFileProducerListener getListener() {
    return (this.m_listener != null) ? this.m_listener
        : new AbstractFileProducerListener();
  }

  /**
   * Get the destination directory
   *
   * @return the destination directory
   */
  protected Path getDestDir() {
    return this.m_destDir;
  }

  /**
   * Perform the example.
   *
   * @throws Throwable
   *           if something goes wrong
   */
  protected void perform() throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    try {
      this.perform();
    } catch (final RuntimeException runExcp) {
      throw runExcp;
    } catch (final Throwable error) {
      throw new RuntimeException(("Failed to run example code " //$NON-NLS-1$
          + this.getClass().getSimpleName() + '.'), error);
    }
  }
}
