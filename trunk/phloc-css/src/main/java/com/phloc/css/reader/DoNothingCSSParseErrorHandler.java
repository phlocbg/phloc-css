package com.phloc.css.reader;

import javax.annotation.Nonnull;

import com.phloc.css.parser.Token;

/**
 * An implementation of {@link ICSSParseErrorHandler} that does nothing.
 * 
 * @author Philip Helger
 */
public final class DoNothingCSSParseErrorHandler implements ICSSParseErrorHandler
{
  private static final DoNothingCSSParseErrorHandler s_aInstance = new DoNothingCSSParseErrorHandler ();

  private DoNothingCSSParseErrorHandler ()
  {}

  @Nonnull
  public static DoNothingCSSParseErrorHandler getInstance ()
  {
    return s_aInstance;
  }

  public void onCSSParseError (@Nonnull final Token aLastValidToken,
                               @Nonnull final int [][] aExpectedTokenSequencesVal,
                               @Nonnull final String [] aTokenImageVal)
  {
    // ignore
  }
}
