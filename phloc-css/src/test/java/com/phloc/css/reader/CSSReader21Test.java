/**
 * Copyright (C) 2006-2013 phloc systems
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

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.writer.CSSWriter;

/**
 * Test reading CSS 2.1 files
 * 
 * @author Philip Helger
 */
public final class CSSReader21Test extends AbstractFuncTestCSSReader
{
  public CSSReader21Test ()
  {
    super (ECSSVersion.CSS21, CCharset.CHARSET_UTF_8_OBJ, false);
  }

  @Test
  public void testReadAll21Good () throws IOException
  {
    testReadGood ("src/test/resources/testfiles/css21/good");
    testReadGood ("src/test/resources/testfiles/css21/bad_but_succeeding");
  }

  @Test
  public void testReadAll21Bad () throws IOException
  {
    testReadBad ("src/test/resources/testfiles/css21/bad");
    testReadBad ("src/test/resources/testfiles/css21/good_but_failing");
  }

  @Test
  public void testReadAll21BadButRecoverable () throws IOException
  {
    testReadBadButRecoverable ("src/test/resources/testfiles/css21/bad_but_recoverable");
  }

  @Test
  public void testReadSpecialGood () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css21/good/artificial/test-url.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
    assertNotNull (aCSS);

    final String sCSS = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
    m_aLogger.info (sCSS);
  }
}
