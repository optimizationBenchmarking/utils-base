package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.style.spec.IStyles;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** Build a chart */
public interface IChartBuilder extends IToolJobBuilder {

	/** {@inheritDoc} */
	@Override
	public abstract IChartBuilder setLogger(final Logger logger);

	/**
	 * Set the graphic to paint on
	 *
	 * @param graphic
	 *            the graphic to paint
	 * @return this builder
	 */
	public abstract IChartBuilder setGraphic(final Graphics2D graphic);

	/**
	 * Set the style set to use for the diagram
	 *
	 * @param styleSet
	 *            the style set to use for the diagram
	 * @return this builder
	 */
	public abstract IChartBuilder setStyleSet(final IStyles styleSet);

	/**
	 * Create the chart selector.
	 *
	 * @return the chart selector
	 */
	@Override
	public abstract IChartSelector create();
}
