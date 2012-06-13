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
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.html.CHTMLColors;

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

  // values
  public static final String _100 = "100";
  public static final String _200 = "200";
  public static final String _300 = "300";
  public static final String _400 = "400";
  public static final String _500 = "500";
  public static final String _600 = "600";
  public static final String _700 = "700";
  public static final String _800 = "800";
  public static final String _900 = "900";
  public static final String ABSOLUTE = "absolute";
  public static final String ALWAYS = "always";
  public static final String AUTO = "auto";
  public static final String BASELINE = "baseline";
  public static final String BLINK = "blink";
  public static final String BLOCK = "block";
  public static final String BOLD = "bold";
  public static final String BOLDER = "bolder";
  public static final String BOTH = "both";
  public static final String BOTTOM = "bottom";
  public static final String CAPITALIZE = "capitalize";
  public static final String CENTER = "center";
  public static final String CIRCLE = "circle";
  public static final String COLLAPSE = "collapse";
  public static final String CROSSHAIR = "crosshair";
  public static final String CURSIVE = "cursive";
  public static final String DASHED = "dashed";
  public static final String DECIMAL = "decimal";
  public static final String DEFAULT = "default";
  public static final String DISC = "disc";
  public static final String DOTTED = "dotted";
  public static final String DOUBLE = "double";
  public static final String E_RESIZE = "e-resize";
  public static final String FANTASY = "fantasy";
  public static final String FIXED = "fixed";
  public static final String GROOVE = "groove";
  public static final String HELP = "help";
  public static final String HIDDEN = "hidden";
  public static final String HIDE = "hide";
  public static final String INHERIT = "inherit";
  public static final String INLINE = "inline";
  public static final String INLINE_BLOCK = "inline-block";
  public static final String INLINE_TABLE = "inline-table";
  public static final String INSET = "inset";
  public static final String INSIDE = "inside";
  public static final String INVERT = "invert";
  public static final String ITALIC = "italic";
  public static final String JUSTIFY = "justify";
  public static final String LARGE = "large";
  public static final String LARGER = "larger";
  public static final String LEFT = "left";
  public static final String LIGHTER = "lighter";
  public static final String LINE_THROUGH = "line-through";
  public static final String LIST_ITEM = "list-item";
  public static final String LOWER_ALPHA = "lower-alpha";
  public static final String LOWER_LATIN = "lower-latin";
  public static final String LOWER_ROMAN = "lower-roman";
  public static final String LOWERCASE = "lowercase";
  public static final String LTR = "ltr";
  public static final String MEDIUM = "medium";
  public static final String MIDDLE = "middle";
  public static final String MONOSPACE = "monospace";
  public static final String MOVE = "move";
  public static final String N_RESIZE = "n-resize";
  public static final String NE_RESIZE = "ne-resize";
  public static final String NO_REPEAT = "no-repeat";
  public static final String NONE = "none";
  public static final String NORMAL = "normal";
  public static final String NOWRAP = "nowrap";
  public static final String NW_RESIZE = "nw-resize";
  public static final String OBLIQUE = "oblique";
  public static final String ONCE = "once";
  public static final String OUTSET = "outset";
  public static final String OUTSIDE = "outside";
  public static final String OVERLINE = "overline";
  public static final String POINTER = "pointer";
  public static final String PRE = "pre";
  public static final String PRE_LINE = "pre-line";
  public static final String PRE_WRAP = "pre-wrap";
  public static final String PROGRESS = "progress";
  public static final String RELATIVE = "relative";
  public static final String REPEAT = "repeat";
  public static final String REPEAT_X = "repeat-x";
  public static final String REPEAT_Y = "repeat-y";
  public static final String RIDGE = "ridge";
  public static final String RIGHT = "right";
  public static final String RTL = "rtl";
  public static final String RUN_IN = "run-in";
  public static final String S_RESIZE = "s-resize";
  public static final String SANS_SERIF = "sans-serif";
  public static final String SCROLL = "scroll";
  public static final String SE_RESIZE = "se-resize";
  public static final String SEPARATE = "separate";
  public static final String SERIF = "serif";
  public static final String SHOW = "show";
  public static final String SMALL = "small";
  public static final String SMALL_CAPS = "small-caps";
  public static final String SMALLER = "smaller";
  public static final String SOLID = "solid";
  public static final String SQUARE = "square";
  public static final String STATIC = "static";
  public static final String SUB = "sub";
  public static final String SUPER = "super";
  public static final String SW_RESIZE = "sw-resize";
  public static final String TABLE = "table";
  public static final String TABLE_CAPTION = "table-caption";
  public static final String TABLE_CELL = "table-cell";
  public static final String TABLE_COLUMN = "table-column";
  public static final String TABLE_COLUMN_GROUP = "table-column-group";
  public static final String TABLE_FOOTER_GROUP = "table-footer-group";
  public static final String TABLE_HEADER_GROUP = "table-header-group";
  public static final String TABLE_ROW = "table-row";
  public static final String TABLE_ROW_GROUP = "table-row-group";
  public static final String TEXT = "text";
  public static final String TEXT_BOTTOM = "text-bottom";
  public static final String TEXT_TOP = "text-top";
  public static final String THICK = "thick";
  public static final String THIN = "thin";
  public static final String TOP = "top";
  public static final String TRANSPARENT = "transparent";
  public static final String UNDERLINE = "underline";
  public static final String UPPER_ALPHA = "upper-alpha";
  public static final String UPPER_LATIN = "upper-latin";
  public static final String UPPER_ROMAN = "upper-roman";
  public static final String UPPERCASE = "uppercase";
  public static final String VISIBLE = "visible";
  public static final String W_RESIZE = "w-resize";
  public static final String WAIT = "wait";
  public static final String X_LARGE = "x-large";
  public static final String X_SMALL = "x-small";
  public static final String XX_LARGE = "xx-large";
  public static final String XX_SMALL = "xx-small";

  public static final String PREFIX_RECT = "rect";
  public static final String PREFIX_URL = "url";
  public static final String PREFIX_URL_OPEN = PREFIX_URL + '(';
  public static final String PREFIX_RGB = "rgb";

  public static final String PX0 = px (0);
  public static final String PERC100 = perc (100);

  private CCSS ()
  {}

  @Nullable
  private static ECSSUnit _findUnitWithPerc (@Nonnull final String sValue)
  {
    // sValue cannot be null here!
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      if (sValue.endsWith (eUnit.getText ()))
        return eUnit;
    return null;
  }

  @Nullable
  private static ECSSUnit _findUnitWithoutPerc (@Nonnull final String sValue)
  {
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      if (eUnit != ECSSUnit.PERCENTAGE)
        if (sValue.endsWith (eUnit.getText ()))
          return eUnit;
    return null;
  }

  public static boolean isNumberWithUnitValue (@Nullable final String sValue, final boolean bWithPerc)
  {
    String sRealValue = StringHelper.trim (sValue);
    if (StringHelper.hasText (sRealValue))
    {
      final ECSSUnit eUnit = bWithPerc ? _findUnitWithPerc (sRealValue) : _findUnitWithoutPerc (sRealValue);
      if (eUnit != null)
        sRealValue = sRealValue.substring (0, sRealValue.length () - eUnit.getText ().length ()).trim ();
      return isNumberValue (sRealValue);
    }
    return false;
  }

  public static boolean isNumberValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && StringHelper.isDouble (sRealValue);
  }

  public static boolean isColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           (isRGBColorValue (sRealValue) || isHexColorValue (sRealValue) || CHTMLColors.isDefaultColorName (sRealValue));
  }

  public static boolean isRGBColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" + PREFIX_RGB + "\\s*\\((\\s*[0-9]+%?\\s*,){2}\\s*[0-9]+%?\\s*\\)$",
                                             sRealValue);
  }

  public static boolean isHexColorValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" + CHTMLColors.PREFIX_HEX + "[0-9a-fA-F]{1,6}$", sRealValue);
  }

  /**
   * Extract the real URL contained in a URL value.
   * 
   * @param sValue
   *          The value containing the CSS value
   * @return <code>null</code> if the passed value is not an URL value
   * @see #isURLValue(String)
   */
  @Nullable
  public static String getURLValue (@Nullable final String sValue)
  {
    if (isURLValue (sValue))
    {
      final String sRealValue = sValue.trim ();
      // Skip leading "url(" and trailing ")"
      return sRealValue.substring (PREFIX_URL_OPEN.length (), sRealValue.length () - 1);
    }
    return null;
  }

  /**
   * Check if the passed value is an URL value.
   * 
   * @param sValue
   *          The value to be checked.
   * @return <code>true</code> if the passed value starts with "url("
   */
  public static boolean isURLValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" + PREFIX_URL + "\\(.+\\)$", sRealValue);
  }

  public static boolean isRectValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" +
                                             PREFIX_RECT +
                                             "\\s*\\((\\s*[0-9]+[a-z%]*\\s*,){3}\\s*[0-9]+[a-z%]*\\s*\\)$", sRealValue);
  }

  @Nonnull
  @Nonempty
  public static String pt (final int nValue)
  {
    return ECSSUnit.LENGTH_PT.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String pc (final int nValue)
  {
    return ECSSUnit.LENGTH_PC.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String in (final int nValue)
  {
    return ECSSUnit.LENGTH_IN.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String mm (final int nValue)
  {
    return ECSSUnit.LENGTH_MM.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String cm (final int nValue)
  {
    return ECSSUnit.LENGTH_CM.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String px (final int nValue)
  {
    return ECSSUnit.PX.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String em (final int nValue)
  {
    return ECSSUnit.EM.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String em (final double dValue)
  {
    return ECSSUnit.EM.format (dValue);
  }

  @Nonnull
  @Nonempty
  public static String ex (final int nValue)
  {
    return ECSSUnit.EX.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String perc (final int nValue)
  {
    return ECSSUnit.PERCENTAGE.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String perc (final double dValue)
  {
    return ECSSUnit.PERCENTAGE.format (dValue);
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param sURL
   *          URL to be wrapped. May neither be <code>null</code> nor empty.
   * @return url(...)
   */
  @Nonnull
  @Nonempty
  public static String url (@Nonnull final String sURL)
  {
    if (StringHelper.hasNoText (sURL))
      throw new IllegalArgumentException ("passed URL is empty!");
    return PREFIX_URL_OPEN + sURL + ')';
  }

  @Nonnull
  @Nonempty
  public static String colorRGB (@Nonnegative final int r, @Nonnegative final int g, @Nonnegative final int b)
  {
    if (r < 0 || r > 255)
      throw new IllegalArgumentException ("Invalid red: " + r);
    if (g < 0 || g > 255)
      throw new IllegalArgumentException ("Invalid green: " + g);
    if (b < 0 || b > 255)
      throw new IllegalArgumentException ("Invalid blue: " + b);

    return new StringBuilder (16).append (PREFIX_RGB)
                                 .append ('(')
                                 .append (r)
                                 .append (',')
                                 .append (g)
                                 .append (',')
                                 .append (b)
                                 .append (')')
                                 .toString ();
  }

  @Nonnull
  @Nonempty
  public static String colorHex (@Nonnegative final int nRed,
                                 @Nonnegative final int nGreen,
                                 @Nonnegative final int nBlue)
  {
    if (nRed < 0 || nRed > 255)
      throw new IllegalArgumentException ("Invalid red: " + nRed);
    if (nGreen < 0 || nGreen > 255)
      throw new IllegalArgumentException ("Invalid green: " + nGreen);
    if (nBlue < 0 || nBlue > 255)
      throw new IllegalArgumentException ("Invalid blue: " + nBlue);

    return new StringBuilder (CHTMLColors.HEXVALUE_LENGTH).append (CHTMLColors.PREFIX_HEX)
                                                          .append (StringHelper.getHexStringLeadingZero (nRed, 2))
                                                          .append (StringHelper.getHexStringLeadingZero (nGreen, 2))
                                                          .append (StringHelper.getHexStringLeadingZero (nBlue, 2))
                                                          .toString ();
  }
}
