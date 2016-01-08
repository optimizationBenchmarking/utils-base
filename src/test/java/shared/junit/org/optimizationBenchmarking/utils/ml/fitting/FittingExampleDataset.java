package shared.junit.org.optimizationBenchmarking.utils.ml.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;

/** An example data set for the fitting of data. */
public class FittingExampleDataset
    implements Comparable<FittingExampleDataset> {

  /** the name of the example data set */
  public final String name;

  /** the data matrix */
  public final IMatrix data;

  /** thefunction to fit */
  public final ParametricUnaryFunction function;

  /**
   * create the fitting example
   *
   * @param _name
   *          the name of the example data set
   * @param _data
   *          the data matrix
   * @param _function
   *          the function to fit
   */
  public FittingExampleDataset(final String _name, final IMatrix _data,
      final ParametricUnaryFunction _function) {
    super();

    if (_function == null) {
      throw new IllegalArgumentException("Function cannot be null."); //$NON-NLS-1$
    }
    if (_data == null) {
      throw new IllegalArgumentException("Data matrix cannot be null."); //$NON-NLS-1$
    }

    if (_data.n() != 2) {
      throw new IllegalArgumentException(
          "Data must have two columns, but has " //$NON-NLS-1$
              + _data.n());
    }
    if (_data.m() < -_function.getParameterCount()) {
      throw new IllegalArgumentException(//
          "There must be at least as many data points as parameters of the function (" //$NON-NLS-1$
              + _function.getParameterCount() + ") but there only are "//$NON-NLS-1$
              + _data.n());

    }
    if (_name == null) {
      throw new IllegalArgumentException(
          "The example name cannot be null."); //$NON-NLS-1$
    }

    this.data = _data;
    this.name = _name;
    this.function = _function;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.name + " with " + //$NON-NLS-1$
        this.function + " on " + //$NON-NLS-1$
        this.data.m() + " points"; //$NON-NLS-1$
  }

  /**
   * Plot the data and the fitting to the given file
   *
   * @param dest
   *          the destination path
   * @param model
   *          the model to plot
   * @param fitting
   *          the fitting
   * @param quality
   *          the quality of the fitting
   * @throws IOException
   *           if i/o fails
   */
  public final void plot(final Path dest,
      final ParametricUnaryFunction model, final double[] fitting,
      final double quality) throws IOException {
    final int size;
    double sum;
    int index;
    double x, y, calc;

    try (final OutputStream os = PathUtils.openOutputStream(dest)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {
          sum = 0d;
          size = this.data.m();
          for (index = 0; index < size; index++) {
            bw.write(Double.toString(x = this.data.getDouble(index, 0)));
            bw.write('\t');
            bw.write(Double.toString(y = this.data.getDouble(index, 1)));
            bw.write('\t');
            bw.write(Double.toString(calc = model.value(x, fitting)));
            calc = Math.abs(calc - y);
            y = Math.abs(y);
            if (y > 0d) {
              calc /= y;
            }
            sum += calc;
            bw.newLine();
          }

          bw.write('#');
          bw.newLine();
          bw.write('#');
          bw.newLine();
          bw.write("# fitting:"); //$NON-NLS-1$
          for (final double d : fitting) {
            bw.write('\t');
            bw.write(Double.toString(d));
          }
          bw.newLine();
          bw.write("# quality:\t"); //$NON-NLS-1$
          bw.write(Double.toString(quality));
          bw.newLine();
          bw.write("# error sum:\t"); //$NON-NLS-1$
          bw.write(Double.toString(sum / size));
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final FittingExampleDataset o) {
    return ((o == this) ? 0 : (this.name.compareTo(o.name)));
  }
}
