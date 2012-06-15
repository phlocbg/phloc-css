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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.handler.CSSHandler;

/**
 * Test class for class {@link CSSVisitor}.
 * 
 * @author philip
 */
public final class CSSVisitorTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSVisitorTest.class);

  private static final class SysOutVisitor extends DefaultCSSUrlVisitor
  {
    private final String m_sFilename;

    public SysOutVisitor (final String sFilename)
    {
      m_sFilename = sFilename;
    }

    @Override
    public void onImport (final CSSImportRule aImportRule)
    {
      assertTrue (m_sFilename, StringHelper.hasText (aImportRule.getLocation ()));
    }

    @Override
    public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                  @Nonnull final CSSDeclaration aDeclaration,
                                  @Nonnull final CSSExpressionMemberTermSimple aExprTerm,
                                  @Nonnull final String sURL)
    {
      assertTrue (m_sFilename, StringHelper.hasText (sURL));
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
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                  CCharset.CHARSET_UTF_8_OBJ,
                                                                  ECSSVersion.CSS21);
      assertNotNull (sKey, aCSS);
      CSSVisitor.visitCSSUrl (aCSS, new SysOutVisitor (sKey));
    }
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/handler30"),
                                                                FilenameFilterFactory.getEndsWithFilter (".css")))
    {
      final String sKey = aFile.getAbsolutePath ();
      if (false)
        s_aLogger.info (sKey);
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile),
                                                                  CCharset.CHARSET_UTF_8_OBJ,
                                                                  ECSSVersion.CSS30);
      assertNotNull (sKey, aCSS);
      CSSVisitor.visitCSSUrl (aCSS, new SysOutVisitor (sKey));
    }
  }
}
