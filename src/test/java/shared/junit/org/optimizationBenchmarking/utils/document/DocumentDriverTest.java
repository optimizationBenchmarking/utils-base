package shared.junit.org.optimizationBenchmarking.utils.document;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

import examples.org.optimizationBenchmarking.utils.document.RandomDocumentExample;
import shared.FileProducerCollector;
import shared.junit.CategorySlowTests;
import shared.junit.InstanceTest;
import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * A test of a document driver
 *
 * @param <ConfigType>
 *          the configuration type
 */
@Ignore
public abstract class DocumentDriverTest<ConfigType>
    extends InstanceTest<ConfigType> {

  /**
   * create
   *
   * @param config
   *          the configuration
   */
  public DocumentDriverTest(final ConfigType config) {
    super(null, config, false, false);
  }

  /**
   * Get the required file types, i.e., the file types which should be
   * produced
   *
   * @return the file types which should be produced
   */
  protected abstract IFileType[] getRequiredTypes();

  /**
   * Create a document. This is a kitchen-sink method where we throw in all
   * the parameters normally to be passed to
   * {@link org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder}
   * and not already defined in this configuration object. This kitchen
   * sink approach is not nice and maybe will be amended later. But for now
   * it will do.
   *
   * @param type
   *          the document type
   * @param basePath
   *          the base path, i.e., the folder in which the document should
   *          be created
   * @param name
   *          the name of the graphics file (without extension)
   * @param listener
   *          the listener to be notified when painting the graphic has
   *          been completed
   * @param logger
   *          the logger to use
   * @return the graphic
   */
  protected abstract IDocument createDocument(final ConfigType type,
      final Path basePath, final String name,
      final IFileProducerListener listener, final Logger logger);

  /**
   * The timeout for serial document generation tests.
   *
   * @return the timeout for serial document generation tests
   */
  protected long getSerialTimeout() {
    return 50_000L;
  }

  /**
   * The timeout for parallel document generation tests.
   *
   * @return the timeout for parallel document generation tests
   */
  protected long getParallelTimeout() {
    return ((this.getSerialTimeout() * 2L) / 3L);
  }

  /**
   * test the document driver for creating random documents
   *
   * @param service
   *          the service
   * @param r
   *          the random number generator
   * @param timeout
   *          the timeout
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doSerialRandomTest(final ExecutorService service,
      final Random r, final long timeout)
          throws IOException, InterruptedException, ExecutionException {
    final ConfigType config;
    RandomDocumentExample example;
    FileProducerCollector files;
    Future<?> future;

    config = this.getInstance();
    Assert.assertNotNull(config);
    try {
      files = new FileProducerCollector();

      try (final TempDir td = new TempDir()) {
        try (final IDocument doc = this.createDocument(config,
            td.getPath(), "document", files, null)) { //$NON-NLS-1$
          example = new RandomDocumentExample(doc, r, null, timeout);
          try {
            if (service != null) {
              try {
                future = service.submit(example);
                future.get();
              } finally {
                future = null;
              }
            } else {
              example.run();
            }
          } finally {
            example = null;
          }
        }
      }

      files.assertFilesOfType(this.getRequiredTypes());
    } finally {
      files = null;
    }

    MemoryUtils.fullGC();
  }

  /**
   * Test the serial generation of documents
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testSerialRandomDocumentCreation() throws Throwable {
    this.__doSerialRandomTest(null, new Random(), this.getSerialTimeout());
  }

  /**
   * test the document driver
   *
   * @param proc
   *          the processors
   * @param fifo
   *          do this fifo-style
   * @param r
   *          the random number generator
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doParallelRandomTest(final int proc,
      final boolean fifo, final Random r)
          throws IOException, InterruptedException, ExecutionException {
    final ForkJoinPool p;

    p = new ForkJoinPool(proc,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, fifo);

    this.__doSerialRandomTest(p, new Random(), this.getParallelTimeout());

    p.shutdown();
    p.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    MemoryUtils.fullGC();
  }

  /**
   * Test the fifo parallel generation of documents with 1 processor
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testParallelRandomDocumentCreation_1_fifo()
      throws Throwable {
    this.__doParallelRandomTest(1, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 1 processor
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testParallelRandomDocumentCreation_1_default()
      throws Throwable {
    this.__doParallelRandomTest(1, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 2 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelRandomDocumentCreation_2_fifo()
      throws Throwable {
    this.__doParallelRandomTest(2, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 2 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelRandomDocumentCreation_2_default()
      throws Throwable {
    this.__doParallelRandomTest(2, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 3 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testParallelRandomDocumentCreation_3_fifo()
      throws Throwable {
    this.__doParallelRandomTest(3, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 3 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testParallelRandomDocumentCreation_3_default()
      throws Throwable {
    this.__doParallelRandomTest(3, false, new Random());
  }

  /**
   * Get the document driver
   *
   * @param type
   *          the configuration type
   * @return the document driver
   */
  protected abstract IDocumentDriver getDocumentDriver(
      final ConfigType type);

  /**
   * Get the document driver
   *
   * @return the document driver
   */
  final IDocumentDriver _getDriver() {
    return this.getDocumentDriver(this.getInstance());
  }

  /**
   * Test whether the document driver can correctly be used as tool.
   */
  @Test(timeout = 3600000)
  public void testDocumentDriverAsTool() {
    new ToolTest<IDocumentDriver>() {
      @Override
      protected IDocumentDriver getInstance() {
        return DocumentDriverTest.this._getDriver();
      }
    }.validateInstance();
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testDocumentDriverAsTool();
    try {
      this.testParallelRandomDocumentCreation_1_default();
      this.testParallelRandomDocumentCreation_1_fifo();
      this.testParallelRandomDocumentCreation_2_default();
      this.testParallelRandomDocumentCreation_2_fifo();
      this.testParallelRandomDocumentCreation_3_default();
      this.testParallelRandomDocumentCreation_3_fifo();
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
