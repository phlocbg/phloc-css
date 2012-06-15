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
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single @keyframes rule.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSKeyframesRule implements ICSSTopLevelRule, ICSSVersionAware
{
  private final String m_sDeclaration;
  private final String m_sAnimationName;
  private final List <CSSKeyframesBlock> m_aBlocks = new ArrayList <CSSKeyframesBlock> ();

  public CSSKeyframesRule (@Nonnull @Nonempty final String sDeclaration, @Nonnull @Nonempty final String sAnimationName)
  {
    if (StringHelper.hasNoText (sDeclaration))
      throw new IllegalArgumentException ("declaration");
    m_sDeclaration = sDeclaration;
    m_sAnimationName = sAnimationName;
  }

  @Nonnull
  @Nonempty
  public String getDeclaration ()
  {
    return m_sDeclaration;
  }

  @Nonnull
  @Nonempty
  public String getAnimationName ()
  {
    return m_sAnimationName;
  }

  public void addBlock (@Nonnull final CSSKeyframesBlock aKeyframesBlock)
  {
    if (aKeyframesBlock == null)
      throw new NullPointerException ("keyframesBlock");
    m_aBlocks.add (aKeyframesBlock);
  }

  @Nonnull
  public EChange removeBlock (@Nonnull final CSSKeyframesBlock aKeyframesBlock)
  {
    return EChange.valueOf (m_aBlocks.remove (aKeyframesBlock));
  }

  @Nonnull
  public EChange removeBlock (@Nonnegative final int nBlockIndex)
  {
    if (nBlockIndex < 0 || nBlockIndex >= m_aBlocks.size ())
      return EChange.UNCHANGED;
    return EChange.valueOf (m_aBlocks.remove (nBlockIndex) != null);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSKeyframesBlock> getAllBlocks ()
  {
    return ContainerHelper.newList (m_aBlocks);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder (m_sDeclaration);
    aSB.append (' ').append (m_sAnimationName).append (bOptimizedOutput ? "{" : " {");
    if (!bOptimizedOutput)
      aSB.append ('\n');

    // Add all blocks
    for (final CSSKeyframesBlock aBlock : m_aBlocks)
    {
      if (!bOptimizedOutput)
        aSB.append (aSettings.getIndent (nIndentLevel + 1));
      aSB.append (aBlock.getAsCSSString (aSettings, nIndentLevel + 1));
      if (!bOptimizedOutput)
        aSB.append ('\n');
    }
    if (!bOptimizedOutput)
      aSB.append (aSettings.getIndent (nIndentLevel));
    aSB.append ('}');
    if (!bOptimizedOutput)
      aSB.append ('\n');
    return aSB.toString ();
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSKeyframesRule))
      return false;
    final CSSKeyframesRule rhs = (CSSKeyframesRule) o;
    return m_sDeclaration.equals (rhs.m_sDeclaration) &&
           m_sAnimationName.equals (rhs.m_sAnimationName) &&
           m_aBlocks.equals (rhs.m_aBlocks);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sDeclaration)
                                       .append (m_sAnimationName)
                                       .append (m_aBlocks)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("declaration", m_sDeclaration)
                                       .append ("animationName", m_sAnimationName)
                                       .append ("blocks", m_aBlocks)
                                       .toString ();
  }
}
