package shared.junit.shared.randomization;

import shared.randomization.IntRandomization;

/** the int randomization test */
public class IntRandomizationTest
    extends PrimitiveTypeRandomizationTest<Integer, IntRandomization> {

  /** create */
  public IntRandomizationTest() {
    super(IntRandomization.INSTANCE);
  }
}
