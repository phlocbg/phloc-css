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

import java.io.IOException;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.streamprovider.ByteArrayInputStreamProvider;
import com.phloc.commons.io.streams.NonBlockingStringWriter;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.handler.CSSHandler;

/**
 * Utility class to compress CSS content
 * 
 * @author philip
 */
public final class CSSCompressor
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSCompressor.class);

  private CSSCompressor ()
  {}

  /**
   * Get the compressed version of the passed CSS code.
   * 
   * @param sOriginalCSS
   *          The original CSS code to be compressed.
   * @param eCSSVersion
   *          The CSS version to use.
   * @return If compression failed because the CSS is invalid or whatsoever, the
   *         original CSS is returned, else the compressed version is returned.
   */
  public static String getCompressedCSS (@Nonnull final String sOriginalCSS, @Nonnull final ECSSVersion eCSSVersion)
  {
    final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new ByteArrayInputStreamProvider (sOriginalCSS.getBytes ()),
                                                                eCSSVersion);
    if (aCSS != null)
    {
      final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();
      try
      {
        new CSSWriter (eCSSVersion, true).writeCSS (aCSS, aSW);
        return aSW.toString ();
      }
      catch (final IOException ex)
      {
        s_aLogger.warn ("Failed to write CSS!", ex);
      }
    }
    return sOriginalCSS;
  }
}