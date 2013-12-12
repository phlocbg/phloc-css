/**
 * Copyright (C) 2006-2013 phloc systems
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
package com.phloc.css.decl;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single namespace rule on top level.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSNamespaceRule implements ICSSWriteable, ICSSSourceLocationAware
{
  private String m_sPrefix;
  private String m_sURL;
  private CSSSourceLocation m_aSourceLocation;

  public CSSNamespaceRule (@Nullable final String sNamespacePrefix, @Nonnull final String sURL)
  {
    setNamespacePrefix (sNamespacePrefix);
    setNamespaceURL (sURL);
  }

  @Nullable
  public String getNamespacePrefix ()
  {
    return m_sPrefix;
  }

  public void setNamespacePrefix (@Nullable final String sNamespacePrefix)
  {
    m_sPrefix = sNamespacePrefix;
  }

  /**
   * @return The namespace URL. May not be <code>null</code> but maybe empty!
   */
  @Nonnull
  public String getNamespaceURL ()
  {
    return m_sURL;
  }

  public void setNamespaceURL (@Nonnull final String sURL)
  {
    if (sURL == null)
      throw new NullPointerException ("URL");
    m_sURL = sURL;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    // Always ignore namespace rules?
    if (!aSettings.isWriteNamespaceRules ())
      return "";

    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("@namespace ");
    if (StringHelper.hasText (m_sPrefix))
      aSB.append (m_sPrefix).append (' ');
    return aSB.append (m_sURL).append (";\n").toString ();
  }

  /**
   * Set the source location of the object, determined while parsing.
   * 
   * @param aSourceLocation
   *        The source location to use. May be <code>null</code>.
   */
  public void setSourceLocation (@Nullable final CSSSourceLocation aSourceLocation)
  {
    m_aSourceLocation = aSourceLocation;
  }

  @Nullable
  public CSSSourceLocation getSourceLocation ()
  {
    return m_aSourceLocation;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSNamespaceRule))
      return false;
    final CSSNamespaceRule rhs = (CSSNamespaceRule) o;
    return EqualsUtils.equals (m_sPrefix, rhs.m_sPrefix) && m_sURL.equals (rhs.m_sURL);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sPrefix).append (m_sURL).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).appendIfNotNull ("prefix", m_sPrefix)
                                       .append ("URL", m_sURL)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
