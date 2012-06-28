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
package com.phloc.css.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

public final class CSSWriterTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSWriterTest.class);

  private static final String CSS1 = "h1 { color : red ; margin: 1px; } h2 { color: rgb(1,2,3);} h3{}"
                                     + " @keyframes x { from { align:left;color:#123;} to { x:y; } 50% {}}"
                                     + " @page {margin: 1in; marks: none; } @page :first {margin: 2in; } @page :last {}"
                                     + " @media print { div { width:100%; min-height:0; }} @media all { div { width:90%; }} @media tv { }"
                                     + "@font-face { font-family: 'Soho'; src: url('Soho.eot'); } @font-face { src: local('Soho Gothic Pro');} @font-face { }";

  @Test
  public void testIndentation () throws IOException
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromString (CSS1, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false);
    final CSSWriter aWriter = new CSSWriter (aSettings).setWriteHeaderText (false);
    assertEquals ("h1 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "h3 {}\n"
                  + "\n"
                  + "@keyframes x {\n"
                  + "  from {\n"
                  + "    align:left;\n"
                  + "    color:#123;\n"
                  + "  }\n"
                  + "  to { x:y; }\n"
                  + "  50% {}\n"
                  + "}\n"
                  + "\n"
                  + "@page {\n"
                  + "  margin:1in;\n"
                  + "  marks:none;\n"
                  + "}\n"
                  + "\n"
                  + "@page :first { margin:2in; }\n"
                  + "\n"
                  + "@page :last {}\n"
                  + "\n"
                  + "@media print {\n"
                  + "  div {\n"
                  + "    width:100%;\n"
                  + "    min-height:0;\n"
                  + "  }\n"
                  + "}\n"
                  + "\n"
                  + "@media all {\n"
                  + "  div { width:90%; }\n"
                  + "}\n"
                  + "\n"
                  + "@media tv {}\n"
                  + "\n"
                  + "@font-face {\n"
                  + "  font-family:'Soho';\n"
                  + "  src:url(Soho.eot);\n"
                  + "}\n"
                  + "\n"
                  + "@font-face { src:local('Soho Gothic Pro'); }\n"
                  + "\n"
                  + "@font-face {}\n", aWriter.getCSSAsString (aCSS));

    // Change indentation
    aSettings.setIndent ("\t");
    assertEquals ("h1 {\n"
                  + "\tcolor:red;\n"
                  + "\tmargin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "h3 {}\n"
                  + "\n"
                  + "@keyframes x {\n"
                  + "\tfrom {\n"
                  + "\t\talign:left;\n"
                  + "\t\tcolor:#123;\n"
                  + "\t}\n"
                  + "\tto { x:y; }\n"
                  + "\t50% {}\n"
                  + "}\n"
                  + "\n"
                  + "@page {\n"
                  + "\tmargin:1in;\n"
                  + "\tmarks:none;\n"
                  + "}\n"
                  + "\n"
                  + "@page :first { margin:2in; }\n"
                  + "\n"
                  + "@page :last {}\n"
                  + "\n"
                  + "@media print {\n"
                  + "\tdiv {\n"
                  + "\t\twidth:100%;\n"
                  + "\t\tmin-height:0;\n"
                  + "\t}\n"
                  + "}\n"
                  + "\n"
                  + "@media all {\n"
                  + "\tdiv { width:90%; }\n"
                  + "}\n"
                  + "\n"
                  + "@media tv {}\n"
                  + "\n"
                  + "@font-face {\n"
                  + "\tfont-family:'Soho';\n"
                  + "\tsrc:url(Soho.eot);\n"
                  + "}\n"
                  + "\n"
                  + "@font-face { src:local('Soho Gothic Pro'); }\n"
                  + "\n"
                  + "@font-face {}\n", aWriter.getCSSAsString (aCSS));
  }

  private static final String CSS2 = "@media print { "
                                     + "h1 { color : red ; margin: 1px; } h2 { color: rgb(1,2,3);} h3{}"
                                     + " @keyframes x { from { align:left;color:#123;} to { x:y; } 50% { } }"
                                     + " @page {margin: 1in; marks: none; } @page :first {margin: 2in; }"
                                     + "@font-face { font-family: 'Soho'; src: url('Soho.eot'); } @font-face { src: local('Soho Gothic Pro');} @font-face { }"
                                     + "}";

  @Test
  public void testIndentationNested () throws IOException
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromString (CSS2, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false);
    final CSSWriter aWriter = new CSSWriter (aSettings).setWriteHeaderText (false);
    assertEquals ("@media print {\n"
                  + "  h1 {\n"
                  + "    color:red;\n"
                  + "    margin:1px;\n"
                  + "  }\n"
                  + "\n"
                  + "  h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "  h3 {}\n"
                  + "\n"
                  + "  @keyframes x {\n"
                  + "    from {\n"
                  + "      align:left;\n"
                  + "      color:#123;\n"
                  + "    }\n"
                  + "    to { x:y; }\n"
                  + "    50% {}\n"
                  + "  }\n"
                  + "\n"
                  + "  @page {\n"
                  + "    margin:1in;\n"
                  + "    marks:none;\n"
                  + "  }\n"
                  + "\n"
                  + "  @page :first { margin:2in; }\n"
                  + "\n"
                  + "  @font-face {\n"
                  + "    font-family:'Soho';\n"
                  + "    src:url(Soho.eot);\n"
                  + "  }\n"
                  + "\n"
                  + "  @font-face { src:local('Soho Gothic Pro'); }\n"
                  + "\n"
                  + "  @font-face {}\n"
                  + "}\n", aWriter.getCSSAsString (aCSS));
  }

  @Test
  public void testRemoveUnnecessaryCode1 () throws IOException
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromString (CSS1, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false).setRemoveUnnecessaryCode (true);
    final CSSWriter aWriter = new CSSWriter (aSettings).setWriteHeaderText (false);
    assertEquals ("h1 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "@keyframes x {\n"
                  + "  from {\n"
                  + "    align:left;\n"
                  + "    color:#123;\n"
                  + "  }\n"
                  + "  to { x:y; }\n"
                  + "}\n"
                  + "\n"
                  + "@page {\n"
                  + "  margin:1in;\n"
                  + "  marks:none;\n"
                  + "}\n"
                  + "\n"
                  + "@page :first { margin:2in; }\n"
                  + "\n"
                  + "@media print {\n"
                  + "  div {\n"
                  + "    width:100%;\n"
                  + "    min-height:0;\n"
                  + "  }\n"
                  + "}\n"
                  + "\n"
                  + "@media all {\n"
                  + "  div { width:90%; }\n"
                  + "}\n"
                  + "\n"
                  + "@font-face {\n"
                  + "  font-family:'Soho';\n"
                  + "  src:url(Soho.eot);\n"
                  + "}\n"
                  + "\n"
                  + "@font-face { src:local('Soho Gothic Pro'); }\n", aWriter.getCSSAsString (aCSS));
  }

  @Test
  public void testRemoveUnnecessaryCode2 () throws IOException
  {
    final CascadingStyleSheet aCSS = CSSReader.readFromString (CSS2, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false).setRemoveUnnecessaryCode (true);
    final CSSWriter aWriter = new CSSWriter (aSettings).setWriteHeaderText (false);
    assertEquals ("@media print {\n"
                  + "  h1 {\n"
                  + "    color:red;\n"
                  + "    margin:1px;\n"
                  + "  }\n"
                  + "\n"
                  + "  h2 { color:rgb(1,2,3); }\n"
                  + "\n"
                  + "  @keyframes x {\n"
                  + "    from {\n"
                  + "      align:left;\n"
                  + "      color:#123;\n"
                  + "    }\n"
                  + "    to { x:y; }\n"
                  + "  }\n"
                  + "\n"
                  + "  @page {\n"
                  + "    margin:1in;\n"
                  + "    marks:none;\n"
                  + "  }\n"
                  + "\n"
                  + "  @page :first { margin:2in; }\n"
                  + "\n"
                  + "  @font-face {\n"
                  + "    font-family:'Soho';\n"
                  + "    src:url(Soho.eot);\n"
                  + "  }\n"
                  + "\n"
                  + "  @font-face { src:local('Soho Gothic Pro'); }\n"
                  + "}\n", aWriter.getCSSAsString (aCSS));
  }

  @Test
  public void testHeaderText () throws IOException
  {
    final String sCSS = "h1 { color : red ; margin: 1px; }h2 { color : red ; margin: 1px; }";
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sCSS, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);
    assertNotNull (aCSS);

    // Non-optimized version
    CSSWriter aWriter = new CSSWriter (ECSSVersion.CSS30, false).setWriteHeaderText (true).setHeaderText ("Unit test");
    assertEquals ("/*\n"
                  + " * Unit test\n"
                  + " */\n"
                  + "h1 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n"
                  + "\n"
                  + "h2 {\n"
                  + "  color:red;\n"
                  + "  margin:1px;\n"
                  + "}\n", aWriter.getCSSAsString (aCSS));

    // Optimized version
    aWriter = new CSSWriter (ECSSVersion.CSS30, true).setWriteHeaderText (true).setHeaderText ("Unit test2");
    assertEquals ("/*\n" + " * Unit test2\n" + " */\n" + "h1{color:red;margin:1px;}h2{color:red;margin:1px;}",
                  aWriter.getCSSAsString (aCSS));
  }
}
