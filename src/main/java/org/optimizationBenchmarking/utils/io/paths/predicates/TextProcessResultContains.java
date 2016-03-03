package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * A predicate checking whether text process' output contains certain
 * strings
 */
public class TextProcessResultContains extends TextProcessResultPredicate {

  /** the string predicates which need to be fulfilled */
  private final IPredicate<String>[] m_find;

  /**
   * create
   *
   * @param args
   *          the arguments to pass to the text process
   * @param find
   *          the string predicates which need to be fulfilled
   */
  public TextProcessResultContains(final String[] args,
      final IPredicate<String>[] find) {
    super(args);
    this.m_find = find;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean processOutput(final BufferedReader output)
      throws IOException {
    String line;
    boolean[] fulfill;
    int index, remaining;

    remaining = this.m_find.length;
    fulfill = new boolean[remaining];
    Arrays.fill(fulfill, true);

    looper: while ((line = output.readLine()) != null) {
      index = 0;
      for (final boolean bool : fulfill) {
        if (bool) {
          if (this.m_find[index].check(line)) {
            fulfill[index] = false;
            if ((--remaining) <= 0) {
              break looper;
            }
          }
        }
        ++index;
      }
    }

    return (((line == null) || (super.processOutput(output)))//
        && (remaining <= 0));
  }
}
