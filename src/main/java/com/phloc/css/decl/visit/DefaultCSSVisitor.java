package com.phloc.css.decl.visit;

import com.phloc.commons.annotations.OverrideOnDemand;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;

/**
 * Default implementation of the {@link ICSSVisitor} interface. Use as base
 * class for your own implementations since this class does nothin on its own.
 * 
 * @author philip
 */
public class DefaultCSSVisitor implements ICSSVisitor
{
  public DefaultCSSVisitor ()
  {}

  @OverrideOnDemand
  public void begin ()
  {}

  @OverrideOnDemand
  public void onImport (final CSSImportRule aImportRule)
  {}

  @OverrideOnDemand
  public void onBeginStyleRule (final CSSStyleRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onStyleRuleSelector (final CSSSelector aSelector)
  {}

  @OverrideOnDemand
  public void onStyleRuleDeclaration (final CSSDeclaration aDeclaration)
  {}

  @OverrideOnDemand
  public void onEndStyleRule (final CSSStyleRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onBeginFontFaceRule (final CSSFontFaceRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onEndFontFaceRule (final CSSFontFaceRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onBeginMediaRule (final CSSMediaRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onEndMediaRule (final CSSMediaRule aStyleRule)
  {}

  @OverrideOnDemand
  public void end ()
  {}
}
