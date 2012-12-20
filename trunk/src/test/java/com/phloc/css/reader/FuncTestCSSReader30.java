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
package com.phloc.css.reader;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;

public final class FuncTestCSSReader30 extends AbstractFuncTestCSSReader
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSReader30.class);

  public FuncTestCSSReader30 ()
  {
    super (ECSSVersion.CSS30, CCharset.CHARSET_UTF_8_OBJ, false);
  }

  @Test
  public void testReadAll30Good () throws IOException
  {
    testReadGood ("src/test/resources/testfiles/css30/good");
    testReadGood ("src/test/resources/testfiles/css30/bad_but_succeeding");
  }

  @Test
  public void testReadAll30Bad () throws IOException
  {
    testReadBad ("src/test/resources/testfiles/css30/bad");
    testReadBad ("src/test/resources/testfiles/css30/good_but_failing");
  }

  @Ignore
  @Test
  public void testReadSpecial ()
  {
    final ECSSVersion eVersion = ECSSVersion.CSS30;
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final File aFile = new File ("src/test/resources/testfiles/css30/good_but_failing/w3c/selectors/css3-modsel-175a.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromFile (aFile, aCharset, eVersion);
    assertNotNull (aCSS);
  }
}
