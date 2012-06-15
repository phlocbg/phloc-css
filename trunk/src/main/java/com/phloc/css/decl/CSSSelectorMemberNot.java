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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
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
  private final ICSSSelectorMember m_aNestedSelectorMember;

  public CSSSelectorMemberNot (@Nonnull @Nonempty final ICSSSelectorMember aNestedSelectorMember)
  {
    if (aNestedSelectorMember == null)
      throw new NullPointerException ("nestedSelector");
    m_aNestedSelectorMember = aNestedSelectorMember;
  }

  @Nonnull
  public ICSSSelectorMember getNestedMember ()
  {
    return m_aNestedSelectorMember;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    return ":not(" + m_aNestedSelectorMember.getAsCSSString (aSettings, nIndentLevel) + ")";
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
    return m_aNestedSelectorMember.equals (rhs.m_aNestedSelectorMember);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aNestedSelectorMember).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("nestedSelector", m_aNestedSelectorMember).toString ();
  }
}
