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
package com.phloc.css.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.charset.EUnicodeBOM;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.io.streamprovider.ByteArrayInputStreamProvider;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.reader.errorhandler.CollectingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

/**
 * Test reading CSS 3.0 stuff
 * 
 * @author Philip Helger
 */
public final class CSSReader30Test extends AbstractFuncTestCSSReader
{
  public CSSReader30Test ()
  {
    super (ECSSVersion.CSS30, CCharset.CHARSET_UTF_8_OBJ, false);
  }

  @Test
  public void testReadAll30Good ()
  {
    testReadGood ("src/test/resources/testfiles/css30/good");
  }

  @Test
  public void testReadAll30BadButSucceeding ()
  {
    testReadGood ("src/test/resources/testfiles/css30/bad_but_succeeding");
  }

  @Test
  public void testReadAll30Bad ()
  {
    testReadBad ("src/test/resources/testfiles/css30/bad");
  }

  @Test
  public void testReadAll30GoodButFailing ()
  {
    testReadBad ("src/test/resources/testfiles/css30/good_but_failing");
  }

  @Test
  public void testReadAll30BadBadButRecoverable ()
  {
    testReadBadButRecoverable ("src/test/resources/testfiles/css30/bad_but_recoverable");
  }

  @Test
  public void testReadSpecialGood ()
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/good/artificial/test-expression.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
    assertNotNull (aCSS);

