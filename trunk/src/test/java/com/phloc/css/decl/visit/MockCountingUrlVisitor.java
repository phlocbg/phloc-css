package com.phloc.css.decl.visit;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Nonnull;

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
  public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
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