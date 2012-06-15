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

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single expression consisting of several expression members
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSExpression implements ICSSWriteable
{
  private final List <ICSSExpressionMember> m_aMembers = new ArrayList <ICSSExpressionMember> ();

  public CSSExpression ()
  {}

  public void addMember (@Nonnull final ICSSExpressionMember aMember)
  {
    if (aMember == null)
      throw new NullPointerException ("member");
    m_aMembers.add (aMember);
  }

  @Nonnull
  public EChange removeMember (@Nonnull final ICSSExpressionMember aMember)
  {
    return EChange.valueOf (m_aMembers.remove (aMember));
  }

  @Nonnull
  public EChange removeMember (@Nonnegative final int nMemberIndex)
  {
    if (nMemberIndex < 0 || nMemberIndex >= m_aMembers.size ())
      return EChange.UNCHANGED;
    m_aMembers.remove (nMemberIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSExpressionMember> getAllMembers ()
  {
    return ContainerHelper.newList (m_aMembers);
  }

  @Nonnull
  public List <CSSExpressionMemberTermSimple> getAllSimpleMembers ()
  {
    final List <CSSExpressionMemberTermSimple> ret = new ArrayList <CSSExpressionMemberTermSimple> ();
    for (final ICSSExpressionMember aMember : m_aMembers)
      if (aMember instanceof CSSExpressionMemberTermSimple)
        ret.add ((CSSExpressionMemberTermSimple) aMember);
    return ret;
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final StringBuilder aSB = new StringBuilder ();
    boolean bPrevWasOperator = false;
    for (final ICSSExpressionMember aMember : m_aMembers)
    {
      final boolean bIsOp = aMember instanceof ECSSExpressionOperator;
      if (!bIsOp && !bPrevWasOperator && aSB.length () > 0)
      {
        // The space is required for separating values like "solid 1px black"
        aSB.append (' ');
      }
      aSB.append (aMember.getAsCSSString (aSettings, nIndentLevel));
      bPrevWasOperator = bIsOp;
    }
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSExpression))
      return false;
    final CSSExpression rhs = (CSSExpression) o;
    return m_aMembers.equals (rhs.m_aMembers);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMembers).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("members", m_aMembers).toString ();
  }
}
