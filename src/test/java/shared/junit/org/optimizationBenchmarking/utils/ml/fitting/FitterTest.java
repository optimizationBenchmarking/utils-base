package shared.junit.org.optimizationBenchmarking.utils.ml.fitting;

import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingJob;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingJobBuilder;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;

import shared.junit.TestBase;

/** A test for function fitters */
@Ignore
public abstract class FitterTest extends TestBase {

  /** create the test */
  protected FitterTest() {
    super();
  }

  /**
   * Get the function fitting tool
   *
   * @return the functionfitting tool
   */
  protected abstract IFunctionFitter getTool();

  /** test whether the fitting engine tool can be constructed */
  @Test(timeout = 3600000)
  public void testToolNotNull() {
    Assert.assertNotNull(this.getTool());
  }

  /**
   * fit an example
   *
   * @param dataset
   *          the data set
   * @param measure
   *          the measure
   */
  protected final void fitExample(final FittingExampleDataset dataset,
      final IFittingQualityMeasure measure) {
    final IFunctionFitter engine;
    final IFittingJobBuilder builder;
    final IFittingJob job;
    final IFittingResult result;
    final double[] params, other;
    final double quality;
    final Random random;
    int count, better, i;
    double newQuality;

    Assert.assertNotNull(dataset);
    Assert.assertNotNull(measure);

    engine = this.getTool();
    Assert.assertNotNull(engine);

    if (engine.canUse()) {
      builder = engine.use();
      Assert.assertNotNull(builder);
      job = builder.setFunctionToFit(dataset.function)//
          .setPoints(dataset.data)//
          .setQualityMeasure(measure).create();
      Assert.assertNotNull(job);

      result = job.call();
      Assert.assertNotNull(result);

      params = result.getFittedParametersRef();
      Assert.assertNotNull(params);
      Assert.assertEquals(dataset.function.getParameterCount(),
          params.length);

      for (final double d : params) {
        Assert.assertTrue(MathUtils.isFinite(d));
      }

      quality = result.getQuality();
      Assert.assertTrue(MathUtils.isFinite(quality));
      Assert.assertTrue(quality >= 0d);

      Assert.assertSame(dataset.function, result.getFittedFunction());

      better = 0;
      other = params.clone();
      random = new Random();
      for (count = 1; count <= 200; count++) {
        for (i = other.length; (--i) >= 0;) {
          other[i] = ((random.nextBoolean()//
              ? ((params[i] * (random.nextInt(101) - 50)))//
              : random.nextGaussian()) + random.nextDouble());
        }
        newQuality = measure.evaluate(dataset.function, other);
        if (newQuality < quality) {
          better++;
        }
      }

      // An extremely randomly modified solution should never be better
      // than the fitting result in more than 10% of the cases...
      Assert.assertTrue((better * 10) < (count - 1));
    }
  }
}
