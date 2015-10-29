/**
 * Copyright (C) 2006-2015 phloc systems
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
import com.phloc.css.ECSSUnit;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.utils.CSSNumberHelper;

/**
 * Part of a CSS calc element
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSExpressionMemberMathUnitSimple implements ICSSExpressionMathMember, ICSSSourceLocationAware
{
  private final String m_sText;
  private final ECSSUnit m_eUnit;
  private CSSSourceLocation m_aSourceLocation;

  public CSSExpressionMemberMathUnitSimple (@Nonnull @Nonempty final String sText)
  {
    if (StringHelper.hasNoTextAfterTrim (sText))
      throw new IllegalArgumentException ("text");
    m_sText = sText.trim ();
    m_eUnit = CSSNumberHelper.getMatchingUnitExclPercentage (m_sText);
  }

  /**
   * @return The text including the unit.
   */
  @Nonnull
  public String getText ()
  {
    return m_sText;
  }

  /**
   * @return The applicable CSS unit. May be <code>null</code> if no unit is
   *         present.
   */
  @Nullable
  public ECSSUnit getUnit ()
  {
    return m_eUnit;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    return m_sText;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
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
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSExpressionMemberMathUnitSimple rhs = (CSSExpressionMemberMathUnitSimple) o;
    return m_sText.equals (rhs.m_sText);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sText).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("text", m_sText)
                                       .append ("unit", m_eUnit)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
