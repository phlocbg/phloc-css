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
import com.phloc.commons.name.IHasName;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.CSSWriterSettings;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;

public enum ECSSSelectorCombinator implements ICSSSelectorMember, ICSSVersionAware, IHasName
{
  PLUS ("+"),
  GREATER (">"),
  TILDE ("~", ECSSVersion.CSS30),
  BLANK (" ");

  private final String m_sName;
  private final ECSSVersion m_eVersion;

  private ECSSSelectorCombinator (@Nonnull @Nonempty final String sName)
  {
    this (sName, ECSSVersion.CSS21);
  }

  private ECSSSelectorCombinator (@Nonnull @Nonempty final String sName, @Nonnull final ECSSVersion eVersion)
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
  public String getAsCSSString (@Nonnull final CSSWriterSettings aSettings)
  {
    aSettings.checkVersionRequirements (this);
    return m_sName;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return m_eVersion;
  }

  @Nullable
  public static ECSSSelectorCombinator getFromNameOrNull (@Nullable final String sName)
  {
    if (StringHelper.hasText (sName))
      for (final ECSSSelectorCombinator eCombinator : values ())
        if (eCombinator.m_sName.equals (sName))
          return eCombinator;
    return null;
  }
}
