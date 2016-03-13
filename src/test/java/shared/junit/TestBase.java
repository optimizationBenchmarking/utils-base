package shared.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Ignore;

/** the base class for tests */
@Ignore
public class TestBase {

  /** the constructor */
  protected TestBase() {
    super();
  }

  /**
   * Serialize {@code o}, then deserialize it
   *
   * @param o
   *          the object
   * @return the de-serialized serialized version
   */
  public static final Object serializeDeserialize(final Object o) {
    byte[] bdata;
    Object o2;

    o2 = null;
    try {
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
          oos.writeObject(o);
          oos.flush();
        }
        bos.flush();
        bdata = bos.toByteArray();
      }

      try (ByteArrayInputStream iis = new ByteArrayInputStream(bdata)) {
        try (ObjectInputStream ois = new ObjectInputStream(iis)) {
          o2 = ois.readObject();
        }
      }
    } catch (final Throwable tt) {
      Assert.fail(tt.toString());
    }

    if (o == null) {
      Assert.assertNull(o2);
    } else {
      Assert.assertNotNull(o2);
    }

    return o2;
  }

  /**
   * Get a logger which discards all its log output
   *
   * @return the logger
   */
  public static final Logger getNullLogger() {
    final Logger logger;
    logger = Logger.getAnonymousLogger();
    TestBase.disableLogger(logger);
    return logger;
  }

  /**
   * Ensure that a logger logs nothing.
   *
   * @param logger
   *          the logger
   */
  public static final void disableLogger(final Logger logger) {
    final Handler[] handlers;

    logger.setLevel(Level.OFF);
    logger.setFilter(new Filter() {
      /** {@inheritDoc} */
      @Override
      public final boolean isLoggable(final LogRecord record) {
        return false;
      }
    });

    logger.setUseParentHandlers(false);
    handlers = logger.getHandlers();
    if (handlers != null) {
      for (final Handler handler : handlers) {
        logger.removeHandler(handler);
      }
    }
  }

  /**
   * Compute a number which is larger than the given input.
   *
   * @param number
   *          the double
   * @param allowsInfinity
   *          is infinity allowed?
   * @return more, or {@code null} if increasing is not possible
   */
  public static final Number bigger(final Number number,
      final boolean allowsInfinity) {
    final double doubleA;
    final float floatA, floatB;
    final long longA, longB;
    final int intA, intB;
    final short shortA, shortB;
    final byte byteA, byteB;
    double doubleB, doubleC;

    if (number instanceof Long) {
      longA = number.longValue();
      longB = (longA + 1L);
      if (longB > longA) {
        return Long.valueOf(longB);
      }
      return null;
    }

    if (number instanceof Integer) {
      intA = number.intValue();
      intB = (intA + 1);
      if (intB > intA) {
        return Integer.valueOf(intB);
      }
      return null;
    }

    if (number instanceof Short) {
      shortA = number.shortValue();
      shortB = (short) (shortA + 1);
      if (shortB > shortA) {
        return Short.valueOf(shortB);
      }
      return null;
    }

    if (number instanceof Byte) {
      byteA = number.byteValue();
      byteB = (byte) (byteA + 1);
      if (byteB > byteA) {
        return Byte.valueOf(byteB);
      }
      return null;
    }

    if (number instanceof Float) {
      floatA = number.floatValue();
      floatB = Math.nextUp(floatA);
      if (floatB > floatA) {
        if (allowsInfinity || (floatB < Float.POSITIVE_INFINITY)) {
          return Float.valueOf(floatB);
        }
      }
      return null;
    }

    doubleA = number.doubleValue();
    doubleB = Math.nextUp(doubleA);
    if (doubleB > doubleA) {
      if (allowsInfinity || (doubleB < Double.POSITIVE_INFINITY)) {
        if (!(number instanceof Double)) {
          doubleC = (doubleB + 1d);
          if (doubleC > doubleB) {
            if (allowsInfinity || (doubleB < Double.POSITIVE_INFINITY)) {
              doubleB = doubleC;
            }
          }

          if (doubleB < (Float.MAX_VALUE)) {
            floatA = ((float) doubleC);
            floatB = Math.nextUp(floatA);
            if (floatB > floatA) {
              if (allowsInfinity || (floatB < Float.POSITIVE_INFINITY)) {
                doubleC = floatB;
                if (doubleC > doubleB) {
                  if (allowsInfinity
                      || (doubleB < Double.POSITIVE_INFINITY)) {
                    doubleB = doubleC;
                  }
                }
              }
            }
          }

          if ((doubleB > Long.MIN_VALUE) && (doubleB < Long.MAX_VALUE)) {
            longA = ((long) doubleB);
            if (longA == doubleB) {
              longB = (longA + 1L);
              if (longB > longA) {
                doubleC = longB;
                if (doubleC > doubleB) {
                  if (allowsInfinity
                      || (doubleB < Double.POSITIVE_INFINITY)) {
                    doubleB = doubleC;
                  }
                }
              }
            }
          }
        }

        return Double.valueOf(doubleB);
      }
    }
    return null;
  }

  /**
   * Compute a number which is smaller than the given input.
   *
   * @param number
   *          the double
   * @param allowsInfinity
   *          is infinity allowed?
   * @return more, or {@code null} if decreasing is not possible
   */
  public static final Number smaller(final Number number,
      final boolean allowsInfinity) {
    final double doubleA;
    final float floatA, floatB;
    final long longA, longB;
    final int intA, intB;
    final short shortA, shortB;
    final byte byteA, byteB;
    double doubleB, doubleC;

    if (number instanceof Long) {
      longA = number.longValue();
      longB = (longA - 1L);
      if (longB < longA) {
        return Long.valueOf(longB);
      }
      return null;
    }

    if (number instanceof Integer) {
      intA = number.intValue();
      intB = (intA - 1);
      if (intB < intA) {
        return Integer.valueOf(intB);
      }
      return null;
    }

    if (number instanceof Short) {
      shortA = number.shortValue();
      shortB = (short) (shortA - 1);
      if (shortB < shortA) {
        return Short.valueOf(shortB);
      }
      return null;
    }

    if (number instanceof Byte) {
      byteA = number.byteValue();
      byteB = (byte) (byteA - 1);
      if (byteB < byteA) {
        return Byte.valueOf(byteB);
      }
      return null;
    }

    if (number instanceof Float) {
      floatA = number.floatValue();
      floatB = Math.nextAfter(floatA, Double.NEGATIVE_INFINITY);
      if (floatB < floatA) {
        if (allowsInfinity || (floatB > Float.NEGATIVE_INFINITY)) {
          return Float.valueOf(floatB);
        }
      }
      return null;
    }

    doubleA = number.doubleValue();
    doubleB = Math.nextAfter(doubleA, Double.NEGATIVE_INFINITY);

    if (doubleB < doubleA) {
      if (allowsInfinity || (doubleB > Double.NEGATIVE_INFINITY)) {

        if (!(number instanceof Double)) {
          doubleC = (doubleB - 1d);
          if (doubleC < doubleB) {
            if (allowsInfinity || (doubleB > Double.NEGATIVE_INFINITY)) {
              doubleB = doubleC;
            }
          }

          if (doubleB > (-Float.MAX_VALUE)) {
            floatA = ((float) doubleC);
            floatB = Math.nextAfter(floatA, Double.NEGATIVE_INFINITY);
            if (floatB < floatA) {
              if (allowsInfinity || (floatB > Float.NEGATIVE_INFINITY)) {
                doubleC = floatB;
                if (doubleC < doubleB) {
                  if (allowsInfinity
                      || (doubleB > Double.NEGATIVE_INFINITY)) {
                    doubleB = doubleC;
                  }
                }
              }
            }
          }

          if ((doubleB > Long.MIN_VALUE) && (doubleB < Long.MAX_VALUE)) {
            longA = ((long) doubleB);
            if (longA == doubleB) {
              longB = (longA - 1L);
              if (longB < longA) {
                doubleC = longB;
                if (doubleC < doubleB) {
                  if (allowsInfinity
                      || (doubleB > Double.NEGATIVE_INFINITY)) {
                    doubleB = doubleC;
                  }
                }
              }
            }
          }
        }

        return Double.valueOf(doubleB);
      }
    }
    return null;
  }

}
