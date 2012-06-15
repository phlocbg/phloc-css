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
  EM ("em"),
  EX ("ex"),
  PX ("px"),
  GD ("gd", ECSSVersion.CSS30),
  REM ("rem", ECSSVersion.CSS30),
  VW ("vw", ECSSVersion.CSS30),
  VH ("vh", ECSSVersion.CSS30),
  VM ("vm", ECSSVersion.CSS30),
  CH ("ch", ECSSVersion.CSS30),
  /** Dots per CSS inch */
  DPI ("dpi", ECSSVersion.CSS30),
  /** Dots per CSS centimetre */
  DPCM ("dpcm", ECSSVersion.CSS30),
  LENGTH_IN ("in"),
  LENGTH_CM ("cm"),
  LENGTH_MM ("mm"),
  LENGTH_PT ("pt"),
  LENGTH_PC ("pc"),
  PERCENTAGE ("%"),
  ANGLE_DEG ("deg"),
  ANGLE_RAD ("rad"),
  ANGLE_GRAD ("grad"),
  ANGLE_TURN ("turn", ECSSVersion.CSS30),
  TIME_MS ("ms"),
  TIME_S ("s"),
  FREQ_HZ ("hz"),
  FREQ_KHZ ("khz");

  private final String m_sName;
  private final ECSSVersion m_eVersion;

  private ECSSUnit (@Nonnull @Nonempty final String sName)
  {
    this (sName, ECSSVersion.CSS21);
  }

  private ECSSUnit (@Nonnull @Nonempty final String sName, @Nonnull final ECSSVersion eVersion)
  {
    m_sName = sName;
    m_eVersion = eVersion;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
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
}
