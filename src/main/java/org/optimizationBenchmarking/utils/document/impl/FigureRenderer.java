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
  public ILabel createFigureLabel(final ILabelBuilder builder) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getFigurePath() {
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
    this.m_isPartOfSeries = false;
  }
}
