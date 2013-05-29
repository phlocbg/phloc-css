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
package com.phloc.css.reader.errorhandler;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.css.parser.ReadonlyToken;
import com.phloc.css.parser.Token;

/**
 * This item contains a single CSSparsing error. It is used e.g. in the
 * {@link CollectingCSSParseErrorHandler}.
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSParseError
{
  private final ReadonlyToken m_aLastValidToken;
  private final int [][] m_aExpectedTokenSequencesVal;
  private final String [] m_aTokenImageVal;
  private final ReadonlyToken m_aFirstSkippedToken;
  private final ReadonlyToken m_aLastSkippedToken;
  private final String m_sErrorMessage;

  public CSSParseError (@Nonnull final Token aLastValidToken,
                        @Nonnull final int [][] aExpectedTokenSequencesVal,
                        @Nonnull final String [] aTokenImageVal,
                        @Nonnull final Token aLastSkippedToken)
  {
    if (aLastValidToken == null)
      throw new NullPointerException ("LastValidToken");
    if (aExpectedTokenSequencesVal == null)
      throw new NullPointerException ("ExpectedTokenSequencesVal");
    if (aTokenImageVal == null)
      throw new NullPointerException ("TokenImageVal");
    if (aLastSkippedToken == null)
      throw new NullPointerException ("LastSkippedToken");

    m_aLastValidToken = new ReadonlyToken (aLastValidToken);
    m_aExpectedTokenSequencesVal = ArrayHelper.getCopy (aExpectedTokenSequencesVal);
    m_aTokenImageVal = ArrayHelper.getCopy (aTokenImageVal);
    m_aFirstSkippedToken = new ReadonlyToken (aLastValidToken.next);
    m_aLastSkippedToken = new ReadonlyToken (aLastSkippedToken);
    m_sErrorMessage = LoggingCSSParseErrorHandler.createLoggingString (aLastValidToken,
                                                                       aExpectedTokenSequencesVal,
                                                                       aTokenImageVal,
                                                                       aLastSkippedToken);
  }

  /**
   * @return The last valid token read.
   */
  @Nonnull
  public ReadonlyToken getLastValidToken ()
  {
    return m_aLastValidToken;
  }

  /**
   * @return The first token that was skipped. That can be used to identify the
   *         start position of the error.
   */
  @Nonnull
  public ReadonlyToken getFirstSkippedToken ()
  {
    return m_aFirstSkippedToken;
  }

  /**
   * @return The last token that was skipped. That can be used to identify the
   *         end position of the error.
   */
  @Nonnull
  public ReadonlyToken getLastSkippedToken ()
  {
    return m_aLastSkippedToken;
  }

  /**
   * @return The error message created by {@link LoggingCSSParseErrorHandler} as
   *         a convience method.
   */
  @Nonnull
  @Nonempty
  public String getErrorMessage ()
  {
    return m_sErrorMessage;
  }
}
