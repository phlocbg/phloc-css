/**
 * Copyright (C) 2006-2014 phloc systems
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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents an expression member URI
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSExpressionMemberTermURI implements ICSSExpressionMember, ICSSSourceLocationAware
{
  private CSSURI m_aURI;

  public CSSExpressionMemberTermURI (@Nonnull @Nonempty final String sURIString)
  {
    this (new CSSURI (sURIString));
  }

  public CSSExpressionMemberTermURI (@Nonnull final CSSURI aURI)
  {
    setURI (aURI);
  }

  /**
   * @return The contained {@link CSSURI} object.
   */
  @Nonnull
  public CSSURI getURI ()
  {
    return m_aURI;
  }

  /**
   * @return A sanity shortcut for <code>getURI().getURI()</code>
   */
  @Nonnull
  @Nonempty
  public String getURIString ()
  {
    return m_aURI.getURI ();
  }

  /**
   * Set a new URI
   * 
   * @param aURI
   *        The new URI to set. May not be <code>null</code>.
   */
  public void setURI (@Nonnull final CSSURI aURI)
  {
    if (aURI == null)
      throw new NullPointerException ("URI");
    m_aURI = aURI;
  }

  /**
   * Replace the URI string in the existing {@link CSSURI} object.
   * 
   * @param sURIString
   *        The new URI string to set. May neither be <code>null</code> nor
   *        empty.
   */
  public void setURIString (@Nonnull @Nonempty final String sURIString)
  {
    m_aURI.setURI (sURIString);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return m_aURI.getAsCSSString (aSettings, nIndentLevel);
  }

  @Nullable
  public CSSSourceLocation getSourceLocation ()
  {
    return m_aURI.getSourceLocation ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSExpressionMemberTermURI))
      return false;
    final CSSExpressionMemberTermURI rhs = (CSSExpressionMemberTermURI) o;
    return m_aURI.equals (rhs.m_aURI);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aURI).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("URI", m_aURI).toString ();
  }
}
