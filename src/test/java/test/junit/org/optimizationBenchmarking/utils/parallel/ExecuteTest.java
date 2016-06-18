package test.junit.org.optimizationBenchmarking.utils.parallel;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.parallel.Execute;

import shared.junit.TestBase;

/**
 * Test the Execute environment.
 */
public class ExecuteTest extends TestBase {

  /** create */
  public ExecuteTest() {
    super();
  }

  /**
   * test the creation and execution of many simple tasks
   *
   * @throws Throwable
   *           if it fails
   */
  @SuppressWarnings("unchecked")
  @Test(timeout = 3600000)
  public void testSimpleTasks() throws Throwable {
    final Future<Object>[] futures;
    final __SimpleTask[] tasks;
    final Random random;
    Future<Object> future;
    __SimpleTask task;
    int size, index;

    futures = new Future[1000];
    tasks = new __SimpleTask[futures.length];
    for (index = futures.length; (--index) >= 0;) {
      tasks[index] = new __SimpleTask();
      futures[index] = Execute.parallel(tasks[index]);
    }

    random = new Random();
    for (size = futures.length; size > 0;) {
      index = random.nextInt(size);
      --size;
      future = futures[index];
      futures[index] = futures[size];
      futures[size] = null;
      task = tasks[index];
      tasks[index] = tasks[size];
      tasks[size] = null;
      Assert.assertSame(task, future.get());
    }
  }

  /**
   * test the creation and execution of many simple tasks
   *
   * @throws Throwable
   *           if it fails
   */
  @SuppressWarnings("unchecked")
  @Test(timeout = 3600000)
  public void testRecursiveTasks() throws Throwable {
    final Future<Object>[] futures;
    final __RecursiveTask[] tasks;
    final Random random;
    Future<Object> future;
    __RecursiveTask task;
    int size, index;

    futures = new Future[100];
    tasks = new __RecursiveTask[futures.length];
    for (index = futures.length; (--index) >= 0;) {
      tasks[index] = new __RecursiveTask(1);
      futures[index] = Execute.parallel(tasks[index]);
    }

    random = new Random();
    for (size = futures.length; size > 0;) {
      index = random.nextInt(size);
      --size;
      future = futures[index];
      futures[index] = futures[size];
      futures[size] = null;
      task = tasks[index];
      tasks[index] = tasks[size];
      tasks[size] = null;
      Assert.assertSame(task, future.get());
    }
  }

  /** a random task */
  private static final class __SimpleTask implements Callable<Object> {

    /** create */
    __SimpleTask() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final __SimpleTask call() throws Exception {
      for (int i = 10000; (--i) > 0;) {
        if (((i * Math.PI) % 3d) > 1d) {
          Math.exp(i);
        } else {
          Math.log(i);
        }
      }
      return this;
    }
  }

  /** a recursive task */
  private static final class __RecursiveTask implements Callable<Object> {
    /** the depth */
    private final int m_depth;

    /**
     * create the recursive task
     *
     * @param depth
     *          the task depth
     */
    __RecursiveTask(final int depth) {
      super();
      this.m_depth = depth;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public final __RecursiveTask call() throws Exception {
      Future<Object>[] futures;
      Callable<Object>[] tasks;
      Future<Object> future;
      Callable<Object> task;
      int index;

      futures = new Future[4];
      tasks = new Callable[futures.length];
      for (index = futures.length; (--index) >= 0;) {
        tasks[index] = ((this.m_depth > 3) ? new __SimpleTask()
            : new __RecursiveTask(this.m_depth + 1));
        futures[index] = Execute.parallel(tasks[index]);
      }

      for (index = futures.length; (--index) > 0;) {
        future = futures[index];
        futures[index] = null;
        task = tasks[index];
        tasks[index] = null;
        Assert.assertSame(task, future.get());
      }

      return this;
    }
  }
}