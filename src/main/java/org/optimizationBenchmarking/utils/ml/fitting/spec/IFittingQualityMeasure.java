package org.optimizationBenchmarking.utils.ml.fitting.spec;

import java.util.Random;

/**
 * This interface can compute the fitting quality of a given set of model
 * parameters on a data set.
 */
public interface IFittingQualityMeasure {

  /**
   * Compute the quality of the given model parameters
   *
   * @param model
   *          the model to be assessed
   * @param parameters
   *          the model parameters
   * @return the quality
   */
  public abstract double evaluate(final ParametricUnaryFunction model,
      final double[] parameters);

  /**
   * Compute the quality, Jacobian, residuals, root-mean-square error and
   * root-square error of a given set of model parameters. The member
   * variables of {@code dest} are re-used if they are not {@code null} and
   * of the right dimension. Otherwise they may be overwritten.
   *
   * @param model
   *          the model
   * @param parameters
   *          the model parameters
   * @param computeResiduals
   *          should we compute the {@link FittingEvaluation#residuals} (
   *          {@code true}) or leave that variable unchanged ({@code false}
   *          )?
   * @param computeJacobinian
   *          should we compute the {@link FittingEvaluation#jacobian} (
   *          {@code true}) or leave that variable unchanged ({@code false}
   *          )?
   * @param dest
   *          the destination record.
   */
  public abstract void evaluate(final ParametricUnaryFunction model,
      final double[] parameters, final boolean computeResiduals,
      final boolean computeJacobinian, final FittingEvaluation dest);

  /**
   * Try to create a compatible quality measure based on a subset of the
   * points backing this measure. Usually, {@code npoints} will be small,
   * say 4 or 5. The returned measure should be a compact object which can
   * be evaluated rather quickly, while the original quality measure may be
   * backed by thousands of points and thus be rather slow to compute. By
   * creating a subselected quality measure, we may be able to perform very
   * quick optimization, at the cose of precision. The original measure
   * then can be used to fine-tune.
   *
   * @param npoints
   *          the suggested number of points to use
   * @param random
   *          a random number generator used for making the point selection
   * @return the measure
   */
  public abstract IFittingQualityMeasure subselect(final int npoints,
      final Random random);

  /**
   * The number of samples used by this quality measure, which is the
   * number of {@linkplain FittingEvaluation#residuals residuals} that will
   * be provided and also the number of rows of the
   * {@linkplain FittingEvaluation#jacobian Jacobian} if
   * {@link #evaluate(ParametricUnaryFunction, double[],boolean,boolean, FittingEvaluation)}
   * is called.
   *
   * @return the number of samples
   */
  public abstract int getSampleCount();
}
