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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.css.ECSSVersion;

/**
 * Test class for class {@link CSSReaderDeclarationList}
 * 
 * @author philip
 */
public final class CSSReaderDeclarationListTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSReaderDeclarationListTest.class);
  private static final List <String> aValid = ContainerHelper.newList ("color:red; background:fixed;",
                                                                       "  color:red; background:fixed;  ",
                                                                       "color:red; background:fixed",
                                                                       "color:red; background:fixed !important");
  private static final List <String> aInvalid = ContainerHelper.newList ("color", " color ");

  @Test
  public void testIsValidCSS ()
  {
    for (final String sCSS : aValid)
      assertTrue (sCSS, CSSReaderDeclarationList.isValidCSS (sCSS, ECSSVersion.CSS30));
    for (final String sCSS : aInvalid)
      assertFalse (sCSS, CSSReaderDeclarationList.isValidCSS (sCSS, ECSSVersion.CSS30));
  }

  @Test
  public void testRead ()
  {
    for (final String sCSS : aValid)
      assertNotNull (sCSS, CSSReaderDeclarationList.readFromString (sCSS, ECSSVersion.CSS30));
    for (final String sCSS : aInvalid)
      assertNull (sCSS, CSSReaderDeclarationList.readFromString (sCSS, ECSSVersion.CSS30));
  }
}
