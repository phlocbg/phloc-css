package com.phloc.css.decl.visit;

import com.phloc.commons.annotations.OverrideOnDemand;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * Default implementation of the {@link ICSSUrlVisitor} interface which does
 * nothing. Use as the base class for your implementation.
 * 
 * @author philip
 */
public class DefaultCSSUrlVisitor implements ICSSUrlVisitor
{
  public DefaultCSSUrlVisitor ()
  {}

  @OverrideOnDemand
  public void begin ()
  {}

  @OverrideOnDemand
  public void onImport (final CSSImportRule aImportRule)
  {}

  @OverrideOnDemand
  public void onUrlDeclaration (final ICSSTopLevelRule aTopLevelRule,
                                final CSSDeclaration aDeclaration,
                                final CSSExpressionMemberTermSimple aExprTerm)
  {}

  @OverrideOnDemand
  public void end ()
  {}
}
