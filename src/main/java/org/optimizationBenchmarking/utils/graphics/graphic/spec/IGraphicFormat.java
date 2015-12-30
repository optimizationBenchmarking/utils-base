package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import org.optimizationBenchmarking.utils.io.IFileType;

/** The interface for graphic formats. */
public interface IGraphicFormat extends IFileType {

  /**
   * Get the default driver of this format
   *
   * @return the default driver of this format
   */
  public abstract IGraphicDriver getDefaultDriver();

  /**
   * <p>
   * Is this graphic format representing an (infinite-precision) vector
   * graphic or a (finite precision) pixel (i.e., raster) graphic?
   * </p>
   * <p>
   * A vector graphic can draw objects with perceived infinite precision.
   * For instance, a horizontal line that consists of 1'000'000 points will
   * be presented as, well, line with 1'000'000 points in a vector graphic.
   * A pixel (raster) graphic, may, for example, map to 1024*640 pixel. A
   * horizontal line will then only consist of 1024 points, regardless of
   * its logical point count. This may play a role when rendering graphics
   * to a file. A vector graphic can become really huge if complex objects
   * are rendered into it. A pixel graphic cannot exceed a given maximum
   * size regardless.
   * </p>
   *
   * @return {@code true} if the format is a vector graphic, {@code false}
   *         if it is a pixel graphic
   */
  public abstract boolean isVectorFormat();
}
