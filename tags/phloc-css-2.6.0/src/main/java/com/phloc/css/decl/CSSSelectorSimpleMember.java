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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

@Immutable
public final class CSSSelectorSimpleMember implements ICSSSelectorMember
{
  private final String m_sValue;

  public CSSSelectorSimpleMember (@Nonnull @Nonempty final String sValue)
  {
    if (StringHelper.hasNoText (sValue))
      throw new IllegalArgumentException ("empty value is not allowed");
    m_sValue = sValue;
  }

  @Nonnull
  @Nonempty
  public String getValue ()
  {
    return m_sValue;
  }

  public boolean isElementName ()
  {
    return !isHash () && !isClass () && !isPseudo ();
  }

  public boolean isHash ()
  {
    return m_sValue.charAt (0) == '#';
  }

  public boolean isClass ()
  {
    return m_sValue.charAt (0) == '.';
  }

  public boolean isPseudo ()
  {
    return m_sValue.charAt (0) == ':';
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    return m_sValue;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSelectorSimpleMember))
      return false;
    final CSSSelectorSimpleMember rhs = (CSSSelectorSimpleMember) o;
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
