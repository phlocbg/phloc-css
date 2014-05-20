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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single selector as the aggregation of selector members. An
 * example for a selector is e.g. <code>div</code> or <code>.class</code> or
 * <code>h1.class</code> or <code>#id</code> or
 * <code>table tbody td .class</code> etc.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSSelector implements ICSSWriteable, ICSSSourceLocationAware
{
  private final List <ICSSSelectorMember> m_aMembers = new ArrayList <ICSSSelectorMember> ();
  private CSSSourceLocation m_aSourceLocation;

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

  @Nonnull
  public CSSSelector addMember (@Nonnull final ICSSSelectorMember aMember)
  {
    ValueEnforcer.notNull (aMember, "Member");

    m_aMembers.add (aMember);
    return this;
  }

  @Nonnull
  public CSSSelector addMember (@Nonnegative final int nIndex, @Nonnull final ICSSSelectorMember aMember)
  {
    ValueEnforcer.isGE0 (nIndex, "Index");
    ValueEnforcer.notNull (aMember, "Member");

    if (nIndex >= getMemberCount ())
      m_aMembers.add (aMember);
    else
      m_aMembers.add (nIndex, aMember);
    return this;
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

  /**
   * Remove all members.
   * 
   * @return {@link EChange#CHANGED} if any member was removed,
   *         {@link EChange#UNCHANGED} otherwise. Never <code>null</code>.
   */
  @Nonnull
  public EChange removeAllMembers ()
  {
    if (m_aMembers.isEmpty ())
      return EChange.UNCHANGED;
    m_aMembers.clear ();
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
    return new ToStringGenerator (this).append ("members", m_aMembers)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
