package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * This utility class allows us for checking the compatibility of numerical
 * types. Its methods surely leave much room for improvement, especially in
 * terms of speed, but for now they will do.
 */
public final class NumericalTypes {

  /**
   * the number can be represented as {@code byte} without loss of
   * precision
   */
  public static final int IS_BYTE = 1;
  /**
   * the number can be represented as {@code short} without loss of
   * precision
   */
  public static final int IS_SHORT = (NumericalTypes.IS_BYTE << 1);
  /**
   * the number can be represented as {@code int} without loss of precision
   */
  public static final int IS_INT = (NumericalTypes.IS_SHORT << 1);
  /**
   * the number can be represented as {@code long} without loss of
   * precision
   */
  public static final int IS_LONG = (NumericalTypes.IS_INT << 1);
  /**
   * the number can be represented as {@code float} without loss of
   * precision
   */
  public static final int IS_FLOAT = (NumericalTypes.IS_LONG << 1);
  /**
   * the number can be represented as {@code double} without loss of
   * precision
   */
  public static final int IS_DOUBLE = (NumericalTypes.IS_FLOAT << 1);

  /**
   * This is a combination of {@link #IS_BYTE}, {@link #IS_SHORT},
   * {@link #IS_INT} and {@link #IS_LONG}, i.e., a bit mask that can be
   * used for testing for integer types.
   */
  public static final int ANY_INTEGER = (NumericalTypes.IS_BYTE
      | NumericalTypes.IS_SHORT | NumericalTypes.IS_INT
      | NumericalTypes.IS_LONG);

  /**
   * This is a combination of {@link #IS_FLOAT} and {@link #IS_DOUBLE},
   * i.e., a bit mask that can be used for testing for floating point
   * numbers.
   */
  public static final int ANY_FLOAT = (NumericalTypes.IS_FLOAT
      | NumericalTypes.IS_DOUBLE);

  /**
   * the smallest {@code int}eger value which fits into a {@code float}
   * without loss of precision: {@value}
   */
  public static final int MIN_FLOAT_INT = -16777216;
  /**
   * the largest {@code int}eger value which fits into a {@code float}
   * without loss of precision: {@value}
   */
  public static final int MAX_FLOAT_INT = 16777216;

  /**
   * the smallest {@code long} value which fits into a {@code double}
   * without loss of precision: {@value}
   */
  public static final long MIN_DOUBLE_LONG = -9007199254740992L;
  /**
   * the largest {@code long} value which fits into a {@code double}
   * without loss of precision: {@value}
   */
  public static final long MAX_DOUBLE_LONG = 9007199254740992L;

  /** the {@link java.lang.Long#MAX_VALUE} as {@code double}: {@value} */
  public static final double MAX_LONG_DOUBLE = Long.MAX_VALUE;
  /** the {@link java.lang.Long#MIN_VALUE} as {@code double}: {@value} */
  public static final double MIN_LONG_DOUBLE = Long.MIN_VALUE;

  /** the {@link java.lang.Long#MAX_VALUE} as {@code float}: {@value} */
  public static final float MAX_LONG_FLOAT = Long.MAX_VALUE;
  /** the {@link java.lang.Long#MIN_VALUE} as {@code float}: {@value} */
  public static final float MIN_LONG_FLOAT = Long.MIN_VALUE;

  /**
   * Get the possible types of a given object
   *
   * @param object
   *          the object
   * @return a bit set (binary or) of the different type constants which
   *         apply to the object, or {@code 0} if the object is not a
   *         number
   */
  public static final int getTypes(final Object object) {
    if (object instanceof Number) {
      return NumericalTypes.getTypes((Number) object);
    }
    return 0;
  }

  /**
   * Get the smallest numerical type of a given object
   *
   * @param object
   *          the object
   * @return the smallest numerical type which applies to the object, or
   *         {@code 0} if the object is not a number
   */
  public static final int getMinType(final Object object) {
    if (object instanceof Number) {
      return NumericalTypes.getMinType((Number) object);
    }
    return 0;
  }

