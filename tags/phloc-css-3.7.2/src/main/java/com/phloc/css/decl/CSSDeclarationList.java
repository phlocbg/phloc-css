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

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CCSS;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a list of {@link CSSDeclaration} objects. This class emits all
 * declarations in a row, without any surrounding block elements.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSDeclarationList implements IHasCSSDeclarations, ICSSSourceLocationAware
{
  private final List <CSSDeclaration> m_aDeclarations = new ArrayList <CSSDeclaration> ();
  private CSSSourceLocation m_aSourceLocation;

  public CSSDeclarationList ()
  {}

  @Nonnull
  public final CSSDeclarationList addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    if (aDeclaration == null)
      throw new NullPointerException ("declaration");

    m_aDeclarations.add (aDeclaration);
    return this;
  }

  @Nonnull
  public final CSSDeclarationList addDeclaration (@Nonnull @Nonempty final String sProperty,
                                                  @Nonnull final CSSExpression aExpression,
                                                  final boolean bImportant)
  {
    return addDeclaration (new CSSDeclaration (sProperty, aExpression, bImportant));
  }

  @Nonnull
  public CSSDeclarationList addDeclaration (@Nonnegative final int nIndex, @Nonnull final CSSDeclaration aNewDeclaration)
  {
    if (nIndex < 0)
      throw new IllegalArgumentException ("index is invalid: " + nIndex);
    if (aNewDeclaration == null)
      throw new NullPointerException ("newDeclaration");

    if (nIndex >= getDeclarationCount ())
      m_aDeclarations.add (aNewDeclaration);
    else
      m_aDeclarations.add (nIndex, aNewDeclaration);
    return this;
  }

  @Nonnull
  public final EChange removeDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    return EChange.valueOf (m_aDeclarations.remove (aDeclaration));
  }

  @Nonnull
  public final EChange removeDeclaration (@Nonnegative final int nDeclarationIndex)
  {
    if (nDeclarationIndex < 0 || nDeclarationIndex >= m_aDeclarations.size ())
      return EChange.UNCHANGED;
    return EChange.valueOf (m_aDeclarations.remove (nDeclarationIndex) != null);
  }

  @Nonnull
  @ReturnsMutableCopy
  public final List <CSSDeclaration> getAllDeclarations ()
  {
    return ContainerHelper.newList (m_aDeclarations);
  }

  @Nullable
  public final CSSDeclaration getDeclarationAtIndex (@Nonnegative final int nIndex)
  {
    return ContainerHelper.getSafe (m_aDeclarations, nIndex);
  }

  @Nonnull
  public CSSDeclarationList setDeclarationAtIndex (@Nonnegative final int nIndex,
                                                   @Nonnull final CSSDeclaration aNewDeclaration)
  {
    if (nIndex < 0)
      throw new IllegalArgumentException ("index is invalid: " + nIndex);
    if (aNewDeclaration == null)
      throw new NullPointerException ("newDeclaration");

    if (nIndex >= getDeclarationCount ())
      m_aDeclarations.add (aNewDeclaration);
    else
      m_aDeclarations.set (nIndex, aNewDeclaration);
    return this;
  }

  public boolean hasDeclarations ()
  {
    return !m_aDeclarations.isEmpty ();
  }

  @Nonnegative
  public final int getDeclarationCount ()
  {
    return m_aDeclarations.size ();
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final int nDeclCount = m_aDeclarations.size ();
    if (nDeclCount == 0)
      return "";
    if (nDeclCount == 1)
    {
      // A single declaration
      final StringBuilder aSB = new StringBuilder ();
      aSB.append (ContainerHelper.getFirstElement (m_aDeclarations).getAsCSSString (aSettings, nIndentLevel));
      // No ';' at the last entry
      if (!bOptimizedOutput)
        aSB.append (CCSS.DEFINITION_END);
      return aSB.toString ();
    }

    // More than one declaration
    final StringBuilder aSB = new StringBuilder ();
    int nIndex = 0;
    for (final CSSDeclaration aDeclaration : m_aDeclarations)
    {
      // Indentation
      if (!bOptimizedOutput)
        aSB.append (aSettings.getIndent (nIndentLevel + 1));
      // Emit the main declaration plus the semicolon
      aSB.append (aDeclaration.getAsCSSString (aSettings, nIndentLevel + 1));
      // No ';' at the last decl
      if (!bOptimizedOutput || nIndex < nDeclCount - 1)
        aSB.append (CCSS.DEFINITION_END);
      if (!bOptimizedOutput)
        aSB.append ('\n');
      ++nIndex;
    }
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
    if (!(o instanceof CSSDeclarationList))
      return false;
    final CSSDeclarationList rhs = (CSSDeclarationList) o;
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
    return new ToStringGenerator (this).append ("declarations", m_aDeclarations)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
