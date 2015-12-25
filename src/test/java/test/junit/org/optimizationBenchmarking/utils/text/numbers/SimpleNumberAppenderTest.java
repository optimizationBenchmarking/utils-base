package test.junit.org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

import shared.junit.org.optimizationBenchmarking.utils.text.numbers.NumberAppenderTest;

/** Test simple number appending */
public class SimpleNumberAppenderTest extends NumberAppenderTest {

  /** create */
  public SimpleNumberAppenderTest() {
    super(SimpleNumberAppender.INSTANCE, true);
  }
}
