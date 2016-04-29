package org.optimizationBenchmarking.utils.versioning;

import java.util.Comparator;

/**
 * Comparator for ordering versions with additionally considering the build
 * meta data field when comparing versions.
 * <p>
 * Note: this comparator imposes orderings that are inconsistent with
 * equals.
 * </p>
 */
public final class VersionWithBuildMetaDataOrder
    implements Comparator<Version> {

  /** the globally shared instance */
  public static final VersionWithBuildMetaDataOrder INSTANCE = new VersionWithBuildMetaDataOrder();

  /** create */
  private VersionWithBuildMetaDataOrder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int compare(final Version o1, final Version o2) {
    return Version.compareWithBuildMetaData(o1, o2);
  }
}
