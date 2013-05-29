package com.phloc.css.reader;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.parser.Token;

public class LoggingCSSParseErrorHandler implements ICSSParseErrorHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (LoggingCSSParseErrorHandler.class);
  private static final int TOKEN_EOF = 0;

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
