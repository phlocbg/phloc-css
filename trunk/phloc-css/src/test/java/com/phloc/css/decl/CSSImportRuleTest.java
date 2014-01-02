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

import com.phloc.css.ECSSVersion;
import com.phloc.css.writer.CSSWriterSettings;

/**
 * Test class for class {@link CSSImportRule}.
 * 
 * @author Philip Helger
 */
public final class CSSImportRuleTest
{
  @Test
  public void testBasic ()
  {
    final CSSURI aURI = new CSSURI ("a.gif");
    final CSSImportRule aImportRule = new CSSImportRule (aURI);
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false);
    assertEquals ("@import url(a.gif);\n", aImportRule.getAsCSSString (aSettings, 0));
    aSettings.setQuoteURLs (true);
    assertEquals ("@import url('a.gif');\n", aImportRule.getAsCSSString (aSettings, 0));
  }
}
