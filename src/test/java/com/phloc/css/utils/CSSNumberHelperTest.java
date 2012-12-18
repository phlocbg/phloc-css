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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.css.ECSSUnit;
import com.phloc.css.propertyvalue.CSSSimpleValueWithUnit;

/**
 * Test class for class {@link CSSNumberHelper}.
 * 
 * @author philip
 */
public final class CSSNumberHelperTest
{
  @Test
  public void testIsValueWithUnit ()
  {
    assertTrue (CSSNumberHelper.isValueWithUnit ("50%", true));
    assertTrue (CSSNumberHelper.isValueWithUnit ("50mm", true));
    assertTrue (CSSNumberHelper.isValueWithUnit ("50cm", true));
    assertTrue (CSSNumberHelper.isValueWithUnit ("50ex", true));
    assertTrue (CSSNumberHelper.isValueWithUnit (" 50ex ", true));
    assertTrue (CSSNumberHelper.isValueWithUnit (" 50ex ", false));
    assertTrue (CSSNumberHelper.isValueWithUnit (" 50 ex ", false));
    assertTrue (CSSNumberHelper.isValueWithUnit (" 50 s ", false));
    assertTrue (CSSNumberHelper.isValueWithUnit (" 50 ms ", false));

    assertFalse (CSSNumberHelper.isValueWithUnit (" 50 xs ", false));
    assertFalse (CSSNumberHelper.isValueWithUnit ("50%", false));
    assertFalse (CSSNumberHelper.isValueWithUnit ("", false));
    assertFalse (CSSNumberHelper.isValueWithUnit ("", true));
    assertFalse (CSSNumberHelper.isValueWithUnit (" ", false));
    assertFalse (CSSNumberHelper.isValueWithUnit (" ", true));
    assertFalse (CSSNumberHelper.isValueWithUnit (" mm ", true));
    assertFalse (CSSNumberHelper.isValueWithUnit (" % ", true));
    assertFalse (CSSNumberHelper.isValueWithUnit (" 50 xyz ", true));
    assertFalse (CSSNumberHelper.isValueWithUnit (" 50 gd ", true));
    assertFalse (CSSNumberHelper.isValueWithUnit ("50gd", true));
  }

  @Test
  public void testGetValueWithUnit ()
  {
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.PERCENTAGE), CSSNumberHelper.getValueWithUnit ("50%", true));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.LENGTH_MM), CSSNumberHelper.getValueWithUnit ("50mm", true));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.LENGTH_CM), CSSNumberHelper.getValueWithUnit ("50cm", true));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.EX), CSSNumberHelper.getValueWithUnit ("50ex", true));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.EX), CSSNumberHelper.getValueWithUnit (" 50ex ", true));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.EX), CSSNumberHelper.getValueWithUnit (" 50ex ", false));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.EX), CSSNumberHelper.getValueWithUnit (" 50 ex ", false));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.TIME_S), CSSNumberHelper.getValueWithUnit (" 50 s ", false));
    assertEquals (new CSSSimpleValueWithUnit (50, ECSSUnit.TIME_MS),
                  CSSNumberHelper.getValueWithUnit (" 50 ms ", false));

    assertNull (CSSNumberHelper.getValueWithUnit (" 50 xs ", false));
    assertNull (CSSNumberHelper.getValueWithUnit ("50%", false));
    assertNull (CSSNumberHelper.getValueWithUnit ("", false));
    assertNull (CSSNumberHelper.getValueWithUnit ("", true));
    assertNull (CSSNumberHelper.getValueWithUnit (" ", false));
    assertNull (CSSNumberHelper.getValueWithUnit (" ", true));
    assertNull (CSSNumberHelper.getValueWithUnit (" mm ", true));
    assertNull (CSSNumberHelper.getValueWithUnit (" % ", true));
    assertNull (CSSNumberHelper.getValueWithUnit (" 50 xyz ", true));
    assertNull (CSSNumberHelper.getValueWithUnit (" 50 gd ", true));
    assertNull (CSSNumberHelper.getValueWithUnit ("50gd", true));
  }
}
