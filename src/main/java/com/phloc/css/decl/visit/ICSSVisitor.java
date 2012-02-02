package com.phloc.css.decl.visit;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;

/**
 * Interface for visiting different elements of a CSS domain object.
 * 
 * @author philip
 */
public interface ICSSVisitor
{
  /**
   * Before visiting starts.
   */
  void begin ();

  /**
   * Called on CSS import statement - rarely used :)
   * 
   * @param aImportRule
   *          Other imported CSS
   */
  void onImport (CSSImportRule aImportRule);

  // style rules:
  void onBeginStyleRule (CSSStyleRule aStyleRule);

  void onStyleRuleSelector (CSSSelector aSelector);

  void onStyleRuleDeclaration (CSSDeclaration aDeclaration);

  void onEndStyleRule (CSSStyleRule aStyleRule);

  // font face rules:
  // contained declarations are handled by onStyleRuleDeclaration

  void onBeginFontFaceRule (CSSFontFaceRule aFontFaceRule);

  void onEndFontFaceRule (CSSFontFaceRule aFontFaceRule);

  // media rules:
  // contained style rules are handled by the on*StyleRule* methods
  void onBeginMediaRule (CSSMediaRule aMediaRule);

  void onEndMediaRule (CSSMediaRule aMediaRule);

  /**
   * After visiting is done.
   */
  void end ();
}
