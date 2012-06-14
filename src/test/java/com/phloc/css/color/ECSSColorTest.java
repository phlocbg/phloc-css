package com.phloc.css.color;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.commons.string.StringHelper;

/**
 * Test class for class {@link ECSSColor}.
 * 
 * @author philip
 */
public final class ECSSColorTest
{
  @Test
  public void testAll ()
  {
    for (final ECSSColor eColor : ECSSColor.values ())
    {
      assertTrue (StringHelper.hasText (eColor.getName ()));
      final String sHex = eColor.getAsHexColorValue ();
      assertTrue (sHex, CSSColorHelper.isHexColorValue (sHex));
      final String sRGB = eColor.getAsRGBColorValue ();
      assertTrue (sRGB, CSSColorHelper.isRGBColorValue (sRGB));
      final String sRGBA = eColor.getAsRGBAColorValue (1f);
      assertTrue (sRGBA, CSSColorHelper.isRGBAColorValue (sRGBA));
      final String sHSL = eColor.getAsHSLColorValue ();
      assertTrue (sHSL, CSSColorHelper.isHSLColorValue (sHSL));
      final String sHSLA = eColor.getAsHSLAColorValue (1f);
      assertTrue (sHSLA, CSSColorHelper.isHSLAColorValue (sHSLA));
      assertSame (eColor, ECSSColor.getFromNameOrNullCaseInsensitive (eColor.getName ()));
    }
  }
}
