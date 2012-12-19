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
package com.phloc.css.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterEndsWith;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

public final class FuncTestCSSReader21
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSReader21.class);

  @Test
  public void testScanTestResourcesArtificial21 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS21;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/testfiles/css21/good/artificial"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String sCSS = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertEquals (sKey, aCSS, CSSReader.readFromString (sCSS, aCharset, eVersion));

      // Write non-optimized version and compare it
      sCSS = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertEquals (sKey, aCSS, CSSReader.readFromString (sCSS, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      sCSS = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertNotNull (sKey, CSSReader.readFromString (sCSS, aCharset, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesCss21 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS21;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/testfiles/css21/good"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String sCSS = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertEquals (sKey, aCSS, CSSReader.readFromString (sCSS, aCharset, eVersion));

      // Write non-optimized version and compare it
      sCSS = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertEquals (sKey, aCSS, CSSReader.readFromString (sCSS, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      sCSS = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertNotNull (sKey, CSSReader.readFromString (sCSS, aCharset, eVersion));
    }
  }
}
