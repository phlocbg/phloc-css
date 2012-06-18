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

import java.text.NumberFormat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.CGlobal;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.lang.EnumHelper;
import com.phloc.commons.name.IHasName;

/**
 * Enumeration containing all predefined CSS units.
 * 
 * @author philip
 */
public enum ECSSUnit implements IHasName, ICSSVersionAware
{
  EM ("em", ECSSMetaUnit.FONT_RELATIVE_LENGTH),
  EX ("ex", ECSSMetaUnit.FONT_RELATIVE_LENGTH),
  PX ("px", ECSSMetaUnit.ABSOLUTE_LENGTH),
  REM ("rem", ECSSMetaUnit.FONT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** Equal to 1% of the width of the initial containing block. */
  VW ("vw", ECSSMetaUnit.VIEWPORT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** Equal to 1% of the height of the initial containing block. */
  VH ("vh", ECSSMetaUnit.VIEWPORT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  /** Equal to the smaller of ‘vw’ or ‘vh’. */
  VMIN ("vmin", ECSSMetaUnit.VIEWPORT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  CH ("ch", ECSSMetaUnit.FONT_RELATIVE_LENGTH, ECSSVersion.CSS30),
  LENGTH_IN ("in", ECSSMetaUnit.ABSOLUTE_LENGTH),
  LENGTH_CM ("cm", ECSSMetaUnit.ABSOLUTE_LENGTH),
  LENGTH_MM ("mm", ECSSMetaUnit.ABSOLUTE_LENGTH),
  LENGTH_PT ("pt", ECSSMetaUnit.ABSOLUTE_LENGTH),
  LENGTH_PC ("pc", ECSSMetaUnit.ABSOLUTE_LENGTH),
  PERCENTAGE ("%", ECSSMetaUnit.PERCENTAGE),
  ANGLE_DEG ("deg", ECSSMetaUnit.ANGLE),
  ANGLE_RAD ("rad", ECSSMetaUnit.ANGLE),
  ANGLE_GRAD ("grad", ECSSMetaUnit.ANGLE),
  ANGLE_TURN ("turn", ECSSMetaUnit.ANGLE, ECSSVersion.CSS30),
  TIME_MS ("ms", ECSSMetaUnit.TIME),
  TIME_S ("s", ECSSMetaUnit.TIME),
  FREQ_HZ ("hz", ECSSMetaUnit.FREQUENZY),
  FREQ_KHZ ("khz", ECSSMetaUnit.FREQUENZY),
  /** Dots per CSS inch */
  DPI ("dpi", ECSSMetaUnit.RESOLUTION, ECSSVersion.CSS30),
  /** Dots per CSS centimetre */
  DPCM ("dpcm", ECSSMetaUnit.RESOLUTION, ECSSVersion.CSS30),
  /** Dots per pixel centimetre */
  DPPX ("dppx", ECSSMetaUnit.RESOLUTION, ECSSVersion.CSS30);

  private final String m_sName;
  private ECSSMetaUnit m_eMetaUnit;
  private final ECSSVersion m_eVersion;

  private ECSSUnit (@Nonnull @Nonempty final String sName, @Nonnull final ECSSMetaUnit eMetaUnit)
  {
    this (sName, eMetaUnit, ECSSVersion.CSS21);
  }

  private ECSSUnit (@Nonnull @Nonempty final String sName,
                    @Nonnull final ECSSMetaUnit eMetaUnit,
                    @Nonnull final ECSSVersion eVersion)
  {
    m_sName = sName;
    m_eMetaUnit = eMetaUnit;
    m_eVersion = eVersion;
  }

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

  @Nonnull
  @Nonempty
  public String format (final int nValue)
  {
    return Integer.toString (nValue) + m_sName;
  }

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

  @Nonnull
  @Nonempty
  public static String pt (final int nValue)
  {
    return LENGTH_PT.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String pc (final int nValue)
  {
    return LENGTH_PC.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String in (final int nValue)
  {
    return LENGTH_IN.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String mm (final int nValue)
  {
    return LENGTH_MM.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String cm (final int nValue)
  {
    return LENGTH_CM.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String px (final int nValue)
  {
    return PX.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String em (final int nValue)
  {
    return EM.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String em (final double dValue)
  {
    return EM.format (dValue);
  }

  @Nonnull
  @Nonempty
  public static String ex (final int nValue)
  {
    return EX.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String perc (final int nValue)
  {
    return PERCENTAGE.format (nValue);
  }

  @Nonnull
  @Nonempty
  public static String perc (final double dValue)
  {
    return PERCENTAGE.format (dValue);
  }
}
