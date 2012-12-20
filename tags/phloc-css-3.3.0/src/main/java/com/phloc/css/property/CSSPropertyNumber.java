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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.utils.CSSNumberHelper;

public class CSSPropertyNumber extends AbstractCSSProperty
{
  private final boolean m_bWithPercentage;

  public CSSPropertyNumber (@Nonnull final ECSSProperty eProp, final boolean bWithPercentage)
  {
    super (eProp);
    m_bWithPercentage = bWithPercentage;
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return CSSNumberHelper.isValueWithUnit (sValue, m_bWithPercentage);
  }

  @Nonnull
  public CSSPropertyNumber getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyNumber (eProp, m_bWithPercentage);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("withPercentage", m_bWithPercentage).toString ();
  }
}
