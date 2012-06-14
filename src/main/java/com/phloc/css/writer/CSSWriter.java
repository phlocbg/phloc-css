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
import java.io.Writer;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.io.streams.NonBlockingStringWriter;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.commons.vendor.VendorInfo;
import com.phloc.css.CSSWriterSettings;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * Convert CSS domain objects back to a text representation.
 * 
 * @author philip
 */
@Immutable
public final class CSSWriter
{
  public static final boolean DEFAULT_OPTIMIZED_OUTPUT = false;

  private final CSSWriterSettings m_aSettings;

  /**
   * Constructor for creating non-optimized output.
   * 
   * @param eVersion
   *          The CSS version to emit the code for. May not be <code>null</code>
   *          .
   */
  public CSSWriter (@Nonnull final ECSSVersion eVersion)
  {
    this (eVersion, DEFAULT_OPTIMIZED_OUTPUT);
  }

  /**
   * Constructor.
   * 
   * @param eVersion
   *          The CSS version to emit the code for. May not be <code>null</code>
   *          .
   * @param bOptimizedOutput
   *          If <code>true</code> the number of bytes emitted by this writer is
   *          minimized. Also style rules having no declarations are omitted.
   */
  public CSSWriter (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    this (new CSSWriterSettings (eVersion, bOptimizedOutput));
  }

  public CSSWriter (@Nonnull final CSSWriterSettings aSettings)
  {
    if (aSettings == null)
      throw new NullPointerException ("settings");
    m_aSettings = aSettings;
  }

  /**
   * Write the CSS content to the passed writer. No specific charset is used.
   * 
   * @param aCSS
   *          The CSS to write. May not be <code>null</code>.
   * @param aWriter
   *          The write to write the text to. May not be <code>null</code>. Is
   *          automatically closed after the writing!
   * @throws IOException
   *           In case writing fails.
   * @throws IllegalStateException
   *           In case some elements cannot be written in the version supplied
   *           in the constructor.
   */
  public void writeCSS (@Nonnull final CascadingStyleSheet aCSS, @Nonnull @WillClose final Writer aWriter) throws IOException
  {
    writeCSS (aCSS, aWriter, null);
  }

  /**
   * Write the CSS content to the passed writer. No specific charset is used.
   * 
   * @param aCSS
   *          The CSS to write. May not be <code>null</code>.
   * @param aWriter
   *          The write to write the text to. May not be <code>null</code>. Is
   *          automatically closed after the writing!
   * @param sCSSCharset
   *          The charset that is explicitly written to the CSS content. Not the
   *          encoding of the CSS file! May be <code>null</code>.
   * @throws IOException
   *           In case writing fails.
   * @throws IllegalStateException
   *           In case some elements cannot be written in the version supplied
   *           in the constructor.
   */
  public void writeCSS (@Nonnull final CascadingStyleSheet aCSS,
                        @Nonnull @WillClose final Writer aWriter,
                        @Nullable final String sCSSCharset) throws IOException
  {
    if (aCSS == null)
      throw new NullPointerException ("css");
    if (aWriter == null)
      throw new NullPointerException ("writer");

    try
    {
      // Write file header
      if (!m_aSettings.isOptimizedOutput ())
      {
        aWriter.write ("/*\n");
        for (final String sLine : VendorInfo.FILE_HEADER_LINES)
          aWriter.write (" * " + sLine + "\n");
        aWriter.write (" */\n");
      }

      // Charset?
      if (sCSSCharset != null)
      {
        aWriter.write ("@charset \"" + sCSSCharset + "\"\n");
        if (!m_aSettings.isOptimizedOutput ())
          aWriter.write ('\n');
      }

      // Import rules
      int nRulesEmitted = 0;
      final List <CSSImportRule> aImportRules = aCSS.getAllImportRules ();
      if (!aImportRules.isEmpty ())
      {
        for (final CSSImportRule aImportRule : aImportRules)
        {
          aWriter.write (aImportRule.getAsCSSString (m_aSettings));
          ++nRulesEmitted;
        }
      }

      // Main CSS rules
      for (final ICSSTopLevelRule aRule : aCSS.getAllRules ())
      {
        if (!m_aSettings.isOptimizedOutput () && nRulesEmitted > 0)
          aWriter.write ('\n');
        aWriter.write (aRule.getAsCSSString (m_aSettings));
        ++nRulesEmitted;
      }
    }
    finally
    {
      StreamUtils.close (aWriter);
    }
  }

  /**
   * Create the CSS without a specific charset.
   * 
   * @param aCSS
   *          The CSS object to be converted to text
   * @return The text representation of the CSS.
   * @throws IOException
   *           If writing fails. Should never happen!
   */
  @Nonnull
  public String getCSSAsString (@Nonnull final CascadingStyleSheet aCSS) throws IOException
  {
    return getCSSAsString (aCSS, null);
  }

  /**
   * Create the CSS with a specific charset.
   * 
   * @param aCSS
   *          The CSS object to be converted to text
   * @param sCSSCharset
   *          The charset to be added to the CSS - not the charset to encode the
   *          CSS content itself! May be <code>null</code>.
   * @return The text representation of the CSS.
   * @throws IOException
   *           If writing fails. Should never happen!
   */
  @Nonnull
  public String getCSSAsString (@Nonnull final CascadingStyleSheet aCSS, @Nullable final String sCSSCharset) throws IOException
  {
    final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();
    writeCSS (aCSS, aSW, sCSSCharset);
    return aSW.toString ();
  }
}
