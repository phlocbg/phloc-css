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

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;

public final class FuncTestCSSReader21 extends AbstractFuncTestCSSReader
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestCSSReader21.class);

  public FuncTestCSSReader21 ()
  {
    super (ECSSVersion.CSS21, CCharset.CHARSET_UTF_8_OBJ, false);
  }

  @Test
  public void testReadAll21GoodArtifical () throws IOException
  {
    testReadGood ("src/test/resources/testfiles/css21/good/artificial");
  }

  @Test
  public void testReadAll21Good () throws IOException
  {
    testReadGood ("src/test/resources/testfiles/css21/good");
  }

  @Test
  public void testReadAll21Bad () throws IOException
  {
    testReadBad ("src/test/resources/testfiles/css21/bad");
  }
}
