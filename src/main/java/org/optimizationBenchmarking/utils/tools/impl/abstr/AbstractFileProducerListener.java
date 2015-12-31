package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map.Entry;

import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** A basic file producer listener. */
public class AbstractFileProducerListener
    implements IFileProducerListener {

  /** Create the abstract file producer listener. */
  public AbstractFileProducerListener() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public void onFilesFinalized(
      final Collection<Entry<Path, IFileType>> result) {
    // does nothing
  }
}
