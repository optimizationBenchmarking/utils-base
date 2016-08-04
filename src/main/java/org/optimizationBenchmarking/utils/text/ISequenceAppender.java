package org.optimizationBenchmarking.utils.text;

import java.util.Collection;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An appender for a sequence of a given type
 *
 * @param <ET>
 *          the sequence element type
 */
public interface ISequenceAppender<ET> {
  /**
   * Append a sequence to this text output.
   *
   * @param textCase
   *          the text case of the first sequence element
   * @param data
   *          the collection providing the sequence elements
   * @param dest
   *          the destination text output
   * @return the next text case
   */
  public abstract ETextCase appendSequence(final ETextCase textCase,
      final Collection<? extends ET> data, final ITextOutput dest);
}
