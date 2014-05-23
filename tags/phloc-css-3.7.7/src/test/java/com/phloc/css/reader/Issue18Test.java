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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.IReadableResource;
import com.phloc.commons.io.resource.ClassPathResource;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.writer.CSSWriter;

/**
 * Test for issue 18: http://code.google.com/p/phloc-css/issues/detail?id=18
 * 
 * @author Philip Helger
 */
public final class Issue18Test
{
  @Test
  public void testIssue18 ()
  {
    final IReadableResource aRes = new ClassPathResource ("testfiles/css30/good/issue18.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromStream (aRes,
                                                               CCharset.CHARSET_UTF_8_OBJ,
                                                               ECSSVersion.CSS30,
                                                               new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    if (false)
      System.out.println (new CSSWriter (ECSSVersion.CSS30).getCSSAsString (aCSS));
  }
}
