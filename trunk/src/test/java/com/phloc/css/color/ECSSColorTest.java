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
