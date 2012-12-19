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

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents an inverted CSS selector.
 * 
 * @author philip
 */
@Immutable
public final class CSSSelectorMemberNot implements ICSSSelectorMember, ICSSVersionAware
{
  private final List <ICSSSelectorMember> m_aNestedSelectorMembers;

  public CSSSelectorMemberNot (@Nonnull @Nonempty final List <ICSSSelectorMember> aNestedSelectorMembers)
  {
    if (ContainerHelper.isEmpty (aNestedSelectorMembers))
      throw new IllegalArgumentException ("nestedSelectorMembers may not be empty");
    m_aNestedSelectorMembers = aNestedSelectorMembers;
  }

  @Nonnegative
  public int getNestedMemberCount ()
  {
    return m_aNestedSelectorMembers.size ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSSelectorMember> getNestedMembers ()
  {
    return ContainerHelper.newList (m_aNestedSelectorMembers);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);

    final StringBuilder aSB = new StringBuilder (":not(");
    for (final ICSSSelectorMember aSelectorMember : m_aNestedSelectorMembers)
      aSB.append (aSelectorMember.getAsCSSString (aSettings, nIndentLevel));
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
    if (!(o instanceof CSSSelectorMemberNot))
      return false;
    final CSSSelectorMemberNot rhs = (CSSSelectorMemberNot) o;
    return m_aNestedSelectorMembers.equals (rhs.m_aNestedSelectorMembers);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aNestedSelectorMembers).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("nestedSelectorMembers", m_aNestedSelectorMembers).toString ();
  }
}
