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

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.file.SimpleFileIO;
import com.phloc.commons.state.ESuccess;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

/**
 * This is example code to write a CSS declaration to a {@link File}.
 * 
 * @author philip
 */
public final class WikiWriteCSS
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (WikiWriteCSS.class);

  /**
   * Write a CSS 3.0 declaration to a file using UTF-8 encoding.
   * 
   * @param aCSS
   *        The CSS to be written to a file
   * @param aFile
   *        The file to be written. May not be <code>null</code>.
   * @return {@link ESuccess#SUCCESS} if everything went okay, and
   *         {@link ESuccess#FAILURE} if an error occurred
   */
  public ESuccess writeCSS30 (final CascadingStyleSheet aCSS, final File aFile)
  {
    // 1.param: version to write
    // 2.param: false== non-optimized output
    final CSSWriterSettings aSettings = new CSSWriterSettings (ECSSVersion.CSS30, false);
    try
    {
      final CSSWriter aWriter = new CSSWriter (aSettings);
      // Write the @charset rule: (optional)
      aWriter.setContentCharset ("utf-8");
      // Write a nice file header
      aWriter.setHeaderText ("This file was generated by phloc-css\nGrab a copy at http://code.google.com/p/phloc-css");
      // Convert the CSS to a String
      final String sCSSCode = aWriter.getCSSAsString (aCSS);
      // Finally write the String to a file
      return SimpleFileIO.writeFile (aFile, sCSSCode, "utf-8");
    }
    catch (final IOException ex)
    {
      s_aLogger.error ("Failed to write the CSS to a file", ex);
      return ESuccess.FAILURE;
    }
  }
}
