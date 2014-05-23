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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.property.ICSSProperty;
import com.phloc.css.propertyvalue.CSSValueMultiProperty;
import com.phloc.css.propertyvalue.ICSSValue;

/**
 * Special customizer for the "border-bottom-left-radius" property.
 * 
 * @author Philip Helger
 */
@Immutable
public class CSSPropertyCustomizerBorderBottomLeftRadius extends AbstractCSSPropertyCustomizer
{
  @Nullable
  public ICSSValue createSpecialValue (@Nonnull final ICSSProperty aProperty,
                                       @Nonnull @Nonempty final String sValue,
                                       final boolean bIsImportant)
  {
    return new CSSValueMultiProperty (aProperty.getProp (),
                                      new ICSSProperty [] { aProperty,
                                                           aProperty.getClone (ECSSProperty._MOZ_BORDER_RADIUS_BOTTOMLEFT),
                                                           aProperty.getClone (ECSSProperty._WEBKIT_BORDER_BOTTOM_LEFT_RADIUS),
                                                           aProperty.getClone (ECSSProperty._KHTML_BORDER_BOTTOM_LEFT_RADIUS) },
                                      sValue,
                                      bIsImportant);
  }
}
