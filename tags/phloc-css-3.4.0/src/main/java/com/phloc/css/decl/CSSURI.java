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
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.utils.CSSURLHelper;

/**
 * Represents a single CSS URI
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSURI implements ICSSWriteable, ICSSSourceLocationAware
{
  private String m_sURI;
  private CSSSourceLocation m_aSourceLocation;

  public CSSURI (@Nonnull @Nonempty final String sURI)
  {
    setURI (sURI);
  }

  /**
   * @return The URL
   */
  @Nonnull
  @Nonempty
  public String getURI ()
  {
    return m_sURI;
  }

  public void setURI (@Nonnull @Nonempty final String sURI)
  {
    if (StringHelper.hasNoText (sURI))
      throw new IllegalArgumentException ("URI may not be empty");
    if (CSSURLHelper.isURLValue (sURI))
      throw new IllegalArgumentException ("Only the URI and not the CSS URI value must be passed!");
    m_sURI = sURI;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return CSSURLHelper.getAsCSSURL (m_sURI, aSettings.isQuoteURLs ());
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
    if (!(o instanceof CSSURI))
      return false;
    final CSSURI rhs = (CSSURI) o;
    return m_sURI.equals (rhs.m_sURI);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sURI).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("URI", m_sURI)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
