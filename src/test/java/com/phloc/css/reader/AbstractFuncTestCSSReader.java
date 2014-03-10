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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.file.filter.FilenameFilterEndsWith;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.mock.PhlocTestUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.errorhandler.CollectingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

/**
 * Abstract class for reading multiple CSS files
 * 
 * @author Philip Helger
 */
public abstract class AbstractFuncTestCSSReader
{
  protected final Logger m_aLogger = LoggerFactory.getLogger (getClass ());
  private final ECSSVersion m_eVersion;
  private final Charset m_aCharset;
  private final boolean m_bDebug;

  protected AbstractFuncTestCSSReader (@Nonnull final ECSSVersion eVersion,
                                       @Nonnull final Charset aCharset,
                                       final boolean bDebug)
  {
    m_eVersion = eVersion;
    m_aCharset = aCharset;
    m_bDebug = bDebug;
  }

  protected final void testReadGood (final String sBaseDir)
  {
    final File aBaseDir = new File (sBaseDir);
    if (!aBaseDir.exists ())
      throw new IllegalArgumentException ("BaseDir " + sBaseDir + " does not exist!");

    for (final File aFile : FileSystemRecursiveIterator.create (aBaseDir, new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (m_bDebug)
        m_aLogger.info (sKey);
      final CollectingCSSParseErrorHandler aErrorHdl = new CollectingCSSParseErrorHandler (new LoggingCSSParseErrorHandler ());
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, m_aCharset, m_eVersion, aErrorHdl);
      assertNotNull (sKey, aCSS);

      // May have errors or not
      if (m_bDebug)
        m_aLogger.info (aErrorHdl.getAllParseErrors ().toString ());

      PhlocTestUtils.testDefaultSerialization (aCSS);

      // Write optimized version and compare it
      String sCSS = new CSSWriter (m_eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      if (m_bDebug)
        m_aLogger.info (sCSS);

      final CascadingStyleSheet aCSSReRead = CSSReader.readFromString (sCSS, m_aCharset, m_eVersion);
      assertNotNull ("Failed to parse " + sKey + ":\n" + sCSS, aCSSReRead);
      assertEquals (sKey, aCSS, aCSSReRead);

      // Write non-optimized version and compare it
      sCSS = new CSSWriter (m_eVersion, false).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      if (m_bDebug)
        m_aLogger.info (sCSS);
      assertEquals (sKey, aCSS, CSSReader.readFromString (sCSS, m_aCharset, m_eVersion));

      // Write non-optimized and code-removed version and ensure it is not
      // null
      sCSS = new CSSWriter (new CSSWriterSettings (m_eVersion, false).setRemoveUnnecessaryCode (true)).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      assertNotNull (sKey, CSSReader.readFromString (sCSS, m_aCharset, m_eVersion));
    }
  }

  protected final void testReadBad (final String sBaseDir)
  {
    final File aBaseDir = new File (sBaseDir);
    if (!aBaseDir.exists ())
      throw new IllegalArgumentException ("BaseDir " + sBaseDir + " does not exist!");

    for (final File aFile : FileSystemRecursiveIterator.create (aBaseDir, new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (m_bDebug)
        m_aLogger.info (sKey);

      // Handle each error as a fatal error!
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, m_aCharset, m_eVersion);
      assertNull (sKey, aCSS);
    }
  }

  protected final void testReadBadButRecoverable (final String sBaseDir)
  {
    final File aBaseDir = new File (sBaseDir);
    if (!aBaseDir.exists ())
      throw new IllegalArgumentException ("BaseDir " + sBaseDir + " does not exist!");

    for (final File aFile : FileSystemRecursiveIterator.create (aBaseDir, new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (m_bDebug)
        m_aLogger.info (sKey);

      // Handle each error as a fatal error!
      final CollectingCSSParseErrorHandler aErrorHdl = new CollectingCSSParseErrorHandler (new LoggingCSSParseErrorHandler ());
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, m_aCharset, m_eVersion, aErrorHdl);
      assertNotNull (sKey, aCSS);
      assertTrue (sKey, aErrorHdl.hasParseErrors ());
      assertTrue (sKey, aErrorHdl.getParseErrorCount () > 0);
      if (m_bDebug)
        m_aLogger.info (aErrorHdl.getAllParseErrors ().toString ());

      // Write optimized version and re-read it
      final String sCSS = new CSSWriter (m_eVersion, true).getCSSAsString (aCSS);
      assertNotNull (sKey, sCSS);
      if (m_bDebug)
        m_aLogger.info (sCSS);

      final CascadingStyleSheet aCSSReRead = CSSReader.readFromString (sCSS, m_aCharset, m_eVersion);
      assertNotNull ("Failed to parse:\n" + sCSS, aCSSReRead);
      assertEquals (sKey, aCSS, aCSSReRead);
    }
  }
}
