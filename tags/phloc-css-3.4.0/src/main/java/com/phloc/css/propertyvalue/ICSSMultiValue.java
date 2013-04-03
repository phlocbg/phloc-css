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
package com.phloc.css.propertyvalue;

import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.MustImplementEqualsAndHashcode;
import com.phloc.commons.annotations.ReturnsMutableCopy;

/**
 * Base interface for a CSS value that contains more than one value
 * 
 * @author philip
 */
@MustImplementEqualsAndHashcode
public interface ICSSMultiValue extends ICSSValue
{
  /**
   * @return The contained CSS values. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  List <CSSValue> getContainedValues ();
}
