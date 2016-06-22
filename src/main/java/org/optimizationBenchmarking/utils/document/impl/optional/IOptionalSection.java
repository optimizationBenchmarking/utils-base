package org.optimizationBenchmarking.utils.document.impl.optional;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabelBuilder;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

/** An optional section. */
public interface IOptionalSection {

  /**
   * Create a label or not. This method may only called if a section is
   * created.
   *
   * @param builder
   *          the label builder to use
   * @return the label or {@code null} if none is needed
   */
  public abstract ILabel createLabel(final ILabelBuilder builder);

  /**
   * Print the title of the section. This method may only called if a
   * section is created.
   *
   * @param title
   *          the title
   */
  public abstract void writeSectionTitle(final IComplexText title);

  /**
   * Print the body of the section.
   *
   * @param isNewSection
   *          was a new section created ({@code true}) or an already
   *          section existing ({@code false})?
   * @param body
   *          the section body
   */
  public abstract void writeSectionBody(final boolean isNewSection,
      final ISectionBody body);

}
