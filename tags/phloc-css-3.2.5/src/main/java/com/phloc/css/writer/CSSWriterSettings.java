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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.utils.CSSURLHelper;

/**
 * This class represents the options required for writing
 * 
 * @author philip
 */
@NotThreadSafe
public class CSSWriterSettings implements ICSSWriterSettings
{
  public static final boolean DEFAULT_OPTIMIZED_OUTPUT = false;
  public static final boolean DEFAULT_REMOVE_UNNECESSARY_CODE = false;
  public static final String DEFAULT_INDENT = "  ";
  public static final boolean DEFAULT_QUOTE_URLS = CSSURLHelper.DEFAULT_QUOTE_URLS;
  public static final boolean DEFAULT_WRITE_FONT_FACE_RULES = true;
  public static final boolean DEFAULT_WRITE_KEYFRAMES_RULES = true;
  public static final boolean DEFAULT_WRITE_MEDIA_RULES = true;
  public static final boolean DEFAULT_WRITE_PAGE_RULES = true;

  private final ECSSVersion m_eVersion;
  private boolean m_bOptimizedOutput;
  private boolean m_bRemoveUnnecessaryCode = DEFAULT_REMOVE_UNNECESSARY_CODE;
  private String m_sIndent = DEFAULT_INDENT;
  private boolean m_bQuoteURLs = DEFAULT_QUOTE_URLS;
  private boolean m_bWriteFontFaceRules = DEFAULT_WRITE_FONT_FACE_RULES;
  private boolean m_bWriteKeyframesRules = DEFAULT_WRITE_KEYFRAMES_RULES;
  private boolean m_bWriteMediaRules = DEFAULT_WRITE_MEDIA_RULES;
  private boolean m_bWritePageRules = DEFAULT_WRITE_PAGE_RULES;

  /**
   * @param eVersion
   *        CSS version to emit
   */
  public CSSWriterSettings (@Nonnull final ECSSVersion eVersion)
  {
    this (eVersion, DEFAULT_OPTIMIZED_OUTPUT);
  }

  /**
   * @param eVersion
   *        CSS version to emit
   * @param bOptimizedOutput
   *        if <code>true</code> the output will be optimized for space, else
   *        for readability
   */
  public CSSWriterSettings (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    m_eVersion = eVersion;
    m_bOptimizedOutput = bOptimizedOutput;
  }

  /**
   * Copy constructor.
   * 
   * @param aBase
   *        The base settings to copy everything from.
   */
  public CSSWriterSettings (@Nonnull final ICSSWriterSettings aBase)
  {
    if (aBase == null)
      throw new NullPointerException ("base");
    m_eVersion = aBase.getVersion ();
    m_bOptimizedOutput = aBase.isOptimizedOutput ();
    m_sIndent = aBase.getIndent (1);
    m_bQuoteURLs = aBase.isQuoteURLs ();
    m_bWriteFontFaceRules = aBase.isWriteFontFaceRules ();
    m_bWriteKeyframesRules = aBase.isWriteKeyframesRules ();
    m_bWriteMediaRules = aBase.isWriteMediaRules ();
    m_bWritePageRules = aBase.isWritePageRules ();
  }

  @Nonnull
  public final ECSSVersion getVersion ()
  {
    return m_eVersion;
  }

  public final boolean isOptimizedOutput ()
  {
    return m_bOptimizedOutput;
  }

  @Nonnull
  public final CSSWriterSettings setOptimizedOutput (final boolean bOptimizedOutput)
  {
    m_bOptimizedOutput = bOptimizedOutput;
    return this;
  }

  public final boolean isRemoveUnnecessaryCode ()
  {
    return m_bRemoveUnnecessaryCode;
  }

  @Nonnull
  public final CSSWriterSettings setRemoveUnnecessaryCode (final boolean bRemoveUnnecessaryCode)
  {
    m_bRemoveUnnecessaryCode = bRemoveUnnecessaryCode;
    return this;
  }

  @Nonnull
  public final String getIndent (@Nonnegative final int nCount)
  {
    return StringHelper.getRepeated (m_sIndent, nCount);
  }

