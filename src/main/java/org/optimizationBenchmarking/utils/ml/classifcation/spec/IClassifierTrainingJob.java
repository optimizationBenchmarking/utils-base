package org.optimizationBenchmarking.utils.ml.classifcation.spec;

import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;

/** the job for creating classifiers */
public interface IClassifierTrainingJob
    extends ICallableToolJob<IClassifierTrainingResult> {

  /** {@inheritDoc *} */
  @Override
  public abstract IClassifierTrainingResult call()
      throws IllegalArgumentException;
}
