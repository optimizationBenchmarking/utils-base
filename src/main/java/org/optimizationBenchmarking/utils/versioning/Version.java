package org.optimizationBenchmarking.utils.versioning;
/*
 * The MIT License (MIT) Copyright (c) 2015 Simon Taddiken Permission is
 * hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions: The
 * above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software. THE SOFTWARE IS
 * PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * This class is an implementation of the full
 * <em>semantic version 2.0.0</em>
 * <a href="http://semver.org/">specification</a>. Instances can be
 * obtained using the static final overloads of the <em>create</em> method
 * or by {@link #parseVersion(String) parsing} a String. This class
 * implements {@link Comparable} to compare two versions by following the
 * specifications linked to above. The {@link #equals(Object)} method
 * conforms to the result of {@link #compareTo(Version)},
 * {@link #hashCode()} is implemented appropriately. Neither method
 * considers the {@link #getBuildMetaData() build meta data} field for
 * comparison.
 * <p>
 * Instances of this class are fully immutable.
 * </p>
 * <p>
 * Note that unless stated otherwise, none of the public final methods of
 * this class accept <code>null</code> values. Most methods will throw an
 * {@link IllegalArgumentException} when encountering a <code>null</code>
 * argument. However, to comply with the {@link Comparable} contract, the
 * comparison methods will throw a {@link NullPointerException} instead.
 * </p>
 * <p>
 * This is a strongly modified version from the code from repository
 * "https://github.com/skuzzle/semantic-version" by Simon Taddiken.
 * </p>
 *
 * @author Simon Taddiken (original class)
 * @author Thomas Weise (modified version)
 */
