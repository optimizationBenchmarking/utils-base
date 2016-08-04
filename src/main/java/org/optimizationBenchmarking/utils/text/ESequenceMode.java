package org.optimizationBenchmarking.utils.text;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The set of sequence types. */
public enum ESequenceMode {
  /** A simple, comma-separated sequence {@code "a, b, c"}. */
  COMMA(null, null),
  /** A sequence of the type {@code "a, b, and c"}. */
  AND(null, new char[] { 'a', 'n', 'd' }),
  /** A sequence of the type {@code "a, b, or c"}. */
  OR(null, new char[] { 'o', 'r' }),
  /** A sequence of the type {@code "either a, b, or c"}. */
  EITHER_OR(new char[] { 'e', 'i', 't', 'h', 'e', 'r' }, OR.m_end),
  /** A sequence of the type {@code "neither a, b, nor c"}. */
  NEITHER_NOR(new char[] { 'n', 'e', 'i', 't', 'h', 'e', 'r' },
      new char[] { 'n', 'o', 'r' }),
  /** A sequence of the type {@code "from a to c"}. */
  FROM_TO(new char[] { 'f', 'r', 'o', 'm' }, new char[] { 't', 'o' }),
  /**
   * A sequence which consists of at most three elements concatenated in
   * {@link #AND} style. If there are more than three elements, then only
   * one element is printed, followed by {@code et al.}.
   */
  ET_AL(null, AND.m_end) {
    /** {@inheritDoc} */
    @Override
    final ETextCase _appendSequence(final char sep,
        final ETextCase textCase, final Collection<?> data,
        final boolean connectLastElementWithNonBreakableSpace,
        final ITextOutput dest) {
      final int size;
      final ETextCase nextCase;

      size = data.size();
      if (size > 3) {
        nextCase = ESequenceMode.__append(
            ESequenceMode._next(data.iterator()), true, true, textCase,
            dest);
        dest.append(ESequenceMode.ET_AL_);
        return nextCase;
      }
      return super._appendSequence(sep, textCase, data,
          connectLastElementWithNonBreakableSpace, dest);
    }
  };
  /** the et al */
  static final char[] ET_AL_ = { ' ', 'e', 't', ' ', 'a', 'l', '.' };
  /** the sequence modes */
  public static final ArraySetView<ESequenceMode> INSTANCES = new ArraySetView<>(
      ESequenceMode.values(), false);
  /** the start string, or {@code null} if none is needed */
  private final char[] m_start;
  /** the end string, or {@code null} if none is needed */
  private final char[] m_end;

  /**
   * create the sequence type
   *
   * @param start
   *          the start string, or {@code null} if none is needed
   * @param end
   *          the end string, or {@code null} if none is needed
   */
  ESequenceMode(final char[] start, final char[] end) {
    this.m_start = start;
    this.m_end = end;
  }

  /**
   * Append an object to a text output
   *
   * @param textCase
   *          the text case
   * @param first
   *          is the object the first one in the sequence?
   * @param last
   *          is the object the last one in the sequence
   * @param o
   *          the object
   * @param dest
   *          the destination
   * @return the next text case
   */
  static final ETextCase __append(final Object o, final boolean first,
      final boolean last, final ETextCase textCase,
      final ITextOutput dest) {
    final String stringValue;
    final int length;
    int i, j;
    char lowerCase, upperCase;

    if (o instanceof ISequenceable) {
      return ((ISequenceable) o).toSequence(first, last, textCase, dest);
    }

    if (o instanceof ISemanticComponent) {
      return ((ISemanticComponent) o).printShortName(dest, textCase);
    }

    if (textCase == ETextCase.IN_SENTENCE) {
      dest.append(o);
      return textCase;
    }

    stringValue = String.valueOf(o);
    length = stringValue.length();
    j = 0;
    do {
      i = j;
      if (textCase == ETextCase.AT_SENTENCE_START) {
        j = length;
      } else {
        j = stringValue.indexOf(' ', i);
        if (j < 0) {
          j = length;
        } else {
          j++;
        }
      }
      lowerCase = stringValue.charAt(i);
      upperCase = textCase.adjustCaseOfFirstCharInWord(lowerCase);
      if (lowerCase != upperCase) {
        dest.append(upperCase);
        dest.append(stringValue, (i + 1), j);
      } else {
        if ((i <= 0) && (j >= length)) {
          dest.append(stringValue);
        } else {
          dest.append(stringValue, i, j);
        }
      }
    } while (j < length);

    return textCase.nextCase();
  }

