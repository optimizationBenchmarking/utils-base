package org.optimizationBenchmarking.utils.ml.clustering.spec;

import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;

/**
 * A job of the clusterer is a {@link java.util.concurrent.Callable} which
 * returns a clustering result record.
 */
public interface IClusteringJob
    extends ICallableToolJob<IClusteringResult> {
  /**
   * Perform the clustering of the data.
   *
   * @return the clustering result
   * @throws IllegalArgumentException
   *           if the input data could not be clustered
   */
  @Override
  public abstract IClusteringResult call() throws IllegalArgumentException;
}
