package org.optimizationBenchmarking.utils.text.predicates;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.comparison.Compare;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * check whether a string contains another string
 */
public class StringContains implements IPredicate<String>, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the string to find */
  private final String m_find;

  /**
   * Create the predicate
   *
   * @param find
   *          the string to find
   */
  public StringContains(final String find) {
    super();
    this.m_find = find;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_find.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    return ((other == this) || //
        ((other instanceof StringContains) && //
            (Compare.equals(this.m_find,
                ((StringContains) other).m_find))));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final String object) {
    return ((object != null) && (object.contains(this.m_find)));
  }
}
