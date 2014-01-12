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
package com.phloc.css.supplementary.wiki;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;
import com.phloc.css.writer.CSSWriter;

/**
 * This is example code to create a font-face rule from scratch
 * 
 * @author Philip Helger
 */
public final class WikiTest
{
  @Test
  public void test ()
  {
    final CascadingStyleSheet aCSS = WikiCreateFontFaceRule.createFontFace ("Your \"typeface\"",
                                                                            "local font name",
                                                                            "folder/",
                                                                            "myfont");
    final String sCSS = new CSSWriter (ECSSVersion.CSS30).getCSSAsString (aCSS);
    System.out.println (sCSS);

    final CascadingStyleSheet aCSS2 = CSSReader.readFromString (sCSS,
                                                                CCharset.CHARSET_ISO_8859_1_OBJ,
                                                                ECSSVersion.CSS30);
    assertNotNull (aCSS2);
    assertEquals (aCSS, aCSS2);
  }
}
