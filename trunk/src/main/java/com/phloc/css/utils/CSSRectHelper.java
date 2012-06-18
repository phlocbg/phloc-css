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

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.regex.RegExHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.CCSS;

/**
 * Provides rectangle handling sanity methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSRectHelper
{
  private static final String PATTERN_CURRENT_SYNTAX = "^" +
                                                       CCSS.PREFIX_RECT +
                                                       "\\s*\\((\\s*[0-9]+([a-z]*|%)\\s*,){3}\\s*[0-9]+([a-z]*|%)\\s*\\)$";
  private static final String PATTERN_OLD_SYNTAX = "^" +
                                                   CCSS.PREFIX_RECT +
                                                   "\\s*\\((\\s*[0-9]+([a-z]*|%)\\s+){3}\\s*[0-9]+([a-z]*|%)\\s*\\)$";

  private CSSRectHelper ()
  {}

  public static boolean isRectValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
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
}
