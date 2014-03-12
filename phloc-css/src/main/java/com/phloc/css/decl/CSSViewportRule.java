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

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
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
 * Represents a single <code>@viewport</code> rule.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSViewportRule implements ICSSTopLevelRule, IHasCSSDeclarations, ICSSVersionAware, ICSSSourceLocationAware
{
  private final String m_sDeclaration;
  private final CSSDeclarationContainer m_aDeclarations = new CSSDeclarationContainer ();
  private CSSSourceLocation m_aSourceLocation;

  public static boolean isValidDeclaration (@Nonnull @Nonempty final String sDeclaration)
  {
    return StringHelper.startsWith (sDeclaration, '@') && StringHelper.endsWithIgnoreCase (sDeclaration, "viewport");
  }

  public CSSViewportRule (@Nonnull @Nonempty final String sDeclaration)
  {
    if (!isValidDeclaration (sDeclaration))
      throw new IllegalArgumentException ("declaration");
    m_sDeclaration = sDeclaration;
  }

  /**
   * @return The rule declaration string used in the CSS. Neither
   *         <code>null</code> nor empty. Always starting with <code>@</code>
   *         and ending with <code>viewport</code>.
   */
  @Nonnull
  @Nonempty
  public String getDeclaration ()
  {
    return m_sDeclaration;
  }

  @Nonnull
  public CSSViewportRule addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    m_aDeclarations.addDeclaration (aDeclaration);
    return this;
  }

  @Nonnull
  public CSSViewportRule addDeclaration (@Nonnull @Nonempty final String sProperty,
                                         @Nonnull final CSSExpression aExpression,
                                         final boolean bImportant)
  {
    m_aDeclarations.addDeclaration (sProperty, aExpression, bImportant);
    return this;
  }

  @Nonnull
  public CSSViewportRule addDeclaration (@Nonnegative final int nIndex, @Nonnull final CSSDeclaration aNewDeclaration)
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
  public EChange removeAllDeclarations ()
  {
    return m_aDeclarations.removeAllDeclarations ();
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
  public CSSViewportRule setDeclarationAtIndex (@Nonnegative final int nIndex,
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
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);

    // Always ignore viewport rules?
    if (!aSettings.isWriteViewportRules ())
      return "";

    if (aSettings.isRemoveUnnecessaryCode () && !hasDeclarations ())
      return "";

    final StringBuilder aSB = new StringBuilder (m_sDeclaration);
    aSB.append (m_aDeclarations.getAsCSSString (aSettings, nIndentLevel));
    if (!aSettings.isOptimizedOutput ())
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
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSViewportRule rhs = (CSSViewportRule) o;
    return m_sDeclaration.equals (rhs.m_sDeclaration) && m_aDeclarations.equals (rhs.m_aDeclarations);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sDeclaration).append (m_aDeclarations).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("declaration", m_sDeclaration)
                                       .append ("declarations", m_aDeclarations)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
