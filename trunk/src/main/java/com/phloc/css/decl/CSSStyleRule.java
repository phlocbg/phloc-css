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

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single CSS style rule. A style rule consists of a number of
 * selectors (determine the element to which the style rule applies) and a
 * number of declarations (the rules to be applied to the selected elements).
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSStyleRule implements ICSSTopLevelRule, IHasCSSDeclarations
{
  private final List <CSSSelector> m_aSelectors = new ArrayList <CSSSelector> ();
  private final List <CSSDeclaration> m_aDeclarations = new ArrayList <CSSDeclaration> ();

  public CSSStyleRule ()
  {}

  public void addSelector (@Nonnull final CSSSelector aSelector)
  {
    if (aSelector == null)
      throw new NullPointerException ("selector");
    m_aSelectors.add (aSelector);
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

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSSelector> getAllSelectors ()
  {
    return ContainerHelper.newList (m_aSelectors);
  }

  public void addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    if (aDeclaration == null)
      throw new NullPointerException ("declaration");
    m_aDeclarations.add (aDeclaration);
  }

  @Nonnull
  public EChange removeDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    return EChange.valueOf (m_aDeclarations.remove (aDeclaration));
  }

  @Nonnull
  public EChange removeDeclaration (@Nonnegative final int nDeclarationIndex)
  {
    if (nDeclarationIndex < 0 || nDeclarationIndex >= m_aDeclarations.size ())
      return EChange.UNCHANGED;
    m_aDeclarations.remove (nDeclarationIndex);
    return EChange.CHANGED;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSDeclaration> getAllDeclarations ()
  {
    return ContainerHelper.newList (m_aDeclarations);
  }

  @Nonnegative
  public int getDeclarationCount ()
  {
    return m_aDeclarations.size ();
  }

  @Nonnull
  public String getSelectorsAsCSSString (@Nonnull final ICSSWriterSettings aSettings)
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
      aSB.append (aSelector.getAsCSSString (aSettings));
    }
    return aSB.toString ();
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings)
  {
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();
    final int nSelectorCount = m_aSelectors.size ();
    final int nDeclarationCount = m_aDeclarations.size ();

    final StringBuilder aSB = new StringBuilder ();
    if (false)
      if (!bOptimizedOutput && (nSelectorCount > 1 || nDeclarationCount > 1))
        aSB.append ('\n');

    // Append the selectors
    aSB.append (getSelectorsAsCSSString (aSettings));
    if (nDeclarationCount == 0)
    {
      // No declarations present
      aSB.append (bOptimizedOutput ? "{}" : " {}\n");
    }
    else
      if (nSelectorCount == 1 && nDeclarationCount == 1)
      {
        // Exactly one selector present
        aSB.append (bOptimizedOutput ? "{" : " { ")
           .append (m_aDeclarations.get (0).getAsCSSString (aSettings))
           .append (bOptimizedOutput ? "}" : " }\n");
      }
      else
      {
        // More than one selector or more than one declaration present
        aSB.append (bOptimizedOutput ? "{" : " {\n");
        for (final CSSDeclaration aDeclaration : m_aDeclarations)
        {
          // Indentation
          if (!bOptimizedOutput)
            aSB.append (aSettings.getIndent ());
          // Emit the main declaration
          aSB.append (aDeclaration.getAsCSSString (aSettings));
          if (!bOptimizedOutput)
            aSB.append ('\n');
        }
        aSB.append (bOptimizedOutput ? "}" : "}\n");
      }
    return aSB.toString ();
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
                                       .toString ();
  }
}
