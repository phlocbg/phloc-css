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
package com.phloc.css.parser;

import java.util.regex.Matcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;

/**
 * This class is used by the generated parsers to do some common stuff.
 * 
 * @author philip
 */
@Immutable
public final class ParseUtils
{
  private ParseUtils ()
  {}

  @Nonnull
  private static String _trimBy (@Nonnull final CharSequence s, final int nLeftSkip, final int nRightSkip)
  {
    return s.toString ().substring (nLeftSkip, s.length () - nRightSkip);
  }

  /**
   * Remove surrounding quotes (single or double) of a string (if present). If
   * the start and the end quote are not equal, nothing happens.
   * 
   * @param sStr
   *        The string where the quotes should be removed
   * @return The string without quotes.
   */
  @Nullable
  public static String extractStringValue (@Nullable final String sStr)
  {
    if (StringHelper.hasNoText (sStr) || sStr.length () < 2)
      return sStr;

    final char cFirst = sStr.charAt (0);
    if ((cFirst == '"' || cFirst == '\'') && StringHelper.getLastChar (sStr) == cFirst)
    {
      // Remove quotes around the string
      return _trimBy (sStr, 1, 1);
    }
    return sStr;
  }

  /**
   * Remove the leading "url(" and the trailing ")" from an URL CSS value. No
   * check is performed for the existence of a leading "url("!
   * 
   * @param s
   *        The value to remove the string from.
   * @return The trimmed value.
   */
  @Nonnull
  public static String trimUrl (@Nonnull final CharSequence s)
  {
    // Extract from "url(...)"
    final String s1 = _trimBy (s, 4, 1).trim ();
    return extractStringValue (s1);
  }

  @Nonnull
  public static String splitNumber (@Nonnull final StringBuilder aPattern)
  {
    // Find the longest matching number within the pattern
    // Order of the rules in brackets is important!
    final String sRegEx = "^([0-9]*\\.[0-9]+|[0-9]+).*$";
    final Matcher m = RegExHelper.getMatcher (sRegEx, aPattern.toString ());
    if (m.matches ())
      return m.group (1);
    return "";
  }

  /**
   * In CSS, identifiers (including element names, classes, and IDs in
   * selectors) can contain only the characters [a-zA-Z0-9] and ISO 10646
   * characters U+00A0 and higher, plus the hyphen (-) and the underscore (_);
   * they cannot start with a digit, two hyphens, or a hyphen followed by a
   * digit. Identifiers can also contain escaped characters and any ISO 10646
   * character as a numeric code (see next item). For instance, the identifier
   * "B&W?" may be written as "B\&W\?" or "B\26 W\3F".
   * 
   * @param aPattern
   *        pattern to check
   * @return The input string
   */
  @Nonnull
  public static String validateIdentifier (@Nonnull final StringBuilder aPattern)
  {
    final int nLength = aPattern.length ();
    final char [] aChars = new char [nLength];
    aPattern.getChars (0, nLength, aChars, 0);

    // Starts with a hack?
    if (aChars[0] == '-' || aChars[0] == '$' || aChars[0] == '*')
    {
      if (Character.isDigit (aChars[1]))
        throw new TokenMgrError ("Identifier may not start with a hyphen and a digit: " + aPattern,
                                 TokenMgrError.LEXICAL_ERROR);
    }
    else
    {
      if (Character.isDigit (aChars[0]))
        throw new TokenMgrError ("Identifier may not start with a digit: " + aPattern, TokenMgrError.LEXICAL_ERROR);
    }

    if (nLength > 1 && aChars[0] == '-' && aChars[1] == '-')
      throw new TokenMgrError ("Identifier may not start with two hyphens: " + aPattern, TokenMgrError.LEXICAL_ERROR);

    return aPattern.toString ();
  }
}
