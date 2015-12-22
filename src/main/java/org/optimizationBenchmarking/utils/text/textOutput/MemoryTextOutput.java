package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.IOException;
import java.io.Reader;
import java.text.CharacterIterator;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.CharArrayCharSequence;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A simple
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * implementation that basically works like a
 * {@link java.lang.StringBuilder}: It stores everything which is written
 * to it in memory. It can be flushed to another
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}.
 * It is used as efficient backing store in
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalText}s
 * if multiple text nodes are forked in parallel.
 */
public class MemoryTextOutput extends AbstractTextOutput
    implements CharSequence, ITextable {

  /** the ten's digits */
  private final static char[] DIGIT_TENS = { '0', '0', '0', '0', '0', '0',
      '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
      '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3',
      '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4',
      '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6',
      '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7',
      '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
      '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', };

  /** the one's digits */
  private final static char[] DIGIT_ONES = { '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3',
      '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
      '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', };

  /** the integer size table */
  private static final int[] INT_SIZE_TABLE = { 9, 99, 999, 9999, 99999,
      999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };

  /** the minimum long */
  private static final char[] LONG_MIN_VAL = //
  Long.toString(Long.MIN_VALUE).toCharArray();

  /** the minimum int */
  private static final char[] INT_MIN_VAL = //
  Integer.toString(Integer.MIN_VALUE).toCharArray();

  /** the data */
  private char[] m_data;

  /** the size */
  private int m_size;

  /** create */
  public MemoryTextOutput() {
    super();
    this.m_data = new char[128];
  }

  /**
   * create
   *
   * @param size
   *          the size
   */
  public MemoryTextOutput(final int size) {
    super();
    this.m_data = new char[size];
  }

  /**
   * Find the last occurrence of the given character.
   *
   * @param ch
   *          the character
   * @return the last occurrence of {@code ch} in this text, or {@code -1}
   *         if it is not found
   * @see #indexOf(char)
   */
  public final int lastIndexOf(final char ch) {
    int i;

    for (i = this.m_size; (--i) >= 0;) {
      if (this.m_data[i] == ch) {
        return i;
      }
    }

    return (-1);
  }

  /**
   * Find the first occurrence of the given character.
   *
   * @param ch
   *          the character
   * @return the last occurrence of {@code ch} in this text, or {@code -1}
   *         if it is not found
   * @see #lastIndexOf(char)
   */
  public final int indexOf(final char ch) {
    final int end;
    int i;

    end = this.m_size;
    for (i = 0; i < end; i++) {
      if (this.m_data[i] == ch) {
        return i;
      }
    }

    return (-1);
  }

  /**
   * Check whether this text output contains the character {@code ch}
   *
   * @param ch
   *          the character to find
   * @return {@code true} if it is contained in this text ouput,
   *         {@code false} otherwise
   */
  public final boolean contains(final char ch) {
    return (this.lastIndexOf(ch) >= 0);
  }

  /** {@inheritDoc} */
  @Override
  public final MemoryTextOutput append(final CharSequence csq) {
    if (csq == null) {
      this.append(AbstractTextOutput.NULL, 0, 4);
    } else {
      this.append(csq, 0, csq.length());
    }
    return this;
  }

  /**
   * Ensure that the internal buffer is big enough to hold a given number
   * of characters, update the internal length variable, and return its old
   * length.
   *
   * @param len
   *          the number of characters to store
   * @return the old length
   */
  private final int __add(final int len) {
    int curLen, newLen, alloc;
    char[] data;

    data = this.m_data;
    curLen = this.m_size;
    if ((newLen = (curLen + len)) < curLen) {
      throw new IllegalStateException(//
          "Internal buffer too big: cannot add " + //$NON-NLS-1$
              len + " characters to the existing " + //$NON-NLS-1$
              curLen + " ones, the result would be " + //$NON-NLS-1$
              newLen);
    }
    this.m_size = (newLen = (curLen + len));
    if (newLen > data.length) {
      alloc = ((newLen + 2) << 1);
      this.m_data = new char[(alloc > newLen) ? alloc : newLen];
      System.arraycopy(data, 0, this.m_data, 0, curLen);
    }
    return curLen;
  }

  /**
   * Read all the characters from the given reader into this buffer.
   *
   * @param reader
   *          the reader
   * @throws IOException
   *           if i/o fails
   */
  public final void appendAll(final Reader reader) throws IOException {
    int curSize, newLen, read;
    char[] data;

    if (reader == null) {
      throw new IllegalArgumentException("Reader cannot be null.");//$NON-NLS-1$
    }

    data = this.m_data;
    curSize = this.m_size;

    for (;;) {
      if (curSize >= data.length) {
        if ((newLen = Math.max(curSize, (curSize + 4096))) <= curSize) {
          throw new IllegalStateException(//
              "Internal buffer too big: cannot add 4096 characters to the existing " //$NON-NLS-1$
                  + curSize + " ones, the result would be " + //$NON-NLS-1$
                  newLen);
        }

        this.m_data = new char[Math.max(((newLen + 2) << 1), newLen)];
        System.arraycopy(data, 0, this.m_data, 0, curSize);
        data = this.m_data;
      }

      read = reader.read(data, curSize, (data.length - curSize));
      if (read <= 0) {
        return;
      }
      curSize += read;
      this.m_size = curSize;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] add, final int start,
      final int end) {
    int old, len;
    len = (end - start);
    if (len > 0) {
      old = this.__add(len);
      System.arraycopy(add, start, this.m_data, old, len);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final MemoryTextOutput append(final CharSequence csq,
      final int start, final int end) {
    final char[] data;
    int len, i;

    if (csq == null) {
      this.append(AbstractTextOutput.NULL, start, end);
      return this;
    }

    if ((len = (end - start)) <= 0) {
      return this;
    }

    len = this.__add(len);
    data = this.m_data;

    if (csq instanceof String) {
      ((String) csq).getChars(start, end, data, len);
      return this;
    }

    if (csq instanceof CharArrayCharSequence) {
      ((CharArrayCharSequence) csq).getChars(start, end, data, len);
      return this;
    }

    for (i = start; i < end; i++, len++) {
      data[len] = csq.charAt(i);
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final MemoryTextOutput append(final char c) {
    final int idx;

    idx = this.__add(1);
    this.m_data[idx] = c;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start,
      final int end) {
    int len;

    if ((len = (end - start)) > 0) {
      len = this.__add(len);
      s.getChars(start, end, this.m_data, len);
    }
  }

  /**
   * This methods appends an integer to the memory buffer. It is basically
   * a copy of the method {@code java.lang.Integer#toString()} which uses
   * some package-private stuff. For performance reasons, we copy it here,
   * slightly modified.
   *
   * @param v
   *          the integer to add
   */
  @Override
  public final void append(final int v) {
    int abs, end, q, r;
    char sign;
    char[] buf;

    if (v < 0) {
      if (v <= Integer.MIN_VALUE) {
        this.append(MemoryTextOutput.INT_MIN_VAL, 0,
            MemoryTextOutput.INT_MIN_VAL.length);
        return;
      }
      abs = (-v);
      sign = '-';
      end = 2;
    } else {
      abs = v;
      sign = 0;
      end = 1;
    }

    for (final int z : MemoryTextOutput.INT_SIZE_TABLE) {
      if (abs <= z) {
        break;
      }
      end++;
    }

    end += this.__add(end);
    buf = this.m_data;

    while (abs >= 65536) {
      q = (abs / 100);
      r = (abs - ((q << 6) + (q << 5) + (q << 2)));
      abs = q;
      buf[--end] = MemoryTextOutput.DIGIT_ONES[r];
      buf[--end] = MemoryTextOutput.DIGIT_TENS[r];
    }

    for (;;) {
      q = ((abs * 52429) >>> (19));
      r = (abs - ((q << 3) + (q << 1)));
      buf[--end] = MemoryTextOutput.DIGIT_ONES[r];
      if (q == 0) {
        break;
      }
      abs = q;
    }
    if (sign != 0) {
      buf[--end] = sign;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.append((int) v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.append((int) v);
  }

  /**
   * This methods appends an integer to the memory buffer. It is basically
   * a copy of the method {@code java.lang.Long#toString()} which uses some
   * package-private stuff. For performance reasons, we copy it here,
   * slightly modified.
   *
   * @param v
   *          the long to add
   */
  @Override
  public final void append(final long v) {
    long abs, p, q;
    int i, q2, end, r;
    char sign;
    char[] buf;

    if (v < 0) {
      if (v <= Long.MIN_VALUE) {
        this.append(MemoryTextOutput.LONG_MIN_VAL, 0,
            MemoryTextOutput.LONG_MIN_VAL.length);
        return;
      }
      abs = (-v);
      sign = '-';
      end = 1;
    } else {
      abs = v;
      sign = 0;
      end = 0;
    }

    p = 10L;
    outer: {
      for (i = 1; i < 19; i++) {
        if (abs < p) {
          break outer;
        }
        p *= 10L;
      }
      i = 19;
    }
    end += i;

    end += this.__add(end);
    buf = this.m_data;

    // Get 2 digits/iteration using longs until quotient fits into an int
    while (abs > Integer.MAX_VALUE) {
      q = (abs / 100L);
      // really: r = i - (q * 100);
      r = (int) (abs - ((q << 6) + (q << 5) + (q << 2)));
      abs = q;
      buf[--end] = MemoryTextOutput.DIGIT_ONES[r];
      buf[--end] = MemoryTextOutput.DIGIT_TENS[r];
    }

    // Get 2 digits/iteration using ints
    int i2 = ((int) abs);
    while (i2 >= 65536) {
      q2 = i2 / 100;
      // really: r = i2 - (q * 100);
      r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
      i2 = q2;
      buf[--end] = MemoryTextOutput.DIGIT_ONES[r];
      buf[--end] = MemoryTextOutput.DIGIT_TENS[r];
    }

    // Fall thru to fast mode for smaller numbers
    // assert(i2 <= 65536, i2);
    for (;;) {
      q2 = (i2 * 52429) >>> (16 + 3);
      r = i2 - ((q2 << 3) + (q2 << 1)); // r = i2-(q2*10) ...
      buf[--end] = MemoryTextOutput.DIGIT_ONES[r];
      if (q2 == 0) {
        break;
      }
      i2 = q2;
    }
    if (sign != 0) {
      buf[--end] = sign;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int length() {
    return this.m_size;
  }

  /** {@inheritDoc} */
  @Override
  public final char charAt(final int index) {
    return this.m_data[index];
  }

  /** {@inheritDoc} */
  @Override
  public final CharSequence subSequence(final int start, final int end) {
    return new CharArrayCharSequence(this.m_data, start, (end - start));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return String.copyValueOf(this.m_data, 0, this.m_size);
  }

  /**
   * Obtain the characters stored in this buffer
   *
   * @return the characters
   */
  public final char[] toChars() {
    char[] ch;
    int s;

    s = this.m_size;
    ch = new char[s];
    System.arraycopy(this.m_data, 0, ch, 0, s);
    return ch;
  }

  /**
   * append a character iterator
   *
   * @param iterator
   *          the iterator
   */
  public final void append(final CharacterIterator iterator) {
    final char[] dest;
    final int end;
    int index, offset;

    if (iterator == null) {
      throw new IllegalArgumentException(
          "Character iterator cannot be null."); //$NON-NLS-1$
    }

    index = iterator.getBeginIndex();
    end = iterator.getEndIndex();

    if (index < end) {
      offset = this.__add(end - index);
      dest = this.m_data;
      while (index < end) {
        iterator.setIndex(index++);
        dest[offset++] = iterator.current();
      }
    }
  }

  /** clear the contents of this text output */
  public final void clear() {
    this.m_size = 0;
  }

  /**
   * Shrink the length of this memory text output to {@code len}
   *
   * @param len
   *          the length to shrink to
   * @throws IllegalArgumentException
   *           if {@code len} is bigger than {@link #length()} or less than
   *           {@code 0}.
   */
  public final void shrinkTo(final int len) {
    if ((len >= 0) && (len <= this.m_size)) {
      this.m_size = len;
    } else {
      throw new IllegalArgumentException("Cannot shrink to length " + len + //$NON-NLS-1$
          " when being " + this.m_size + //$NON-NLS-1$
          " long."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final int len;
    len = this.m_size;
    if (len > 0) {
      textOut.append(this.m_data, 0, len);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final CharSequence seq;
    int index;

    if (o == this) {
      return true;
    }
    if (o instanceof CharSequence) {
      seq = ((CharSequence) o);
      if (seq.length() == (index = this.m_size)) {
        for (; (--index) >= 0;) {
          if (this.m_data[index] != seq.charAt(index)) {
            return false;
          }
        }
        return true;
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    final int end;
    int index, hc;

    end = this.m_size;
    hc = 0;
    for (index = 0; index < end; index++) {
      hc = HashUtils.combineHashes(hc,
          HashUtils.hashCode(this.m_data[index]));
    }
    return hc;
  }

  /**
   * Compare the contents of this memory text output device with another
   * char sequence in a case-insensitive manner.
   *
   * @param seq
   *          the other char sequence
   * @return {@code true} if the two sequences are equal under ignorance of
   *         case
   */
  public final boolean equalsIgnoreCase(final CharSequence seq) {
    int index;
    char a, b;

    if (seq == this) {
      return true;
    }
    if (seq.length() == (index = this.m_size)) {
      // same procedure as in String.equalsIgnoreCase
      for (; (--index) >= 0;) {
        if ((a = this.m_data[index]) == (b = seq.charAt(index))) {
          continue;
        }
        a = TextUtils.toUpperCase(a);
        b = TextUtils.toUpperCase(b);
        if (a == b) {
          continue;
        }
        if (TextUtils.toLowerCase(a) == TextUtils.toLowerCase(b)) {
          continue;
        }
        return false;
      }
      return true;
    }

    return false;
  }

}
