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
package com.phloc.css.color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.CCSS;

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
           sRealValue.equals (CCSS.CURRENTCOLOR);
  }

  public static boolean isRGBColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" +
                                             CCSS.PREFIX_RGB +
                                             "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){2}\\s*\\-?[0-9]+%?\\s*\\)$", sRealValue);
  }

  public static boolean isRGBAColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" +
                                                 CCSS.PREFIX_RGBA +
                                                 "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){3}\\s*[0-9]+(\\.[0-9]*)?\\s*\\)$",
                                             sRealValue);
  }

  public static boolean isHSLColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" +
                                             CCSS.PREFIX_HSL +
                                             "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){2}\\s*\\-?[0-9]+%?\\s*\\)$", sRealValue);
  }

  public static boolean isHSLAColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" +
                                                 CCSS.PREFIX_HSLA +
                                                 "\\s*\\((\\s*\\-?[0-9]+%?\\s*,){3}\\s*[0-9]+(\\.[0-9]*)?\\s*\\)$",
                                             sRealValue);
  }

  public static boolean isHexColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" + CCSS.PREFIX_HEX + "[0-9a-fA-F]{1,6}$", sRealValue);
  }

  private static int _mod (final int nValue, final int nMod)
  {
    // modulo does not work on negative numbers
    int nPositiveValue = nValue;
    while (nPositiveValue < 0)
      nPositiveValue += nMod;
    return nPositiveValue % nMod;
  }

  @Nonnull
  @Nonempty
  public static String getRGBColorValue (final int nRed, final int nGreen, final int nBlue)
  {
    return new StringBuilder (16).append (CCSS.PREFIX_RGB)
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
    return new StringBuilder (16).append (CCSS.PREFIX_RGBA)
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
    return new StringBuilder (16).append (CCSS.PREFIX_HSL)
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
    return new StringBuilder (16).append (CCSS.PREFIX_HSLA)
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
  public static String getHexColorValue (final int nRed, final int nGreen, final int nBlue)
  {
    return new StringBuilder (CCSS.HEXVALUE_LENGTH).append (CCSS.PREFIX_HEX)
                                                   .append (StringHelper.getHexStringLeadingZero (_mod (nRed, RGB_RANGE),
                                                                                                  2))
                                                   .append (StringHelper.getHexStringLeadingZero (_mod (nGreen,
                                                                                                        RGB_RANGE),
                                                                                                  2))
                                                   .append (StringHelper.getHexStringLeadingZero (_mod (nBlue,
                                                                                                        RGB_RANGE),
                                                                                                  2))
                                                   .toString ();
  }
}
