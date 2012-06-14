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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * This class is used to walk a CSS domain object and call the respective
 * {@link ICSSVisitor} and {@link ICSSUrlVisitor} interface methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSVisitor
{
  private CSSVisitor ()
  {}

  private static void _visitStyleRule (@Nonnull final ICSSVisitor aVisitor, @Nonnull final CSSStyleRule aStyleRule)
  {
    aVisitor.onBeginStyleRule (aStyleRule);
    try
    {
      // for all selectors
      for (final CSSSelector aSelector : aStyleRule.getAllSelectors ())
        aVisitor.onStyleRuleSelector (aSelector);

      // for all declarations
      for (final CSSDeclaration aDeclaration : aStyleRule.getAllDeclarations ())
        aVisitor.onDeclaration (aDeclaration);
    }
    finally
    {
      aVisitor.onEndStyleRule (aStyleRule);
    }
  }

  /**
   * Visit CSS elements in the order of their declaration.
   * 
   * @param aCSS
   *          The CSS to visit. May not be <code>null</code>.
   * @param aVisitor
   *          The callback to be invoked for each element found. May not be
   *          <code>null</code>.
   */
  public static void visitCSS (@Nonnull final CascadingStyleSheet aCSS, @Nonnull final ICSSVisitor aVisitor)
  {
    if (aCSS == null)
      throw new NullPointerException ("CSS");
    if (aVisitor == null)
      throw new NullPointerException ("visitor");

    aVisitor.begin ();
    try
    {
      // for all imports
      for (final CSSImportRule aImportRule : aCSS.getAllImportRules ())
        aVisitor.onImport (aImportRule);

      // for all other top level rules
      for (final ICSSTopLevelRule aTopLevelRule : aCSS.getAllRules ())
      {
        if (aTopLevelRule instanceof CSSStyleRule)
        {
          _visitStyleRule (aVisitor, (CSSStyleRule) aTopLevelRule);
        }
        else
          if (aTopLevelRule instanceof CSSFontFaceRule)
          {
            final CSSFontFaceRule aFontFaceRule = (CSSFontFaceRule) aTopLevelRule;
            aVisitor.onBeginFontFaceRule (aFontFaceRule);
            try
            {
              // for all declarations
              for (final CSSDeclaration aDeclaration : aFontFaceRule.getAllDeclarations ())
                aVisitor.onDeclaration (aDeclaration);
            }
            finally
            {
              aVisitor.onEndFontFaceRule (aFontFaceRule);
            }
          }
          else
            if (aTopLevelRule instanceof CSSMediaRule)
            {
              final CSSMediaRule aMediaRule = (CSSMediaRule) aTopLevelRule;
              aVisitor.onBeginMediaRule (aMediaRule);
              try
              {
                // for all declarations
                for (final CSSStyleRule aStyleRule : aMediaRule.getAllStyleRules ())
                  _visitStyleRule (aVisitor, aStyleRule);
              }
              finally
              {
                aVisitor.onEndMediaRule (aMediaRule);
              }
            }
            else
              throw new IllegalStateException ("Top level rule " + aTopLevelRule + " is unsupported!");
      }
    }
    finally
    {
      aVisitor.end ();
    }
  }

  /**
   * Visit all items that can contain URLs in CSS files.
   * 
   * @param aCSS
   *          The CSS to visit.
   * @param aVisitor
   *          The callback to invoke for each found occurrence.
   */
  public static void visitCSSUrl (@Nonnull final CascadingStyleSheet aCSS, @Nonnull final ICSSUrlVisitor aVisitor)
  {
    // Visit only the URLs of a CSS with a specific CSS visitor
    visitCSS (aCSS, new CSSVisitorForUrl (aVisitor));
  }
}
