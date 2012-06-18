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
package com.phloc.css.property;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.utils.CSSNumberHelper;

public class CSSPropertyEnumOrNumbers extends CSSPropertyEnum
{
  private final boolean m_bWithPercentage;
  private final int m_nMinNumbers;
  private final int m_nMaxNumbers;

  public CSSPropertyEnumOrNumbers (@Nonnull final ECSSProperty eProp,
                                   final boolean bWithPercentage,
                                   @Nonnegative final int nMinNumbers,
                                   @Nonnegative final int nMaxNumbers,
                                   @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aEnumValues);
    if (nMinNumbers < 0)
      throw new IllegalArgumentException ("minNumbers: " + nMinNumbers);
    if (nMaxNumbers < 0 || nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("maxNumbers: " + nMaxNumbers);
    m_bWithPercentage = bWithPercentage;
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
  }

  public CSSPropertyEnumOrNumbers (@Nonnull final ECSSProperty eProp,
                                   final boolean bWithPercentage,
                                   @Nonnegative final int nMinNumbers,
                                   @Nonnegative final int nMaxNumbers,
                                   @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    super (eProp, aEnumValues);
    if (nMinNumbers < 0)
      throw new IllegalArgumentException ("minNumbers: " + nMinNumbers);
    if (nMaxNumbers < 0 || nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("maxNumbers: " + nMaxNumbers);
    m_bWithPercentage = bWithPercentage;
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
  }

  @Override
  public boolean isValidValue (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;
    final String [] aParts = RegExHelper.split (sValue.trim (), "\\s+");
    if (aParts.length < m_nMinNumbers || aParts.length > m_nMaxNumbers)
      return false;
    for (int i = 0; i < aParts.length; ++i)
    {
      aParts[i] = aParts[i].trim ();
      if (!super.isValidValue (aParts[i]) && !CSSNumberHelper.isNumberWithUnitValue (aParts[i], m_bWithPercentage))
        return false;
    }
    return true;
  }

  @Override
  @Nonnull
  public CSSPropertyEnumOrNumbers getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnumOrNumbers (eProp, m_bWithPercentage, m_nMinNumbers, m_nMaxNumbers, m_aEnumValues);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("withPercentage", m_bWithPercentage)
                            .append ("minNumbers", m_nMinNumbers)
                            .append ("maxNumbers", m_nMaxNumbers)
                            .toString ();
  }
}
