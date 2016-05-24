package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/**
 * The base class for tool job builders
 *
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class ToolJobBuilder<J extends IToolJob, R extends ToolJobBuilder<J, R>>
    implements IToolJobBuilder {

  /** the logger */
  Logger m_logger;

  /** create the tool job builder */
  protected ToolJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final R setLogger(final Logger logger) {
    this.m_logger = logger;
    return ((R) this);
  }

  /**
   * Get the logger
   *
   * @return the logger
   */
  public final Logger getLogger() {
    return this.m_logger;
  }

  /**
   * Create the job. Throw an exception if necessary.
   *
   * @return the job
   * @throws Exception
   *           if something goes wrong
   */
  @Override
  public abstract J create() throws Exception;
}
