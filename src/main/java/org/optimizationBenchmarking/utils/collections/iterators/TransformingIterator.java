package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Iterator;

/**
 * An iterator which transforms the contents of another iterator.
 *
 * @param <IN>
 *          the class of the result elements of the inner iterator
 * @param <OUT>
 *          the class of the elements provided by this iterator
 */
public abstract class TransformingIterator<IN, OUT>
    extends BasicIterator<OUT> {

  /** the inner, wrapped iterator */
  private Iterator<IN> m_inner;

  /**
   * create the transforming iterator
   *
   * @param inner
   *          the inner iterator to be wrapped and transformed
   */
  public TransformingIterator(final Iterator<IN> inner) {
    if (inner == null) {
      throw new IllegalArgumentException("Inner iterator cannot be null."); //$NON-NLS-1$
    }
    this.m_inner = inner;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    if (this.m_inner != null) {
      if (this.m_inner.hasNext()) {
        return true;
      }
      this.m_inner = null;
    }
    return false;
  }

  /**
   * transform the specified element
   *
   * @param element
   *          the element
   * @return the transformed element
   */
  @SuppressWarnings("unchecked")
  protected OUT transform(final IN element) {
    return ((OUT) element);
  }

  /** {@inheritDoc} */
  @Override
  public final OUT next() {
    if (this.m_inner != null) {
      return this.transform(this.m_inner.next());
    }
    return super.next();
  }

}
