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
package com.phloc.css.utils;

import java.awt.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.propertyvalue.CCSSValue;

/**
 * Provides color handling sanity methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSColorHelper
{
  public static final int RGB_MIN = 0;
  public static final int RGB_MAX = 255;
  public static final int RGB_RANGE = 256;
  public static final int HSL_MIN = 0;
  public static final int HSL_MAX = 359;
  public static final int HSL_RANGE = 360;
  public static final float OPACITY_MIN = 0f;
  public static final float OPACITY_MAX = 1f;

  private static final String PATTERN_RGB = "^" +
                                            CCSSValue.PREFIX_RGB +
                                            "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){2}\\s*\\-?[0-9]+%?\\s*\\)$";
  private static final String PATTERN_RGBA = "^" +
                                             CCSSValue.PREFIX_RGBA +
                                             "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){3}\\s*[0-9]+(\\.[0-9]*)?\\s*\\)$";
  private static final String PATTERN_HSL = "^" +
                                            CCSSValue.PREFIX_HSL +
                                            "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){2}\\s*\\-?[0-9]+%?\\s*\\)$";
  private static final String PATTERN_HSLA = "^" +
                                             CCSSValue.PREFIX_HSLA +
                                             "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){3}\\s*[0-9]+(\\.[0-9]*)?\\s*\\)$";
  private static final String PATTERN_HEX = "^" + CCSSValue.PREFIX_HEX + "[0-9a-fA-F]{1,6}$";

  private CSSColorHelper ()
  {}

  public static boolean isColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    if (StringHelper.hasNoText (sRealValue))
      return false;

    return isRGBColorValue (sRealValue) ||
           isRGBAColorValue (sRealValue) ||
           isHSLColorValue (sRealValue) ||
           isHSLAColorValue (sRealValue) ||
           isHexColorValue (sRealValue) ||
           ECSSColor.isDefaultColorName (sRealValue) ||
           ECSSColorName.isDefaultColorName (sRealValue) ||
           sRealValue.equals (CCSSValue.CURRENTCOLOR);
  }

  public static boolean isRGBColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && RegExHelper.stringMatchesPattern (PATTERN_RGB, sRealValue);
  }

  public static boolean isRGBAColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && RegExHelper.stringMatchesPattern (PATTERN_RGBA, sRealValue);
  }

  public static boolean isHSLColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && RegExHelper.stringMatchesPattern (PATTERN_HSL, sRealValue);
  }

  public static boolean isHSLAColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && RegExHelper.stringMatchesPattern (PATTERN_HSLA, sRealValue);
  }

  public static boolean isHexColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && RegExHelper.stringMatchesPattern (PATTERN_HEX, sRealValue);
  }

  private static int _mod (final int nValue, final int nMod)
  {
    // modulo does not work as expected on negative numbers
    int nPositiveValue = nValue;
    while (nPositiveValue < 0)
      nPositiveValue += nMod;
    return nPositiveValue % nMod;
  }

  @Nonnull
  @Nonempty
  public static String getRGBColorValue (final int nRed, final int nGreen, final int nBlue)
  {
    return new StringBuilder (16).append (CCSSValue.PREFIX_RGB)
                                 .append ('(')
                                 .append (_mod (nRed, RGB_RANGE))
                                 .append (',')
                                 .append (_mod (nGreen, RGB_RANGE))
                                 .append (',')
                                 .append (_mod (nBlue, RGB_RANGE))
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getRGBAColorValue (final int nRed, final int nGreen, final int nBlue, final float fOpacity)
  {
    final float fRealOpacity = fOpacity < OPACITY_MIN ? OPACITY_MIN : fOpacity > OPACITY_MAX ? OPACITY_MAX : fOpacity;
    return new StringBuilder (24).append (CCSSValue.PREFIX_RGBA)
                                 .append ('(')
                                 .append (_mod (nRed, RGB_RANGE))
                                 .append (',')
                                 .append (_mod (nGreen, RGB_RANGE))
                                 .append (',')
                                 .append (_mod (nBlue, RGB_RANGE))
                                 .append (',')
                                 .append (fRealOpacity)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getHSLColorValue (final int nHue, final int nSaturation, final int nLightness)
  {
    return new StringBuilder (16).append (CCSSValue.PREFIX_HSL)
                                 .append ('(')
                                 .append (_mod (nHue, HSL_RANGE))
                                 .append (',')
                                 .append (_mod (nSaturation, HSL_RANGE))
                                 .append (',')
                                 .append (_mod (nLightness, HSL_RANGE))
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getHSLAColorValue (final int nHue,
                                          final int nSaturation,
                                          final int nLightness,
                                          final float fOpacity)
  {
    final float fRealOpacity = fOpacity < OPACITY_MIN ? OPACITY_MIN : fOpacity > OPACITY_MAX ? OPACITY_MAX : fOpacity;
    return new StringBuilder (24).append (CCSSValue.PREFIX_HSLA)
                                 .append ('(')
                                 .append (_mod (nHue, HSL_RANGE))
                                 .append (',')
                                 .append (_mod (nSaturation, HSL_RANGE))
                                 .append (',')
                                 .append (_mod (nLightness, HSL_RANGE))
                                 .append (',')
                                 .append (fRealOpacity)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  private static String _getRGBPartAsHexString (final int nRGBPart)
  {
    return StringHelper.getHexStringLeadingZero (_mod (nRGBPart, RGB_RANGE), 2);
  }

  @Nonnull
  @Nonempty
  public static String getHexColorValue (final int nRed, final int nGreen, final int nBlue)
  {
    return new StringBuilder (CCSSValue.HEXVALUE_LENGTH).append (CCSSValue.PREFIX_HEX)
                                                        .append (_getRGBPartAsHexString (nRed))
                                                        .append (_getRGBPartAsHexString (nGreen))
                                                        .append (_getRGBPartAsHexString (nBlue))
                                                        .toString ();
  }

  /**
   * Get the passed RGB values as HSL values compliant for CSS in the range
   * 0-359
   * 
   * @param nRed
   *          red value
   * @param nGreen
   *          green value
   * @param nBlue
   *          blue value
   * @return An array of 3 ints, containing hue, saturation and lightness (in
   *         this order)
   */
  @Nonnull
  @Nonempty
  public static int [] getRGBAsHSLValue (final int nRed, final int nGreen, final int nBlue)
  {
    // Convert RGB to HSB(=HSL) - brightness vs. lightness
    // All returned values in the range 0-1
    final float [] aHSL = new float [3];
    Color.RGBtoHSB (nRed, nGreen, nBlue, aHSL);
    return new int [] { (int) (aHSL[0] * HSL_RANGE), (int) (aHSL[1] * HSL_RANGE), (int) (aHSL[2] * HSL_RANGE) };
  }
}