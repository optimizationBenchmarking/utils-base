package shared;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * An immediate future
 *
 * @param <T>
 *          the type parameter
 */
public final class ImmediateFuture<T> implements Future<T> {

  /** the result */
  final T m_result;
  /** the caught error */
  final Throwable m_error;

  /**
   * Create the immediate future
   *
   * @param callable
   *          the callable
   */
  public ImmediateFuture(final Callable<T> callable) {
    super();

    T result;
    Throwable error;

    try {
      result = callable.call();
      error = null;
    } catch (final Throwable caught) {
      error = caught;
      result = null;
    }
    this.m_error = error;
    this.m_result = result;
  }

  /**
   * Create the immediate future
   *
   * @param runnable
   *          the runnable
   * @param result
   *          the result
   */
  public ImmediateFuture(final Runnable runnable, final T result) {
    super();

    Throwable error;

    try {
      runnable.run();
      error = null;
    } catch (final Throwable caught) {
      error = caught;
    }
    this.m_error = error;
    this.m_result = result;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean cancel(final boolean mayInterruptIfRunning) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isCancelled() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isDone() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final T get() throws InterruptedException, ExecutionException {
    if (this.m_error != null) {
      throw new ExecutionException(this.m_error);
    }
    return this.m_result;
  }

  /** {@inheritDoc} */
  @Override
  public final T get(final long timeout, final TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    if (this.m_error != null) {
      throw new ExecutionException(this.m_error);
    }
    return this.m_result;
  }
}
