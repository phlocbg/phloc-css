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
package com.phloc.css.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.commons.io.streams.NonBlockingStringReader;
import com.phloc.css.AbstractCSS30Test;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.handler.CSSHandler;

/**
 * Test class for class {@link ParserCSS30}.
 *
 * @author Philip Helger
 */
public final class ParserCSS30Test extends AbstractCSS30Test
{
  @Test
  public void test1 () throws ParseException
  {
    final ParserCSS30TokenManager aTokenHdl = new ParserCSS30TokenManager (new CSSCharStream (new NonBlockingStringReader (CSS1)));
    aTokenHdl.setDebugStream (System.out);
    final ParserCSS30 aParser = new ParserCSS30 (aTokenHdl);
    aParser.disable_tracing ();
    final CSSNode aNode = aParser.styleSheet ();
    assertNotNull (aNode);
  }

  @Test
  public void test2 () throws ParseException
  {
    final ParserCSS30TokenManager aTokenHdl = new ParserCSS30TokenManager (new CSSCharStream (new NonBlockingStringReader (CSS2)));
    aTokenHdl.setDebugStream (System.out);
    final ParserCSS30 aParser = new ParserCSS30 (aTokenHdl);
    aParser.disable_tracing ();
    final CSSNode aNode = aParser.styleSheet ();
    assertNotNull (aNode);

    final CascadingStyleSheet aCSS = CSSHandler.readCascadingStyleSheetFromNode (ECSSVersion.CSS30, aNode);
    assertNotNull (aCSS);

    for (final ICSSTopLevelRule aTopLevelRule : aCSS.getAllFontFaceRules ())
      assertTrue (aCSS.removeRule (aTopLevelRule).isChanged ());
  }
}
