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
package com.phloc.css;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.utils.CSSColorHelper;
import com.phloc.css.utils.CSSNumberHelper;
import com.phloc.css.utils.CSSRectHelper;
import com.phloc.css.utils.CSSURLHelper;

/**
 * Contains CSS style constants and utility stuff. Only constants that are part
 * of the CSS specification are contained in this class.<br>
 * Units of measurement are based on:
 * http://de.selfhtml.org/css/formate/wertzuweisung.htm<br>
 * 
 * @author philip
 */
@Immutable
public final class CCSS
{
  /** The separator between a property and a value. (e.g. display:none) */
  public static final char SEPARATOR_PROPERTY_VALUE = ':';
  public static final String SEPARATOR_PROPERTY_VALUE_STR = Character.toString (SEPARATOR_PROPERTY_VALUE);

  /** The character to end a definition. (e.g. display:none;) */
  public static final char DEFINITION_END = ';';
  public static final String DEFINITION_END_STR = Character.toString (DEFINITION_END);

  /** Regular CSS file extension */
  public static final String FILE_EXTENSION_CSS = ".css";

  /** Minified CSS file extension */
  public static final String FILE_EXTENSION_MIN_CSS = ".min.css";

  public static final String IMPORTANT_SUFFIX = " !important";

  private CCSS ()
  {}

  /**
   * @deprecated Use
   *             {@link CSSNumberHelper#isNumberWithUnitValue(String, boolean)}
   *             instead
   */
  @Deprecated
  public static boolean isNumberWithUnitValue (@Nullable final String sValue, final boolean bWithPerc)
  {
    return CSSNumberHelper.isNumberWithUnitValue (sValue, bWithPerc);
  }

  /**
   * @deprecated Use {@link CSSNumberHelper#isNumberValue(String)} instead
   */
  @Deprecated
  public static boolean isNumberValue (@Nullable final String sValue)
  {
    return CSSNumberHelper.isNumberValue (sValue);
  }

  /**
   * @deprecated Use {@link CSSColorHelper#isColorValue(String)} instead
   */
  @Deprecated
  public static boolean isColorValue (@Nullable final String sValue)
  {
    return CSSColorHelper.isColorValue (sValue);
  }

  /**
   * @deprecated Use {@link CSSColorHelper#isRGBColorValue(String)} instead
   */
  @Deprecated
  public static boolean isRGBColorValue (@Nullable final String sValue)
  {
    return CSSColorHelper.isRGBColorValue (sValue);
  }

  /**
   * @deprecated Use {@link CSSColorHelper#isHexColorValue(String)} instead
   */
  @Deprecated
  public static boolean isHexColorValue (@Nullable final String sValue)
  {
    return CSSColorHelper.isHexColorValue (sValue);
  }

  /**
   * Extract the real URL contained in a URL value.
   * 
   * @param sValue
   *          The value containing the CSS value
   * @return <code>null</code> if the passed value is not an URL value
   * @see CSSURLHelper#isURLValue(String)
   * @deprecated Use {@link CSSURLHelper#getURLValue(String)} instead
   */
  @Deprecated
  @Nullable
  public static String getURLValue (@Nullable final String sValue)
  {
    return CSSURLHelper.getURLValue (sValue);
  }

  /**
   * Check if the passed value is an URL value.
   * 
   * @param sValue
   *          The value to be checked.
   * @return <code>true</code> if the passed value starts with "url("
   * @deprecated Use {@link CSSURLHelper#isURLValue(String)} instead
   */
  @Deprecated
  public static boolean isURLValue (@Nullable final String sValue)
  {
    return CSSURLHelper.isURLValue (sValue);
  }

  /**
   * @deprecated Use {@link CSSRectHelper#isRectValue(String)} instead
   */
  @Deprecated
  public static boolean isRectValue (@Nullable final String sValue)
  {
    return CSSRectHelper.isRectValue (sValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String pt (final int nValue)
  {
    return ECSSUnit.pt (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String pc (final int nValue)
  {
    return ECSSUnit.pc (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String in (final int nValue)
  {
    return ECSSUnit.in (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String mm (final int nValue)
  {
    return ECSSUnit.mm (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String cm (final int nValue)
  {
    return ECSSUnit.cm (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String px (final int nValue)
  {
    return ECSSUnit.px (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String em (final int nValue)
  {
    return ECSSUnit.EM.format (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String em (final double dValue)
  {
    return ECSSUnit.em (dValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String ex (final int nValue)
  {
    return ECSSUnit.ex (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String perc (final int nValue)
  {
    return ECSSUnit.perc (nValue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String perc (final double dValue)
  {
    return ECSSUnit.perc (dValue);
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param sURL
   *          URL to be wrapped. May neither be <code>null</code> nor empty.
   * @return url(...)
   * @deprecated Use {@link CSSURLHelper#getAsCSSURL(String)} instead
   */
  @Deprecated
  @Nonnull
  @Nonempty
  public static String url (@Nonnull final String sURL)
  {
    return CSSURLHelper.getAsCSSURL (sURL);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String colorRGB (@Nonnegative final int nRed,
                                 @Nonnegative final int nGreen,
                                 @Nonnegative final int nBlue)
  {
    return CSSColorHelper.getRGBColorValue (nRed, nGreen, nBlue);
  }

  @Nonnull
  @Nonempty
  @Deprecated
  public static String colorHex (@Nonnegative final int nRed,
                                 @Nonnegative final int nGreen,
                                 @Nonnegative final int nBlue)
  {
    return CSSColorHelper.getHexColorValue (nRed, nGreen, nBlue);
  }
}
