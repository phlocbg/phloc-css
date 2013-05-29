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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.parser.Token;

/**
 * A logging implementation of {@link ICSSParseErrorHandler}
 * 
 * @author Philip Helger
 */
public class LoggingCSSParseErrorHandler implements ICSSParseErrorHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (LoggingCSSParseErrorHandler.class);
  private static final int TOKEN_EOF = 0;

  public LoggingCSSParseErrorHandler ()
  {}

  @Nonnull
  @Nonempty
  public static String createLoggingString (@Nonnull final Token aLastValidToken,
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

    final StringBuilder aExpected = new StringBuilder ();
    int nMaxSize = 0;
    for (final int [] aExpectedTokens : aExpectedTokenSequencesVal)
    {
      if (nMaxSize < aExpectedTokens.length)
        nMaxSize = aExpectedTokens.length;

      for (final int nExpectedToken : aExpectedTokens)
        aExpected.append (' ').append (aTokenImageVal[nExpectedToken]);

      aExpected.append (",");
    }

    final StringBuilder retval = new StringBuilder ("[").append (aLastValidToken.next.beginLine)
                                                        .append (':')
                                                        .append (aLastValidToken.next.beginColumn)
                                                        .append ("]-[")
                                                        .append (aLastSkippedToken.endLine)
                                                        .append (':')
                                                        .append (aLastSkippedToken.endColumn)
                                                        .append ("] Encountered");
    Token aCurToken = aLastValidToken.next;
    for (int i = 0; i < nMaxSize; i++)
    {
      retval.append (' ');
      if (aCurToken.kind == TOKEN_EOF)
      {
        retval.append (aTokenImageVal[TOKEN_EOF]);
        break;
      }
      retval.append ("token ")
            .append (aTokenImageVal[aCurToken.kind])
            .append (" with image '")
            .append (aCurToken.image)
            .append ('\'');
      aCurToken = aCurToken.next;
    }
    retval.append (". Skipped until token ")
          .append (aLastSkippedToken)
          .append (". ")
          .append (aExpectedTokenSequencesVal.length == 1 ? "Was expecting:" : "Was expecting one of:")
          .append (aExpected);
    return retval.toString ();
  }

  public void onCSSParseError (@Nonnull final Token aLastValidToken,
                               @Nonnull final int [][] aExpectedTokenSequencesVal,
                               @Nonnull final String [] aTokenImageVal,
                               @Nonnull final Token aLastSkippedToken)
  {
    s_aLogger.warn (createLoggingString (aLastValidToken, aExpectedTokenSequencesVal, aTokenImageVal, aLastSkippedToken));
  }
}
