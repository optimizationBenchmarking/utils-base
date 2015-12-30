package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Graphics;

import org.optimizationBenchmarking.utils.IScope;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A style element is a uniquely identifyable resource within a style
 * palette.
 */
public interface IStyle {

  /**
   * Apply a style to a graphic. The returned instance of
   * {@link org.optimizationBenchmarking.utils.IScope} marks the scope of
   * the style application. When it is
   * {@linkplain org.optimizationBenchmarking.utils.IScope#close()} method
   * is called, the parameters of the graphics object modified by the style
   * application shall be reset to their state before the style
   * application.
   *
   * @param g
   *          the graphic
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.IScope}
   */
  public abstract IScope applyTo(final Graphics g);

  /**
   * Append the name of this style to a given text output
   *
   * @param textCase
   *          the text case to use
   * @param dest
   *          the text output destination
   * @param omitDefaults
   *          should default features be omitted?
   * @return {@code true} if something was appended to {@code dest},
   *         {@code false} otherwise
   */
  public abstract boolean appendDescription(final ETextCase textCase,
      final ITextOutput dest, final boolean omitDefaults);

  /**
   * Obtain the ID of this style. This id is unique within the owning
   * palette.
   *
   * @return a unique ID for this style
   */
  public abstract String getID();

}
