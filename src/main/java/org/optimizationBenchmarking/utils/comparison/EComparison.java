package org.optimizationBenchmarking.utils.comparison;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of comparisons that return {@code true} if they match and
 * {@code false} if they don't.
 */
public enum EComparison {

  /** less */
  LESS("less than") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (Compare.compare(a, b) < 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return ((!a) && b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) < 0);
    }
  },

  /** less or equal */
  LESS_OR_EQUAL("less than or equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (Compare.compare(a, b) <= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (Compare.compare(a, b) <= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (Compare.compare(a, b) <= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return ((!a) || b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) <= 0);
    }
  },

  /** equal */
  EQUAL("equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return Compare.equals(a, b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return Compare.equals(a, b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return Compare.equals(a, b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) == 0);
    }
  },

  /** greater or equal */
  GREATER_OR_EQUAL("greater than or equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (Compare.compare(a, b) >= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (Compare.compare(a, b) >= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (Compare.compare(a, b) >= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a || (!b));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) >= 0);
    }
  },

  /** greater */
  GREATER("greater than") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (Compare.compare(a, b) > 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a && (!b));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) > 0);
    }
  },

  /** not equal */
  NOT_EQUAL("not equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (!(Compare.equals(a, b)));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (!(Compare.equals(a, b)));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (!(Compare.equals(a, b)));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) != 0);
    }
  };

  /** an array set view of the comparison instances */
  public static final ArraySetView<EComparison> INSTANCES = new ArraySetView<>(
      EComparison.values(), false);

  /** the name */
  private final String m_name;

  /**
   * create
   *
   * @param name
   *          the name
   */
  private EComparison(final String name) {
    this.m_name = name;
  }

  /**
   * The {@link #toString()} method returns a string which can replace
   * {@code [relation]} in the following sentence {@code "x [relation] y}
   * in the case that {@link #compare(Object, Object) compare(X, Y)}
   * returns {@code true}.
   *
   * @return a string properly representing the comparison relationship
   */
  @Override
  public final String toString() {
    return this.m_name;
  }

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final byte a, final byte b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final short a, final short b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final int a, final int b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final long a, final long b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final float a, final float b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final double a, final double b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final Object a, final Object b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first boolean
   * @param b
   *          the second boolean
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final boolean a, final boolean b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first character
   * @param b
   *          the second character
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final char a, final char b);

}
