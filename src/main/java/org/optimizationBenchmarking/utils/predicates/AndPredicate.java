package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;
import java.util.List;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * The combination of two predicates with logical {@code or}
 *
 * @param <T>
 *          the element type this condition applies to.
 */
public final class AndPredicate<T> extends HashObject
    implements IPredicate<T>, Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the first predicate
   *
   * @serial the first predicate to evaluate (must not be {@code null}
   */
  private final IPredicate<? super T> m_a;
  /**
   * the second predicate
   *
   * @serial the second predicate to evaluate (must not be {@code null}
   */
  private final IPredicate<? super T> m_b;

  /**
   * The condition
   *
   * @param a
   *          the first predicate to evaluate (must not be {@code null}
   * @param b
   *          the second predicate to evaluate (must not be {@code null}
   */
  public AndPredicate(final IPredicate<? super T> a,
      final IPredicate<? super T> b) {
    super();

    this.m_a = a;
    this.m_b = b;
  }

  /**
   * calculate the hash code
   *
   * @return the hash code
   */
  protected final int calcHash() {
    return HashUtils.combineHashesUnsorted(//
        HashUtils.hashCode(this.m_a), //
        HashUtils.hashCode(this.m_b));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final T param) {
    return (this.m_a.check(param) && this.m_b.check(param));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final AndPredicate c;

    if (o == this) {
      return true;
    }

    if (o instanceof AndPredicate) {
      c = ((AndPredicate) o);
      return ((this.m_a.equals(c.m_a) && this.m_a.equals(c.m_b)) || //
          (this.m_a.equals(c.m_b) && this.m_a.equals(c.m_a)));
    }
    return false;
  }

  /**
   * Join all the predicates in the list with logical AND.
   *
   * @param list
   *          the list of predicates
   * @return the predicate representing the list, or {@code null} if no
   *         predicate is contained or {@code list==null}.
   * @param <T>
   *          the predicate input element type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final <T> IPredicate<T> and(
      final List<IPredicate<? super T>> list) {
    if (list == null) {
      return null;
    }
    return AndPredicate.__and(((List) list), 0, (list.size() - 1));
  }

  /**
   * Join all the predicates in the list with logical AND.
   *
   * @param list
   *          the list of predicates
   * @param startInclusive
   *          the inclusive start index
   * @param endInclusive
   *          the inclusive end index
   * @return the predicate representing the list, or {@code null} if no
   *         predicate is contained or {@code list==null}.
   * @param <T>
   *          the predicate input element type
   */
  private static final <T> IPredicate<T> __and(
      final List<IPredicate<T>> list, final int startInclusive,
      final int endInclusive) {
    final int mid;

    if (startInclusive >= endInclusive) {
      if (startInclusive <= endInclusive) {
        return list.get(startInclusive);
      }
      return null;
    }

    mid = ((startInclusive + endInclusive) >>> 1);
    return new AndPredicate<>(
        AndPredicate.__and(list, startInclusive, mid), //
        AndPredicate.__and(list, (mid + 1), endInclusive));//
  }
}
