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

import com.phloc.commons.annotations.ReturnsMutableObject;
import com.phloc.commons.io.streams.NonBlockingStringWriter;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.vendor.VendorInfo;
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
  private boolean m_bWriteHeaderText;
  private String m_sHeaderText = StringHelper.getImploded ("\n", VendorInfo.FILE_HEADER_LINES);
  private String m_sContentCharset;

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

  /**
   * Constructor
   * 
   * @param aSettings
   *          The settings to be used. May not be <code>null</code>.
   */
  public CSSWriter (@Nonnull final CSSWriterSettings aSettings)
  {
    if (aSettings == null)
      throw new NullPointerException ("settings");
    m_aSettings = aSettings;
    m_bWriteHeaderText = !aSettings.isOptimizedOutput ();
  }

  /**
   * Check if the header text should be emitted. By default it is enabled, if
   * non-optimized output is desired.
   * 
   * @return <code>true</code> if the header text should be emitted,
   *         <code>false</code> if not.
   */
  public boolean isWriteHeaderText ()
  {
    return m_bWriteHeaderText;
  }

  /**
   * Determine whether the file header should be written or not. By default it
   * is enabled, if non-optimized output is desired.
   * 
   * @param bWriteHeaderText
   *          If <code>true</code> the header text will be written, if
   *          <code>false</code> the text will not be written.
   * @return this
   */
  @Nonnull
  public CSSWriter setWriteHeaderText (final boolean bWriteHeaderText)
  {
    m_bWriteHeaderText = bWriteHeaderText;
    return this;
  }

  /**
   * @return The currently defined header text. May be <code>null</code>.
   */
  @Nullable
  public String getHeaderText ()
  {
    return m_sHeaderText;
  }

  /**
   * Set a custom header text that should be emitted. This text may be multi
   * line separated by the '\n' character. It will emitted if
   * {@link #isWriteHeaderText()} returns <code>true</code>.
   * 
   * @param sHeaderText
   *          The header text to be emitted. May be <code>null</code>.
   * @return this
   */
  @Nonnull
  public CSSWriter setHeaderText (@Nullable final String sHeaderText)
  {
    m_sHeaderText = sHeaderText;
    return this;
  }

  /**
   * @return The current defined content charset for the CSS. By default it is
   *         <code>null</code>.
   */
  @Nullable
  public String getContentCharset ()
  {
    return m_sContentCharset;
  }

  /**
   * Define the content charset to be used. If not <code>null</code> and not
   * empty, the <code>@charset</code> element is emitted into the CSS. By
   * default no charset is defined.<br>
   * <b>Important:</b> this does not define the encoding of the output - it is
   * just a declarative marker inside the code. Best practise is to use the same
   * encoding for the CSS and the respective writer!
   * 
   * @param sContentCharset
   *          The content charset to be used. May be <code>null</code> to
   *          indicate that no special charset name should be emitted into the
   *          CSS.
   * @return this
   */
  @Nonnull
  public CSSWriter setContentCharset (@Nullable final String sContentCharset)
  {
    m_sContentCharset = sContentCharset;
    return this;
  }

  /**
   * @return The CSS writer settings that are used to generate the different
   *         element code. This is the same object as passed into/created by the
   *         constructor. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableObject (reason = "Design")
  public CSSWriterSettings getSettings ()
  {
    return m_aSettings;
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
    if (aCSS == null)
      throw new NullPointerException ("css");
    if (aWriter == null)
      throw new NullPointerException ("writer");

    try
    {
      final boolean bOptimizedOutput = m_aSettings.isOptimizedOutput ();

      // Write file header
      if (m_bWriteHeaderText && StringHelper.hasText (m_sHeaderText))
      {
        aWriter.write ("/*\n");
        for (final String sLine : StringHelper.getExploded ("\n", m_sHeaderText))
          aWriter.write (" * " + sLine + "\n");
        aWriter.write (" */\n");
      }

      // Charset? Must be the first element before the import
      if (StringHelper.hasText (m_sContentCharset))
      {
        aWriter.write ("@charset \"" + m_sContentCharset + "\"\n");
        if (!bOptimizedOutput)
          aWriter.write ('\n');
      }

      // Import rules
      int nRulesEmitted = 0;
      final List <CSSImportRule> aImportRules = aCSS.getAllImportRules ();
      if (!aImportRules.isEmpty ())
      {
        for (final CSSImportRule aImportRule : aImportRules)
        {
          aWriter.write (aImportRule.getAsCSSString (m_aSettings, 0));
          ++nRulesEmitted;
        }
      }

      // Main CSS rules
      for (final ICSSTopLevelRule aRule : aCSS.getAllRules ())
      {
        final String sRuleCSS = aRule.getAsCSSString (m_aSettings, 0);
        if (StringHelper.hasText (sRuleCSS))
        {
          if (!bOptimizedOutput && nRulesEmitted > 0)
            aWriter.write ('\n');

          aWriter.write (sRuleCSS);
          ++nRulesEmitted;
        }
      }
    }
    finally
    {
      StreamUtils.close (aWriter);
    }
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
   * @deprecated Use {@link #setContentCharset(String)} instead!
   */
  @Deprecated
  public void writeCSS (@Nonnull final CascadingStyleSheet aCSS,
                        @Nonnull @WillClose final Writer aWriter,
                        @Nullable final String sCSSCharset) throws IOException
  {
    writeCSS (aCSS, aWriter);
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
    final NonBlockingStringWriter aSW = new NonBlockingStringWriter ();
    writeCSS (aCSS, aSW);
    return aSW.getAsString ();
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
   * @deprecated Use {@link #setContentCharset(String)} instead!
   */
  @Nonnull
  @Deprecated
  public String getCSSAsString (@Nonnull final CascadingStyleSheet aCSS, @Nullable final String sCSSCharset) throws IOException
  {
    return getCSSAsString (aCSS);
  }
}