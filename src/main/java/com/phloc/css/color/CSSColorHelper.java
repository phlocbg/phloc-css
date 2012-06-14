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

import javax.annotation.Nonnegative;
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
  private CSSColorHelper ()
  {}

  public static boolean isColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           (isRGBColorValue (sRealValue) ||
            isRGBAColorValue (sRealValue) ||
            isHSLColorValue (sRealValue) ||
            isHSLAColorValue (sRealValue) ||
            isHexColorValue (sRealValue) || CHTMLColors.isDefaultColorName (sRealValue));
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

  @Nonnull
  @Nonempty
  public static String getRGBColorValue (@Nonnegative final int nRed,
                                         @Nonnegative final int nGreen,
                                         @Nonnegative final int nBlue)
  {
    if (nRed < 0 || nRed > 255)
      throw new IllegalArgumentException ("Invalid red: " + nRed);
    if (nGreen < 0 || nGreen > 255)
      throw new IllegalArgumentException ("Invalid green: " + nGreen);
    if (nBlue < 0 || nBlue > 255)
      throw new IllegalArgumentException ("Invalid blue: " + nBlue);

    return new StringBuilder (16).append (CCSS.PREFIX_RGB)
                                 .append ('(')
                                 .append (nRed)
                                 .append (',')
                                 .append (nGreen)
                                 .append (',')
                                 .append (nBlue)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getRGBAColorValue (@Nonnegative final int nRed,
                                          @Nonnegative final int nGreen,
                                          @Nonnegative final int nBlue,
                                          @Nonnegative final float fOpacity)
  {
    if (nRed < 0 || nRed > 255)
      throw new IllegalArgumentException ("Invalid red: " + nRed);
    if (nGreen < 0 || nGreen > 255)
      throw new IllegalArgumentException ("Invalid green: " + nGreen);
    if (nBlue < 0 || nBlue > 255)
      throw new IllegalArgumentException ("Invalid blue: " + nBlue);
    if (fOpacity < 0 || fOpacity > 1)
      throw new IllegalArgumentException ("Invalid opacity: " + fOpacity);

    return new StringBuilder (16).append (CCSS.PREFIX_RGBA)
                                 .append ('(')
                                 .append (nRed)
                                 .append (',')
                                 .append (nGreen)
                                 .append (',')
                                 .append (nBlue)
                                 .append (',')
                                 .append (fOpacity)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getHSLColorValue (@Nonnegative final float fHue,
                                         @Nonnegative final float fSaturation,
                                         @Nonnegative final float fLightness)
  {
    if (fHue < 0 || fHue > 360)
      throw new IllegalArgumentException ("Invalid hue: " + fHue);
    if (fSaturation < 0 || fSaturation > 360)
      throw new IllegalArgumentException ("Invalid saturation: " + fSaturation);
    if (fLightness < 0 || fLightness > 360)
      throw new IllegalArgumentException ("Invalid lightness: " + fLightness);

    return new StringBuilder (16).append (CCSS.PREFIX_HSL)
                                 .append ('(')
                                 .append (fHue)
                                 .append (',')
                                 .append (fSaturation)
                                 .append (',')
                                 .append (fLightness)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getHSLAColorValue (@Nonnegative final float fHue,
                                          @Nonnegative final float fSaturation,
                                          @Nonnegative final float fLightness,
                                          @Nonnegative final float fOpacity)
  {
    if (fHue < 0 || fHue > 360)
      throw new IllegalArgumentException ("Invalid hue: " + fHue);
    if (fSaturation < 0 || fSaturation > 360)
      throw new IllegalArgumentException ("Invalid saturation: " + fSaturation);
    if (fLightness < 0 || fLightness > 360)
      throw new IllegalArgumentException ("Invalid lightness: " + fLightness);
    if (fOpacity < 0 || fOpacity > 1)
      throw new IllegalArgumentException ("Invalid opacity: " + fOpacity);

    return new StringBuilder (16).append (CCSS.PREFIX_HSLA)
                                 .append ('(')
                                 .append (fHue)
                                 .append (',')
                                 .append (fSaturation)
                                 .append (',')
                                 .append (fLightness)
                                 .append (',')
                                 .append (fOpacity)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String getHexColorValue (@Nonnegative final int nRed,
                                         @Nonnegative final int nGreen,
                                         @Nonnegative final int nBlue)
  {
    if (nRed < 0 || nRed > 255)
      throw new IllegalArgumentException ("Invalid red: " + nRed);
    if (nGreen < 0 || nGreen > 255)
      throw new IllegalArgumentException ("Invalid green: " + nGreen);
    if (nBlue < 0 || nBlue > 255)
      throw new IllegalArgumentException ("Invalid blue: " + nBlue);

    return new StringBuilder (CCSS.HEXVALUE_LENGTH).append (CCSS.PREFIX_HEX)
                                                   .append (StringHelper.getHexStringLeadingZero (nRed, 2))
                                                   .append (StringHelper.getHexStringLeadingZero (nGreen, 2))
                                                   .append (StringHelper.getHexStringLeadingZero (nBlue, 2))
                                                   .toString ();
  }
}