  /**
   * Append a sequence to this text output.
   *
   * @param textCase
   *          the text case of the first sequence element
   * @param data
   *          the collection providing the sequence elements
   * @param connectLastElementWithNonBreakableSpace
   *          {@code true} if the last element should be connected with a
   *          non-breakable space, {@code false} if normal spaces can be
   *          used
   * @param dest
   *          the destination text output
   * @return the next text case
   */
  public final ETextCase appendSequence(final ETextCase textCase,
      final Collection<?> data,
      final boolean connectLastElementWithNonBreakableSpace,
      final ITextOutput dest) {
    return this._appendSequence(',', textCase, data,
        connectLastElementWithNonBreakableSpace, dest);
  }

  /**
   * Append a sequence to this text output.
   *
   * @param textCase
   *          the text case of the first sequence element
   * @param data
   *          the collection providing the sequence elements
   * @param connectLastElementWithNonBreakableSpace
   *          {@code true} if the last element should be connected with a
   *          non-breakable space, {@code false} if normal spaces can be
   *          used
   * @param hierarchyLevel
   *          the hierarchy level: 0 for a normal sequence, 1 for a
   *          sequence containing a normal sequence, 2 for a sequence
   *          containing a sequence containing a sequence, etc.
   * @param dest
   *          the destination text output
   * @return the next text case
   */
  public final ETextCase appendNestedSequence(final ETextCase textCase,
      final Collection<?> data,
      final boolean connectLastElementWithNonBreakableSpace,
      final int hierarchyLevel, final ITextOutput dest) {
    final char ch;
    switch (hierarchyLevel % 3) {
      case 0: {
        ch = ',';
        break;
      }
      case 1: {
        ch = ';';
        break;
      }
      default: {
        ch = '.';
        break;
      }
    }
    return this._appendSequence(ch, textCase, data,
        connectLastElementWithNonBreakableSpace, dest);
  }

  /**
   * Get the next element from an iterator
   *
   * @param iterator
   *          the iterator
   * @return the next element
   */
  @SuppressWarnings("unused")
  static final Object _next(final Iterator<?> iterator) {
    try {
      return iterator.next();
    } catch (final Throwable error) {
      throw new IllegalStateException(//
          "There should have been another element in the iterator. Maybe there was a concurrent modification."); //$NON-NLS-1$
    }
  }

  /**
   * Append a sequence to this text output with a given separator.
   *
   * @param sep
   *          the separator
   * @param textCase
   *          the text case of the first sequence element
   * @param data
   *          the collection providing the sequence elements
   * @param connectLastElementWithNonBreakableSpace
   *          {@code true} if the last element should be connected with a
   *          non-breakable space, {@code false} if normal spaces can be
   *          used
   * @param dest
   *          the destination text output
   * @return the next text case
   */
  ETextCase _appendSequence(final char sep, final ETextCase textCase,
      final Collection<?> data,
      final boolean connectLastElementWithNonBreakableSpace,
      final ITextOutput dest) {
    final int size;
    final Iterator<?> iterator;
    int i;
    char[] mark;
    ETextCase useCase;
    char lower, upper;

    size = data.size();
    if (size <= 0) {
      return textCase;
    }

    iterator = data.iterator();

    if (size <= 1) {
      return ESequenceMode.__append(ESequenceMode._next(iterator), true,
          true, textCase, dest);
    }

    useCase = textCase;
    mark = this.m_start;
    if (mark != null) {
      app: {
        if ((useCase == ETextCase.AT_SENTENCE_START)
            || (useCase == ETextCase.AT_TITLE_START)) {
          lower = mark[0];
          upper = useCase.adjustCaseOfFirstCharInWord(lower);
          if (lower != upper) {
            dest.append(upper);
            dest.append(mark, 1, mark.length);
            break app;
          }
        }
        dest.append(mark);
      }
      dest.append(' ');
      useCase = useCase.nextCase();
    }
    ESequenceMode.__append(ESequenceMode._next(iterator), true, false,
        useCase, dest);

    for (i = size; (--i) > 1;) {
      dest.append(sep);
      dest.append(' ');
      useCase = useCase.nextCase();
      ESequenceMode.__append(ESequenceMode._next(iterator), false, false,
          useCase, dest);
    }
    if (size > 2) {
      dest.append(sep);
    }
    dest.append(' ');

    mark = this.m_end;
    if (mark != null) {
      useCase = useCase.nextCase();
      dest.append(mark);
      if (connectLastElementWithNonBreakableSpace) {
        dest.appendNonBreakingSpace();
      } else {
        dest.append(' ');
      }
    }

    return ESequenceMode.__append(ESequenceMode._next(iterator), false,
        true, useCase, dest);
  }
}