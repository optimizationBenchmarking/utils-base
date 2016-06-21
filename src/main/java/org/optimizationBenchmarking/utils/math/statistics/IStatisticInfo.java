package org.optimizationBenchmarking.utils.math.statistics;

/** An interface providing a set of standard statistical measures */
public interface IStatisticInfo {

  /**
   * Get the number of elements from which the information was computed. In
   * the case that the information describes a general distribution and has
   * not been computed as aggregate from elements, this function will
   * return {@code 1L}.
   *
   * @return the number of elements from which the information was
   *         computed, or {@code -1L} for general information not based on
   *         sample statistics.
   */
  public abstract long getSampleSize();

  /**
   * Get the minimum, i.e., the smallest possible element in the sample or
   * distribution. This method will return
   * {@link java.lang.Double#NEGATIVE_INFINITY} if the source either
   * contains that value or is a distribution unbounded towards the
   * minimum.
   *
   * @return the minimum
   */
  public abstract Number getMinimum();

  /**
   * Get the maximum, i.e., the largest possible element in the sample or
   * distribution. This method will return
   * {@link java.lang.Double#POSITIVE_INFINITY} if the source either
   * contains that value or is a distribution unbounded towards the
   * maximum.
   *
   * @return the maximum
   */
  public abstract Number getMaximum();

  /**
   * Get the arithmetic mean, {@link java.lang.Double#NaN} if undefined
   *
   * @return the arithmetic mean
   */
  public abstract Number getArithmeticMean();

  /**
   * Get the standard deviation, {@link java.lang.Double#NaN} if undefined
   *
   * @return the standard deviation
   */
  public abstract Number getStandardDeviation();

  /**
   * Get the median, {@link java.lang.Double#NaN} if undefined
   *
   * @return the median
   */
  public abstract Number getMedian();

  /**
   * Get the inter-quartile range, i.e.,
   * <code>{@link #get75Quantile()}-{@link #get25Quantile()}</code>,
   * {@link java.lang.Double#NaN} if undefined
   *
   * @return the inter-quartile range
   */
  public abstract Number getInterQuartileRange();

  /**
   * Get the 5% quantile, {@link java.lang.Double#NaN} if undefined
   *
   * @return the 5% quantile
   */
  public abstract Number get05Quantile();

  /**
   * Get the 25% quantile, {@link java.lang.Double#NaN} if undefined
   *
   * @return the 25% quantile
   */
  public abstract Number get25Quantile();

  /**
   * Get the 75% quantile, {@link java.lang.Double#NaN} if undefined
   *
   * @return the 75% quantile
   */
  public abstract Number get75Quantile();

  /**
   * Get the 95% quantile, {@link java.lang.Double#NaN} if undefined
   *
   * @return the 95% quantile
   */
  public abstract Number get95Quantile();
}
