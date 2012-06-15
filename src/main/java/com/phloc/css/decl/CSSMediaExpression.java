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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CCSS;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.media.ECSSMediaExpressionFeature;

/**
 * Represents a single media expression
 */
public final class CSSMediaExpression implements ICSSWriteable, ICSSVersionAware
{
  private final String m_sFeature;
  private final String m_sValue;

  public CSSMediaExpression (@Nonnull final ECSSMediaExpressionFeature eFeature)
  {
    this (eFeature.getName ());
  }

  public CSSMediaExpression (@Nonnull @Nonempty final String sFeature)
  {
    this (sFeature, null);
  }

  public CSSMediaExpression (@Nonnull final ECSSMediaExpressionFeature eFeature, @Nullable final String sValue)
  {
    this (eFeature.getName (), sValue);
  }

  public CSSMediaExpression (@Nonnull @Nonempty final String sFeature, @Nullable final String sValue)
  {
    if (StringHelper.hasNoText (sFeature))
      throw new IllegalArgumentException ("feature");
    m_sFeature = sFeature;
    m_sValue = sValue;
  }

  @Nonnull
  @Nonempty
  public String getFeature ()
  {
    return m_sFeature;
  }

  @Nullable
  public String getValue ()
  {
    return m_sValue;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings)
  {
    aSettings.checkVersionRequirements (this);

    final StringBuilder aSB = new StringBuilder ("(").append (m_sFeature);
    if (StringHelper.hasText (m_sValue))
      aSB.append (CCSS.SEPARATOR_PROPERTY_VALUE).append (m_sValue);
    return aSB.append (')').toString ();
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaExpression))
      return false;
    final CSSMediaExpression rhs = (CSSMediaExpression) o;
    return m_sFeature.equals (rhs.m_sFeature) && EqualsUtils.equals (m_sValue, rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sFeature).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("feature", m_sFeature).appendIfNotNull ("value", m_sValue).toString ();
  }
}
