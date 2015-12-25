package org.optimizationBenchmarking.utils.graphics;

import java.awt.image.BufferedImage;

/**
 * This enum holds some constants for the color models which may be
 * supported by our graphic drivers.
 */
public enum EColorModel {

  /** 8 bit gray scale */
  GRAY_8_BIT(BufferedImage.TYPE_BYTE_GRAY, false, "8bit Grayscale"), //$NON-NLS-1$
  /** 15 bit rgb */
  RGB_15_BIT(BufferedImage.TYPE_USHORT_555_RGB, true, "15bit RGB"), //$NON-NLS-1$
  /** 16 bit gray scale */
  GRAY_16_BIT(BufferedImage.TYPE_USHORT_GRAY, false, "16bit Grayscale"), //$NON-NLS-1$
  /** 16 bit rgb */
  RGB_16_BIT(BufferedImage.TYPE_USHORT_565_RGB, true, "16bit RGB"), //$NON-NLS-1$
  /** 24 bits for rbg */
  RGB_24_BIT(BufferedImage.TYPE_INT_RGB, true, "24bit RGB"), //$NON-NLS-1$
  /** 32 bits for RGB and alpha */
  ARGB_32_BIT(BufferedImage.TYPE_INT_ARGB, true, "32bit RGBA"); //$NON-NLS-1$

  /**
   * the buffered image type, for use with
   * {@link java.awt.image.BufferedImage}
   */
  private final int m_biType;

  /** is this a coloful model?, to be returned by {@link #isColor()} */
  private final boolean m_isColor;

  /** the name, to be returned by {@link #toString()} */
  private final String m_name;

  /**
   * Create the image color model
   *
   * @param biType
   *          the buffered image type, for use with
   *          {@link java.awt.image.BufferedImage}
   * @param name
   *          the name, to be returned by {@link #toString()}
   * @param isColor
   *          is this a coloful model?, to be returned by
   *          {@link #isColor()}
   */
  private EColorModel(final int biType, final boolean isColor,
      final String name) {
    this.m_biType = biType;
    this.m_name = name;
    this.m_isColor = isColor;
  }

  /**
   * Get the color model type to be used when instantiating the
   * {@link java.awt.image.BufferedImage buffered image}
   *
   * @return the color model type to be used when instantiating the
   *         {@link java.awt.image.BufferedImage buffered image}
   */
  public final int getBufferedImageType() {
    return this.m_biType;
  }

  /**
   * Is the model a colorful color model ({@code true}) or a
   * grayscale-based model ({@code false})?
   *
   * @return {@code true} for colorful color models, {@code false} for
   *         grayscale ones
   */
  public final boolean isColor() {
    return this.m_isColor;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_name;
  }
}
