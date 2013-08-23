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
package com.phloc.css.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link CSSDataURLHelper}.
 * 
 * @author Philip Helger
 */
public final class CSSDataURLHelperTest
{
  private static final String [] VALID = new String [] { "data:image/gif;base64,R0lGODdhMAAwAPAAAAAAAP///ywAAAAAMAAw\r\n"
                                                         + "AAAC8IyPqcvt3wCcDkiLc7C0qwyGHhSWpjQu5yqmCYsapyuvUUlvONmOZtfzgFz\r\n"
                                                         + "ByTB10QgxOR0TqBQejhRNzOfkVJ+5YiUqrXF5Y5lKh/DeuNcP5yLWGsEbtLiOSp\r\n"
                                                         + "a/TPg7JpJHxyendzWTBfX0cxOnKPjgBzi4diinWGdkF8kjdfnycQZXZeYGejmJl\r\n"
                                                         + "ZeGl9i2icVqaNVailT6F5iJ90m6mvuTS4OK05M0vDk0Q4XUtwvKOzrcd3iq9uis\r\n"
                                                         + "F81M1OIcR7lEewwcLp7tuNNkM3uNna3F2JQFo97Vriy/Xl4/f1cf5VWzXyym7PH\r\n"
                                                         + "hhx4dbgYKAAA7",
                                                        "data:text/xml,<root>code</root>",
                                                        "data:text/xml;param1=abc,<root>code</root>",
                                                        "data:text/xml;param1=abc;param2=ab%2bcd,<root>code</root>" };

  @Test
  public void testIsDataURL ()
  {
    assertFalse (CSSDataURLHelper.isDataURL (null));
    assertFalse (CSSDataURLHelper.isDataURL (""));
    assertFalse (CSSDataURLHelper.isDataURL ("data"));
    assertFalse (CSSDataURLHelper.isDataURL (" data"));
    assertFalse (CSSDataURLHelper.isDataURL ("data:"));
    assertFalse (CSSDataURLHelper.isDataURL ("data:any"));
    assertFalse (CSSDataURLHelper.isDataURL (" data:"));
    assertFalse (CSSDataURLHelper.isDataURL (" data: "));
    assertFalse (CSSDataURLHelper.isDataURL (" data:any"));
    for (final String sValid : VALID)
    {
      assertTrue (CSSDataURLHelper.isDataURL (sValid));
      CSSDataURL.parseDataURL (sValid);
    }
  }
}