  /**
   * Get the possible types of a given number
   *
   * @param number
   *          the number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Number number) {
    final BasicNumber base;
    int res;

    if (number instanceof Byte) {
      return NumericalTypes.getTypes((Byte) number);
    }
    if (number instanceof Short) {
      return NumericalTypes.getTypes((Short) number);
    }
    if (number instanceof Integer) {
      return NumericalTypes.getTypes((Integer) number);
    }
    if (number instanceof Long) {
      return NumericalTypes.getTypes((Long) number);
    }
    if (number instanceof Float) {
      return NumericalTypes.getTypes((Float) number);
    }
    if (number instanceof Double) {
      return NumericalTypes.getTypes((Double) number);
    }

    if (number instanceof BasicNumber) {
      res = 0;
      base = ((BasicNumber) number);
      if (base.isInteger()) {
        res |= NumericalTypes.getTypes(base.longValue());
      }
      if (base.isReal()) {
        res |= NumericalTypes.getTypes(base.doubleValue());
      }
    } else {
      res = (NumericalTypes.getTypes(number.longValue()) & //
          NumericalTypes.getTypes(number.doubleValue()));
    }
    return ((res != 0) ? res : NumericalTypes.IS_DOUBLE);
  }

  /**
   * Get the smallest numerical type of a given number
   *
   * @param number
   *          the number
   * @return the smallest type which can store the number without loss of
   *         fidelity
   */
  public static final int getMinType(final Number number) {
    final BasicNumber base;

    if (number instanceof Byte) {
      return NumericalTypes.getMinType((Byte) number);
    }
    if (number instanceof Short) {
      return NumericalTypes.getMinType((Short) number);
    }
    if (number instanceof Integer) {
      return NumericalTypes.getMinType((Integer) number);
    }
    if (number instanceof Long) {
      return NumericalTypes.getMinType((Long) number);
    }
    if (number instanceof Float) {
      return NumericalTypes.getMinType((Float) number);
    }
    if (number instanceof Double) {
      return NumericalTypes.getMinType((Double) number);
    }

    if (number instanceof BasicNumber) {
      base = ((BasicNumber) number);
      if (base.isInteger()) {
        return NumericalTypes.getMinType(base.longValue());
      }
      return NumericalTypes.getMinType(base.doubleValue());
    }
    return Math.max(NumericalTypes.getMinType(number.longValue()), //
        NumericalTypes.getMinType(number.doubleValue()));
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Byte
   * byte} number can be stored
   *
   * @param number
   *          the {@link java.lang.Byte byte} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Byte byte} number can be stored
   */
  public static final int getMinType(final byte number) {
    return NumericalTypes.IS_BYTE;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Byte
   * byte} number can be stored
   *
   * @param number
   *          the {@link java.lang.Byte byte} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Byte byte} number can be stored
   */
  public static final int getMinType(final Byte number) {
    return NumericalTypes.IS_BYTE;
  }

  /**
   * Get the possible types of a given {@link java.lang.Byte byte} number
   *
   * @param number
   *          the {@link java.lang.Byte byte} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Byte number) {
    return (NumericalTypes.ANY_INTEGER | NumericalTypes.ANY_FLOAT);
  }

  /**
   * Get the possible types of a given {@code byte} number
   *
   * @param number
   *          the {@code byte} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final byte number) {
    return (NumericalTypes.ANY_INTEGER | NumericalTypes.ANY_FLOAT);
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Short
   * short} number can be stored
   *
   * @param number
   *          the {@link java.lang.Short short} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Short short} number can be stored
   */
  public static final int getMinType(final short number) {
    if ((number < Byte.MIN_VALUE) || (number > Byte.MAX_VALUE)) {
      return NumericalTypes.IS_SHORT;
    }
    return NumericalTypes.IS_BYTE;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Short
   * short} number can be stored
   *
   * @param number
   *          the {@link java.lang.Short short} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Short short} number can be stored
   */
  public static final int getMinType(final Short number) {
    return NumericalTypes.getMinType(number.shortValue());
  }

  /**
   * Get the possible types of a given {@link java.lang.Short short} number
   *
   * @param number
   *          the {@link java.lang.Short short} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Short number) {
    return NumericalTypes.getTypes(number.shortValue());
  }

  /**
   * Get the possible types of a given {@code short} number
   *
   * @param number
   *          the {@code short} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final short number) {
    if ((number < Byte.MIN_VALUE) || (number > Byte.MAX_VALUE)) {
      return (NumericalTypes.IS_SHORT | NumericalTypes.IS_INT
          | NumericalTypes.IS_LONG | NumericalTypes.ANY_FLOAT);
    }
    return (NumericalTypes.ANY_INTEGER | NumericalTypes.ANY_FLOAT);
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Integer
   * int} number can be stored
   *
   * @param number
   *          the {@link java.lang.Integer int} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Integer int} number can be stored
   */
  public static final int getMinType(final int number) {
    if ((number >= Short.MIN_VALUE) && (number <= Short.MAX_VALUE)) {
      if ((number >= Byte.MIN_VALUE) && (number <= Byte.MAX_VALUE)) {
        return NumericalTypes.IS_BYTE;
      }
      return NumericalTypes.IS_SHORT;
    }
    return NumericalTypes.IS_INT;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Integer
   * int} number can be stored
   *
   * @param number
   *          the {@link java.lang.Integer int} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Integer int} number can be stored
   */
  public static final int getMinType(final Integer number) {
    return NumericalTypes.getMinType(number.intValue());
  }

  /**
   * Get the possible types of a given {@link java.lang.Integer int} number
   *
   * @param number
   *          the {@link java.lang.Integer int} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Integer number) {
    return NumericalTypes.getTypes(number.intValue());
  }

  /**
   * Get the possible types of a given {@code int} number
   *
   * @param number
   *          the {@code int} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final int number) {
    final float f;
    int res;

    res = (NumericalTypes.IS_INT | NumericalTypes.IS_LONG
        | NumericalTypes.IS_DOUBLE);
    if ((number >= NumericalTypes.MIN_FLOAT_INT)
        && (number <= NumericalTypes.MAX_FLOAT_INT)) {
      res |= NumericalTypes.IS_FLOAT;
      if ((number >= Short.MIN_VALUE) && (number <= Short.MAX_VALUE)) {
        res |= NumericalTypes.IS_SHORT | NumericalTypes.IS_FLOAT;
        if ((number >= Byte.MIN_VALUE) && (number <= Byte.MAX_VALUE)) {
          res |= NumericalTypes.IS_BYTE;
        }
      }
    } else {
      f = number;
      if (((long) f) == number) {
        res |= NumericalTypes.IS_FLOAT;
      }
    }

    return res;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Long
   * long} number can be stored
   *
   * @param number
   *          the {@link java.lang.Long long} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Long long} number can be stored
   */
  public static final int getMinType(final long number) {
    if ((number >= Integer.MIN_VALUE) && (number <= Integer.MAX_VALUE)) {
      if ((number >= Short.MIN_VALUE) && (number <= Short.MAX_VALUE)) {
        if ((number >= Byte.MIN_VALUE) && (number <= Byte.MAX_VALUE)) {
          return NumericalTypes.IS_BYTE;
        }
        return NumericalTypes.IS_SHORT;
      }
      return NumericalTypes.IS_INT;
    }
    return NumericalTypes.IS_LONG;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Long
   * long} number can be stored
   *
   * @param number
   *          the {@link java.lang.Long long} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Long long} number can be stored
   */
  public static final int getMinType(final Long number) {
    return NumericalTypes.getMinType(number.longValue());
  }

  /**
   * Get the possible types of a given {@link java.lang.Long long} number
   *
   * @param number
   *          the {@link java.lang.Long long} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Long number) {
    return NumericalTypes.getTypes(number.longValue());
  }

  /**
   * Get the possible types of a given {@code long} number
   *
   * @param number
   *          the {@code long} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final long number) {
    final double d;
    final float f;
    int res;

    checkDoubleAndFloat: {
      res = NumericalTypes.IS_LONG;
      if ((number >= NumericalTypes.MIN_DOUBLE_LONG)
          && (number <= NumericalTypes.MAX_DOUBLE_LONG)) {
        res |= NumericalTypes.IS_DOUBLE;
        if ((number >= Integer.MIN_VALUE)
            && (number <= Integer.MAX_VALUE)) {
          res |= NumericalTypes.IS_INT;
          if ((number >= NumericalTypes.MIN_FLOAT_INT)
              && (number <= NumericalTypes.MAX_FLOAT_INT)) {
            res |= NumericalTypes.IS_FLOAT;
            if ((number >= Short.MIN_VALUE)
                && (number <= Short.MAX_VALUE)) {
              res |= NumericalTypes.IS_SHORT | NumericalTypes.IS_FLOAT;
              if ((number >= Byte.MIN_VALUE)
                  && (number <= Byte.MAX_VALUE)) {
                res |= NumericalTypes.IS_BYTE;
              }
            }

            break checkDoubleAndFloat;
          }
        }
      } else {
        d = number;
        if (((long) d) == number) {
          res |= NumericalTypes.IS_DOUBLE;
        }
      }

      f = number;
      if (((long) f) == number) {
        res |= NumericalTypes.IS_FLOAT;
      }
    }

    return res;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Float
   * float} number can be stored
   *
   * @param number
   *          the {@link java.lang.Float float} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Float float} number can be stored
   */
  public static final int getMinType(final float number) {
    final long l;

    if ((number >= NumericalTypes.MIN_LONG_FLOAT)
        && (number <= NumericalTypes.MAX_LONG_FLOAT)) {
      l = ((long) number);
      if (l == number) {
        if ((l >= Integer.MIN_VALUE) && (l <= Integer.MAX_VALUE)) {
          if ((l >= Short.MIN_VALUE) && (l <= Short.MAX_VALUE)) {
            if ((l >= Byte.MIN_VALUE) && (l <= Byte.MAX_VALUE)) {
              return NumericalTypes.IS_BYTE;
            }
            return NumericalTypes.IS_SHORT;
          }
          return NumericalTypes.IS_INT;
        }
        return NumericalTypes.IS_LONG;
      }
    }
    return NumericalTypes.IS_FLOAT;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Float
   * float} number can be stored
   *
   * @param number
   *          the {@link java.lang.Float float} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Float float} number can be stored
   */
  public static final int getMinType(final Float number) {
    return NumericalTypes.getMinType(number.floatValue());
  }

  /**
   * Get the possible types of a given {@link java.lang.Float float} number
   *
   * @param number
   *          the {@link java.lang.Float float} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Float number) {
    return NumericalTypes.getTypes(number.floatValue());
  }

  /**
   * Get the possible types of a given {@code float} number
   *
   * @param number
   *          the {@code float} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final float number) {
    final double d;
    final long l;
    int res;

    res = NumericalTypes.IS_FLOAT;

    checkDouble: {
      if ((number >= NumericalTypes.MIN_LONG_FLOAT)
          && (number <= NumericalTypes.MAX_LONG_FLOAT)) {
        l = ((long) number);
        if (l == number) {
          res |= NumericalTypes.IS_LONG;
          if ((l >= NumericalTypes.MIN_DOUBLE_LONG)
              && (l <= NumericalTypes.MAX_DOUBLE_LONG)) {
            res |= NumericalTypes.IS_DOUBLE;
            if ((l >= Integer.MIN_VALUE) && (l <= Integer.MAX_VALUE)) {
              res |= NumericalTypes.IS_INT;
              if ((l >= Short.MIN_VALUE) && (l <= Short.MAX_VALUE)) {
                res |= NumericalTypes.IS_SHORT;
                if ((l >= Byte.MIN_VALUE) && (l <= Byte.MAX_VALUE)) {
                  res |= NumericalTypes.IS_BYTE;
                }
              }
            }
            break checkDouble;
          }
        }
      }

      if (number != number) {
        res |= NumericalTypes.IS_DOUBLE;
      } else {
        d = number;
        if (((float) d) == number) {// should always be true, but let's
          // check
          res |= NumericalTypes.IS_DOUBLE;
        }
      }
    }

    return res;
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Double
   * double} number can be stored
   *
   * @param number
   *          the {@link java.lang.Double double} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Double double} number can be stored
   */
  public static final int getMinType(final double number) {
    final long l;
    final float f;

    if ((number >= NumericalTypes.MIN_LONG_DOUBLE)
        && (number <= NumericalTypes.MAX_LONG_DOUBLE)) {
      l = ((long) number);
      if (l == number) {
        if ((l >= Integer.MIN_VALUE) && (l <= Integer.MAX_VALUE)) {
          if ((l >= Short.MIN_VALUE) && (l <= Short.MAX_VALUE)) {
            if ((l >= Byte.MIN_VALUE) && (l <= Byte.MAX_VALUE)) {
              return NumericalTypes.IS_BYTE;
            }
            return NumericalTypes.IS_SHORT;
          }
          return NumericalTypes.IS_INT;
        }
        return NumericalTypes.IS_LONG;
      }
    }

    if ((number <= Double.NEGATIVE_INFINITY)
        || (number >= Double.POSITIVE_INFINITY) || (number != number)) {
      return NumericalTypes.IS_FLOAT;
    }

    f = ((float) number);
    return ((f == number) ? NumericalTypes.IS_FLOAT
        : NumericalTypes.IS_DOUBLE);
  }

  /**
   * Get the smallest data type in which the given {@link java.lang.Double
   * double} number can be stored
   *
   * @param number
   *          the {@link java.lang.Double double} number
   * @return the smallest data type in which the given
   *         {@link java.lang.Double double} number can be stored
   */
  public static final int getMinType(final Double number) {
    return NumericalTypes.getMinType(number.doubleValue());
  }

  /**
   * Get the possible types of a given {@link java.lang.Double double}
   * number
   *
   * @param number
   *          the {@link java.lang.Double double} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final Double number) {
    return NumericalTypes.getTypes(number.doubleValue());
  }

  /**
   * Check whether a {@code float} number can be converted to a
   * {@code long} without loss of precision or accuracy.
   *
   * @param number
   *          the {@code float} / floating point number
   * @return {@code true} if {@code ((long)number) == number},
   *         {@code false} otherwise
   */
  public static final boolean isLong(final float number) {
    final long l;
    if ((number >= NumericalTypes.MIN_LONG_FLOAT)
        && (number <= NumericalTypes.MAX_LONG_FLOAT)) {
      l = ((long) number);
      return (l == number);
    }
    return false;
  }

  /**
   * Check whether a {@code double} number can be converted to a
   * {@code long} without loss of precision or accuracy.
   *
   * @param number
   *          the {@code double} / floating point number
   * @return {@code true} if {@code ((long)number) == number},
   *         {@code false} otherwise
   */
  public static final boolean isLong(final double number) {
    final long l;
    if ((number >= NumericalTypes.MIN_LONG_DOUBLE)
        && (number <= NumericalTypes.MAX_LONG_DOUBLE)) {
      l = ((long) number);
      return (l == number);
    }
    return false;
  }

  /**
   * Get the possible types of a given {@code short} number
   *
   * @param number
   *          the {@code short} number
   * @return a bit set (binary or) of the different type constants which
   *         apply to the number
   */
  public static final int getTypes(final double number) {
    int res;
    final long l;
    final float f;

    res = NumericalTypes.IS_DOUBLE;

    checkFloat: {
      if ((number >= NumericalTypes.MIN_LONG_DOUBLE)
          && (number <= NumericalTypes.MAX_LONG_DOUBLE)) {
        l = ((long) number);
        if (l == number) {
          res |= NumericalTypes.IS_LONG;
          if ((l >= Integer.MIN_VALUE) && (l <= Integer.MAX_VALUE)) {
            res |= NumericalTypes.IS_INT;
            if ((l >= NumericalTypes.MIN_FLOAT_INT)
                && (l <= NumericalTypes.MAX_FLOAT_INT)) {
              res |= NumericalTypes.IS_FLOAT;
              if ((l >= Short.MIN_VALUE) && (l <= Short.MAX_VALUE)) {
                res |= NumericalTypes.IS_SHORT;
                if ((l >= Byte.MIN_VALUE) && (l <= Byte.MAX_VALUE)) {
                  res |= NumericalTypes.IS_BYTE;
                }
              }
              break checkFloat;
            }
          }
        }
      }

      if (number != number) {
        res |= NumericalTypes.IS_FLOAT;
      } else {
        f = ((float) number);
        if (f == number) {
          res |= NumericalTypes.IS_FLOAT;
        }
      }
    }

    return res;
  }

  /**
   * Find the best floating point representation for the given {@code long}
   * value:
   * <ol>
   * <li>If the {@code long} {@code value} can be converted to a
   * {@code float} without loss of precision, we return {@link #IS_FLOAT}.
   * </li>
   * <li>Otherwise, if the {@code long} {@code value} can be converted to a
   * {@code double} without loss of precision, we return {@link #IS_DOUBLE}
   * .</li>
   * <li>Otherwise, we convert to value both to a {@code double} and to a
   * {@code float} and check which representation leads to the smallest
   * difference to {@code value} when converted back. If the difference is
   * the same, we return {@link #IS_FLOAT}, otherwise the representation
   * with the smaller difference.</li>
   * </ol>
   *
   * @param value
   *          the value
   * @return either {@link #IS_FLOAT} or {@link #IS_DOUBLE}
   */
  public static final int getBestFloatingPointRepresentation(
      final long value) {
    final int types;
    final float f;
    final double d;
    long difF, difD;

    types = NumericalTypes.getTypes(value);
    if ((types & NumericalTypes.IS_FLOAT) != 0) {
      return NumericalTypes.IS_FLOAT;
    }
    if ((types & NumericalTypes.IS_DOUBLE) != 0) {
      return NumericalTypes.IS_DOUBLE;
    }

    f = value;
    d = value;

    difF = (((long) f) - value);
    if (difF <= 0L) {
      if (difF == 0L) {
        return NumericalTypes.IS_FLOAT;
      }
      difF = (-difF);
    }
    difD = (((long) d) - value);
    if (difD <= 0L) {
      if (difD == 0L) {
        return NumericalTypes.IS_DOUBLE;
      }
      difD = (-difD);
    }

    return ((difF <= difD) ? NumericalTypes.IS_FLOAT
        : NumericalTypes.IS_DOUBLE);
  }

  /**
   * Find the best floating point representation for the given {@code int}
   * value:
   * <ol>
   * <li>If the {@code int} {@code value} can be converted to a
   * {@code float} without loss of precision, we return {@link #IS_FLOAT}.
   * </li>
   * <li>Otherwise, we return {@link #IS_DOUBLE}, since any {@code int} can
   * be converted to a {@code double} without loss of precision.</li>
   * </ol>
   *
   * @param value
   *          the value
   * @return either {@link #IS_FLOAT} or {@link #IS_DOUBLE}
   */
  public static final int getBestFloatingPointRepresentation(
      final int value) {
    if ((NumericalTypes.getTypes(value) & NumericalTypes.IS_FLOAT) != 0) {
      return NumericalTypes.IS_FLOAT;
    }
    return NumericalTypes.IS_DOUBLE;
  }

  /**
   * Convert the given {@code byte} value to a number
   *
   * @param val
   *          the value
   * @return the number
   */
  public static final Number valueOf(final byte val) {
    return Byte.valueOf(val);
  }

  /**
   * Convert the given {@code short} value to a number
   *
   * @param val
   *          the value
   * @return the number
   */
  public static final Number valueOf(final short val) {
    switch (NumericalTypes.getMinType(val)) {
      case IS_BYTE: {
        return Byte.valueOf((byte) val);
      }
      default: {
        return Short.valueOf(val);
      }
    }
  }

  /**
   * Convert the given {@code int} value to a number
   *
   * @param val
   *          the value
   * @return the number
   */
  public static final Number valueOf(final int val) {
    switch (NumericalTypes.getMinType(val)) {
      case IS_BYTE: {
        return Byte.valueOf((byte) val);
      }
      case IS_SHORT: {
        return Short.valueOf((short) val);
      }
      default: {
        return Integer.valueOf(val);
      }
    }
  }

  /**
   * Convert the given {@code long} value to a number
   *
   * @param val
   *          the value
   * @return the number
   */
  public static final Number valueOf(final long val) {
    switch (NumericalTypes.getMinType(val)) {
      case IS_BYTE: {
        return Byte.valueOf((byte) val);
      }
      case IS_SHORT: {
        return Short.valueOf((short) val);
      }
      case IS_INT: {
        return Integer.valueOf((int) val);
      }
      default: {
        return Long.valueOf(val);
      }
    }
  }

  /**
   * Convert the given {@code float} value to a number
   *
   * @param val
   *          the value
   * @return the number
   */
  public static final Number valueOf(final float val) {
    switch (NumericalTypes.getMinType(val)) {
      case IS_BYTE: {
        return Byte.valueOf((byte) val);
      }
      case IS_SHORT: {
        return Short.valueOf((short) val);
      }
      case IS_INT: {
        return Integer.valueOf((int) val);
      }
      case IS_LONG: {
        return Long.valueOf((long) val);
      }
      default: {
        return Float.valueOf(val);
      }
    }
  }

  /**
   * Convert the given {@code double} value to a number
   *
   * @param val
   *          the value
   * @return the number
   */
  public static final Number valueOf(final double val) {
    switch (NumericalTypes.getMinType(val)) {
      case IS_BYTE: {
        return Byte.valueOf((byte) val);
      }
      case IS_SHORT: {
        return Short.valueOf((short) val);
      }
      case IS_INT: {
        return Integer.valueOf((int) val);
      }
      case IS_LONG: {
        return Long.valueOf((long) val);
      }
      case IS_FLOAT: {
        return Float.valueOf((float) val);
      }
      default: {
        return Double.valueOf(val);
      }
    }
  }

  /** the forbidden constructor */
  private NumericalTypes() {
    ErrorUtils.doNotCall();
  }

}
