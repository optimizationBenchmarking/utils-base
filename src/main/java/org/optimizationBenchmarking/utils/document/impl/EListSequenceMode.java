package org.optimizationBenchmarking.utils.document.impl;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceAppender;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A structured list-based sequence mode */
public enum EListSequenceMode implements ISequenceAppender<Object> {

  /** Print the elements in an enumeration */
  ENUMERATION {
    /** {@inheritDoc} */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final ETextCase appendSequence(final ETextCase textCase,
        final Collection<?> data, final ITextOutput dest,
        final ISequenceAppender<?> fallback) {
      final int size;

      size = data.size();
      if (size <= 0) {
        return textCase;
      }

      if (dest instanceof IStructuredText) {
        try (final IList enumeration = ((IStructuredText) dest)
            .enumeration()) {
          EListSequenceMode._doSequence(size - 1, data, enumeration,
              textCase);
        }
        return textCase.nextCase();
      }

      dest.append(' ');
      return ((ISequenceAppender) fallback).appendSequence(textCase, data,
          dest);
    }
  },

  /** Print the elements in an itemization */
  ITEMIZATION {
    /** {@inheritDoc} */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final ETextCase appendSequence(final ETextCase textCase,
        final Collection<?> data, final ITextOutput dest,
        final ISequenceAppender<?> fallback) {
      final int size;

      size = data.size();
      if (size <= 0) {
        return textCase;
      }

      if (dest instanceof IStructuredText) {
        try (final IList itemization = ((IStructuredText) dest)
            .itemization()) {
          EListSequenceMode._doSequence(size - 1, data, itemization,
              textCase);
        }
        return textCase.nextCase();
      }

      dest.append(' ');
      return ((ISequenceAppender) fallback).appendSequence(textCase, data,
          dest);
    }
  };

  /**
   * Transform the given collection to a sequence.
   *
   * @param last
   *          the index of the last element to render
   * @param data
   *          the data to render
   * @param list
   *          the list to render to
   * @param textCase
   *          the text case to use for all elements
   */
  static final void _doSequence(final int last, final Collection<?> data,
      final IList list, final ETextCase textCase) {
    final Iterator<?> iterator;
    int index;

    iterator = data.iterator();
    for (index = 0; index <= last; index++) {
      try (final IStructuredText item = list.item()) {
        TextUtils.appendObjectToSequence(iterator.next(), (index <= 0),
            (index >= last), textCase, item);
      }
    }
  }

  /**
   * Transform the given collection to a sequence.
   *
   * @param textCase
   *          the text case to use
   * @param data
   *          the data to use
   * @param dest
   *          the destination text output
   * @param fallback
   *          the fallback sequence mode, if the text destination does not
   *          support lists
   * @return the next text case
   */
  public abstract ETextCase appendSequence(final ETextCase textCase,
      final Collection<?> data, final ITextOutput dest,
      final ISequenceAppender<?> fallback);

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendSequence(final ETextCase textCase,
      final Collection<?> data, final ITextOutput dest) {
    return this.appendSequence(textCase, data, dest, ESequenceMode.AND);
  }
}
