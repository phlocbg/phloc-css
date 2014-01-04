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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single <code>@</code> rule that is non-standard and/or
 * unhandled.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSUnknownRule implements ICSSTopLevelRule, ICSSSourceLocationAware
{
  private final String m_sDeclaration;
  private CSSSourceLocation m_aSourceLocation;

  public static boolean isValidDeclaration (@Nonnull @Nonempty final String sDeclaration)
  {
    return StringHelper.startsWith (sDeclaration, '@');
  }

  public CSSUnknownRule (@Nonnull @Nonempty final String sDeclaration)
  {
    if (!isValidDeclaration (sDeclaration))
      throw new IllegalArgumentException ("declaration");
    m_sDeclaration = sDeclaration;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    // Always ignore unknown rules?
    if (!aSettings.isWriteUnknownRules ())
      return "";

    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();

    final StringBuilder aSB = new StringBuilder (m_sDeclaration);
    // TODO
    aSB.append (bOptimizedOutput ? "{" : " {\n");
    // TODO
    aSB.append ('}');
    if (!aSettings.isOptimizedOutput ())
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
    if (!(o instanceof CSSUnknownRule))
      return false;
    final CSSUnknownRule rhs = (CSSUnknownRule) o;
    return m_sDeclaration.equals (rhs.m_sDeclaration);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sDeclaration).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("declaration", m_sDeclaration)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
