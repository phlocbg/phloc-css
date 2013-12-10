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
import com.phloc.css.CCSS;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single element in a CSS style rule. (eg. <code>color:red;</code>
 * or <code>background:uri(a.gif) !important;</code>)
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSDeclaration implements ICSSWriteable, ICSSSourceLocationAware
{
  private final String m_sProperty;
  private final CSSExpression m_aExpression;
  private final boolean m_bImportant;
  private CSSSourceLocation m_aSourceLocation;

  public CSSDeclaration (@Nonnull @Nonempty final String sProperty,
                         @Nonnull final CSSExpression aExpression,
                         final boolean bImportant)
  {
    if (StringHelper.hasNoText (sProperty))
      throw new IllegalArgumentException ("empty property is not allowed");
    if (aExpression == null)
      throw new NullPointerException ("expression");
    m_sProperty = sProperty.toLowerCase ();
    m_aExpression = aExpression;
    m_bImportant = bImportant;
  }

  /**
   * @return The property of this declaration (e.g. "color" or "margin-top").
   *         Never <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public String getProperty ()
  {
    return m_sProperty;
  }

  /**
   * @return The expression of this declaration (e.g. "red" or "25px" or
   *         "25px 10px 25px 9px") as a structured value. Never
   *         <code>null</code>.
   */
  @Nonnull
  public CSSExpression getExpression ()
  {
    return m_aExpression;
  }

  /**
   * @return <code>true</code> if this declaration is important (
   *         <code>!important</code>) or not.
   */
  public boolean isImportant ()
  {
    return m_bImportant;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return m_sProperty +
           CCSS.SEPARATOR_PROPERTY_VALUE +
           m_aExpression.getAsCSSString (aSettings, nIndentLevel) +
           (m_bImportant ? CCSS.IMPORTANT_SUFFIX : "");
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
    if (!(o instanceof CSSDeclaration))
      return false;
    final CSSDeclaration rhs = (CSSDeclaration) o;
    return m_sProperty.equals (rhs.m_sProperty) &&
           m_aExpression.equals (rhs.m_aExpression) &&
           m_bImportant == rhs.m_bImportant;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sProperty)
                                       .append (m_aExpression)
                                       .append (m_bImportant)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("property", m_sProperty)
                                       .append ("expression", m_aExpression)
                                       .append ("important", m_bImportant)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
