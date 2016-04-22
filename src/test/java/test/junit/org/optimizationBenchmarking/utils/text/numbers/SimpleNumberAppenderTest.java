package test.junit.org.optimizationBenchmarking.utils.text.numbers;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.NumberAppender;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

import shared.junit.org.optimizationBenchmarking.utils.text.numbers.NumberAppenderTest;

/** Test simple number appending */
public class SimpleNumberAppenderTest extends NumberAppenderTest {

  /** create */
  public SimpleNumberAppenderTest() {
    super(SimpleNumberAppender.INSTANCE, true);
  }

  /** Test whether scientific notation is generated properly */
  @Test(timeout = 3600000)
  public void testScientificNotation() {
    final NumberAppender appender;

    appender = this.getInstance();
    Assert.assertNotNull(appender);

    Assert.assertEquals("0", appender.toString(0d, ETextCase.IN_SENTENCE)); //$NON-NLS-1$
    Assert.assertEquals("0.1", //$NON-NLS-1$
        appender.toString(0.1d, ETextCase.IN_SENTENCE));
    Assert.assertEquals("1E-20", //$NON-NLS-1$
        appender.toString(1e-20d, ETextCase.IN_SENTENCE));
    Assert.assertEquals("1.1E-20", //$NON-NLS-1$
        appender.toString(1.10e-20d, ETextCase.IN_SENTENCE));
    Assert.assertEquals("-1E-20", //$NON-NLS-1$
        appender.toString(-1e-20d, ETextCase.IN_SENTENCE));
    Assert.assertEquals("-1.1E-20", //$NON-NLS-1$
        appender.toString(-1.10e-20d, ETextCase.IN_SENTENCE));
  }
}
