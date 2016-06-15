package org.optimizationBenchmarking.utils.text.numbers;

import java.text.DecimalFormat;

import org.optimizationBenchmarking.utils.comparison.Compare;
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
public class TruncatedNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the fallback decimal format */
  private final DecimalFormat m_format;

  /** create */
  public TruncatedNumberAppender() {
    super();

    this.m_format = new DecimalFormat("#.###E0"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final long v, final ETextCase textCase) {
    final String best;
    final int length;

    best = SimpleNumberAppender.INSTANCE.toString(v, textCase);
    if ((best == null) || ((length = best.length()) <= 0)
        || (length > ((best.charAt(0) == '-') ? 4 : 5))) {
      return this.m_format.format(v);
    }
    return best;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    final String best;
    int length;

    best = SimpleNumberAppender.INSTANCE.toString(v, textCase);

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

    return this.m_format.format(v);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof TruncatedNumberAppender) && //
        Compare.equals(this.m_format,
            ((TruncatedNumberAppender) o).m_format)));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (45674441 ^ this.m_format.hashCode());
  }
}
