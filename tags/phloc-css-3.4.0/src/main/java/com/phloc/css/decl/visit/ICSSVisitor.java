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
import com.phloc.css.decl.CSSViewportRule;

/**
 * Interface for visiting different elements of a CSS domain object.
 * 
 * @author philip
 * @see DefaultCSSVisitor
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
   *        Other imported CSS
   */
  void onImport (@Nonnull CSSImportRule aImportRule);

  /**
   * Called on CSS namespace statement
   * 
   * @param aNamespaceRule
   *        The namespace rule
   */
  void onNamespace (@Nonnull CSSNamespaceRule aNamespaceRule);

  // style rules:
  void onBeginStyleRule (@Nonnull CSSStyleRule aStyleRule);

  void onStyleRuleSelector (@Nonnull CSSSelector aSelector);

  void onDeclaration (@Nonnull CSSDeclaration aDeclaration);

  void onEndStyleRule (@Nonnull CSSStyleRule aStyleRule);

  // page rules:
  // contained declarations are handled by onDeclaration
  void onBeginPageRule (@Nonnull CSSPageRule aPageRule);

  void onEndPageRule (@Nonnull CSSPageRule aPageRule);

  // font face rules:
  // contained declarations are handled by onDeclaration
  void onBeginFontFaceRule (@Nonnull CSSFontFaceRule aFontFaceRule);

  void onEndFontFaceRule (@Nonnull CSSFontFaceRule aFontFaceRule);

  // media rules:
  // contained style rules are handled by the on*StyleRule* methods
  void onBeginMediaRule (@Nonnull CSSMediaRule aMediaRule);

  void onEndMediaRule (@Nonnull CSSMediaRule aMediaRule);

  // keyframes rules:
  void onBeginKeyframesRule (@Nonnull CSSKeyframesRule aKeyframesRule);

  // contained declarations are handled by onDeclaration
  void onBeginKeyframesBlock (@Nonnull CSSKeyframesBlock aKeyframesBlock);

  void onEndKeyframesBlock (@Nonnull CSSKeyframesBlock aKeyframesBlock);

  void onEndKeyframesRule (@Nonnull CSSKeyframesRule aKeyframesRule);

  // viewport rules
  void onBeginViewportRule (@Nonnull CSSViewportRule aViewportRule);

  void onEndViewportRule (@Nonnull CSSViewportRule aViewportRule);

  /**
   * After visiting is done.
   */
  void end ();
}
