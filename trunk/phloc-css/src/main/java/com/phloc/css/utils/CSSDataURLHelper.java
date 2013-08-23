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
package com.phloc.css.utils;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.WorkInProgress;

/**
 * Provides data URL handling sanity methods (RFC 2397).
 * 
 * @author Philip Helger
 */
@Immutable
@WorkInProgress
public final class CSSDataURLHelper
{
  /** The default prefix for data URLs */
  public static final String PREFIX_DATA_URL = "data:";

  private CSSDataURLHelper ()
  {}

  /**
   * Check if the passed URL is a data URL. It is checked, whether the passed
   * URL starts with {@value #PREFIX_DATA_URL} (after trimming) and has a comma
   * (',') inside.
   * 
   * @param sURL
   *        The URL to check. May be <code>null</code>.
   * @return <code>true</code> if the passed URL is a data URL,
   *         <code>false</code> if not.
   */
  public static boolean isDataURL (@Nullable final String sURL)
  {
    if (sURL == null)
      return false;
    final String sRealURL = sURL.trim ();
    return sRealURL.startsWith (PREFIX_DATA_URL) && sRealURL.indexOf (',') > 0;
  }
}
