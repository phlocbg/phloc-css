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
package com.phloc.css.wiki;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberFunction;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ECSSExpressionOperator;
import com.phloc.css.reader.CSSReader;
import com.phloc.css.writer.CSSWriter;

/**
 * This is example code to create a font-face rule from scratch
 * 
 * @author philip
 */
public final class WikiCreateFontFaceRule
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (WikiCreateFontFaceRule.class);

  public CascadingStyleSheet createFontFace (final String sPath, final String sBasename) throws IOException
  {
    /**
     * <pre>
     *     @font-face {
     *       font-family: "Your typeface";
     *       src: url("path/basename.eot");
     *       src: local("?"),
     *         url("path/basename.woff") format("woff"),
     *         url("path/basename.otf") format("opentype"),
     *         url("path/basename.svg#filename") format("svg");
     *     }
     * </pre>
     */
    final CascadingStyleSheet aCSS = new CascadingStyleSheet ();
    final CSSFontFaceRule aFFR = new CSSFontFaceRule ();
    aFFR.addDeclaration (new CSSDeclaration ("font-family", CSSExpression.createString ("Your \"typeface\""), false));
    aFFR.addDeclaration (new CSSDeclaration ("src", CSSExpression.createURI (sPath + sBasename + ".eot"), false));
    final CSSExpression aExpr = new CSSExpression ().addMember (new CSSExpressionMemberFunction ("local",
                                                                                                 CSSExpression.createString ("?")))
                                                    .addMember (ECSSExpressionOperator.COMMA)
                                                    .addURI (sPath + sBasename + ".woff")
                                                    .addMember (new CSSExpressionMemberFunction ("format",
                                                                                                 CSSExpression.createString ("woff")))
                                                    .addMember (ECSSExpressionOperator.COMMA)
                                                    .addURI (sPath + sBasename + ".otf")
                                                    .addMember (new CSSExpressionMemberFunction ("format",
                                                                                                 CSSExpression.createString ("opentype")))
                                                    .addMember (ECSSExpressionOperator.COMMA)
                                                    .addURI (sPath + sBasename + ".svg#" + sBasename)
                                                    .addMember (new CSSExpressionMemberFunction ("format",
                                                                                                 CSSExpression.createString ("svg")));
    aFFR.addDeclaration (new CSSDeclaration ("src", aExpr, false));

    aCSS.addRule (aFFR);
    return aCSS;
  }

  @Test
  public void test () throws IOException
  {
    final CascadingStyleSheet aCSS = createFontFace ("folder/", "myfont");
    final String sCSS = new CSSWriter (ECSSVersion.CSS30).getCSSAsString (aCSS);
    System.out.println (sCSS);

    final CascadingStyleSheet aCSS2 = CSSReader.readFromString (sCSS, CCharset.CHARSET_ISO_8859_1, ECSSVersion.CSS30);
    assertNotNull (aCSS2);
    assertEquals (aCSS, aCSS2);
  }
}
