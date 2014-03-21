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
package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.property.customizer.ICSSPropertyCustomizer;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.propertyvalue.CSSValue;
import com.phloc.css.propertyvalue.ICSSValue;
import com.phloc.css.utils.ICSSNamedColor;

/**
 * Abstract base class for implementing {@link ICSSProperty}
 *
 * @author Philip Helger
 */
@NotThreadSafe
public abstract class AbstractCSSProperty implements ICSSProperty
{
  private final ECSSProperty m_eProp;
  private final ICSSPropertyCustomizer m_aCustomizer;

  /**
   * Constructor
   *
   * @param eProp
   *        The base property to use. May not be <code>null</code>.
   * @param aCustomizer
   *        The customizer to be used. May be <code>null</code>.
   */
  protected AbstractCSSProperty (@Nonnull final ECSSProperty eProp, @Nullable final ICSSPropertyCustomizer aCustomizer)
  {
    m_eProp = ValueEnforcer.notNull (eProp, "Property");
    m_aCustomizer = aCustomizer;
  }

  @Nonnull
  public final ECSSVersion getMinimumCSSVersion ()
  {
    return m_eProp.getMinimumCSSVersion ();
  }

  @Nonnull
  public final ECSSProperty getProp ()
  {
    return m_eProp;
  }

  @Nullable
  public final ICSSPropertyCustomizer getCustomizer ()
  {
    return m_aCustomizer;
  }

  public static boolean isValidPropertyValue (@Nullable final String sValue)
  {
    // "inherit" and "initial" is valid for all values in CSS 3.0
    return CCSSValue.INHERIT.equals (sValue) || CCSSValue.INITIAL.equals (sValue);
  }

  @OverridingMethodsMustInvokeSuper
  public boolean isValidValue (@Nullable final String sValue)
  {
    return isValidPropertyValue (sValue);
  }

  @Nonnull
  public ICSSValue newValue (@Nonnull @Nonempty final String sValue, final boolean bIsImportant)
  {
    if (StringHelper.hasNoText (sValue))
      throw new NullPointerException ("value");

    // Special handling for browser specific value creation
    if (m_aCustomizer != null)
    {
      final ICSSValue aCustomizedValue = m_aCustomizer.createSpecialValue (this, sValue, bIsImportant);
      if (aCustomizedValue != null)
        return aCustomizedValue;
    }

    return new CSSValue (this, sValue, bIsImportant);
  }

  @Nonnull
  public final ICSSValue newValue (@Nonnull @Nonempty final String sValue)
  {
    return newValue (sValue, false);
  }

  @Nonnull
  public final ICSSValue newImportantValue (@Nonnull @Nonempty final String sValue)
  {
    return newValue (sValue, true);
  }

  @Nonnull
  public final ICSSValue newValue (@Nonnull final ICSSNamedColor aColor, final boolean bImportant)
  {
    return newValue (aColor.getName (), bImportant);
  }

  @Nonnull
  public final ICSSValue newValue (@Nonnull final ICSSNamedColor aColor)
  {
    return newValue (aColor.getName ());
  }

  @Nonnull
  public final ICSSValue newImportantValue (@Nonnull final ICSSNamedColor aColor)
  {
    return newImportantValue (aColor.getName ());
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final AbstractCSSProperty rhs = (AbstractCSSProperty) o;
    return m_eProp.equals (rhs.m_eProp);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eProp).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("prop", m_eProp).toString ();
  }
}
