package examples.org.optimizationBenchmarking.utils.document;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ICode;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IEquation;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabeledObject;
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.document.spec.ITable;
import org.optimizationBenchmarking.utils.document.spec.ITableRow;
import org.optimizationBenchmarking.utils.document.spec.ITableSection;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokeStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStyles;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import examples.org.optimizationBenchmarking.utils.bibliography.RandomBibliography;
import examples.org.optimizationBenchmarking.utils.chart.ChartExample;
import shared.randomization.LoremIpsum;
import shared.randomization.RandomUtils;

/**
 * <p>
 * An example used to illustrate how documents can be created with the
 * document output API both serial and in parallel.
 * </p>
 * <p>
 * If more than one processor is specified, then the creation of each of
 * the (nested) sections in a document is launched as a separate
 * {@link java.util.concurrent.RecursiveAction ForkJoinTask}. If only one
 * processor is specified, no parallelization will be performed and all
 * produced documents should look approximately the same, except for
 * necessary adaptation to the underlying output format.
 * </p>
 * <p>
 * The class can be used for testing purposes, in which case the
 * constructor also receives a timeout parameter. This parameter is a
 * suggestion and best effort is used to not exceed it, however the actual
 * runtime may be arbitrarily much longer.
 * </p>
 */
public class RandomDocumentExample extends DocumentExample {

  /** the comparison */
  private static final EMathComparison[] COMP = EMathComparison.values();
  /** the modes */
  private static final ECitationMode[] CITES = ECitationMode.values();
  /** the sequence modes */
  private static final ESequenceMode[] SEQUENCE = ESequenceMode.values();
  /** the label types */
  private static final ELabelType[] LABEL_TYPES = ELabelType.values();

  /** the seed to use */
  private static final long SEED = new Random().nextLong();

  /** the maximum code depth: {@value} */
  private static final int CODE_MAX_DEPTH = 8;
  /** the spaces */
  private static final char[] SPACES = new char[RandomDocumentExample.CODE_MAX_DEPTH << 1];

  static {
    Arrays.fill(RandomDocumentExample.SPACES, ' ');
  }

  /** the maximum allowed section depth */
  private static final int MAX_ALLOWED_SECTION_DEPTH = 4;

  /** the choices for complex text */
  private static final _ERandomDocumentExampleElements[] COMPLEX_TEXT = {
      _ERandomDocumentExampleElements.IN_BRACES,
      _ERandomDocumentExampleElements.IN_QUOTES,
      _ERandomDocumentExampleElements.WITH_FONT,
      _ERandomDocumentExampleElements.WITH_COLOR,
      _ERandomDocumentExampleElements.EMPH,
      _ERandomDocumentExampleElements.INLINE_CODE,
      _ERandomDocumentExampleElements.SUBSCRIPT,
      _ERandomDocumentExampleElements.SUPERSCRIPT,
      _ERandomDocumentExampleElements.INLINE_MATH,
      _ERandomDocumentExampleElements.REFERENCE,
      _ERandomDocumentExampleElements.CITATION, };
  /** the choices for simple text */
  private static final _ERandomDocumentExampleElements[] SIMPLE_TEXT = {
      _ERandomDocumentExampleElements.IN_BRACES,
      _ERandomDocumentExampleElements.IN_QUOTES, };
  /** the choices for inside a section */
  private static final _ERandomDocumentExampleElements[] SECTION_PLAIN = {
      _ERandomDocumentExampleElements.SECTION,
      _ERandomDocumentExampleElements.ENUM,
      _ERandomDocumentExampleElements.ITEMIZE,
      _ERandomDocumentExampleElements.FIGURE,
      _ERandomDocumentExampleElements.FIGURE_SERIES,
      _ERandomDocumentExampleElements.TABLE,
      _ERandomDocumentExampleElements.EQUATION,
      _ERandomDocumentExampleElements.CODE,
      _ERandomDocumentExampleElements.NORMAL_TEXT, };

  /** the maximum runtime: {@value} ms */
  private static final long MAX_RUNTIME = 1400_000L;

  /** the log */
  private final PrintStream m_log;

  /** the randomizer */
  private final Random m_rand;

  /** the fonts */
  private IFontStyle[] m_fonts;

  /** the colors */
  private IColorStyle[] m_colors;

  /** the strokes */
  private IStrokeStyle[] m_strokes;

  /** the labels */
  private final ArrayList<ILabel> m_labels;

  /** the figure counter */
  private volatile long m_figureCounter;

  /** the bibliography */
  private Bibliography m_bib;

  /** the allocated labels */
  private final ArrayList<ILabel>[] m_allocatedLabels;

  /** the start time */
  private _RandomDocumentExampleTermination m_termination;

  /** the maximum runtime */
  private final long m_maxTime;

  /**
   * note a used label
   *
   * @param e
   *          the labeled element
   */
  private final void __useLabel(final ILabeledObject e) {
    final ILabel l;
    if (e != null) {
      l = e.getLabel();
      if (l != null) {
        synchronized (this.m_labels) {
          this.m_labels.add(l);
        }
      }
    }
  }

  /**
   * pop a label
   *
   * @return the label, or {@code null} if the label list is empty
   */
  private final ILabel __getLabel() {
    final int s, i;
    synchronized (this.m_labels) {
      s = this.m_labels.size();
      if (s <= 0) {
        return null;
      }
      i = this.m_rand.nextInt(s);
      if (s > 10) {
        return this.m_labels.remove(i);
      }
      return this.m_labels.get(i);
    }
  }

  /**
   * get the next figure
   *
   * @return the next figure
   */
  private synchronized final long __nextFigure() {
    return (this.m_figureCounter++);
  }

  /**
   * create the header
   *
   * @param header
   *          the header
   */
  private final void __createHeader(final IDocumentHeader header) {
    RandomDocumentExample._createRandomHeader(header,
        this.m_doc.getClass().getSimpleName(), this.m_rand);
  }

