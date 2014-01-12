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
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single @keyframes rule.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSKeyframesRule implements ICSSTopLevelRule, ICSSVersionAware, ICSSSourceLocationAware
{
  private final String m_sDeclaration;
  private final String m_sAnimationName;
  private final List <CSSKeyframesBlock> m_aBlocks = new ArrayList <CSSKeyframesBlock> ();
  private CSSSourceLocation m_aSourceLocation;

  public static boolean isValidDeclaration (@Nonnull @Nonempty final String sDeclaration)
  {
    return StringHelper.startsWith (sDeclaration, '@') && StringHelper.endsWithIgnoreCase (sDeclaration, "keyframes");
  }

  public CSSKeyframesRule (@Nonnull @Nonempty final String sDeclaration, @Nonnull @Nonempty final String sAnimationName)
  {
    if (!isValidDeclaration (sDeclaration))
      throw new IllegalArgumentException ("declaration");
    m_sDeclaration = sDeclaration;
    m_sAnimationName = sAnimationName;
  }

  /**
   * @return The rule declaration string used in the CSS. Neither
   *         <code>null</code> nor empty. Always starting with <code>@</code>
   *         and ending with <code>keyframes</code>.
   */
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

  public boolean hasBlocks ()
  {
    return !m_aBlocks.isEmpty ();
  }

  @Nonnegative
  public int getBlockCount ()
  {
    return m_aBlocks.size ();
  }

  @Nonnull
  public CSSKeyframesRule addBlock (@Nonnull final CSSKeyframesBlock aKeyframesBlock)
  {
    if (aKeyframesBlock == null)
      throw new NullPointerException ("keyframesBlock");

    m_aBlocks.add (aKeyframesBlock);
    return this;
  }

  @Nonnull
  public CSSKeyframesRule addBlock (@Nonnegative final int nIndex, @Nonnull final CSSKeyframesBlock aKeyframesBlock)
  {
    if (nIndex < 0)
      throw new IllegalArgumentException ("Index too small: " + nIndex);
    if (aKeyframesBlock == null)
      throw new NullPointerException ("keyframesBlock");

    if (nIndex >= getBlockCount ())
      m_aBlocks.add (aKeyframesBlock);
    else
      m_aBlocks.add (nIndex, aKeyframesBlock);
    return this;
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

  @Nullable
  public CSSKeyframesBlock getBlockAtIndex (@Nonnegative final int nBlockIndex)
  {
    if (nBlockIndex < 0 || nBlockIndex >= m_aBlocks.size ())
      return null;
    return m_aBlocks.get (nBlockIndex);
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

    // Always ignore keyframes rules?
    if (!aSettings.isWriteKeyframesRules ())
      return "";

    if (aSettings.isRemoveUnnecessaryCode () && m_aBlocks.isEmpty ())
      return "";

    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder (m_sDeclaration);
    aSB.append (' ').append (m_sAnimationName).append (bOptimizedOutput ? "{" : " {");
    if (!bOptimizedOutput)
      aSB.append ('\n');

    // Add all blocks
    for (final CSSKeyframesBlock aBlock : m_aBlocks)
    {
      final String sBlockCSS = aBlock.getAsCSSString (aSettings, nIndentLevel + 1);
      if (StringHelper.hasText (sBlockCSS))
      {
        if (!bOptimizedOutput)
          aSB.append (aSettings.getIndent (nIndentLevel + 1));
        aSB.append (sBlockCSS);
        if (!bOptimizedOutput)
          aSB.append ('\n');
      }
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
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
