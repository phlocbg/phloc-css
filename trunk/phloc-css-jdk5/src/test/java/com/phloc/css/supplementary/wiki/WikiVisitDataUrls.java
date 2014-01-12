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
package com.phloc.css.supplementary.wiki;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSURI;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.decl.visit.CSSVisitor;
import com.phloc.css.decl.visit.DefaultCSSUrlVisitor;
import com.phloc.css.decl.visit.ICSSUrlVisitor;
import com.phloc.css.reader.CSSReader;
import com.phloc.css.utils.CSSDataURL;

/**
 * Example how to extract all URLs from a certain CSS file using an
 * {@link ICSSUrlVisitor} and handle them as data URLs. This example works with
 * phloc-css >= 3.5.7
 * 
 * @author Philip Helger
 */
public final class WikiVisitDataUrls
{
  public void readFromStyleAttributeWithAPI ()
  {
    final String sStyle = "@import '/folder/foobar.css';\n"
                          + "div{background:fixed url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAIAAAACUFjqAAAABGdBTUEAALGPC/xhBQAAAAlwSFlzAAALEgAACxIB0t1+/AAAAAd0SU1FB9EFBAoYMhVvMQIAAAAtSURBVHicY/z//z8DHoBH+v///yy4FDEyMjIwMDDhM3lgpaEuh7gTEzDiDxYA9HEPDF90e5YAAAAASUVORK5CYII=) !important;}\n"
                          + "span { background-image:url('/my/folder/b.gif');}";
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sStyle, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    CSSVisitor.visitCSSUrl (aCSS, new DefaultCSSUrlVisitor ()
    {
      // Called for each import
      @Override
      public void onImport (@Nonnull final CSSImportRule aImportRule)
      {
        System.out.println ("Import: " + aImportRule.getLocationString ());
      }

      // Call for URLs outside of URLs
      @Override
      public void onUrlDeclaration (@Nullable final ICSSTopLevelRule aTopLevelRule,
                                    @Nonnull final CSSDeclaration aDeclaration,
                                    @Nonnull final CSSExpressionMemberTermURI aURITerm)
      {
        final CSSURI aURI = aURITerm.getURI ();

        if (aURI.isDataURL ())
        {
          final CSSDataURL aDataURL = aURI.getAsDataURL ();
          System.out.println (aDeclaration.getProperty () +
                              " - references data URL with " +
                              aDataURL.getContentLength () +
                              " bytes of content");
        }
        else
          System.out.println (aDeclaration.getProperty () + " - references regular URL: " + aURI.getURI ());
      }
    });
  }
}
