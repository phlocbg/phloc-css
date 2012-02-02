package com.phloc.css.decl;

import com.phloc.css.ECSSVersion;

public enum ECSSExpressionOperator implements ICSSExpressionMember
{
  SLASH ("/"),
  COMMA (","),
  EQUALS ("=");

  private final String m_sText;

  private ECSSExpressionOperator (final String sText)
  {
    m_sText = sText;
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    return m_sText;
  }

  public static ECSSExpressionOperator fromTextOrNull (final String sText)
  {
    for (final ECSSExpressionOperator eOperator : values ())
      if (eOperator.m_sText.equals (sText))
        return eOperator;
    return null;
  }
}
