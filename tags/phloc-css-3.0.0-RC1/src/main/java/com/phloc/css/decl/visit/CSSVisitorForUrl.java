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
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.css.CCSS;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSKeyframesRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSPageRule;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * A special {@link ICSSVisitor} that is used to extract URLs from the available
 * rules.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSVisitorForUrl extends DefaultCSSVisitor
{
  private final ICSSUrlVisitor m_aVisitor;
  private ICSSTopLevelRule m_aTopLevelRule;

  public CSSVisitorForUrl (@Nonnull final ICSSUrlVisitor aVisitor)
  {
    if (aVisitor == null)
      throw new NullPointerException ("visitor");
    m_aVisitor = aVisitor;
  }

  @Override
  public void begin ()
  {
    m_aVisitor.begin ();
  }

  @Override
  public void onImport (@Nonnull final CSSImportRule aImportRule)
  {
    m_aVisitor.onImport (aImportRule);
  }

  @Override
  public void onDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    final CSSExpression aExpr = aDeclaration.getExpression ();
    for (final ICSSExpressionMember aMember : aExpr.getAllMembers ())
      if (aMember instanceof CSSExpressionMemberTermSimple)
      {
        final CSSExpressionMemberTermSimple aExprTerm = (CSSExpressionMemberTermSimple) aMember;
        if (CCSS.isURLValue (aExprTerm.getValue ()))
        {
          // Extract the URL for sanity reasons
          final String sURL = CCSS.getURLValue (aExprTerm.getValue ());
          m_aVisitor.onUrlDeclaration (m_aTopLevelRule, aDeclaration, aExprTerm, sURL);
        }
      }
  }

  @Override
  public void onBeginStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {
    m_aTopLevelRule = aStyleRule;
  }

  @Override
  public void onEndStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {
    m_aTopLevelRule = null;
  }

  @Override
  public void onBeginPageRule (@Nonnull final CSSPageRule aPageRule)
  {
    m_aTopLevelRule = aPageRule;
  }

  @Override
  public void onEndPageRule (@Nonnull final CSSPageRule aPageRule)
  {
    m_aTopLevelRule = null;
  }

  @Override
  public void onBeginFontFaceRule (@Nonnull final CSSFontFaceRule aFontFaceRule)
  {
    m_aTopLevelRule = aFontFaceRule;
  }

  @Override
  public void onEndFontFaceRule (@Nonnull final CSSFontFaceRule aFontFaceRule)
  {
    m_aTopLevelRule = null;
  }

  @Override
  public void onBeginMediaRule (@Nonnull final CSSMediaRule aMediaRule)
  {
    m_aTopLevelRule = aMediaRule;
  }

  @Override
  public void onEndMediaRule (@Nonnull final CSSMediaRule aMediaRule)
  {
    m_aTopLevelRule = null;
  }

  @Override
  public void onBeginKeyframesRule (@Nonnull final CSSKeyframesRule aKeyframesRule)
  {
    m_aTopLevelRule = aKeyframesRule;
  }

  @Override
  public void onEndKeyframesRule (@Nonnull final CSSKeyframesRule aKeyframesRule)
  {
    m_aTopLevelRule = null;
  }

  @Override
  public void end ()
  {
    m_aVisitor.end ();
  }
}
