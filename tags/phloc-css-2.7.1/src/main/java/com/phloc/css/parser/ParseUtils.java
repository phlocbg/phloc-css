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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;

@Immutable
public final class ParseUtils
{
  private ParseUtils ()
  {}

  @Nonnull
  private static String _trimBy (@Nonnull final CharSequence s, final int nLeft, final int nRight)
  {
    return s.toString ().substring (nLeft, s.length () - nRight);
  }

  /**
   * Remove surrounding quotes (single or double) of a string (if present). If
   * the start and the end quote are not equal, nothing happens.
   * 
   * @param sStr
   *          The string where the quotes should be removed
   * @return The string without quotes.
   */
  @Nullable
  public static String extractStringValue (@Nullable final String sStr)
  {
    if (StringHelper.hasNoText (sStr) || sStr.length () < 2)
      return sStr;
    final char cFirst = sStr.charAt (0);
    if ((cFirst == '"' || cFirst == '\'') && StringHelper.getLastChar (sStr) == cFirst)
      return _trimBy (sStr, 1, 1);
    return sStr;
  }

  /**
   * Remove the leading "url(" and the trailing ")" from an URL CSS value. No
   * check is performed for the existence of a leading "url("!
   * 
   * @param s
   *          The value to remove the string from.
   * @return The trimmed value.
   */
  @Nonnull
  public static String trimUrl (@Nonnull final CharSequence s)
  {
    // Extract from "url(...)"
    final String s1 = _trimBy (s, 4, 1).trim ();
    return extractStringValue (s1);
  }

  // Find the longest matching number within the pattern
  @Nonnull
  public static String splitNumber (@Nonnull final StringBuilder aPattern)
  {
    final String sRegEx = "[0-9]+|[0-9]*.[0-9]+";
    int nChars = 1;
    while (RegExHelper.stringMatchesPattern (sRegEx, aPattern.substring (0, nChars)))
      nChars++;

    if (aPattern.charAt (nChars - 1) == '.')
    {
      nChars++;
      while (RegExHelper.stringMatchesPattern (sRegEx, aPattern.substring (0, nChars)))
        nChars++;
    }
    return aPattern.substring (0, nChars - 1);
  }
}
