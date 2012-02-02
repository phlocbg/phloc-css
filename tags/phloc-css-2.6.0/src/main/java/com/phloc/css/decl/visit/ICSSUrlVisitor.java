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
