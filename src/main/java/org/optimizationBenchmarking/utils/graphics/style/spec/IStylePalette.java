package org.optimizationBenchmarking.utils.graphics.style.spec;

import java.util.List;
import java.util.Set;

/**
 * A style palette marks a set of available instances of a given style
 * type. Style palettes are typically immutable.
 *
 * @param <T>
 *          the style type
 */
public interface IStylePalette<T extends IStyle> extends List<T>, Set<T> {
  //
}
