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
package com.phloc.css.reader;

import javax.annotation.Nonnull;

import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.Token;

/**
 * Special CSS handler that is invoked during reading in case of a recoverable
 * error.
 * 
 * @author Philip Helger
 */
public interface ICSSParseErrorHandler
{
  /**
   * Called upon a recoverable error. The parameter list is similar to the one
   * of the {@link com.phloc.css.parser.ParseException}.
   * 
   * @param aLastValidToken
   *        The last valid token.
   * @param aExpectedTokenSequencesVal
   *        The expected token.
   * @param aTokenImageVal
   *        The error token image.
   * @param aLastSkippedToken
   *        The token until which was skipped (incl.)
   * @throws ParseException
   *         In case the error is fatal and should be propagated.
   */
  void onCSSParseError (@Nonnull Token aLastValidToken,
                        @Nonnull int [][] aExpectedTokenSequencesVal,
                        @Nonnull String [] aTokenImageVal,
                        @Nonnull Token aLastSkippedToken) throws ParseException;
}
