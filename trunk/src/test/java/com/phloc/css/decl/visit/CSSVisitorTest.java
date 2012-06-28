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
package com.phloc.css.decl.visit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.css.AbstractCSS30Test;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.reader.CSSReader;

/**
 * Test class for class {@link CSSVisitor}.
 * 
 * @author philip
 */
public final class CSSVisitorTest extends AbstractCSS30Test
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSVisitorTest.class);

  private static final class MockSysOutVisitor extends DefaultCSSUrlVisitor
  {
    private final String m_sFilename;

    public MockSysOutVisitor (final String sFilename)
    {
      m_sFilename = sFilename;
    }

    @Override
    public void onImport (final CSSImportRule aImportRule)
    {
      assertNotNull (m_sFilename, aImportRule.getLocation ());
    }

    @Override
    public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                  @Nonnull final CSSDeclaration aDeclaration,
                                  @Nonnull final CSSExpressionMemberTermURI aURITerm)
    {
      assertNotNull (m_sFilename, aURITerm.getURI ());
    }
  }

  private static final class MockCountingVisitor extends DefaultCSSUrlVisitor
  {
    private int m_nCount = 0;

    public MockCountingVisitor ()
    {}

    @Override
    public void onImport (final CSSImportRule aImportRule)
    {
      assertNotNull (aImportRule.getLocation ());
      m_nCount++;
    }

    @Override
    public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                  @Nonnull final CSSDeclaration aDeclaration,
                                  @Nonnull final CSSExpressionMemberTermURI aURITerm)
    {
      assertNotNull (aURITerm.getURI ());
      m_nCount++;
    }

    public int getCount ()
    {
      return m_nCount;
    }
  }

  @Test
  public void testVisitContent ()
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS21);
      assertNotNull (sKey, aCSS);
      CSSVisitor.visitCSSUrl (aCSS, new MockSysOutVisitor (sKey));
    }

    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
      assertNotNull (sKey, aCSS);
      CSSVisitor.visitCSSUrl (aCSS, new MockSysOutVisitor (sKey));
    }
  }

  @Test
  public void testVisitConstantCSS ()
  {
    // CSS 1
    CascadingStyleSheet aCSS = CSSReader.readFromString (CSS1, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    MockCountingVisitor aVisitor = new MockCountingVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (4, aVisitor.getCount ());

    // CSS 2
    aCSS = CSSReader.readFromString (CSS2, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    aVisitor = new MockCountingVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (17, aVisitor.getCount ());

    // CSS 3
    aCSS = CSSReader.readFromString (CSS3, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    aVisitor = new MockCountingVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (1, aVisitor.getCount ());

    // CSS 4
    aCSS = CSSReader.readFromString (CSS4, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    aVisitor = new MockCountingVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (1, aVisitor.getCount ());

    // CSS 5
    aCSS = CSSReader.readFromString (CSS5, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    aVisitor = new MockCountingVisitor ();
    CSSVisitor.visitCSSUrl (aCSS, aVisitor);
    assertEquals (0, aVisitor.getCount ());
  }
}
