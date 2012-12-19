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
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;

/**
 * This is the main object for a parsed CSS declaration.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CascadingStyleSheet {
  private final List <CSSImportRule> m_aImportRules = new ArrayList <CSSImportRule> ();
  private final List <ICSSTopLevelRule> m_aRules = new ArrayList <ICSSTopLevelRule> ();

  public CascadingStyleSheet () {}

  public void addImportRule (@Nonnull final CSSImportRule aImportRule) {
    if (aImportRule == null)
      throw new NullPointerException ("importRule");
    m_aImportRules.add (aImportRule);
  }

  @Nonnull
  public EChange removeImportRule (@Nullable final CSSImportRule aImportRule) {
    return EChange.valueOf (m_aImportRules.remove (aImportRule));
  }

  @Nonnull
  public EChange removeImportRule (@Nonnegative final int nImportRuleIndex) {
    if (nImportRuleIndex < 0 || nImportRuleIndex >= m_aImportRules.size ())
      return EChange.UNCHANGED;
    m_aImportRules.remove (nImportRuleIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSImportRule> getAllImportRules () {
    return ContainerHelper.newList (m_aImportRules);
  }

  public void addRule (@Nonnull final ICSSTopLevelRule aStyleRule) {
    if (aStyleRule == null)
      throw new NullPointerException ("styleRule");
    m_aRules.add (aStyleRule);
  }

  @Nonnull
  public EChange removeRule (@Nullable final ICSSTopLevelRule aRule) {
    return EChange.valueOf (m_aRules.remove (aRule));
  }

  public EChange removeRule (@Nonnegative final int nRuleIndex) {
    if (nRuleIndex < 0 || nRuleIndex >= m_aRules.size ())
      return EChange.UNCHANGED;
    m_aRules.remove (nRuleIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSTopLevelRule> getAllRules () {
    return ContainerHelper.newList (m_aRules);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSStyleRule> getAllStyleRules () {
    final List <CSSStyleRule> ret = new ArrayList <CSSStyleRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSStyleRule)
        ret.add ((CSSStyleRule) aTopLevelRule);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSPageRule> getAllPageRules () {
    final List <CSSPageRule> ret = new ArrayList <CSSPageRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSPageRule)
        ret.add ((CSSPageRule) aTopLevelRule);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSMediaRule> getAllMediaRules () {
    final List <CSSMediaRule> ret = new ArrayList <CSSMediaRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSMediaRule)
        ret.add ((CSSMediaRule) aTopLevelRule);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSFontFaceRule> getAllFontFaceRules () {
    final List <CSSFontFaceRule> ret = new ArrayList <CSSFontFaceRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSFontFaceRule)
        ret.add ((CSSFontFaceRule) aTopLevelRule);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSKeyframesRule> getAllKeyframesRules () {
    final List <CSSKeyframesRule> ret = new ArrayList <CSSKeyframesRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSKeyframesRule)
        ret.add ((CSSKeyframesRule) aTopLevelRule);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSViewportRule> getAllViewportRules () {
    final List <CSSViewportRule> ret = new ArrayList <CSSViewportRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSViewportRule)
        ret.add ((CSSViewportRule) aTopLevelRule);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSNamespaceRule> getAllNamespaceRules () {
    final List <CSSNamespaceRule> ret = new ArrayList <CSSNamespaceRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSNamespaceRule)
        ret.add ((CSSNamespaceRule) aTopLevelRule);
    return ret;
  }

  @Override
  public boolean equals (final Object o) {
    if (o == this)
      return true;
    if (!(o instanceof CascadingStyleSheet))
      return false;
    final CascadingStyleSheet rhs = (CascadingStyleSheet) o;
    return m_aImportRules.equals (rhs.m_aImportRules) && m_aRules.equals (rhs.m_aRules);
  }

  @Override
  public int hashCode () {
    return new HashCodeGenerator (this).append (m_aImportRules).append (m_aRules).getHashCode ();
  }

  @Override
  public String toString () {
    return new ToStringGenerator (this).append ("importRules", m_aImportRules).append ("rules", m_aRules).toString ();
  }
}
