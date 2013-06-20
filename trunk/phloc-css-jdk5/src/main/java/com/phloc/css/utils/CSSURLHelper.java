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
 * @author Philip Helger
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
      return ParseUtils.trimUrl (sValue);
    }
    return null;
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
   * Check if the passed character is a valid character inside a URL.
   * 
   * @param c
   *        The character to be checked.
   * @return <code>true</code> if the passed character can be contained inside a
   *         URL, <code>false</code> otherwise.
   */
  public static boolean isValidCSSURLChar (final char c)
  {
    return c == '!' ||
           c == '#' ||
           c == '$' ||
           c == '%' ||
           c == '&' ||
           (c >= '*' && c <= '[') ||
           (c >= ']' && c <= '~') ||
           (c >= '\u0080' && c <= '\uffff');
  }

  public static boolean isCSSURLRequiringQuotes (@Nonnull final String sURL)
  {
    if (sURL == null)
      throw new NullPointerException ("passed URL is null!");

    for (final char c : sURL.toCharArray ())
      if (!isValidCSSURLChar (c))
        return true;
    return false;
  }

  /**
   * Internal method to escape a CSS URL. Because this method is only called for
   * quoted URLs, only the quote character itself needs to be quoted.
   * 
   * @param sURL
   *        The URL to be escaped. May not be <code>null</code>.
   * @param cQuoteChar
   *        The quote char that is used. Either '\'' or '"'
   * @return The escaped string. Never <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public static String getEscapedCSSURL (@Nonnull final String sURL, final char cQuoteChar)
  {
    if (sURL == null)
      throw new NullPointerException ("URL");

    int nIndex = sURL.indexOf (cQuoteChar);
    if (nIndex < 0)
    {
      // Found nothing to quote
      return sURL;
    }

    final StringBuilder aSB = new StringBuilder (sURL.length () * 2);
    int nPrevIndex = 0;
    do
    {
      // Append everything before the first quote char
      aSB.append (sURL, nPrevIndex, nIndex);
      // Append the escape char itself
      aSB.append (ParseUtils.URL_ESCAPE_CHAR);
      // Append the char to be escaped
      aSB.append (cQuoteChar);
      // The new position to start searching
      nPrevIndex = nIndex + 1;
      // Search the next escaped char
      nIndex = sURL.indexOf (cQuoteChar, nPrevIndex);
    } while (nIndex >= 0);
    // Append the rest
    aSB.append (sURL.substring (nPrevIndex));
    return aSB.toString ();
  }

  /**
   * Surround the passed URL with the CSS "url(...)". When the passed URL
   * contains characters that require quoting, quotes are automatically added!
   * 
   * @param sURL
   *        URL to be wrapped. May neither be <code>null</code> nor empty.
   * @param bForceQuoteURL
   *        if <code>true</code> single quotes are added around the URL
   * @return <code>url(<i>sURL</i>)</code> or <code>url('<i>sURL</i>')</code>
   */
  @Nonnull
  @Nonempty
  public static String getAsCSSURL (@Nonnull @Nonempty final String sURL, final boolean bForceQuoteURL)
  {
    if (StringHelper.hasNoText (sURL))
      throw new IllegalArgumentException ("passed URL is empty!");

    final StringBuilder aSB = new StringBuilder (CCSSValue.PREFIX_URL_OPEN);
    final boolean bAreQuotesRequired = bForceQuoteURL || isCSSURLRequiringQuotes (sURL);
    if (bAreQuotesRequired)
    {
      // Determine the best quote char to use - default to '\'' for backwards
      // compatibility
      final int nIndexSingleQuote = sURL.indexOf ('\'');
      final int nIndexDoubleQuote = sURL.indexOf ('"');
      final char cQuote = nIndexSingleQuote >= 0 && nIndexDoubleQuote < 0 ? '"' : '\'';
      // Append the quoted and escaped URL
      aSB.append (cQuote).append (getEscapedCSSURL (sURL, cQuote)).append (cQuote);
    }
    else
    {
      // No quotes needed
      aSB.append (sURL);
    }
    return aSB.append (')').toString ();
  }
}
