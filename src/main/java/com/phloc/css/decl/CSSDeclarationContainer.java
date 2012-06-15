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
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a common container of {@link CSSDeclaration} objects.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSDeclarationContainer implements IHasCSSDeclarations
{
  private final List <CSSDeclaration> m_aDeclarations = new ArrayList <CSSDeclaration> ();

  public CSSDeclarationContainer ()
  {}

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
    return EChange.valueOf (m_aDeclarations.remove (nDeclarationIndex) != null);
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
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    if (nIndentLevel < 1)
      throw new NullPointerException ("This cannot be on top level!");

    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder ();
    final int nDeclCount = m_aDeclarations.size ();
    if (nDeclCount == 0)
    {
      // Skip the whole rule
      // Leads to different results in comparison!
      if (false && bOptimizedOutput)
        return "";
      aSB.append (bOptimizedOutput ? "{}" : " {}");
    }
    else
    {
      if (nDeclCount == 1)
      {
        // A single declaration
        aSB.append (bOptimizedOutput ? "{" : " { ");
        aSB.append (ContainerHelper.getFirstElement (m_aDeclarations).getAsCSSString (aSettings, nIndentLevel));
        aSB.append (bOptimizedOutput ? "}" : " }");
      }
      else
      {
        // More than one declaration
        aSB.append (bOptimizedOutput ? "{" : " {\n");
        for (final CSSDeclaration aDeclaration : m_aDeclarations)
        {
          // Indentation
          if (!bOptimizedOutput)
            aSB.append (aSettings.getIndent (nIndentLevel));
          // Emit the main declaration
          aSB.append (aDeclaration.getAsCSSString (aSettings, nIndentLevel + 1));
          if (!bOptimizedOutput)
            aSB.append ('\n');
        }
        if (!bOptimizedOutput)
          aSB.append (aSettings.getIndent (nIndentLevel - 1));
        aSB.append ('}');
      }
    }
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSDeclarationContainer))
      return false;
    final CSSDeclarationContainer rhs = (CSSDeclarationContainer) o;
    return m_aDeclarations.equals (rhs.m_aDeclarations);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aDeclarations).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("declarations", m_aDeclarations).toString ();
  }
}
