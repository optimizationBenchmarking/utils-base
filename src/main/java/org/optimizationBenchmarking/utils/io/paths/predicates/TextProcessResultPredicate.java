package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessExecutor;

/** An base predicate checking a text process for some results. */
public class TextProcessResultPredicate implements IPredicate<Path> {

  /** the arguments to pass to the text process */
  private final String[] m_args;

  /**
   * create
   *
   * @param args
   *          the arguments to pass to the text process
   */
  public TextProcessResultPredicate(final String[] args) {
    super();
    this.m_args = args;
  }

  /**
   * Process the output of the process
   *
   * @param output
   *          the output
   * @return {@code true} if the output meets the predicate's demands
   * @throws IOException
   *           if i/o fails
   */
  protected boolean processOutput(final BufferedReader output)
      throws IOException {
    do {
      output.skip(Long.MAX_VALUE);
    } while (output.read() >= 0);
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Path object) {
    boolean result;
    TextProcessExecutor tool;
    TextProcessBuilder builder;

    tool = TextProcessExecutor.getInstance();
    if ((tool != null) && (tool.canUse())) {

      builder = tool.use();
      tool = null;
      builder.setDirectory(PathUtils.getTempDir());
      builder.setExecutable(object);

      if (this.m_args != null) {
        for (final String arg : this.m_args) {
          builder.addStringArgument(arg);
        }
      }

      builder.setStdOut(EProcessStream.AS_STREAM);
      builder.setStdErr(EProcessStream.AS_STREAM);
      builder.setStdIn(EProcessStream.IGNORE);
      builder.setMergeStdOutAndStdErr(true);

      try (final TextProcess process = builder.create()) {
        builder = null;

        result = this.processOutput(process.getStdOut());
        if (process.waitFor() == 0) {
          return result;
        }

      } catch (@SuppressWarnings("unused") final Throwable error) {
        return false;
      }
    }

    return false;
  }

}
