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

import com.phloc.commons.string.StringHelper;

public class CSSPropertyFree extends AbstractCSSProperty
{
  public CSSPropertyFree (@Nonnull final ECSSProperty eProp)
  {
    super (eProp);
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return StringHelper.hasText (sValue);
  }

  @Nonnull
  public CSSPropertyFree getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyFree (eProp);
  }
}
