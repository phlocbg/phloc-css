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
package com.phloc.css.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.url.ISimpleURL;
import com.phloc.css.CCSS;

/**
 * Provides URL handling sanity methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSURLHelper
{
  private CSSURLHelper ()
  {}

  /**
   * Check if the passed CSS value is an URL value.
   * 
   * @param sValue
   *          The value to be checked.
   * @return <code>true</code> if the passed value starts with "url("
   */
  public static boolean isURLValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) &&
           RegExHelper.stringMatchesPattern ("^" + CCSS.PREFIX_URL + "\\(.+\\)$", sRealValue);
  }

  /**
   * Extract the real URL contained in a CSS URL value.
   * 
   * @param sValue
   *          The value containing the CSS value
   * @return <code>null</code> if the passed value is not an URL value
   * @see #isURLValue
   */
  @Nullable
  public static String getURLValue (@Nullable final String sValue)
  {
    if (isURLValue (sValue))
    {
      final String sRealValue = sValue.trim ();
      // Skip leading "url(" and trailing ")"
      return sRealValue.substring (CCSS.PREFIX_URL_OPEN.length (), sRealValue.length () - 1);
    }
    return null;
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param aURL
   *          URL to be wrapped. May not be <code>null</code>.
   * @return <code>url(<i>sURL</i>)</code>
   */
  @Nonnull
  @Nonempty
  public static String getAsCSSURL (@Nonnull final ISimpleURL aURL)
  {
    if (aURL == null)
      throw new NullPointerException ("URL");
    return getAsCSSURL (aURL.getAsString ());
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param sURL
   *          URL to be wrapped. May neither be <code>null</code> nor empty.
   * @return <code>url(<i>sURL</i>)</code>
   */
  @Nonnull
  @Nonempty
  public static String getAsCSSURL (@Nonnull @Nonempty final String sURL)
  {
    if (StringHelper.hasNoText (sURL))
      throw new IllegalArgumentException ("passed URL is empty!");
    return CCSS.PREFIX_URL_OPEN + sURL + ')';
  }
}
