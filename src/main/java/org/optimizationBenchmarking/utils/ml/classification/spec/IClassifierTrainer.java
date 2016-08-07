package org.optimizationBenchmarking.utils.ml.classification.spec;

import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.tools.spec.ITool;

/** A tool able to train classifiers */
public interface IClassifierTrainer extends ITool, ISemanticComponent {

  /** {@inheritDoc} */
  @Override
  public abstract IClassifierTrainingJobBuilder use();
}
