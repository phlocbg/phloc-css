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
package com.phloc.css.writer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * This class represents the options required for writing
 * 
 * @author philip
 */
@NotThreadSafe
public class CSSWriterSettings implements ICSSWriterSettings
{
  public static final String DEFAULT_INDENT = "  ";

  private final ECSSVersion m_eVersion;
  private final boolean m_bOptimizedOutput;
  private String m_sIndent = DEFAULT_INDENT;

  /**
   * @param eVersion
   *          CSS version to emit
   * @param bOptimizedOutput
   *          if <code>true</code> the output will be optimized for space, else
   *          for readability
   */
  public CSSWriterSettings (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    m_eVersion = eVersion;
    m_bOptimizedOutput = bOptimizedOutput;
  }

  @Nonnull
  public final ECSSVersion getVersion ()
  {
    return m_eVersion;
  }

  public final boolean isOptimizedOutput ()
  {
    return m_bOptimizedOutput;
  }

  @Nonnull
  public final String getIndent (@Nonnegative final int nCount)
  {
    return StringHelper.getRepeated (m_sIndent, nCount);
  }

  @Nonnull
  public final ICSSWriterSettings setIndent (@Nonnull final String sIndent)
  {
    if (sIndent == null)
      throw new NullPointerException ("indent");
    m_sIndent = sIndent;
    return this;
  }

  public void checkVersionRequirements (@Nonnull final ICSSVersionAware aCSSObject)
  {
    final ECSSVersion eMinCSSVersion = aCSSObject.getMinimumCSSVersion ();
    if (m_eVersion.compareTo (eMinCSSVersion) < 0)
      throw new IllegalStateException ("This object cannot be serialized to CSS version " +
                                       m_eVersion.getVersion ().getAsString () +
                                       " but requires at least " +
                                       eMinCSSVersion.getVersion ().getAsString ());
  }
}
