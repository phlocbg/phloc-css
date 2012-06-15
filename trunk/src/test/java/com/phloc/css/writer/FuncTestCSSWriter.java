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
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.io.resource.ClassPathResource;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.commons.io.streams.NonBlockingStringWriter;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.handler.CSSHandler;

public final class FuncTestCSSWriter
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSWriter.class);

  private void _testMe (final File aFile, final ECSSVersion eVersion) throws IOException
  {
    if (false)
      s_aLogger.info (aFile.getAbsolutePath ());

    // read and interpret
    final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                CCharset.CHARSET_UTF_8_OBJ,
                                                                eVersion);
    assertNotNull (aFile.getAbsolutePath (), aCSS);

    // Both normal and optimized!
    for (int i = 0; i < 2; ++i)
    {
      // write to buffer
      final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();
      new CSSWriter (eVersion, i == 1).writeCSS (aCSS, aSW);
      final String sContent = aSW.toString ();
      if (false)
        System.out.println ("--" + i + "--\n" + sContent);

      // read again from buffer
      assertEquals (aFile.getAbsolutePath () + (i == 0 ? " unoptimized" : " optimized"),
                    aCSS,
                    CSSHandler.readFromString (sContent, CCharset.CHARSET_UTF_8_OBJ, eVersion));
    }
  }

  @Test
  public void testScanTestResourcesHandler21 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler21"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      _testMe (aFile, ECSSVersion.CSS21);
    }
  }

  @Test
  public void testScanTestResourcesHandler30 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      _testMe (aFile, ECSSVersion.CSS30);
    }
  }

  @Test
  public void testRead30Write21 () throws IOException
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      try
      {
        // read and interpret CSS 3.0
        final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                    CCharset.CHARSET_UTF_8_OBJ,
                                                                    ECSSVersion.CSS30);
        assertNotNull (aCSS);

        // write to CSS 2.1
        final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();
        new CSSWriter (ECSSVersion.CSS21).writeCSS (aCSS, aSW);
        fail (aFile.toString () + ": " + aSW.toString ());
      }
      catch (final IllegalStateException ex)
      {}
    }
  }

  @Test
  public void testCompressCSS_Size () throws IOException
  {
    final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();

    final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new ClassPathResource ("/css/phloc/test/content.css"),
                                                                CCharset.CHARSET_UTF_8_OBJ,
                                                                ECSSVersion.CSS21);
    assertNotNull (aCSS);

    // write to buffer
    new CSSWriter (ECSSVersion.CSS21, true).writeCSS (aCSS, aSW);
    final String sContent = aSW.toString ();
    assertEquals (2866, sContent.length ());
  }

  @Test
  public void testIndentation () throws IOException
  {
    final String sCSS = "h1 { color : red ; margin: 1px; } h2 { color: rgb(1,2,3);} h3{} @keyframes x { from { align:left;color:#123;} to { x:y; }}";
    final CascadingStyleSheet aCSS = CSSHandler.readFromString (sCSS, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false);
    final CSSWriter aWriter = new CSSWriter (aSettings).setWriteHeaderText (false);
    assertEquals ("h1 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "h3 {}\n"
                  + "\n"
                  + "@keyframes x {\n"
                  + "  from {\n"
                  + "    align:left;\n"
                  + "    color:#123;\n"
                  + "  }\n"
                  + "  to { x:y; }\n"
                  + "}\n", aWriter.getCSSAsString (aCSS));

    // Change indentation
    aSettings.setIndent ("\t");
    assertEquals ("h1 {\n"
                  + "\tcolor:red;\n"
                  + "\tmargin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "h3 {}\n"
                  + "\n"
                  + "@keyframes x {\n"
                  + "\tfrom {\n"
                  + "\t\talign:left;\n"
                  + "\t\tcolor:#123;\n"
                  + "\t}\n"
                  + "\tto { x:y; }\n"
                  + "}\n", aWriter.getCSSAsString (aCSS));
  }

  @Test
  public void testHeaderText () throws IOException
  {
    final String sCSS = "h1 { color : red ; margin: 1px; }h2 { color : red ; margin: 1px; }";
    final CascadingStyleSheet aCSS = CSSHandler.readFromString (sCSS, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);

    // Non-optimized version
    CSSWriter aWriter = new CSSWriter (ECSSVersion.CSS30, false).setWriteHeaderText (true).setHeaderText ("Unit test");
    assertEquals ("/*\n"
                  + " * Unit test\n"
                  + " */\n"
                  + "h1 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n", aWriter.getCSSAsString (aCSS));

    // Optimized version
    aWriter = new CSSWriter (ECSSVersion.CSS30, true).setWriteHeaderText (true).setHeaderText ("Unit test2");
    assertEquals ("/*\n" + " * Unit test2\n" + " */\n" + "h1{color:red;margin:1px;}h2{color:red;margin:1px;}",
                  aWriter.getCSSAsString (aCSS));
  }
}
