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
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * Default implementation of the {@link ICSSUrlVisitor} interface which does
 * nothing. Use as the base class for your implementation.
 * 
 * @author Philip Helger
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

  @OverrideOnDemand
  public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                @Nonnull final CSSDeclaration aDeclaration,
                                @Nonnull final CSSExpressionMemberTermURI aURITerm)
  {}

  @OverrideOnDemand
  public void end ()
  {}
}
