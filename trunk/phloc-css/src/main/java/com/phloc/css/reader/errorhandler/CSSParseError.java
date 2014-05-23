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
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.StringHelper;
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

  public CSSParseError (@Nonnull @Nonempty final String sErrorMsg)
  {
    if (StringHelper.hasNoText (sErrorMsg))
      throw new IllegalArgumentException ("Error message");
    m_aLastValidToken = null;
    m_sExpectedTokens = null;
    m_aFirstSkippedToken = null;
    m_aLastSkippedToken = null;
    m_sErrorMessage = sErrorMsg;
  }

  public CSSParseError (@Nonnull final Token aLastValidToken,
                        @Nonnull final int [][] aExpectedTokenSequencesVal,
                        @Nonnull final String [] aTokenImageVal,
                        @Nullable final Token aLastSkippedToken)
  {
    ValueEnforcer.notNull (aLastValidToken, "LastValidToken");
    ValueEnforcer.notNull (aExpectedTokenSequencesVal, "ExpectedTokenSequencesVal");
    ValueEnforcer.notNull (aTokenImageVal, "TokenImageVal");

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
    m_aLastSkippedToken = aLastSkippedToken == null ? null : new ReadonlyToken (aLastSkippedToken);
    m_sErrorMessage = LoggingCSSParseErrorHandler.createLoggingStringParseError (aLastValidToken,
                                                                                 aExpectedTokenSequencesVal,
                                                                                 aTokenImageVal,
                                                                                 aLastSkippedToken);
  }

  /**
   * @return The last valid token read. May be <code>null</code>.
   */
  @Nullable
  public ReadonlyToken getLastValidToken ()
  {
    return m_aLastValidToken;
  }

  /**
   * @return The expected tokens as a string representation. May be
   *         <code>null</code>.
   */
  @Nullable
  public String getExpectedTokens ()
  {
    return m_sExpectedTokens;
  }

  /**
   * @return The first token that was skipped. That can be used to identify the
   *         start position of the error. May be <code>null</code>.
   */
  @Nullable
  public ReadonlyToken getFirstSkippedToken ()
  {
    return m_aFirstSkippedToken;
  }

  /**
   * @return The last token that was skipped. That can be used to identify the
   *         end position of the error. May be <code>null</code>.
   */
  @Nullable
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
    return new ToStringGenerator (this).appendIfNotNull ("lastValidToken", m_aLastValidToken)
                                       .appendIfNotNull ("expectedTokens", m_sExpectedTokens)
                                       .appendIfNotNull ("firstSkippedToken", m_aFirstSkippedToken)
                                       .appendIfNotNull ("lastSkippedToken", m_aLastSkippedToken)
                                       .append ("errorMessage", m_sErrorMessage)
                                       .toString ();
  }

  @Nonnull
  public static CSSParseError createUnexpectedRule (@Nonnull final Token aCurrentToken,
                                                    @Nonnull @Nonempty final String sRule,
                                                    @Nonnull @Nonempty final String sMsg)
  {
    return new CSSParseError (LoggingCSSParseErrorHandler.createLoggingStringUnexpectedRule (aCurrentToken, sRule, sMsg));
  }
}
