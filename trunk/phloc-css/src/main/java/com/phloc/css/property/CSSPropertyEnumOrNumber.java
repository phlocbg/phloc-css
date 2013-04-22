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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.utils.CSSNumberHelper;

/**
 * CSS property that is either an enumeration or a numeric value (e.g.
 * font-size)
 * 
 * @author Philip Helger
 */
public class CSSPropertyEnumOrNumber extends CSSPropertyEnum
{
  private final boolean m_bWithPercentage;

  public CSSPropertyEnumOrNumber (@Nonnull final ECSSProperty eProp,
                                  final boolean bWithPercentage,
                                  @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aEnumValues);
    m_bWithPercentage = bWithPercentage;
  }

  public CSSPropertyEnumOrNumber (@Nonnull final ECSSProperty eProp,
                                  final boolean bWithPercentage,
                                  @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    super (eProp, aEnumValues);
    m_bWithPercentage = bWithPercentage;
  }

  @Override
  public boolean isValidValue (@Nullable final String sValue)
  {
    return super.isValidValue (sValue) || CSSNumberHelper.isValueWithUnit (sValue, m_bWithPercentage);
  }

  @Override
  @Nonnull
  public CSSPropertyEnumOrNumber getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnumOrNumber (eProp, m_bWithPercentage, m_aEnumValues);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("withPercentage", m_bWithPercentage).toString ();
  }
}
