/**
 * Copyright (C) 2006-2014 phloc systems
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
package com.phloc.css.decl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.phloc.commons.mock.PhlocTestUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.writer.CSSWriterSettings;

/**
 * Test class for class {@link CSSRGB}.
 * 
 * @author Philip Helger
 */
public final class CSSRGBTest
{
  @Test
  public void testBasic ()
  {
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false);
    final CSSRGB aColor = new CSSRGB (1, 2, 3);
    assertEquals ("rgb(1,2,3)", aColor.getAsCSSString (aSettings, 0));

    PhlocTestUtils.testDefaultImplementationWithEqualContentObject (aColor, new CSSRGB (aColor));
    PhlocTestUtils.testDefaultImplementationWithEqualContentObject (aColor, new CSSRGB (1, 2, 3));
    PhlocTestUtils.testDefaultImplementationWithDifferentContentObject (aColor, new CSSRGB (0, 2, 3));
    PhlocTestUtils.testDefaultImplementationWithDifferentContentObject (aColor, new CSSRGB (1, 0, 3));
    PhlocTestUtils.testDefaultImplementationWithDifferentContentObject (aColor, new CSSRGB (1, 2, 0));
  }
}
