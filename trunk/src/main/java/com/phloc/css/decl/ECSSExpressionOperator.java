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
