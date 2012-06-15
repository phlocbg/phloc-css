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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;

public enum ECSSAttributeOperator implements ICSSVersionAware, ICSSWriteable
{
  EQUALS ("="),
  INCLUDES ("~="),
  DASHMATCH ("|="),
  BEGINMATCH ("^=", ECSSVersion.CSS30),
  ENDMATCH ("$=", ECSSVersion.CSS30),
  CONTAINSMATCH ("*=");

  private final String m_sText;
  private final ECSSVersion m_eVersion;

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

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings)
  {
    aSettings.checkVersionRequirements (this);
    return m_sText;
  }

  @Nullable
  public static ECSSAttributeOperator getFromTextOrNull (@Nullable final String sText)
  {
    if (StringHelper.hasText (sText))
      for (final ECSSAttributeOperator eOperator : values ())
        if (eOperator.m_sText.equals (sText))
          return eOperator;
    return null;
  }
}
