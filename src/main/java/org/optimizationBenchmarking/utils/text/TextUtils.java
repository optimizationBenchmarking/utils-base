package org.optimizationBenchmarking.utils.text;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.optimizationBenchmarking.utils.document.impl.EListSequenceMode;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The text utilities class provides several methods that help us to
 * process {@link java.lang.String strings} or to transform data to textual
 * representations.
 */
public final class TextUtils {

  /**
   * the string corresponding to the text representation of an object which
   * is {@code null}, i.e.,
   * <code>{@link java.lang.String#valueOf(Object) String.valueOf}((Object) null)</code>
   */
  public static final String NULL_STRING = String.valueOf((Object) null);

  /** the default normalizer form */
  public static final Normalizer.Form DEFAULT_NORMALIZER_FORM = Normalizer.Form.NFC;

  /**
   * the globally shared line separator: The value of this variable is
   * compatible to what {@link java.io.BufferedWriter} et al. are using
   */
  public static final String LINE_SEPARATOR = //
  TextUtils.__getLineSeparator();

  /** A character which represents a non-breaking space: {@value} */
  public static final char NON_BREAKING_SPACE = 0x2007;

  /** the sizes */
  private static final long[] SIZES = { 0, //
      (1L << 10L), //
      (1L << 20L), //
      (1L << 30L), //
      (1L << 40L), //
      (1L << 50L), //
      (1L << 60L),//
  };

  /** the size names */
  private static final char[][] SIZE_NAMES = { { 'B' }, //
      { 'K', 'i', 'B' }, //
      { 'M', 'i', 'B' }, //
      { 'G', 'i', 'B' }, //
      { 'T', 'i', 'B' }, //
      { 'P', 'i', 'B' }, //
      { 'E', 'i', 'B' },//
  };

  /**
   * obtain the line separator
   *
   * @return the line separator
   */
  @SuppressWarnings("unused")
  private static final String __getLineSeparator() {
    final byte[] bs;
    final char[] cs;
    final int l;
    String r;
    int i;

    r = null;
    try {
      try (java.io.CharArrayWriter caw = new java.io.CharArrayWriter(2)) {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(caw,
            2)) {
          bw.newLine();
          bw.flush();
        }
        r = caw.toString();
        if ((r != null) && (r.length() > 0)) {
          return r;
        }
      }
    } catch (final Throwable tt) {
      //
    }

