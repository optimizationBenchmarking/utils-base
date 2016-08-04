package shared.junit.org.optimizationBenchmarking.utils.ml.classification;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.ml.classification.spec.ClassifiedSample;
import org.optimizationBenchmarking.utils.ml.classification.spec.IClassifier;
import org.optimizationBenchmarking.utils.ml.classification.spec.IClassifierQualityMeasure;
import org.optimizationBenchmarking.utils.ml.classification.spec.IClassifierTrainer;
import org.optimizationBenchmarking.utils.ml.classification.spec.IClassifierTrainingResult;

import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * A base class for tests for classifier trainers
 */
@Ignore
public abstract class ClassifierTrainerTest
    extends ToolTest<IClassifierTrainer> {

  /**
   * create the test
   *
   * @param classifier
   *          the classifier
   */
  protected ClassifierTrainerTest(final IClassifierTrainer classifier) {
    super(classifier);
  }

  /**
   * Apply a classifier trainer to the given sample data and verify the
   * result
   *
   * @param samples
   *          the samples
   * @param measure
   *          the quality measure
   */
  protected void applyClassifierTrainer(
      final ClassifierExampleDataset samples,
      final IClassifierQualityMeasure<?> measure) {
    final IClassifierTrainer trainer;
    final IClassifierTrainingResult result;
    final IClassifier classifier;
    int maxClass, currentClass;

    Assert.assertNotNull(samples);

    trainer = this.getInstance();

    Assert.assertNotNull(trainer);
    if (!(trainer.canUse())) {
      return;
    }

    result = trainer.use()//
        .setFeatureTypes(samples.features)//
        .setQualityMeasure(measure)//
        .setTrainingSamples(samples.data)//
        .create().call();//

    Assert.assertNotNull(result);

    classifier = result.getClassifier();
    Assert.assertNotNull(classifier);
    Assert.assertNotNull(classifier.getPathComponentSuggestion());
    Assert.assertNotNull(classifier.toString());
    Assert.assertTrue(classifier.equals(classifier));

    maxClass = 0;
    for (final ClassifiedSample sample : samples.data) {
      maxClass = Math.max(maxClass, sample.sampleClass);
    }

    for (final ClassifiedSample sample : samples.data) {
      currentClass = classifier.classify(sample.featureValues);
      Assert.assertTrue(currentClass >= 0);
      Assert.assertTrue(currentClass <= maxClass);
    }

    Assert.assertTrue(result.getQuality() >= 0d);
    Assert.assertTrue(MathUtils.isFinite(result.getQuality()));
  }

}
