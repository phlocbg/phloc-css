/**
 * Copyright (C) 2006-2013 phloc systems
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

import com.phloc.commons.annotations.OverrideOnDemand;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSKeyframesBlock;
import com.phloc.css.decl.CSSKeyframesRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSNamespaceRule;
import com.phloc.css.decl.CSSPageRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CSSSupportsRule;
import com.phloc.css.decl.CSSViewportRule;

/**
 * Default implementation of the {@link ICSSVisitor} interface. Use as base
 * class for your own implementations since this class does nothin on its own.
 * 
 * @author Philip Helger
 */
public class DefaultCSSVisitor implements ICSSVisitor
{
  public DefaultCSSVisitor ()
  {}

  @OverrideOnDemand
  public void begin ()
  {}

  @OverrideOnDemand
  public void onImport (@Nonnull final CSSImportRule aImportRule)
  {}

  @OverrideOnDemand
  public void onNamespace (@Nonnull final CSSNamespaceRule aNamespaceRule)
  {}

  @OverrideOnDemand
  public void onDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {}

  @OverrideOnDemand
  public void onBeginStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onStyleRuleSelector (@Nonnull final CSSSelector aSelector)
  {}

  @OverrideOnDemand
  public void onEndStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {}

  @OverrideOnDemand
  public void onBeginPageRule (@Nonnull final CSSPageRule aPageRule)
  {}

  @OverrideOnDemand
  public void onEndPageRule (@Nonnull final CSSPageRule aPageRule)
  {}

  @OverrideOnDemand
  public void onBeginFontFaceRule (@Nonnull final CSSFontFaceRule aFontFaceRule)
  {}

  @OverrideOnDemand
  public void onEndFontFaceRule (@Nonnull final CSSFontFaceRule aFontFaceRule)
  {}

  @OverrideOnDemand
  public void onBeginMediaRule (@Nonnull final CSSMediaRule aMediaRule)
  {}

  @OverrideOnDemand
  public void onEndMediaRule (@Nonnull final CSSMediaRule aMediaRule)
  {}

  @OverrideOnDemand
  public void onBeginKeyframesRule (@Nonnull final CSSKeyframesRule aKeyframesRule)
  {}

  @OverrideOnDemand
  public void onBeginKeyframesBlock (@Nonnull final CSSKeyframesBlock aKeyframesBlock)
  {}

  @OverrideOnDemand
  public void onEndKeyframesBlock (@Nonnull final CSSKeyframesBlock aKeyframesBlock)
  {}

  @OverrideOnDemand
  public void onEndKeyframesRule (@Nonnull final CSSKeyframesRule aKeyframesRule)
  {}

  @OverrideOnDemand
  public void onBeginViewportRule (@Nonnull final CSSViewportRule aViewportRule)
  {}

  @OverrideOnDemand
  public void onEndViewportRule (@Nonnull final CSSViewportRule aViewportRule)
  {}

  @OverrideOnDemand
  public void onBeginSupportsRule (@Nonnull final CSSSupportsRule aSupportsRule)
  {}

  @OverrideOnDemand
  public void onEndSupportsRule (@Nonnull final CSSSupportsRule aSupportsRule)
  {}

  @OverrideOnDemand
  public void end ()
  {}
}
