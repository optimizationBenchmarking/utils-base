package org.optimizationBenchmarking.utils.ml.clustering.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A distance measure which can be used in clustering. */
public interface IDistanceMeasure {

  /**
   * Compute the distance of {@code rowA} of {@code matrixA} to
   * {@code rowB} of {@code matrixB}.
   *
   * @param matrixA
   *          the first matrix
   * @param rowA
   *          the row of the first matrix which should be used
   * @param matrixB
   *          the second matrix
   * @param rowB
   *          the row of the second matrix which should be used
   * @return the distance
   */
  public abstract double compute(final IMatrix matrixA, final int rowA,
      final IMatrix matrixB, final int rowB);

  /**
   * Will this {@linkplain #compute(IMatrix, int, IMatrix, int) distance
   * measure} usually produce integer results ({@code true}) or floating
   * point results ({@code false})? This can be used to optimize backing
   * data structures.
   *
   * @return {@code true} if {@link #compute(IMatrix, int, IMatrix, int)
   *         distance measure} usually returns integer numbers,
   *         {@code false} otherwise
   */
  public abstract boolean isInteger();
}
