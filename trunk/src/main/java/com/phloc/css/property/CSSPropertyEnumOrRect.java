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

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.utils.CSSRectHelper;

public class CSSPropertyEnumOrRect extends CSSPropertyEnum
{
  public CSSPropertyEnumOrRect (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aEnumValues);
  }

  public CSSPropertyEnumOrRect (@Nonnull final ECSSProperty eProp,
                                @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    super (eProp, aEnumValues);
  }

  @Override
  public boolean isValidValue (@Nullable final String sValue)
  {
    return super.isValidValue (sValue) || CSSRectHelper.isRectValue (sValue);
  }

  @Override
  @Nonnull
  public CSSPropertyEnumOrRect getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnumOrRect (eProp, m_aEnumValues);
  }
}
