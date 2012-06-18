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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link CSSNumberHelper}.
 * 
 * @author philip
 */
public final class CSSNumberHelperTest
{
  @Test
  public void testIsNumberWithUnitValue ()
  {
    assertTrue (CSSNumberHelper.isNumberWithUnitValue ("50%", true));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue ("50mm", true));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue ("50cm", true));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue ("50ex", true));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue (" 50ex ", true));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue (" 50ex ", false));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue (" 50 ex ", false));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue (" 50 s ", false));
    assertTrue (CSSNumberHelper.isNumberWithUnitValue (" 50 ms ", false));

    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" 50 xs ", false));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue ("50%", false));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue ("", false));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue ("", true));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" ", false));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" ", true));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" mm ", true));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" % ", true));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" 50 xyz ", true));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue (" 50 gd ", true));
    assertFalse (CSSNumberHelper.isNumberWithUnitValue ("50gd", true));
  }
}
