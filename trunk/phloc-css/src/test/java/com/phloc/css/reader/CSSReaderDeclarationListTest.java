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
package com.phloc.css.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSDeclarationList;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.handler.DoNothingCSSParseExceptionHandler;
import com.phloc.css.handler.ICSSParseExceptionHandler;

/**
 * Test class for class {@link CSSReaderDeclarationList}
 * 
 * @author Philip Helger
 */
public final class CSSReaderDeclarationListTest
{
  private static final List <String> VALID = ContainerHelper.newList ("",
                                                                      "    ",
                                                                      ";",
                                                                      ";;",
                                                                      "  ;     ;     ;   ",
                                                                      "color:red; background:fixed;",
                                                                      "  color:red; background:fixed;  ",
                                                                      "color:red; background:fixed",
                                                                      "color:red; background:fixed !important");
  private static final List <String> INVALID = ContainerHelper.newList ("color", " color ", " color : ");

  @Test
  public void testIsValidCSS21 ()
  {
    for (final String sCSS : VALID)
      assertTrue (sCSS, CSSReaderDeclarationList.isValidCSS (sCSS, ECSSVersion.CSS21));
    for (final String sCSS : INVALID)
      assertFalse (sCSS, CSSReaderDeclarationList.isValidCSS (sCSS, ECSSVersion.CSS21));
  }

  @Test
  public void testIsValidCSS30 ()
  {
    for (final String sCSS : VALID)
      assertTrue (sCSS, CSSReaderDeclarationList.isValidCSS (sCSS, ECSSVersion.CSS30));
    for (final String sCSS : INVALID)
      assertFalse (sCSS, CSSReaderDeclarationList.isValidCSS (sCSS, ECSSVersion.CSS30));
  }

  @Test
  public void testRead21 ()
  {
    final ICSSParseExceptionHandler aHdl = DoNothingCSSParseExceptionHandler.getInstance ();
    for (final String sCSS : VALID)
    {
      final CSSDeclarationList aDL = CSSReaderDeclarationList.readFromString (sCSS, ECSSVersion.CSS21, aHdl);
      assertNotNull (aDL);
    }
    for (final String sCSS : INVALID)
      assertNull (sCSS, CSSReaderDeclarationList.readFromString (sCSS, ECSSVersion.CSS21, aHdl));
  }

  @Test
  public void testRead30 ()
  {
    final ICSSParseExceptionHandler aHdl = DoNothingCSSParseExceptionHandler.getInstance ();
    for (final String sCSS : VALID)
      assertNotNull (sCSS, CSSReaderDeclarationList.readFromString (sCSS, ECSSVersion.CSS30, aHdl));
    for (final String sCSS : INVALID)
      assertNull (sCSS, CSSReaderDeclarationList.readFromString (sCSS, ECSSVersion.CSS30, aHdl));
  }

  @Test
  public void testReadAndValidate ()
  {
    final CSSDeclarationList aList = CSSReaderDeclarationList.readFromString ("color:red; background:fixed;",
                                                                              ECSSVersion.CSS30);
    assertNotNull (aList);
    assertEquals (2, aList.getDeclarationCount ());
    CSSDeclaration aDecl = aList.getDeclarationAtIndex (0);
    assertNotNull (aDecl);
    assertEquals ("color", aDecl.getProperty ());
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    assertEquals ("red", ((CSSExpressionMemberTermSimple) aDecl.getExpression ().getMemberAtIndex (0)).getValue ());

    aDecl = aList.getDeclarationAtIndex (1);
    assertNotNull (aDecl);
    assertEquals ("background", aDecl.getProperty ());
  }
}
