package org.optimizationBenchmarking.utils.versioning;

import java.util.Comparator;

/** the natural order of version comparison */
public final class VersionNaturalOrder implements Comparator<Version> {

  /** the globally shared instance */
  public static final VersionNaturalOrder INSTANCE = new VersionNaturalOrder();

  /** create */
  private VersionNaturalOrder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int compare(final Version o1, final Version o2) {
    return Version.compare(o1, o2);
  }
}
