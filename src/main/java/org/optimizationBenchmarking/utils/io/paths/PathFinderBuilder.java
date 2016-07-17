package org.optimizationBenchmarking.utils.io.paths;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsDirectoryPredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.TextProcessResultContains;
import org.optimizationBenchmarking.utils.io.paths.predicates.TextProcessResultPredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.text.predicates.StringContains;
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
   *          the path to visit first during the search, {@code null} is
   *          silently ignored.
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
   *          the path to visit first during the search, {@code null} is
   *          silently ignored.
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
   * Add a configuration-argument based path to be visited first. If a path
   * is stored under key {@code argument} in {@code config}, this path is
   * added to the visit-first list for the search. Otherwise, nothing is
   * added.
   *
   * @param config
   *          the configuration context, {@code null} is silently ignored.
   * @param argument
   *          the argument, {@code null} is silently ignored.
   * @return this builder
   */
  public final PathFinderBuilder addConfigArgVisitFirst(
      final Configuration config, final String argument) {
    final Path path;
    if ((config != null) && (argument != null)) {
      path = config.getPath(argument, null);
      if (path != null) {
        return this.addVisitFirst(path);
      }
    }
    return this;
  }

  /**
   * Add a configuration-argument based path to be visited first. If a path
   * is stored under key {@code argument} in
   * {@link org.optimizationBenchmarking.utils.config.Configuration#getRoot()}
   * , this path is added to the visit-first list for the search.
   * Otherwise, nothing is added.
   *
   * @param argument
   *          the argument, {@code null} is silently ignored.
   * @return this builder
   */
  public final PathFinderBuilder addConfigArgVisitFirst(
      final String argument) {
    return this.addConfigArgVisitFirst(Configuration.getRoot(), argument);
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
   * Add a predicate which executes the given process with the given
   * {@code arguments}, reads all of its output, and returns {@code true}
   * only if the process terminates successfully (with return value
   * {@code 0}).
   *
   * @param arguments
   *          the arguments
   * @return this builder
   */
  public final PathFinderBuilder addCanExecuteAsTextProcess(
      final String... arguments) {
    return this.addPathPredicate(//
        new TextProcessResultPredicate(arguments));
  }

  /**
   * Add a predicate which executes the given process with the given
   * {@code arguments}, reads all of its output, and returns {@code true}
   * only if each of predicates in {@code predicates} at least return
   * {@code true} once and the process itself returns {@code 0}.
   *
   * @param arguments
   *          the command line arguments
   * @param predicates
   *          the predicates
   * @return this builder
   */
  public final PathFinderBuilder addTextProcessOutputContainsAll(
      final String[] arguments, final IPredicate<String>[] predicates) {
    return this.addPathPredicate(
        new TextProcessResultContains(arguments, predicates));
  }

  /**
   * Add a predicate which executes the given process with the given
   * {@code arguments}, reads all of its output, and returns {@code true}
   * only if each of the strings in {@code contains} are found in at least
   * one line of output.
   *
   * @param arguments
   *          the arguments for the process to be executed
   * @param contains
   *          the strings to be found in the output of the process
   * @return this builder
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final PathFinderBuilder addTextProcessOutputContainsAll(
      final String[] arguments, final String[] contains) {
    final IPredicate[] predicates;
    int i;

    predicates = new IPredicate[contains.length];
    i = (-1);
    for (final String s : contains) {
      predicates[++i] = new StringContains(s);
    }
    return this.addTextProcessOutputContainsAll(arguments, predicates);
  }

  /**
   * Add a file name comparison predicate
   *
   * @param ignoreExtension
   *          ignore (remove) the file extension (if any) before the name
   *          comparison
   * @param names
   *          the string list
   * @return this builder
   */
  public final PathFinderBuilder addNamePredicate(
      final boolean ignoreExtension, final String... names) {
    return this.addPathPredicate(//
        new FileNamePredicate(ignoreExtension, names));
  }

  /**
   * Add an attribute predicate. All predicates are concatenated with
   * logical AND, {@code null} is silently ignored.
   *
   * @param predicate
   *          the predicate to add, {@code null} is silently ignored.
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
  public final PathFinder create() {
    final Path[] first;
    ArrayList<Path> firstList;

    firstList = this.m_visitFirst;
    if (firstList != null) {
      first = firstList.toArray(new Path[firstList.size()]);
    } else {
      first = null;
    }

    return new PathFinder(this.getLogger(),
        AndPredicate.and(this.m_pathPredicates),
        AndPredicate.and(this.m_attributePredicates), first);
  }
}
