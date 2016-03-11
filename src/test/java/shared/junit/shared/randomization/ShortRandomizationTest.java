package shared.junit.shared.randomization;

import shared.randomization.ShortRandomization;

/** the short randomization test */
public class ShortRandomizationTest
    extends PrimitiveTypeRandomizationTest<Short, ShortRandomization> {

  /** create */
  public ShortRandomizationTest() {
    super(ShortRandomization.INSTANCE);
  }
}
