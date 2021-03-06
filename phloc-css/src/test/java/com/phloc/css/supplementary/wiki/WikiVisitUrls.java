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
package com.phloc.css.supplementary.wiki;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.decl.visit.CSSVisitor;
import com.phloc.css.decl.visit.DefaultCSSUrlVisitor;
import com.phloc.css.decl.visit.ICSSUrlVisitor;
import com.phloc.css.reader.CSSReader;

/**
 * Example how to extract all URLs from a certain CSS file using an
 * {@link ICSSUrlVisitor}.
 *
 * @author Philip Helger
 */
public final class WikiVisitUrls
{
  public static String getSourceLocationString (@Nonnull final CSSSourceLocation aSourceLoc)
  {
    return "source location reaches from [" +
           aSourceLoc.getFirstTokenBeginLineNumber () +
           "/" +
           aSourceLoc.getFirstTokenBeginColumnNumber () +
           "] up to [" +
           aSourceLoc.getLastTokenEndLineNumber () +
           "/" +
           aSourceLoc.getLastTokenEndColumnNumber () +
           "]";
  }

  public void readFromStyleAttributeWithAPI ()
  {
    final String sStyle = "@import 'foobar.css';\n"
                          + "div{background:fixed url(a.gif) !important;}\n"
                          + "span { background-image:url('/my/folder/b.gif');}";
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sStyle, ECSSVersion.CSS30);
    CSSVisitor.visitCSSUrl (aCSS, new DefaultCSSUrlVisitor ()
    {
      // Called for each import
      @Override
      public void onImport (@Nonnull final CSSImportRule aImportRule)
      {
        System.out.println ("Import: " +
                            aImportRule.getLocationString () +
                            " - " +
                            getSourceLocationString (aImportRule.getSourceLocation ()));
      }

      // Call for URLs outside of URLs
      @Override
      public void onUrlDeclaration (@Nullable final ICSSTopLevelRule aTopLevelRule,
                                    @Nonnull final CSSDeclaration aDeclaration,
                                    @Nonnull final CSSExpressionMemberTermURI aURITerm)
      {
        System.out.println (aDeclaration.getProperty () +
                            " - references: " +
                            aURITerm.getURIString () +
                            " - " +
                            getSourceLocationString (aURITerm.getSourceLocation ()));
      }
    });
  }
}
