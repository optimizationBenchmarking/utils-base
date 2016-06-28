package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeriesRenderer;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabelBuilder;

/** An abstract base class for figure series renderers. */
public abstract class FigureSeriesRenderer
    implements IFigureSeriesRenderer {

  /** create the optional section */
  protected FigureSeriesRenderer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ILabel createFigureSeriesLabel(final ILabelBuilder builder) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getFigureSeriesPath() {
    return this.getClass().getSimpleName();
  }

  /** {@inheritDoc} */
  @Override
  public EFigureSize getFigureSize() {
    return EFigureSize.PAGE_3_PER_ROW;
  }
}
