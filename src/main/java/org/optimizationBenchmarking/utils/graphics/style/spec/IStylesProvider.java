package org.optimizationBenchmarking.utils.graphics.style.spec;

/**
 * An interface for entities which can provide {@link IStyles style sets}.
 */
public interface IStylesProvider {

  /**
   * Access the style set
   *
   * @return the style set
   */
  public abstract IStyles getStyles();
}
