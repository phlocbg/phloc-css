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
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

/**
 * A single CSS selector attribute.
 * 
 * @see ECSSAttributeOperator
 * @author philip
 */
@Immutable
public final class CSSSelectorAttribute implements ICSSSelectorMember
{
  private final String m_sAttrName;
  private final ECSSAttributeOperator m_eOperator;
  private final String m_sAttrValue;

  public CSSSelectorAttribute (@Nonnull @Nonempty final String sAttrName)
  {
    if (StringHelper.hasNoText (sAttrName))
      throw new IllegalArgumentException ("attrName");
    m_sAttrName = sAttrName;
    m_eOperator = null;
    m_sAttrValue = null;
  }

  public CSSSelectorAttribute (@Nonnull @Nonempty final String sAttrName,
                               @Nonnull final ECSSAttributeOperator eOperator,
                               @Nonnull final String sAttrValue)
  {
    if (StringHelper.hasNoText (sAttrName))
      throw new IllegalArgumentException ("attrName");
    if (eOperator == null)
      throw new NullPointerException ("operator");
    if (sAttrValue == null)
      throw new NullPointerException ("attrValue");
    m_sAttrName = sAttrName;
    m_eOperator = eOperator;
    m_sAttrValue = sAttrValue;
  }

  @Nonnull
  @Nonempty
  public String getAttrName ()
  {
    return m_sAttrName;
  }

  @Nullable
  public ECSSAttributeOperator getOperator ()
  {
    return m_eOperator;
  }

  @Nullable
  public String getAttrValue ()
  {
    return m_sAttrValue;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (m_eOperator == null)
      return "[" + m_sAttrName + "]";
    return "[" + m_sAttrName + m_eOperator.getAsCSSString (eVersion, bOptimizedOutput) + m_sAttrValue + "]";
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSelectorAttribute))
      return false;
    final CSSSelectorAttribute rhs = (CSSSelectorAttribute) o;
    return m_sAttrName.equals (rhs.m_sAttrName) &&
           EqualsUtils.equals (m_eOperator, rhs.m_eOperator) &&
           EqualsUtils.equals (m_sAttrValue, rhs.m_sAttrValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sAttrName).append (m_eOperator).append (m_sAttrValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("attrName", m_sAttrName)
                                       .appendIfNotNull ("operator", m_eOperator)
                                       .appendIfNotNull ("attrValue", m_sAttrValue)
                                       .toString ();
  }
}
