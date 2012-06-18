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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link CSSRectHelper}.
 * 
 * @author philip
 */
public final class CSSRectHelperTest
{
  @Test
  public void testIsRectValue ()
  {
    // OK, current syntax
    assertTrue (CSSRectHelper.isRectValue ("rect(0,0,100,50)"));
    assertTrue (CSSRectHelper.isRectValue ("rect( 0 , 0 , 100 , 50 ) "));
    assertTrue (CSSRectHelper.isRectValue ("rect(0%,0%,100%,50%)"));
    assertTrue (CSSRectHelper.isRectValue ("rect( 0% , 0% , 100% , 50% )"));
    assertTrue (CSSRectHelper.isRectValue ("rect(0in,0px,10em,50pt)"));
    assertTrue (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em , 50pt ) "));
    assertTrue (CSSRectHelper.isRectValue ("rect(auto, auto, auto, auto)"));

    // OK, backward compatible syntax
    assertTrue (CSSRectHelper.isRectValue ("rect(0 0 100 50)"));
    assertTrue (CSSRectHelper.isRectValue ("rect( 0   0   100   50 ) "));
    assertTrue (CSSRectHelper.isRectValue ("rect(0% 0% 100% 50%)"));
    assertTrue (CSSRectHelper.isRectValue ("rect( 0%   0%   100%   50% )"));
    assertTrue (CSSRectHelper.isRectValue ("rect(0in 0px 10em 50pt)"));
    assertTrue (CSSRectHelper.isRectValue ("rect( 0in   0px   10em   50pt ) "));
    assertTrue (CSSRectHelper.isRectValue ("rect(auto  auto  auto  auto)"));

    // error cases
    assertFalse (CSSRectHelper.isRectValue ("rect( a , 0px , 10em , 50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , a , 10em , 50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , a , 50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em , a ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em, 20pt, 22pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em, 20.5pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect(auto, auto, auto, auto,auto)"));

    assertFalse (CSSRectHelper.isRectValue ("rect( a   0px   10em   50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   a   10em   50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   a   50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   10em   a ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   10em    ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   10em  20pt  22pt  ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   10em  20.5pt) "));
    assertFalse (CSSRectHelper.isRectValue ("rect(auto  auto  auto  auto auto)"));

    assertFalse (CSSRectHelper.isRectValue ("rect( 0in% , 0px , 10em , 50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px% , 10em , 50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em% , 50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in , 0px , 10em , 50pt% ) "));

    assertFalse (CSSRectHelper.isRectValue ("rect( 0in%   0px   10em   50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px%   10em   50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   10em%   50pt ) "));
    assertFalse (CSSRectHelper.isRectValue ("rect( 0in   0px   10em   50pt% ) "));
  }

  @Test
  public void testGetRectValue ()
  {
    // OK, current syntax
    assertArrayEquals (new String [] { "0in", "0pt", "100ex", "50em" },
                       CSSRectHelper.getRectValue ("rect(0in,0pt,100ex,50em)"));
    assertArrayEquals (new String [] { "0", "0", "100", "50" }, CSSRectHelper.getRectValue ("rect(0,0,100,50)"));
    assertArrayEquals (new String [] { "0", "0", "100", "50" },
                       CSSRectHelper.getRectValue ("rect( 0 , 0 , 100 , 50 ) "));
    assertArrayEquals (new String [] { "0%", "0%", "100%", "50%" }, CSSRectHelper.getRectValue ("rect(0%,0%,100%,50%)"));
    assertArrayEquals (new String [] { "0%", "0%", "100%", "50%" },
                       CSSRectHelper.getRectValue ("rect( 0% , 0% , 100% , 50% )"));
    assertArrayEquals (new String [] { "0in", "0px", "10em", "50pt" },
                       CSSRectHelper.getRectValue ("rect(0in,0px,10em,50pt)"));
    assertArrayEquals (new String [] { "0in", "0px", "10em", "50pt" },
                       CSSRectHelper.getRectValue ("rect( 0in , 0px , 10em , 50pt ) "));
    assertArrayEquals (new String [] { "auto", "auto", "auto", "auto" },
                       CSSRectHelper.getRectValue ("rect(auto, auto, auto, auto)"));

    // OK, backward compatible syntax
    assertArrayEquals (new String [] { "0", "0", "100", "50" }, CSSRectHelper.getRectValue ("rect(0 0 100 50)"));
    assertArrayEquals (new String [] { "0", "0", "100", "50" },
                       CSSRectHelper.getRectValue ("rect( 0   0   100   50 ) "));
    assertArrayEquals (new String [] { "0%", "0%", "100%", "50%" }, CSSRectHelper.getRectValue ("rect(0% 0% 100% 50%)"));
    assertArrayEquals (new String [] { "0%", "0%", "100%", "50%" },
                       CSSRectHelper.getRectValue ("rect( 0%   0%   100%   50% )"));
    assertArrayEquals (new String [] { "0in", "0px", "10em", "50pt" },
                       CSSRectHelper.getRectValue ("rect(0in 0px 10em 50pt)"));
    assertArrayEquals (new String [] { "0in", "0px", "10em", "50pt" },
                       CSSRectHelper.getRectValue ("rect( 0in   0px   10em   50pt ) "));
    assertArrayEquals (new String [] { "auto", "auto", "auto", "auto" },
                       CSSRectHelper.getRectValue ("rect(auto  auto  auto  auto)"));
  }
}
