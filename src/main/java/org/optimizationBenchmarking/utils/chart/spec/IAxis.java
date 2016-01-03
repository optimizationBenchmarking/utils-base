package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

/** The interface for defining an axis */
public interface IAxis extends ITitledElement {

  /**
   * Set the font to be used for the ticks of the axis. Depending on the
   * chart implementation, this font may be scaled to a smaller (or maybe
   * even larger) size to create a more overall pleasing visual expression.
   * Thus, you can directly use fonts from a
   * {@link org.optimizationBenchmarking.utils.graphics.style.spec.IFontPalette}
   * which would potentially be too large or too small to look good and
   * expect the underlying
   * {@link org.optimizationBenchmarking.utils.chart.spec.IChartDriver
   * chart driver} implementation to resize them appropriately.
   *
   * @param tickFont
   *          the font to be used for the ticks of the axis
   */
  public abstract void setTickFont(final Font tickFont);

  /**
   * Set the stroke to be used for the axis. If you do not set a stroke, a
   * default stroke will be used.
   *
   * @param axisStroke
   *          the stroke to be used for the axis
   */
  public abstract void setAxisStroke(final Stroke axisStroke);

  /**
   * Set the color to be used for the axis. If you do not set a color, a
   * default color will be used.
   *
   * @param axisColor
   *          the color to be used for the axis
   */
  public abstract void setAxisColor(final Color axisColor);

  /**
   * Set the stroke to be used for the grid lines. If you do not set a
   * stroke, a default stroke will be used.
   *
   * @param gridLineStroke
   *          the stroke to be used for the grid lines
   */
  public abstract void setGridLineStroke(final Stroke gridLineStroke);

  /**
   * Set the color to be used for the grid lines. If you do not set a
   * color, a default color will be used.
   *
   * @param gridLineColor
   *          the color to be used for the grid lines
   */
  public abstract void setGridLineColor(final Color gridLineColor);

  /**
   * Set the minimum value for the axis as number. If the object provided
   * as {@code minimum} implements the interface
   * {@link org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate}
   * , then its {@code append} methods will be invoked for all available
   * data elements of the axis dimension. Only after that, the numerical
   * value will be accessed. Otherwise, i.e., if {@code minimum} is just a
   * plain {@link java.lang.Number}, then we will directly access its
   * value.
   *
   * @param minimum
   *          the minimum value for the axis, may be an implementation of
   *          {@link org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate}
   */
  public abstract void setMinimum(final Number minimum);

  /**
   * Set the minimum value for the axis as {@code double} constant.
   *
   * @param minimum
   *          the minimum value for the axis
   */
  public abstract void setMinimum(final double minimum);

  /**
   * Set the maximum value for the axis as number. If the object provided
   * as {@code maximum} implements the interface
   * {@link org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate}
   * , then its {@code append} methods will be invoked for all available
   * data elements of the axis dimension. Only after that, the numerical
   * value will be accessed. Otherwise, i.e., if {@code maximum} is just a
   * plain {@link java.lang.Number}, then we will directly access its
   * value.
   *
   * @param maximum
   *          the maximum value for the axis, may be an implementation of
   *          {@link org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate}
   */
  public abstract void setMaximum(final Number maximum);

  /**
   * Set the maximum value for the axis as {@code double} constant.
   *
   * @param maximum
   *          the minimum value for the axis
   */
  public abstract void setMaximum(final double maximum);
}
