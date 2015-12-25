package test.junit.org.optimizationBenchmarking.utils.parallel;

import java.util.Random;

import org.junit.Assert;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;
import org.optimizationBenchmarking.utils.parallel.ProducerConsumerBuffer;

import shared.junit.org.optimizationBenchmarking.utils.parallel.ProducerConsumerBufferTest;

/**
 * The base class for testing producer and consumer buffers.
 */
public class ByteProducerConsumerBufferTest
    extends ProducerConsumerBufferTest<byte[]> {

  /** create */
  public ByteProducerConsumerBufferTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final byte[] createArray(final int size) {
    return new byte[size];
  }

  /** {@inheritDoc} */
  @Override
  protected final void randomizeArray(final byte[] array, final Random r) {
    r.nextBytes(array);
  }

  /** {@inheritDoc} */
  @Override
  protected final void assertEquals(final byte[] a, final byte[] b) {
    Assert.assertArrayEquals(a, b);
  }

  /** {@inheritDoc} */
  @Override
  protected final ProducerConsumerBuffer<byte[]> createBuffer(
      final int size) {
    return new ByteProducerConsumerBuffer(size);
  }

}
