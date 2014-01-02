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

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.utils.CSSColorHelper;

/**
 * Represents a single HSL color value
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSHSL implements ICSSWriteable, ICSSVersionAware
{
  private String m_sHue;
  private String m_sSaturation;
  private String m_sLightness;

  /**
   * Copy constructor
   * 
   * @param aOther
   *        The object to copy the data from. May not be <code>null</code>.
   */
  public CSSHSL (@Nonnull final CSSHSL aOther)
  {
    this (aOther.getHue (), aOther.getSaturation (), aOther.getLightness ());
  }

  /**
   * Constructor
   * 
   * @param nHue
   *        Hue value. Is scaled to the range 0-360
   * @param nSaturation
   *        Saturation value. Is cut to the range 0-100 (percentage)
   * @param nLightness
   *        Lightness value. Is cut to the range 0-100 (percentage)
   */
  public CSSHSL (final int nHue, final int nSaturation, final int nLightness)
  {
    this (Integer.toString (CSSColorHelper.getHSLHueValue (nHue)),
          Integer.toString (CSSColorHelper.getHSLPercentageValue (nSaturation)) + "%",
          Integer.toString (CSSColorHelper.getHSLPercentageValue (nLightness)) + "%");
  }

  /**
   * Constructor
   * 
   * @param fHue
   *        Hue value. Is scaled to the range 0-360
   * @param fSaturation
   *        Saturation value. Is cut to the range 0-100 (percentage)
   * @param fLightness
   *        Lightness value. Is cut to the range 0-100 (percentage)
   */
  public CSSHSL (final float fHue, final float fSaturation, final float fLightness)
  {
    this (Float.toString (CSSColorHelper.getHSLHueValue (fHue)),
          Float.toString (CSSColorHelper.getHSLPercentageValue (fSaturation)) + "%",
          Float.toString (CSSColorHelper.getHSLPercentageValue (fLightness)) + "%");
  }

  public CSSHSL (@Nonnull @Nonempty final String sHue,
                 @Nonnull @Nonempty final String sSaturation,
                 @Nonnull @Nonempty final String sLightness)
  {
    setHue (sHue);
    setSaturation (sSaturation);
    setLightness (sLightness);
  }

  /**
   * @return hue part
   */
  @Nonnull
  @Nonempty
  public String getHue ()
  {
    return m_sHue;
  }

  public void setHue (@Nonnull @Nonempty final String sHue)
  {
    if (StringHelper.hasNoText (sHue))
      throw new IllegalArgumentException ("hue");
    m_sHue = sHue;
  }

  /**
   * @return saturation part
   */
  @Nonnull
  @Nonempty
  public String getSaturation ()
  {
    return m_sSaturation;
  }

  public void setSaturation (@Nonnull @Nonempty final String sSaturation)
  {
    if (StringHelper.hasNoText (sSaturation))
      throw new IllegalArgumentException ("saturation");
    m_sSaturation = sSaturation;
  }

  /**
   * @return lightness part
   */
  @Nonnull
  @Nonempty
  public String getLightness ()
  {
    return m_sLightness;
  }

  public void setLightness (@Nonnull @Nonempty final String sLightness)
  {
    if (StringHelper.hasNoText (sLightness))
      throw new IllegalArgumentException ("lightness");
    m_sLightness = sLightness;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    return CCSSValue.PREFIX_HSL_OPEN + m_sHue + ',' + m_sSaturation + ',' + m_sLightness + CCSSValue.SUFFIX_HSL_CLOSE;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSHSL))
      return false;
    final CSSHSL rhs = (CSSHSL) o;
    return m_sHue.equals (rhs.m_sHue) &&
           m_sSaturation.equals (rhs.m_sSaturation) &&
           m_sLightness.equals (rhs.m_sLightness);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sHue).append (m_sSaturation).append (m_sLightness).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("hue", m_sHue)
                                       .append ("saturation", m_sSaturation)
                                       .append ("lightness", m_sLightness)
                                       .toString ();
  }
}
