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

import java.util.regex.Matcher;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.propertyvalue.CCSSValue;

/**
 * Provides rectangle handling sanity methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSRectHelper
{
  // Possible values are: "0", "0px", "0%" or "auto"
  // "?:" - non-capturing group
  private static final String PATTERN_PART_VALUE = "([0-9]+(?:[a-zA-Z]+|%)?|auto)";
  // Do not use a recurring group (...{3}) so that capturing works!
  private static final String PATTERN_CURRENT_SYNTAX = "^" +
                                                       CCSSValue.PREFIX_RECT +
                                                       "\\s*\\(\\s*" +
                                                       PATTERN_PART_VALUE +
                                                       "\\s*,\\s*" +
                                                       PATTERN_PART_VALUE +
                                                       "\\s*,\\s*" +
                                                       PATTERN_PART_VALUE +
                                                       "\\s*,\\s*" +
                                                       PATTERN_PART_VALUE +
                                                       "\\s*\\)$";
  private static final String PATTERN_OLD_SYNTAX = "^" +
                                                   CCSSValue.PREFIX_RECT +
                                                   "\\s*\\(\\s*" +
                                                   PATTERN_PART_VALUE +
                                                   "\\s+" +
                                                   PATTERN_PART_VALUE +
                                                   "\\s+" +
                                                   PATTERN_PART_VALUE +
                                                   "\\s+" +
                                                   PATTERN_PART_VALUE +
                                                   "\\s*\\)$";

  private CSSRectHelper ()
  {}

  public static boolean isRectValue (@Nullable final String sCSSValue)
  {
    final String sRealValue = StringHelper.trim (sCSSValue);
    if (StringHelper.hasText (sRealValue))
    {
      // Current syntax: rect(a,b,c,d)
      if (RegExHelper.stringMatchesPattern (PATTERN_CURRENT_SYNTAX, sRealValue))
        return true;

      // Backward compatible syntax: rect(a b c d)
      if (RegExHelper.stringMatchesPattern (PATTERN_OLD_SYNTAX, sRealValue))
        return true;
    }
    return false;
  }

  @Nullable
  public static String [] getRectValue (@Nullable final String sCSSValue)
  {
    final String sRealValue = StringHelper.trim (sCSSValue);
    if (StringHelper.hasText (sRealValue))
    {
      // TODO use RegHexHelper.getAllMatchingGroupValues
      Matcher m = RegExHelper.getMatcher (PATTERN_CURRENT_SYNTAX, sRealValue);
      if (!m.find ())
      {
        m = RegExHelper.getMatcher (PATTERN_OLD_SYNTAX, sRealValue);
        if (!m.find ())
          return null;
      }

      // groupCount is excluding the .group(0) match!
      final String [] ret = new String [m.groupCount ()];
      for (int i = 0; i < m.groupCount (); ++i)
        ret[i] = m.group (i + 1);
      return ret;
    }
    return null;
  }
}
