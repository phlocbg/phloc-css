package com.phloc.css;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface ICSSWriterSettings
{
  /**
   * @return The CSS version to be used. May not be <code>null</code>.
   */
  @Nonnull
  ECSSVersion getVersion ();

  /**
   * @return <code>true</code> if all unnecessary whitespaces should be ignored
   *         when writing.
   */
  boolean isOptimizedOutput ();

  /**
   * @return <code>true</code> if all unnecessary elements (like empty style
   *         declarations) should be removed. This will than potentially lead to
   *         CSS that is not equal to the original CSS!
   */
  boolean isRemoveUnnecessaryCode ();

  /**
   * Get the indentation for an arbitrary number of levels.
   * 
   * @param nCount
   *          The number of indentations desired.
   * @return The string to be used for indentation. May not be <code>null</code>
   *         but may be empty.
   */
  @Nonnull
  String getIndent (@Nonnegative int nCount);

  /**
   * Check if the passed object matches the version requirements defined be this
   * settings.
   * 
   * @param aCSSObject
   *          The object to be checked.
   * @throws IllegalStateException
   *           In case the version does not match
   */
  void checkVersionRequirements (@Nonnull ICSSVersionAware aCSSObject) throws IllegalStateException;
}
