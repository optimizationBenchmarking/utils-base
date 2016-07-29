package org.optimizationBenchmarking.utils.ml.classification.spec;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This interface provides the methods necessary to render a classifier.
 */
public interface IClassifierParameterRenderer {

  /**
   * Render the short class name of a given class
   *
   * @param classIndex
   *          the index of the class whose name shall be rendered
   * @param textOut
   *          the text output destination
   */
  public abstract void renderShortClassName(final int classIndex,
      final ITextOutput textOut);

  /**
   * Render the long class name of a given class
   *
   * @param classIndex
   *          the index of the class whose name shall be rendered
   * @param textOut
   *          the text output destination
   */
  public abstract void renderLongClassName(final int classIndex,
      final ITextOutput textOut);

  /**
   * Render the short name of a given feature
   *
   * @param featureIndex
   *          the index of the feature whose name shall be rendered
   * @param textOut
   *          the text output destination
   */
  public abstract void renderShortFeatureName(final int featureIndex,
      final ITextOutput textOut);

  /**
   * Render the long name of a given feature
   *
   * @param featureIndex
   *          the index of the feature whose name shall be rendered
   * @param textOut
   *          the text output destination
   */
  public abstract void renderLongFeatureName(final int featureIndex,
      final ITextOutput textOut);

  /**
   * Render the value of a given feature
   *
   * @param featureIndex
   *          the feature index
   * @param featureValue
   *          the feature value to be rendered
   * @param textOut
   *          the text output destination
   */
  public abstract void renderFeatureValue(final int featureIndex,
      final double featureValue, final ITextOutput textOut);
}
