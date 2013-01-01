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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single selector as the aggregation of selector members.
 * 
 * @author philip
 */
public final class CSSSelector implements ICSSWriteable
{
  private final List <ICSSSelectorMember> m_aMembers = new ArrayList <ICSSSelectorMember> ();

  public CSSSelector ()
  {}

  public boolean hasMembers ()
  {
    return !m_aMembers.isEmpty ();
  }

  @Nonnegative
  public int getMemberCount ()
  {
    return m_aMembers.size ();
  }

  public void addMember (@Nonnull final ICSSSelectorMember aMember)
  {
    if (aMember == null)
      throw new NullPointerException ("member");
    m_aMembers.add (aMember);
  }

  @Nonnull
  public EChange removeMember (@Nonnull final ICSSSelectorMember aMember)
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

  @Nullable
  public ICSSSelectorMember getMemberAtIndex (@Nonnegative final int nMemberIndex)
  {
    if (nMemberIndex < 0 || nMemberIndex >= m_aMembers.size ())
      return null;
    return m_aMembers.get (nMemberIndex);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSSelectorMember> getAllMembers ()
  {
    return ContainerHelper.newList (m_aMembers);
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final StringBuilder aSB = new StringBuilder ();
    for (final ICSSSelectorMember aMember : m_aMembers)
      aSB.append (aMember.getAsCSSString (aSettings, nIndentLevel));
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSelector))
      return false;
    final CSSSelector rhs = (CSSSelector) o;
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
    return new ToStringGenerator (this).append ("members", m_aMembers).toString ();
  }
}
