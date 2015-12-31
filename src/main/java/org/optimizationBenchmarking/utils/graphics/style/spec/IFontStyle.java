package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.awt.Font;
import java.util.Collection;

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

  /**
   * Get the list of font name or face choices. The choices are ordered by
   * priority, i.e., the first list element represents the
   * {@link #getFont() physical font} of the style, the following elements
   * may represent aliases, and the last element may represent the
   * {@link #getFamily() font family}. The aim is to provide a list of
   * fonts that an underlying physical system may try to load in order to
   * create the same physical appearance. The earlier the element in the
   * list which could finally be loaded, the more similar the appearance
   * should be.
   *
   * @return the list of font name or face choices
   */
  public abstract Collection<String> getFaceChoices();
}
