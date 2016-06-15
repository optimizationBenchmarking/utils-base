package test.junit.org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.numbers.TruncatedNumberAppender;

import shared.junit.org.optimizationBenchmarking.utils.text.numbers.NumberAppenderTest;

/** Test truncated number appending */
public class TruncatedNumberAppenderTest extends NumberAppenderTest {

  /** create */
  public TruncatedNumberAppenderTest() {
    super(null, false, false);
  }

  /** {@inheritDoc} */
  @Override
  protected final TruncatedNumberAppender getInstance() {
    return new TruncatedNumberAppender();
  }
}
