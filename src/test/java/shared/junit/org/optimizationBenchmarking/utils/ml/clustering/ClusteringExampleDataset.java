package shared.junit.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** a clustering example */
public final class ClusteringExampleDataset
    implements Comparable<ClusteringExampleDataset> {

  /** the name of the example */
  public final String name;

  /** the matrix */
  public final IMatrix data;

  /** the number of anticipated clusters */
  public final int classes;

  /** the goal clusters */
  public final int[] clusters;

  /**
   * create the clustering example
   *
   * @param _name
   *          the example name
   * @param _data
   *          the data
   * @param _classes
   *          the goal clusters
   * @param _clusters
   *          the clusters
   */
  public ClusteringExampleDataset(final String _name, final IMatrix _data,
      final int _classes, final int[] _clusters) {
    super();

    if (_name == null) {
      throw new IllegalArgumentException("Name cannot be null."); //$NON-NLS-1$
    }
    if (_data == null) {
      throw new IllegalArgumentException("Data cannot be null."); //$NON-NLS-1$
    }
    if (_data.m() <= 1) {
      throw new IllegalArgumentException(//
          "The number of rows in the data matrix cannot be less than 2, but is " //$NON-NLS-1$
              + _data.m());
    }
    if (_data.n() <= 0) {
      throw new IllegalArgumentException(//
          "The number of columns in the data matrix cannot be less than 1, but is " //$NON-NLS-1$
              + _data.n());
    }
    if (_clusters == null) {
      throw new IllegalArgumentException("Clusters cannot be null."); //$NON-NLS-1$
    }
    if (_clusters.length != _data.m()) {
      throw new IllegalArgumentException(//
          "Cluster assignments cannot different from number of rows in data matrix (" //$NON-NLS-1$
              + _data.m() + ") but is " + _clusters.length);//$NON-NLS-1$
    }
    if ((_classes <= 0) || (_classes > _clusters.length)) {
      throw new IllegalArgumentException("Invalid cluster number: " //$NON-NLS-1$
          + _clusters);
    }

    this.data = _data;
    this.name = _name;
    this.classes = _classes;
    this.clusters = _clusters;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final ClusteringExampleDataset o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name);
  }
}
