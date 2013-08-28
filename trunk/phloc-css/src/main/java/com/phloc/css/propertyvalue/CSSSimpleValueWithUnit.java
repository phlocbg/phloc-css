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

import java.io.Serializable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSUnit;

/**
 * This class encapsulates a single numeric value and a unit.
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSSimpleValueWithUnit implements Serializable
{
  private final double m_dValue;
  private final ECSSUnit m_eUnit;

  /**
   * Constructor
   * 
   * @param dValue
   *        Numeric value
   * @param eUnit
   *        CSS unit to use. May not be <code>null</code>.
   */
  public CSSSimpleValueWithUnit (final double dValue, @Nonnull final ECSSUnit eUnit)
  {
    if (eUnit == null)
      throw new NullPointerException ("unit");
    m_dValue = dValue;
    m_eUnit = eUnit;
  }

  /**
   * @return The numeric value as a decimal value
   */
  public double getValue ()
  {
    return m_dValue;
  }

  /**
   * @return The numeric value as an int value - no check for validity is
   *         performed
   */
  public int getAsIntValue ()
  {
    return (int) m_dValue;
  }

  /**
   * @return The numeric value as a long value - no check for validity is
   *         performed
   */
  public long getAsLongValue ()
  {
    return (long) m_dValue;
  }

  /**
   * @return The CSS unit of this value. Never <code>null</code>.
   */
  @Nonnull
  public ECSSUnit getUnit ()
  {
    return m_eUnit;
  }

  /**
   * Get a new object with the same unit but an added value.
   * 
   * @param dDelta
   *        The delta to be added.
   * @return A new object. Never <code>null</code>.
   */
  @Nonnull
  @CheckReturnValue
  public CSSSimpleValueWithUnit add (final double dDelta)
  {
    return new CSSSimpleValueWithUnit (m_dValue + dDelta, m_eUnit);
  }

  /**
   * Get a new object with the same unit but a subtracted value.
   * 
   * @param dDelta
   *        The delta to be subtracted.
   * @return A new object. Never <code>null</code>.
   */
  @Nonnull
  @CheckReturnValue
  public CSSSimpleValueWithUnit substract (final double dDelta)
  {
    return new CSSSimpleValueWithUnit (m_dValue - dDelta, m_eUnit);
  }

  /**
   * Get a new object with the same unit but a multiplied value.
   * 
   * @param dValue
   *        The value to be multiply with this value.
   * @return A new object. Never <code>null</code>.
   */
  @Nonnull
  @CheckReturnValue
  public CSSSimpleValueWithUnit multiply (final double dValue)
  {
    return new CSSSimpleValueWithUnit (m_dValue * dValue, m_eUnit);
  }

  /**
   * Get a new object with the same unit but an divided value.
   * 
   * @param dDivisor
   *        The divisor to use.
   * @return A new object. Never <code>null</code>.
   */
  @Nonnull
  @CheckReturnValue
  public CSSSimpleValueWithUnit divide (final double dDivisor)
  {
    return new CSSSimpleValueWithUnit (m_dValue / dDivisor, m_eUnit);
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
