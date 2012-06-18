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
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.mutable.Wrapper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.parser.ParseException;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

public final class FuncTestCSSHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSHandler.class);

  @Test
  public void testScanTestResourcesHandler21 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS21;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler21"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSHandler.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized version and compare it
      s = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      s = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertNotNull (sKey, CSSHandler.readFromString (s, aCharset, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesHandler30 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSHandler.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, s);

      if (false)
        s_aLogger.info (s);

      final CascadingStyleSheet aCSSReRead = CSSHandler.readFromString (s, aCharset, eVersion);
      assertNotNull ("Failed to parse:\n" + s, aCSSReRead);
      assertEquals (sKey, aCSS, aCSSReRead);

      // Write non-optimized version and compare it
      s = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      s = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertNotNull (sKey, CSSHandler.readFromString (s, aCharset, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesCss21 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS21;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSHandler.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized version and compare it
      s = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      s = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertNotNull (sKey, CSSHandler.readFromString (s, aCharset, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesCss30 () throws IOException
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSHandler.readFromFile (aFile, aCharset, eVersion);
      assertNotNull (sKey, aCSS);

      // Write optimized version and compare it
      String s = new CSSWriter (eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized version and compare it
      s = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertEquals (sKey, aCSS, CSSHandler.readFromString (s, aCharset, eVersion));

      // Write non-optimized and code-removed version and ensure it is not null
      s = new CSSWriter (new CSSWriterSettings (eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, s);
      assertNotNull (sKey, CSSHandler.readFromString (s, aCharset, eVersion));
    }
  }

  @Test
  @Ignore
  @SuppressWarnings ("DMI_HARDCODED_ABSOLUTE_FILENAME")
  public void testScanDrive ()
  {
    int nFilesOK = 0;
    int nFilesError = 0;
    final Map <File, ParseException> aErrors = new LinkedHashMap <File, ParseException> ();
    final Wrapper <File> aCurrentFile = new Wrapper <File> ();
    final ICSSParseExceptionHandler aHdl = new ICSSParseExceptionHandler ()
    {
      public void onException (final ParseException ex)
      {
        aErrors.put (aCurrentFile.get (), ex);
      }
    };
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("/"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      if (false)
        s_aLogger.info (aFile.getAbsolutePath ());
      aCurrentFile.set (aFile);
      final CascadingStyleSheet aCSS = CSSHandler.readFromFile (aFile,
                                                                CCharset.CHARSET_UTF_8_OBJ,
                                                                ECSSVersion.CSS30,
                                                                aHdl);
      if (aCSS == null)
        nFilesError++;
      else
        nFilesOK++;
    }

    s_aLogger.info ("Done");
    for (final Map.Entry <File, ParseException> aEntry : aErrors.entrySet ())
      s_aLogger.info ("  " + aEntry.getKey ().getAbsolutePath () + ":\n" + aEntry.getValue ().getMessage () + "\n");
    s_aLogger.info ("OK: " + nFilesOK + "; Error: " + nFilesError);
  }
}
