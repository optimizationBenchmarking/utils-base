package shared.junit.org.optimizationBenchmarking.utils.tools;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.tools.spec.ITool;

import shared.junit.InstanceTest;

/**
 * the basic tool test
 *
 * @param <T>
 *          the tool type
 */
@Ignore
public abstract class ToolTest<T extends ITool> extends InstanceTest<T> {

  /**
   * create
   *
   * @param tool
   *          the tool
   */
  public ToolTest(final T tool) {
    super(null, tool, true, false);
  }

  /** test whether the tool can be used */
  @Test(timeout = 3600000)
  public void testToolCanUse() {
    Assert.assertTrue(this.getInstance().canUse());
  }

  /**
   * test whether the tool can be used: if not, this method should throw an
   * exception
   */
  @Test(timeout = 3600000)
  public void testToolCheckCanUse() {
    final T instance;
    instance = this.getInstance();
    Assert.assertNotNull(instance);
    if (instance.canUse()) {
      instance.checkCanUse();
    }
  }

  /** test whether the tool returns a non-{@code null} tool job builder */
  @Test(timeout = 3600000)
  public void testToolCanCreateToolJobBuilder() {
    final T instance;
    instance = this.getInstance();
    Assert.assertNotNull(instance);
    if (instance.canUse()) {
      Assert.assertNotNull(instance.use());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();

    this.testToolCanUse();
    this.testToolCheckCanUse();
    this.testToolCanCreateToolJobBuilder();
  }
}
