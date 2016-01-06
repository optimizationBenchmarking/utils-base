package org.optimizationBenchmarking.utils.ml.fitting.spec;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathRenderable;
import org.optimizationBenchmarking.utils.document.spec.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for representing a real function that depends on one
 * independent variable plus some extra parameters.
 */
public abstract class ParametricUnaryFunction implements IMathRenderable {

  /** create */
  protected ParametricUnaryFunction() {
    super();
  }

  /**
   * Compute the value of the function.
   *
   * @param x
   *          Point for which the function value should be computed.
   * @param parameters
   *          function parameters.
   * @return the value.
   */
  public abstract double value(double x, double[] parameters);

  /**
   * Compute the gradient of the function with respect to its parameters.
   *
   * @param x
   *          Point for which the function value should be computed.
   * @param parameters
   *          Function parameters.
   * @param gradient
   *          the variable to receive the gradient
   */
  public abstract void gradient(double x, double[] parameters,
      final double[] gradient);

  /**
   * Create a unary function representing a specific configuration of this
   * parametric unary function.
   *
   * @param parameters
   *          the parameter configuration
   * @return the unary function
   */
  public abstract UnaryFunction toUnaryFunction(final double[] parameters);

  /**
   * Get the number of parameters
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
