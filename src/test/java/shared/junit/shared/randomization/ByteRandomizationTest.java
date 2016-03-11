package shared.junit.shared.randomization;

import shared.randomization.ByteRandomization;

/** the byte randomization test */
public class ByteRandomizationTest
    extends PrimitiveTypeRandomizationTest<Byte, ByteRandomization> {

  /** create */
  public ByteRandomizationTest() {
    super(ByteRandomization.INSTANCE);
  }
}
