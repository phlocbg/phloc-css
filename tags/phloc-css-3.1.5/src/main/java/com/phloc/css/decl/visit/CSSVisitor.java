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
import com.phloc.css.decl.CSSKeyframesBlock;
import com.phloc.css.decl.CSSKeyframesRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSPageRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.decl.IHasCSSDeclarations;

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

  public static void visitAllDeclarations (@Nonnull final IHasCSSDeclarations aHasDeclarations,
                                           @Nonnull final ICSSVisitor aVisitor)
  {
    // for all declarations
    for (final CSSDeclaration aDeclaration : aHasDeclarations.getAllDeclarations ())
      aVisitor.onDeclaration (aDeclaration);
  }

  public static void visitStyleRule (@Nonnull final CSSStyleRule aStyleRule, @Nonnull final ICSSVisitor aVisitor)
  {
    aVisitor.onBeginStyleRule (aStyleRule);
    try
    {
      // for all selectors
      for (final CSSSelector aSelector : aStyleRule.getAllSelectors ())
        aVisitor.onStyleRuleSelector (aSelector);

      // for all declarations
      visitAllDeclarations (aStyleRule, aVisitor);
    }
    finally
    {
      aVisitor.onEndStyleRule (aStyleRule);
    }
  }

  public static void visitPageRule (@Nonnull final CSSPageRule aPageRule, @Nonnull final ICSSVisitor aVisitor)
  {
    aVisitor.onBeginPageRule (aPageRule);
    try
    {
      // for all declarations
      visitAllDeclarations (aPageRule, aVisitor);
    }
    finally
    {
      aVisitor.onEndPageRule (aPageRule);
    }
  }

  public static void visitFontFaceRule (@Nonnull final CSSFontFaceRule aFontFaceRule,
                                        @Nonnull final ICSSVisitor aVisitor)
  {
    aVisitor.onBeginFontFaceRule (aFontFaceRule);
    try
    {
      // for all declarations
      visitAllDeclarations (aFontFaceRule, aVisitor);
    }
    finally
    {
      aVisitor.onEndFontFaceRule (aFontFaceRule);
    }
  }

  public static void visitMediaRule (@Nonnull final CSSMediaRule aMediaRule, @Nonnull final ICSSVisitor aVisitor)
  {
    aVisitor.onBeginMediaRule (aMediaRule);
    try
    {
      // for all rules
      for (final ICSSTopLevelRule aRule : aMediaRule.getAllRules ())
        visitTopLevelRule (aRule, aVisitor);
    }
    finally
    {
      aVisitor.onEndMediaRule (aMediaRule);
    }
  }

  public static void visitKeyframesRule (@Nonnull final CSSKeyframesRule aKeyframesRule,
                                         @Nonnull final ICSSVisitor aVisitor)
  {
    aVisitor.onBeginKeyframesRule (aKeyframesRule);
    try
    {
      // for all keyframes blocks
      for (final CSSKeyframesBlock aBlock : aKeyframesRule.getAllBlocks ())
      {
        aVisitor.onBeginKeyframesBlock (aBlock);
        try
        {
          // for all declarations
          visitAllDeclarations (aBlock, aVisitor);
        }
        finally
        {
          aVisitor.onEndKeyframesBlock (aBlock);
        }
      }
    }
    finally
    {
      aVisitor.onEndKeyframesRule (aKeyframesRule);
    }
  }

  public static void visitTopLevelRule (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                        @Nonnull final ICSSVisitor aVisitor)
  {
    if (aTopLevelRule instanceof CSSStyleRule)
    {
      visitStyleRule ((CSSStyleRule) aTopLevelRule, aVisitor);
    }
    else
      if (aTopLevelRule instanceof CSSPageRule)
      {
        visitPageRule ((CSSPageRule) aTopLevelRule, aVisitor);
      }
      else
        if (aTopLevelRule instanceof CSSFontFaceRule)
        {
          visitFontFaceRule ((CSSFontFaceRule) aTopLevelRule, aVisitor);
        }
        else
          if (aTopLevelRule instanceof CSSMediaRule)
          {
            visitMediaRule ((CSSMediaRule) aTopLevelRule, aVisitor);
          }
          else
            if (aTopLevelRule instanceof CSSKeyframesRule)
            {
              visitKeyframesRule ((CSSKeyframesRule) aTopLevelRule, aVisitor);
            }
            else
              throw new IllegalStateException ("Top level rule " + aTopLevelRule + " is unsupported!");
  }

  /**
   * Visit CSS elements in the order of their declaration.
   * 
   * @param aCSS
   *        The CSS to visit. May not be <code>null</code>.
   * @param aVisitor
   *        The callback to be invoked for each element found. May not be
   *        <code>null</code>.
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
        visitTopLevelRule (aTopLevelRule, aVisitor);
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
   *        The CSS to visit.
   * @param aVisitor
   *        The callback to invoke for each found occurrence.
   */
  public static void visitCSSUrl (@Nonnull final CascadingStyleSheet aCSS, @Nonnull final ICSSUrlVisitor aVisitor)
  {
    // Visit only the URLs of a CSS with a specific CSS visitor
    visitCSS (aCSS, new CSSVisitorForUrl (aVisitor));
  }

  /**
   * Visit all items that can contain URLs in CSS files.
   * 
   * @param aCSS
   *        The CSS to visit.
   * @param aVisitor
   *        The callback to invoke for each found occurrence.
   */
  public static void visitAllDeclarationUrls (@Nonnull final IHasCSSDeclarations aCSS,
                                              @Nonnull final ICSSUrlVisitor aVisitor)
  {
    // Visit only the URLs of a CSS with a specific CSS visitor
    visitAllDeclarations (aCSS, new CSSVisitorForUrl (aVisitor));
  }
}