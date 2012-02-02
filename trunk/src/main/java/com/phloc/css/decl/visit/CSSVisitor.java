package com.phloc.css.decl.visit;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.css.CCSS;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSExpressionMember;
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

  private static void _visitStyleRule (@Nonnull final ICSSVisitor aVisitor,
                                       @Nonnull final ICSSTopLevelRule aTopLevelRule)
  {
    // Handle a single style rule
    final CSSStyleRule aStyleRule = (CSSStyleRule) aTopLevelRule;
    aVisitor.onBeginStyleRule (aStyleRule);
    try
    {
      // for all selectors
      for (final CSSSelector aSelector : aStyleRule.getAllSelectors ())
        aVisitor.onStyleRuleSelector (aSelector);

      // for all declarations
      for (final CSSDeclaration aDeclaration : aStyleRule.getAllDeclarations ())
        aVisitor.onStyleRuleDeclaration (aDeclaration);
    }
    finally
    {
      aVisitor.onEndStyleRule (aStyleRule);
    }
  }

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
          _visitStyleRule (aVisitor, aTopLevelRule);
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
                aVisitor.onStyleRuleDeclaration (aDeclaration);
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
    visitCSS (aCSS, new DefaultCSSVisitor ()
    {
      private ICSSTopLevelRule m_aTopLevelRule;

      @Override
      public void begin ()
      {
        aVisitor.begin ();
      }

      @Override
      public void onImport (final CSSImportRule aImportRule)
      {
        aVisitor.onImport (aImportRule);
      }

      @Override
      public void onBeginStyleRule (final CSSStyleRule aStyleRule)
      {
        m_aTopLevelRule = aStyleRule;
      }

      @Override
      public void onStyleRuleDeclaration (final CSSDeclaration aDeclaration)
      {
        final CSSExpression aExpr = aDeclaration.getExpression ();
        for (final ICSSExpressionMember aMember : aExpr.getAllMembers ())
          if (aMember instanceof CSSExpressionMemberTermSimple)
          {
            final CSSExpressionMemberTermSimple aExprTerm = (CSSExpressionMemberTermSimple) aMember;
            if (CCSS.isURLValue (aExprTerm.getValue ()))
              aVisitor.onUrlDeclaration (m_aTopLevelRule, aDeclaration, aExprTerm);
          }
      }

      @Override
      public void onBeginFontFaceRule (final CSSFontFaceRule aFontFaceRule)
      {
        m_aTopLevelRule = aFontFaceRule;
      }

      @Override
      public void onBeginMediaRule (final CSSMediaRule aMediaRule)
      {
        m_aTopLevelRule = aMediaRule;
      }

      @Override
      public void end ()
      {
        aVisitor.end ();
      }
    });
  }
}
