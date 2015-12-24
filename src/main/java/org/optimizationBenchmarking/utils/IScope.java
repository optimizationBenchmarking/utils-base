package org.optimizationBenchmarking.utils;

import java.io.Closeable;

/**
 * This interface marks a scope. It is basically a version of
 * {@link java.io.Closeable} whose {@link java.io.Closeable#close()} method
 * does not throw an exception.
 */
public interface IScope extends Closeable {

  /** End the scope. */
  @Override
  public abstract void close();
}
