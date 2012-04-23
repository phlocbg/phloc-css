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
import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSWriteable;

/**
 * Represents a single import rule on top level
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSImportRule implements ICSSWriteable
{
  private String m_sLocation;
  private final List <String> m_aMedia = new ArrayList <String> ();

  public CSSImportRule (@Nonnull @Nonempty final String sLocation)
  {
    setLocation (sLocation);
  }

  public void addMedium (@Nonnull @Nonempty final String sMedium)
  {
    if (StringHelper.hasNoText (sMedium))
      throw new IllegalArgumentException ("medium");
    m_aMedia.add (sMedium);
  }

  @Nonnull
  public EChange removeMedium (@Nonnull final String sMedium)
  {
    return EChange.valueOf (m_aMedia.remove (sMedium));
  }

  @Nonnull
  public EChange removeMedium (@Nonnegative final int nMediumIndex)
  {
    if (nMediumIndex < 0 || nMediumIndex >= m_aMedia.size ())
      return EChange.UNCHANGED;
    m_aMedia.remove (nMediumIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <String> getAllMedia ()
  {
    return ContainerHelper.makeUnmodifiable (m_aMedia);
  }

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
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("@import '").append (m_sLocation).append ('\'');
    if (!m_aMedia.isEmpty ())
    {
      aSB.append (' ');
      boolean bFirst = true;
      for (final String sMedium : m_aMedia)
      {
        if (bFirst)
          bFirst = false;
        else
          aSB.append (bOptimizedOutput ? "," : ", ");
        aSB.append (sMedium);
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
    return m_sLocation.equals (rhs.m_sLocation) && m_aMedia.equals (rhs.m_aMedia);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sLocation).append (m_aMedia).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("location", m_sLocation).append ("media", m_aMedia).toString ();
  }
}
