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
package com.phloc.css;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.commons.string.StringHelper;

/**
 * Test class for class {@link ECSSUnit}.
 * 
 * @author Philip Helger
 */
public final class ECSSUnitTest
{
  @Test
  public void testBasic ()
  {
    for (final ECSSUnit e : ECSSUnit.values ())
    {
      assertTrue (StringHelper.hasText (e.getName ()));
      assertNotNull (e.getMinimumCSSVersion ());
      assertSame (e, ECSSUnit.getFromNameOrNull (e.getName ()));
      assertTrue (StringHelper.hasText (e.format (4711)));
      assertTrue (StringHelper.hasText (e.format (47.11)));
    }
  }

  @Test
  public void testStatics ()
  {
    assertEquals ("5pt", ECSSUnit.pt (5));
    assertEquals ("0pt", ECSSUnit.pt (0));
    assertEquals ("-5pt", ECSSUnit.pt (-5));
    assertEquals ("5pc", ECSSUnit.pc (5));
    assertEquals ("0pc", ECSSUnit.pc (0));
    assertEquals ("-5pc", ECSSUnit.pc (-5));
    assertEquals ("5in", ECSSUnit.in (5));
    assertEquals ("0in", ECSSUnit.in (0));
    assertEquals ("-5in", ECSSUnit.in (-5));
    assertEquals ("5mm", ECSSUnit.mm (5));
    assertEquals ("0mm", ECSSUnit.mm (0));
    assertEquals ("-5mm", ECSSUnit.mm (-5));
    assertEquals ("5cm", ECSSUnit.cm (5));
    assertEquals ("0cm", ECSSUnit.cm (0));
    assertEquals ("-5cm", ECSSUnit.cm (-5));
    assertEquals ("5px", ECSSUnit.px (5));
    assertEquals ("0px", ECSSUnit.px (0));
    assertEquals ("-5px", ECSSUnit.px (-5));
    assertEquals ("5em", ECSSUnit.em (5));
    assertEquals ("0em", ECSSUnit.em (0));
    assertEquals ("-5em", ECSSUnit.em (-5));
    assertEquals ("5em", ECSSUnit.em (5.0));
    assertEquals ("0em", ECSSUnit.em (0.0));
    assertEquals ("-5em", ECSSUnit.em (-5.0));
    assertEquals ("5ex", ECSSUnit.ex (5));
    assertEquals ("0ex", ECSSUnit.ex (0));
    assertEquals ("-5ex", ECSSUnit.ex (-5));
    assertEquals ("5%", ECSSUnit.perc (5));
    assertEquals ("0%", ECSSUnit.perc (0));
    assertEquals ("-5%", ECSSUnit.perc (-5));
  }
}