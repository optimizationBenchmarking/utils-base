package shared.junit.org.optimizationBenchmarking.utils.ml.fitting;

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
    final double[] params;

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

      Assert.assertTrue(MathUtils.isFinite(result.getQuality()));
      Assert.assertTrue(result.getQuality() >= 0d);

      Assert.assertSame(dataset.function, result.getFittedFunction());
    }
  }
}
