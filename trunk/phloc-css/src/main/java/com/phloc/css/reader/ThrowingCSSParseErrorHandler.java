package com.phloc.css.reader;

import javax.annotation.Nonnull;

import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.Token;

/**
 * An implementation of {@link ICSSParseErrorHandler} that throws an exception
 * in case of a parse error. This is the most strict version.
 * 
 * @author Philip Helger
 */
public final class ThrowingCSSParseErrorHandler implements ICSSParseErrorHandler
{
  private static final ThrowingCSSParseErrorHandler s_aInstance = new ThrowingCSSParseErrorHandler ();

  private ThrowingCSSParseErrorHandler ()
  {}

  @Nonnull
  public static ThrowingCSSParseErrorHandler getInstance ()
  {
    return s_aInstance;
  }

  public void onCSSParseError (@Nonnull final Token aLastValidToken,
                               @Nonnull final int [][] aExpectedTokenSequencesVal,
                               @Nonnull final String [] aTokenImageVal) throws ParseException
  {
    throw new ParseException (aLastValidToken, aExpectedTokenSequencesVal, aTokenImageVal);
  }
}
