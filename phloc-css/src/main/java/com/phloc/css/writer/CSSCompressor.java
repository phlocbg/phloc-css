/**
 * Copyright (C) 2006-2015 phloc systems
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

import java.nio.charset.Charset;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.PresentForCodeCoverage;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

/**
 * Utility class to compress CSS content
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSCompressor
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSCompressor.class);

  @SuppressWarnings ("unused")
  @PresentForCodeCoverage
  private static final CSSCompressor s_aInstance = new CSSCompressor ();

  private CSSCompressor ()
  {}

  /**
   * Get the compressed version of the passed CSS code.
   * 
   * @param sOriginalCSS
   *        The original CSS code to be compressed.
   * @param aCharset
   *        The character set to be used. May not be <code>null</code>.
   * @param eCSSVersion
   *        The CSS version to use.
   * @return If compression failed because the CSS is invalid or whatsoever, the
   *         original CSS is returned, else the compressed version is returned.
   */
  @Nonnull
  public static String getCompressedCSS (@Nonnull final String sOriginalCSS,
                                         @Nonnull final Charset aCharset,
                                         @Nonnull final ECSSVersion eCSSVersion)
  {
    return getCompressedCSS (sOriginalCSS, aCharset, eCSSVersion, false);
  }

  /**
   * Get the compressed version of the passed CSS code.
   * 
   * @param sOriginalCSS
   *        The original CSS code to be compressed.
   * @param aCharset
   *        The character set to be used. May not be <code>null</code>.
   * @param eCSSVersion
   *        The CSS version to use.
   * @param bRemoveUnnecessaryCode
   *        if <code>true</code> unnecessary empty declarations are omitted
   * @return If compression failed because the CSS is invalid or whatsoever, the
   *         original CSS is returned, else the compressed version is returned.
   */
  @Nonnull
  public static String getCompressedCSS (@Nonnull final String sOriginalCSS,
                                         @Nonnull final Charset aCharset,
                                         @Nonnull final ECSSVersion eCSSVersion,
                                         final boolean bRemoveUnnecessaryCode)
  {
    final CSSWriterSettings aSettings = new CSSWriterSettings (eCSSVersion, true);
    aSettings.setRemoveUnnecessaryCode (bRemoveUnnecessaryCode);
    return getRewrittenCSS (sOriginalCSS, aCharset, aSettings);
  }

  /**
   * Get the rewritten version of the passed CSS code. This is done by
   * interpreting the CSS and than writing it again with the passed settings.
   * This can e.g. be used to create a compressed version of a CSS.
   * 
   * @param sOriginalCSS
   *        The original CSS code to be compressed.
   * @param aCharset
   *        The character set to be used. May not be <code>null</code>.
   * @param aSettings
   *        The CSS writer settings to use. The version is used to read the
   *        original CSS.
   * @return If compression failed because the CSS is invalid or whatsoever, the
   *         original CSS is returned, else the rewritten version is returned.
   */
  @Nonnull
  @Deprecated
  public static String getRewrittenCSS (@Nonnull final String sOriginalCSS,
                                        @Nonnull final Charset aCharset,
                                        @Nonnull final CSSWriterSettings aSettings)
  {
    return getRewrittenCSS (sOriginalCSS, aSettings);
  }

  /**
   * Get the rewritten version of the passed CSS code. This is done by
   * interpreting the CSS and than writing it again with the passed settings.
   * This can e.g. be used to create a compressed version of a CSS.
   * 
   * @param sOriginalCSS
   *        The original CSS code to be compressed.
   * @param aSettings
   *        The CSS writer settings to use. The version is used to read the
   *        original CSS.
   * @return If compression failed because the CSS is invalid or whatsoever, the
   *         original CSS is returned, else the rewritten version is returned.
   */
  @Nonnull
  public static String getRewrittenCSS (@Nonnull final String sOriginalCSS, @Nonnull final CSSWriterSettings aSettings)
  {
    ValueEnforcer.notNull (sOriginalCSS, "OriginalCSS");
    ValueEnforcer.notNull (aSettings, "Settings");

    final CascadingStyleSheet aCSS = CSSReader.readFromString (sOriginalCSS, aSettings.getVersion ());
    if (aCSS != null)
    {
      try
      {
        return new CSSWriter (aSettings).getCSSAsString (aCSS);
      }
      catch (final Exception ex)
      {
        s_aLogger.warn ("Failed to write optimized CSS!", ex);
      }
    }
    return sOriginalCSS;
  }
}
