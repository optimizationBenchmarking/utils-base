package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * <p>
 * A character transformer copies a character sequence from a source to a
 * destination and applies some modification. The modification may be to
 * replace some characters with other symbols.Instances of this class
 * translate unicode characters ({@code char}s) to a (canonical) textual
 * representations.
 * </p>
 */
public abstract class CharTransformer {

  /** create */
  protected CharTransformer() {
    super();
  }

  /**
   * Create a transformation wrapper around a given
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * {@code out}. The new wrapper will transform all character data that it
   * receives according to the rules of this character transformation
   * definition and then pass them on to {@code out}.
   *
   * @param out
   *          the
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   *          to receive the transformed character data
   * @param form
   *          a normalization form to be applied before the text
   *          transformation, or {@code null} if none needs to be applied
   * @return the new
   *         {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   *         instance which can be written to and which transforms all text
   *         it receives and writes it to {@code out}
   * @see #transform(ITextOutput)
   */
  public abstract AbstractTextOutput transform(final ITextOutput out,
      final Normalizer.Form form);

  /**
   * Create a transformation wrapper around a given
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * {@code out}.
   *
   * @param out
   *          the
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   *          to receive the transformed character data
   * @return the new
   *         {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   *         instance which can be written to and which transforms all text
   *         it receives and writes it to {@code out}
   * @see #transform(ITextOutput, java.text.Normalizer.Form)
   */
  public final AbstractTextOutput transform(final ITextOutput out) {
    return this.transform(out, null);
  }

  /**
   * Transform a string
   *
   * @param s
   *          the string
   * @param form
   *          a normalization form to be applied before the text
   *          transformation, or {@code null} if none needs to be applied
   * @return the transformed string
   * @see #transform(String)
   */
  public final String transform(final String s,
      final Normalizer.Form form) {
    String t, r;
    final int len;
    final MemoryTextOutput sb;

    t = ((s == null) ? TextUtils.NULL_STRING : s);

    if (form != null) {
      r = Normalizer.normalize(t, form);
      if (r.equals(t)) {
        r = null;
      } else {
        t = r;
      }
    }

    len = t.length();
    sb = new MemoryTextOutput(Math.max(Math.max(len, 128), //
        ((len + 1) << 1)));
    this.transform(sb, null).append(t);

    r = sb.toString();
    if (r.equals(t)) {
      return t;
    }
    return r;
  }

  /**
   * {@linkplain #transform(String, java.text.Normalizer.Form) Transform} a
   * string.
   *
   * @see #transform(String, java.text.Normalizer.Form)
   * @param s
   *          the string
   * @return the transformed string
   */
  public final String transform(final String s) {
    return this.transform(s, null);
  }
}
