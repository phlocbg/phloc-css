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
import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

/**
 * Represents a single <code>@media</code> rule: a list of style rules only
 * valid for certain media.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSMediaRule implements ICSSTopLevelRule
{
  private final List <String> m_aMedia = new ArrayList <String> ();
  private final List <CSSStyleRule> m_aStyleRules = new ArrayList <CSSStyleRule> ();

  public CSSMediaRule ()
  {}

  public void addMedium (@Nonnull @Nonempty final String sMedium)
  {
    if (StringHelper.hasNoText (sMedium))
      throw new IllegalArgumentException ("medium");
    m_aMedia.add (sMedium);
  }

  @Nonnull
  public EChange removeMedium (@Nonnull final String sMedium)
  {
    return EChange.valueOf (m_aMedia.remove (sMedium));
  }

  @Nonnull
  public EChange removeMedium (@Nonnegative final int nMediumIndex)
  {
    if (nMediumIndex < 0 || nMediumIndex >= m_aMedia.size ())
      return EChange.UNCHANGED;
    m_aMedia.remove (nMediumIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <String> getAllMedia ()
  {
    return ContainerHelper.makeUnmodifiable (m_aMedia);
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
  @ReturnsImmutableObject
  public List <CSSStyleRule> getAllStyleRules ()
  {
    return ContainerHelper.makeUnmodifiable (m_aStyleRules);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (bOptimizedOutput && m_aMedia.isEmpty ())
      return "";

    final StringBuilder aSB = new StringBuilder ("@media ");
    boolean bFirst = true;
    for (final String sMedium : m_aMedia)
    {
      if (bFirst)
        bFirst = false;
      else
        aSB.append (bOptimizedOutput ? "," : ", ");
      aSB.append (sMedium);
    }
    aSB.append (bOptimizedOutput ? "{" : " {");
    if (!bOptimizedOutput && !m_aStyleRules.isEmpty ())
      aSB.append ('\n');
    for (final CSSStyleRule aStyleRule : m_aStyleRules)
    {
      if (!bOptimizedOutput)
        aSB.append ("  ");
      aSB.append (aStyleRule.getAsCSSString (eVersion, bOptimizedOutput));
      if (!bOptimizedOutput)
        aSB.append ('\n');
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
    return m_aMedia.equals (rhs.m_aMedia) && m_aStyleRules.equals (rhs.m_aStyleRules);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMedia).append (m_aStyleRules).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("medias", m_aMedia).append ("styleRules", m_aStyleRules).toString ();
  }
}
