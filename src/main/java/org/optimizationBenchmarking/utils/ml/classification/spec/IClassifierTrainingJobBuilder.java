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
   * Set the quality measure to compute the classifier's quality.
   *
   * @param qualityMeasure
   *          the quality measure
   * @return this builder
   */
  public abstract IClassifierTrainingJobBuilder setQualityMeasure(
      final IClassifierQualityMeasure<?> qualityMeasure);

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
   * @param knownSamples
   *          the known samples to be used for training
   * @return this builder
   */
  public abstract IClassifierTrainingJobBuilder setTrainingSamples(
      ClassifiedSample... knownSamples);

  /** {@inheritDoc} */
  @Override
  public abstract IClassifierTrainingJob create();
}
