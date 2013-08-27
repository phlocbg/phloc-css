/**
 * Copyright (C) 2006-2013 phloc systems
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

import java.text.NumberFormat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.CGlobal;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.lang.EnumHelper;
import com.phloc.commons.name.IHasName;

/**
 * Enumeration containing all predefined CSS units.<br>
 * Source: http://www.w3.org/TR/css3-values/
 * 
 * @author Philip Helger
 */
public enum ECSSUnit implements IHasName, ICSSVersionAware
{
  /** font size of the element */
  EM ("em", ECSSMetaUnit.FONT_RELATIVE_LENGTH, ECSSVersion.CSS10),
  /** x-height of the element's font */
  EX ("ex", ECSSMetaUnit.FONT_RELATIVE_LENGTH, ECSSVersion.CSS10),
  /** pixels; 1px is equal to 1/96th of 1in */
  PX ("px", ECSSMetaUnit.ABSOLUTE_LENGTH, ECSSVersion.CSS10),
  /** font size of the root element */
  REM ("rem", ECSSMetaUnit.FONT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** Equal to 1% of the width of the initial containing block. */
  VW ("vw", ECSSMetaUnit.VIEWPORT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** Equal to 1% of the height of the initial containing block. */
  VH ("vh", ECSSMetaUnit.VIEWPORT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** Equal to the smaller of ‘vw’ or ‘vh’. */
  VMIN ("vmin", ECSSMetaUnit.VIEWPORT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** width of the "0" glyph in the element's font */
  CH ("ch", ECSSMetaUnit.FONT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** inches; 1in is equal to 2.54cm */
  LENGTH_IN ("in", ECSSMetaUnit.ABSOLUTE_LENGTH, ECSSVersion.CSS10),
  /** centimeters */
  LENGTH_CM ("cm", ECSSMetaUnit.ABSOLUTE_LENGTH, ECSSVersion.CSS10),
  /** millimeters; 10 millimeters = 1 centimeter */
  LENGTH_MM ("mm", ECSSMetaUnit.ABSOLUTE_LENGTH, ECSSVersion.CSS10),
  /** points; 1pt is equal to 1/72nd of 1in */
  LENGTH_PT ("pt", ECSSMetaUnit.ABSOLUTE_LENGTH, ECSSVersion.CSS10),
  /** picas; 1pc is equal to 12pt */
  LENGTH_PC ("pc", ECSSMetaUnit.ABSOLUTE_LENGTH, ECSSVersion.CSS10),
  /** percentage */
  PERCENTAGE ("%", ECSSMetaUnit.PERCENTAGE, ECSSVersion.CSS10),
  /** Degrees. There are 360 degrees in a full circle. */
  ANGLE_DEG ("deg", ECSSMetaUnit.ANGLE, ECSSVersion.CSS21),
  /** Radians. There are 2π radians in a full circle. */
  ANGLE_RAD ("rad", ECSSMetaUnit.ANGLE, ECSSVersion.CSS21),
  /**
   * Gradians, also known as "gons" or "grades". There are 400 gradians in a
   * full circle.
   */
  ANGLE_GRAD ("grad", ECSSMetaUnit.ANGLE, ECSSVersion.CSS21),
  /** Turns. There is 1 turn in a full circle. */
  ANGLE_TURN ("turn", ECSSMetaUnit.ANGLE, ECSSVersion.CSS30),
  /** Milliseconds. There are 1000 milliseconds in a second. */
  TIME_MS ("ms", ECSSMetaUnit.TIME, ECSSVersion.CSS21),
  /** Seconds. */
  TIME_S ("s", ECSSMetaUnit.TIME, ECSSVersion.CSS21),
  /** Hertz. It represents the number of occurrences per second. */
  FREQ_HZ ("hz", ECSSMetaUnit.FREQUENZY, ECSSVersion.CSS21),
  /** Kilohertz. A kiloHertz is 1000 Hertz. */
  FREQ_KHZ ("khz", ECSSMetaUnit.FREQUENZY, ECSSVersion.CSS21),
  /** Dots per CSS inch */
  DPI ("dpi", ECSSMetaUnit.RESOLUTION, ECSSVersion.CSS30),
  /** Dots per CSS centimeter */
  DPCM ("dpcm", ECSSMetaUnit.RESOLUTION, ECSSVersion.CSS30),
  /** Dots per pixel centimeter */
  DPPX ("dppx", ECSSMetaUnit.RESOLUTION, ECSSVersion.CSS30);

  // synonyms
  /** Length in pixel */
  public static final ECSSUnit LENGTH_PX = PX;

  private final String m_sName;
  private final ECSSMetaUnit m_eMetaUnit;
  private final ECSSVersion m_eVersion;

  private ECSSUnit (@Nonnull @Nonempty final String sName,
                    @Nonnull final ECSSMetaUnit eMetaUnit,
                    @Nonnull final ECSSVersion eVersion)
  {
    m_sName = sName;
    m_eMetaUnit = eMetaUnit;
    m_eVersion = eVersion;
  }

  /**
   * @return The name of the unit as it should be used in CSS. E.g. "px"
   */
  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nonnull
  public ECSSMetaUnit getMetaUnit ()
  {
    return m_eMetaUnit;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return m_eVersion;
  }

  /**
   * Get the passed value formatted with this unit
   * 
   * @param nValue
   *        Value to format
   * @return <code>value + getName()</code>
   */
  @Nonnull
  @Nonempty
  public String format (final int nValue)
  {
    return Integer.toString (nValue) + m_sName;
  }

  /**
   * Get the passed value formatted with this unit. Always '.' is used as the
   * separator.
   * 
   * @param dValue
   *        Value to format
   * @return <code>value + getName()</code>
   */
  @Nonnull
  @Nonempty
  public String format (final double dValue)
  {
    // Always format with English locale ('.' as decimal separator)
    return NumberFormat.getNumberInstance (CGlobal.LOCALE_FIXED_NUMBER_FORMAT).format (dValue) + m_sName;
  }

  @Nullable
  public static ECSSUnit getFromNameOrNull (@Nullable final String sName)
  {
    return EnumHelper.getFromNameOrNull (ECSSUnit.class, sName);
  }

  @Nullable
  public static ECSSUnit getFromNameOrDefault (@Nullable final String sName, @Nullable final ECSSUnit eDefault)
  {
    return EnumHelper.getFromNameOrDefault (ECSSUnit.class, sName, eDefault);
  }

  // --- [utility methods] ---

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "em"</code>
   */
  @Nonnull
  @Nonempty
  public static String em (final int nValue)
  {
    return EM.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "em"</code>
   */
  @Nonnull
  @Nonempty
  public static String em (final double dValue)
  {
    return EM.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "ex"</code>
   */
  @Nonnull
  @Nonempty
  public static String ex (final int nValue)
  {
    return EX.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "ex"</code>
   */
  @Nonnull
  @Nonempty
  public static String ex (final double dValue)
  {
    return EX.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "px"</code>
   */
  @Nonnull
  @Nonempty
  public static String px (final int nValue)
  {
    return PX.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "px"</code>
   */
  @Nonnull
  @Nonempty
  public static String px (final double dValue)
  {
    return PX.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "rem"</code>
   */
  @Nonnull
  @Nonempty
  public static String rem (final int nValue)
  {
    return REM.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "rem"</code>
   */
  @Nonnull
  @Nonempty
  public static String rem (final double dValue)
  {
    return REM.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "vw"</code>
   */
  @Nonnull
  @Nonempty
  public static String vw (final int nValue)
  {
    return VW.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "vw"</code>
   */
  @Nonnull
  @Nonempty
  public static String vw (final double dValue)
  {
    return VW.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "vh"</code>
   */
  @Nonnull
  @Nonempty
  public static String vh (final int nValue)
  {
    return VH.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "vh"</code>
   */
  @Nonnull
  @Nonempty
  public static String vh (final double dValue)
  {
    return VH.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "vmin"</code>
   */
  @Nonnull
  @Nonempty
  public static String vmin (final int nValue)
  {
    return VMIN.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "vmin"</code>
   */
  @Nonnull
  @Nonempty
  public static String vmin (final double dValue)
  {
    return VMIN.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "ch"</code>
   */
  @Nonnull
  @Nonempty
  public static String ch (final int nValue)
  {
    return CH.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "ch"</code>
   */
  @Nonnull
  @Nonempty
  public static String ch (final double dValue)
  {
    return CH.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "in"</code>
   */
  @Nonnull
  @Nonempty
  public static String in (final int nValue)
  {
    return LENGTH_IN.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "in"</code>
   */
  @Nonnull
  @Nonempty
  public static String in (final double dValue)
  {
    return LENGTH_IN.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "cm"</code>
   */
  @Nonnull
  @Nonempty
  public static String cm (final int nValue)
  {
    return LENGTH_CM.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "cm"</code>
   */
  @Nonnull
  @Nonempty
  public static String cm (final double dValue)
  {
    return LENGTH_CM.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "mm"</code>
   */
  @Nonnull
  @Nonempty
  public static String mm (final int nValue)
  {
    return LENGTH_MM.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "mm"</code>
   */
  @Nonnull
  @Nonempty
  public static String mm (final double dValue)
  {
    return LENGTH_MM.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "pt"</code>
   */
  @Nonnull
  @Nonempty
  public static String pt (final int nValue)
  {
    return LENGTH_PT.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "pt"</code>
   */
  @Nonnull
  @Nonempty
  public static String pt (final double dValue)
  {
    return LENGTH_PT.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "pc"</code>
   */
  @Nonnull
  @Nonempty
  public static String pc (final int nValue)
  {
    return LENGTH_PC.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "pc"</code>
   */
  @Nonnull
  @Nonempty
  public static String pc (final double dValue)
  {
    return LENGTH_PC.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "%"</code>
   */
  @Nonnull
  @Nonempty
  public static String perc (final int nValue)
  {
    return PERCENTAGE.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "%"</code>
   */
  @Nonnull
  @Nonempty
  public static String perc (final double dValue)
  {
    return PERCENTAGE.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "deg"</code>
   */
  @Nonnull
  @Nonempty
  public static String deg (final int nValue)
  {
    return ANGLE_DEG.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "deg"</code>
   */
  @Nonnull
  @Nonempty
  public static String deg (final double dValue)
  {
    return ANGLE_DEG.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "rad"</code>
   */
  @Nonnull
  @Nonempty
  public static String rad (final int nValue)
  {
    return ANGLE_RAD.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "rad"</code>
   */
  @Nonnull
  @Nonempty
  public static String rad (final double dValue)
  {
    return ANGLE_RAD.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "grad"</code>
   */
  @Nonnull
  @Nonempty
  public static String grad (final int nValue)
  {
    return ANGLE_GRAD.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "grad"</code>
   */
  @Nonnull
  @Nonempty
  public static String grad (final double dValue)
  {
    return ANGLE_GRAD.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "urn"</code>
   */
  @Nonnull
  @Nonempty
  public static String turn (final int nValue)
  {
    return ANGLE_TURN.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "urn"</code>
   */
  @Nonnull
  @Nonempty
  public static String turn (final double dValue)
  {
    return ANGLE_TURN.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "ms"</code>
   */
  @Nonnull
  @Nonempty
  public static String ms (final int nValue)
  {
    return TIME_MS.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "ms"</code>
   */
  @Nonnull
  @Nonempty
  public static String ms (final double dValue)
  {
    return TIME_MS.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "s"</code>
   */
  @Nonnull
  @Nonempty
  public static String s (final int nValue)
  {
    return TIME_S.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "s"</code>
   */
  @Nonnull
  @Nonempty
  public static String s (final double dValue)
  {
    return TIME_S.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "hz"</code>
   */
  @Nonnull
  @Nonempty
  public static String hz (final int nValue)
  {
    return FREQ_HZ.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "hz"</code>
   */
  @Nonnull
  @Nonempty
  public static String hz (final double dValue)
  {
    return FREQ_HZ.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "khz"</code>
   */
  @Nonnull
  @Nonempty
  public static String khz (final int nValue)
  {
    return FREQ_KHZ.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "khz"</code>
   */
  @Nonnull
  @Nonempty
  public static String khz (final double dValue)
  {
    return FREQ_KHZ.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "dpi"</code>
   */
  @Nonnull
  @Nonempty
  public static String dpi (final int nValue)
  {
    return DPI.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "dpi"</code>
   */
  @Nonnull
  @Nonempty
  public static String dpi (final double dValue)
  {
    return DPI.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "dpcm"</code>
   */
  @Nonnull
  @Nonempty
  public static String dpcm (final int nValue)
  {
    return DPCM.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "dpcm"</code>
   */
  @Nonnull
  @Nonempty
  public static String dpcm (final double dValue)
  {
    return DPCM.format (dValue);
  }

  /**
   * @param nValue
   *        value to format
   * @return <code>value + "dppx"</code>
   */
  @Nonnull
  @Nonempty
  public static String dppx (final int nValue)
  {
    return DPPX.format (nValue);
  }

  /**
   * @param dValue
   *        value to format
   * @return <code>value + "dppx"</code>
   */
  @Nonnull
  @Nonempty
  public static String dppx (final double dValue)
  {
    return DPPX.format (dValue);
  }

  /**
   * @return "0" without a unit
   */
  @Nonnull
  @Nonempty
  public static String zero ()
  {
    return "0";
  }
}
