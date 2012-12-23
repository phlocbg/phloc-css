package com.phloc.css.decl.visit;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Nonnull;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.ICSSTopLevelRule;

final class MockUrlVisitor extends DefaultCSSUrlVisitor
{
  private final String m_sFilename;

  public MockUrlVisitor (final String sFilename)
  {
    m_sFilename = sFilename;
  }

  @Override
  public void onImport (final CSSImportRule aImportRule)
  {
    assertNotNull (m_sFilename, aImportRule.getLocation ());
  }

  @Override
  public void onUrlDeclaration (@Nonnull final ICSSTopLevelRule aTopLevelRule,
                                @Nonnull final CSSDeclaration aDeclaration,
                                @Nonnull final CSSExpressionMemberTermURI aURITerm)
  {
    assertNotNull (m_sFilename, aURITerm.getURI ());
  }
}