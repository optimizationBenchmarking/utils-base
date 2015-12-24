package org.optimizationBenchmarking.utils.graphics.style.spec;

import org.optimizationBenchmarking.utils.graphics.EFontFamily;

/** A font palette. */
public interface IFontPalette
    extends IStylePalette<IFontStyle>, IBasicFonts {
  /**
   * Find the font style most similar to a given setup
   *
   * @param family
   *          the font family
   * @param bold
   *          is the font bold?
   * @param italic
   *          is the font italic?
   * @param underlined
   *          is the font underlined?
   * @param size
   *          the size of the font
   * @return the font style
   */
  public abstract IFontStyle getMostSimilarFont(final EFontFamily family,
      final boolean bold, final boolean italic, final boolean underlined,
      final float size);
}
