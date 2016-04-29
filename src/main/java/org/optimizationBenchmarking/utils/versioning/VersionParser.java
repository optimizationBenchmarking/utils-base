package org.optimizationBenchmarking.utils.versioning;

import org.optimizationBenchmarking.utils.parsers.Parser;

/** A parser for versions. */
public final class VersionParser extends Parser<Version> {

  /** the serial version uid */
  private static final long serialVersionUID = 0L;

  /** the gloally shared instance */
  public static final VersionParser INSTANCE = new VersionParser();

  /** the internal constructor */
  private VersionParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Version> getOutputClass() {
    return Version.class;
  }

  /** {@inheritDoc} */
  @Override
  public final Version parseString(final String string)
      throws IllegalArgumentException {
    return Version.parseVersion(string);
  }
}
