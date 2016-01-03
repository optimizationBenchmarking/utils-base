package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;

import org.optimizationBenchmarking.utils.IScope;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokeStyle;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The example basic styles. */
public class ExampleBasicStyles implements IBasicStyles {

  /** the black color */
  private final __ColorStyle m_black;
  /** the white color */
  private final __ColorStyle m_white;

  /** the thin stroke */
  private final __StrokeStyle m_thinStroke;
  /** the thick stroke */
  private final __StrokeStyle m_thickStroke;
  /** the default stroke */
  private final __StrokeStyle m_defaultStroke;

  /** the default font */
  private final __FontStyle m_defaultFont;
  /** the emph font */
  private final __FontStyle m_emphFont;
  /** the code font */
  private final __FontStyle m_codeFont;

  /** create */
  public ExampleBasicStyles() {
    super();
    this.m_black = new __ColorStyle(Color.BLACK);
    this.m_white = new __ColorStyle(Color.WHITE);
    this.m_thinStroke = new __StrokeStyle(new BasicStroke(0.02f), "thin"); //$NON-NLS-1$
    this.m_thickStroke = new __StrokeStyle(new BasicStroke(2.25f),
        "thick"); //$NON-NLS-1$
    this.m_defaultStroke = new __StrokeStyle(new BasicStroke(1f),
        "default"); //$NON-NLS-1$
    this.m_defaultFont = new __FontStyle(new Font("Arial", Font.PLAIN, 11), //$NON-NLS-1$
        "normal", EFontFamily.SANS_SERIF); //$NON-NLS-1$
    this.m_emphFont = new __FontStyle(
        new Font("Times New Roman", Font.ITALIC, 11), //$NON-NLS-1$
        "emphasize", EFontFamily.SERIF); //$NON-NLS-1$
    this.m_codeFont = new __FontStyle(
        new Font("Courier New", Font.PLAIN, 10), //$NON-NLS-1$
        "code", EFontFamily.MONOSPACED); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final IColorStyle getBlack() {
    return this.m_black;
  }

  /** {@inheritDoc} */
  @Override
  public final IColorStyle getWhite() {
    return this.m_white;
  }

  /** {@inheritDoc} */
  @Override
  public final void initializeWithDefaults(final Graphics graphics) {
    final Graphics2D g2d;
    final Rectangle2D rectangle;

    graphics.setFont(this.m_defaultFont.getFont());

    graphics.setColor(this.m_white.getColor());
    rectangle = GraphicUtils.getBounds(graphics);
    if (graphics instanceof Graphics2D) {
      g2d = ((Graphics2D) graphics);
      g2d.fill(rectangle);
      g2d.setStroke(this.m_defaultStroke.getStroke());
    } else {
      graphics.fillRect(((int) (rectangle.getX())), //
          ((int) (rectangle.getY())), //
          ((int) (rectangle.getWidth())), //
          ((int) (rectangle.getHeight())));
    }
    graphics.setColor(this.m_black.getColor());
  }

  /** {@inheritDoc} */
  @Override
  public final IStrokeStyle getDefaultStroke() {
    return this.m_defaultStroke;
  }

  /** {@inheritDoc} */
  @Override
  public final IStrokeStyle getThinStroke() {
    return this.m_thinStroke;
  }

  /** {@inheritDoc} */
  @Override
  public final IStrokeStyle getThickStroke() {
    return this.m_thickStroke;
  }

  /** {@inheritDoc} */
  @Override
  public final IFontStyle getDefaultFont() {
    return this.m_defaultFont;
  }

  /** {@inheritDoc} */
  @Override
  public final IFontStyle getEmphFont() {
    return this.m_emphFont;
  }

  /** {@inheritDoc} */
  @Override
  public final IFontStyle getCodeFont() {
    return this.m_codeFont;
  }

  /**
   * A scoped application of a style. In their constructor, sub-classes
   * will setup the provided {@link java.awt.Graphics} with their
   * style-specific settings after storing the corresponding current setup.
   * In the {@link #_cleanUp(Graphics)} method, they restore of saved
   * state.
   */
  static abstract class _StyleApplication implements IScope {

    /** the graphic used */
    private final Graphics m_graphics;

    /** has the {@link #close()} method already been called? */
    private boolean m_closed;

    /**
     * the style
     *
     * @param graphics
     *          the graphics
     */
    _StyleApplication(final Graphics graphics) {
      super();
      if (graphics == null) {
        throw new IllegalArgumentException(//
            "The graphics cannot be null."); //$NON-NLS-1$
      }
      this.m_graphics = graphics;
    }

    /**
     * clean up the graphic object
     *
     * @param graphics
     *          the graphics
     */
    void _cleanUp(final Graphics graphics) {
      // nothing
    }

    /** {@inheritDoc} */
    @Override
    public final void close() {
      final boolean done;
      done = this.m_closed;
      this.m_closed = true;
      if (!done) {
        this._cleanUp(this.m_graphics);
      }
    }
  }

  /** the color style */
  private static final class __ColorStyle implements IColorStyle {

    /** the color */
    private final Color m_color;

    /**
     * create the color style
     *
     * @param color
     *          the color to copy
     */
    __ColorStyle(final Color color) {
      super();
      this.m_color = color;
    }

    /** {@inheritDoc} */
    @Override
    public final IScope applyTo(final Graphics graphics) {
      return new __ColorApplication(graphics, this.m_color);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean appendDescription(final ETextCase textCase,
        final ITextOutput dest, final boolean omitDefaults) {
      textCase.appendWords(this.m_color.toString(), dest);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public final String getID() {
      return this.m_color.toString();
    }

    /** {@inheritDoc} */
    @Override
    public final Color getColor() {
      return this.m_color;
    }

    /** the color application */
    private final class __ColorApplication extends _StyleApplication {

      /** the color */
      private final Color m_oldColor;

      /**
       * the style
       *
       * @param graphics
       *          the graphic
       * @param color
       *          the color
       */
      __ColorApplication(final Graphics graphics, final Color color) {
        super(graphics);
        this.m_oldColor = graphics.getColor();
        graphics.setColor(color);
      }

      /** {@inheritDoc} */
      @Override
      final void _cleanUp(final Graphics graphics) {
        graphics.setColor(this.m_oldColor);
      }
    }
  }

  /** the stroke style */
  private static final class __StrokeStyle implements IStrokeStyle {
    /** the basic stroke */
    private final BasicStroke m_stroke;
    /** the name */
    private final String m_name;

    /**
     * create the stroke style
     *
     * @param stroke
     *          the stroke
     * @param name
     *          the name
     */
    __StrokeStyle(final BasicStroke stroke, final String name) {
      super();
      this.m_stroke = stroke;
      this.m_name = name;
    }

    /** {@inheritDoc} */
    @Override
    public final IScope applyTo(final Graphics graphics) {
      return new __StrokeApplication(graphics, this.m_stroke);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean appendDescription(final ETextCase textCase,
        final ITextOutput dest, final boolean omitDefaults) {
      textCase.appendWord(this.m_name, dest);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public final String getID() {
      return this.m_name;
    }

    /** {@inheritDoc} */
    @Override
    public final Stroke getStroke() {
      return this.m_stroke;
    }

    /** the stroke application */
    private static final class __StrokeApplication
        extends _StyleApplication {

      /** the stroke */
      private final Stroke m_oldStroke;

      /**
       * the style
       *
       * @param graphics
       *          the graphic
       * @param stroke
       *          the stroke
       */
      __StrokeApplication(final Graphics graphics, final Stroke stroke) {
        super(graphics);

        final Graphics2D g2d;

        if (graphics instanceof Graphics2D) {
          g2d = ((Graphics2D) graphics);
          this.m_oldStroke = g2d.getStroke();
          g2d.setStroke(stroke);
        } else {
          this.m_oldStroke = null;
        }
      }

      /** {@inheritDoc} */
      @Override
      final void _cleanUp(final Graphics graphics) {
        if ((this.m_oldStroke != null)
            && (graphics instanceof Graphics2D)) {
          ((Graphics2D) graphics).setStroke(this.m_oldStroke);
        }
      }
    }
  }

  /** the font style */
  private static final class __FontStyle implements IFontStyle {
    /** the font */
    private final Font m_font;
    /** the name */
    private final String m_name;
    /** the font family */
    private final EFontFamily m_family;

    /** the font faces */
    private final ArrayListView<String> m_faces;

    /**
     * create
     *
     * @param font
     *          the font
     * @param name
     *          the font name
     * @param family
     *          the font family
     */
    __FontStyle(final Font font, final String name,
        final EFontFamily family) {
      super();

      final LinkedHashSet<String> set;

      this.m_font = font;
      this.m_name = name;
      this.m_family = family;

      set = new LinkedHashSet<>();
      set.add(font.getFontName());
      set.add(font.getName());
      set.add(font.getFamily());
      set.add(font.getFamily(Locale.ENGLISH));
      set.add(font.getPSName());
      set.add(family.getFontFamilyName());
      set.remove(null);
      this.m_faces = ArrayListView.collectionToView(set);
    }

    /** {@inheritDoc} */
    @Override
    public final IScope applyTo(final Graphics graphics) {
      return new __FontApplication(graphics, this.m_font);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean appendDescription(final ETextCase textCase,
        final ITextOutput dest, final boolean omitDefaults) {
      textCase.appendWord(this.m_name, dest);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public final String getID() {
      return this.m_name;
    }

    /** {@inheritDoc} */
    @Override
    public final Font getFont() {
      return this.m_font;
    }

    /** {@inheritDoc} */
    @Override
    public final EFontFamily getFamily() {
      return this.m_family;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean isItalic() {
      return this.m_font.isItalic();
    }

    /** {@inheritDoc} */
    @Override
    public final boolean isBold() {
      return this.m_font.isBold();
    }

    /** {@inheritDoc} */
    @Override
    public final boolean isUnderlined() {
      return GraphicUtils.isFontUnderlined(this.m_font);
    }

    /** {@inheritDoc} */
    @Override
    public final int getStyle() {
      return this.m_font.getStyle();
    }

    /** {@inheritDoc} */
    @Override
    public final int getSize() {
      return this.m_font.getSize();
    }

    /** {@inheritDoc} */
    @Override
    public final Collection<String> getFaceChoices() {
      return this.m_faces;
    }

    /** the font application */
    private static final class __FontApplication
        extends _StyleApplication {

      /** the font */
      private final Font m_oldFont;

      /**
       * the style
       *
       * @param graphics
       *          the graphic
       * @param font
       *          the font
       */
      __FontApplication(final Graphics graphics, final Font font) {
        super(graphics);
        this.m_oldFont = graphics.getFont();
        graphics.setFont(font);
      }

      /** {@inheritDoc} */
      @Override
      final void _cleanUp(final Graphics graphics) {
        graphics.setFont(this.m_oldFont);
      }
    }
  }
}
