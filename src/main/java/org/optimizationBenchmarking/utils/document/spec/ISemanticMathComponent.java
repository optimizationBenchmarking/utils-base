package org.optimizationBenchmarking.utils.document.spec;

/**
 * A combination of the
 * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent}
 * and the
 * {@link org.optimizationBenchmarking.utils.document.spec.IMathRenderable}
 * interfaces. This interface is intended for objects which have both a
 * mathematical function representation as well as are semantic entities.
 */
public interface ISemanticMathComponent
    extends ISemanticComponent, IMathRenderable {
  //
}
