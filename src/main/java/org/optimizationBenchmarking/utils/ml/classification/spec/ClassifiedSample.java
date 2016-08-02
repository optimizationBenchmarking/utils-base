package org.optimizationBenchmarking.utils.ml.classification.spec;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.comparison.Compare;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A class for samples whose class is known. */
public final class ClassifiedSample extends Textable
    implements Comparable<ClassifiedSample> {

  /** the feature values */
  public final double[] featureValues;

  /** the class to which the sample belongs */
  public final int sampleClass;

  /** the hash code */
  private int m_hashCode;

  /**
   * Create a classified sample.
   *
   * @param _sampleClass
   *          the sample class, the first class has index {@code 0}
   * @param _featureValues
   *          the feature values
   */
  public ClassifiedSample(final int _sampleClass,
      final double... _featureValues) {
    super();

    if ((_sampleClass < 0) || (_sampleClass > 1000)) {
      throw new IllegalArgumentException((//
          "Class must be in 0...1000, but specified is " //$NON-NLS-1$
              + _sampleClass)
          + '.');
    }
    if (_featureValues == null) {
      throw new IllegalArgumentException(//
          "Feature vector must not be null."); //$NON-NLS-1$
    }
    if (_featureValues.length <= 0) {
      throw new IllegalArgumentException(//
          "Feature vector must not be empty."); //$NON-NLS-1$
    }
    this.sampleClass = _sampleClass;
    this.featureValues = _featureValues;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    int hashCode;

    hashCode = this.m_hashCode;
    if (hashCode != 0) {
      return hashCode;
    }

    hashCode = HashUtils.hashCode(this.sampleClass);
    for (final double value : this.featureValues) {
      hashCode = HashUtils.combineHashes(hashCode,
          HashUtils.hashCode(value));
    }

    return (this.m_hashCode = ((hashCode == 0) ? 234098239 : hashCode));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    final ClassifiedSample sample;
    if (other == this) {
      return true;
    }

    if (other instanceof ClassifiedSample) {
      sample = ((ClassifiedSample) other);
      if (sample.sampleClass == this.sampleClass) {
        return Arrays.equals(this.featureValues, sample.featureValues);
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final ClassifiedSample o) {
    int index, result;

    if (this.sampleClass == o.sampleClass) {
      index = (-1);
      for (final double value : o.featureValues) {
        result = Compare.compare(this.featureValues[++index], value);
        if (result != 0) {
          return result;
        }
      }
    }

    return ((this.sampleClass < o.sampleClass) ? (-1) : 1);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    char next;

    textOut.append(this.sampleClass);
    next = '[';
    for (final double value : this.featureValues) {
      textOut.append(next);
      textOut.append(value);
      next = ',';
    }
    textOut.append(']');
  }
}
