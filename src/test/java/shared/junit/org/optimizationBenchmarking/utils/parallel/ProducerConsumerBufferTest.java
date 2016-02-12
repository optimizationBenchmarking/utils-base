package shared.junit.org.optimizationBenchmarking.utils.parallel;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.parallel.ProducerConsumerBuffer;

import shared.junit.TestBase;

/**
 * The base class for testing producer and consumer buffers.
 *
 * @param <T>
 *          the array type
 */
@Ignore
public abstract class ProducerConsumerBufferTest<T> extends TestBase {

  /** create */
  protected ProducerConsumerBufferTest() {
    super();
  }

  /**
   * create a new array
   *
   * @param size
   *          the size
   * @return the array
   */
  protected abstract T createArray(final int size);

  /**
   * fill an array with random data
   *
   * @param array
   *          the array
   * @param r
   *          the randomizer
   */
  protected abstract void randomizeArray(final T array, final Random r);

  /**
   * assert that the contents of two arrays are equal
   *
   * @param a
   *          the first array
   * @param b
   *          the second array
   */
  protected abstract void assertEquals(final T a, final T b);

  /**
   * create a new buffer
   *
   * @param size
   *          the size
   * @return the buffer
   */
  protected abstract ProducerConsumerBuffer<T> createBuffer(
      final int size);

  /**
   * Test whether the written data and the data we read is the same
   *
   * @throws InterruptedException
   *           if it fails...
   */
  @Test(timeout = 3600000)
  public void testReadEqualsWrite() throws InterruptedException {
    int i, size, use;
    T readArray, writeArray;
    Random random;
    Thread writer, reader;

    random = new Random();
    for (i = 0; i < 50; i++) {

      switch (random.nextInt(3)) {
        case 0: {
          size = (1 + random.nextInt(1024 * 1024 * 16));
          break;
        }
        case 1: {
          size = (i + 1);
          break;
        }
        default: {
          size = ((i + 1) * 64);
        }
      }
      readArray = this.createArray(size);
      writeArray = this.createArray(size);
      this.randomizeArray(writeArray, random);

      switch (random.nextInt(3)) {
        case 0: {
          use = (1 + random.nextInt(100));
          break;
        }
        case 1: {
          use = 1;
          break;
        }
        case 2: {
          use = -1;
          break;
        }
        case 3: {
          use = 1024;
          break;
        }
        default: {
          use = (1 + random.nextInt(size << 1));
          break;
        }
      }

      try (final ProducerConsumerBuffer<T> buffer = this
          .createBuffer(use)) {
        writer = new __WriterThread(writeArray, size, buffer, random);
        reader = new __ReaderThread(readArray, size, buffer);

        if (random.nextBoolean()) {
          writer.start();
          Thread.sleep(100);
          reader.start();
        } else {
          reader.start();
          Thread.sleep(100);
          writer.start();
        }

        writer.join();
        reader.join();
      }

      this.assertEquals(writeArray, readArray);
    }
  }

  /** a writer thread */
  private final class __WriterThread extends Thread {
    /** the data to write */
    private final T m_write;
    /** the amount to write */
    private final int m_size;
    /** the buffer */
    private final ProducerConsumerBuffer<T> m_buffer;
    /** the randomizer */
    private final Random m_rand;

    /**
     * create the writer thread
     *
     * @param write
     *          the stuff to write
     * @param size
     *          the total amount to write
     * @param buffer
     *          the buffer
     * @param rand
     *          the randomizer
     */
    __WriterThread(final T write, final int size,
        final ProducerConsumerBuffer<T> buffer, final Random rand) {
      super();
      this.m_write = write;
      this.m_size = size;
      this.m_buffer = buffer;
      this.m_rand = rand;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unused")
    @Override
    public final void run() {
      final Random r;
      int pos, w;

      r = new Random();
      pos = 0;

      while (pos < this.m_size) {
        switch (r.nextInt(4)) {
          case 0: {
            w = r.nextInt(this.m_size - pos);
            break;
          }
          case 1: {
            w = Math.min((this.m_size - pos), r.nextInt(100));
            break;
          }
          case 2: {
            w = 1;
            break;
          }
          default: {
            w = 0;
          }
        }
        this.m_buffer.writeToBuffer(this.m_write, pos, w);
        pos += w;
        switch (r.nextInt(3)) {
          case 0: {
            Thread.yield();
            break;
          }
          case 1: {
            try {
              Thread.sleep(r.nextInt(100));
            } catch (final Throwable t) {/* */
            }
            break;
          }
          default: {
            // nothing
          }
        }
      }

      if (this.m_rand.nextBoolean()) {
        this.m_buffer.close();
      }
    }
  }

  /** a reader thread */
  private final class __ReaderThread extends Thread {
    /** the data to read */
    private final T m_read;
    /** the amount to read */
    private final int m_size;
    /** the buffer */
    private final ProducerConsumerBuffer<T> m_buffer;

    /**
     * create the read thread
     *
     * @param read
     *          the stuff to read
     * @param size
     *          the total amount to read
     * @param buffer
     *          the buffer
     */
    __ReaderThread(final T read, final int size,
        final ProducerConsumerBuffer<T> buffer) {
      super();
      this.m_read = read;
      this.m_size = size;
      this.m_buffer = buffer;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unused")
    @Override
    public final void run() {
      final Random r;
      int pos, w;

      r = new Random();
      pos = 0;

      while (pos < this.m_size) {
        switch (r.nextInt(4)) {
          case 0: {
            w = r.nextInt(this.m_size - pos);
            break;
          }
          case 1: {
            w = Math.min((this.m_size - pos), r.nextInt(100));
            break;
          }
          case 2: {
            w = 1;
            break;
          }
          default: {
            w = 0;
          }
        }
        w = this.m_buffer.readFromBuffer(this.m_read, pos, w);
        if (w < 0) {
          return;
        }
        pos += w;
        switch (r.nextInt(3)) {
          case 0: {
            Thread.yield();
            break;
          }
          case 1: {
            try {
              Thread.sleep(r.nextInt(100));
            } catch (final Throwable t) {/* */
            }
            break;
          }
          default: {
            // nothing
          }
        }
      }
    }
  }
}
