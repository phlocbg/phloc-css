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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.visit.CSSVisitor;
import com.phloc.css.decl.visit.DefaultCSSVisitor;
import com.phloc.css.decl.visit.ICSSVisitor;
import com.phloc.css.reader.CSSReader;
import com.phloc.css.writer.CSSWriterSettings;

/**
 * Example how to extract all selectors from a certain CSS file using an
 * {@link ICSSVisitor}.
 * 
 * @author Philip Helger
 */
public final class WikiVisitSelectors
{
  public void readAllSelectors ()
  {
    final String sStyle = "blockquote p,\r\n"
                          + "blockquote ul,\r\n"
                          + "blockquote ol {\r\n"
                          + "  line-height:normal;\r\n"
                          + "  font-style:italic;\r\n"
                          + "}\r\n"
                          + "\r\n"
                          + "a { color:#FFEA6F; }\r\n"
                          + "\r\n"
                          + "a:hover { text-decoration:none; }\r\n"
                          + "\r\n"
                          + "img { border:none; }";
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sStyle, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    final List <String> aAllSelectors = new ArrayList <String> ();
    CSSVisitor.visitCSS (aCSS, new DefaultCSSVisitor ()
    {
      @Override
      public void onStyleRuleSelector (@Nonnull final CSSSelector aSelector)
      {
        aAllSelectors.add (aSelector.getAsCSSString (new CSSWriterSettings (ECSSVersion.CSS30), 0));
      }
    });
    System.out.println (aAllSelectors);
  }
}
