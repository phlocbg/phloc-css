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
import static org.junit.Assert.assertNotNull;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.reader.CSSReader;

/**
 * Test class for {@link CSSUnknownRule}.
 * 
 * @author Philip Helger
 */
public final class CSSUnknownRuleTest
{
  @Nonnull
  private static CSSUnknownRule _parse (final String sCSS)
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sCSS, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST);
    assertNotNull (sCSS, aCSS);
    assertEquals (1, aCSS.getUnknownRuleCount ());
    final CSSUnknownRule ret = aCSS.getAllUnknownRules ().get (0);
    assertNotNull (ret);
    return ret;
  }

  @Test
  public void testRead ()
  {
    CSSUnknownRule aNSR;
    aNSR = _parse ("@-moz-document {}");
    assertEquals ("@-moz-document", aNSR.getDeclaration ());
    assertEquals ("", aNSR.getParameterList ());
    assertEquals ("", aNSR.getBody ());

    aNSR = _parse ("@-moz-document    anything else or whatever 4711    {   }");
    assertEquals ("@-moz-document", aNSR.getDeclaration ());
    assertEquals ("anything else or whatever 4711", aNSR.getParameterList ());
    assertEquals ("", aNSR.getBody ());

    aNSR = _parse ("@-moz-document { color: red; }");
    assertEquals ("@-moz-document", aNSR.getDeclaration ());
    assertEquals ("", aNSR.getParameterList ());
    assertEquals ("color: red;", aNSR.getBody ());

    aNSR = _parse ("@three-dee {\n"
                   + "  @background-lighting {\n"
                   + "    azimuth: 30deg;\n"
                   + "    elevation: 190deg;\n"
                   + "  }\n"
                   + "  h1 { color: red }\n"
                   + "}");
    assertEquals ("@three-dee", aNSR.getDeclaration ());
    assertEquals ("", aNSR.getParameterList ());
    assertEquals ("@background-lighting {\n"
                  + "    azimuth: 30deg;\n"
                  + "    elevation: 190deg;\n"
                  + "  }\n"
                  + "  h1 { color: red }", aNSR.getBody ());

  }
}
