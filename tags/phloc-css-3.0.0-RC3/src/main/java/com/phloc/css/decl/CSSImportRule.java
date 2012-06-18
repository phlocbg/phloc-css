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
package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single import rule on top level
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSImportRule implements ICSSWriteable
{
  private String m_sLocation;
  private final List <CSSMediaQuery> m_aMediaQueries = new ArrayList <CSSMediaQuery> ();

  public CSSImportRule (@Nonnull @Nonempty final String sLocation)
  {
    setLocation (sLocation);
  }

  public void addMediaQuery (@Nonnull @Nonempty final CSSMediaQuery aMediaQuery)
  {
    m_aMediaQueries.add (aMediaQuery);
  }

  @Nonnull
  public EChange removeMediaQuery (@Nonnull final CSSMediaQuery aMediaQuery)
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
   * @return The URL of the CSS file to import.
   */
  @Nonnull
  @Nonempty
  public String getLocation ()
  {
    return m_sLocation;
  }

  public void setLocation (@Nonnull @Nonempty final String sLocation)
  {
    if (StringHelper.hasNoText (sLocation))
      throw new IllegalArgumentException ("location may not be empty");
    m_sLocation = sLocation;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("@import '").append (m_sLocation).append ('\'');
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

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSImportRule))
      return false;
    final CSSImportRule rhs = (CSSImportRule) o;
    return m_sLocation.equals (rhs.m_sLocation) && m_aMediaQueries.equals (rhs.m_aMediaQueries);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sLocation).append (m_aMediaQueries).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("location", m_sLocation)
                                       .append ("mediaQueries", m_aMediaQueries)
                                       .toString ();
  }
}
