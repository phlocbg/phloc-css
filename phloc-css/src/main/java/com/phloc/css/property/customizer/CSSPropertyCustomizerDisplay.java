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
package com.phloc.css.property.customizer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.property.ICSSProperty;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.propertyvalue.CSSValueMultiValue;
import com.phloc.css.propertyvalue.ICSSValue;

/**
 * Special customizer for the "display" property.
 * 
 * @author Philip Helger
 */
public class CSSPropertyCustomizerDisplay implements ICSSPropertyCustomizer
{
  @Nullable
  public ICSSValue createSpecialValue (@Nonnull final ICSSProperty aProperty,
                                       @Nonnull @Nonempty final String sValue,
                                       final boolean bIsImportant)
  {
    if (sValue.equals (CCSSValue.INLINE_BLOCK))
      return new CSSValueMultiValue (aProperty, new String [] { "-moz-inline-block", sValue }, bIsImportant);
    return null;
  }
}
