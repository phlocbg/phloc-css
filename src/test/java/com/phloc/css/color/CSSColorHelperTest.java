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
package com.phloc.css.color;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

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
    try
    {
      // illegal red
      CSSColorHelper.getRGBColorValue (-1, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal red
      CSSColorHelper.getRGBColorValue (256, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CSSColorHelper.getRGBColorValue (0, -1, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CSSColorHelper.getRGBColorValue (0, 256, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CSSColorHelper.getRGBColorValue (0, 0, -1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CSSColorHelper.getRGBColorValue (0, 0, 256);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  @edu.umd.cs.findbugs.annotations.SuppressWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testColorHex ()
  {
    assertEquals ("#000000", CSSColorHelper.getHexColorValue (0, 0, 0));
    assertEquals ("#0f80ff", CSSColorHelper.getHexColorValue (15, 128, 255));
    try
    {
      // illegal red
      CSSColorHelper.getHexColorValue (-1, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal red
      CSSColorHelper.getHexColorValue (256, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CSSColorHelper.getHexColorValue (0, -1, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CSSColorHelper.getHexColorValue (0, 256, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CSSColorHelper.getHexColorValue (0, 0, -1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CSSColorHelper.getHexColorValue (0, 0, 256);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
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
}
