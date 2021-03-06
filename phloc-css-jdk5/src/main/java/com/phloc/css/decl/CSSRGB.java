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
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.utils.CSSColorHelper;

/**
 * Represents a single RGB color value (red, green, blue)
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSRGB implements ICSSWriteable
{
  private String m_sRed;
  private String m_sGreen;
  private String m_sBlue;

  /**
   * Copy constructor
   * 
   * @param aOther
   *        The object to copy the data from. May not be <code>null</code>.
   */
  public CSSRGB (@Nonnull final CSSRGB aOther)
  {
    this (aOther.getRed (), aOther.getGreen (), aOther.getBlue ());
  }

  /**
   * Constructor
   * 
   * @param nRed
   *        Red part. Is fitted to a value between 0 and 255.
   * @param nGreen
   *        Green part. Is fitted to a value between 0 and 255.
   * @param nBlue
   *        Blue part. Is fitted to a value between 0 and 255.
   */
  public CSSRGB (final int nRed, final int nGreen, final int nBlue)
  {
    this (Integer.toString (CSSColorHelper.getRGBValue (nRed)),
          Integer.toString (CSSColorHelper.getRGBValue (nGreen)),
          Integer.toString (CSSColorHelper.getRGBValue (nBlue)));
  }

  /**
   * Constructor
   * 
   * @param sRed
   *        Red part.
   * @param sGreen
   *        Green part.
   * @param sBlue
   *        Blue part.
   */
  public CSSRGB (@Nonnull @Nonempty final String sRed,
                 @Nonnull @Nonempty final String sGreen,
                 @Nonnull @Nonempty final String sBlue)
  {
    setRed (sRed);
    setGreen (sGreen);
    setBlue (sBlue);
  }

  /**
   * @return red part
   */
  @Nonnull
  @Nonempty
  public String getRed ()
  {
    return m_sRed;
  }

  @Nonnull
  public CSSRGB setRed (@Nonnull @Nonempty final String sRed)
  {
    ValueEnforcer.notEmpty (sRed, "Red");

    m_sRed = sRed;
    return this;
  }

  /**
   * @return green part
   */
  @Nonnull
  @Nonempty
  public String getGreen ()
  {
    return m_sGreen;
  }

  @Nonnull
  public CSSRGB setGreen (@Nonnull @Nonempty final String sGreen)
  {
    ValueEnforcer.notEmpty (sGreen, "Green");

    m_sGreen = sGreen;
    return this;
  }

  /**
   * @return blue part
   */
  @Nonnull
  @Nonempty
  public String getBlue ()
  {
    return m_sBlue;
  }

  @Nonnull
  public CSSRGB setBlue (@Nonnull @Nonempty final String sBlue)
  {
    ValueEnforcer.notEmpty (sBlue, "Blue");

    m_sBlue = sBlue;
    return this;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return CCSSValue.PREFIX_RGB_OPEN + m_sRed + ',' + m_sGreen + ',' + m_sBlue + CCSSValue.SUFFIX_RGB_CLOSE;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSRGB rhs = (CSSRGB) o;
    return m_sRed.equals (rhs.m_sRed) && m_sGreen.equals (rhs.m_sGreen) && m_sBlue.equals (rhs.m_sBlue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sRed).append (m_sGreen).append (m_sBlue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("red", m_sRed)
                                       .append ("green", m_sGreen)
                                       .append ("blue", m_sBlue)
                                       .toString ();
  }
}
