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

import com.phloc.css.ICSSVersionAware;
import com.phloc.css.propertyvalue.ICSSValue;
import com.phloc.css.utils.ICSSNamedColor;

/**
 * Base interface for a single CSS property.
 * 
 * @see com.phloc.css.property.CCSSProperties for a list of default CSS
 *      properties
 * @author philip
 */
public interface ICSSProperty extends ICSSVersionAware
{
  @Nonnull
  ECSSProperty getProp ();

  boolean isValidValue (@Nullable String sValue);

  @Nonnull
  ICSSValue newValue (@Nonnull String sValue);

  @Nonnull
  ICSSValue newImportantValue (@Nonnull String sValue);

  @Nonnull
  ICSSValue newValue (@Nonnull ICSSNamedColor aColor);

  @Nonnull
  ICSSValue newImportantValue (@Nonnull ICSSNamedColor aColor);

  @Nonnull
  ICSSProperty getClone (@Nonnull ECSSProperty eProp);
}
