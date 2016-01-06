package shared.junit.org.optimizationBenchmarking.utils.math.functions;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.collections.iterators.IterableIterator;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;

import shared.junit.TestBase;

/**
 * A test for mathematical functions.
 */
@Ignore
public abstract class MathematicalFunctionTest extends TestBase {

  /** create the test */
  public MathematicalFunctionTest() {
    super();
  }

  /**
   * Get the mathematical function to test
   *
   * @return the mathematical function to test
   */
  public abstract MathematicalFunction getFunction();

  /**
   * Get the test cases to be used to test the function
   *
   * @return the test cases
   */
  public FunctionTestCase[] getTestCases() {
    return FunctionTestCase.EMPTY;
  }

  /**
   * Is this operator commutative? Example: {@code a*b = b*a}, i.e.,
   * multiplication is commutative. Division is not, as {@code a/b != b/a}.
   *
   * @return {@code true} if it is, {@code false} if not
   */
  protected boolean isCommutative() {
    return false;
  }

  /**
   * iterate the permutations.
   *
   * @param length
   *          the length
   * @return the iteraboe
   */
  protected final Iterable<int[]> iteratePermutations(final int length) {
    return new __PermutationIterator(length);
  }

  /**
   * Test the {@code double}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllDouble() {
    MathematicalFunction function;
    double[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.ALL_AS_DOUBLE) != 0) {
        params = new double[test.m_in.length];
        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].doubleValue();
          }

          Assert.assertEquals(test.m_out.doubleValue(),
              function.computeAsDouble(params), 1e-15d);
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code float}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllFloat() {
    MathematicalFunction function;
    float[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.ALL_AS_FLOAT) != 0) {
        params = new float[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].floatValue();
          }

          Assert.assertEquals(test.m_out.floatValue(),
              function.computeAsFloat(params), 1e-10f);
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code long}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllLong() {
    MathematicalFunction function;
    long[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.ALL_AS_LONG) != 0) {
        params = new long[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].longValue();
          }
          Assert.assertEquals(test.m_out.longValue(),
              function.computeAsLong(params));
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code int}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllInt() {
    MathematicalFunction function;
    int[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.ALL_AS_INT) != 0) {
        params = new int[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].intValue();
          }

          Assert.assertEquals(test.m_out.intValue(),
              function.computeAsInt(params));
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code short}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllShort() {
    MathematicalFunction function;
    short[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.ALL_AS_SHORT) != 0) {
        params = new short[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].shortValue();
          }

          Assert.assertEquals(test.m_out.shortValue(),
              function.computeAsShort(params));
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code byte}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllByte() {
    MathematicalFunction function;
    byte[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.ALL_AS_BYTE) != 0) {
        params = new byte[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].byteValue();
          }

          Assert.assertEquals(test.m_out.byteValue(),
              function.computeAsByte(params));
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code long}-based computation based on the test cases with
   * {@code double}-based result
   */
  @Test(timeout = 3600000)
  public void testTestCasesInLongOutDouble() {
    MathematicalFunction function;
    long[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.IN_LONG_OUT_DOUBLE) != 0) {
        params = new long[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].longValue();
          }

          Assert.assertEquals(test.m_out.doubleValue(),
              function.computeAsDouble(params), 1e-15d);
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * Test the {@code int}-based computation based on the test cases with
   * {@code double}-based result
   */
  @Test(timeout = 3600000)
  public void testTestCasesInIntOutDouble() {
    MathematicalFunction function;
    int[] params;
    int length;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final FunctionTestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & FunctionTestCase.IN_INT_OUT_DOUBLE) != 0) {
        params = new int[test.m_in.length];

        for (final int[] perm : this
            .iteratePermutations(test.m_in.length)) {
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length]] = test.m_in[length].intValue();
          }

          Assert.assertEquals(test.m_out.doubleValue(),
              function.computeAsDouble(params), 1e-15d);
          if (!(this.isCommutative())) {
            break;
          }
        }
      }
    }
  }

  /**
   * This iterator enumerates all permutations of a given length. It starts
   * with a canonical permutation and enumerates all possible permutations
   * one by one. This implementation here uses Even's Speedup of the
   * Steinhaus-Johnson-Trotter Algorithm. Even's Speedup allows us to get
   * the next permutation from the current permutation by performing a
   * single, adjacent swap (i.e., exchanging two neighboring elements).
   */
  private static final class __PermutationIterator
      extends IterableIterator<int[]> {

    /** the m_work */
    private final int[] m_work;

    /** the direction */
    private final boolean[] m_dir;

    /** the state */
    private int m_state;

    /**
     * create the iterator
     *
     * @param n
     *          the length of the permutations
     */
    __PermutationIterator(final int n) {
      super();
      if (n > 0) {
        this.m_work = new int[n];
        this.m_dir = new boolean[n];
      } else {
        this.m_work = EmptyUtils.EMPTY_INTS;
        this.m_dir = EmptyUtils.EMPTY_BOOLEANS;
      }
      this._reset();
    }

    /** reset the state of the iterator */
    final void _reset() {
      int i;

      this.m_state = 0;
      for (i = this.m_work.length; (--i) >= 0;) {
        this.m_work[i] = i;
      }
      Arrays.fill(this.m_dir, false);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean hasNext() {
      final int movePos, n, nMinus1;
      final int[] work;
      final boolean[] dir;
      int largestMobile, largestMobileIndex, before, after, current, index,
          indexBefore;
      boolean largestMobileDir, currentDir;

      switch (this.m_state) {

        case 0: {
          return true;
        }

        case 1: {

          work = this.m_work;
          dir = this.m_dir;

          largestMobileDir = false;
          largestMobile = largestMobileIndex = (-1);
          n = work.length;
          nMinus1 = (n - 1);

          index = n;
          indexBefore = (index - 1);
          before = work[indexBefore];
          current = (-1);

          looper: while (index > 0) {
            index = indexBefore;
            after = current;
            current = before;
            if (index > 0) {
              before = work[--indexBefore];
            }

            if (largestMobile < current) {

              currentDir = dir[index];
              isNotMobile: {

                if (index <= 0) {
                  if (!currentDir) {
                    continue looper;
                  }
                  if (current > after) {
                    break isNotMobile;
                  }
                  continue looper;
                }

                if (index >= nMinus1) {
                  if (currentDir) {
                    continue looper;
                  }
                  if (current > before) {
                    break isNotMobile;
                  }
                  continue looper;
                }

                if (currentDir) {
                  if (current > after) {
                    break isNotMobile;
                  }
                } else {
                  if (current > before) {
                    break isNotMobile;
                  }
                }
                continue looper;
              }

              largestMobile = current;
              largestMobileIndex = index;
              largestMobileDir = currentDir;
              if (current >= n) {
                break looper;
              }
            }
          }

          if (largestMobileIndex < 0) {// we are at the end
            this.m_state = 2;
            return false;
          }

          movePos = (largestMobileIndex + (largestMobileDir ? 1 : (-1)));

          work[largestMobileIndex] = work[movePos];
          work[movePos] = largestMobile;

          dir[largestMobileIndex] = dir[movePos];
          dir[movePos] = largestMobileDir;

          // reverse direction
          index = 0;
          for (final int workAtIndex : this.m_work) {
            if (workAtIndex > largestMobile) {
              dir[index] ^= true;// toggle
            }
            index++;
          }

          this.m_state = 0;
          return true;
        }
        default: {
          return false;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final int[] next() {
      if (this.hasNext()) {
        this.m_state = 1;
        return this.m_work;
      }
      super.next();
      return null;
    }
  }
}
