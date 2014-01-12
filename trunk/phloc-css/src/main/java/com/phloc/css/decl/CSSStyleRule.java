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

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single CSS style rule. A style rule consists of a number of
 * selectors (determine the element to which the style rule applies) and a
 * number of declarations (the rules to be applied to the selected elements).
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSStyleRule implements ICSSTopLevelRule, IHasCSSDeclarations, ICSSSourceLocationAware
{
  private final List <CSSSelector> m_aSelectors = new ArrayList <CSSSelector> ();
  private final CSSDeclarationContainer m_aDeclarations = new CSSDeclarationContainer ();
  private CSSSourceLocation m_aSourceLocation;

  public CSSStyleRule ()
  {}

  public boolean hasSelectors ()
  {
    return !m_aSelectors.isEmpty ();
  }

  @Nonnegative
  public int getSelectorCount ()
  {
    return m_aSelectors.size ();
  }

  @Nonnull
  public CSSStyleRule addSelector (@Nonnull final ICSSSelectorMember aSingleSelectorMember)
  {
    if (aSingleSelectorMember == null)
      throw new NullPointerException ("SingleSelectorMember");
    return addSelector (new CSSSelector ().addMember (aSingleSelectorMember));
  }

  @Nonnull
  public CSSStyleRule addSelector (@Nonnull final CSSSelector aSelector)
  {
    if (aSelector == null)
      throw new NullPointerException ("selector");

    m_aSelectors.add (aSelector);
    return this;
  }

  @Nonnull
  public CSSStyleRule addSelector (@Nonnegative final int nIndex,
                                   @Nonnull final ICSSSelectorMember aSingleSelectorMember)
  {
    if (aSingleSelectorMember == null)
      throw new NullPointerException ("SingleSelectorMember");

    return addSelector (nIndex, new CSSSelector ().addMember (aSingleSelectorMember));
  }

  @Nonnull
  public CSSStyleRule addSelector (@Nonnegative final int nIndex, @Nonnull final CSSSelector aSelector)
  {
    if (nIndex < 0)
      throw new IllegalArgumentException ("Index too small: " + nIndex);
    if (aSelector == null)
      throw new NullPointerException ("selector");

    if (nIndex >= getSelectorCount ())
      m_aSelectors.add (aSelector);
    else
      m_aSelectors.add (nIndex, aSelector);
    return this;
  }

  @Nonnull
  public EChange removeSelector (@Nonnull final CSSSelector aSelector)
  {
    return EChange.valueOf (m_aSelectors.remove (aSelector));
  }

  @Nonnull
  public EChange removeSelector (@Nonnegative final int nSelectorIndex)
  {
    if (nSelectorIndex < 0 || nSelectorIndex >= m_aSelectors.size ())
      return EChange.UNCHANGED;
    m_aSelectors.remove (nSelectorIndex);
    return EChange.CHANGED;
  }

  @Nullable
  public CSSSelector getSelectorAtIndex (@Nonnegative final int nSelectorIndex)
  {
    if (nSelectorIndex < 0 || nSelectorIndex >= m_aSelectors.size ())
      return null;
    return m_aSelectors.get (nSelectorIndex);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSSelector> getAllSelectors ()
  {
    return ContainerHelper.newList (m_aSelectors);
  }

  @Nonnull
  public CSSStyleRule addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    m_aDeclarations.addDeclaration (aDeclaration);
    return this;
  }

  @Nonnull
  public CSSStyleRule addDeclaration (@Nonnegative final int nIndex, @Nonnull final CSSDeclaration aNewDeclaration)
  {
    m_aDeclarations.addDeclaration (nIndex, aNewDeclaration);
    return this;
  }

  @Nonnull
  public EChange removeDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    return m_aDeclarations.removeDeclaration (aDeclaration);
  }

  @Nonnull
  public EChange removeDeclaration (@Nonnegative final int nDeclarationIndex)
  {
    return m_aDeclarations.removeDeclaration (nDeclarationIndex);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSDeclaration> getAllDeclarations ()
  {
    return m_aDeclarations.getAllDeclarations ();
  }

  @Nullable
  public CSSDeclaration getDeclarationAtIndex (@Nonnegative final int nIndex)
  {
    return m_aDeclarations.getDeclarationAtIndex (nIndex);
  }

  @Nonnull
  public CSSStyleRule setDeclarationAtIndex (@Nonnegative final int nIndex,
                                             @Nonnull final CSSDeclaration aNewDeclaration)
  {
    m_aDeclarations.setDeclarationAtIndex (nIndex, aNewDeclaration);
    return this;
  }

  public boolean hasDeclarations ()
  {
    return m_aDeclarations.hasDeclarations ();
  }

  @Nonnegative
  public int getDeclarationCount ()
  {
    return m_aDeclarations.getDeclarationCount ();
  }

  @Nonnull
  public String getSelectorsAsCSSString (@Nonnull final ICSSWriterSettings aSettings,
                                         @Nonnegative final int nIndentLevel)
  {
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();
    final StringBuilder aSB = new StringBuilder ();
    boolean bFirst = true;
    for (final CSSSelector aSelector : m_aSelectors)
    {
      if (bFirst)
        bFirst = false;
      else
        aSB.append (bOptimizedOutput ? "," : ",\n");
      aSB.append (aSelector.getAsCSSString (aSettings, nIndentLevel));
    }
    return aSB.toString ();
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    if (aSettings.isRemoveUnnecessaryCode () && !hasDeclarations ())
      return "";

    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder ();

    // Append the selectors
    aSB.append (getSelectorsAsCSSString (aSettings, nIndentLevel));

    // Append the declarations
    aSB.append (m_aDeclarations.getAsCSSString (aSettings, nIndentLevel));
    if (!bOptimizedOutput)
      aSB.append ('\n');
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
    if (!(o instanceof CSSStyleRule))
      return false;
    final CSSStyleRule rhs = (CSSStyleRule) o;
    return m_aSelectors.equals (rhs.m_aSelectors) && m_aDeclarations.equals (rhs.m_aDeclarations);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aSelectors).append (m_aDeclarations).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("selectors", m_aSelectors)
                                       .append ("declarations", m_aDeclarations)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
