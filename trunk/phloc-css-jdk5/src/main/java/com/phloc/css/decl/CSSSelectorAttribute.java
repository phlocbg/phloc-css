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
package com.phloc.css.decl;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * A single CSS selector attribute.
 * 
 * @see ECSSAttributeOperator
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSSelectorAttribute implements ICSSSelectorMember, ICSSSourceLocationAware
{
  private final String m_sNamespacePrefix;
  private final String m_sAttrName;
  private final ECSSAttributeOperator m_eOperator;
  private final String m_sAttrValue;
  private CSSSourceLocation m_aSourceLocation;

  private static boolean _isValidNamespacePrefix (@Nullable final String sNamespacePrefix)
  {
    return StringHelper.hasNoText (sNamespacePrefix) || sNamespacePrefix.endsWith ("|");
  }

  public CSSSelectorAttribute (@Nullable final String sNamespacePrefix, @Nonnull @Nonempty final String sAttrName)
  {
    if (!_isValidNamespacePrefix (sNamespacePrefix))
      throw new IllegalArgumentException ("namespacePrefix is illegal!");
    if (StringHelper.hasNoText (sAttrName))
      throw new IllegalArgumentException ("attrName");
    m_sNamespacePrefix = sNamespacePrefix;
    m_sAttrName = sAttrName;
    m_eOperator = null;
    m_sAttrValue = null;
  }

  public CSSSelectorAttribute (@Nullable final String sNamespacePrefix,
                               @Nonnull @Nonempty final String sAttrName,
                               @Nonnull final ECSSAttributeOperator eOperator,
                               @Nonnull final String sAttrValue)
  {
    if (!_isValidNamespacePrefix (sNamespacePrefix))
      throw new IllegalArgumentException ("namespacePrefix is illegal!");
    if (StringHelper.hasNoText (sAttrName))
      throw new IllegalArgumentException ("attrName");
    if (eOperator == null)
      throw new NullPointerException ("operator");
    if (sAttrValue == null)
      throw new NullPointerException ("attrValue");
    m_sNamespacePrefix = sNamespacePrefix;
    m_sAttrName = sAttrName;
    m_eOperator = eOperator;
    m_sAttrValue = sAttrValue;
  }

  @Nullable
  public String getNamespacePrefix ()
  {
    return m_sNamespacePrefix;
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
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ('[');
    if (StringHelper.hasText (m_sNamespacePrefix))
      aSB.append (m_sNamespacePrefix);
    aSB.append (m_sAttrName);
    if (m_eOperator != null)
      aSB.append (m_eOperator.getAsCSSString (aSettings, nIndentLevel)).append (m_sAttrValue);
    return aSB.append (']').toString ();
  }

  /**
   * Set the source location of the object, determined while parsing.
   * 
   * @param aSourceLocation
   *        The source location to use. May be <code>null</code>.
   */
  public void setSourceLocation (@Nullable final CSSSourceLocation aSourceLocation)
  {
    m_aSourceLocation = aSourceLocation;
  }

  @Nullable
  public CSSSourceLocation getSourceLocation ()
  {
    return m_aSourceLocation;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSelectorAttribute))
      return false;
    final CSSSelectorAttribute rhs = (CSSSelectorAttribute) o;
    return EqualsUtils.equals (m_sNamespacePrefix, rhs.m_sNamespacePrefix) &&
           m_sAttrName.equals (rhs.m_sAttrName) &&
           EqualsUtils.equals (m_eOperator, rhs.m_eOperator) &&
           EqualsUtils.equals (m_sAttrValue, rhs.m_sAttrValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sNamespacePrefix)
                                       .append (m_sAttrName)
                                       .append (m_eOperator)
                                       .append (m_sAttrValue)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("namespacePrefix", m_sNamespacePrefix)
                                       .append ("attrName", m_sAttrName)
                                       .appendIfNotNull ("operator", m_eOperator)
                                       .appendIfNotNull ("attrValue", m_sAttrValue)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
