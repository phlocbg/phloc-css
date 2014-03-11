/**
 * Copyright (C) 2006-2014 phloc systems
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
package com.phloc.css.reader.errorhandler;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.Token;

/**
 * An implementation of {@link ICSSParseErrorHandler} that throws a
 * {@link ParseException} in case of a parse error. This is the most strict
 * version.
 * 
 * @author Philip Helger
 */
public class ThrowingCSSParseErrorHandler implements ICSSParseErrorHandler
{
  private static final ThrowingCSSParseErrorHandler s_aInstance = new ThrowingCSSParseErrorHandler ();

  private ThrowingCSSParseErrorHandler ()
  {}

  /**
   * @return The singleton instance. Never <code>null</code>.
   */
  @Nonnull
  public static ThrowingCSSParseErrorHandler getInstance ()
  {
    return s_aInstance;
  }

  public void onCSSParseError (@Nonnull final Token aLastValidToken,
                               @Nonnull final int [][] aExpectedTokenSequencesVal,
                               @Nonnull final String [] aTokenImageVal,
                               @Nonnull final Token aLastSkippedToken) throws ParseException
  {
    throw new ParseException (aLastValidToken, aExpectedTokenSequencesVal, aTokenImageVal);
  }

  public void onCSSUnexpectedRule (@Nonnull @Nonempty final String sRule, @Nonnull @Nonempty final String sMsg) throws ParseException
  {
    throw new ParseException ("Unexpected rule '" + sRule + "': " + sMsg);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).toString ();
  }
}
