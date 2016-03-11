package shared.junit.shared.randomization;

import shared.randomization.LongRandomization;

/** the long randomization test */
public class LongRandomizationTest
    extends PrimitiveTypeRandomizationTest<Long, LongRandomization> {

  /** create */
  public LongRandomizationTest() {
    super(LongRandomization.INSTANCE);
  }
}
