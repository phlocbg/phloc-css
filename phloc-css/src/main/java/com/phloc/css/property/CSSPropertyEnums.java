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
package com.phloc.css.property;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.property.customizer.ICSSPropertyCustomizer;

/**
 * CSS property that is a list of enumeration entries (e.g. border-style)
 * 
 * @author Philip Helger
 */
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
    if (nMinNumbers < 0)
      throw new IllegalArgumentException ("minNumbers: " + nMinNumbers);
    if (nMaxNumbers < 0 || nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("maxNumbers: " + nMaxNumbers);
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
    if (nMinNumbers < 0)
      throw new IllegalArgumentException ("minNumbers: " + nMinNumbers);
    if (nMaxNumbers < 0 || nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("maxNumbers: " + nMaxNumbers);
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
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
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("minNumbers", m_nMinNumbers)
                            .append ("maxNumbers", m_nMaxNumbers)
                            .toString ();
  }
}
