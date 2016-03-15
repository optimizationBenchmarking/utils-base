package shared.junit.org.optimizationBenchmarking.utils.chart;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;

import examples.org.optimizationBenchmarking.utils.chart.SimplePieChartExample;
import examples.org.optimizationBenchmarking.utils.graphics.ExampleBasicStyles;
import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/** Test a chart driver can be used */
@Ignore
public abstract class ChartDriverTest extends ToolTest<IChartDriver> {

  /**
   * create
   *
   * @param driver
   *          the chart driver
   */
  public ChartDriverTest(final IChartDriver driver) {
    super(driver);
  }

  /**
   * Create a graphic.
   *
   * @return a graphics to paint on
   */
  protected Graphics2D createGraphic() {
    final BufferedImage img;
    final int width, height;
    final Graphics2D g2d;

    width = 1000;
    height = 1000;
    img = new BufferedImage(width, height,
        EColorModel.RGB_24_BIT.getBufferedImageType());
    g2d = ((Graphics2D) (img.getGraphics()));
    GraphicUtils.applyDefaultRenderingHints(g2d);
    g2d.setClip(new Rectangle(0, 0, width, height));
    return g2d;
  }

  /**
   * Create the basic styles
   *
   * @return the basic styles
   */
  protected IBasicStyles createStyles() {
    return new ExampleBasicStyles();
  }

  /** test the simple pie chart drawing. */
  @Test(timeout = 3600000)
  public void testSimplePieChart() {
    final IChartDriver instance;
    final IBasicStyles styles;

    instance = this.getInstance();
    Assert.assertNotNull(instance);

    styles = this.createStyles();
    new SimplePieChartExample(//
        instance.use().setGraphic(this.createGraphic())//
            .setStyles(styles).create(),
        styles, new Random()).run();
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testSimplePieChart();
  }
}
