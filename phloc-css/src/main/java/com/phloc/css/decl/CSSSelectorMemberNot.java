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

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents an inverted CSS selector.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSSelectorMemberNot implements ICSSSelectorMember, ICSSVersionAware, ICSSSourceLocationAware
{
  private final List <CSSSelector> m_aNestedSelectors;
  private CSSSourceLocation m_aSourceLocation;

  public CSSSelectorMemberNot (@Nonnull final List <CSSSelector> aNestedSelectors)
  {
    ValueEnforcer.notNull (aNestedSelectors, "NestedSelectors");
    m_aNestedSelectors = ContainerHelper.newList (aNestedSelectors);
  }

  public boolean hasNestedMembers ()
  {
    return !m_aNestedSelectors.isEmpty ();
  }

  @Nonnegative
  public int getNestedMemberCount ()
  {
    return m_aNestedSelectors.size ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSSelector> getNestedSelectors ()
  {
    return ContainerHelper.newList (m_aNestedSelectors);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);

    final StringBuilder aSB = new StringBuilder (":not(");
    int nIndex = 0;
    for (final CSSSelector aNestedSelector : m_aNestedSelectors)
    {
      if (nIndex > 0)
        aSB.append (',');
      aSB.append (aNestedSelector.getAsCSSString (aSettings, 0));
      nIndex++;
    }
    return aSB.append (')').toString ();
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
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSSelectorMemberNot rhs = (CSSSelectorMemberNot) o;
    return m_aNestedSelectors.equals (rhs.m_aNestedSelectors);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aNestedSelectors).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("nestedSelectors", m_aNestedSelectors)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
