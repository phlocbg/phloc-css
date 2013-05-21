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
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;

/**
 * This is the main object for a parsed CSS declaration. It has special handling
 * for import and namespace rules, as these rules must always be on the
 * beginning of a file. All other rules (all implementing
 * {@link ICSSTopLevelRule}) are maintained in a combined list.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CascadingStyleSheet implements ICSSSourceLocationAware
{
  private final List <CSSImportRule> m_aImportRules = new ArrayList <CSSImportRule> ();
  private final List <CSSNamespaceRule> m_aNamespaceRules = new ArrayList <CSSNamespaceRule> ();
  private final List <ICSSTopLevelRule> m_aRules = new ArrayList <ICSSTopLevelRule> ();
  private CSSSourceLocation m_aSourceLocation;

  public CascadingStyleSheet ()
  {}

  /**
   * @return <code>true</code> if at least one <code>@import</code> rule is
   *         present, <code>false</code> otherwise.
   */
  public boolean hasImportRules ()
  {
    return !m_aImportRules.isEmpty ();
  }

  /**
   * @return The number of contained <code>@import</code> rules. Always &ge; 0.
   */
  @Nonnegative
  public int getImportRuleCount ()
  {
    return m_aImportRules.size ();
  }

  /**
   * Add a new <code>@import</code> rule at the end.
   * 
   * @param aImportRule
   *        The import rule to add. May not be <code>null</code>.
   */
  public void addImportRule (@Nonnull final CSSImportRule aImportRule)
  {
    if (aImportRule == null)
      throw new NullPointerException ("ImportRule");
    m_aImportRules.add (aImportRule);
  }

  /**
   * Add a new <code>@import</code> rule at a specified index.
   * 
   * @param nIndex
   *        The index where the rule should be added.
   * @param aImportRule
   *        The import rule to add. May not be <code>null</code>.
   * @throws ArrayIndexOutOfBoundsException
   *         if the index is invalid
   */
  public void addImportRule (@Nonnegative final int nIndex, @Nonnull final CSSImportRule aImportRule)
  {
    if (aImportRule == null)
      throw new NullPointerException ("ImportRule");
    m_aImportRules.add (nIndex, aImportRule);
  }

  /**
   * Remove the specified <code>@import</code> rule.
   * 
   * @param aImportRule
   *        The import rule to be removed. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if removal was successful.
   */
  @Nonnull
  public EChange removeImportRule (@Nullable final CSSImportRule aImportRule)
  {
    return EChange.valueOf (m_aImportRules.remove (aImportRule));
  }

  /**
   * Remove the <code>@import</code> rule at the specified index.
   * 
   * @param nImportRuleIndex
   *        The index to be removed.
   * @return {@link EChange#CHANGED} if removal was successful.
   */
  @Nonnull
  public EChange removeImportRule (@Nonnegative final int nImportRuleIndex)
  {
    if (nImportRuleIndex < 0 || nImportRuleIndex >= m_aImportRules.size ())
      return EChange.UNCHANGED;
    m_aImportRules.remove (nImportRuleIndex);
    return EChange.CHANGED;
  }

  /**
   * @return A copy of all contained <code>@import</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSImportRule> getAllImportRules ()
  {
    return ContainerHelper.newList (m_aImportRules);
  }

  /**
   * @return <code>true</code> if at least one <code>@namespace</code> rule is
   *         present, <code>false</code> otherwise.
   */
  public boolean hasNamespaceRules ()
  {
    return !m_aNamespaceRules.isEmpty ();
  }

  /**
   * @return The number of contained <code>@namespace</code> rules. Always &ge;
   *         0.
   */
  @Nonnegative
  public int getNamespaceRuleCount ()
  {
    return m_aNamespaceRules.size ();
  }

  /**
   * Add a new <code>@namespace</code> rule at the end.
   * 
   * @param aNamespaceRule
   *        The namespace rule to be added. May not be <code>null</code>.
   */
  public void addNamespaceRule (@Nonnull final CSSNamespaceRule aNamespaceRule)
  {
    if (aNamespaceRule == null)
      throw new NullPointerException ("NamespaceRule");
    m_aNamespaceRules.add (aNamespaceRule);
  }

  /**
   * Add a new <code>@namespace</code> rule at the specified index.
   * 
   * @param nIndex
   *        The index where the rule should be added.
   * @param aNamespaceRule
   *        The namespace rule to be added. May not be <code>null</code>.
   */
  public void addNamespaceRule (@Nonnegative final int nIndex, @Nonnull final CSSNamespaceRule aNamespaceRule)
  {
    if (aNamespaceRule == null)
      throw new NullPointerException ("NamespaceRule");
    m_aNamespaceRules.add (nIndex, aNamespaceRule);
  }

  /**
   * Remove the specified <code>@namespace</code> rule.
   * 
   * @param aNamespaceRule
   *        The namespace rule to be removed. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if the namespace rule was successfully
   *         removed
   */
  @Nonnull
  public EChange removeNamespaceRule (@Nullable final CSSNamespaceRule aNamespaceRule)
  {
    return EChange.valueOf (m_aNamespaceRules.remove (aNamespaceRule));
  }

  /**
   * Remove the <code>@namespace</code> rule at the specified index.
   * 
   * @param nNamespaceRuleIndex
   *        The index to be removed.
   * @return {@link EChange#CHANGED} if the namespace rule was successfully
   *         removed
   */
  @Nonnull
  public EChange removeNamespaceRule (@Nonnegative final int nNamespaceRuleIndex)
  {
    if (nNamespaceRuleIndex < 0 || nNamespaceRuleIndex >= m_aNamespaceRules.size ())
      return EChange.UNCHANGED;
    m_aNamespaceRules.remove (nNamespaceRuleIndex);
    return EChange.CHANGED;
  }

  /**
   * @return A copy of all <code>@namespace</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSNamespaceRule> getAllNamespaceRules ()
  {
    return ContainerHelper.newList (m_aNamespaceRules);
  }

  /**
   * Check if any top-level rule is present. This method only considers
   * top-level rules and not import and namespace rules!
   * 
   * @return <code>true</code> if at least one top-level rule is present,
   *         <code>false</code> if otherwise.
   */
  public boolean hasRules ()
  {
    return !m_aRules.isEmpty ();
  }

  /**
   * @return The number of total contained top-level rules. Always &ge; 0.
   */
  @Nonnegative
  public int getRuleCount ()
  {
    return m_aRules.size ();
  }

  /**
   * Add a new top-level rule at the end.
   * 
   * @param aRule
   *        The rule to be added. May not be <code>null</code>.
   */
  public void addRule (@Nonnull final ICSSTopLevelRule aRule)
  {
    if (aRule == null)
      throw new NullPointerException ("styleRule");
    m_aRules.add (aRule);
  }

  /**
   * Add a new top-level rule at the specified index.
   * 
   * @param nIndex
   *        The index where the top-level rule should be added. Must be &ge; 0.
   * @param aRule
   *        The rule to be added. May not be <code>null</code>.
   */
  public void addRule (@Nonnegative final int nIndex, @Nonnull final ICSSTopLevelRule aRule)
  {
    if (aRule == null)
      throw new NullPointerException ("styleRule");
    m_aRules.add (nIndex, aRule);
  }

  /**
   * Remove the specified top-level rule.
   * 
   * @param aRule
   *        The rule to be removed. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if the rule was successfully removed.
   */
  @Nonnull
  public EChange removeRule (@Nullable final ICSSTopLevelRule aRule)
  {
    return EChange.valueOf (m_aRules.remove (aRule));
  }

  /**
   * Remove the rule at the specified index.
   * 
   * @param nRuleIndex
   *        The index of the rule to be removed.
   * @return {@link EChange#CHANGED} if the rule at the specified index was
   *         successfully removed
   */
  @Nonnull
  public EChange removeRule (@Nonnegative final int nRuleIndex)
  {
    if (nRuleIndex < 0 || nRuleIndex >= m_aRules.size ())
      return EChange.UNCHANGED;
    m_aRules.remove (nRuleIndex);
    return EChange.CHANGED;
  }

  /**
   * @return A copy of all contained top-level rules. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSTopLevelRule> getAllRules ()
  {
    return ContainerHelper.newList (m_aRules);
  }

  /**
   * @return <code>true</code> if at least one style rule is contained,
   *         <code>false</code> otherwise.
   */
  public boolean hasStyleRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSStyleRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained style rules. Always &ge; 0.
   */
  @Nonnegative
  public int getStyleRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSStyleRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained style rules. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSStyleRule> getAllStyleRules ()
  {
    final List <CSSStyleRule> ret = new ArrayList <CSSStyleRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSStyleRule)
        ret.add ((CSSStyleRule) aTopLevelRule);
    return ret;
  }

  /**
   * @return <code>true</code> if at least one <code>@page</code> rule is
   *         contained, <code>false</code> otherwise.
   */
  public boolean hasPageRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSPageRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained <code>@page</code> rules. Always &ge; 0.
   */
  @Nonnegative
  public int getPageRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSPageRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained <code>@page</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSPageRule> getAllPageRules ()
  {
    final List <CSSPageRule> ret = new ArrayList <CSSPageRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSPageRule)
        ret.add ((CSSPageRule) aTopLevelRule);
    return ret;
  }

  /**
   * @return <code>true</code> if at least one <code>@media</code> rule is
   *         contained, <code>false</code> otherwise.
   */
  public boolean hasMediaRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSMediaRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained <code>@media</code> rules. Always &ge; 0.
   */
  @Nonnegative
  public int getMediaRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSMediaRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained <code>@media</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSMediaRule> getAllMediaRules ()
  {
    final List <CSSMediaRule> ret = new ArrayList <CSSMediaRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSMediaRule)
        ret.add ((CSSMediaRule) aTopLevelRule);
    return ret;
  }

  /**
   * @return <code>true</code> if at least one <code>@font-face</code> rule is
   *         contained, <code>false</code> otherwise.
   */
  public boolean hasFontFaceRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSFontFaceRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained <code>@font-face</code> rules. Always &ge;
   *         0.
   */
  @Nonnegative
  public int getFontFaceRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSFontFaceRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained <code>@font-face</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSFontFaceRule> getAllFontFaceRules ()
  {
    final List <CSSFontFaceRule> ret = new ArrayList <CSSFontFaceRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSFontFaceRule)
        ret.add ((CSSFontFaceRule) aTopLevelRule);
    return ret;
  }

  /**
   * @return <code>true</code> if at least one <code>@keyframes</code> rule is
   *         contained, <code>false</code> otherwise.
   */
  public boolean hasKeyframesRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSKeyframesRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained <code>@keyframes</code> rules. Always &ge;
   *         0.
   */
  @Nonnegative
  public int getKeyframesRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSKeyframesRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained <code>@keyframes</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSKeyframesRule> getAllKeyframesRules ()
  {
    final List <CSSKeyframesRule> ret = new ArrayList <CSSKeyframesRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSKeyframesRule)
        ret.add ((CSSKeyframesRule) aTopLevelRule);
    return ret;
  }

  /**
   * @return <code>true</code> if at least one <code>@viewport</code> rule is
   *         contained, <code>false</code> otherwise.
   */
  public boolean hasViewportRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSViewportRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained <code>@viewport</code> rules. Always &ge;
   *         0.
   */
  @Nonnegative
  public int getViewportRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSViewportRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained <code>@viewport</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSViewportRule> getAllViewportRules ()
  {
    final List <CSSViewportRule> ret = new ArrayList <CSSViewportRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSViewportRule)
        ret.add ((CSSViewportRule) aTopLevelRule);
    return ret;
  }

  /**
   * @return <code>true</code> if at least one <code>@supports</code> rule is
   *         contained, <code>false</code> otherwise.
   */
  public boolean hasSupportsRules ()
  {
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSSupportsRule)
        return true;
    return false;
  }

  /**
   * @return The number of contained <code>@supports</code> rules. Always &ge;
   *         0.
   */
  @Nonnegative
  public int getSupportsRuleCount ()
  {
    int ret = 0;
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSSupportsRule)
        ret++;
    return ret;
  }

  /**
   * @return A copy of all contained <code>@supports</code> rules. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <CSSSupportsRule> getAllSupportsRules ()
  {
    final List <CSSSupportsRule> ret = new ArrayList <CSSSupportsRule> ();
    for (final ICSSTopLevelRule aTopLevelRule : m_aRules)
      if (aTopLevelRule instanceof CSSSupportsRule)
        ret.add ((CSSSupportsRule) aTopLevelRule);
    return ret;
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
    if (!(o instanceof CascadingStyleSheet))
      return false;
    final CascadingStyleSheet rhs = (CascadingStyleSheet) o;
    return m_aImportRules.equals (rhs.m_aImportRules) &&
           m_aNamespaceRules.equals (rhs.m_aNamespaceRules) &&
           m_aRules.equals (rhs.m_aRules);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aImportRules)
                                       .append (m_aNamespaceRules)
                                       .append (m_aRules)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("importRules", m_aImportRules)
                                       .append ("namespaceRules", m_aNamespaceRules)
                                       .append ("rules", m_aRules)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
