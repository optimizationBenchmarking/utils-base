package org.optimizationBenchmarking.utils.ml.classification.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** The builder for classification jobs. */
public interface IClassifierTrainingJobBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IClassifierTrainingJobBuilder setLogger(
      final Logger logger);

  /**
   * Specify the types of the features. This method must be called before
   *
   * @param featureTypes
   *          the feature types
   * @return this builder
   */
  public abstract IClassifierTrainingJobBuilder setFeatureTypes(
      final EFeatureType... featureTypes);

  /**
   * Add a training sample. This method must be called after
   * {@literal #setFeatureTypes(EFeatureType...)}.
   *
   * @param trainingSample
   *          the training sample
   * @return this builder
   */
  public abstract IClassifierTrainingJobBuilder addTrainingSample(
      final double... trainingSample);

  /** {@inheritDoc} */
  @Override
  public abstract IClassifierTrainingJob create();

}
