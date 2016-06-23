package org.optimizationBenchmarking.utils.document.impl;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IOptionalSection;
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
   *          the optional section writer
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

  /**
   * Optionally create a set of sections. {@code writers} is an iterator
   * returning the sections to be written. If it returns only a single
   * section, no sub-section is created in {@code body} and the body of the
   * section is directly printed. If it returns multiple sections, a
   * section is created for each of them. It can also sometimes return
   * {@code null}, which is always ignored.
   *
   * @param body
   *          an existing body
   * @param labelCollection
   *          the destination collection to receive the created labels, if
   *          any, or {@code null} to store the labels
   * @param writers
   *          the optional section writers
   */
  public static final void optionalSections(final ISectionBody body,
      final Collection<ILabel> labelCollection,
      final Iterator<? extends IOptionalSection> writers) {
    boolean putSections;
    IOptionalSection cached, current;

    if (writers != null) {
      cached = null;
      putSections = false;
      while (writers.hasNext()) {
        current = writers.next();
        if (current != null) {
          if (!putSections) {
            if (cached == null) {
              cached = current;
              continue;
            }
            putSections = true;
            OptionalElements.optionalSection(body, true, labelCollection,
                cached);
            cached = null;
          }

          OptionalElements.optionalSection(body, true, labelCollection,
              current);
        }
      }
      if (cached != null) {
        OptionalElements.optionalSection(body, false, labelCollection,
            cached);
      }
    }
  }

  /**
   * Optionally create a set of sections. {@code writers} is an iterator
   * returning the sections to be written. If it returns only a single
   * section, no sub-section is created in {@code body} and the body of the
   * section is directly printed. If it returns multiple sections, a
   * section is created for each of them. It can also sometimes return
   * {@code null}, which is always ignored.
   *
   * @param body
   *          an existing body
   * @param labelCollection
   *          the destination collection to receive the created labels, if
   *          any, or {@code null} to store the labels
   * @param writers
   *          the optional section writers
   */
  public static final void optionalSections(final ISectionBody body,
      final Collection<ILabel> labelCollection,
      final Iterable<? extends IOptionalSection> writers) {
    if (writers != null) {
      OptionalElements.optionalSections(body, labelCollection,
          writers.iterator());
    }
  }
}
