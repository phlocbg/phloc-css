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

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single <code>@page</code> rule.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSPageRule implements ICSSTopLevelRule, IHasCSSDeclarations, ICSSVersionAware
{
  private final String m_sPseudoPage;
  private final List <CSSDeclaration> m_aDeclarations = new ArrayList <CSSDeclaration> ();

  public CSSPageRule (@Nullable final String sPseudoPage)
  {
    m_sPseudoPage = sPseudoPage;
  }

  @Nullable
  public String getPseudoPage ()
  {
    return m_sPseudoPage;
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
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings)
  {
    aSettings.checkVersionRequirements (this);
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder ("@page");

    if (StringHelper.hasText (m_sPseudoPage))
      aSB.append (' ').append (m_sPseudoPage);

    final int nDeclCount = m_aDeclarations.size ();
    if (nDeclCount == 0)
    {
      // Skip the whole rule
      if (false && bOptimizedOutput)
        return "";
      aSB.append (bOptimizedOutput ? "{}\n" : " {}\n");
    }
    else
    {
      if (nDeclCount == 1)
      {
        // A single declaration
        aSB.append (bOptimizedOutput ? "{" : " { ");
        aSB.append (ContainerHelper.getFirstElement (m_aDeclarations).getAsCSSString (aSettings));
        aSB.append (bOptimizedOutput ? "}" : " }\n");
      }
      else
      {
        // More than one declaration
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
    }
    return aSB.toString ();
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS21;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSPageRule))
      return false;
    final CSSPageRule rhs = (CSSPageRule) o;
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
