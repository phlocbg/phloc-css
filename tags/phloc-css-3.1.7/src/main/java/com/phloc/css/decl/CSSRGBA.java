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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.utils.CSSColorHelper;

/**
 * Represents a single RGBA color value
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSRGBA implements ICSSWriteable
{
  private String m_sRed;
  private String m_sGreen;
  private String m_sBlue;
  private String m_sOpacity;

  public CSSRGBA (final int nRed, final int nGreen, final int nBlue, final float fOpacity)
  {
    this (Integer.toString (CSSColorHelper.getRGBValue (nRed)),
          Integer.toString (CSSColorHelper.getRGBValue (nGreen)),
          Integer.toString (CSSColorHelper.getRGBValue (nBlue)),
          Float.toString (CSSColorHelper.getOpacityToUse (fOpacity)));
  }

  public CSSRGBA (@Nonnull @Nonempty final String sRed,
                  @Nonnull @Nonempty final String sGreen,
                  @Nonnull @Nonempty final String sBlue,
                  @Nonnull @Nonempty final String sOpacity)
  {
    setRed (sRed);
    setGreen (sGreen);
    setBlue (sBlue);
    setOpacity (sOpacity);
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

  public void setRed (@Nonnull @Nonempty final String sRed)
  {
    if (StringHelper.hasNoText (sRed))
      throw new IllegalArgumentException ("red");
    m_sRed = sRed;
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

  public void setGreen (@Nonnull @Nonempty final String sGreen)
  {
    if (StringHelper.hasNoText (sGreen))
      throw new IllegalArgumentException ("green");
    m_sGreen = sGreen;
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

  public void setBlue (@Nonnull @Nonempty final String sBlue)
  {
    if (StringHelper.hasNoText (sBlue))
      throw new IllegalArgumentException ("blue");
    m_sBlue = sBlue;
  }

  /**
   * @return opacity part
   */
  @Nonnull
  @Nonempty
  public String getOpacity ()
  {
    return m_sOpacity;
  }

  public void setOpacity (@Nonnull @Nonempty final String sOpacity)
  {
    if (StringHelper.hasNoText (sOpacity))
      throw new IllegalArgumentException ("opacity");
    m_sOpacity = sOpacity;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return CCSSValue.PREFIX_RGBA_OPEN + m_sRed + ',' + m_sGreen + ',' + m_sBlue + ',' + m_sOpacity + ')';
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSRGBA))
      return false;
    final CSSRGBA rhs = (CSSRGBA) o;
    return m_sRed.equals (rhs.m_sRed) &&
           m_sGreen.equals (rhs.m_sGreen) &&
           m_sBlue.equals (rhs.m_sBlue) &&
           m_sOpacity.equals (rhs.m_sOpacity);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sRed)
                                       .append (m_sGreen)
                                       .append (m_sBlue)
                                       .append (m_sOpacity)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("red", m_sRed)
                                       .append ("green", m_sGreen)
                                       .append ("blue", m_sBlue)
                                       .append ("opacity", m_sOpacity)
                                       .toString ();
  }
}
