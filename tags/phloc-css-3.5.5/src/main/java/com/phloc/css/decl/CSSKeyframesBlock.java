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
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * keyframes block
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSKeyframesBlock implements IHasCSSDeclarations, ICSSVersionAware, ICSSSourceLocationAware
{
  private final List <String> m_aKeyframesSelectors;
  private final CSSDeclarationContainer m_aDeclarations = new CSSDeclarationContainer ();
  private CSSSourceLocation m_aSourceLocation;

  public CSSKeyframesBlock (@Nonnull @Nonempty final List <String> aKeyframesSelectors)
  {
    if (ContainerHelper.isEmpty (aKeyframesSelectors))
      throw new IllegalArgumentException ("keyframesSelectors");
    if (ContainerHelper.containsAnyNullElement (aKeyframesSelectors))
      throw new IllegalArgumentException ("keyframesSelectors contains at least one null element");
    m_aKeyframesSelectors = ContainerHelper.newList (aKeyframesSelectors);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <String> getAllKeyframesSelectors ()
  {
    return ContainerHelper.newList (m_aKeyframesSelectors);
  }

  public void addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    m_aDeclarations.addDeclaration (aDeclaration);
  }

  public void addDeclaration (@Nonnegative final int nIndex, @Nonnull final CSSDeclaration aNewDeclaration)
  {
    m_aDeclarations.addDeclaration (nIndex, aNewDeclaration);
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

  public void setDeclarationAtIndex (@Nonnegative final int nIndex, @Nonnull final CSSDeclaration aNewDeclaration)
  {
    m_aDeclarations.setDeclarationAtIndex (nIndex, aNewDeclaration);
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
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);

    if (aSettings.isRemoveUnnecessaryCode () && !hasDeclarations ())
      return "";

    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder ();

    // Emit all selectors
    for (final String sSelector : m_aKeyframesSelectors)
    {
      if (aSB.length () > 0)
        aSB.append (bOptimizedOutput ? "," : ", ");
      aSB.append (sSelector);
    }

    aSB.append (m_aDeclarations.getAsCSSString (aSettings, nIndentLevel));
    return aSB.toString ();
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
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
    if (!(o instanceof CSSKeyframesBlock))
      return false;
    final CSSKeyframesBlock rhs = (CSSKeyframesBlock) o;
    return m_aKeyframesSelectors.equals (rhs.m_aKeyframesSelectors) && m_aDeclarations.equals (rhs.m_aDeclarations);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aKeyframesSelectors).append (m_aDeclarations).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("keyframesSelectors", m_aKeyframesSelectors)
                                       .append ("declarations", m_aDeclarations)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
