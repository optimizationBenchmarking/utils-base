package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Font;

/** A font style. */
public interface IFontStyle extends IStyle {

  /**
   * Get the {@link java.awt.Font font} associated with this style.
   *
   * @return the {@link java.awt.Font font} associated with this style.
   */
  public abstract Font getFont();

}
