package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabelBuilder;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionRenderer;

/** An abstract base class for section renderers. */
public abstract class SectionRenderer implements ISectionRenderer {

  /** did we have a title */
  private boolean m_hasTitle;

  /** create the optional section */
  protected SectionRenderer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ILabel createSectionLabel(final ILabelBuilder builder) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final void renderSectionTitle(final IComplexText title) {// empty
    this.m_hasTitle = true;
    this.doRenderSectionTitle(title);
  }

  /**
   * Do the actual work of rendering the section body.
   *
   * @param isNewSection
   *          was the section rendered as a new section ({@code true}) or
   *          as part of an existing section ({@code false})?
   * @param body
   *          the body to render to
   */
  protected void doRenderSectionBody(final boolean isNewSection,
      final ISectionBody body) {// empty
  }

  /**
   * Do the actual work of rendering the section title
   *
   * @param title
   *          the title to render to
   */
  protected void doRenderSectionTitle(final IComplexText title) {// empty
  }

  /** {@inheritDoc} */
  @Override
  public final void renderSectionBody(final ISectionBody body) {
    this.doRenderSectionBody(this.m_hasTitle, body);
    this.m_hasTitle = false;
  }
}
