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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a CSS calc() element
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSExpressionMemberMath implements ICSSExpressionMember, ICSSVersionAware, ICSSSourceLocationAware
{
  private final List <ICSSExpressionMathMember> m_aMembers = new ArrayList <ICSSExpressionMathMember> ();
  private CSSSourceLocation m_aSourceLocation;

  public CSSExpressionMemberMath ()
  {}

  @Nonnull
  public CSSExpressionMemberMath addMember (@Nonnull final ICSSExpressionMathMember aMember)
  {
    if (aMember == null)
      throw new NullPointerException ("member");

    m_aMembers.add (aMember);
    return this;
  }

  @Nonnull
  public CSSExpressionMemberMath addMember (@Nonnegative final int nIndex,
                                            @Nonnull final ICSSExpressionMathMember aMember)
  {
    if (nIndex < 0)
      throw new IllegalArgumentException ("Index too small: " + nIndex);
    if (aMember == null)
      throw new NullPointerException ("member");

    if (nIndex >= getMemberCount ())
      m_aMembers.add (aMember);
    else
      m_aMembers.add (nIndex, aMember);
    return this;
  }

  @Nonnull
  public EChange removeMember (@Nonnull final ICSSExpressionMathMember aMember)
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
  public List <ICSSExpressionMathMember> getAllMembers ()
  {
    return ContainerHelper.newList (m_aMembers);
  }

  @Nonnegative
  public int getMemberCount ()
  {
    return m_aMembers.size ();
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    final StringBuilder aSB = new StringBuilder ("calc(");
    for (final ICSSExpressionMathMember aMember : m_aMembers)
      aSB.append (aMember.getAsCSSString (aSettings, nIndentLevel));
    return aSB.append (")").toString ();
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
    if (!(o instanceof CSSExpressionMemberMath))
      return false;
    final CSSExpressionMemberMath rhs = (CSSExpressionMemberMath) o;
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
    return new ToStringGenerator (null).append ("members", m_aMembers)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
