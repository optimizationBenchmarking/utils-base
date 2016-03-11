package shared.junit.shared.randomization;

import shared.randomization.DoubleRandomization;

/** the double randomization test */
public class DoubleRandomizationTest
    extends PrimitiveTypeRandomizationTest<Double, DoubleRandomization> {

  /** create */
  public DoubleRandomizationTest() {
    super(DoubleRandomization.INSTANCE);
  }
}
