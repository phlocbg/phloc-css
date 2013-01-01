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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.url.ISimpleURL;
import com.phloc.css.parser.ParseUtils;
import com.phloc.css.propertyvalue.CCSSValue;

/**
 * Provides URL handling sanity methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSURLHelper
{
  /** For compatibility reasons, this is set to false */
  public static final boolean DEFAULT_QUOTE_URLS = false;

  private CSSURLHelper ()
  {}

  /**
   * Check if the passed CSS value is an URL value.
   * 
   * @param sValue
   *        The value to be checked.
   * @return <code>true</code> if the passed value starts with "url("
   */
  public static boolean isURLValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    // 5 = "url(".length () + ")".length
    return StringHelper.getLength (sRealValue) > 5 &&
           sRealValue.startsWith (CCSSValue.PREFIX_URL_OPEN) &&
           sRealValue.endsWith (")");
  }

  /**
   * Extract the real URL contained in a CSS URL value.
   * 
   * @param sValue
   *        The value containing the CSS value
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
      final String sStripped = sRealValue.substring (CCSSValue.PREFIX_URL_OPEN.length (), sRealValue.length () - 1);
      // Eventually remove leading and trailing '"' or '''
      return ParseUtils.extractStringValue (sStripped);
    }
    return null;
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param aURL
   *        URL to be wrapped. May not be <code>null</code>.
   * @return <code>url(<i>sURL</i>)</code>
   */
  @Nonnull
  @Nonempty
  @Deprecated
  public static String getAsCSSURL (@Nonnull final ISimpleURL aURL)
  {
    return getAsCSSURL (aURL, DEFAULT_QUOTE_URLS);
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param aURL
   *        URL to be wrapped. May not be <code>null</code>.
   * @param bQuoteURL
   *        if <code>true</code> single quotes are added around the URL
   * @return <code>url(<i>sURL</i>)</code> or <code>url('<i>sURL</i>')</code>
   */
  @Nonnull
  @Nonempty
  public static String getAsCSSURL (@Nonnull final ISimpleURL aURL, final boolean bQuoteURL)
  {
    if (aURL == null)
      throw new NullPointerException ("URL");
    return getAsCSSURL (aURL.getAsString (), bQuoteURL);
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param sURL
   *        URL to be wrapped. May neither be <code>null</code> nor empty.
   * @return <code>url(<i>sURL</i>)</code>
   */
  @Nonnull
  @Nonempty
  @Deprecated
  public static String getAsCSSURL (@Nonnull @Nonempty final String sURL)
  {
    return getAsCSSURL (sURL, DEFAULT_QUOTE_URLS);
  }

  /**
   * Surround the passed URL with the CSS "url(...)"
   * 
   * @param sURL
   *        URL to be wrapped. May neither be <code>null</code> nor empty.
   * @param bQuoteURL
   *        if <code>true</code> single quotes are added around the URL
   * @return <code>url(<i>sURL</i>)</code> or <code>url('<i>sURL</i>')</code>
   */
  @Nonnull
  @Nonempty
  public static String getAsCSSURL (@Nonnull @Nonempty final String sURL, final boolean bQuoteURL)
  {
    if (StringHelper.hasNoText (sURL))
      throw new IllegalArgumentException ("passed URL is empty!");
    if (bQuoteURL)
      return CCSSValue.PREFIX_URL_OPEN + "'" + sURL + "')";
    return CCSSValue.PREFIX_URL_OPEN + sURL + ')';
  }
}
