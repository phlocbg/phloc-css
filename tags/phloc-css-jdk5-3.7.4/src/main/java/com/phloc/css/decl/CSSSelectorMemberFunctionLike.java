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
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single CSS complex selector pseudo element. Like
 * <code>:lang(fr)</code>
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSSelectorMemberFunctionLike implements ICSSSelectorMember, ICSSSourceLocationAware
{
  private final String m_sFuncName;
  private final CSSExpression m_aParamExpr;
  private CSSSourceLocation m_aSourceLocation;

  public CSSSelectorMemberFunctionLike (@Nonnull @Nonempty final String sFuncName,
                                        @Nonnull final CSSExpression aParamExpr)
  {
    if (StringHelper.hasNoText (sFuncName))
      throw new IllegalArgumentException ("empty function name is not allowed");
    if (!sFuncName.endsWith ("("))
      throw new IllegalArgumentException ("function name must end with a '('");
    if (aParamExpr == null)
      throw new NullPointerException ("paramExpr");
    m_sFuncName = sFuncName;
    m_aParamExpr = aParamExpr;
  }

  /**
   * @return The name of the function with a trailing "("
   */
  @Nonnull
  @Nonempty
  public String getFunctionName ()
  {
    return m_sFuncName;
  }

  @Nonnull
  public CSSExpression getParameterExpression ()
  {
    return m_aParamExpr;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return m_sFuncName + m_aParamExpr.getAsCSSString (aSettings, nIndentLevel) + ')';
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
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSSelectorMemberFunctionLike rhs = (CSSSelectorMemberFunctionLike) o;
    return m_sFuncName.equals (rhs.m_sFuncName) && m_aParamExpr.equals (rhs.m_aParamExpr);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sFuncName).append (m_aParamExpr).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("functionName", m_sFuncName)
                                       .append ("paramExpr", m_aParamExpr)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
