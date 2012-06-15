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
  public void onImport (@Nonnull final CSSImportRule aImportRule)
  {}

  /**
   * Called on a CSS declaration value that contains an URL.<br>
   * Note: for keyframes it is currently not possible to retrieve the keyframes
   * block to which the declaration belongs. <br>
   * Note: this method is deprecated, overwrite
   * {@link #onUrlDeclaration(ICSSTopLevelRule, CSSDeclaration, CSSExpressionMemberTermSimple, String)}
   * instead.
   * 
   * @param aTopLevelRule
   *          Top level rule of the URL. Never <code>null</code>.
   * @param aDeclaration
   *          Declaration of the URL. Never <code>null</code>.
   * @param aExprTerm
   *          The expression member that contains the value.
   */
  @OverrideOnDemand
  @Deprecated
  public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                @Nonnull final CSSDeclaration aDeclaration,
                                @Nonnull final CSSExpressionMemberTermSimple aExprTerm)
  {}

  @OverrideOnDemand
  public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                @Nonnull final CSSDeclaration aDeclaration,
                                @Nonnull final CSSExpressionMemberTermSimple aExprTerm,
                                @Nonnull final String sURL)
  {
    // For backward compatibility, invoke the old version by default
    onUrlDeclaration (aTopLevelRule, aDeclaration, aExprTerm);
  }

  @OverrideOnDemand
  public void end ()
  {}
}
