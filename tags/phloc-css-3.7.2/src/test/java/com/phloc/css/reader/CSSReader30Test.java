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
package com.phloc.css.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.errorhandler.CollectingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.writer.CSSWriter;

/**
 * Test reading CSS 3.0 stuff
 * 
 * @author Philip Helger
 */
public final class CSSReader30Test extends AbstractFuncTestCSSReader
{
  public CSSReader30Test ()
  {
    super (ECSSVersion.CSS30, CCharset.CHARSET_UTF_8_OBJ, false);
  }

  @Test
  public void testReadAll30Good ()
  {
    testReadGood ("src/test/resources/testfiles/css30/good");
  }

  @Test
  public void testReadAll30BadButSucceeding ()
  {
    testReadGood ("src/test/resources/testfiles/css30/bad_but_succeeding");
  }

  @Test
  public void testReadAll30Bad ()
  {
    testReadBad ("src/test/resources/testfiles/css30/bad");
  }

  @Test
  public void testReadAll30GoodButFailing ()
  {
    testReadBad ("src/test/resources/testfiles/css30/good_but_failing");
  }

  @Test
  public void testReadAll30BadBadButRecoverable ()
  {
    testReadBadButRecoverable ("src/test/resources/testfiles/css30/bad_but_recoverable");
  }

  @Test
  public void testReadSpecialGood ()
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/good/issue15.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
    assertNotNull (aCSS);

    final String sCSS = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
    m_aLogger.info (sCSS);
  }

  @Test
  public void testReadSpecialBadButRecoverable ()
  {
    final CollectingCSSParseErrorHandler aErrors = new CollectingCSSParseErrorHandler (new LoggingCSSParseErrorHandler ());
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/bad_but_recoverable/test-string.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion, aErrors);
    assertNotNull (aCSS);
  }

  @Test
  public void testSpecialCases ()
  {
    String sCSS = ".class{color:red;.class{color:green}.class{color:blue}";
    CascadingStyleSheet aCSS = CSSReader.readFromString (sCSS,
                                                         CCharset.CHARSET_ISO_8859_1_OBJ,
                                                         ECSSVersion.CSS30,
                                                         DoNothingCSSParseErrorHandler.getInstance ());
    assertEquals (".class{color:red}.class{color:blue}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));

    sCSS = "  \n/* comment */\n  \n.class{color:red;}";
    aCSS = CSSReader.readFromString (sCSS,
                                     CCharset.CHARSET_ISO_8859_1_OBJ,
                                     ECSSVersion.CSS30,
                                     DoNothingCSSParseErrorHandler.getInstance ());
    assertEquals (".class{color:red}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));

    sCSS = "\u00ef\u00bb\u00bf\r\n"
           + "/* validation styles */\r\n"
           + "\r\n"
           + ".validation-summary-errors\r\n"
           + "{\r\n"
           + "  color:Red;\r\n"
           + "}";
    aCSS = CSSReader.readFromString (sCSS,
                                     CCharset.CHARSET_ISO_8859_1_OBJ,
                                     ECSSVersion.CSS30,
                                     DoNothingCSSParseErrorHandler.getInstance ());
    assertEquals (".validation-summary-errors{color:Red}",
                  new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));
  }
}