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
package com.phloc.css.propertyvalue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CCSS;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.property.ICSSProperty;

/**
 * Represents the combination of a single CSS property and it's according value.
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSValue implements ICSSValue
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSValue.class);

  private final ICSSProperty m_aProperty;
  private final String m_sValue;
  private final boolean m_bIsImportant;

  /**
   * Constructor
   * 
   * @param aProperty
   *        The CSS property. May not be <code>null</code>.
   * @param sValue
   *        The String value to use. May be <code>null</code>.
   * @param bIsImportant
   *        <code>true</code> if the value should be important,
   *        <code>false</code> otherwise
   */
  public CSSValue (@Nonnull final ICSSProperty aProperty, @Nonnull final String sValue, final boolean bIsImportant)
  {
    if (aProperty == null)
      throw new NullPointerException ("property");
    if (!aProperty.isValidValue (sValue))
      s_aLogger.warn ("CSS: the value '" +
                      sValue +
                      "' is not valid for property '" +
                      aProperty.getProp ().getName () +
                      "'");
    if (sValue.contains (CCSS.IMPORTANT_SUFFIX))
      s_aLogger.warn ("CSS: the value '" +
                      sValue +
                      "' should not contain the '" +
                      CCSS.IMPORTANT_SUFFIX +
                      "' string! Pass 'true' as important parameter instead.");

    m_aProperty = aProperty;
    m_sValue = StringHelper.hasText (sValue) ? sValue + (bIsImportant ? CCSS.IMPORTANT_SUFFIX : "") : null;
    m_bIsImportant = bIsImportant;
  }

  /**
   * @return The CSS property used. Never <code>null</code>.
   */
  @Nonnull
  public ICSSProperty getProperty ()
  {
    return m_aProperty;
  }

  /**
   * @return The CSS base property used. Never <code>null</code>.
   */
  @Nonnull
  public ECSSProperty getProp ()
  {
    return m_aProperty.getProp ();
  }

  /**
   * @return The CSS value used. May be <code>null</code>.
   */
  public String getValue ()
  {
    return m_sValue;
  }

  /**
   * @return <code>true</code> if it is important, <code>false</code> if not
   */
  public boolean isImportant ()
  {
    return m_bIsImportant;
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (m_aProperty);
    return m_aProperty.getProp ().getName () + CCSS.SEPARATOR_PROPERTY_VALUE + m_sValue + CCSS.DEFINITION_END;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSValue))
      return false;
    final CSSValue rhs = (CSSValue) o;
    // Important flag is contained in the value!
    return m_aProperty.getProp ().equals (rhs.m_aProperty.getProp ()) && m_sValue.equals (rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aProperty.getProp ()).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("property", m_aProperty)
                                       .append ("value", m_sValue)
                                       .append ("important", m_bIsImportant)
                                       .toString ();
  }
}
