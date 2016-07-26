package shared;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Stroke;
import java.nio.file.Path;
import java.util.Collection;

import org.optimizationBenchmarking.utils.IScope;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
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
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.document.spec.ITable;
import org.optimizationBenchmarking.utils.document.spec.ITableRow;
import org.optimizationBenchmarking.utils.document.spec.ITableSection;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.spec.IColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStrokeStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStyles;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A hard-core dummy document: this class implements all document-related
 * interfaces and extends the dummy graphics. All content written to this
 * document is directly ignored, all methods which open new document
 * elements return the same instance.
 */
public class DummyDocument extends DummyGraphic implements IDocument,
    IStyles, ILabel, IDocumentHeader, IDocumentBody, IColorStyle,
    IStrokeStyle, IFontStyle, ISection, ISectionBody, IList, IFigure,
    ICode, ITable, ITableSection, IFigureSeries, IEquation, IPieChart,
    IMathName, ILineChart2D, IDataScalar, ITableRow, IAxis, ILine2D {

  /** create */
  public DummyDocument() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public void close() {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public IStyles getStyles() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ILabel createLabel(final ELabelType type) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DummyDocument header() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DummyDocument body() {
    return this;
  }

  /** {@inheritDoc} */
  /** {@inheritDoc} */
  @Override
  public DummyDocument footer() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Path getDocumentPath() {
    return PathUtils.getTempDir();
  }

  /** {@inheritDoc} */
  @Override
  public IColorStyle getBlack() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IColorStyle getWhite() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void initializeWithDefaults(final Graphics graphics) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public IStrokeStyle getDefaultStroke() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IStrokeStyle getThinStroke() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IStrokeStyle getThickStroke() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFontStyle getDefaultFont() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFontStyle getEmphFont() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFontStyle getCodeFont() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ISection section(final ILabel useLabel) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText title() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public BibAuthorsBuilder authors() {
    return new BibAuthorsBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public BibDateBuilder date() {
    return new BibDateBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public IPlainText summary() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IColorStyle allocateColor() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFontStyle allocateFont() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IStrokeStyle allocateStroke() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IColorStyle getColor(final String name,
      final boolean allocateIfUndefined) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IStrokeStyle getStroke(final String name,
      final boolean allocateIfUndefined) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFontStyle getFont(final String name,
      final boolean allocateIfUndefined) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IScope applyTo(final Graphics graphics) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public boolean appendDescription(final ETextCase textCase,
      final ITextOutput dest, final boolean omitDefaults) {
    textCase.appendWord("bla", dest); //$NON-NLS-1$
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public String getID() {
    return String.valueOf(this.hashCode());
  }

  /** {@inheritDoc} */
  @Override
  public ILabel getLabel() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IList enumeration() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IList itemization() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText style(final IStyle style) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IPlainText subscript() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IPlainText superscript() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath inlineMath() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IText inlineCode() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IPlainText emphasize() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public BibliographyBuilder cite(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode) {
    return new BibliographyBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public void reference(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public IPlainText inQuotes() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DummyDocument inBraces() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ITextOutput append(final CharSequence csq) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ITextOutput append(final CharSequence csq, final int start,
      final int end) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ITextOutput append(final char c) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void append(final String s) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final String s, final int start, final int end) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final char[] chars) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final char[] chars, final int start, final int end) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final byte v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final short v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final int v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final long v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final float v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final double v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final boolean v) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void append(final Object o) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void appendLineBreak() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void appendNonBreakingSpace() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public void flush() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public ITable table(final ILabel useLabel, final boolean spansAllColumns,
      final ETableCellDef... cells) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFigure figure(final ILabel useLabel, final EFigureSize size,
      final String path) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFigureSeries figureSeries(final ILabel useLabel,
      final EFigureSize size, final String path) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ICode code(final ILabel useLabel, final boolean spansAllColumns) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IEquation equation(final ILabel useLabel) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public EFontFamily getFamily() {
    return EFontFamily.SERIF;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isItalic() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isBold() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isUnderlined() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int getStyle() {
    return this.getFont().getStyle();
  }

  /** {@inheritDoc} */
  @Override
  public int getSize() {
    return this.getFont().getSize();
  }

  /** {@inheritDoc} */
  @Override
  public Collection<String> getFaceChoices() {
    return new ArrayListView<>(
        new String[] { this.getFont().getFontName() }, false);
  }

  /** {@inheritDoc} */
  @Override
  public IPieChart pieChart() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath add() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath sub() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath mul() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath div() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath divInline() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath mod() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath log() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath ln() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath ld() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath lg() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath pow() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath root() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath sqrt() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath cbrt() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath compare(final EMathComparison cmp) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath negate() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath abs() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath factorial() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath sin() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath cos() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath tan() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath exp() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath sqr() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath cube() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath max() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath min() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMath nAryFunction(final String name, final int minArity,
      final int maxArity) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText text() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IMathName name() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IText number() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText caption() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Graphic graphic() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ILineChart2D lineChart2D() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IStructuredText item() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void setTitle(final String title) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setLegendMode(final ELegendMode legendMode) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setTitleFont(final Font titleFont) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public IDataScalar slice() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IFigure figure(final ILabel useLabel, final String path) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ITableRow row() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void separator() {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setColor(final Color color) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setStroke(final Stroke stroke) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText cell(final int rowSpan, final int colSpan,
      final ETableCellDef... definition) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText cell() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void setData(final double value) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public void setData(final long value) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public IAxis xAxis() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IAxis yAxis() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ILine2D line() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void setData(final IMatrix matrix) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setType(final ELineType type) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setTickFont(final Font tickFont) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setAxisStroke(final Stroke axisStroke) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setAxisColor(final Color axisColor) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setGridLineStroke(final Stroke gridLineStroke) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setGridLineColor(final Color gridLineColor) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setMinimum(final Number minimum) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setMinimum(final double minimum) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setMaximum(final Number maximum) {
    // ignore

  }

  /** {@inheritDoc} */
  @Override
  public void setMaximum(final double maximum) {
    // ignore

  }
}
