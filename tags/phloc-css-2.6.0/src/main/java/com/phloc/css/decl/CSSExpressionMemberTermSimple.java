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
package com.phloc.css.decl;

import java.util.Arrays;

import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSUnit;
import com.phloc.css.ECSSVersion;

@NotThreadSafe
public final class CSSExpressionMemberTermSimple implements ICSSExpressionMember
{
  private String m_sValue;

  public CSSExpressionMemberTermSimple (final String sValue)
  {
    setValue (sValue);
  }

  public void setValue (final String sValue)
  {
    if (StringHelper.hasNoText (sValue))
      throw new IllegalArgumentException ("Empty value is not allowed");
    m_sValue = sValue;
  }

  public String getValue ()
  {
    return m_sValue;
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (false && bOptimizedOutput)
    {
      // Replace e.g. "0px" with "0"
      for (final ECSSUnit eUnit : ECSSUnit.values ())
        if (m_sValue.equals (eUnit.format (0)))
          return "0";

      // Replace e.g. "#ffffff" with "#fff"
      final char [] cLong = new char [7];
      cLong[0] = '#';
      for (final char c : new char [] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' })
      {
        Arrays.fill (cLong, 1, 6, c);
        if (m_sValue.equalsIgnoreCase (new String (cLong)))
          return new String (cLong, 0, 4);
      }
    }
    return m_sValue;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSExpressionMemberTermSimple))
      return false;
    final CSSExpressionMemberTermSimple rhs = (CSSExpressionMemberTermSimple) o;
    return m_sValue.equals (rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("value", m_sValue).toString ();
  }
}