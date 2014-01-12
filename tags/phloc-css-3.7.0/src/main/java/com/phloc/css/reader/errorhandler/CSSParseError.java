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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.parser.ReadonlyToken;
import com.phloc.css.parser.Token;

/**
 * This item contains a single CSSparsing error. It is used e.g. in the
 * {@link CollectingCSSParseErrorHandler}.
 * 
 * @author Philip Helger
 */
@Immutable
public class CSSParseError
{
  private final ReadonlyToken m_aLastValidToken;
  private final String m_sExpectedTokens;
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
    final StringBuilder aExpected = new StringBuilder ();
    for (final int [] aExpectedTokens : aExpectedTokenSequencesVal)
    {
      if (aExpected.length () > 0)
        aExpected.append (",");
      for (final int nExpectedToken : aExpectedTokens)
        aExpected.append (' ').append (aTokenImageVal[nExpectedToken]);
    }
    m_sExpectedTokens = aExpected.toString ();
    m_aFirstSkippedToken = new ReadonlyToken (aLastValidToken.next);
    m_aLastSkippedToken = new ReadonlyToken (aLastSkippedToken);
    m_sErrorMessage = LoggingCSSParseErrorHandler.createLoggingString (aLastValidToken,
                                                                       aExpectedTokenSequencesVal,
                                                                       aTokenImageVal,
                                                                       aLastSkippedToken);
  }

  /**
   * @return The last valid token read. Never <code>null</code>.
   */
  @Nonnull
  public ReadonlyToken getLastValidToken ()
  {
    return m_aLastValidToken;
  }

  /**
   * @return The first token that was skipped. That can be used to identify the
   *         start position of the error. Never <code>null</code>.
   */
  @Nonnull
  public ReadonlyToken getFirstSkippedToken ()
  {
    return m_aFirstSkippedToken;
  }

  /**
   * @return The last token that was skipped. That can be used to identify the
   *         end position of the error. Never <code>null</code>.
   */
  @Nonnull
  public ReadonlyToken getLastSkippedToken ()
  {
    return m_aLastSkippedToken;
  }

  /**
   * @return The error message created by {@link LoggingCSSParseErrorHandler} as
   *         a convenience method. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getErrorMessage ()
  {
    return m_sErrorMessage;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("lastValidToken", m_aLastValidToken)
                                       .append ("expectedTokens", m_sExpectedTokens)
                                       .append ("firstSkippedToken", m_aFirstSkippedToken)
                                       .append ("lastSkippedToken", m_aLastSkippedToken)
                                       .toString ();
  }
}
