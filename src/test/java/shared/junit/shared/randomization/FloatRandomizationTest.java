package shared.junit.shared.randomization;

import shared.randomization.FloatRandomization;

/** the float randomization test */
public class FloatRandomizationTest
    extends PrimitiveTypeRandomizationTest<Float, FloatRandomization> {

  /** create */
  public FloatRandomizationTest() {
    super(FloatRandomization.INSTANCE);
  }
}
