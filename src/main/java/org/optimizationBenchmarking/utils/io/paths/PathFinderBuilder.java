package org.optimizationBenchmarking.utils.io.paths;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsDirectoryPredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/** The builder for path finders. */
public final class PathFinderBuilder
    extends ToolJobBuilder<PathFinder, PathFinderBuilder> {

  /** the paths to visit first */
  private ArrayList<Path> m_visitFirst;

  /** the path predicates */
  private ArrayList<IPredicate<? super Path>> m_pathPredicates;

  /** the attribute predicates */
  private ArrayList<IPredicate<? super BasicFileAttributes>> m_attributePredicates;

  /** create */
  public PathFinderBuilder() {
    super();
  }

  /**
   * Add a path to visit first during the search
   *
   * @param path
   *          the path to visit first during the search
   * @return this builder
   */
  public final PathFinderBuilder addVisitFirst(final Path path) {
    final Path norm;

    if (path != null) {
      norm = PathUtils.normalize(path);
      if (norm != null) {
        if (this.m_visitFirst == null) {
          this.m_visitFirst = new ArrayList<>();
        }
        this.m_visitFirst.add(norm);
      }
    }
    return this;
  }

  /**
   * Add a path to visit first during the search
   *
   * @param path
   *          the path to visit first during the search
   * @return this builder
   */
  public final PathFinderBuilder addVisitFirst(final String path) {
    final Path use;

    if (path != null) {
      use = PathUtils.normalize(path);
      if (use != null) {
        if (this.m_visitFirst == null) {
          this.m_visitFirst = new ArrayList<>();
        }
        this.m_visitFirst.add(use);
      }
    }
    return this;
  }

  /**
   * Add a path predicate. All predicates are concatenated with logical
   * AND.
   *
   * @param predicate
   *          the predicate to add
   * @return this finder builder
   */
  public final PathFinderBuilder addPathPredicate(
      final IPredicate<? super Path> predicate) {
    if (predicate != null) {
      if (this.m_pathPredicates == null) {
        this.m_pathPredicates = new ArrayList<>();
      }
      this.m_pathPredicates.add(predicate);
    }
    return this;
  }

  /**
   * Add an attribute predicate. All predicates are concatenated with
   * logical AND.
   *
   * @param predicate
   *          the predicate to add
   * @return this finder builder
   */
  public final PathFinderBuilder addAttributePredicate(
      final IPredicate<? super BasicFileAttributes> predicate) {
    if (predicate != null) {
      if (this.m_attributePredicates == null) {
        this.m_attributePredicates = new ArrayList<>();
      }
      this.m_attributePredicates.add(predicate);
    }
    return this;
  }

  /**
   * The path we look for must belong to a file.
   *
   * @return this builder
   */
  public final PathFinderBuilder mustBeFile() {
    return this.addAttributePredicate(IsFilePredicate.INSTANCE);
  }

  /**
   * The path we look for must belongs to an executable file.
   *
   * @return this builder
   */
  public final PathFinderBuilder mustBeExecutableFile() {
    return this.addPathPredicate(CanExecutePredicate.INSTANCE)
        .mustBeFile();
  }

  /**
   * The path we look for must be a directory
   *
   * @return this builder
   */
  public final PathFinderBuilder mustBeDirectory() {
    return this.addAttributePredicate(IsDirectoryPredicate.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    if (this.m_attributePredicates.size() <= 0) {
      throw new IllegalArgumentException(
          "Must specify at least one path predicate."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final PathFinder create() {
    final Path[] first;

    this.validate();
    if (this.m_visitFirst != null) {
      first = this.m_visitFirst.toArray(//
          new Path[this.m_visitFirst.size()]);
    } else {
      first = null;
    }

    return new PathFinder(this.getLogger(),
        AndPredicate.and(this.m_pathPredicates),
        AndPredicate.and(this.m_attributePredicates), first);
  }
}