  /**
   * create a random header
   *
   * @param header
   *          the header
   * @param clazz
   *          the driver class
   * @param rand
   *          the randomizer
   */
  static final void _createRandomHeader(final IDocumentHeader header,
      final String clazz, final Random rand) {
    boolean first;

    try (final IPlainText t = header.title()) {
      t.append("Report Created by Class "); //$NON-NLS-1$
      t.append(clazz);
    }

    try (final BibAuthorsBuilder babs = header.authors()) {
      try (final BibAuthorBuilder bab = babs.author()) {
        bab.setFamilyName("Weise");//$NON-NLS-1$
        bab.setPersonalName("Thomas");//$NON-NLS-1$
      }
      try (final BibAuthorBuilder bab = babs.author()) {
        bab.setFamilyName("Other");//$NON-NLS-1$
        bab.setPersonalName("Someone");//$NON-NLS-1$
      }
    }

    try (final BibDateBuilder bd = header.date()) {
      bd.fromNow();
    }

    first = true;
    try (final IPlainText t = header.summary()) {
      do {
        if (first) {
          first = false;
        } else {
          t.append(' ');
        }
        LoremIpsum.appendLoremIpsum(t, rand);
      } while (rand.nextInt(5) > 0);
    }
  }

  /**
   * Create a section in a section container
   *
   * @param sc
   *          the section container
   * @param sectionDepth
   *          the section depth
   */
  final void _createSection(final ISectionContainer sc,
      final int sectionDepth) {
    Object error;
    boolean hasSection, hasText, needsText, needsNewLine;
    _ERandomDocumentExampleElements cur, last;
    ArrayList<Future<Void>> ra;
    _SectionTask st;

    hasText = hasSection = false;

    needsText = true;
    needsNewLine = hasText = hasSection = false;

    error = null;
    this.m_termination._done(_ERandomDocumentExampleElements.SECTION);

    try {
      this.m_termination._setMaxSectionDepth(sectionDepth + 1);
      cur = last = null;
      ra = null;

      try (final ISection section = sc.section(
          this.m_termination._getLabel(this.m_rand, ELabelType.SECTION))) {
        this.__useLabel(section);

        try (final IPlainText title = section.title()) {
          LoremIpsum.appendLoremIpsum(title, this.m_rand, 8);
        }

        try (final ISectionBody body = section.body()) {

          main: do {

            if (hasSection) {
              cur = _ERandomDocumentExampleElements.SECTION;
            } else {
              if (needsText) {
                if (sectionDepth > RandomDocumentExample.MAX_ALLOWED_SECTION_DEPTH) {
                  cur = _ERandomDocumentExampleElements.NORMAL_TEXT;
                } else {
                  cur = ((this.m_rand.nextInt(3) > 0)//
                      ? _ERandomDocumentExampleElements.NORMAL_TEXT
                      : _ERandomDocumentExampleElements.SECTION);
                }
              } else {
                cur = this.m_termination._suggest(this.m_rand,
                    RandomDocumentExample.SECTION_PLAIN);
                while ((cur == last)// never have two same
                    || ((cur == _ERandomDocumentExampleElements.FIGURE) && //
                        (last == _ERandomDocumentExampleElements.FIGURE_SERIES))//
                    || ((cur == _ERandomDocumentExampleElements.FIGURE_SERIES)
                        && //
                        (last == _ERandomDocumentExampleElements.FIGURE))//
                    || ((cur == _ERandomDocumentExampleElements.ENUM) && //
                        (last == _ERandomDocumentExampleElements.ITEMIZE))//
                    || ((cur == _ERandomDocumentExampleElements.ITEMIZE) && //
                        (last == _ERandomDocumentExampleElements.ENUM))) {
                  cur = RandomDocumentExample.SECTION_PLAIN[this.m_rand
                      .nextInt(
                          RandomDocumentExample.SECTION_PLAIN.length)];
                }
              }
            }
            last = cur;

            switch (cur) {
              case SECTION: {
                if (sectionDepth > RandomDocumentExample.MAX_ALLOWED_SECTION_DEPTH) {
                  continue main;
                }
                hasSection = true;

                st = new _SectionTask(this, body, (sectionDepth + 1));

                if (ForkJoinTask.inForkJoinPool()) {
                  if (ra == null) {
                    ra = new ArrayList<>();
                  }
                  ra.add(st.fork());
                } else {
                  try {
                    st.compute();
                  } catch (final Throwable tt) {
                    error = ErrorUtils.aggregateError(tt, error);
                    break main;
                  }
                }
                needsText = false;
                break;
              }
              case ENUM: {
                try {
                  this.__createList(body, 0, cur);
                  needsNewLine = false;
                  needsText = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              case ITEMIZE: {
                try {
                  this.__createList(body, 0, cur);
                  needsNewLine = false;
                  needsText = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              case FIGURE: {
                try {
                  this.__createFigure(body);
                  needsText = needsNewLine = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              case FIGURE_SERIES: {
                try {
                  this.__createFigureSeries(body);
                  needsText = needsNewLine = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              case TABLE: {
                try {
                  this.__createTable(body);
                  needsText = needsNewLine = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              case EQUATION: {
                try {
                  this.__createEquation(body);
                  needsText = needsNewLine = false;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              case CODE: {
                try {
                  this.__createCode(body);
                  needsText = needsNewLine = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              default: {
                try {
                  this.__text(body, 0, needsNewLine, true);
                  needsText = false;
                  needsNewLine = hasText = true;
                  break;
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
            }
          } while ((!(hasText || hasSection))
              || (this.m_termination._continue()
                  && this.m_rand.nextBoolean()));

          if (ra != null) {
            looper: for (final Future<Void> f : ra) {
              if (f != null) {
                try {
                  f.get();
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break looper;
                }
              }
            }
          }
        }
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(t, error);
    }
    if (error != null) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while building a section of the example document", //$NON-NLS-1$
          true, error);
    }
  }

  /**
   * print some text
   *
   * @param out
   *          the text output
   * @param depth
   *          the depth
   * @param needsNewLine
   *          needs a new line?
   * @param canLineBreak
   *          can we have a line break?
   */
  @SuppressWarnings("fallthrough")
  private final void __text(final ITextOutput out, final int depth,
      final boolean needsNewLine, final boolean canLineBreak) {
    boolean first, must, needs;
    IStyle s;
    ArrayList<ILabel> labels;
    ILabel l;
    Object error;

    first = true;
    must = (needsNewLine & (!canLineBreak));
    needs = false;
    labels = null;
    error = null;

    this.m_termination._done(_ERandomDocumentExampleElements.NORMAL_TEXT);
    try {
      main: do {
        spacer: {
          if (first) {
            if ((depth <= 0) && must && (!canLineBreak)) {
              out.appendLineBreak();
            }
            break spacer;
          }
          if ((!canLineBreak) || this.m_rand.nextBoolean()) {
            out.append(' ');
          } else {
            out.appendLineBreak();
          }
        }
        first = false;

        if (canLineBreak) {
          LoremIpsum.appendLoremIpsum(out, this.m_rand,
              ((depth > 0) ? 8 : 64));
        } else {
          LoremIpsum.appendLoremIpsum(out, this.m_rand, 4);
        }

        if ((out instanceof IPlainText) && (depth < 10)) {

          switch (this.m_termination._suggest(this.m_rand,
              (out instanceof IComplexText)
                  ? RandomDocumentExample.COMPLEX_TEXT
                  : RandomDocumentExample.SIMPLE_TEXT)) {
            case IN_BRACES: {
              out.append(' ');
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.IN_BRACES);
              try (final IPlainText t = ((IPlainText) out).inBraces()) {
                try {
                  this.__text(t, (depth + 1), this.m_rand.nextBoolean(),
                      canLineBreak);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }
            case IN_QUOTES: {
              out.append(' ');
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.IN_QUOTES);
              try (final IPlainText t = ((IPlainText) out).inQuotes()) {
                try {
                  this.__text(t, (depth + 1), this.m_rand.nextBoolean(),
                      canLineBreak);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }
            case WITH_FONT: {
              out.append(' ');
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.WITH_FONT);
              try (final IPlainText t = ((IComplexText) out)
                  .style((s = this.m_fonts[this.m_rand
                      .nextInt(this.m_fonts.length)]))) {
                try {
                  t.append("In ");//$NON-NLS-1$
                  s.appendDescription(ETextCase.IN_SENTENCE, t, false);
                  t.append(" font: "); //$NON-NLS-1$
                  this.__text(t, (depth + 1), this.m_rand.nextBoolean(),
                      false);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }
            case WITH_COLOR: {
              out.append(' ');
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.WITH_COLOR);
              try (final IPlainText t = ((IComplexText) out)
                  .style((s = this.m_colors[this.m_rand
                      .nextInt(this.m_colors.length)]))) {
                try {
                  t.append("In ");//$NON-NLS-1$
                  s.appendDescription(ETextCase.IN_SENTENCE, t, false);
                  t.append(" color: "); //$NON-NLS-1$
                  this.__text(t, (depth + 1), this.m_rand.nextBoolean(),
                      canLineBreak);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }
            case EMPH: {
              out.append(' ');
              this.m_termination
                  ._done(_ERandomDocumentExampleElements.EMPH);
              try (final IPlainText t = ((IComplexText) out).emphasize()) {
                try {
                  t.append("Something Emphasized: "); //$NON-NLS-1$
                  LoremIpsum.appendLoremIpsum(t, this.m_rand, 5);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }
            case INLINE_CODE: {
              if (canLineBreak) {
                out.append(' ');
                this.m_termination
                    ._done(_ERandomDocumentExampleElements.INLINE_CODE);
                try (final IText t = ((IComplexText) out).inlineCode()) {
                  try {
                    t.append("Inline Code: "); //$NON-NLS-1$
                    LoremIpsum.appendLoremIpsum(t, this.m_rand, 5);
                  } catch (final Throwable tt) {
                    error = ErrorUtils.aggregateError(tt, error);
                    break main;
                  }
                }
                break;
              }
            }
            case SUBSCRIPT: {
              this.m_termination
                  ._done(_ERandomDocumentExampleElements.SUBSCRIPT);
              out.append(" Sub: "); //$NON-NLS-1$
              try (final IPlainText t = ((IComplexText) out).subscript()) {
                try {
                  LoremIpsum.appendLoremIpsum(t, this.m_rand, 2);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }
            case SUPERSCRIPT: {
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.SUPERSCRIPT);
              out.append(" Super: "); //$NON-NLS-1$
              try (final IPlainText t = ((IComplexText) out)
                  .superscript()) {
                try {
                  LoremIpsum.appendLoremIpsum(t, this.m_rand, 2);
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
              break;
            }

            case INLINE_MATH: {
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.INLINE_MATH);
              out.append(" Inline equation: ");//$NON-NLS-1$
              try {
                this.__createInlineMath(((IComplexText) out));
              } catch (final Throwable tt) {
                error = ErrorUtils.aggregateError(tt, error);
                break main;
              }
              break;
            }

            case REFERENCE: {
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.REFERENCE);
              try {
                if (labels == null) {
                  labels = new ArrayList<>();
                }
                do {
                  l = this.__getLabel();
                  if (l == null) {
                    break;
                  }
                  if (labels.contains(l)) {
                    continue;
                  }
                  labels.add(l);
                } while (this.m_rand.nextBoolean());

                if (labels.size() > 0) {
                  out.append(" And here we reference");//$NON-NLS-1$

                  ((IComplexText) out).reference(ETextCase.IN_SENTENCE,
                      RandomDocumentExample.SEQUENCE[this.m_rand
                          .nextInt(RandomDocumentExample.SEQUENCE.length)],
                      labels.toArray(new ILabel[labels.size()]));
                  labels.clear();
                  out.append('.');
                }
              } catch (final Throwable tt) {
                error = ErrorUtils.aggregateError(tt, error);
                break main;
              }
              break;
            }

            case CITATION: {
              this.m_termination._done(//
                  _ERandomDocumentExampleElements.CITATION);
              try {
                out.append(" And here we cite");//$NON-NLS-1$

                try (BibliographyBuilder bb = ((IComplexText) out).cite(
                    RandomDocumentExample.CITES[this.m_rand
                        .nextInt(RandomDocumentExample.CITES.length)],
                    ETextCase.IN_SENTENCE,
                    RandomDocumentExample.SEQUENCE[this.m_rand.nextInt(
                        RandomDocumentExample.SEQUENCE.length)])) {

                  do {
                    bb.add(this.m_bib
                        .get(this.m_rand.nextInt(this.m_bib.size())));
                  } while (this.m_rand.nextBoolean());
                }
                out.append('.');
              } catch (final Throwable tt) {
                error = ErrorUtils.aggregateError(tt, error);
                break main;
              }
              break;
            }
            default: {
              throw new IllegalStateException();
            }
          }

          needs = true;
        }

        if (needs && (this.m_rand.nextBoolean())) {
          try {
            out.append(' ');

            if (canLineBreak) {
              LoremIpsum.appendLoremIpsum(out, this.m_rand,
                  ((depth > 0) ? 8 : 64));
            } else {
              LoremIpsum.appendLoremIpsum(out, this.m_rand, 4);
            }

            this.m_termination._done(//
                _ERandomDocumentExampleElements.NORMAL_TEXT);
          } catch (final Throwable tt) {
            error = ErrorUtils.aggregateError(tt, error);
            break main;
          }
        }

        first = false;
      } while ((this.m_termination._continue())
          && ((this.m_rand.nextInt(depth + 2) <= 0)));
    } catch (final Throwable a) {
      error = ErrorUtils.aggregateError(a, error);
    }
    if (error != null) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while adding text to example document.", //$NON-NLS-1$
          true, error);
    }
  }

  /**
   * create a list
   *
   * @param sb
   *          the section body
   * @param listDepth
   *          the list depth
   * @param type
   *          the list type
   */
  private final void __createList(final IStructuredText sb,
      final int listDepth, final _ERandomDocumentExampleElements type) {
    int ic;

    this.m_termination._done(type);

    try (final IList list = ((_ERandomDocumentExampleElements.ENUM == type)//
        ? sb.enumeration() : sb.itemization())) {
      ic = 0;
      do {
        try (final IStructuredText item = list.item()) {
          if (this.m_rand.nextInt(10) <= 0) {
            this.__text(item, 0, false, true);
          } else {
            LoremIpsum.appendLoremIpsum(item, this.m_rand, 32);
          }
          if ((listDepth < 3)
              && (this.m_rand.nextInt(listDepth + 5) <= 0)) {
            this.__createList(item, (listDepth + 1),
                this.m_rand.nextBoolean()
                    ? _ERandomDocumentExampleElements.ENUM
                    : _ERandomDocumentExampleElements.ITEMIZE);
          }
        }
      } while (this.m_termination._continue()
          && (((++ic) <= 2) || (this.m_rand.nextBoolean())));

    }
  }

  /**
   * create the body
   *
   * @param body
   *          the body
   */
  private final void __createBody(final IDocumentBody body) {
    do {
      this._createSection(body, 0);
    } while (this.m_termination._isSomethingMissing()
        || (this.m_termination._continue()
            && (this.m_rand.nextBoolean())));
  }

  /**
   * create the footer
   *
   * @param footer
   *          the footer
   */
  private final void __createFooter(final IDocumentBody footer) {
    do {
      this._createSection(footer, 0);
    } while (this.m_termination._continue()
        && (this.m_rand.nextBoolean()));
  }

  /**
   * create
   *
   * @param doc
   *          the document
   * @param r
   *          the randomizer
   * @param log
   *          the log stream
   */
  public RandomDocumentExample(final IDocument doc, final Random r,
      final PrintStream log) {
    this(doc, r, log, RandomDocumentExample.MAX_RUNTIME);
  }

  /**
   * create
   *
   * @param doc
   *          the document
   * @param r
   *          the randomizer
   * @param log
   *          the log stream
   * @param maxTime
   *          a goal suggestion for the maximum runtime
   */
  @SuppressWarnings("unchecked")
  public RandomDocumentExample(final IDocument doc, final Random r,
      final PrintStream log, final long maxTime) {
    super(doc);
    int i;

    if (r == null) {
      this.m_rand = new Random();
      this.m_rand.setSeed(RandomDocumentExample.SEED);
    } else {
      this.m_rand = r;
    }
    this.m_labels = new ArrayList<>();

    this.m_allocatedLabels = new ArrayList[i = RandomDocumentExample.LABEL_TYPES.length];
    for (; (--i) >= 0;) {
      this.m_allocatedLabels[i] = new ArrayList<>();
    }
    this.m_log = log;
    this.m_maxTime = Math.max(3L, maxTime);
  }

  /** create the bibliography */
  private final void __createBib() {
    this.m_bib = new RandomBibliography(this.m_rand).call();
  }

  /** pre-allocate some labels */
  private final void __preAllocateLabels() {
    int i;
    ILabel l;
    ArrayList<ILabel> a;

    do {
      i = this.m_rand.nextInt(RandomDocumentExample.LABEL_TYPES.length);
      l = this.m_doc.createLabel(RandomDocumentExample.LABEL_TYPES[i]);
      a = this.m_allocatedLabels[i];

      synchronized (a) {
        a.add(l);
      }

      a = this.m_labels;
      synchronized (a) {
        a.add(l);
      }
    } while (this.m_rand.nextInt(7) > 0);
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    Object error;
    long end;

    error = null;
    end = (System.currentTimeMillis() + this.m_maxTime);
    main: {
      try {
        try {
          if (this.m_log != null) {
            synchronized (this.m_log) {
              this.m_log.print("Begin creating document "); //$NON-NLS-1$
              this.m_log.println(this.m_doc);
            }
          }

          this.m_labels.clear();
          for (final ArrayList<ILabel> l : this.m_allocatedLabels) {
            l.clear();
          }
          this.m_figureCounter = 0L;

          try {
            this.__loadStyles();
          } catch (final Throwable tt) {
            error = ErrorUtils.aggregateError(tt, error);
            break main;
          }
          try {
            this.__createBib();
          } catch (final Throwable tt) {
            error = ErrorUtils.aggregateError(tt, error);
            break main;
          }
          try {
            this.__preAllocateLabels();
          } catch (final Throwable tt) {
            error = ErrorUtils.aggregateError(tt, error);
            break main;
          }

          try (final IDocumentHeader header = this.m_doc.header()) {
            try {
              this.__createHeader(header);
            } catch (final Throwable tt) {
              error = ErrorUtils.aggregateError(tt, error);
              break main;
            }
          } finally {
            try (final IDocumentBody body = this.m_doc.body()) {
              try {
                this.m_termination = new _RandomDocumentExampleTermination(
                    this.m_allocatedLabels, (Math.max(1L,
                        (end - System.currentTimeMillis())) >>> 2));
                try {
                  this.__createBody(body);
                } finally {
                  this.m_termination._terminate();
                  this.m_termination = null;
                }
              } catch (final Throwable tt) {
                error = ErrorUtils.aggregateError(tt, error);
                break main;
              }
            } finally {
              try (final IDocumentBody footer = this.m_doc.footer()) {
                try {
                  this.m_termination = new _RandomDocumentExampleTermination(
                      this.m_allocatedLabels, (Math.max(1L,
                          (end - System.currentTimeMillis())) >>> 2));
                  try {
                    this.m_termination._assumeAllDone();
                    this.__createFooter(footer);
                  } finally {
                    this.m_termination._terminate();
                    this.m_termination = null;
                  }
                } catch (final Throwable tt) {
                  error = ErrorUtils.aggregateError(tt, error);
                  break main;
                }
              }
            }
          }
        } finally {
          try {
            try {
              this.m_doc.close();
            } catch (final Throwable tt) {
              error = ErrorUtils.aggregateError(tt, error);
              break main;
            }
          } finally {
            if (this.m_log != null) {
              synchronized (this.m_log) {
                this.m_log.print("Finished creating document "); //$NON-NLS-1$
                this.m_log.println(this.m_doc);
              }
            }
          }
        }
      } catch (final Throwable ttt) {
        error = ErrorUtils.aggregateError(ttt, error);
      }
    }

    if (error != null) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while creating the global structure of the example document.", //$NON-NLS-1$
          true, error);
    }
  }

  /** load the styles */
  private final void __loadStyles() {
    final IStyles ss;
    final ArrayList<Object> list;
    HashSet<Object> set;

    ss = this.m_doc.getStyles();
    list = new ArrayList<>();

    set = new HashSet<>();
    while (set.add(ss.allocateFont())) {/* */
    }
    list.addAll(set);
    set.clear();

    list.add(ss.getCodeFont());
    list.add(ss.getDefaultFont());
    list.add(ss.getEmphFont());
    this.m_fonts = list.toArray(new IFontStyle[list.size()]);

    list.clear();
    while (set.add(ss.allocateColor())) {/* */
    }
    list.addAll(set);
    set.clear();
    list.add(ss.getBlack());
    this.m_colors = list.toArray(new IColorStyle[list.size()]);

    list.clear();
    while (set.add(ss.allocateStroke())) {/* */
    }
    list.addAll(set);
    set = null;
    list.add(ss.getDefaultStroke());
    list.add(ss.getThickStroke());
    list.add(ss.getThinStroke());
    this.m_strokes = list.toArray(new IStrokeStyle[list.size()]);
  }

  /**
   * make the variable declaration
   *
   * @param body
   *          the dest
   * @param vars
   *          the number of variables
   * @param r
   *          the random number generator
   */
  private static final void __makeVars(final IText body, final int vars,
      final Random r) {
    int i, last, cur;

    last = Integer.MIN_VALUE;

    for (i = 0; i < vars; i++) {
      if ((i == 0) || (r.nextInt(6) == 0)) {
        if (i != 0) {
          do {
            cur = r.nextInt(10) - 5;
          } while (cur == last);
          body.append(cur);
          last = cur;

          body.append(';');
          body.appendLineBreak();
        }
        body.append("int ");//$NON-NLS-1$
      }
      body.append((char) ('a' + i));
      body.append(' ');
      body.append('=');
      body.append(' ');
    }
    do {
      cur = r.nextInt(10) - 5;
    } while (cur == last);
    body.append(cur);
    body.append(';');
  }

  /**
   * make the variable declaration
   *
   * @param body
   *          the destination
   * @param depth
   *          the depth
   */
  private static final void __indentLine(final IText body,
      final int depth) {
    body.appendLineBreak();
    body.append(RandomDocumentExample.SPACES, 0, (depth << 1));
  }

  /**
   * create a code
   *
   * @param sb
   *          the section body
   */
  @SuppressWarnings("fallthrough")
  private final void __createCode(final ISectionBody sb) {
    final Random r;
    int i, depth, vars, a, b, c, d, lc;
    boolean needsContent;

    r = this.m_rand;
    this.m_termination._done(_ERandomDocumentExampleElements.CODE);
    try (final ICode code = sb.code(
        this.m_termination._getLabel(this.m_rand, ELabelType.CODE),
        r.nextBoolean())) {
      this.__useLabel(code);
      try (final IPlainText cap = code.caption()) {
        LoremIpsum.appendLoremIpsum(cap, this.m_rand, r.nextInt(10) + 10);
      }

      try (final IText body = code.body()) {
        depth = lc = 0;
        vars = (r.nextInt(22) + 5);
        RandomDocumentExample.__makeVars(body, vars, r);
        needsContent = false;

        do {
          lc++;
          switch (r.nextInt(//
              (lc >= 50) ? 1
                  : //
                  (depth < RandomDocumentExample.CODE_MAX_DEPTH) ? 11
                      : 8)) {

            case 0:
            case 1:
            case 2:
            case 3: {
              if ((!needsContent) && (depth > 0)) {
                depth--;
                RandomDocumentExample.__indentLine(body, depth);
                body.append('}');
                break;
              }
            }

            case 4: {
              RandomDocumentExample.__indentLine(body, depth);
              needsContent = false;
              a = r.nextInt(vars);
              do {
                b = r.nextInt(vars);
              } while ((vars > 1) && (a == b));
              do {
                c = r.nextInt(vars);
              } while ((vars > 2) && ((a == c) || (b == c)));
              body.append((char) ('a' + a));
              body.append(' ');
              body.append('=');
              body.append(' ');
              body.append((char) ('a' + b));
              body.append(' ');
              switch (r.nextInt(10)) {
                case 0: {
                  body.append('+');
                  break;
                }
                case 1: {
                  body.append('-');
                  break;
                }
                case 2: {
                  body.append('*');
                  break;
                }
                case 3: {
                  body.append("mod"); //$NON-NLS-1$
                  break;
                }
                case 4: {
                  body.append('<');
                  body.append('<');
                  break;
                }
                case 5: {
                  body.append('>');
                  body.append('>');
                  break;
                }
                case 6: {
                  body.append('|');
                  break;
                }
                case 7: {
                  body.append('&');
                  break;
                }
                case 8: {
                  body.append('^');
                  break;
                }
                default: {
                  body.append('/');
                  break;
                }
              }
              body.append(' ');
              body.append((char) ('a' + c));
              body.append(';');
              break;
            }

            case 5: {
              RandomDocumentExample.__indentLine(body, depth);
              needsContent = false;
              a = r.nextInt(vars);
              do {
                b = r.nextInt(vars);
              } while ((vars > 1) && (a == b));
              do {
                c = r.nextInt(vars);
              } while ((vars > 2) && ((a == c) || (b == c)));
              do {
                d = r.nextInt(vars);
              } while ((vars > 3) && ((a == d) || (b == d) || (c == d)));

              body.append((char) ('a' + a));
              body.append(' ');
              body.append('=');
              body.append(' ');
              body.append('(');
              body.append('(');
              body.append((char) ('a' + b));
              body.append(' ');
              switch (r.nextInt(4)) {
                case 0: {
                  body.append('=');
                  break;
                }
                case 1: {
                  body.append('<');
                  break;
                }
                case 2: {
                  body.append('>');
                  break;
                }
                default: {
                  body.append('!');
                  break;
                }
              }
              body.append('=');
              body.append(' ');
              body.append((char) ('a' + c));
              body.append(')');
              body.append(' ');
              body.append('?');
              body.append(' ');
              body.append((char) ('a' + d));
              body.append(' ');
              body.append(':');
              body.append(' ');
              body.append((char) ('a' + (r.nextBoolean() ? b : c)));
              body.append(')');
              body.append(';');
              break;
            }

            case 6: {
              RandomDocumentExample.__indentLine(body, depth);
              needsContent = false;
              body.append("System.out.println("); //$NON-NLS-1$
              body.append((char) ('a' + r.nextInt(vars)));
              body.append(')');
              body.append(';');
              break;
            }

            case 7: {
              body.appendLineBreak();
              body.append('/');
              body.append('*');
              body.append(' ');
              LoremIpsum.appendLoremIpsum(body, r, 10);
              body.append(' ');
              body.append('*');
              body.append('/');
              break;
            }

            case 8: {
              RandomDocumentExample.__indentLine(body, depth);
              needsContent = true;
              a = r.nextInt(vars);
              do {
                b = r.nextInt(vars);
              } while ((vars > 1) && (a == b));
              body.append("if("); //$NON-NLS-1$
              body.append((char) ('a' + a));
              body.append(' ');
              body.append(r.nextBoolean() ? '<' : '>');
              body.append(' ');
              body.append((char) ('a' + b));
              body.append(')');
              body.append(' ');
              body.append('{');
              depth++;
              break;
            }

            default: {
              RandomDocumentExample.__indentLine(body, depth);
              needsContent = true;
              a = r.nextInt(vars);
              do {
                b = r.nextInt(vars);
              } while ((vars > 1) && (a == b));
              do {
                c = r.nextInt(vars);
              } while ((vars > 2) && (((a == c) || (b == c))));
              body.append("for("); //$NON-NLS-1$
              body.append((char) ('a' + a));
              body.append(' ');
              body.append('=');
              body.append(' ');
              body.append((char) ('a' + b));
              body.append(';');
              body.append(' ');
              body.append((char) ('a' + a));
              body.append(' ');

              i = r.nextInt(5) - 2;
              switch (i) {
                case -2: {
                  body.append('<');
                  body.append('=');
                  break;
                }
                case -1: {
                  body.append('<');
                  break;
                }
                case 0: {
                  body.append('!');
                  body.append('=');
                  break;
                }
                case 1: {
                  body.append('>');
                  body.append('=');
                  break;
                }
                default: {
                  body.append('>');
                  break;
                }
              }

              body.append(' ');
              body.append((char) ('a' + c));
              body.append(';');
              body.append(' ');
              body.append((char) ('a' + a));
              if (i <= 0) {
                body.append('+');
                body.append('+');
              } else {
                body.append('-');
                body.append('-');
              }
              body.append(')');
              body.append(' ');
              body.append('{');
              depth++;
              break;
            }

          }
        } while ((lc < 100)
            && ((depth > 0) || ((lc < 50) && (r.nextInt(4) > 0))));
      }
    }
  }

  /**
   * create a table
   *
   * @param sb
   *          the section body
   */
  private final void __createTable(final ISectionBody sb) {
    final ArrayList<ETableCellDef> def, pureDef;
    final ETableCellDef[] pureDefs;
    ETableCellDef d;
    int min;

    this.m_termination._done(_ERandomDocumentExampleElements.TABLE);

    def = new ArrayList<>();
    pureDef = new ArrayList<>();
    min = (this.m_rand.nextInt(3) + 1);

    do {
      d = ETableCellDef.INSTANCES
          .get(this.m_rand.nextInt(ETableCellDef.INSTANCES.size()));
      def.add(d);
      if (d != ETableCellDef.VERTICAL_SEPARATOR) {
        pureDef.add(d);
      }
    } while ((pureDef.size() < min) || (((this.m_termination._continue())
        && this.m_rand.nextBoolean())));
    pureDefs = pureDef.toArray(new ETableCellDef[pureDef.size()]);

    try (final ITable tab = sb.table(
        this.m_termination._getLabel(this.m_rand, ELabelType.TABLE),
        this.m_rand.nextBoolean(),
        def.toArray(new ETableCellDef[def.size()]))) {
      this.__useLabel(tab);
      try (final IPlainText cap = tab.caption()) {
        LoremIpsum.appendLoremIpsum(cap, this.m_rand,
            this.m_rand.nextInt(10) + 10);
      }
      try (final ITableSection s = tab.header()) {
        this.__makeTableSection(s, pureDefs, 1, 3);
      }
      try (final ITableSection s = tab.body()) {
        this.__makeTableSection(s, pureDefs, 2, 1000);
      }
      try (final ITableSection s = tab.footer()) {
        this.__makeTableSection(s, pureDefs, 0, 3);
      }
    }
  }

  /**
   * make a table section
   *
   * @param sec
   *          the section
   * @param cells
   *          the cells
   * @param minRows
   *          the minimum number of rows
   * @param maxRows
   *          the maximum number of rows
   */
  private final void __makeTableSection(final ITableSection sec,
      final ETableCellDef[] cells, final int minRows, final int maxRows) {
    int rows, neededRows, i, maxX, maxY;
    int[] blocked;
    ETableCellDef d;

    neededRows = minRows;

    rows = 0;
    blocked = new int[cells.length];
    while (((rows < maxRows)
        && ((rows < neededRows) || (this.m_termination._continue()
            && (this.m_rand.nextInt(4) > 0))))) {
      try (final ITableRow row = sec.row()) {
        rows++;
        for (i = 0; i < cells.length; i++) {

          if (blocked[i] < rows) {
            if (this.m_rand.nextInt(7) <= 0) {
              findMaxX: for (maxX = i; (++maxX) < cells.length;) {
                if (blocked[maxX] >= rows) {
                  break findMaxX;
                }
              }

              maxX = ((i + this.m_rand.nextInt(maxX - i)) + 1);
            } else {
              maxX = (i + 1);
            }

            if (this.m_rand.nextInt(7) <= 0) {
              maxY = (rows + 1 + this.m_rand
                  .nextInt(Math.min(5, ((maxRows - rows) + 1))));
            } else {
              maxY = (rows + 1);
            }

            Arrays.fill(blocked, i, maxX, (maxY - 1));
            neededRows = Math.max(neededRows, (maxY - 1));

            if ((maxX <= (i + 1)) && (maxY <= (rows + 1))) {
              try (final IPlainText cell = row.cell()) {
                cell.append(RandomUtils
                    .longToObject(this.m_rand.nextLong(), true));
              }
            } else {
              do {
                d = ETableCellDef.INSTANCES.get(
                    this.m_rand.nextInt(ETableCellDef.INSTANCES.size()));
              } while (d == ETableCellDef.VERTICAL_SEPARATOR);
              try (final IPlainText cell = row.cell((maxX - i),
                  (maxY - rows), d)) {
                if (this.m_rand.nextBoolean()) {
                  cell.append(d);
                } else {
                  cell.append(RandomUtils
                      .longToObject(this.m_rand.nextLong(), true));
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * create a figure
   *
   * @param sb
   *          the section body
   */
  private final void __createFigure(final ISectionBody sb) {
    final EFigureSize[] s;
    final EFigureSize v;

    s = EFigureSize.values();

    this.m_termination._done(_ERandomDocumentExampleElements.FIGURE);
    try (final IFigure fig = sb.figure(
        this.m_termination._getLabel(this.m_rand, ELabelType.FIGURE),
        (v = s[this.m_rand.nextInt(s.length)]),
        RandomUtils.longToString(null, this.__nextFigure()))) {
      this.__fillFigure(fig, v);
    }
    MemoryUtils.quickGC();
  }

  /**
   * create a figure series
   *
   * @param sb
   *          the section body
   */
  private final void __createFigureSeries(final ISectionBody sb) {
    final EFigureSize[] s;
    final EFigureSize v;
    final int c;
    int i;

    s = EFigureSize.values();

    v = s[this.m_rand.nextInt(s.length)];
    c = v.getNX();
    this.m_termination
        ._done(_ERandomDocumentExampleElements.FIGURE_SERIES);
    try (final IFigureSeries fs = sb.figureSeries(
        this.m_termination._getLabel(this.m_rand, ELabelType.FIGURE), v,
        RandomUtils.longToString(null, this.__nextFigure()))) {
      this.__useLabel(fs);
      try (final IPlainText caption = fs.caption()) {
        caption.append("A figure series with figures of size "); //$NON-NLS-1$
        caption.append(v.toString());
        caption.append(':');
        caption.append(' ');
        LoremIpsum.appendLoremIpsum(caption, this.m_rand);
      }
      for (i = (1 + this.m_rand.nextInt(10 * c)); (--i) >= 0;) {
        try (final IFigure fig = fs.figure(
            this.m_termination._getLabel(this.m_rand,
                ELabelType.SUBFIGURE),
            RandomUtils.longToString(null, this.__nextFigure()))) {
          this.__fillFigure(fig, null);
        }
        MemoryUtils.quickGC();
      }
    }
  }

  /**
   * get a point
   *
   * @param r
   *          the rectangle
   * @return the point
   */
  private final Point2D __randomPoint(final Rectangle2D r) {
    return new Point2D.Double(
        (r.getX() + (this.m_rand.nextDouble() * r.getWidth())), //
        (r.getY() + (this.m_rand.nextDouble() * r.getHeight())));
  }

  /**
   * get a random polygon
   *
   * @param r
   *          the rectangle
   * @return the point
   */
  private final double[][] __randomPolygon(final Rectangle2D r) {
    final double[][] data;
    double x, y;
    int dirX, dirY;
    Point2D p;
    int i;

    data = new double[2][this.m_rand.nextInt(100) + 3];
    p = this.__randomPoint(r);
    data[0][0] = p.getX();
    data[1][0] = p.getY();

    dirX = dirY = 0;
    for (i = 1; i < data[0].length; i++) {

      if (this.m_rand.nextInt(10) <= 0) {
        p = this.__randomPoint(r);
        data[0][i] = p.getX();
        data[1][i] = p.getY();
      } else {
        do {
          dirX += (this.m_rand.nextBoolean() ? (1) : (-1));
          dirY += (this.m_rand.nextBoolean() ? (1) : (-1));
        } while ((dirX == 0) && (dirY == 0));

        data[0][i] = data[0][i - 1];

        x = (this.m_rand.nextDouble() * 0.05d * r.getWidth());
        if (dirX > 0) {
          data[0][i] += x;
        } else {
          if (dirX < 0) {
            data[0][i] -= x;
          }
        }

        data[1][i] = data[1][i - 1];
        y = (this.m_rand.nextDouble() * 0.05d * r.getHeight());
        if (dirX > 0) {
          data[1][i] += y;
        } else {
          if (dirX < 0) {
            data[1][i] -= y;
          }
        }
      }

    }

    return data;
  }

  /**
   * fill a figure
   *
   * @param fig
   *          the figure
   * @param v
   *          the figure size
   */
  private final void __fillFigure(final IFigure fig, final EFigureSize v) {
    this.__useLabel(fig);

    try (final IPlainText cap = fig.caption()) {
      if (v != null) {
        cap.append("A figure of size "); //$NON-NLS-1$
        cap.append(v.toString());
        cap.append(':');
        cap.append(' ');
      }
      LoremIpsum.appendLoremIpsum(cap, this.m_rand, 20);
    }

    if (this.m_rand.nextInt(3) > 0) {
      ChartExample.createRandomChartExample(fig, this.m_doc.getStyles(),
          this.m_rand).run();
    } else {
      try (final Graphic g = fig.graphic()) {
        this.__randomGraphic(g);
      }
    }
  }

  /**
   * Fill a graphic with random contents
   *
   * @param g
   *          the graphic
   */
  @SuppressWarnings("fallthrough")
  private final void __randomGraphic(final Graphic g) {
    final Rectangle2D r;
    int k, e, i, limit;
    Point2D a, b;
    double[][] d;
    MemoryTextOutput mo;
    IFontStyle fs;

    e = 500;
    mo = new MemoryTextOutput();
    fs = null;

    r = g.getBounds();
    k = 0;
    limit = 100;
    do {
      switch (this.m_rand.nextInt(13)) {
        case 0: {
          i = this.m_fonts.length;
          if (i > 0) {
            g.setFont(
                (fs = this.m_fonts[this.m_rand.nextInt(i)]).getFont());
            break;
          }
        }
        case 1: {
          i = this.m_colors.length;
          if (i > 0) {
            g.setColor(this.m_colors[this.m_rand.nextInt(i)].getColor());
            break;
          }
        }
        case 2: {
          i = this.m_strokes.length;
          if (i > 0) {
            g.setStroke(
                this.m_strokes[this.m_rand.nextInt(i)].getStroke());
            break;
          }
        }
        case 3: {
          a = this.__randomPoint(r);
          b = this.__randomPoint(r);
          g.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
          k++;
          break;
        }
        case 4: {
          a = this.__randomPoint(r);
          b = this.__randomPoint(r);
          g.drawRect(a.getX(), a.getY(), (b.getX() - a.getX()),
              (b.getY() - a.getY()));
          k++;
          break;
        }
        case 5: {
          a = this.__randomPoint(r);
          b = this.__randomPoint(r);
          g.fillRect(a.getX(), a.getY(), (b.getX() - a.getX()),
              (b.getY() - a.getY()));
          k++;
          break;
        }
        case 6: {
          a = this.__randomPoint(r);
          b = this.__randomPoint(r);
          g.drawArc(a.getX(), a.getY(), (b.getX() - a.getX()),
              (b.getY() - a.getY()),
              (Math.PI * 2d * this.m_rand.nextDouble()),
              (Math.PI * 2d * this.m_rand.nextDouble()));
          k++;
          break;
        }
        case 7: {
          a = this.__randomPoint(r);
          b = this.__randomPoint(r);
          g.fillArc(a.getX(), a.getY(), (b.getX() - a.getX()),
              (b.getY() - a.getY()),
              (Math.PI * 2d * this.m_rand.nextDouble()),
              (Math.PI * 2d * this.m_rand.nextDouble()));
          k++;
          break;
        }
        case 8: {
          d = this.__randomPolygon(r);
          g.drawPolygon(d[0], d[1], d[0].length);
          k++;
          break;
        }
        case 9: {
          d = this.__randomPolygon(r);
          g.fillPolygon(d[0], d[1], d[0].length);
          k++;
          break;
        }
        case 10: {
          d = this.__randomPolygon(r);
          g.drawPolyline(d[0], d[1], d[0].length);
          k++;
          break;
        }

        default: {
          a = this.__randomPoint(r);
          mo.clear();
          if ((fs != null) && (this.m_rand.nextBoolean())) {
            fs.appendDescription(ETextCase.AT_SENTENCE_START, mo, false);
            mo.append(' ');
          }
          LoremIpsum.appendLoremIpsum(mo, this.m_rand,
              this.m_rand.nextInt(10) + 1);
          g.drawString(mo.toString(), a.getX(), a.getY());
          mo.clear();
          k++;
        }
      }
    } while (((limit--) > 0)
        && ((k < e) || (this.m_rand.nextInt(10) > 0)));
  }

  /**
   * create an equation
   *
   * @param sb
   *          the section body
   */
  private final void __createEquation(final ISectionBody sb) {

    try (final IEquation equ = sb.equation(
        this.m_termination._getLabel(this.m_rand, ELabelType.EQUATION))) {
      this.m_termination._done(_ERandomDocumentExampleElements.EQUATION);
      this.__useLabel(equ);
      this.__fillMath(equ, 1, 1, 5);
    }
  }

  /**
   * create an inline math
   *
   * @param sb
   *          the text
   */
  private final void __createInlineMath(final IComplexText sb) {
    try (final IMath equ = sb.inlineMath()) {

      this.m_termination
          ._done(_ERandomDocumentExampleElements.INLINE_MATH);
      this.__fillMath(equ, 1, 1, 4);
    }
  }

  /**
   * fill a mathematics context
   *
   * @param math
   *          the maths context
   * @param minArgs
   *          the minimum arguments
   * @param maxArgs
   *          the maximum arguments
   * @param depth
   *          the depth
   */
  private final void __fillMath(final IMath math, final int minArgs,
      final int maxArgs, final int depth) {
    int args, n;

    args = 0;

    do {
      switch (this.m_rand.nextInt((depth <= 0) ? 3 : 26)) {

        case 0: {
          try (final IText text = math.name()) {
            text.append("id"); //$NON-NLS-1$
            text.append(this.m_rand.nextInt(10));
          }
          break;
        }

        case 1: {
          try (final IText text = math.number()) {
            text.append(this.m_rand.nextInt(201) - 100);
          }
          break;
        }

        case 2: {
          try (final IText text = math.text()) {
            text.append("text_"); //$NON-NLS-1$
            text.append(this.m_rand.nextInt(10));
          }
          break;
        }

        case 3: {
          try (final IMath nm = math.add()) {
            this.__fillMath(nm, 2, 3, (depth - 1));
          }
          break;
        }

        case 4: {
          try (final IMath nm = math.sub()) {
            this.__fillMath(nm, 2, 3, (depth - 1));
          }
          break;
        }

        case 5: {
          try (final IMath nm = math.mul()) {
            this.__fillMath(nm, 2, 3, (depth - 1));
          }
          break;
        }

        case 6: {
          try (final IMath nm = math.div()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 7: {
          try (final IMath nm = math.divInline()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 8: {
          try (final IMath nm = math.mod()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 9: {
          try (final IMath nm = math.log()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 10: {
          try (final IMath nm = math.ln()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 11: {
          try (final IMath nm = math.ld()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 12: {
          try (final IMath nm = math.lg()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 13: {
          try (final IMath nm = math.sqrt()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 14: {
          try (final IMath nm = math.root()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 15: {
          try (final IMath nm = math
              .compare(RandomDocumentExample.COMP[this.m_rand
                  .nextInt(RandomDocumentExample.COMP.length)])) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 16: {
          try (final IMath nm = math.inBraces()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 17: {
          try (final IMath nm = math.negate()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 18: {
          try (final IMath nm = math.abs()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 19: {
          try (final IMath nm = math.factorial()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 20: {
          try (final IMath nm = math.sin()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 21: {
          try (final IMath nm = math.cos()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 22: {
          try (final IMath nm = math.tan()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 23:
        case 24:
        case 25: {
          n = (1 + this.m_rand.nextInt(3));
          try (final IMath nm = math.nAryFunction(
              ("operator" + this.m_rand.nextInt(100)), //$NON-NLS-1$
              n, n)) {
            this.__fillMath(nm, n, n, (depth - 1));
          }
          break;
        }

        default: {
          throw new IllegalStateException();
        }
      }

      args++;
    } while ((args < minArgs)
        || (((args < maxArgs) && (this.m_termination._continue()) && //
            (this.m_rand.nextInt(3) <= 0))));

  }
}
