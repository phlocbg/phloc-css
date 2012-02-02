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

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

/**
 * Represents a single CSS style rule. A style rule consists of a number of
 * selectors (determine the element to which the style rule applies) and a
 * number of declarations (the rules to be applied to the selected elements).
 * 
 * @author philip
 */
public final class CSSStyleRule implements ICSSTopLevelRule
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

  public void addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    if (aDeclaration == null)
      throw new NullPointerException ("declaration");
    m_aDeclarations.add (aDeclaration);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <CSSSelector> getAllSelectors ()
  {
    return ContainerHelper.makeUnmodifiable (m_aSelectors);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <CSSDeclaration> getAllDeclarations ()
  {
    return ContainerHelper.makeUnmodifiable (m_aDeclarations);
  }

  public String getSelectorsAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ();
    boolean bFirst = true;
    for (final CSSSelector aSelector : m_aSelectors)
    {
      if (bFirst)
        bFirst = false;
      else
        aSB.append (bOptimizedOutput ? "," : ",\n");
      aSB.append (aSelector.getAsCSSString (eVersion, bOptimizedOutput));
    }
    return aSB.toString ();
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ();
    if (!bOptimizedOutput && (m_aSelectors.size () > 1 || m_aDeclarations.size () > 1))
      aSB.append ('\n');

    aSB.append (getSelectorsAsCSSString (eVersion, bOptimizedOutput));
    if (m_aDeclarations.isEmpty ())
      aSB.append (bOptimizedOutput ? "{}" : " {}\n");
    else
      if (m_aSelectors.size () == 1 && m_aDeclarations.size () == 1)
      {
        aSB.append (bOptimizedOutput ? "{" : " { ")
           .append (m_aDeclarations.get (0).getAsCSSString (eVersion, bOptimizedOutput))
           .append (bOptimizedOutput ? "}" : " }\n");
      }
      else
      {
        aSB.append (bOptimizedOutput ? "{" : " {\n");
        for (final CSSDeclaration aDeclaration : m_aDeclarations)
        {
          if (!bOptimizedOutput)
            aSB.append ("  ");
          aSB.append (aDeclaration.getAsCSSString (eVersion, bOptimizedOutput));
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