public final class Version extends Textable
    implements Comparable<Version>, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the pattern for matching pre-releases */
  private static final Pattern PRE_RELEASE = Pattern.compile(
      "(?:(?:[0-9]+[a-zA-Z-][\\w-]*)|(?:[a-zA-Z][\\w-]*)|(?:[1-9]\\d*)|0)(?:\\.(?:(?:[0-9]+[a-zA-Z-][\\w-]*)|(?:[a-zA-Z][\\w-]*)|(?:[1-9]\\d*)|0))*");//$NON-NLS-1$
  /** the pattern for matching build meta-data */
  private static final Pattern BUILD_MD = Pattern
      .compile("[\\w-]+(\\.[\\w-]+)*");//$NON-NLS-1$
  /** the pattern for matching version strings */
  private static final Pattern VERSION_PATTERN = Pattern.compile(
      "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:(?:[0-9]+[a-zA-Z-][\\w-]*)|(?:[a-zA-Z][\\w-]*)|(?:[1-9]\\d*)|0)(?:\\.(?:(?:[0-9]+[a-zA-Z-][\\w-]*)|(?:[a-zA-Z][\\w-]*)|(?:[1-9]\\d*)|0))*))?(?:\\+([\\w-]+(\\.[\\w-]+)*))?");//$NON-NLS-1$

  /** the match result index for the m_major group */
  private static final int MAJOR_GROUP = 1;
  /** the match result index for the m_minor group */
  private static final int MINOR_GROUP = 2;
  /** the match result index for the m_patch group */
  private static final int PATCH_GROUP = 3;
  /** the match result index for the pre-releast group */
  private static final int PRE_RELEASE_GROUP = 4;
  /** the match result index for the build-metadata group */
  private static final int BUILD_MD_GROUP = 5;
  /** the estimate for the to-string length */
  private static final int TO_STRING_ESTIMATE = 24;
  /** a prime number for computing hashes */
  private static final int HASH_PRIME = 31;

  /** the major version */
  private final int m_major;
  /** the minor version */
  private final int m_minor;
  /** the patch number */
  private final int m_patch;
  /** the pre-release data */
  private final String m_preRelease;
  /** the build metadata */
  private final String buildMetaData;

  /** the cached hash code */
  private volatile transient int m_hash;

  /**
   * create the version
   *
   * @param major
   *          the major version
   * @param minor
   *          the minor version
   * @param patch
   *          the patch number
   * @param preRelease
   *          the pre-release data
   * @param buildMd
   *          the build meta-data
   */
  public Version(final int major, final int minor, final int patch,
      final String preRelease, final String buildMd) {
    String preReleasePrepared, metaDataPrepared;
    if (major < 0) {
      throw new IllegalArgumentException(//
          "Major version number cannot be less than 0, but is " //$NON-NLS-1$
              + major);
    }
    if (minor < 0) {
      throw new IllegalArgumentException(//
          "Minor version number cannot be less than 0, but is " //$NON-NLS-1$
              + minor);
    }
    if (patch < 0) {
      throw new IllegalArgumentException(//
          "Patch version number cannot be less than 0, but is " //$NON-NLS-1$
              + patch);
    }

    preReleasePrepared = TextUtils.prepare(preRelease);
    if (preReleasePrepared != null) {
      if (!(Version.PRE_RELEASE.matcher(preReleasePrepared).matches())) {
        throw new IllegalArgumentException(//
            "If pre-release information is not null, it must match to the pattern '"//$NON-NLS-1$
                + Version.PRE_RELEASE.pattern() + "', but '" + //$NON-NLS-1$
                preRelease + "' does not.");//$NON-NLS-1$
      }
    }
    metaDataPrepared = TextUtils.prepare(buildMd);
    if (metaDataPrepared != null) {
      if (!(Version.BUILD_MD.matcher(metaDataPrepared).matches())) {
        throw new IllegalArgumentException(//
            "If build meta data information is not null, it must match to the pattern '"//$NON-NLS-1$
                + Version.BUILD_MD.pattern() + "', but '" + //$NON-NLS-1$
                buildMd + "' does not.");//$NON-NLS-1$
      }
    }

    this.m_major = major;
    this.m_minor = minor;
    this.m_patch = patch;
    this.m_preRelease = ((preReleasePrepared != null) ? preReleasePrepared
        : "");//$NON-NLS-1$
    this.buildMetaData = ((metaDataPrepared != null) ? metaDataPrepared
        : "");//$NON-NLS-1$
  }

  /**
   * Tries to parse the given String as a semantic version and returns
   * whether the String is properly formatted according to the semantic
   * version specification.
   * <p>
   * Note: this method does not throw an exception upon <code>null</code>
   * input, but returns <code>false</code> instead.
   * </p>
   *
   * @param version
   *          The String to check.
   * @return Whether the given String is formatted as a semantic version.
   */
  public static final boolean isValidVersion(final String version) {
    if ((version == null) || version.isEmpty()) {
      return false;
    }
    return Version.VERSION_PATTERN.matcher(version).matches();
  }

  /**
   * Returns whether the given String is a valid pre-release identifier.
   * That is, this method returns <code>true</code> if, and only if the
   * {@code m_preRelease} parameter is either the empty string or properly
   * formatted as a pre-release identifier according to the semantic
   * version specification.
   * <p>
   * Note: this method does not throw an exception upon <code>null</code>
   * input, but returns <code>false</code> instead.
   * </p>
   *
   * @param preRelease
   *          The String to check.
   * @return Whether the given String is a valid pre-release identifier.
   */
  public static final boolean isValidPreRelease(final String preRelease) {
    return (preRelease != null) && (preRelease.isEmpty()
        || Version.PRE_RELEASE.matcher(preRelease).matches());
  }

  /**
   * Returns whether the given String is a valid build meta data
   * identifier. That is, this method returns <code>true</code> if, and
   * only if the {@code buildMetaData} parameter is either the empty string
   * or properly formatted as a build meta data identifier according to the
   * semantic version specification.
   * <p>
   * Note: this method does not throw an exception upon <code>null</code>
   * input, but returns <code>false</code> instead.
   * </p>
   *
   * @param buildMetaData
   *          The String to check.
   * @return Whether the given String is a valid build meta data
   *         identifier.
   */
  public static final boolean isValidBuildMetaData(
      final String buildMetaData) {
    return (buildMetaData != null) && (buildMetaData.isEmpty()
        || Version.BUILD_MD.matcher(buildMetaData).matches());
  }

  /**
   * Returns the greater of the two given versions by comparing them using
   * their natural ordering. If both versions are equal, then the first
   * argument is returned.
   *
   * @param v1
   *          The first version.
   * @param v2
   *          The second version.
   * @return The greater version.
   * @throws IllegalArgumentException
   *           If either argument is <code>null</code>.
   */
  public static final Version max(final Version v1, final Version v2) {
    if (v1 == null) {
      throw new IllegalArgumentException(
          "First version record cannot be null for max version computation."); //$NON-NLS-1$
    }
    if (v2 == null) {
      throw new IllegalArgumentException(
          "Second version record cannot be null for max version computation."); //$NON-NLS-1$
    }
    return Version.__compare(v1, v2, false) < 0 ? v2 : v1;
  }

  /**
   * Returns the lower of the two given versions by comparing them using
   * their natural ordering. If both versions are equal, then the first
   * argument is returned.
   *
   * @param v1
   *          The first version.
   * @param v2
   *          The second version.
   * @return The lower version.
   * @throws IllegalArgumentException
   *           If either argument is <code>null</code>.
   */
  public static final Version min(final Version v1, final Version v2) {
    if (v1 == null) {
      throw new IllegalArgumentException(
          "First version record cannot be null for min version computation."); //$NON-NLS-1$
    }
    if (v2 == null) {
      throw new IllegalArgumentException(
          "Second version record cannot be null for min version computation."); //$NON-NLS-1$
    }
    return Version.__compare(v1, v2, false) <= 0 ? v1 : v2;
  }

  /**
   * Compares two versions, following the <em>semantic version</em>
   * specification. Here is a quote from
   * <a href="http://semver.org/">semantic version 2.0.0 specification</a>:
   * <p>
   * <em> Precedence refers to how versions are compared to each other when
   * ordered. Precedence MUST be calculated by separating the version into
   * m_major, m_minor, m_patch and pre-release identifiers in that order (Build
   * metadata does not figure into precedence). Precedence is determined by
   * the first difference when comparing each of these identifiers from left
   * to right as follows: Major, m_minor, and m_patch versions are always compared
   * numerically. Example: 1.0.0 &lt; 2.0.0 &lt; 2.1.0 &lt; 2.1.1. When m_major, m_minor,
   * and m_patch are equal, a pre-release version has lower precedence than a
   * normal version. Example: 1.0.0-alpha &lt; 1.0.0. Precedence for two
   * pre-release versions with the same m_major, m_minor, and m_patch version MUST
   * be determined by comparing each dot separated identifier from left to
   * right until a difference is found as follows: identifiers consisting of
   * only digits are compared numerically and identifiers with letters or
   * hyphens are compared lexically in ASCII sort order. Numeric identifiers
   * always have lower precedence than non-numeric identifiers. A larger set
   * of pre-release fields has a higher precedence than a smaller set, if all
   * of the preceding identifiers are equal. Example: 1.0.0-alpha &lt;
   * 1.0.0-alpha.1 &lt; 1.0.0-alpha.beta &lt; 1.0.0-beta &lt; 1.0.0-beta.2 &lt;
   * 1.0.0-beta.11 &lt; 1.0.0-rc.1 &lt; 1.0.0.
   * </em>
   * </p>
   * <p>
   * This method fulfills the general contract for Java's {@link Comparator
   * Comparators} and {@link Comparable Comparables}.
   * </p>
   *
   * @param v1
   *          The first version for comparison.
   * @param v2
   *          The second version for comparison.
   * @return A value below 0 iff {@code v1 &lt; v2}, a value above 0 iff
   *         {@code v1 &gt; v2</tt> and 0 iff <tt>v1 = v2}.
   * @throws NullPointerException
   *           If either parameter is null.
   * @since 0.2.0
   */
  public static final int compare(final Version v1, final Version v2) {
    if (v1 == null) {
      throw new IllegalArgumentException(
          "First version record cannot be null for version comparison."); //$NON-NLS-1$
    }
    if (v2 == null) {
      throw new IllegalArgumentException(
          "Second version record cannot be null for version comparison."); //$NON-NLS-1$
    }
    return Version.__compare(v1, v2, false);
  }

  /**
   * Compares two Versions with additionally considering the build meta
   * data field if all other parts are equal. Note: This is <em>not</em>
   * part of the semantic version specification.
   * <p>
   * Comparison of the build meta data parts happens exactly as for pre
   * release identifiers. Considering of build meta data first kicks in if
   * both versions are equal when using their natural order.
   * </p>
   * <p>
   * This method fulfills the general contract for Java's {@link Comparator
   * Comparators} and {@link Comparable Comparables}.
   * </p>
   *
   * @param v1
   *          The first version for comparison.
   * @param v2
   *          The second version for comparison.
   * @return A value below 0 iff {@code v1 &lt; v2}, a value above 0 iff
   *         {@code v1 &gt; v2</tt> and 0 iff <tt>v1 = v2}.
   * @throws NullPointerException
   *           If either parameter is null.
   */
  public static final int compareWithBuildMetaData(final Version v1,
      final Version v2) {
    if (v1 == null) {
      throw new IllegalArgumentException(
          "First version record cannot be null for version comparison with meta data."); //$NON-NLS-1$
    }
    if (v2 == null) {
      throw new IllegalArgumentException(
          "Second version record cannot be null for version comparison with meta data."); //$NON-NLS-1$
    }
    return Version.__compare(v1, v2, true);
  }

  /**
   * the internal version comparison method
   *
   * @param v1
   *          The first version for comparison.
   * @param v2
   *          The second version for comparison.
   * @param withBuildMetaData
   *          should we use meta-data
   * @return the result
   */
  private static final int __compare(final Version v1, final Version v2,
      final boolean withBuildMetaData) {
    int result;

    if (v1 == v2) {
      return 0;
    }
    if ((result = Integer.compare(v1.m_major, v2.m_major)) != 0) {
      return result;
    }
    if ((result = Integer.compare(v1.m_minor, v2.m_minor)) != 0) {
      return result;
    }
    if ((result = Integer.compare(v1.m_patch, v2.m_patch)) != 0) {
      return result;
    }
    if ((result = Version.__comparePreRelease(v1, v2)) != 0) {
      return result;
    }
    if (withBuildMetaData
        && ((result = Version.__compareBuildMetaData(v1, v2)) != 0)) {
      return result;
    }

    return 0;
  }

  /**
   * compare pre-release data
   *
   * @param v1
   *          the first version
   * @param v2
   *          the second version
   * @return the result
   */
  private static final int __comparePreRelease(final Version v1,
      final Version v2) {

    if (v1.isPreRelease() && v2.isPreRelease()) {
      return Version.__compareIdentifiers(v1.getPreReleaseParts(),
          v2.getPreReleaseParts());
    }
    if (v1.isPreRelease()) {
      return (-1);
    }
    if (v2.isPreRelease()) {
      return 1;
    }
    return 0;
  }

  /**
   * compare the build meta-data
   *
   * @param v1
   *          the first version
   * @param v2
   *          the second version
   * @return the result
   */
  private static final int __compareBuildMetaData(final Version v1,
      final Version v2) {

    if (v1.hasBuildMetaData() && v2.hasBuildMetaData()) {
      return Version.__compareIdentifiers(v1.getBuildMetaDataParts(),
          v2.getBuildMetaDataParts());
    }
    if (v1.hasBuildMetaData()) {
      return -1;
    }
    if (v2.hasBuildMetaData()) {
      return 1;
    }
    return 0;
  }

  /**
   * compare identifiers
   *
   * @param parts1
   *          the first identifier set
   * @param parts2
   *          the second identifier set
   * @return the result
   */
  private static final int __compareIdentifiers(final String[] parts1,
      final String[] parts2) {
    final int min;
    int result;

    min = Math.min(parts1.length, parts2.length);

    for (int i = 0; i < min; ++i) {
      result = Version.__comparePreReleaseParts(parts1[i], parts2[i]);
      if (result != 0) {
        return result;
      }
    }

    return Integer.compare(parts1.length, parts2.length);
  }

  /**
   * compare two identifiers
   *
   * @param p1
   *          the first identifer
   * @param p2
   *          the second identifer
   * @return the result
   */
  private static final int __comparePreReleaseParts(final String p1,
      final String p2) {
    final int num1, num2;

    num1 = Version.__isNumeric(p1);
    num2 = Version.__isNumeric(p2);

    if ((num1 < 0) && (num2 < 0)) {
      return p1.compareTo(p2);
    }
    if ((num1 >= 0) && (num2 >= 0)) {
      return Integer.compare(num1, num2);
    }
    if (num1 >= 0) {
      return (-1);
    }
    return 1;
  }

  /**
   * Determines whether s is a positive number. If it is, the number is
   * returned, otherwise the result is -1.
   *
   * @param s
   *          The String to check.
   * @return The positive number (incl. 0) if s a number, or -1 if it is
   *         not.
   */
  private static final int __isNumeric(final String s) {
    try {
      return Integer.parseInt(s);
    } catch (@SuppressWarnings("unused") final NumberFormatException e) {
      return -1;
    }
  }

  /**
   * Tries to parse the provided String as a semantic version. If the
   * string does not conform to the semantic version specification, a
   * {@link IllegalArgumentException} will be thrown.
   *
   * @param versionString
   *          The String to parse.
   * @return The parsed version.
   * @throws IllegalArgumentException
   *           If the String is no valid version or {@code null}
   */
  public static final Version parseVersion(final String versionString) {
    final Matcher matcher;

    if (versionString == null) {
      throw new IllegalArgumentException(
          "Version string must not be null."); //$NON-NLS-1$
    }

    matcher = Version.VERSION_PATTERN.matcher(versionString);
    if (!matcher.matches()) {
      throw new IllegalArgumentException(
          "Version string must match to pattern '" + //$NON-NLS-1$
              Version.VERSION_PATTERN.pattern() + "', but '" //$NON-NLS-1$
              + versionString + "' does not.");//$NON-NLS-1$
    }

    return new Version(//
        Integer.parseInt(matcher.group(Version.MAJOR_GROUP)), //
        Integer.parseInt(matcher.group(Version.MINOR_GROUP)), //
        Integer.parseInt(matcher.group(Version.PATCH_GROUP)), //
        matcher.group(Version.PRE_RELEASE_GROUP), //
        matcher.group(Version.BUILD_MD_GROUP));
  }

  /**
   * Returns the lower of this version and the given version according to
   * its natural ordering. If versions are equal, {@code this} is returned.
   *
   * @param other
   *          The version to compare with.
   * @return The lower version.
   * @throws IllegalArgumentException
   *           If {@code other} is <code>null</code>.
   * @since 0.5.0
   * @see #min(Version, Version)
   */
  public final Version min(final Version other) {
    return Version.min(this, other);
  }

  /**
   * Returns the greater of this version and the given version according to
   * its natural ordering. If versions are equal, {@code this} is returned.
   *
   * @param other
   *          The version to compare with.
   * @return The greater version.
   * @throws IllegalArgumentException
   *           If {@code other} is <code>null</code>.
   * @since 0.5.0
   * @see #max(Version, Version)
   */
  public final Version max(final Version other) {
    return Version.max(this, other);
  }

  /**
   * Gets this version's m_major number.
   *
   * @return The m_major version.
   */
  public final int getMajor() {
    return this.m_major;
  }

  /**
   * Gets this version's m_minor number.
   *
   * @return The m_minor version.
   */
  public final int getMinor() {
    return this.m_minor;
  }

  /**
   * Gets this version's path number.
   *
   * @return The m_patch number.
   */
  public final int getPatch() {
    return this.m_patch;
  }

  /**
   * Gets the pre release parts of this version as array by splitting the
   * pre result version string at the dots.
   *
   * @return Pre release version as array. Array is empty if this version
   *         has no pre release part.
   */
  public final String[] getPreReleaseParts() {
    return this.m_preRelease.split("\\."); //$NON-NLS-1$
  }

  /**
   * Gets the pre release identifier of this version. If this version has
   * no such identifier, an empty string is returned.
   *
   * @return This version's pre release identifier or an empty String if
   *         this version has no such identifier.
   */
  public final String getPreRelease() {
    return this.m_preRelease;
  }

  /**
   * Gets this version's build meta data. If this version has no build meta
   * data, the returned string is empty.
   *
   * @return The build meta data or an empty String if this version has no
   *         build meta data.
   */
  public final String getBuildMetaData() {
    return this.buildMetaData;
  }

  /**
   * Gets this version's build meta data as array by splitting the meta
   * data at dots. If this version has no build meta data, the result is an
   * empty array.
   *
   * @return The build meta data as array.
   */
  public final String[] getBuildMetaDataParts() {
    return this.buildMetaData.split("\\."); //$NON-NLS-1$
  }

  /**
   * Determines whether this version is still under initial development.
   *
   * @return <code>true</code> iff this version's m_major part is zero.
   */
  public final boolean isInitialDevelopment() {
    return (this.m_major <= 0);
  }

  /**
   * Determines whether this is a pre release version.
   *
   * @return <code>true</code> iff {@link #getPreRelease()} is not empty.
   */
  public final boolean isPreRelease() {
    return ((this.m_preRelease != null)
        && (this.m_preRelease.length() > 0));
  }

  /**
   * Determines whether this version has a build meta data field.
   *
   * @return <code>true</code> iff {@link #getBuildMetaData()} is not
   *         empty.
   */
  public final boolean hasBuildMetaData() {
    return ((this.buildMetaData != null)
        && (this.buildMetaData.length() > 0));
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.m_major);
    textOut.append('.');
    textOut.append(this.m_minor);
    textOut.append('.');
    textOut.append(this.m_patch);

    if ((this.m_preRelease != null) && (this.m_preRelease.length() > 0)) {
      textOut.append('-');
      textOut.append(this.m_preRelease);
    }
    if ((this.buildMetaData != null)
        && (this.buildMetaData.length() > 0)) {
      textOut.append('+');
      textOut.append(this.buildMetaData);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput textOut;

    textOut = new MemoryTextOutput(this.m_preRelease.length()
        + this.buildMetaData.length() + Version.TO_STRING_ESTIMATE);
    this.toText(textOut);
    return textOut.toString();
  }

  /**
   * The m_hash code for a version instance is computed from the fields
   * {@link #getMajor() m_major}, {@link #getMinor() m_minor},
   * {@link #getPatch() m_patch} and {@link #getPreRelease() pre-release}.
   *
   * @return A m_hash code for this object.
   */
  @Override
  public final int hashCode() {
    int hash;

    hash = this.m_hash;
    if (hash == 0) {
      hash = (((((((Version.HASH_PRIME + this.m_major)
          * Version.HASH_PRIME) + this.m_minor) * Version.HASH_PRIME)
          + this.m_patch) * Version.HASH_PRIME)
          + this.m_preRelease.hashCode());
      this.m_hash = hash;
    }
    return hash;
  }

  /**
   * Determines whether this version is equal to the passed object. This is
   * the case if the passed object is an instance of Version and this
   * version {@link #compareTo(Version) compared} to the provided one
   * yields 0. Thus, this method ignores the {@link #getBuildMetaData()}
   * field.
   *
   * @param obj
   *          the object to compare with.
   * @return <code>true</code> iff {@code obj} is an instance of
   *         {@code Version} and {@code this.compareTo((Version) obj) == 0}
   * @see #compareTo(Version)
   */
  @Override
  public final boolean equals(final Object obj) {
    return this.testEquality(obj, false);
  }

  /**
   * Determines whether this version is equal to the passed object (as
   * determined by {@link #equals(Object)} and additionally considers the
   * build meta data part of both versions for equality.
   *
   * @param obj
   *          The object to compare with.
   * @return <code>true</code> iff {@code this.equals(obj)} and
   *         {@code this.getBuildMetaData().equals(((Version) obj).getBuildMetaData())}
   * @since 0.4.0
   */
  public final boolean equalsWithBuildMetaData(final Object obj) {
    return this.testEquality(obj, true);
  }

  /**
   * the internal test for equality
   *
   * @param obj
   *          the object to compare with
   * @param includeBuildMd
   *          should we include meta-data?
   * @return {@code true} if this object is equal to {@code obj},
   *         {@code false} otherwise
   */
  private boolean testEquality(final Object obj,
      final boolean includeBuildMd) {
    return (obj == this) || ((obj instanceof Version)
        && (Version.__compare(this, (Version) obj, includeBuildMd) == 0));
  }

  /**
   * Compares this version to the provided one, following the
   * <em>semantic versioning</em> specification. See
   * {@link #compare(Version, Version)} for more information.
   *
   * @param other
   *          The version to compare to.
   * @return A value lower than 0 if this &lt; other, a value greater than
   *         0 if this &gt; other and 0 if this == other. The absolute
   *         value of the result has no semantical interpretation.
   */
  @Override
  public final int compareTo(final Version other) {
    return Version.compare(this, other);
  }

  /**
   * Compares this version to the provided one. Unlike the
   * {@link #compareTo(Version)} method, this one additionally considers
   * the build meta data field of both versions, if all other parts are
   * equal. Note: This is <em>not</em> part of the semantic version
   * specification.
   * <p>
   * Comparison of the build meta data parts happens exactly as for pre
   * release identifiers. Considering of build meta data first kicks in if
   * both versions are equal when using their natural order.
   * </p>
   *
   * @param other
   *          The version to compare to.
   * @return A value lower than 0 if this &lt; other, a value greater than
   *         0 if this &gt; other and 0 if this == other. The absolute
   *         value of the result has no semantical interpretation.
   */
  public final int compareToWithBuildMetaData(final Version other) {
    return Version.compareWithBuildMetaData(this, other);
  }
}