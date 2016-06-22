package org.optimizationBenchmarking.utils.document.impl.optional;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabelBuilder;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

/** an abstract optional section */
public class OptionalSection implements IOptionalSection {

  /** create the optional section */
  protected OptionalSection() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ILabel createLabel(final ILabelBuilder builder) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void writeSectionTitle(final IComplexText title) {// empty
  }

  /** {@inheritDoc} */
  @Override
  public void writeSectionBody(final boolean isNewSection,
      final ISectionBody body) {// empty
  }
}
