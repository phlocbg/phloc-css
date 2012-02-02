package com.phloc.css.media;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;

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
  AURAL ("aural"),

  /** for blind people */
  BRAILLE ("braille"),

  /** for blind people */
  EMBOSSED ("embossed"),

  /** for PDAs */
  HANDHELD ("handheld"),

  /** for printing */
  PRINT ("print"),

  /** for projection */
  PROJECTION ("projection"),

  /** for normal screen display */
  SCREEN ("screen"),

  /** For computer synthesized voice. As of CSS 3 */
  SPEECH ("speech", ECSSVersion.CSS30),

  /** for text oriented devices */
  TTY ("tty"),

  /** for televisions */
  TV ("tv");

  private String m_sAttrValue;
  private ECSSVersion m_eVersion;

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
    for (final ECSSMedium eMedia : values ())
      if (eMedia.m_sAttrValue.equals (sAttr))
        return eMedia;
    return null;
  }
}
