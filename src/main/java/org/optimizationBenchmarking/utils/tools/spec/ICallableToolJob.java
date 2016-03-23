package org.optimizationBenchmarking.utils.tools.spec;

import java.util.concurrent.Callable;

/**
 * The base interface for callable tool jobs
 *
 * @param <R>
 *          the result type
 */
public interface ICallableToolJob<R> extends Callable<R>, IToolJob {
  //
}
