package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerTool;

/**
 * An interface for graphics drivers, i.e., objects that can create
 * graphics objects.
 */
public interface IGraphicDriver extends IDocumentProducerTool {

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder use();

  /**
   * Obtain the graphic format managed by this driver
   *
   * @return the graphic format managed by this driver
   */
  @Override
  public abstract IGraphicFormat getFileType();
}
