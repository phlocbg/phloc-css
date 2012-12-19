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

import org.junit.Ignore;
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

public final class FuncTestCSSReader30
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSReader30.class);

  @Test
  public void testScanTestResourcesArtifical30 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/testfiles/css30/good/artificial"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (true)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String sCSS = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      if (false)
        s_aLogger.info (sCSS);

      final CascadingStyleSheet aCSSReRead = CSSReader.readFromString (sCSS, aCharset, eVersion);
      assertNotNull ("Failed to parse:\n" + sCSS, aCSSReRead);
      assertEquals (sKey, aCSS, aCSSReRead);

      // Write non-optimized version and compare it
      sCSS = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      if (false)
        s_aLogger.info (sCSS);
      assertEquals (sKey, aCSS, CSSReader.readFromString (sCSS, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      sCSS = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertNotNull (sKey, CSSReader.readFromString (sCSS, aCharset, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesCss30 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/testfiles/css30/good"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (true)
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

  @Ignore
  @Test
  public void testReadSpecial ()
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/good_but_failing/w3c/selectors/css3-modsel-175a.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
    assertNotNull (aCSS);
  }
}
