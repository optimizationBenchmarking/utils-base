package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** Build a chart */
public interface IChartBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IChartBuilder setLogger(final Logger logger);

  /**
   * Set the graphic to paint on. The bounding box of the chart to be
   * painted will be set to the bounding box returned by
   * {@link org.optimizationBenchmarking.utils.graphics.GraphicUtils#getBounds(java.awt.Graphics)}
   * . This will either be the
   * {@linkplain org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#getBounds()
   * bounding box} of the graphics context, if {@code graphic} is an
   * instance of
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic}
   * , or {@linkplain java.awt.Graphics2D#getClipBounds() bounding box} of
   * the current clipping area.
   *
   * @param graphic
   *          the graphic to paint
   * @return this builder
   */
  public abstract IChartBuilder setGraphic(final Graphics2D graphic);

  /**
   * Set the basic styles to use for the diagram
   *
   * @param styles
   *          the basic styles to use for the diagram
   * @return this builder
   */
  public abstract IChartBuilder setStyles(final IBasicStyles styles);

  /**
   * Create the chart selector.
   *
   * @return the chart selector
   */
  @Override
  public abstract IChartSelector create();
}
