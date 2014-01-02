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
 * Represents a single HSLA color value
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSHSLA implements ICSSWriteable, ICSSVersionAware
{
  private String m_sHue;
  private String m_sSaturation;
  private String m_sLightness;
  private String m_sOpacity;

  /**
   * Copy constructor
   * 
   * @param aOther
   *        The object to copy the data from. May not be <code>null</code>.
   */
  public CSSHSLA (@Nonnull final CSSHSLA aOther)
  {
    this (aOther.getHue (), aOther.getSaturation (), aOther.getLightness (), aOther.getOpacity ());
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
   * @param fOpacity
   *        Opacity - is scaled to 0-1
   */
  public CSSHSLA (final int nHue, final int nSaturation, final int nLightness, final float fOpacity)
  {
    this (Integer.toString (CSSColorHelper.getHSLHueValue (nHue)),
          Integer.toString (CSSColorHelper.getHSLPercentageValue (nSaturation)) + "%",
          Integer.toString (CSSColorHelper.getHSLPercentageValue (nLightness)) + "%",
          Float.toString (CSSColorHelper.getOpacityToUse (fOpacity)));
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
   * @param fOpacity
   *        Opacity - is scaled to 0-1
   */
  public CSSHSLA (final float fHue, final float fSaturation, final float fLightness, final float fOpacity)
  {
    this (Float.toString (CSSColorHelper.getHSLHueValue (fHue)),
          Float.toString (CSSColorHelper.getHSLPercentageValue (fSaturation)) + "%",
          Float.toString (CSSColorHelper.getHSLPercentageValue (fLightness)) + "%",
          Float.toString (CSSColorHelper.getOpacityToUse (fOpacity)));
  }

  public CSSHSLA (@Nonnull @Nonempty final String sHue,
                  @Nonnull @Nonempty final String sSaturation,
                  @Nonnull @Nonempty final String sLightness,
                  @Nonnull @Nonempty final String sOpacity)
  {
    setHue (sHue);
    setSaturation (sSaturation);
    setLightness (sLightness);
    setOpacity (sOpacity);
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
    aSettings.checkVersionRequirements (this);
    return CCSSValue.PREFIX_HSLA_OPEN +
           m_sHue +
           ',' +
           m_sSaturation +
           ',' +
           m_sLightness +
           ',' +
           m_sOpacity +
           CCSSValue.SUFFIX_HSLA_CLOSE;
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
    if (!(o instanceof CSSHSLA))
      return false;
    final CSSHSLA rhs = (CSSHSLA) o;
    return m_sHue.equals (rhs.m_sHue) &&
           m_sSaturation.equals (rhs.m_sSaturation) &&
           m_sLightness.equals (rhs.m_sLightness) &&
           m_sOpacity.equals (rhs.m_sOpacity);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sHue)
                                       .append (m_sSaturation)
                                       .append (m_sLightness)
                                       .append (m_sOpacity)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("hue", m_sHue)
                                       .append ("saturation", m_sSaturation)
                                       .append ("lightness", m_sLightness)
                                       .append ("opacity", m_sOpacity)
                                       .toString ();
  }
}
