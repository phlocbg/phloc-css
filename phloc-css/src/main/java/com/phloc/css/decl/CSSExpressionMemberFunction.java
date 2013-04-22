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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a CSS function element
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSExpressionMemberFunction implements ICSSExpressionMember, ICSSSourceLocationAware
{
  private final String m_sFunctionName;
  private final CSSExpression m_aExpression;
  private CSSSourceLocation m_aSourceLocation;

  @Nonnull
  private static String _skipBrackets (@Nonnull final String sName)
  {
    final String sRealName = sName.trim ();
    if (sRealName.length () > 2 && sRealName.endsWith ("()"))
      return sRealName.substring (0, sRealName.length () - 2).trim ();
    return sRealName;
  }

  /**
   * Ctor
   * 
   * @param sFunctionName
   *        Function name
   */
  public CSSExpressionMemberFunction (@Nonnull @Nonempty final String sFunctionName)
  {
    this (sFunctionName, null);
  }

  /**
   * Ctor
   * 
   * @param sFunctionName
   *        Function name
   * @param aExpression
   *        Optional parameter expression
   */
  public CSSExpressionMemberFunction (@Nonnull @Nonempty final String sFunctionName,
                                      @Nullable final CSSExpression aExpression)
  {
    if (StringHelper.hasNoText (sFunctionName))
      throw new IllegalArgumentException ("Empty function name is not allowed");
    // expression may be null
    m_sFunctionName = _skipBrackets (sFunctionName);
    m_aExpression = aExpression;
  }

  @Nonnull
  @Nonempty
  public String getFunctionName ()
  {
    return m_sFunctionName;
  }

  @Nullable
  public CSSExpression getExpression ()
  {
    return m_aExpression;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    if (m_aExpression == null)
      return m_sFunctionName + "()";
    return m_sFunctionName + "(" + m_aExpression.getAsCSSString (aSettings, nIndentLevel) + ")";
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
    if (!(o instanceof CSSExpressionMemberFunction))
      return false;
    final CSSExpressionMemberFunction rhs = (CSSExpressionMemberFunction) o;
    return m_sFunctionName.equals (rhs.m_sFunctionName) && EqualsUtils.equals (m_aExpression, rhs.m_aExpression);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sFunctionName).append (m_aExpression).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("funcName", m_sFunctionName)
                                       .appendIfNotNull ("expression", m_aExpression)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
