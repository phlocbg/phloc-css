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

import java.io.Serializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.property.customizer.ICSSPropertyCustomizer;
import com.phloc.css.propertyvalue.ICSSValue;
import com.phloc.css.utils.ICSSNamedColor;

/**
 * Base interface for a single CSS property.
 * 
 * @see com.phloc.css.property.CCSSProperties CCSSProperties for a list of
 *      default CSS properties
 * @author Philip Helger
 */
public interface ICSSProperty extends ICSSVersionAware, Serializable
{
  /**
   * @return The underlying base property. Never <code>null</code>.
   */
  @Nonnull
  ECSSProperty getProp ();

  /**
   * @return The CSS customizer assigned to this property. May be
   *         <code>null</code>.
   */
  @Nullable
  ICSSPropertyCustomizer getCustomizer ();

  /**
   * @return The minimum number of arguments for this property.
   * @since 3.7.4
   */
  @Nonnegative
  int getMinimumArgumentCount ();

  /**
   * @return The maximum number of arguments for this property.
   * @since 3.7.4
   */
  @Nonnegative
  int getMaximumArgumentCount ();

  /**
   * Check if the passed value is valid for this property according to the
   * defined rule.
   * 
   * @param sValue
   *        The value to check. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise
   */
  boolean isValidValue (@Nullable String sValue);

  /**
   * Create a new CSS value with this property and the specified value.
   * 
   * @param sValue
   *        The CSS String value. May neither be <code>null</code> nor empty.
   * @param bImportant
   *        <code>true</code> if it is an important value, <code>false</code> if
   *        not
   * @return Never <code>null</code>.
   */
  @Nonnull
  ICSSValue newValue (@Nonnull @Nonempty String sValue, boolean bImportant);

  /**
   * Create a new CSS value with this property and the specified value. This is
   * a shortcut for <code>newValue (sValue, false)</code>.
   * 
   * @param sValue
   *        The CSS String value. May neither be <code>null</code> nor empty.
   * @return Never <code>null</code>.
   */
  @Nonnull
  ICSSValue newValue (@Nonnull @Nonempty String sValue);

  /**
   * Create a new important CSS value with this property and the specified
   * value. This is a shortcut for <code>newValue (sValue, true)</code>.
   * 
   * @param sValue
   *        The CSS String value. May neither be <code>null</code> nor empty.
   * @return Never <code>null</code>.
   */
  @Nonnull
  ICSSValue newImportantValue (@Nonnull @Nonempty String sValue);

  /**
   * Create a new CSS value with this property and the specified named color.
   * 
   * @param aColor
   *        The CSS color value
   * @param bImportant
   *        <code>true</code> if it is an important value, <code>false</code> if
   *        not
   * @return Never <code>null</code>.
   */
  @Nonnull
  ICSSValue newValue (@Nonnull ICSSNamedColor aColor, boolean bImportant);

  /**
   * Create a new CSS value with this property and the specified named color.
   * This is a shortcut for <code>newValue (aColor, false)</code>.
   * 
   * @param aColor
   *        The CSS color value
   * @return Never <code>null</code>.
   */
  @Nonnull
  ICSSValue newValue (@Nonnull ICSSNamedColor aColor);

  /**
   * Create a new important CSS value with this property and the specified named
   * color. This is a shortcut for <code>newValue (aColor, true)</code>.
   * 
   * @param aColor
   *        The CSS color value
   * @return Never <code>null</code>.
   */
  @Nonnull
  ICSSValue newImportantValue (@Nonnull ICSSNamedColor aColor);

  /**
   * Get a clone of this property with another (or the same) base property.
   * 
   * @param eProp
   *        The base property to use. May not be <code>null</code>.
   * @return Never <code>null</code>
   */
  @Nonnull
  ICSSProperty getClone (@Nonnull ECSSProperty eProp);
}
