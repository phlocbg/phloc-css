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
package com.phloc.css.media;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Manages a set of {@link ECSSMedium} objects.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSMediaList implements Serializable
{
  // Ordered but unique
  private final Set <ECSSMedium> m_aMedia = new LinkedHashSet <ECSSMedium> ();

  public CSSMediaList ()
  {}

  public CSSMediaList (@Nonnull final ECSSMedium eMedium)
  {
    addMedium (eMedium);
  }

  public CSSMediaList (@Nonnull final ECSSMedium... aMedia)
  {
    for (final ECSSMedium eMedium : aMedia)
      addMedium (eMedium);
  }

  public CSSMediaList (@Nullable final Iterable <ECSSMedium> aMedia)
  {
    if (aMedia != null)
      for (final ECSSMedium eMedium : aMedia)
        addMedium (eMedium);
  }

  @Nonnull
  public CSSMediaList addMedium (@Nonnull final ECSSMedium eMedium)
  {
    if (eMedium == null)
      throw new NullPointerException ("medium");
    m_aMedia.add (eMedium);
    return this;
  }

  public boolean hasMedia ()
  {
    return !m_aMedia.isEmpty ();
  }

  public boolean isForScreen ()
  {
    return m_aMedia.isEmpty () || m_aMedia.contains (ECSSMedium.SCREEN) || m_aMedia.contains (ECSSMedium.ALL);
  }

  @Nonnull
  @ReturnsMutableCopy
  public Set <ECSSMedium> getMedia ()
  {
    return ContainerHelper.newSet (m_aMedia);
  }

  @Nonnull
  public String getMediaString ()
  {
    if (m_aMedia.isEmpty ())
      return "";

    final StringBuilder aSB = new StringBuilder ();
    for (final ECSSMedium eMedia : m_aMedia)
    {
      if (aSB.length () > 0)
        aSB.append (", ");
      aSB.append (eMedia.getAttrValue ());
    }
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaList))
      return false;
    final CSSMediaList rhs = (CSSMediaList) o;
    return m_aMedia.equals (rhs.m_aMedia);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMedia).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("media", m_aMedia).toString ();
  }
}
