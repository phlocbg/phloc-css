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

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single media query
 * 
 * @author philip
 */
public final class CSSMediaQuery implements ICSSWriteable
{
  public static enum EModifier
  {
    NONE (""),
    NOT ("not "),
    ONLY ("only ");

    private final String m_sText;

    private EModifier (@Nonnull final String sText)
    {
      m_sText = sText;
    }

    @Nonnull
    public String getCSSText ()
    {
      return m_sText;
    }
  }

  private final EModifier m_eModifier;
  private final String m_sMedium;
  private final List <CSSMediaExpression> m_aMediaExpressions = new ArrayList <CSSMediaExpression> ();

  public CSSMediaQuery (@Nonnull final EModifier eModifier, @Nullable final String sMedium)
  {
    if (eModifier == null)
      throw new NullPointerException ("modifier");
    m_eModifier = eModifier;
    m_sMedium = sMedium;
  }

  public boolean isNot ()
  {
    return m_eModifier == EModifier.NOT;
  }

  public boolean isOnly ()
  {
    return m_eModifier == EModifier.ONLY;
  }

  @Nullable
  public String getMedium ()
  {
    return m_sMedium;
  }

  public boolean hasMediaExpressions ()
  {
    return !m_aMediaExpressions.isEmpty ();
  }

  @Nonnegative
  public int getMediaExpressionCount ()
  {
    return m_aMediaExpressions.size ();
  }

  public void addMediaExpression (@Nonnull final CSSMediaExpression aMediaExpression)
  {
    if (aMediaExpression == null)
      throw new NullPointerException ("expression");
    m_aMediaExpressions.add (aMediaExpression);
  }

  @Nonnull
  public EChange removeMediaExpression (@Nonnull final CSSMediaExpression aMediaExpression)
  {
    return EChange.valueOf (m_aMediaExpressions.remove (aMediaExpression));
  }

  @Nonnull
  public EChange removeMediaExpression (@Nonnegative final int nExpressionIndex)
  {
    if (nExpressionIndex < 0 || nExpressionIndex >= m_aMediaExpressions.size ())
      return EChange.UNCHANGED;
    return EChange.valueOf (m_aMediaExpressions.remove (nExpressionIndex) != null);
  }

  @Nullable
  public CSSMediaExpression getMediaExpression (@Nonnegative final int nExpressionIndex)
  {
    if (nExpressionIndex < 0 || nExpressionIndex >= m_aMediaExpressions.size ())
      return null;
    return m_aMediaExpressions.get (nExpressionIndex);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSMediaExpression> getMediaExpressions ()
  {
    return ContainerHelper.newList (m_aMediaExpressions);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final StringBuilder aSB = new StringBuilder ();

    // The modifier already contains a trailing space if necessary!
    aSB.append (m_eModifier.getCSSText ());

    boolean bIsFirstExpression = true;
    if (m_sMedium != null)
    {
      // Medium is optional
      aSB.append (m_sMedium);
      bIsFirstExpression = false;
    }

    if (!m_aMediaExpressions.isEmpty ())
    {
      for (final CSSMediaExpression aMediaExpression : m_aMediaExpressions)
      {
        if (bIsFirstExpression)
          bIsFirstExpression = false;
        else
        {
          // The leading blank is required in case this is the first expression
          // after a medium declaration ("projectorand" instead of
          // "projector and")!
          // The trailing blank is required, because otherwise it is considered
          // a function ("and(")!
          aSB.append (" and ");
        }
        aSB.append (aMediaExpression.getAsCSSString (aSettings, nIndentLevel));
      }
    }

    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaQuery))
      return false;
    final CSSMediaQuery rhs = (CSSMediaQuery) o;
    return m_eModifier.equals (rhs.m_eModifier) &&
           EqualsUtils.equals (m_sMedium, rhs.m_sMedium) &&
           m_aMediaExpressions.equals (rhs.m_aMediaExpressions);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eModifier)
                                       .append (m_sMedium)
                                       .append (m_aMediaExpressions)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("modifier", m_eModifier)
                                       .append ("medium", m_sMedium)
                                       .append ("expressions", m_aMediaExpressions)
                                       .toString ();
  }
}
