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
package com.phloc.css.property;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.utils.CSSNumberHelper;

/**
 * CSS property with a list of numbers (e.g. padding)
 * 
 * @author Philip Helger
 */
public class CSSPropertyNumbers extends AbstractCSSProperty
{
  private final boolean m_bWithPercentage;
  private final int m_nMinNumbers;
  private final int m_nMaxNumbers;

  public CSSPropertyNumbers (@Nonnull final ECSSProperty eProp,
                             final boolean bWithPercentage,
                             @Nonnegative final int nMinNumbers,
                             @Nonnegative final int nMaxNumbers)
  {
    super (eProp);
    if (nMinNumbers < 0)
      throw new IllegalArgumentException ("minNumbers: " + nMinNumbers);
    if (nMaxNumbers < 0 || nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("maxNumbers: " + nMaxNumbers);
    m_bWithPercentage = bWithPercentage;
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
  }

  @Override
  @OverridingMethodsMustInvokeSuper
  public boolean isValidValue (@Nullable final String sValue)
  {
    if (super.isValidValue (sValue))
      return true;

    if (sValue == null)
      return false;

    // Split by whitespaces
    final String [] aParts = RegExHelper.getSplitToArray (sValue.trim (), "\\s+");
    if (aParts.length < m_nMinNumbers || aParts.length > m_nMaxNumbers)
      return false;

    // Check if each part is a valid number
    for (int i = 0; i < aParts.length; ++i)
      if (!CSSNumberHelper.isValueWithUnit (aParts[i].trim (), m_bWithPercentage))
        return false;
    return true;
  }

  @Nonnull
  public CSSPropertyNumbers getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyNumbers (eProp, m_bWithPercentage, m_nMinNumbers, m_nMaxNumbers);
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
