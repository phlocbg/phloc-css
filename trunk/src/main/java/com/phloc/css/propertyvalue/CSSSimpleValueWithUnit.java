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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSUnit;

/**
 * This class encapsulates a single numeric value and a unit.
 * 
 * @author philip
 */
@Immutable
public final class CSSSimpleValueWithUnit
{
  private final double m_dValue;
  private final ECSSUnit m_eUnit;

  public CSSSimpleValueWithUnit (final double dValue, @Nonnull final ECSSUnit eUnit)
  {
    if (eUnit == null)
      throw new NullPointerException ("unit");
    m_dValue = dValue;
    m_eUnit = eUnit;
  }

  public double getValue ()
  {
    return m_dValue;
  }

  public int getAsIntValue ()
  {
    return (int) m_dValue;
  }

  public long getAsLongValue ()
  {
    return (long) m_dValue;
  }

  @Nonnull
  public ECSSUnit getUnit ()
  {
    return m_eUnit;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSimpleValueWithUnit))
      return false;
    final CSSSimpleValueWithUnit rhs = (CSSSimpleValueWithUnit) o;
    return EqualsUtils.equals (m_dValue, rhs.m_dValue) && m_eUnit.equals (rhs.m_eUnit);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_dValue).append (m_eUnit).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("value", m_dValue).append ("unit", m_eUnit).toString ();
  }
}
