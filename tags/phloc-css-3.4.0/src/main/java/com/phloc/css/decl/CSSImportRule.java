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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single import rule on top level
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSImportRule implements ICSSWriteable, ICSSSourceLocationAware
{
  private CSSURI m_aLocation;
  private final List <CSSMediaQuery> m_aMediaQueries = new ArrayList <CSSMediaQuery> ();
  private CSSSourceLocation m_aSourceLocation;

  public CSSImportRule (@Nonnull final CSSURI aLocation)
  {
    setLocation (aLocation);
  }

  public boolean hasMediaQueries ()
  {
    return !m_aMediaQueries.isEmpty ();
  }

  @Nonnegative
  public int getMediaQueryCount ()
  {
    return m_aMediaQueries.size ();
  }

  public void addMediaQuery (@Nonnull final CSSMediaQuery aMediaQuery)
  {
    if (aMediaQuery == null)
      throw new NullPointerException ("mediaQuery");
    m_aMediaQueries.add (aMediaQuery);
  }

  public void addMediaQuery (@Nonnegative final int nIndex, @Nonnull final CSSMediaQuery aMediaQuery)
  {
    if (aMediaQuery == null)
      throw new NullPointerException ("mediaQuery");
    m_aMediaQueries.add (nIndex, aMediaQuery);
  }

  @Nonnull
  public EChange removeMediaQuery (@Nullable final CSSMediaQuery aMediaQuery)
  {
    return EChange.valueOf (m_aMediaQueries.remove (aMediaQuery));
  }

  @Nonnull
  public EChange removeMediaQuery (@Nonnegative final int nMediumIndex)
  {
    if (nMediumIndex < 0 || nMediumIndex >= m_aMediaQueries.size ())
      return EChange.UNCHANGED;
    m_aMediaQueries.remove (nMediumIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSMediaQuery> getAllMediaQueries ()
  {
    return ContainerHelper.newList (m_aMediaQueries);
  }

  /**
   * @return The URL object of the CSS file to import.
   */
  @Nonnull
  public CSSURI getLocation ()
  {
    return m_aLocation;
  }

  /**
   * @return The URL of the CSS file to import.
   */
  @Nonnull
  public String getLocationString ()
  {
    return m_aLocation.getURI ();
  }

  public void setLocation (@Nonnull final CSSURI aLocation)
  {
    if (aLocation == null)
      throw new NullPointerException ("location");
    m_aLocation = aLocation;
  }

  public void setLocationString (@Nonnull @Nonempty final String sLocationURI)
  {
    m_aLocation.setURI (sLocationURI);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("@import ").append (m_aLocation.getAsCSSString (aSettings, nIndentLevel));
    if (!m_aMediaQueries.isEmpty ())
    {
      aSB.append (' ');
      boolean bFirst = true;
      for (final CSSMediaQuery aMediaQuery : m_aMediaQueries)
      {
        if (bFirst)
          bFirst = false;
        else
          aSB.append (bOptimizedOutput ? "," : ", ");
        aSB.append (aMediaQuery.getAsCSSString (aSettings, nIndentLevel));
      }
    }
    return aSB.append (";\n").toString ();
  }

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
    if (!(o instanceof CSSImportRule))
      return false;
    final CSSImportRule rhs = (CSSImportRule) o;
    return m_aLocation.equals (rhs.m_aLocation) && m_aMediaQueries.equals (rhs.m_aMediaQueries);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aLocation).append (m_aMediaQueries).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("location", m_aLocation)
                                       .append ("mediaQueries", m_aMediaQueries)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
