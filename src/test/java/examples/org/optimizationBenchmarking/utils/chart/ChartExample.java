package examples.org.optimizationBenchmarking.utils.chart;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.chart.spec.ITitledElement;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;

import shared.randomization.LoremIpsum;

/** The abstract base class for chart examples */
public abstract class ChartExample implements Runnable {

  /** the legend modes */
  private static final ELegendMode[] LEGEND_MODES = ELegendMode.values();
  /** the line types */
  private static final ELineType[] LINE_TYPES = ELineType.values();

  /** the chart selector */
  private final IChartSelector m_selector;

  /** the styles */
  private final IBasicStyles m_styles;

  /** the random number generator */
  private final Random m_random;

  /**
   * create
   *
   * @param selector
   *          the chart selector
   * @param styles
   *          the basic styles
   * @param random
   *          the random number generator
   */
  protected ChartExample(final IChartSelector selector,
      final IBasicStyles styles, final Random random) {
    super();
    if (styles == null) {
      throw new IllegalArgumentException("Styles must not be null."); //$NON-NLS-1$
    }
    if (selector == null) {
      throw new IllegalArgumentException(
          "Chart selector must not be null."); //$NON-NLS-1$
    }
    this.m_selector = selector;
    this.m_random = ((random == null) ? new Random() : random);
    this.m_styles = styles;
  }

  /**
   * Perform the chart example
   *
   * @param selector
   *          the chart selector
   * @param styles
   *          the style set
   * @param random
   *          a random number generator
   */
  public abstract void perform(final IChartSelector selector,
      final IBasicStyles styles, final Random random);

  /**
   * Obtain a random legend mode
   *
   * @param rand
   *          the random number generator
   * @return the legend mode
   */
  protected static final ELegendMode randomLegendMode(final Random rand) {
    return ChartExample.LEGEND_MODES[rand
        .nextInt(ChartExample.LEGEND_MODES.length)];
  }

  /**
   * Obtain a random line type
   *
   * @param rand
   *          the random number generator
   * @return the line type
   */
  protected static final ELineType randomLineType(final Random rand) {
    return ChartExample.LINE_TYPES[rand
        .nextInt(ChartExample.LINE_TYPES.length)];
  }

  /**
   * Create a random font
   *
   * @param styles
   *          the styles
   * @param random
   *          the random number generator
   * @return the font
   */
  protected static final Font randomFont(final IBasicStyles styles,
      final Random random) {
    final String family;
    int style;

    switch (random.nextInt(4)) {
      case 0: {
        return styles.getCodeFont().getFont();
      }
      case 1: {
        return styles.getEmphFont().getFont();
      }
      case 2: {
        return styles.getDefaultFont().getFont();
      }
      default: {
        switch (random.nextInt(4)) {
          case 0: {
            family = Font.DIALOG;
            break;
          }
          case 1: {
            family = Font.DIALOG_INPUT;
            break;
          }
          case 2: {
            family = Font.MONOSPACED;
            break;
          }
          case 3: {
            family = Font.SANS_SERIF;
            break;
          }
          default: {
            family = Font.SERIF;
            break;
          }
        }
        style = Font.PLAIN;
        if (random.nextBoolean()) {
          style |= Font.BOLD;
        }
        if (random.nextBoolean()) {
          style |= Font.ITALIC;
        }
        return new Font(family, style, 8 + random.nextInt(5));
      }
    }
  }

  /**
   * Set the title of a titled element
   *
   * @param element
   *          the element
   * @param styles
   *          the style set
   * @param rand
   *          the random number generator
   * @param title
   *          a string to use
   */
  protected static final void setTitle(final ITitledElement element,
      final String title, final Random rand, final IBasicStyles styles) {
    final String useTitle;

    if (rand.nextBoolean()) {
      if (title != null) {
        useTitle = title;
      } else {
        useTitle = LoremIpsum.loremIpsum(rand, 4);
      }

      element.setTitle(useTitle);
      if (rand.nextBoolean()) {
        element.setTitleFont(ChartExample.randomFont(styles, rand));
      }

    }
  }

  /**
   * create a random color
   *
   * @param styles
   *          the styles
   * @param rand
   *          the random number generator
   * @return the color
   */
  protected static final Color randomColor(final IBasicStyles styles,
      final Random rand) {
    int r, g, b, color;

    switch (rand.nextInt(13)) {
      case 0: {
        return Color.red;
      }
      case 1: {
        return Color.blue;
      }
      case 2: {
        return Color.green;
      }
      case 3: {
        return Color.gray;
      }
      case 4: {
        return Color.cyan;
      }
      case 5: {
        return Color.darkGray;
      }
      case 6: {
        return Color.lightGray;
      }
      case 7: {
        return Color.magenta;
      }
      case 8: {
        return Color.yellow;
      }
      case 9: {
        return Color.pink;
      }
      case 10: {
        return Color.orange;
      }
      default: {

        r = rand.nextInt(256);
        g = rand.nextInt(256);
        b = rand.nextInt(256);

        color = ((r << 16) | (g << 8) | b);
        if (((r < g) ? ((g < b) ? b : g) : ((r < b) ? b : r)) <= 60) {
          color |= 0x070707;
        }
        if ((r + g + b) >= 600) {
          color &= 0x7f7f7f;
        }
        return new Color(color);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    this.perform(this.m_selector, this.m_styles, this.m_random);
  }

  /**
   * create a random chart example
   *
   * @param selector
   *          the chart selector
   * @param styles
   *          the basic styles
   * @param random
   *          the random number generator
   * @return the chart example
   */
  public static final ChartExample createRandomChartExample(
      final IChartSelector selector, final IBasicStyles styles,
      final Random random) {
    return new SimplePieChartExample(selector, styles, random);
  }
}
