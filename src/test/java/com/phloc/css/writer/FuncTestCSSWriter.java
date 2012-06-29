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
package com.phloc.css.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterEndsWith;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.io.resource.ClassPathResource;
import com.phloc.commons.io.streams.NonBlockingStringWriter;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

public final class FuncTestCSSWriter
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSWriter.class);

  private void _testMe (final File aFile, final ECSSVersion eVersion) throws IOException
  {
    if (false)
      s_aLogger.info (aFile.getAbsolutePath ());

    // read and interpret
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, CCharset.CHARSET_UTF_8_OBJ, eVersion);
    assertNotNull (aFile.getAbsolutePath (), aCSS);

    // Both normal and optimized!
    for (int i = 0; i < 2; ++i)
    {
      // write to buffer
      final String sCSS = new CSSWriter (eVersion, i == 1).getCSSAsString (aCSS);
      if (false)
        System.out.println ("--" + i + "--\n" + sCSS);

      // read again from buffer
      assertEquals (aFile.getAbsolutePath () + (i == 0 ? " unoptimized" : " optimized"),
                    aCSS,
                    CSSReader.readFromString (sCSS, CCharset.CHARSET_UTF_8_OBJ, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesHandler21 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler21"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      _testMe (aFile, ECSSVersion.CSS21);
    }
  }

  @Test
  public void testScanTestResourcesHandler30 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      _testMe (aFile, ECSSVersion.CSS30);
    }
  }

  @Test
  public void testRead30Write21 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      try
      {
        // read and interpret CSS 3.0
        final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
        assertNotNull (aCSS);

        // write to CSS 2.1
        final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();
        new CSSWriter (ECSSVersion.CSS21).writeCSS (aCSS, aSW);
        fail (aFile.toString () + ": " + aSW.getAsString ());
      }
      catch (final IllegalStateException ex)
      {}
    }
  }

  @Test
  public void testCompressCSS_Size () throws IOException
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromStream (new ClassPathResource ("/css/phloc/test/content.css"),
                                                               CCharset.CHARSET_UTF_8_OBJ,
                                                               ECSSVersion.CSS21);
    assertNotNull (aCSS);

    // Only whitespace optimisation
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS21, true);
    String sContent = new CSSWriter (aSettings).getCSSAsString (aCSS);
    assertEquals (2873, sContent.length ());

    // Also remove empty declarations
    aSettings.setRemoveUnnecessaryCode (true);
    sContent = new CSSWriter (aSettings).getCSSAsString (aCSS);
    assertEquals (2866, sContent.length ());
  }
}
