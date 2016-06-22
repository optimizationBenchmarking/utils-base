package org.optimizationBenchmarking.utils.document.impl.optional;

import java.util.Collection;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** the optional elements */
public final class OptionalElements {

  /** the hidden constructor */
  private OptionalElements() {
    ErrorUtils.doNotCall();
  }

  /**
   * Optionally create a section
   *
   * @param body
   *          an existing body
   * @param createSection
   *          should a new section be created ({@code true}) or the
   *          existing {@code body} be reused ({@code false})?
   * @param labelCollection
   *          the destination collection to receive the created labels, if
   *          any, or {@code null} to store the labels
   * @param writer
   */
  public static final void optionalSection(final ISectionBody body,
      final boolean createSection,
      final Collection<ILabel> labelCollection,
      final IOptionalSection writer) {
    final ILabel label;
    if (createSection) {
      label = writer.createLabel(body);
      if ((label != null) && (labelCollection != null)) {
        labelCollection.add(label);
      }
      try (final ISection section = body.section(label)) {
        try (final IComplexText title = section.title()) {
          writer.writeSectionTitle(title);
        }
        try (final ISectionBody text = section.body()) {
          writer.writeSectionBody(true, text);
        }
      }
    } else {
      writer.writeSectionBody(false, body);
    }
  }
}
