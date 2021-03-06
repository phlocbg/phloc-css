/**
 * Copyright (C) 2006-2015 phloc systems
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
package com.phloc.css.property;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.property.customizer.ICSSPropertyCustomizer;

/**
 * CSS property that is a list of enumeration entries (e.g. border-style)
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSPropertyEnums extends CSSPropertyEnum
{
  private final int m_nMinNumbers;
  private final int m_nMaxNumbers;

  public CSSPropertyEnums (@Nonnull final ECSSProperty eProp,
                           @Nonnegative final int nMinNumbers,
                           @Nonnegative final int nMaxNumbers,
                           @Nonnull @Nonempty final String... aEnumValues)
  {
    this (eProp, null, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnums (@Nonnull final ECSSProperty eProp,
                           @Nullable final ICSSPropertyCustomizer aCustomizer,
                           @Nonnegative final int nMinNumbers,
                           @Nonnegative final int nMaxNumbers,
                           @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aCustomizer, aEnumValues);
    ValueEnforcer.isGT0 (nMinNumbers, "MinNumbers");
    ValueEnforcer.isGT0 (nMaxNumbers, "MaxNumbers");
    if (nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("MaxNumbers (" +
                                          nMaxNumbers +
                                          ") must be >= MinNumbers (" +
                                          nMinNumbers +
                                          ")");
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
  }

  public CSSPropertyEnums (@Nonnull final ECSSProperty eProp,
                           @Nonnegative final int nMinNumbers,
                           @Nonnegative final int nMaxNumbers,
                           @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    this (eProp, null, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnums (@Nonnull final ECSSProperty eProp,
                           @Nullable final ICSSPropertyCustomizer aCustomizer,
                           @Nonnegative final int nMinNumbers,
                           @Nonnegative final int nMaxNumbers,
                           @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    super (eProp, aCustomizer, aEnumValues);
    ValueEnforcer.isGT0 (nMinNumbers, "MinNumbers");
    ValueEnforcer.isGT0 (nMaxNumbers, "MaxNumbers");
    if (nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("MaxNumbers (" +
                                          nMaxNumbers +
                                          ") must be >= MinNumbers (" +
                                          nMinNumbers +
                                          ")");
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
  }

  @Override
  public int getMinimumArgumentCount ()
  {
    return m_nMinNumbers;
  }

  @Override
  public int getMaximumArgumentCount ()
  {
    return m_nMaxNumbers;
  }

  @Override
  @OverridingMethodsMustInvokeSuper
  public boolean isValidValue (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    // Split by whitespaces "  a   b " results in { "a", "b" }
    final String [] aParts = RegExHelper.getSplitToArray (sValue.trim (), "\\s+");
    if (aParts.length < m_nMinNumbers || aParts.length > m_nMaxNumbers)
      return false;

    for (final String sPart : aParts)
      if (!super.isValidValue (sPart.trim ()))
        return false;
    return true;
  }

  @Override
  @Nonnull
  public CSSPropertyEnums getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnums (eProp, getCustomizer (), m_nMinNumbers, m_nMaxNumbers, directGetEnumValues ());
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final CSSPropertyEnums rhs = (CSSPropertyEnums) o;
    return m_nMinNumbers == rhs.m_nMinNumbers && m_nMaxNumbers == rhs.m_nMaxNumbers;
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ())
                            .append (m_nMinNumbers)
                            .append (m_nMaxNumbers)
                            .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("minNumbers", m_nMinNumbers)
                            .append ("maxNumbers", m_nMaxNumbers)
                            .toString ();
  }
}
