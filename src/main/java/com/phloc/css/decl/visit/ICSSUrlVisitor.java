package com.phloc.css.decl.visit;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * Interface for visiting all URLs in a CSS document.
 * 
 * @author philip
 */
public interface ICSSUrlVisitor
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

  /**
   * Called on a CSS declaration value that contains an URL.
   * 
   * @param aTopLevelRule
   *          Top level rule of the URL
   * @param aDeclaration
   *          Declaration of the URL
   * @param aExprTerm
   *          The expression member that contains the value.
   */
  void onUrlDeclaration (ICSSTopLevelRule aTopLevelRule,
                         CSSDeclaration aDeclaration,
                         CSSExpressionMemberTermSimple aExprTerm);

  /**
   * After visiting is done.
   */
  void end ();
}
