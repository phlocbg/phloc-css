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

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSWriterSettings;

/**
 * Represents a single <code>@media</code> rule: a list of style rules only
 * valid for certain media.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSMediaRule implements ICSSTopLevelRule
{
  private final List <CSSMediaQuery> m_aMediaQueries = new ArrayList <CSSMediaQuery> ();
  private final List <CSSStyleRule> m_aStyleRules = new ArrayList <CSSStyleRule> ();

  public CSSMediaRule ()
  {}

  public void addMediaQuery (@Nonnull @Nonempty final CSSMediaQuery aMediaQuery)
  {
    if (aMediaQuery == null)
      throw new NullPointerException ("mediaQuery");
    m_aMediaQueries.add (aMediaQuery);
  }

  @Nonnull
  public EChange removeMediaQuery (@Nonnull final CSSMediaQuery aMediaQuery)
  {
    return EChange.valueOf (m_aMediaQueries.remove (aMediaQuery));
  }

  @Nonnull
  public EChange removeMediaQuery (@Nonnegative final int nMediumIndex)
  {
    if (nMediumIndex < 0 || nMediumIndex >= m_aMediaQueries.size ())
      return EChange.UNCHANGED;
    m_aMediaQueries.remove (nMediumIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSMediaQuery> getAllMediaQueries ()
  {
    return ContainerHelper.newList (m_aMediaQueries);
  }

  public void addStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {
    if (aStyleRule == null)
      throw new NullPointerException ("styleRule");
    m_aStyleRules.add (aStyleRule);
  }

  @Nonnull
  public EChange removeStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {
    return EChange.valueOf (m_aStyleRules.remove (aStyleRule));
  }

  @Nonnull
  public EChange removeStyleRule (@Nonnegative final int nStyleRuleIndex)
  {
    if (nStyleRuleIndex < 0 || nStyleRuleIndex >= m_aStyleRules.size ())
      return EChange.UNCHANGED;
    m_aStyleRules.remove (nStyleRuleIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSStyleRule> getAllStyleRules ()
  {
    return ContainerHelper.newList (m_aStyleRules);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final CSSWriterSettings aSettings)
  {
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    if (bOptimizedOutput && m_aMediaQueries.isEmpty ())
      return "";

    final StringBuilder aSB = new StringBuilder ("@media ");
    boolean bFirst = true;
    for (final CSSMediaQuery sMedium : m_aMediaQueries)
    {
      if (bFirst)
        bFirst = false;
      else
        aSB.append (bOptimizedOutput ? "," : ", ");
      aSB.append (sMedium.getAsCSSString (aSettings));
    }
    aSB.append (bOptimizedOutput ? "{" : " {");
    if (!m_aStyleRules.isEmpty ())
    {
      // At least one style rule present
      if (!bOptimizedOutput)
        aSB.append ('\n');
      for (final CSSStyleRule aStyleRule : m_aStyleRules)
      {
        if (!bOptimizedOutput)
          aSB.append (aSettings.getIndent ());
        aSB.append (aStyleRule.getAsCSSString (aSettings));
        if (!bOptimizedOutput)
          aSB.append ('\n');
      }
    }
    aSB.append (bOptimizedOutput ? "}" : "}\n");
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaRule))
      return false;
    final CSSMediaRule rhs = (CSSMediaRule) o;
    return m_aMediaQueries.equals (rhs.m_aMediaQueries) && m_aStyleRules.equals (rhs.m_aStyleRules);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMediaQueries).append (m_aStyleRules).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("mediaQueries", m_aMediaQueries)
                                       .append ("styleRules", m_aStyleRules)
                                       .toString ();
  }
}
