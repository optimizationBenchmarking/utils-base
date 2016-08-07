package org.optimizationBenchmarking.utils.document.impl;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A sequenceable wrapping a semantic component.
 */
public class SemanticComponentSequenceable implements ISequenceable {

  /** the internal semantic component */
  private final ISemanticComponent m_component;

  /** should we print the short name? */
  private final boolean m_printShortName;
  /** should we print the long name? */
  private final boolean m_printLongName;
  /** should we print the description? */
  private final boolean m_printDescription;

  /**
   * Create the semantic component to sequenceable wrapper
   *
   * @param component
   *          the component
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   * @param printDescription
   *          should we print the description?
   */
  public SemanticComponentSequenceable(final ISemanticComponent component,
      final boolean printShortName, final boolean printLongName,
      final boolean printDescription) {
    super();
    if (component == null) {
      throw new IllegalArgumentException(//
          "Semantic component to be wrapped cannot be null."); //$NON-NLS-1$
    }
    SemanticComponentSequenceable.__checkPrint(printShortName,
        printLongName, printDescription);
    this.m_component = component;
    this.m_printShortName = printShortName;
    this.m_printLongName = printLongName;
    this.m_printDescription = printDescription;
  }

  /**
   * check the print parameters
   *
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   * @param printDescription
   *          should we print the description?
   */
  private static final void __checkPrint(final boolean printShortName,
      final boolean printLongName, final boolean printDescription) {
    if (!(printShortName || printLongName || printDescription)) {
      throw new IllegalArgumentException(//
          "When printing a semantic component, printShortName, printLongName, and printDescription cannot all be false, i.e., we need to print at least something.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    ETextCase next;
    if (this.m_printShortName) {
      if (this.m_printLongName) {
        next = SemanticComponentUtils.printLongAndShortNameIfDifferent(
            this.m_component, textOut, textCase);
      } else {
        next = this.m_component.printShortName(textOut, textCase);
      }
    } else {
      if (this.m_printLongName) {
        next = this.m_component.printLongName(textOut, textCase);
      } else {
        next = textCase;
      }
    }

    if (this.m_printDescription) {
      if (this.m_printShortName || this.m_printLongName) {
        textOut.append(':');
        textOut.append(' ');
      }
      return this.m_component.printDescription(textOut, next);
    }
    return next;
  }

  /**
   * Wrap a collection of elements to semantic component sequenceables.
   *
   * @param components
   *          the components
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   * @param printDescription
   *          should we print the description?
   * @return the wrapped collection
   */
  public static final ArrayListView<ISequenceable> wrap(
      final Collection<? extends ISemanticComponent> components,
      final boolean printShortName, final boolean printLongName,
      final boolean printDescription) {
    final ISequenceable[] wrapped;
    int index;

    SemanticComponentSequenceable.__checkPrint(printShortName,
        printLongName, printDescription);
    wrapped = new ISequenceable[components.size()];
    index = 0;
    for (final ISemanticComponent isc : components) {
      wrapped[index++] = new SemanticComponentSequenceable(isc,
          printShortName, printLongName, printDescription);
    }
    return new ArrayListView<>(wrapped, false);
  }
}
