/**
 * Copyright (C) 2006-2013 phloc systems
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

import javax.annotation.Nonnull;

import com.phloc.commons.version.IHasVersion;
import com.phloc.commons.version.Version;

/**
 * Contains the different CSS versions that may be of relevance.
 * 
 * @author philip
 */
public enum ECSSVersion implements IHasVersion
{
  // Sort fields according to the version!
  HTML4 (new Version (1, 0)),
  CSS21 (new Version (2, 1)),
  CSS30 (new Version (3, 0));

  /** Default version is CSS 3.0 */
  @Nonnull
  public static final ECSSVersion LATEST = CSS30;

  private final Version m_aVersion;

  private ECSSVersion (@Nonnull final Version aVersion)
  {
    m_aVersion = aVersion;
  }

  @Nonnull
  public Version getVersion ()
  {
    return m_aVersion;
  }

  @Nonnull
  public String getVersionString ()
  {
    return m_aVersion.getMajor () + "." + m_aVersion.getMinor ();
  }
}
