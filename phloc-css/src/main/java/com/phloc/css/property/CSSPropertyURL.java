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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.css.property.customizer.ICSSPropertyCustomizer;
import com.phloc.css.utils.CSSURLHelper;

/**
 * CSS property that needs to be an URL
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSPropertyURL extends AbstractCSSProperty
{
  public CSSPropertyURL (@Nonnull final ECSSProperty eProp)
  {
    this (eProp, null);
  }

  public CSSPropertyURL (@Nonnull final ECSSProperty eProp, @Nullable final ICSSPropertyCustomizer aCustomizer)
  {
    super (eProp, aCustomizer);
  }

  public static boolean isValidPropertyValue (@Nullable final String sValue)
  {
    return AbstractCSSProperty.isValidPropertyValue (sValue) || CSSURLHelper.isURLValue (sValue);
  }

  @Override
  @OverridingMethodsMustInvokeSuper
  public boolean isValidValue (@Nullable final String sValue)
  {
    return isValidPropertyValue (sValue);
  }

  @Nonnull
  public CSSPropertyURL getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyURL (eProp, getCustomizer ());
  }
}
