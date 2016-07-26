package org.optimizationBenchmarking.utils.collections.lists;

import java.util.Arrays;
import java.util.Set;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * A view on an array whose elements are sorted and instances of
 * {@link java.lang.Comparable}. The elements in this view must not be
 * {@code null}. The sorted character of the data allows us to consider the
 * array both as a {@link java.util.List list} and as {@link java.util.Set}
 * .
 *
 * @param
 *          <DT>
 *          the type
 */
public class ArraySetView<DT> extends ArrayListView<DT>
    implements Set<DT> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the empty list view */
  public static final ArraySetView<Object> EMPTY_SET_VIEW = new ArraySetView<>(
      EmptyUtils.EMPTY_OBJECTS, false);

  /**
   * instantiate
   *
   * @param data
   *          the data of the set - will not be copied or cloned, but used
   *          directly
   */
  public ArraySetView(final DT[] data) {
    this(data, true);
  }

  /**
   * instantiate
   *
   * @param data
   *          the data of the set - will not be copied or cloned, but used
   *          directly
   * @param isNullPermitted
   *          are {@code null} values permitted?
   */
  public ArraySetView(final DT[] data, final boolean isNullPermitted) {
    super(data, isNullPermitted);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "unused" })
  public final int indexOf(final Object o) {
    final Comparable<Object>[] data;
    int i;

    if (o == null) {
      return (-1);
    }

    try {
      data = ((Comparable<Object>[]) (this.m_data));
      i = Arrays.binarySearch(data, o);

      if (i < 0) {
        return (-1);
      }

      for (; (--i) >= 0;) {
        if (data[i].compareTo(o) != 0) {
          break;
        }
      }

      return (i + 1);
    } catch (final ClassCastException cce) {
      // in case we are looking for an incompatible object
      return super.indexOf(o);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "unused" })
  public final int lastIndexOf(final Object o) {
    final int size;
    final Comparable<Object>[] data;
    int i;

    if (o == null) {
      return (-1);
    }

    try {
      data = ((Comparable<Object>[]) (this.m_data));
      size = data.length;

      i = Arrays.binarySearch(data, 0, size, o);
      if (i < 0) {
        return (-1);
      }

      for (; (++i) < size;) {
        if (data[i].compareTo(o) != 0) {
          break;
        }
      }

      return (i - 1);
    } catch (final ClassCastException cce) {
      // in case we are looking for an incompatible object
      return super.lastIndexOf(o);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final ArraySetView<DT> select(
      final IPredicate<? super DT> condition) {
    DT[] data;
    DT x;
    int i, s, len;

    data = this.m_data;
    len = s = data.length;

    if (s <= 0) {
      return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
    }

    check: {
      for (i = 0; i < len; i++) {
        if (condition.check(data[i])) {
          continue;
        }
        break check;
      }
      return this;
    }

    s = i;
    for (; (++i) < len;) {
      x = data[i];
      if (condition.check(x)) {
        if (data == this.m_data) {
          data = data.clone();
        }
        data[s++] = x;
      }
    }

    if (s <= 0) {
      return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
    }

    return new ArraySetView<>(Arrays.copyOf(data, s));
  }
}
