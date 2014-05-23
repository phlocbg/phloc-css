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

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single negation supports condition. E.g. "not (color: blue)"
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSSupportsConditionNegation implements ICSSSupportsConditionMember, ICSSSourceLocationAware
{
  private final ICSSSupportsConditionMember m_aSupportsMember;
  private CSSSourceLocation m_aSourceLocation;

  public CSSSupportsConditionNegation (@Nonnull final ICSSSupportsConditionMember aSupportsMember)
  {
    m_aSupportsMember = ValueEnforcer.notNull (aSupportsMember, "SupportsMember");
  }

  /**
   * @return The contained supports condition member. Never <code>null</code>.
   */
  @Nonnull
  public ICSSSupportsConditionMember getSupportsMember ()
  {
    return m_aSupportsMember;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    return "not " + m_aSupportsMember.getAsCSSString (aSettings, nIndentLevel);
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
    final CSSSupportsConditionNegation rhs = (CSSSupportsConditionNegation) o;
    return m_aSupportsMember.equals (rhs.m_aSupportsMember);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aSupportsMember).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("supportsMember", m_aSupportsMember)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
