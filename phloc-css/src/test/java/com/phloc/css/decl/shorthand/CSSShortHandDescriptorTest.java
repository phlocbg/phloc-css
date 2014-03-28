package com.phloc.css.decl.shorthand;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.property.ECSSProperty;

/**
 * Test class for class {@link CSSShortHandDescriptor}.
 * 
 * @author Philip Helger
 */
public class CSSShortHandDescriptorTest
{
  @Test
  public void testBasic ()
  {
    assertTrue (CSSShortHandRegistry.isShortHandProperty (ECSSProperty.MARGIN));
    assertFalse (CSSShortHandRegistry.isShortHandProperty (ECSSProperty.MARGIN_TOP));
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.BORDER);
    assertNotNull (aSHD);
    final CSSExpression aExpr = new CSSExpression ();
    aExpr.addMember (new CSSExpressionMemberTermSimple ("1px"));
    aExpr.addMember (new CSSExpressionMemberTermSimple ("dotted"));
    aExpr.addMember (new CSSExpressionMemberTermSimple ("red"));
    final CSSDeclaration aDecl = new CSSDeclaration (ECSSProperty.BORDER.getName (), aExpr);
    System.out.println (aSHD.getSplitIntoPieces (aDecl));
  }
}
