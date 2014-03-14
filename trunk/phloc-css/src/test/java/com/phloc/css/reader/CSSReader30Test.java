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
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.charset.EUnicodeBOM;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.io.streamprovider.ByteArrayInputStreamProvider;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.errorhandler.CollectingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

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
    CascadingStyleSheet aCSS, aCSS2;
    aCSS = CSSReader.readFromString (sCSS,
                                     CCharset.CHARSET_ISO_8859_1_OBJ,
                                     ECSSVersion.CSS30,
                                     new LoggingCSSParseErrorHandler ());
    assertEquals (".class{color:red}.class{color:blue}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));

    sCSS = "  \n/* comment */\n  \n.class{color:red;}";
    aCSS = CSSReader.readFromString (sCSS,
                                     CCharset.CHARSET_ISO_8859_1_OBJ,
                                     ECSSVersion.CSS30,
                                     new LoggingCSSParseErrorHandler ());
    assertEquals (".class{color:red}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));

    // With Umlauts
    sCSS = "@charset 'iso-8859-1'; div { colör: räd; }";
    aCSS = CSSReader.readFromString (sCSS,
                                     CCharset.CHARSET_UTF_16_OBJ,
                                     ECSSVersion.CSS30,
                                     new LoggingCSSParseErrorHandler ());
    assertEquals ("div{colör:räd}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));
    aCSS2 = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertEquals ("div{colör:räd}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS2));
    assertEquals (aCSS, aCSS2);
  }

  @Test
  public void testReadWithBOM ()
  {
    final String sCSSBase = "/* comment */.class{color:red}.class{color:blue}";
    for (final EUnicodeBOM eBOM : EUnicodeBOM.values ())
    {
      Charset aDeterminedCharset = null;
      switch (eBOM)
      {
        case BOM_UTF_8:
          aDeterminedCharset = CharsetManager.getCharsetFromName ("utf-8");
          break;
        case BOM_UTF_16_BIG_ENDIAN:
          aDeterminedCharset = CharsetManager.getCharsetFromName ("utf-16be");
          break;
        case BOM_UTF_16_LITTLE_ENDIAN:
          aDeterminedCharset = CharsetManager.getCharsetFromName ("utf-16le");
          break;
        case BOM_UTF_32_BIG_ENDIAN:
          aDeterminedCharset = CharsetManager.getCharsetFromName ("utf-32be");
          break;
        case BOM_UTF_32_LITTLE_ENDIAN:
          aDeterminedCharset = CharsetManager.getCharsetFromName ("utf-32le");
          break;
        case BOM_GB_18030:
          aDeterminedCharset = CharsetManager.getCharsetFromName ("gb18030");
          break;
        default:
          // The charset required by the BOM is not a standard charset
          break;
      }
      if (aDeterminedCharset != null)
      {
        final CascadingStyleSheet aCSS = CSSReader.readFromStream (new ByteArrayInputStreamProvider (ArrayHelper.getConcatenated (eBOM.getBytes (),
                                                                                                                                  sCSSBase.getBytes (aDeterminedCharset))),
                                                                   aDeterminedCharset,
                                                                   ECSSVersion.CSS30,
                                                                   DoNothingCSSParseErrorHandler.getInstance ());
        assertNotNull ("Failed to read with BOM " + eBOM, aCSS);
        assertEquals (".class{color:red}.class{color:blue}",
                      new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));
      }
    }
  }
}
