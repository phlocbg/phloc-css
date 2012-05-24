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
package com.phloc.css.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.commons.io.streamprovider.StringInputStreamProvider;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.writer.CSSWriter;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

public final class FuncTestCSSHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSHandler.class);

  @Test
  public void testScanTestResourcesHandler21 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler21"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      s_aLogger.info (aFile.getName ());
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                  CCharset.CHARSET_UTF_8_OBJ,
                                                                  ECSSVersion.CSS21);
      assertNotNull (aFile.getAbsolutePath (), aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (ECSSVersion.CSS21, true).getCSSAsString (aCSS);
      assertNotNull (s);
      assertEquals (aCSS, CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                                     CCharset.CHARSET_UTF_8_OBJ,
                                                     ECSSVersion.CSS21));

      // Write non-optimized version and compare it
      s = new CSSWriter (ECSSVersion.CSS21, false).getCSSAsString (aCSS);
      assertNotNull (s);
      assertEquals (aCSS, CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                                     CCharset.CHARSET_UTF_8_OBJ,
                                                     ECSSVersion.CSS21));
    }
  }

  @Test
  public void testScanTestResourcesHandler30 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                  CCharset.CHARSET_UTF_8_OBJ,
                                                                  ECSSVersion.CSS30);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey,
                    aCSS,
                    CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                               CCharset.CHARSET_UTF_8_OBJ,
                                               ECSSVersion.CSS30));

      // Write non-optimized version and compare it
      s = new CSSWriter (ECSSVersion.CSS30, false).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey,
                    aCSS,
                    CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                               CCharset.CHARSET_UTF_8_OBJ,
                                               ECSSVersion.CSS30));
    }
  }

  @Test
  public void testScanTestResourcesCss21 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      s_aLogger.info (aFile.getPath ());
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                  CCharset.CHARSET_UTF_8_OBJ,
                                                                  ECSSVersion.CSS21);
      assertNotNull (aFile.getAbsolutePath (), aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (ECSSVersion.CSS21, true).getCSSAsString (aCSS);
      assertNotNull (s);
      assertEquals (aCSS, CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                                     CCharset.CHARSET_UTF_8_OBJ,
                                                     ECSSVersion.CSS21));

      // Write non-optimized version and compare it
      s = new CSSWriter (ECSSVersion.CSS21, false).getCSSAsString (aCSS);
      assertNotNull (s);
      assertEquals (aCSS, CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                                     CCharset.CHARSET_UTF_8_OBJ,
                                                     ECSSVersion.CSS21));
    }
  }

  @Test
  public void testScanTestResourcesCss30 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      s_aLogger.info (aFile.getPath ());
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                  CCharset.CHARSET_UTF_8_OBJ,
                                                                  ECSSVersion.CSS30);
      assertNotNull (aFile.getAbsolutePath (), aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS);
      assertNotNull (s);
      assertEquals (aCSS, CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                                     CCharset.CHARSET_UTF_8_OBJ,
                                                     ECSSVersion.CSS30));

      // Write non-optimized version and compare it
      s = new CSSWriter (ECSSVersion.CSS30, false).getCSSAsString (aCSS);
      assertNotNull (s);
      assertEquals (aCSS, CSSHandler.readFromStream (new StringInputStreamProvider (s, CCharset.CHARSET_UTF_8_OBJ),
                                                     CCharset.CHARSET_UTF_8_OBJ,
                                                     ECSSVersion.CSS30));
    }
  }

  @Test
  @Ignore
  @SuppressWarnings (value = "DMI_HARDCODED_ABSOLUTE_FILENAME")
  public void testScanDrive ()
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("/"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      if (false)
        s_aLogger.info (aFile.getAbsolutePath ());
      CSSHandler.readFromStream (new FileSystemResource (aFile), CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS21);
    }
  }
}
