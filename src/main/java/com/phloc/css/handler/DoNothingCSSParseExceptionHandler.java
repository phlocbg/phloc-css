package com.phloc.css.handler;

import com.phloc.css.parser.ParseException;

public final class DoNothingCSSParseExceptionHandler implements ICSSParseExceptionHandler
{
  public void onException (final ParseException ex)
  {
    // ignore
  }
}