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

import static org.junit.Assert.assertNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.ICSSTopLevelRule;

final class MockCountingUrlVisitor extends DefaultCSSUrlVisitor
{
  private int m_nCount = 0;

  public MockCountingUrlVisitor ()
  {}

  @Override
  public void onImport (final CSSImportRule aImportRule)
  {
    assertNotNull (aImportRule.getLocation ());
    m_nCount++;
  }

  @Override
  public void onUrlDeclaration (@Nullable final ICSSTopLevelRule aTopLevelRule,
                                @Nonnull final CSSDeclaration aDeclaration,
                                @Nonnull final CSSExpressionMemberTermURI aURITerm)
  {
    assertNotNull (aURITerm.getURI ());
    m_nCount++;
  }

  public int getCount ()
  {
    return m_nCount;
  }
}
