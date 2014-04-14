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
package com.phloc.css.decl.visit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.Test;

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSDeclarationList;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.reader.CSSReaderDeclarationList;

/**
 * Test class for class {@link CSSVisitor}.
 * 
 * @author Philip Helger
 */
public final class CSSVisitorDeclarationListTest
{
  @Test
  public void testVisitConstantCSS ()
  {
    // CSS 1
    CSSDeclarationList aCSS = CSSReaderDeclarationList.readFromString ("color:red;", ECSSVersion.CSS30);
    assertNotNull (aCSS);
    MockCountingUrlVisitor aVisitor = new MockCountingUrlVisitor ();
    CSSVisitor.visitAllDeclarationUrls (aCSS, aVisitor);
    CSSVisitor.visitAllDeclarationUrls (aCSS, new DefaultCSSUrlVisitor ());
    CSSVisitor.visitAllDeclarations (aCSS, new DefaultCSSVisitor ());
    assertEquals (0, aVisitor.getCount ());

    // CSS 2
    aCSS = CSSReaderDeclarationList.readFromString ("background:url(a.gif)", ECSSVersion.CSS30);
    assertNotNull (aCSS);
    aVisitor = new MockCountingUrlVisitor ();
    CSSVisitor.visitAllDeclarationUrls (aCSS, aVisitor);
    CSSVisitor.visitAllDeclarationUrls (aCSS, new DefaultCSSUrlVisitor ());
    CSSVisitor.visitAllDeclarations (aCSS, new DefaultCSSVisitor ());
    assertEquals (1, aVisitor.getCount ());

    // CSS 3
    aCSS = CSSReaderDeclarationList.readFromString ("background:url(a.gif);background:url(b.gif);", ECSSVersion.CSS30);
    assertNotNull (aCSS);
    aVisitor = new MockCountingUrlVisitor ();
    CSSVisitor.visitAllDeclarationUrls (aCSS, aVisitor);
    CSSVisitor.visitAllDeclarationUrls (aCSS, new DefaultCSSUrlVisitor ());
    CSSVisitor.visitAllDeclarations (aCSS, new DefaultCSSVisitor ());
    assertEquals (2, aVisitor.getCount ());
  }

  @Test
  public void testModifyingCSSUrlVisitor ()
  {
    final CSSDeclarationList aCSS = CSSReaderDeclarationList.readFromString ("background:url(a.gif);background:url(b.gif);",
                                                                             ECSSVersion.CSS30);
    assertNotNull (aCSS);
    final MockModifyingCSSUrlVisitor aVisitor2 = new MockModifyingCSSUrlVisitor ();
    CSSVisitor.visitAllDeclarationUrls (aCSS, aVisitor2);
    CSSVisitor.visitAllDeclarationUrls (aCSS, new DefaultCSSUrlVisitor ()
    {
      @Override
      public void onImport (@Nonnull final CSSImportRule aImportRule)
      {
        assertTrue (aImportRule.getLocationString ().endsWith (".modified"));
      }

      @Override
      public void onUrlDeclaration (@Nullable final ICSSTopLevelRule aTopLevelRule,
                                    @Nonnull final CSSDeclaration aDeclaration,
                                    @Nonnull final CSSExpressionMemberTermURI aURITerm)
      {
        assertTrue (aURITerm.getURIString ().endsWith (".modified"));
      }
    });
  }
}
