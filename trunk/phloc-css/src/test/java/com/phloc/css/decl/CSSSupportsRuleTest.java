/**
 * Copyright (C) 2006-2014 phloc systems
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
package com.phloc.css.decl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.mock.PhlocTestUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.reader.CSSReader;

/**
 * Test class for {@link CSSSupportsRule}.
 * 
 * @author Philip Helger
 */
public final class CSSSupportsRuleTest
{
  @Nonnull
  private static CSSSupportsRule _parse (@Nonnull final String sCSS)
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sCSS, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST);
    assertNotNull (sCSS, aCSS);
    assertTrue (aCSS.hasSupportsRules ());
    assertEquals (1, aCSS.getSupportsRuleCount ());
    final CSSSupportsRule ret = aCSS.getAllSupportsRules ().get (0);
    assertNotNull (ret);
    return ret;
  }

  @Test
  public void testRead1 ()
  {
    CSSSupportsRule aSR;
    aSR = _parse ("@supports(column-count: 1) {}");
    assertTrue (aSR.hasSupportConditionMembers ());
    assertEquals (1, aSR.getSupportsConditionMemberCount ());
    assertNotNull (aSR.getSupportsConditionMemberAtIndex (0));
    assertEquals (1, aSR.getAllSupportConditionMembers ().size ());
    assertFalse (aSR.hasRules ());
    assertEquals (0, aSR.getRuleCount ());
    assertTrue (aSR.getAllRules ().isEmpty ());
    final ICSSSupportsConditionMember aMember0 = aSR.getSupportsConditionMemberAtIndex (0);
    assertTrue (aMember0 instanceof CSSSupportsConditionDeclaration);
    final CSSSupportsConditionDeclaration aDecl0 = (CSSSupportsConditionDeclaration) aMember0;
    assertNotNull (aDecl0.getDeclaration ());
    assertEquals ("column-count", aDecl0.getDeclaration ().getProperty ());
    final ICSSExpressionMember aDecl0Member0 = aDecl0.getDeclaration ().getExpression ().getMemberAtIndex (0);
    assertTrue (aDecl0Member0 instanceof CSSExpressionMemberTermSimple);
    assertEquals ("1", ((CSSExpressionMemberTermSimple) aDecl0Member0).getValue ());

    // Create the same rule by application
    final CSSSupportsRule aCreated = new CSSSupportsRule ();
    aCreated.addSupportConditionMember (new CSSSupportsConditionDeclaration (new CSSDeclaration ("column-count",
                                                                                                 CSSExpression.createNumber (1))));
    PhlocTestUtils.testDefaultImplementationWithEqualContentObject (aSR, aCreated);
  }

  @Test
  public void testRead2 ()
  {
    CSSSupportsRule aSR;
    aSR = _parse ("@supports (column-count: 1) and (not (color: blue)) { div { color:red; }}");
    assertTrue (aSR.hasSupportConditionMembers ());
    assertEquals (3, aSR.getSupportsConditionMemberCount ());
    assertNotNull (aSR.getSupportsConditionMemberAtIndex (0));
    assertNotNull (aSR.getSupportsConditionMemberAtIndex (1));
    assertNotNull (aSR.getSupportsConditionMemberAtIndex (2));
    assertEquals (3, aSR.getAllSupportConditionMembers ().size ());
    assertTrue (aSR.hasRules ());
    assertEquals (1, aSR.getRuleCount ());
    assertNotNull (aSR.getRule (0));

    final ICSSSupportsConditionMember aMember0 = aSR.getSupportsConditionMemberAtIndex (0);
    assertTrue (aMember0 instanceof CSSSupportsConditionDeclaration);
    final CSSSupportsConditionDeclaration aDecl0 = (CSSSupportsConditionDeclaration) aMember0;
    assertNotNull (aDecl0.getDeclaration ());
    assertEquals ("column-count", aDecl0.getDeclaration ().getProperty ());
    final ICSSExpressionMember aDecl0Member0 = aDecl0.getDeclaration ().getExpression ().getMemberAtIndex (0);
    assertTrue (aDecl0Member0 instanceof CSSExpressionMemberTermSimple);
    assertEquals ("1", ((CSSExpressionMemberTermSimple) aDecl0Member0).getValue ());

    final ICSSSupportsConditionMember aMember1 = aSR.getSupportsConditionMemberAtIndex (1);
    assertTrue (aMember1 instanceof ECSSSupportsConditionOperator);
    assertSame (aMember1, ECSSSupportsConditionOperator.AND);

    final ICSSSupportsConditionMember aMember2 = aSR.getSupportsConditionMemberAtIndex (2);
    assertTrue (aMember2 instanceof CSSSupportsConditionNested);
    final ICSSSupportsConditionMember aMember2M0 = ((CSSSupportsConditionNested) aMember2).getMemberAtIndex (0);
    assertTrue (aMember2M0 instanceof CSSSupportsConditionNegation);
    final CSSSupportsConditionDeclaration aDecl2 = (CSSSupportsConditionDeclaration) ((CSSSupportsConditionNegation) aMember2M0).getSupportsMember ();
    assertNotNull (aDecl2.getDeclaration ());
    assertEquals ("color", aDecl2.getDeclaration ().getProperty ());
    final ICSSExpressionMember aDecl2Member0 = aDecl2.getDeclaration ().getExpression ().getMemberAtIndex (0);
    assertTrue (aDecl2Member0 instanceof CSSExpressionMemberTermSimple);
    assertEquals ("blue", ((CSSExpressionMemberTermSimple) aDecl2Member0).getValue ());

    assertTrue (aSR.getRule (0) instanceof CSSStyleRule);
    assertEquals ("div", ((CSSSelectorSimpleMember) ((CSSStyleRule) aSR.getRule (0)).getSelectorAtIndex (0)
                                                                                    .getMemberAtIndex (0)).getValue ());

    // Create the same rule by application
    final CSSSupportsRule aCreated = new CSSSupportsRule ();
    aCreated.addSupportConditionMember (new CSSSupportsConditionDeclaration (new CSSDeclaration ("column-count",
                                                                                                 CSSExpression.createNumber (1))));
    aCreated.addSupportConditionMember (ECSSSupportsConditionOperator.AND);
    aCreated.addSupportConditionMember (new CSSSupportsConditionNested ().addMember (new CSSSupportsConditionNegation (new CSSSupportsConditionDeclaration (new CSSDeclaration ("color",
                                                                                                                                                                                CSSExpression.createSimple ("blue"))))));
    aCreated.addRule (new CSSStyleRule ().addSelector (new CSSSelectorSimpleMember ("div"))
                                         .addDeclaration (new CSSDeclaration ("color",
                                                                              CSSExpression.createSimple ("red"))));
    PhlocTestUtils.testDefaultImplementationWithEqualContentObject (aSR, aCreated);
  }
}