    final String sCSS = new CSSWriter (eVersion, false).getCSSAsString (aCSS);
    m_aLogger.info (sCSS);
  }

  @Test
  public void testReadExpressions ()
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final CSSWriterSettings aCSSWS = new CSSWriterSettings (eVersion, false);
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/good/artificial/test-expression.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
    assertNotNull (aCSS);
    assertEquals (1, aCSS.getRuleCount ());
    assertEquals (1, aCSS.getStyleRuleCount ());
    final CSSStyleRule aSR = aCSS.getStyleRuleAtIndex (0);
    assertEquals ("div", aSR.getSelectorsAsCSSString (aCSSWS, 0));
    assertEquals (23, aSR.getDeclarationCount ());
    int i = 0;
    for (final CSSDeclaration aDecl : aSR.getAllDeclarations ())
    {
      final String sExpectedName = Character.toString ((char) ('a' + i));
      assertEquals (sExpectedName, aDecl.getProperty ());
      ++i;
    }

    CSSDeclaration aDecl;
    ICSSExpressionMember aMember;

    // a: -5
    aDecl = aSR.getDeclarationOfPropertyName ("a");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("-5", aMember.getAsCSSString (aCSSWS, 0));

    // b: +5
    aDecl = aSR.getDeclarationOfPropertyName ("b");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("+5", aMember.getAsCSSString (aCSSWS, 0));

    // c: 5
    aDecl = aSR.getDeclarationOfPropertyName ("c");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("5", aMember.getAsCSSString (aCSSWS, 0));

    // d: -5.12
    aDecl = aSR.getDeclarationOfPropertyName ("d");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("-5.12", aMember.getAsCSSString (aCSSWS, 0));

    // e: +5.12
    aDecl = aSR.getDeclarationOfPropertyName ("e");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("+5.12", aMember.getAsCSSString (aCSSWS, 0));

    // f: 5.12
    aDecl = aSR.getDeclarationOfPropertyName ("f");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("5.12", aMember.getAsCSSString (aCSSWS, 0));

    // g: -5.12%
    aDecl = aSR.getDeclarationOfPropertyName ("g");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("-5.12%", aMember.getAsCSSString (aCSSWS, 0));

    // h: +5.12%
    aDecl = aSR.getDeclarationOfPropertyName ("h");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("+5.12%", aMember.getAsCSSString (aCSSWS, 0));

    // i: 5.12%
    aDecl = aSR.getDeclarationOfPropertyName ("i");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("5.12%", aMember.getAsCSSString (aCSSWS, 0));

    // j: -5px
    aDecl = aSR.getDeclarationOfPropertyName ("j");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("-5px", aMember.getAsCSSString (aCSSWS, 0));

    // k: +5px
    aDecl = aSR.getDeclarationOfPropertyName ("k");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("+5px", aMember.getAsCSSString (aCSSWS, 0));

    // l: 5px
    aDecl = aSR.getDeclarationOfPropertyName ("l");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("5px", aMember.getAsCSSString (aCSSWS, 0));

    // m: 'string1'
    aDecl = aSR.getDeclarationOfPropertyName ("m");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("'string1'", aMember.getAsCSSString (aCSSWS, 0));

    // n: "string2"
    aDecl = aSR.getDeclarationOfPropertyName ("n");
    assertNotNull (aDecl);
    assertEquals (1, aDecl.getExpression ().getMemberCount ());
    aMember = aDecl.getExpression ().getMemberAtIndex (0);
    assertTrue (aMember instanceof CSSExpressionMemberTermSimple);
    assertEquals ("\"string2\"", aMember.getAsCSSString (aCSSWS, 0));

    // Write result
    final String sCSS = new CSSWriter (aCSSWS).getCSSAsString (aCSS);
    m_aLogger.info (sCSS);
  }

  @Test
  public void testReadSpecialBadButRecoverable ()
  {
    final CollectingCSSParseErrorHandler aErrors = new CollectingCSSParseErrorHandler (new LoggingCSSParseErrorHandler ());
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/bad_but_recoverable/test-string.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion, aErrors);
    assertNotNull (aCSS);
  }

  @Test
  public void testSpecialCasesAsString ()
  {
    // Parsing problem
    String sCSS = ".class{color:red;.class{color:green}.class{color:blue}";
    CascadingStyleSheet aCSS, aCSS2;
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals (".class{color:red}.class{color:blue}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));

    sCSS = "  \n/* comment */\n  \n.class{color:red;}";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals (".class{color:red}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));

    // With Umlauts
    sCSS = "div { colör: räd; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("div{colör:räd}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));
    aCSS2 = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS2);
    assertEquals ("div{colör:räd}", new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS2));
    assertEquals (aCSS, aCSS2);

    // With masking
    sCSS = "#mask\\26{ color: red; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("#mask\\26{color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));

    sCSS = "#mask\\26 { color: red; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("#mask\\26 {color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));

    sCSS = "#mask\\26   { color: red; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("#mask\\26 {color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));

    // With masking
    sCSS = "#mask\\x{ color: red; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("#mask\\x{color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));

    sCSS = "#mask\\x { color: red; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("#mask\\x{color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));

    sCSS = "#mask\\x   { color: red; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("#mask\\x{color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));

    // With charset rule defined
    sCSS = "@charset \"iso-8859-1\"; div{color:red ; }";
    aCSS = CSSReader.readFromString (sCSS, ECSSVersion.CSS30, new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    assertEquals ("div{color:red}",
                  new CSSWriter (new CSSWriterSettings (ECSSVersion.CSS30).setOptimizedOutput (true)).getCSSAsString (aCSS));
  }

  @Test
  public void testReadWithBOM ()
  {
    final String sCSSBase = "/* comment */.class{color:red}.class{color:blue}";
    for (final EUnicodeBOM eBOM : EUnicodeBOM.values ())
    {
      final Charset aDeterminedCharset = eBOM.getCharset ();
      if (aDeterminedCharset != null)
      {
        final CascadingStyleSheet aCSS = CSSReader.readFromStream (new ByteArrayInputStreamProvider (ArrayHelper.getConcatenated (eBOM.getBytes (),
                                                                                                                                  CharsetManager.getAsBytes (sCSSBase,
                                                                                                                                                             aDeterminedCharset))),
                                                                   aDeterminedCharset,
                                                                   ECSSVersion.CSS30,
                                                                   DoNothingCSSParseErrorHandler.getInstance ());
        assertNotNull ("Failed to read with BOM " + eBOM, aCSS);
        assertEquals (".class{color:red}.class{color:blue}",
                      new CSSWriter (ECSSVersion.CSS30, true).getCSSAsString (aCSS));
      }
    }
  }
}
