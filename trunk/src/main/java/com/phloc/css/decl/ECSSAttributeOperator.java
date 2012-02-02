package com.phloc.css.decl;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.CSSVersionHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriteable;

public enum ECSSAttributeOperator implements ICSSVersionAware, ICSSWriteable
{
  EQUALS ("="),
  INCLUDES ("~="),
  DASHMATCH ("|="),
  BEGINMATCH ("^=", ECSSVersion.CSS30),
  ENDMATCH ("$=", ECSSVersion.CSS30),
  CONTAINSMATCH ("*=");

  private String m_sText;
  private ECSSVersion m_eVersion;

  private ECSSAttributeOperator (@Nonnull @Nonempty final String sText)
  {
    this (sText, ECSSVersion.CSS21);
  }

  private ECSSAttributeOperator (@Nonnull @Nonempty final String sText, @Nonnull final ECSSVersion eVersion)
  {
    m_sText = sText;
    m_eVersion = eVersion;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return m_eVersion;
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    CSSVersionHelper.checkVersionRequirements (eVersion, this);
    return m_sText;
  }

  public static ECSSAttributeOperator fromTextOrNull (final String sText)
  {
    for (final ECSSAttributeOperator eOperator : values ())
      if (eOperator.m_sText.equals (sText))
        return eOperator;
    return null;
  }
}
