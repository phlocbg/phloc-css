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
package com.phloc.css.propertyvalue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.property.ICSSProperty;

/**
 * Represents a CSS value that has several property names, but only one value.
 * This is e.g. if the property <code>border-radius</code> is used, as in this
 * case also <code>-moz-border-radius</code> should be emitted (with the same
 * value).<br>
 * For consistency issues,
 * 
 * @author philip
 */
public final class CSSValueMultiProperty implements ICSSValue
{
  private final List <CSSValue> m_aValues = new ArrayList <CSSValue> ();

  public CSSValueMultiProperty (@Nonnull final ICSSProperty [] aProperties,
                                @Nonnull @Nonempty final String sValue,
                                final boolean bIsImportant)
  {
    if (ArrayHelper.isEmpty (aProperties))
      throw new IllegalArgumentException ("No properties passed!");
    if (sValue == null)
      throw new NullPointerException ("value");

    for (final ICSSProperty aProperty : aProperties)
      m_aValues.add (new CSSValue (aProperty, sValue, bIsImportant));
  }

  @Nonnull
  public ECSSProperty getProp ()
  {
    // ... not necessarily right
    return m_aValues.get (0).getProp ();
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final StringBuilder ret = new StringBuilder ();
    for (final CSSValue aValue : m_aValues)
      ret.append (aValue.getAsCSSString (aSettings, nIndentLevel));
    return ret.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSValueMultiProperty))
      return false;
    final CSSValueMultiProperty rhs = (CSSValueMultiProperty) o;
    return m_aValues.equals (rhs.m_aValues);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aValues).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("values", m_aValues).toString ();
  }
}
