package org.optimizationBenchmarking.utils.parallel;

import java.util.concurrent.Future;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/**
 * An iterator working on a list of Futures.
 *
 * @param <T>
 *          the element type
 */
public final class FutureIterator<T> extends BasicIterator<T> {

  /** the futures */
  private transient Future<? extends T>[] m_futures;

  /** the index */
  private transient int m_index;

  /**
   * create the future iterator
   *
   * @param futures
   *          the list of futures.
   */
  public FutureIterator(final Future<? extends T>[] futures) {
    super();
    if (futures == null) {//
      throw new IllegalStateException(
          "The array of futures cannot be null."); //$NON-NLS-1$
    }
    this.m_futures = ((futures.length > 0) ? futures : null);
    this.m_index = 0;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_futures != null);
  }

  /** {@inheritDoc} */
  @Override
  public final T next() {
    final int index, nextIndex;
    final T result;

    if (this.m_futures != null) {
      index = this.m_index;
      this.m_index = nextIndex = (index + 1);
      try {
        result = this.m_futures[index].get();
      } catch (final RuntimeException runtime) {
        throw runtime;
      } catch (final Error error) {
        throw error;
      } catch (final Throwable throwable) {
        throw new IllegalStateException("Cannot evaluate future.", //$NON-NLS-1$
            throwable);
      } finally {
        this.m_futures[index] = null;
        if (nextIndex >= this.m_futures.length) {
          this.m_futures = null;
        }
      }
      return result;
    }

    return super.next();
  }

  /** {@inheritDoc} */
  @Override
  public final void remove() {
    // do nothing
  }
}
