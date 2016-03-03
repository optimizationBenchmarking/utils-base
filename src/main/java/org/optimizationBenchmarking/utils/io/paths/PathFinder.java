package org.optimizationBenchmarking.utils.io.paths;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * This job attempts to find a path.
 */
public final class PathFinder extends ToolJob implements Callable<Path> {

  /** the path predicates */
  private final IPredicate<Path> m_pathPredicate;
  /** the attribute predicates */
  private final IPredicate<BasicFileAttributes> m_attsPredicate;
  /** the paths to visit first */
  private final Path[] m_visitFirst;

  /**
   * create the path finder
   *
   * @param logger
   *          the logger
   * @param visitFirst
   *          {@code null} or a list of paths to visit <em>before</em>
   *          walking through the {@code PATH}. Elements in this array may
   *          also be {@code null} or non-existing paths.
   * @param pathPredicate
   *          the path predicate to match
   * @param attsPredicate
   *          the attributes predicate
   */
  PathFinder(final Logger logger, final IPredicate<Path> pathPredicate,
      final IPredicate<BasicFileAttributes> attsPredicate,
      final Path[] visitFirst) {
    super(logger);
    this.m_pathPredicate = pathPredicate;
    this.m_attsPredicate = attsPredicate;
    this.m_visitFirst = visitFirst;
  }

  /** {@inheritDoc} */
  @Override
  public final Path call() {
    return PathUtils.findFirstInPath(this.m_pathPredicate,
        this.m_attsPredicate, this.m_visitFirst, this.getLogger());
  }
}
