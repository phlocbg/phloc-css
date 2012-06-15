package com.phloc.css.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for class {@link ParseUtils}.
 * 
 * @author philip
 */
public final class ParseUtilsTest
{
  private static String _split (final String s)
  {
    return ParseUtils.splitNumber (new StringBuilder (s));
  }

  @Test
  public void testSplitNumber ()
  {
    assertEquals ("0", _split ("0px"));
    assertEquals ("17", _split ("17in"));
    assertEquals ("17", _split ("17 in"));
    assertEquals ("17.1234", _split ("17.1234 in"));
    assertEquals ("0.5", _split ("0.5dpi"));
    assertEquals (".5", _split (".5dpi"));

    assertEquals ("0", _split ("0"));
    assertEquals ("17", _split ("17"));
    assertEquals ("17", _split ("17 "));
    assertEquals ("17.1234", _split ("17.1234 "));
    assertEquals ("0.5", _split ("0.5"));
    assertEquals (".5", _split (".5"));
    assertEquals ("", _split ("."));
    assertEquals ("", _split ("any"));
    assertEquals ("", _split (""));
  }
}
