package org.optimizationBenchmarking.utils.document.impl;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureRenderer;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeriesRenderer;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionRenderer;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** A set of utility methods to deal with different rendering objects. */
public final class Renderers {

  /** the hidden constructor */
  private Renderers() {
    ErrorUtils.doNotCall();
  }

  /**
   * Optionally create a section or directly include its body.
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
  public static final void renderSection(final ISectionBody body,
      final boolean createSection,
      final Collection<ILabel> labelCollection,
      final ISectionRenderer writer) {
    final ILabel label;
    if (createSection) {
      label = writer.createSectionLabel(body);
      if ((label != null) && (labelCollection != null)) {
        labelCollection.add(label);
      }
      try (final ISection section = body.section(label)) {
        try (final IComplexText title = section.title()) {
          writer.renderSectionTitle(title);
        }
        try (final ISectionBody text = section.body()) {
          writer.renderSectionBody(text);
        }
      }
    } else {
      writer.renderSectionBody(body);
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
  public static final void renderSections(final ISectionBody body,
      final Collection<ILabel> labelCollection,
      final Iterator<? extends ISectionRenderer> writers) {
    boolean putSections;
    ISectionRenderer cached, current;

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
            Renderers.renderSection(body, true, labelCollection, cached);
            cached = null;
          }

          Renderers.renderSection(body, true, labelCollection, current);
        }
      }
      if (cached != null) {
        Renderers.renderSection(body, false, labelCollection, cached);
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
  public static final void renderSections(final ISectionBody body,
      final Collection<ILabel> labelCollection,
      final Iterable<? extends ISectionRenderer> writers) {
    if (writers != null) {
      Renderers.renderSections(body, labelCollection, writers.iterator());
    }
  }

  /**
   * Render a figure
   *
   * @param figureRenderer
   *          the figure renderer
   * @param labelCollection
   *          the label collection
   * @param body
   *          the section body to use
   */
  public static final void renderFigure(
      final IFigureRenderer figureRenderer,
      final Collection<ILabel> labelCollection, final ISectionBody body) {
    Renderers.renderFigure(figureRenderer, labelCollection, body, null);
  }

  /**
   * Render a figure
   *
   * @param figureRenderer
   *          the figure renderer
   * @param labelCollection
   *          the label collection
   * @param body
   *          the section body to use
   * @param series
   *          the figure series to use, or {@code null} to render into
   *          {@code body}
   */
  public static final void renderFigure(
      final IFigureRenderer figureRenderer,
      final Collection<ILabel> labelCollection, final ISectionBody body,
      final IFigureSeries series) {
    final boolean isPartOfSeries;
    final ILabel label;

    isPartOfSeries = (series != null);
    if (figureRenderer instanceof FigureRenderer) {
      ((FigureRenderer) figureRenderer).m_isPartOfSeries = isPartOfSeries;
    }

    label = figureRenderer.createFigureLabel(body);
    if ((label != null) && (labelCollection != null)) {
      labelCollection.add(label);
    }

    if (isPartOfSeries) {
      try (@SuppressWarnings("null")
      final IFigure figure = series.figure(label,
          figureRenderer.getFigurePath())) {
        figureRenderer.renderFigure(figure);
      }
    } else {
      try (final IFigure figure = body.figure(label,
          figureRenderer.getFigureSize(),
          figureRenderer.getFigurePath())) {
        figureRenderer.renderFigure(figure);
      }
    }
  }

  /**
   * Render a series of figures
   *
   * @param figureRenderers
   *          the figure renderers
   * @param figureSeriesRenderer
   *          the figure series renderer
   * @param labelCollection
   *          the label collection
   * @param body
   *          the section body to use
   */
  public static final void renderFigures(
      final Iterator<? extends IFigureRenderer> figureRenderers,
      final IFigureSeriesRenderer figureSeriesRenderer,
      final Collection<ILabel> labelCollection, final ISectionBody body) {
    IFigureRenderer cached, current;
    IFigureSeries series;
    ILabel label;

    if (figureRenderers != null) {
      series = null;
      cached = null;
      while (figureRenderers.hasNext()) {
        current = figureRenderers.next();
        if (current == null) {
          continue;
        }
        if (series != null) {
          Renderers.renderFigure(current, labelCollection, body, series);
          continue;
        }
        if (cached == null) {
          cached = current;
          continue;
        }
        label = figureSeriesRenderer.createFigureSeriesLabel(body);
        if ((label != null) && (labelCollection != null)) {
          labelCollection.add(label);
        }
        series = body.figureSeries(label,
            figureSeriesRenderer.getFigureSize(),
            figureSeriesRenderer.getFigureSeriesPath());
        Renderers.renderFigure(cached, labelCollection, body, series);
        Renderers.renderFigure(current, labelCollection, body, series);
        cached = null;
      }
      if (cached != null) {
        Renderers.renderFigure(cached, labelCollection, body, series);
      }
      if (series != null) {
        series.close();
        series = null;
      }
    }

  }
}
