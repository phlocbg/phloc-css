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
