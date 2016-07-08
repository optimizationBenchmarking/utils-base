package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureRenderer;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabelBuilder;

/** An abstract base class for figure renderers. */
public abstract class FigureRenderer implements IFigureRenderer {

  /** has this renderer be used as part of a figure series? */
  boolean m_isPartOfSeries;

  /** create the optional section */
  protected FigureRenderer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ILabel createFigureLabel(final ILabelBuilder builder) {
    return this.doCreateFigureLabel(this.m_isPartOfSeries, builder);
  }

  /**
   * create the label for this figure series
   *
   * @param isPartOfSeries
   *          is this figure part of a figure series?
   * @param builder
   *          the label builder
   * @return the label, or {@code null} if none is needed
   */
  protected ILabel doCreateFigureLabel(final boolean isPartOfSeries,
      final ILabelBuilder builder) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final String getFigurePathComponentSuggestion() {
    return this.doGetFigurePathComponentSuggestion(this.m_isPartOfSeries);
  }

  /**
   * get the path component of this figure
   *
   * @param isPartOfSeries
   *          is this figure part of a figure series?
   * @return the path component of this figure
   */
  protected String doGetFigurePathComponentSuggestion(
      final boolean isPartOfSeries) {
    return this.getClass().getSimpleName();
  }

  /** {@inheritDoc} */
  @Override
  public EFigureSize getFigureSize() {
    return EFigureSize.PAGE_3_PER_ROW;
  }

  /**
   * render the figure
   *
   * @param isPartOfSeries
   *          is this figure part of a figure series?
   * @param figure
   *          the figure to render
   */
  protected void doRenderFigure(final boolean isPartOfSeries,
      final IFigure figure) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void renderFigure(final IFigure figure) {
    this.doRenderFigure(this.m_isPartOfSeries, figure);
  }
}
