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
package com.phloc.css.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.phloc.commons.url.SMap;
import com.phloc.commons.url.SimpleURL;

/**
 * Test class for class {@link CSSURLHelper}.
 * 
 * @author philip
 */
public final class CSSURLHelperTest
{
  @Test
  public void testGetURLValue ()
  {
    assertEquals ("a.gif", CSSURLHelper.getURLValue ("url(a.gif)"));
    assertEquals ("a.gif", CSSURLHelper.getURLValue ("url('a.gif')"));
    assertEquals ("a.gif", CSSURLHelper.getURLValue ("url(\"a.gif\")"));
    assertEquals ("a.gif?x=y", CSSURLHelper.getURLValue ("url(\"a.gif?x=y\")"));
    // different quote types
    assertEquals ("\"a.gif?x=y'", CSSURLHelper.getURLValue ("url(\"a.gif?x=y')"));
    // missing trailing ")"
    assertNull (CSSURLHelper.getURLValue ("url(a.gif"));
    // blank between "url" and "("
    assertNull (CSSURLHelper.getURLValue ("url (a.gif)"));
  }

  @Test
  public void testGetAsCSSURL ()
  {
    assertEquals ("url(a.gif)", CSSURLHelper.getAsCSSURL ("a.gif", false));
    assertEquals ("url('a.gif')", CSSURLHelper.getAsCSSURL ("a.gif", true));
    final SimpleURL aURL = new SimpleURL ("a.gif", new SMap ("x", "y"));
    assertEquals ("url(a.gif?x=y)", CSSURLHelper.getAsCSSURL (aURL, false));
    assertEquals ("url('a.gif?x=y')", CSSURLHelper.getAsCSSURL (aURL, true));
    // SimpleURL -> CSS URL -> String -> SimpleURL
    assertEquals (aURL, new SimpleURL (CSSURLHelper.getURLValue (CSSURLHelper.getAsCSSURL (aURL, true))));
    try
    {
      // Results in an empty URL!
      CSSURLHelper.getAsCSSURL (new SimpleURL (), false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // empty URL!
      CSSURLHelper.getAsCSSURL ("", false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}