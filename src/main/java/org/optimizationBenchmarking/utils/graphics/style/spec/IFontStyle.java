package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Font;

import org.optimizationBenchmarking.utils.graphics.EFontFamily;

/** A font style. */
public interface IFontStyle extends IStyle {

  /**
   * Get the {@link java.awt.Font font} associated with this style.
   *
   * @return the {@link java.awt.Font font} associated with this style.
   */
  public abstract Font getFont();

  /**
   * Get the font family
   *
   * @return the font family, or {@code null} if no family is defined
   */
  public abstract EFontFamily getFamily();

  /**
   * Is this font italic?
   *
   * @return {@code true} for italic fonts, {@code false} for normal ones
   */
  public abstract boolean isItalic();

  /**
   * Is this font bold?
   *
   * @return {@code true} for bold fonts, {@code false} for normal ones
   */
  public abstract boolean isBold();

  /**
   * Is this font underlined?
   *
   * @return {@code true} for underlined fonts, {@code false} for normal
   *         ones
   */
  public abstract boolean isUnderlined();

  /**
   * Get the {@link java.awt.Font#getStyle() style flags} of this font.
   *
   * @return the style flags, a combination of {@link java.awt.Font#PLAIN},
   *         {@link java.awt.Font#ITALIC}, and {@link java.awt.Font#BOLD}
   */
  public abstract int getStyle();

  /**
   * Get the size of this font
   *
   * @return the size of this font
   */
  public abstract int getSize();
}
