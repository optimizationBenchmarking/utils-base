package org.optimizationBenchmarking.utils.ml.classification.spec;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The interface for classifiers. */
public interface IClassifier {

  /**
   * Classify the given feature vector. Features are always represented as
   * {@code double}: If they are numerical, that will do anyway. Otherwise,
   * i.e., for nominal features, the number represents the 0-based index of
   * the feature value in the sorted list of nominal features.
   *
   * @param features
   *          the feature vector
   * @return the 0-based class index
   */
  public abstract int classify(final double[] features);

  /**
   * Render the classifier to a given text output destination.
   *
   * @param renderer
   *          the renderer
   * @param textOutput
   *          the text output destination (could be an instance of
   *          {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   *          or even
   *          {@link org.optimizationBenchmarking.utils.document.spec.ISectionBody}
   *          , in which case you should do something cool...)
   */
  public abstract void render(final IClassifierParameterRenderer renderer,
      final ITextOutput textOutput);
}
