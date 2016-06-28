package org.optimizationBenchmarking.utils.document.spec;

/** A renderer for figure series. */
public interface IFigureSeriesRenderer {
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
  public abstract String getFigureSeriesPath();

  /**
   * Get the size of the figure
   *
   * @return the size of the figure
   */
  public abstract EFigureSize getFigureSize();
}
