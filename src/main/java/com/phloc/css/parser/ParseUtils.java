package com.phloc.css.parser;

import com.phloc.commons.string.StringHelper;
import com.phloc.commons.regex.RegExHelper;

public final class ParseUtils
{
  private ParseUtils ()
  {}

  private static String _trimBy (final CharSequence s, final int nLeft, final int nRight)
  {
    return s.toString ().substring (nLeft, s.length () - nRight);
  }

  public static String extractStringValue (final String sStr)
  {
    if (StringHelper.hasNoText (sStr) || sStr.length () < 2)
      return sStr;
    final char cFirst = sStr.charAt (0);
    if ((cFirst == '"' || cFirst == '\'') && StringHelper.getLastChar (sStr) == cFirst)
      return _trimBy (sStr, 1, 1);
    return sStr;
  }

  public static String trimUrl (final CharSequence s)
  {
    // Extract from "url(...)"
    final String s1 = _trimBy (s, 4, 1).trim ();
    return extractStringValue (s1);
  }

  // Find the longest matching number within the pattern
  public static String splitNumber (final StringBuilder aPattern)
  {
    final String sRegEx = "[0-9]+|[0-9]*.[0-9]+";
    int j = 1;
    while (RegExHelper.stringMatchesPattern (sRegEx, aPattern.substring (0, j)))
      j++;

    if (aPattern.charAt (j - 1) == '.')
    {
      j++;
      while (RegExHelper.stringMatchesPattern (sRegEx, aPattern.substring (0, j)))
        j++;
    }
    return aPattern.substring (0, j - 1);
  }
}