  @Nonnull
  public final CSSWriterSettings setIndent (@Nonnull final String sIndent)
  {
    if (sIndent == null)
      throw new NullPointerException ("indent");
    m_sIndent = sIndent;
    return this;
  }

  public final boolean isQuoteURLs ()
  {
    return m_bQuoteURLs;
  }

  @Nonnull
  public final CSSWriterSettings setQuoteURLs (final boolean bQuoteURLs)
  {
    m_bQuoteURLs = bQuoteURLs;
    return this;
  }

  public final boolean isWriteFontFaceRules ()
  {
    return m_bWriteFontFaceRules;
  }

  @Nonnull
  public final CSSWriterSettings setWriteFontFaceRules (final boolean bWriteFontFaceRules)
  {
    m_bWriteFontFaceRules = bWriteFontFaceRules;
    return this;
  }

  public final boolean isWriteKeyframesRules ()
  {
    return m_bWriteKeyframesRules;
  }

  @Nonnull
  public final CSSWriterSettings setWriteKeyframesRules (final boolean bWriteKeyframesRules)
  {
    m_bWriteKeyframesRules = bWriteKeyframesRules;
    return this;
  }

  public final boolean isWriteMediaRules ()
  {
    return m_bWriteMediaRules;
  }

  @Nonnull
  public final CSSWriterSettings setWriteMediaRules (final boolean bWriteMediaRules)
  {
    m_bWriteMediaRules = bWriteMediaRules;
    return this;
  }

  public final boolean isWritePageRules ()
  {
    return m_bWritePageRules;
  }

  @Nonnull
  public final CSSWriterSettings setWritePageRules (final boolean bWritePageRules)
  {
    m_bWritePageRules = bWritePageRules;
    return this;
  }

  public void checkVersionRequirements (@Nonnull final ICSSVersionAware aCSSObject)
  {
    final ECSSVersion eMinCSSVersion = aCSSObject.getMinimumCSSVersion ();
    if (m_eVersion.compareTo (eMinCSSVersion) < 0)
      throw new IllegalStateException ("This object cannot be serialized to CSS version " +
                                       m_eVersion.getVersion ().getAsString () +
                                       " but requires at least " +
                                       eMinCSSVersion.getVersion ().getAsString ());
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSWriterSettings rhs = (CSSWriterSettings) o;
    return m_eVersion.equals (rhs.m_eVersion) &&
           m_bOptimizedOutput == rhs.m_bOptimizedOutput &&
           m_bRemoveUnnecessaryCode == rhs.m_bRemoveUnnecessaryCode &&
           m_sIndent.equals (rhs.m_sIndent) &&
           m_bQuoteURLs == rhs.m_bQuoteURLs &&
           m_bWriteFontFaceRules == rhs.m_bWriteFontFaceRules &&
           m_bWriteKeyframesRules == rhs.m_bWriteKeyframesRules &&
           m_bWriteMediaRules == rhs.m_bWriteMediaRules &&
           m_bWritePageRules == rhs.m_bWritePageRules;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eVersion)
                                       .append (m_bOptimizedOutput)
                                       .append (m_bRemoveUnnecessaryCode)
                                       .append (m_sIndent)
                                       .append (m_bQuoteURLs)
                                       .append (m_bWriteFontFaceRules)
                                       .append (m_bWriteKeyframesRules)
                                       .append (m_bWriteMediaRules)
                                       .append (m_bWritePageRules)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("version", m_eVersion)
                                       .append ("optimizedOutput", m_bOptimizedOutput)
                                       .append ("removeUnnecessaryCode", m_bRemoveUnnecessaryCode)
                                       .append ("indent", m_sIndent)
                                       .append ("quoteURLs", m_bQuoteURLs)
                                       .append ("writeFontFaceRules", m_bWriteFontFaceRules)
                                       .append ("writeKeyframesRules", m_bWriteKeyframesRules)
                                       .append ("writeMediaRules", m_bWriteMediaRules)
                                       .append ("writePageRules", m_bWritePageRules)
                                       .toString ();
  }
}
