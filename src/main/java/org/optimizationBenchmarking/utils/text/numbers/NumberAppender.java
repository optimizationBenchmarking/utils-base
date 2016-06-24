package org.optimizationBenchmarking.utils.text.numbers;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A class that can format numbers. Different from Java's
 * {@link java.text.DecimalFormat}, this class can be specialized for a
 * wider variety of different formats and styles. It also integrates more
 * seamlessly with our
 * {@linkplain org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
 * text output API}.
 */
public abstract class NumberAppender
    implements Serializable, ISemanticComponent {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the number formatter */
  protected NumberAppender() {
    super();
  }

  /**
   * Append a {@code long} number to a text output device
   *
   * @param value
   *          the {@code long} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public ETextCase appendTo(final long value, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(value);
    return textCase.nextCase();
  }

  /**
   * Convert a {@code long} number to a string
   *
   * @param value
   *          the {@code long} value to append
   * @param textCase
   *          the text case
   * @return the string
   */
  public String toString(final long value, final ETextCase textCase) {
    final MemoryTextOutput m;

    m = new MemoryTextOutput();
    this.appendTo(value, textCase, m);
    return m.toString();
  }

  /**
   * Append an {@code int} number to a text output device
   *
   * @param value
   *          the {@code int} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public ETextCase appendTo(final int value, final ETextCase textCase,
      final ITextOutput textOut) {
    return this.appendTo((long) value, textCase, textOut);
  }

  /**
   * Convert a {@code int} number to a string
   *
   * @param value
   *          the {@code int} value to append
   * @param textCase
   *          the text case
   * @return the string
   */
  public String toString(final int value, final ETextCase textCase) {
    final MemoryTextOutput m;

    m = new MemoryTextOutput();
    this.appendTo(value, textCase, m);
    return m.toString();
  }

  /**
   * Append a {@code double} number to a text output device
   *
   * @param value
   *          the {@code double} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public ETextCase appendTo(final double value, final ETextCase textCase,
      final ITextOutput textOut) {
    final int types;

    types = NumericalTypes.getTypes(value);
    if ((types & NumericalTypes.IS_INT) != 0) {
      return this.appendTo(((int) value), textCase, textOut);
    }

    if ((types & NumericalTypes.IS_LONG) != 0) {
      return this.appendTo(((long) value), textCase, textOut);
    }

    textOut.append(value);
    return textCase.nextCase();
  }

  /**
   * Should the text output destination handle non-finite values itself? If
   * the number appender would normally print numerical representations of
   * {@code double} values, there may be situations where that is best
   * handled by the text output itself. This is the case for
   * {@link java.lang.Double#POSITIVE_INFINITY},
   * {@link java.lang.Double#NEGATIVE_INFINITY}, and
   * {@link java.lang.Double#NaN} if the text output is an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IDocumentElement}
   * , i.e., part of the document generation API: Since this API can be
   * implemented for different formats, such as LaTeX, it may be able to
   * render these non-finite values more appropriately by itself.
   *
   * @param textOut
   *          the text output destination
   * @return {@code true} if it makes sense to let the destination handle
   *         non-finite values by itself, {@code false} otherwise
   */
  protected static final boolean shouldTextOutputHandleNonFiniteValues(
      final ITextOutput textOut) {
    return (textOut instanceof IDocumentElement);
  }

  /**
   * Append a {link java.lang.Number number} {@code value} to a text output
   * device
   *
   * @param value
   *          the {link java.lang.Number number} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public final ETextCase appendTo(final Number value,
      final ETextCase textCase, final ITextOutput textOut) {

    if ((value instanceof Byte) || (value instanceof Short)
        || (value instanceof Integer)) {
      return this.appendTo(value.intValue(), textCase, textOut);
    }

    if (value instanceof Long) {
      return this.appendTo(value.longValue(), textCase, textOut);
    }

    if ((value instanceof Float) || (value instanceof Double)) {
      return this.appendTo(value.doubleValue(), textCase, textOut);
    }

    switch (NumericalTypes.getMinType(value)) {
      case NumericalTypes.IS_BYTE:
      case NumericalTypes.IS_SHORT:
      case NumericalTypes.IS_INT: {
        return this.appendTo(value.intValue(), textCase, textOut);
      }
      case NumericalTypes.IS_LONG: {
        return this.appendTo(value.longValue(), textCase, textOut);
      }
      case NumericalTypes.IS_FLOAT:
      case NumericalTypes.IS_DOUBLE: {
        return this.appendTo(value.doubleValue(), textCase, textOut);
      }
      default: {
        throw new IllegalArgumentException("Number " + value + //$NON-NLS-1$
            " cannot be dealt with."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Convert a {@code double} number to a string
   *
   * @param value
   *          the {@code double} value to append
   * @param textCase
   *          the text case
   * @return the string
   */
  public String toString(final double value, final ETextCase textCase) {
    final MemoryTextOutput m;

    m = new MemoryTextOutput();
    this.appendTo(value, textCase, m);
    return m.toString();
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(this.toString(), textOut);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.printShortName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public abstract ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase);

  /** {@inheritDoc} */
  @Override
  public String getPathComponentSuggestion() {
    return this.toString();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
