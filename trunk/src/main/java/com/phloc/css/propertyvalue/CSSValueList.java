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

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSProperty;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.property.ICSSProperty;

/**
 * Represents a CSS value that has both different property names and multiple
 * different values. This is e.g. if the property <code>display</code> is used
 * with the value <code>inline-block</code> than the result coding should first
 * emit <code>display:-moz-inline-block;</code> and them
 * <code>display:inline-block;</code> for FireFox 2.x specific support.
 * 
 * @author philip
 */
public final class CSSValueList implements ICSSValue
{
  private final List <CSSValue> m_aValues = new ArrayList <CSSValue> ();

  public CSSValueList (@Nonnull final ICSSProperty [] aProperties,
                       @Nonnull final String [] aValues,
                       final boolean bIsImportant)
  {
    if (aProperties == null || aProperties.length == 0)
      throw new IllegalArgumentException ("No properties passed!");
    if (aValues == null || aValues.length == 0)
      throw new IllegalArgumentException ("No value passed!");
    if (aProperties.length != aValues.length)
      throw new IllegalArgumentException ("Different number of properties and values passed");

    for (int i = 0; i < aProperties.length; ++i)
      m_aValues.add (new CSSValue (aProperties[i], aValues[i], bIsImportant));
  }

  @Nonnull
  public ECSSProperty getProp ()
  {
    if (m_aValues.isEmpty ())
      throw new IllegalStateException ("No value present to determine the property from!");
    return ContainerHelper.getLastElement (m_aValues).getProp ();
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
    if (!(o instanceof CSSValueList))
      return false;
    final CSSValueList rhs = (CSSValueList) o;
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
