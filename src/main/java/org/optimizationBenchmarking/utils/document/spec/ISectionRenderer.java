package org.optimizationBenchmarking.utils.document.spec;

/**
 * This interface allows us to render a section into an existing
 * {@link ISectionBody}. It has three methods
 * {@link #createSectionLabel(ILabelBuilder)},
 * {@link #renderSectionBody(ISectionBody)}, and
 * {@link #renderSectionTitle(IComplexText)}. Of these three methods, only
 * the last one, i.e., {@link #renderSectionBody(ISectionBody)},
 * <em>must</em> be called when printing the section, the other two methods
 * are <em>optional</em>. Rendering a section by using an implementation of
 * this interface thus has at most the following three steps:
 * <ol>
 * <li><em>optionally</em> call {@link #createSectionLabel(ILabelBuilder)}
 * (or not)</li>
 * <li><em>optionally</em> call {@link #renderSectionTitle(IComplexText)}
 * (or not)</li>
 * <li>call {@link #renderSectionBody(ISectionBody)}</li>
 * </ol>
 * The idea of having the two optional methods is to permit the caller to
 * decide whether actually a new section should be created or whether the
 * contents (body) of the section can directly be inserted into an owning
 * section body.
 */
public interface ISectionRenderer {

  /**
   * Create a label or not. This method may only called if a section is
   * created.
   *
   * @param builder
   *          the label builder to use
   * @return the label or {@code null} if none is needed
   */
  public abstract ILabel createSectionLabel(final ILabelBuilder builder);

  /**
   * Print the title of the section. This method may only called if a
   * section is created.
   *
   * @param title
   *          the title
   */
  public abstract void renderSectionTitle(final IComplexText title);

  /**
   * Print the body of the section.
   *
   * @param body
   *          the section body
   */
  public abstract void renderSectionBody(final ISectionBody body);

}
