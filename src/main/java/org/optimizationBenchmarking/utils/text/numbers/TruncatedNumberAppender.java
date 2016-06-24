package org.optimizationBenchmarking.utils.text.numbers;

import java.text.DecimalFormat;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A number appender using truncation to make numbers fit the common format
 * of three decimal places and human-readability.
 * </p>
 * <p>
 * Warning: Instances of this number appender must either not be shared
 * between threads or synchronized, since we internally use
 * {@link java.text.DecimalFormat}!.
 * </p>
 */
public final class TruncatedNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the truncated number appender */
  public static final TruncatedNumberAppender INSTANCE = new TruncatedNumberAppender();

  /** the shared instance of the format holder */
  private static final __Local LOCAL = new __Local();

  /** create */
  private TruncatedNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final long value,
      final ETextCase textCase) {
    final String best;
    final int length;

    best = SimpleNumberAppender.INSTANCE.toString(value, textCase);
    if ((best == null) || ((length = best.length()) <= 0)
        || (length > ((best.charAt(0) == '-') ? 4 : 5))) {
      return TruncatedNumberAppender.LOCAL.get().m_scientificFormat
          .format(value);
    }
    return best;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double value,
      final ETextCase textCase) {
    final String best;
    final __FormatHolder holder;
    int length;

    best = SimpleNumberAppender.INSTANCE.toString(value, textCase);

    isOK: {

      if ((best == null) || ((length = best.length()) <= 0)
          || (length > 5)) {
        break isOK;
      }
      if (best.lastIndexOf('E') >= 0) {
        break isOK;
      }
      if (best.charAt(0) == '-') {
        --length;
      }
      if (best.lastIndexOf('.') > 0) {
        if (length > 4) {
          break isOK;
        }
      } else {
        if (length > 5) {
          break isOK;
        }
      }
      return best;
    }

    holder = TruncatedNumberAppender.LOCAL.get();

    if ((value <= -1e-3d) || (value >= 1e-3d)) {
      if ((value > (-10d)) && (value < 10d)) {
        return holder.m_normalFormat1.format(value);
      }
      if ((value > (-100d)) && (value < 100d)) {
        return holder.m_normalFormat2.format(value);
      }
      if ((value > (-1000d)) && (value < 1000d)) {
        return holder.m_normalFormat3.format(value);
      }
      if ((value > (-9999.5d)) && (value < 9999.5d)) {
        return String.valueOf(Math.round(value));
      }
    }
    return holder.m_scientificFormat.format(value);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final long value,
      final ETextCase textCase, final ITextOutput textOut) {
    textOut.append(this.toString(value, textCase));
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final double value,
      final ETextCase textCase, final ITextOutput textOut) {

    if ((!(MathUtils.isFinite(value)))
        && NumberAppender.shouldTextOutputHandleNonFiniteValues(textOut)) {
      textOut.append(value);
    } else {
      textOut.append(this.toString(value, textCase));
    }
    return textCase.nextCase();
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return TruncatedNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return TruncatedNumberAppender.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(//
        "numbers are rounded to four significant digits", //$NON-NLS-1$
        textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "four digits";//$NON-NLS-1$
  }

  /** a class holding the formats */
  private static final class __FormatHolder {

    /** the fallback decimal format */
    final DecimalFormat m_scientificFormat;
    /** the normal decimal format 1 */
    final DecimalFormat m_normalFormat1;
    /** the normal decimal format 2 */
    final DecimalFormat m_normalFormat2;
    /** the normal decimal format 3 */
    final DecimalFormat m_normalFormat3;

    /** create */
    __FormatHolder() {
      super();
      this.m_scientificFormat = new DecimalFormat("#.###E0"); //$NON-NLS-1$
      this.m_normalFormat1 = new DecimalFormat("#.###"); //$NON-NLS-1$
      this.m_normalFormat2 = new DecimalFormat("##.##"); //$NON-NLS-1$
      this.m_normalFormat3 = new DecimalFormat("###.#"); //$NON-NLS-1$
    }
  }

  /** the thread-local supplier for formats */
  private static final class __Local extends ThreadLocal<__FormatHolder> {
    /** create */
    __Local() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    protected final __FormatHolder initialValue() {
      return new __FormatHolder();
    }
  }
}
