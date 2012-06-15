/**
 * Copyright (C) 2006-2012 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
