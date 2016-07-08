package org.optimizationBenchmarking.utils.document.spec;

/** A renderer for figures. */
public interface IFigureRenderer {

  /**
   * Create a label or not.
   *
   * @param builder
   *          the label builder to use
   * @return the label or {@code null} if none is needed
   */
  public abstract ILabel createFigureLabel(final ILabelBuilder builder);

  /**
   * Get the path component for the figure
   *
   * @return the path component of the figure
   */
  public abstract String getFigurePathComponentSuggestion();

  /**
   * Get the size of the figure
   *
   * @return the size of the figure
   */
  public abstract EFigureSize getFigureSize();

  /**
   * Render the figure
   *
   * @param figure
   *          the figure to render
   */
  public abstract void renderFigure(final IFigure figure);
}
