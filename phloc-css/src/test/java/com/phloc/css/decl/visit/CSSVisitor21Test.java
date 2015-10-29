/**
 * Copyright (C) 2006-2015 phloc systems
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
package com.phloc.css.decl.visit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterEndsWith;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.css.AbstractCSS21Test;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

/**
 * Test class for class {@link CSSVisitor}.
 * 
 * @author Philip Helger
 */
public final class CSSVisitor21Test extends AbstractCSS21Test
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSVisitor21Test.class);

  @Test
  public void testVisitContent21 ()
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/testfiles/css21/good"),
                                                                new FilenameFilterEndsWith (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS21);
      assertNotNull (sKey, aCSS);
      CSSVisitor.visitCSSUrl (aCSS, new MockUrlVisitor (sKey));
    }
  }

  @Test
  public void testVisitConstantCSS ()
  {
    // CSS 1
    CascadingStyleSheet aCSS = CSSReader.readFromString (CSS1, ECSSVersion.CSS21);
    assertNotNull (aCSS);
    MockCountingUrlVisitor aVisitor = new MockCountingUrlVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (4, aVisitor.getCount ());

    // CSS 2
    aCSS = CSSReader.readFromString (CSS2, ECSSVersion.CSS21);
    assertNotNull (aCSS);
    aVisitor = new MockCountingUrlVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (2, aVisitor.getCount ());
  }
}
