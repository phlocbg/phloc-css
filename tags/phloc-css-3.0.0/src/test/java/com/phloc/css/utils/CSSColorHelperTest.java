/**
 * Copyright (C) 2006-2012 phloc systems
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
package com.phloc.css.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.css.utils.CSSColorHelper;

/**
 * Test class for class {@link CSSColorHelper}.
 * 
 * @author philip
 */
public final class CSSColorHelperTest
{
  @Test
  @edu.umd.cs.findbugs.annotations.SuppressWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testColorRGB ()
  {
    assertEquals ("rgb(0,0,0)", CSSColorHelper.getRGBColorValue (0, 0, 0));
    assertEquals ("rgb(13,123,145)", CSSColorHelper.getRGBColorValue (13, 123, 145));
    assertEquals ("rgb(255,0,0)", CSSColorHelper.getRGBColorValue (-1, 0, 0));
    assertEquals ("rgb(255,0,0)", CSSColorHelper.getRGBColorValue (-257, 0, 0));
    assertEquals ("rgb(255,0,0)", CSSColorHelper.getRGBColorValue (-513, 0, 0));
    assertEquals ("rgb(0,0,0)", CSSColorHelper.getRGBColorValue (256, 0, 0));
    assertEquals ("rgb(0,255,0)", CSSColorHelper.getRGBColorValue (0, -1, 0));
    assertEquals ("rgb(0,0,0)", CSSColorHelper.getRGBColorValue (0, 256, 0));
    assertEquals ("rgb(0,0,255)", CSSColorHelper.getRGBColorValue (0, 0, -1));
    assertEquals ("rgb(0,0,0)", CSSColorHelper.getRGBColorValue (0, 0, 256));
  }

  @Test
  @edu.umd.cs.findbugs.annotations.SuppressWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testColorHex ()
  {
    assertEquals ("#000000", CSSColorHelper.getHexColorValue (0, 0, 0));
    assertEquals ("#0f80ff", CSSColorHelper.getHexColorValue (15, 128, 255));

    // Check overflow
    assertEquals ("#000000", CSSColorHelper.getHexColorValue (256, 256, 256));
    assertEquals ("#010101", CSSColorHelper.getHexColorValue (257, 257, 257));
    assertEquals ("#ffffff", CSSColorHelper.getHexColorValue (-1, -1, -1));
  }

  @Test
  public void testIsHexColorValue ()
  {
    final String [] HEX_COLORS = new String [] { "#000000",
                                                "#99aa00",
                                                "#9900aa",
                                                "#aa9900",
                                                "#ffffff",
                                                "#aaa",
                                                "#000",
                                                "#1234",
                                                "#000f" };
    for (final String sHexColor : HEX_COLORS)
    {
      assertTrue (sHexColor, CSSColorHelper.isHexColorValue (sHexColor));
      assertTrue (sHexColor, CSSColorHelper.isColorValue (sHexColor));
    }
    assertFalse (CSSColorHelper.isHexColorValue ("123456"));
    assertFalse (CSSColorHelper.isHexColorValue ("#1234567"));
  }

  @Test
  public void testIsRGBColorValue ()
  {
    final String [] RGB_COLORS = new String [] { "rgb(0,0,0)",
                                                "rgb(255,0,0)",
                                                "rgb(300,0,0)",
                                                "rgb(  300  ,  0  ,  0  )  ",
                                                "rgb(255,-10,0)",
                                                "rgb(110%, 0%, 0%)",
                                                "rgb(100%, 0%, 0%)" };
    for (final String sRGBColor : RGB_COLORS)
    {
      assertTrue (sRGBColor, CSSColorHelper.isRGBColorValue (sRGBColor));
      assertTrue (sRGBColor, CSSColorHelper.isColorValue (sRGBColor));
    }
    assertFalse (CSSColorHelper.isRGBColorValue ("rgb(a,0,0)"));
    assertFalse (CSSColorHelper.isRGBColorValue ("rgb(0,0,0,0)"));
  }

  @Test
  public void testIsRGBAColorValue ()
  {
    final String [] RGBA_COLORS = new String [] { "rgba(0,0,0,0)",
                                                 "rgba(0,0,0,1)",
                                                 "rgba(255,0,0, 0.1)",
                                                 "rgba(255,0,0, 0.875)",
                                                 "rgba(300,0,0, 0.999999)",
                                                 "rgba(255,-10,0, 0)",
                                                 "rgba(110%, 0%, 0%, 1.)",
                                                 "rgba(100%, 0%, 0%, 1.  )" };
    for (final String sRGBAColor : RGBA_COLORS)
    {
      assertTrue (sRGBAColor, CSSColorHelper.isRGBAColorValue (sRGBAColor));
      assertTrue (sRGBAColor, CSSColorHelper.isColorValue (sRGBAColor));
    }
    assertFalse (CSSColorHelper.isRGBAColorValue ("rgba(a,0,0)"));
    assertFalse (CSSColorHelper.isRGBAColorValue ("rgba(0,0,0,5%)"));
  }

  @Test
  public void testIsHSLColorValue ()
  {
    final String [] HSL_COLORS = new String [] { "hsl(0,0,0)",
                                                "hsl(255,0,0)",
                                                "hsl(300,0,0)",
                                                "hsl(  300  ,  0  ,  0  )  ",
                                                "hsl(255,-10,0)",
                                                "hsl(110%, 0%, 0%)",
                                                "hsl(100%, 0%, 0%)" };
    for (final String sHSLColor : HSL_COLORS)
    {
      assertTrue (sHSLColor, CSSColorHelper.isHSLColorValue (sHSLColor));
      assertTrue (sHSLColor, CSSColorHelper.isColorValue (sHSLColor));
    }
    assertFalse (CSSColorHelper.isHSLColorValue ("hsl(a,0,0)"));
    assertFalse (CSSColorHelper.isHSLColorValue ("hsl(0,0,0,0)"));
  }

  @Test
  public void testIsHSLAColorValue ()
  {
    final String [] HSLA_COLORS = new String [] { "hsla(0,0,0,0)",
                                                 "hsla(0,0,0,1)",
                                                 "hsla(255,0,0, 0.1)",
                                                 "hsla(255,0,0, 0.875)",
                                                 "hsla(300,0,0, 0.999999)",
                                                 "hsla(255,-10,0, 0)",
                                                 "hsla(110%, 0%, 0%, 1.)",
                                                 "hsla(100%, 0%, 0%, 1.  )" };
    for (final String sHSLAColor : HSLA_COLORS)
    {
      assertTrue (sHSLAColor, CSSColorHelper.isHSLAColorValue (sHSLAColor));
      assertTrue (sHSLAColor, CSSColorHelper.isColorValue (sHSLAColor));
    }
    assertFalse (CSSColorHelper.isHSLAColorValue ("hsla(a,0,0)"));
    assertFalse (CSSColorHelper.isHSLAColorValue ("hsla(0,0,0,5%)"));
  }
}
