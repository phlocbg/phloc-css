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
package com.phloc.css.media;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.annotations.DeprecatedInCSS21;

/**
 * Defines all default CSS media types.
 * 
 * @author philip
 */
public enum ECSSMedium implements ICSSVersionAware
{
  /** for all media types */
  ALL ("all"),

  /**
   * For computer synthesized voice. Deprecated in CSS 2.1. Is "speech" in CSS
   * 3.
   */
  @DeprecatedInCSS21
  AURAL ("aural"),

  /** for blind people */
  BRAILLE ("braille"),

  /** for blind people */
  EMBOSSED ("embossed"),

  /** for PDAs */
  HANDHELD ("handheld"),

  /** for printing */
  PRINT ("print", ECSSVersion.HTML4),

  /** for projection */
  PROJECTION ("projection"),

  /** for normal screen display */
  SCREEN ("screen", ECSSVersion.HTML4),

  /** For computer synthesized voice. */
  SPEECH ("speech"),

  /** for text oriented devices */
  TTY ("tty"),

  /** for televisions */
  TV ("tv");

  private final String m_sAttrValue;
  private final ECSSVersion m_eVersion;

  private ECSSMedium (@Nonnull @Nonempty final String sAttrValue)
  {
    this (sAttrValue, ECSSVersion.CSS21);
  }

  private ECSSMedium (@Nonnull @Nonempty final String sAttrValue, @Nonnull final ECSSVersion eVersion)
  {
    m_sAttrValue = sAttrValue;
    m_eVersion = eVersion;
  }

  @Nonnull
  @Nonempty
  public String getAttrValue ()
  {
    return m_sAttrValue;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return m_eVersion;
  }

  @Nullable
  public static ECSSMedium getFromAttrOrNull (@Nullable final String sAttr)
  {
    if (StringHelper.hasText (sAttr))
      for (final ECSSMedium eMedia : values ())
        if (eMedia.m_sAttrValue.equals (sAttr))
          return eMedia;
    return null;
  }
}
