package org.optimizationBenchmarking.utils.document.spec;

/** A renderer for figure series. */
public interface IFigureSeriesRenderer extends Iterable<IFigureRenderer> {
  /**
   * Create a label or not.
   *
   * @param builder
   *          the label builder to use
   * @return the label or {@code null} if none is needed
   */
  public abstract ILabel createFigureSeriesLabel(
      final ILabelBuilder builder);

  /**
   * Get the path component for the figure
   *
   * @return the path component of the figure
   */
  public abstract String getFigureSeriesPathComponentSuggestion();

  /**
   * Get the size of the figure
   *
   * @return the size of the figure
   */
  public abstract EFigureSize getFigureSize();

  /**
   * Render the figure series caption
   *
   * @param caption
   *          the caption destination
   */
  public abstract void renderFigureSeriesCaption(
      final IComplexText caption);
}