    try {
      try (java.io.CharArrayWriter caw = new java.io.CharArrayWriter(2)) {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(caw)) {
          pw.println();
          pw.flush();
        }
        r = caw.toString();
        if ((r != null) && (r.length() > 0)) {
          return r;
        }
      }
    } catch (final Throwable tt) {
      //
    }

    try {
      try (java.io.ByteArrayOutputStream baos = //
      new java.io.ByteArrayOutputStream(2)) {
        try (java.io.PrintStream ps = new java.io.PrintStream(baos)) {
          ps.println();
          ps.flush();
        }
        bs = baos.toByteArray();
        if (bs != null) {
          l = bs.length;
          if (l > 0) {
            cs = new char[l];
            for (i = l; (--i) >= 0;) {
              cs[i] = ((char) (bs[i]));
              r = new String(cs, 0, l);
            }
          }
        }
      }
    } catch (final Throwable tt) {
      //
    }

    if ((r != null) && (r.length() > 0)) {
      return r;
    }
    return "\n"; //$NON-NLS-1$
  }

  /**
   * Prepare a string by trimming it and setting it to {@code null} if the
   * length is 0.
   *
   * @param s
   *          the string
   * @return the prepared version of {@code s}, {@code null} if it was
   *         empty
   */
  public static final String prepare(final String s) {
    String t;

    if (s == null) {
      return null;
    }

    t = s.trim();
    if (t.length() <= 0) {
      return null;
    }

    return t;
  }

  /**
   * Convert a string to lower case
   *
   * @param string
   *          the string
   * @return the lower case
   */
  public static final String toLowerCase(final String string) {
    return string.toLowerCase(Locale.ROOT);
  }

  /**
   * Convert a string to upper case
   *
   * @param string
   *          the string
   * @return the upper case
   */
  public static final String toUpperCase(final String string) {
    return string.toUpperCase(Locale.ROOT);
  }

  /**
   * Convert a character to lower case
   *
   * @param chr
   *          the char
   * @return the lower case
   */
  public static final char toLowerCase(final char chr) {
    return Character.toLowerCase(chr);
  }

  /**
   * Convert a character to upper case
   *
   * @param chr
   *          the char
   * @return the upper case
   */
  public static final char toUpperCase(final char chr) {
    return Character.toUpperCase(chr);
  }

  /**
   * Convert a character to lower case
   *
   * @param chr
   *          the char
   * @return the lower case
   */
  public static final int toLowerCase(final int chr) {
    return Character.toLowerCase(chr);
  }

  /**
   * Convert a character to upper case
   *
   * @param chr
   *          the char
   * @return the upper case
   */
  public static final int toUpperCase(final int chr) {
    return Character.toUpperCase(chr);
  }

  /**
   * Convert a character to title case
   *
   * @param chr
   *          the char
   * @return the title case
   */
  public static final int toTitleCase(final int chr) {
    return Character.toTitleCase(chr);
  }

  /**
   * Convert a character to title case
   *
   * @param chr
   *          the char
   * @return the title case
   */
  public static final char toTitleCase(final char chr) {
    return Character.toTitleCase(chr);
  }

  /**
   * Translate a string to a form which is most suitable for storage in
   * data structures which perform many case-insensitive comparisons and
   * case-insensitive hashing.
   *
   * @param s
   *          the string
   * @return the form most suitable for many case-insensitive comparisons
   *         and case-insensitive hashing
   */
  public static final String toComparisonCase(final String s) {
    return TextUtils.toUpperCase(s);
  }

  /**
   * Translate a character to a form which is most suitable for storage in
   * data structures which perform many case-insensitive comparisons and
   * case-insensitive hashing.
   *
   * @param c
   *          the character
   * @return the form most suitable for many case-insensitive comparisons
   *         and case-insensitive hashing
   */
  public static final int toComparisonCase(final int c) {
    return TextUtils.toUpperCase(c);
  }

  /**
   * Translate a character to a form which is most suitable for storage in
   * data structures which perform many case-insensitive comparisons and
   * case-insensitive hashing.
   *
   * @param c
   *          the character
   * @return the form most suitable for many case-insensitive comparisons
   *         and case-insensitive hashing
   */
  public static final char toComparisonCase(final char c) {
    return TextUtils.toUpperCase(c);
  }

  /**
   * Normalize a string.
   *
   * @param s
   *          the string
   * @return the normalized version
   */
  public static final String normalize(final String s) {
    return TextUtils.normalize(s, TextUtils.DEFAULT_NORMALIZER_FORM);
  }

  /**
   * Normalize a string.
   *
   * @param s
   *          the string
   * @param normalizerForm
   *          the normalizer form ({@code null} for
   *          {@link #DEFAULT_NORMALIZER_FORM})
   * @return the normalized version
   */
  public static final String normalize(final String s,
      final Form normalizerForm) {
    String t, u;

    if (s == null) {
      return null;
    }

    t = s.trim();
    if (t.length() <= 0) {
      return null;
    }

    u = Normalizer.normalize(t, ((normalizerForm != null) ? normalizerForm
        : TextUtils.DEFAULT_NORMALIZER_FORM));
    if (u != t) {
      t = u.trim();

      if (t.length() <= 0) {
        return null;
      }
    }

    return (t.equals(s) ? s : t);
  }

  /**
   * Get an easy-to-use representation of a class.
   *
   * @param c
   *          the class
   * @return the name
   */
  public static final String className(final Class<?> c) {
    String s;
    Class<?> z;

    if (c == null) {
      return "Cannot get name of null class."; //$NON-NLS-1$
    }

    s = c.getCanonicalName();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    z = c;
    s = "";//$NON-NLS-1$
    while ((z != null) && (z.isArray())) {
      s += "[]";//$NON-NLS-1$
      z = z.getComponentType();
    }
    if (z != null) {
      if (s.length() > 0) {
        return (TextUtils.className(z) + s);
      }
    }

    s = c.getName();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    s = c.getSimpleName();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    s = c.toString();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }
    return "nameless"; //$NON-NLS-1$
  }

  /**
   * Translate a throwable to a string.
   *
   * @param t
   *          the throwable
   * @return the string
   */
  public static final String throwableToString(final Throwable t) {
    try {
      try (StringWriter sw = new StringWriter()) {
        try (PrintWriter pw = new PrintWriter(sw)) {
          t.printStackTrace(pw);
        }
        return sw.toString();
      }
    } catch (final Throwable v) {
      throw new RuntimeException(v);
    }
  }

  /**
   * Could the given string be a class name?
   *
   * @param s
   *          the string
   * @return {@code true} if {@code s} could be a class name, {@code false}
   *         otherwise
   */
  public static final boolean couldBeClassName(final String s) {
    final int l;
    boolean b;
    char ch;
    int i;

    if (s != null) {
      l = s.length();
      if (l > 0) {
        b = false;

        for (i = l; (--i) > 0;) {
          ch = s.charAt(i);
          if ((Character.isAlphabetic(ch) || Character.isDigit(ch)
              || (ch == '_'))) {
            b = true;
          } else {
            if (!(Character.isJavaIdentifierPart(ch))) {
              return false;
            }
          }
        }

        ch = s.charAt(0);
        if ((Character.isAlphabetic(ch) || Character.isDigit(ch)
            || (ch == '_'))) {
          return true;
        }
        return (b && Character.isJavaIdentifierStart(ch));
      }
    }

    return false;
  }

  /**
   * Format a file size
   *
   * @param size
   *          the file size
   * @param to
   *          the destination
   */
  public static final void appendFileSize(final long size,
      final ITextOutput to) {
    long use, unit, mod;
    int index;

    if (size < 0L) {
      to.append('-');
      if (size <= Long.MIN_VALUE) {
        to.append('8');
        to.appendNonBreakingSpace();
        to.append(TextUtils.SIZE_NAMES[TextUtils.SIZE_NAMES.length - 1]);
        return;
      }
      use = (-size);
    } else {
      use = size;
    }

    index = Arrays.binarySearch(TextUtils.SIZES, use);
    if (index < 0) {
      index = (-(index + 2));
    }

    if (index > 0) {
      unit = TextUtils.SIZES[index];
      mod = (use % unit);
      use /= unit;

      if (mod >= (unit >>> 1L)) {
        use++; // round sizes up
        if (index < (TextUtils.SIZE_NAMES.length - 1)) {
          // what if we have 1023.7 KiB -> 1 MiB
          if ((use * unit) >= TextUtils.SIZES[index + 1]) {
            use = 1L;
            index++;
          }
        }
      }
    }
    to.append(use);
    to.appendNonBreakingSpace();
    to.append(TextUtils.SIZE_NAMES[index]);
  }

  /**
   * Append an object to a text output
   *
   * @param textCase
   *          the text case
   * @param isObjectFirstInSequence
   *          is the object the isObjectFirstInSequence one in the
   *          sequence?
   * @param isObjectLastInSequence
   *          is the object the isObjectLastInSequence one in the sequence
   * @param objectToAppend
   *          the object
   * @param dest
   *          the destination
   * @return the next text case
   */
  public static final ETextCase appendObjectToSequence(
      final Object objectToAppend, final boolean isObjectFirstInSequence,
      final boolean isObjectLastInSequence, final ETextCase textCase,
      final ITextOutput dest) {
    final String stringValue;
    final int length;
    int i, j;
    char lowerCase, upperCase;

    if (objectToAppend instanceof ISequenceable) {
      return ((ISequenceable) objectToAppend).toSequence(
          isObjectFirstInSequence, isObjectLastInSequence, textCase, dest);
    }

    if (objectToAppend instanceof ISemanticComponent) {
      return ((ISemanticComponent) objectToAppend).printShortName(dest,
          textCase);
    }

    if (textCase == ETextCase.IN_SENTENCE) {
      dest.append(objectToAppend);
      return textCase;
    }

    stringValue = String.valueOf(objectToAppend);
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
   * List the elements in the given collection
   *
   * @param elements
   *          the collection with the elements to list
   * @param singular
   *          the singular name of the element type
   * @param plural
   *          the plural name of the element type
   * @param textCase
   *          the text case
   * @param appender
   *          the appender to use
   * @param textOut
   *          the text destination the next text case
   * @return the next text case
   * @param <ET>
   *          the element type
   */
  public static final <ET> ETextCase appendElements(
      final Collection<? extends ET> elements, final String singular,
      final String plural, final ETextCase textCase,
      final ISequenceAppender<? super ET> appender,
      final ITextOutput textOut) {
    final int size;
    ETextCase next;

    size = elements.size();

    if (size <= 0) {
      return textCase.appendWord("no", textOut)//$NON-NLS-1$
          .appendWords(plural, textOut);
    }

    next = InTextNumberAppender.INSTANCE.appendTo(elements.size(),
        textCase, textOut);

    textOut.append(' ');
    next = next.appendWords(((size > 1) ? plural : singular), textOut);
    textOut.append(' ');
    next = next.appendWord(", namely", textOut);//$NON-NLS-1$
    if (!(appender instanceof EListSequenceMode)) {
      textOut.append(' ');
    }
    return appender.appendSequence(next, elements, textOut);
  }
}