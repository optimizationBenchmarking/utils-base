package org.optimizationBenchmarking.utils.ml.classifcation.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/** A tool able to train classifiers */
public interface IClassifierTrainer extends ITool {

  /** {@inheritDoc} */
  @Override
  public abstract IClassifierTrainingJobBuilder use();
}
