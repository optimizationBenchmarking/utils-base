package org.optimizationBenchmarking.utils.ml.fitting.spec;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathRenderable;
import org.optimizationBenchmarking.utils.document.spec.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for representing a real function that depends on one
 * independent variable plus some extra parameters. It represents a
 * <a href="https://en.wikipedia.org/wiki/Family_of_curves">family of
 * curves</a> over a single parameter {@code x} and a set of
 * {@link #getParameterCount() m} parameters.
 * <p>
 * A family of curves is a set of curves, each of which is given by a
 * function or parametrization in which one or more of the parameters is
 * variable. In general, the parameter(s) influence the shape of the curve
 * in a way that is more complicated than a simple linear transformation.
 * Sets of curves given by an implicit relation may also represent families
 * of curves.
 * <sup><a href="https://en.wikipedia.org/wiki/Family_of_curves">citation
 * </a></sup>
 * </p>
 */
public abstract class ParametricUnaryFunction implements IMathRenderable {

  /** create */
  protected ParametricUnaryFunction() {
    super();
  }

  /**
   * Compute the value of the function defined by the given
   * {@code parameters} at a given {@code x}-coordinate.
   *
   * @param x
   *          the point for which the function value should be computed
   * @param parameters
   *          the parameters which specify the shape of the function
   * @return the value of this function at {@code x}
   */
  public abstract double value(final double x, final double[] parameters);

  /**
   * Compute the gradient of the function with respect to its
   * {@code parameters} at a given {@code x}-coordinate. {@code gradient}
   * is the destination array which must have length
   * {@link #getParameterCount() m}. For each parameter {@code P_1..P_m}
   * the array will be filled with {@code df/dP_1}, {@code df/dP_2},
   * {@code ..}, {@code df/dP_m} at {@code x}.
   *
   * @param x
   *          the point for which the function gradients should be computed
   * @param parameters
   *          the parameters which specify the shape of the function
   * @param gradient
   *          the variables to receive the gradients
   */
  public abstract void gradient(final double x, final double[] parameters,
      final double[] gradient);

  /**
   * Create a unary function representing a specific configuration of this
   * parametric unary function. This will be a {@link UnaryFunction} which
   * has the {@code parameters} stored inside and (probably) delegates its
   * {@code compute} calls to {@link #value(double, double[])}.
   *
   * @param parameters
   *          the parameters which specify the shape of the function
   * @return the unary function
   */
  public abstract UnaryFunction toUnaryFunction(final double[] parameters);

  /**
   * Get the number of parameters of this family of curves.
   *
   * @return the number of parameters
   */
  public abstract int getParameterCount();

  /**
   * Create a parameter guesser
   *
   * @param data
   *          the data matrix
   * @return the parameter guesser
   */
  public abstract IParameterGuesser createParameterGuesser(
      final IMatrix data);

  /** {@inheritDoc} */
  @Override
  public abstract void mathRender(final ITextOutput out,
      final IParameterRenderer renderer);

  /** {@inheritDoc} */
  @Override
  public abstract void mathRender(final IMath out,
      final IParameterRenderer renderer);

  /**
   * Render the unary function, but provide a renderable for the parameter
   * {@code x}
   *
   * @param out
   *          the destination
   * @param renderer
   *          the parameter renderer for the {@link #getParameterCount()}
   *          parameters
   * @param x
   *          the parameter renderer for the {@code x} input of the
   *          function
   */
  public abstract void mathRender(ITextOutput out,
      IParameterRenderer renderer, final IMathRenderable x);

  /**
   * Render the unary function, but provide a renderable for the parameter
   * {@code x}
   *
   * @param out
   *          the destination
   * @param renderer
   *          the parameter renderer for the {@link #getParameterCount()}
   *          parameters
   * @param x
   *          the parameter renderer for the {@code x} input of the
   *          function
   */
  public abstract void mathRender(IMath out, IParameterRenderer renderer,
      final IMathRenderable x);

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
        ((o != null) && (o.getClass() == this.getClass())));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return ((this.getClass().hashCode() - 1709) * 991);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
