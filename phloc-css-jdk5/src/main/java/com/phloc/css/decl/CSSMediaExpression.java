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
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CCSS;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.media.ECSSMediaExpressionFeature;

/**
 * Represents a single media expression
 */
@NotThreadSafe
public class CSSMediaExpression implements ICSSWriteable, ICSSVersionAware, ICSSSourceLocationAware
{
  private final String m_sFeature;
  private final CSSExpression m_aValue;
  private CSSSourceLocation m_aSourceLocation;

  public CSSMediaExpression (@Nonnull final ECSSMediaExpressionFeature eFeature)
  {
    this (eFeature.getName ());
  }

  public CSSMediaExpression (@Nonnull @Nonempty final String sFeature)
  {
    this (sFeature, null);
  }

  public CSSMediaExpression (@Nonnull final ECSSMediaExpressionFeature eFeature, @Nullable final CSSExpression aValue)
  {
    this (eFeature.getName (), aValue);
  }

  public CSSMediaExpression (@Nonnull @Nonempty final String sFeature, @Nullable final CSSExpression aValue)
  {
    if (StringHelper.hasNoText (sFeature))
      throw new IllegalArgumentException ("feature");
    m_sFeature = sFeature;
    m_aValue = aValue;
  }

  @Nonnull
  @Nonempty
  public String getFeature ()
  {
    return m_sFeature;
  }

  @Nullable
  public CSSExpression getValue ()
  {
    return m_aValue;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);

    final StringBuilder aSB = new StringBuilder ("(").append (m_sFeature);
    if (m_aValue != null)
      aSB.append (CCSS.SEPARATOR_PROPERTY_VALUE).append (m_aValue.getAsCSSString (aSettings, nIndentLevel));
    return aSB.append (')').toString ();
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
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
    if (!(o instanceof CSSMediaExpression))
      return false;
    final CSSMediaExpression rhs = (CSSMediaExpression) o;
    return m_sFeature.equals (rhs.m_sFeature) && EqualsUtils.equals (m_aValue, rhs.m_aValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sFeature).append (m_aValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("feature", m_sFeature)
                                       .appendIfNotNull ("value", m_aValue)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
