package org.optimizationBenchmarking.utils.ml.clustering.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** The basic, common interface for clustering job builders. */
public interface IClusteringJobBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IClusteringJobBuilder setLogger(final Logger logger);

  /**
   * <p>
   * Set the minimal number of clusters. If this number is specified, the
   * algorithm will attempt to divide the data into at least {@code number}
   * clusters.
   * </p>
   * <p>
   * If the {@linkplain #setMinClusters(int) minimum} and
   * {@linkplain #setMaxClusters(int) maximum} number of clusters is
   * specified to be the same, the clusterer will attempt to produce
   * exactly this many clusters. If a range is specified, it will attempt
   * to find the best clustering within the range or use a default value.
   * This also holds if only one end or neither end of the range is
   * defined.
   * </p>
   *
   * @param minClusters
   *          the minimum number of clusters
   * @return the cluster job builder
   * @see #setMaxClusters(int)
   */
  public abstract IClusteringJobBuilder setMinClusters(
      final int minClusters);

  /**
   * <p>
   * Set the maximum number of clusters. If this number is specified, the
   * algorithm will attempt to divide the data into at most {@code number}
   * clusters.
   * </p>
   * <p>
   * If the {@linkplain #setMinClusters(int) minimum} and
   * {@linkplain #setMaxClusters(int) maximum} number of clusters is
   * specified to be the same, the clusterer will attempt to produce
   * exactly this many clusters. If a range is specified, it will attempt
   * to find the best clustering within the range or use a default value.
   * This also holds if only one end or neither end of the range is
   * defined.
   * </p>
   *
   * @param maxClusters
   *          the maximum number of clusters
   * @return the cluster job builder
   * @see #setMinClusters(int)
   */
  public abstract IClusteringJobBuilder setMaxClusters(
      final int maxClusters);

  /** {@inheritDoc} */
  @Override
  public abstract IClusteringJob create();
}
