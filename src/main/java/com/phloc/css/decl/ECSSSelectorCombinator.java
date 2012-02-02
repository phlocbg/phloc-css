package com.phloc.css.decl;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.CSSVersionHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;

public enum ECSSSelectorCombinator implements ICSSSelectorMember, ICSSVersionAware
{
  PLUS ("+"),
  GREATER (">"),
  TILDE ("~", ECSSVersion.CSS30),
  BLANK (" ");

  private String m_sText;
  private ECSSVersion m_eVersion;

  private ECSSSelectorCombinator (@Nonnull @Nonempty final String sText)
  {
    this (sText, ECSSVersion.CSS21);
  }

  private ECSSSelectorCombinator (@Nonnull @Nonempty final String sText, @Nonnull final ECSSVersion eVersion)
  {
    m_sText = sText;
    m_eVersion = eVersion;
  }

  @Nonnull
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    CSSVersionHelper.checkVersionRequirements (eVersion, this);
    return m_sText;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return m_eVersion;
  }

  public static ECSSSelectorCombinator fromTextOrNull (final String sText)
  {
    for (final ECSSSelectorCombinator eCombinator : values ())
      if (eCombinator.m_sText.equals (sText))
        return eCombinator;
    return null;
  }
}
