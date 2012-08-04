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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.io.IInputStreamProvider;
import com.phloc.commons.io.IReadableResource;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.commons.io.streamprovider.StringInputStreamProvider;
import com.phloc.commons.io.streams.NonBlockingStringReader;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.handler.CSSHandler;
import com.phloc.css.handler.DoNothingCSSParseExceptionHandler;
import com.phloc.css.handler.ICSSParseExceptionHandler;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.CharStream;
import com.phloc.css.parser.JavaCharStream;
import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.ParseUtils;
import com.phloc.css.parser.ParserCSS21;
import com.phloc.css.parser.ParserCSS21TokenManager;
import com.phloc.css.parser.ParserCSS30;
import com.phloc.css.parser.ParserCSS30TokenManager;
import com.phloc.css.parser.ParserCSSCharsetDetector;
import com.phloc.css.parser.ParserCSSCharsetDetectorTokenManager;

/**
 * This is the central user class for reading and parsing CSS from an input
 * stream.
 * 
 * @author philip
 */
@Immutable
public final class CSSReader
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSReader.class);

  private CSSReader ()
  {}

  @Nullable
  private static CSSNode _readFromStream (@Nonnull final CharStream aStream,
                                          @Nonnull final ECSSVersion eVersion,
                                          @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    switch (eVersion)
    {
      case CSS21:
      {
        final ParserCSS21TokenManager aTokenHdl = new ParserCSS21TokenManager (aStream);
        final ParserCSS21 aParser = new ParserCSS21 (aTokenHdl);
        try
        {
          return aParser.styleSheet ();
        }
        catch (final ParseException ex)
        {
          if (aCustomExceptionHandler != null)
            aCustomExceptionHandler.onException (ex);
          else
            s_aLogger.error ("Failed to parse CSS 2.1 definition: " + ex.getMessage ());
          return null;
        }
      }
      case CSS30:
      {
        final ParserCSS30TokenManager aTokenHdl = new ParserCSS30TokenManager (aStream);
        final ParserCSS30 aParser = new ParserCSS30 (aTokenHdl);
        try
        {
          return aParser.styleSheet ();
        }
        catch (final ParseException ex)
        {
          if (aCustomExceptionHandler != null)
            aCustomExceptionHandler.onException (ex);
          else
            s_aLogger.error ("Failed to parse CSS 3.0 definition: " + ex.getMessage ());
          return null;
        }
      }
      default:
        throw new IllegalArgumentException ("Unsupported CSS version " + eVersion);
    }
  }

  /**
   * Check if the passed CSS file can be parsed without error
   * 
   * @param aFile
   *        The file to be parsed. May not be <code>null</code>.
   * @param sCharset
   *        The charset to be used for reading the CSS file. May not be
   *        <code>null</code>.
   * @param eVersion
   *        The CSS version to be used for scanning. May not be
   *        <code>null</code>.
   * @return <code>true</code> if the file can be parsed without error,
   *         <code>false</code> if not
   * @throws IllegalArgumentException
   *         if the passed charset is unknown
   */
  public static boolean isValidCSS (@Nonnull final File aFile,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return isValidCSS (new FileSystemResource (aFile), sCharset, eVersion);
  }

  /**
   * Check if the passed CSS file can be parsed without error
   * 
   * @param aFile
   *        The file to be parsed. May not be <code>null</code>.
   * @param aCharset
   *        The charset to be used for reading the CSS file. May not be
   *        <code>null</code>.
   * @param eVersion
   *        The CSS version to be used for scanning. May not be
   *        <code>null</code>.
   * @return <code>true</code> if the file can be parsed without error,
   *         <code>false</code> if not
   */
  public static boolean isValidCSS (@Nonnull final File aFile,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return isValidCSS (new FileSystemResource (aFile), aCharset, eVersion);
  }

  /**
   * Check if the passed CSS resource can be parsed without error
   * 
   * @param aRes
   *        The resource to be parsed. May not be <code>null</code>.
   * @param sCharset
   *        The charset to be used for reading the CSS file. May not be
   *        <code>null</code>.
   * @param eVersion
   *        The CSS version to be used for scanning. May not be
   *        <code>null</code>.
   * @return <code>true</code> if the file can be parsed without error,
   *         <code>false</code> if not
   * @throws IllegalArgumentException
   *         if the passed charset is unknown
   */
  public static boolean isValidCSS (@Nonnull final IReadableResource aRes,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    final Charset aCharset = CharsetManager.getCharsetFromName (sCharset);
    if (aCharset == null)
      throw new IllegalArgumentException ("Illegal charset '" + sCharset + "'");
    return isValidCSS (aRes, aCharset, eVersion);
  }

  /**
   * Check if the passed CSS resource can be parsed without error
   * 
   * @param aRes
   *        The resource to be parsed. May not be <code>null</code>.
   * @param aCharset
   *        The charset to be used for reading the CSS file. May not be
   *        <code>null</code>.
   * @param eVersion
   *        The CSS version to be used for scanning. May not be
   *        <code>null</code>.
   * @return <code>true</code> if the file can be parsed without error,
   *         <code>false</code> if not
   */
  public static boolean isValidCSS (@Nonnull final IReadableResource aRes,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    if (aRes == null)
      throw new NullPointerException ("resources");
    if (aCharset == null)
      throw new NullPointerException ("charset");
    if (eVersion == null)
      throw new NullPointerException ("version");

    final Reader aReader = aRes.getReader (aCharset);
    if (aReader == null)
    {
      s_aLogger.warn ("Failed to open CSS reader " + aRes);
      return false;
    }
    return isValidCSS (aReader, eVersion);
  }

  /**
   * Check if the passed input stream can be resembled to valid CSS content.
   * This is accomplished by fully parsing the CSS file each time the method is
   * called. This is similar to calling
   * {@link #readFromStream(IInputStreamProvider, String, ECSSVersion)} and
   * checking for a non-<code>null</code> result.
   * 
   * @param aIS
   *        The input stream to use. Is automatically closed. May not be
   *        <code>null</code>.
   * @param sCharset
   *        The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version,
   *         <code>false</code> if not
   */
  public static boolean isValidCSS (@Nonnull @WillClose final InputStream aIS,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    if (aIS == null)
      throw new NullPointerException ("inputStream");
    if (sCharset == null)
      throw new NullPointerException ("charset");

    return isValidCSS (StreamUtils.createReader (aIS, sCharset), eVersion);
  }

  /**
   * Check if the passed input stream can be resembled to valid CSS content.
   * This is accomplished by fully parsing the CSS file each time the method is
   * called. This is similar to calling
   * {@link #readFromStream(IInputStreamProvider,Charset, ECSSVersion)} and
   * checking for a non-<code>null</code> result.
   * 
   * @param aIS
   *        The input stream to use. Is automatically closed. May not be
   *        <code>null</code>.
   * @param aCharset
   *        The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version,
   *         <code>false</code> if not
   */
  public static boolean isValidCSS (@Nonnull @WillClose final InputStream aIS,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    if (aIS == null)
      throw new NullPointerException ("inputStream");
    if (aCharset == null)
      throw new NullPointerException ("charset");

    return isValidCSS (StreamUtils.createReader (aIS, aCharset), eVersion);
  }

  /**
   * Check if the passed String can be resembled to valid CSS content. This is
   * accomplished by fully parsing the CSS file each time the method is called.
   * This is similar to calling
   * {@link #readFromString(String, Charset, ECSSVersion)} and checking for a
   * non-<code>null</code> result.
   * 
   * @param sCSS
   *        The CSS string to scan. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version,
   *         <code>false</code> if not
   */
  public static boolean isValidCSS (@Nonnull final String sCSS, @Nonnull final ECSSVersion eVersion)
  {
    if (sCSS == null)
      throw new NullPointerException ("reader");

    return isValidCSS (new NonBlockingStringReader (sCSS), eVersion);
  }

  /**
   * Check if the passed input stream can be resembled to valid CSS content.
   * This is accomplished by fully parsing the CSS file each time the method is
   * called. This is similar to calling
   * {@link #readFromStream(IInputStreamProvider, Charset, ECSSVersion)} and
   * checking for a non-<code>null</code> result.
   * 
   * @param aReader
   *        The reader to use. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version,
   *         <code>false</code> if not
   */
  public static boolean isValidCSS (@Nonnull @WillClose final Reader aReader, @Nonnull final ECSSVersion eVersion)
  {
    if (aReader == null)
      throw new NullPointerException ("reader");
    if (eVersion == null)
      throw new NullPointerException ("version");

    try
    {
      final JavaCharStream aCharStream = new JavaCharStream (aReader);
      final CSSNode aNode = _readFromStream (aCharStream, eVersion, new DoNothingCSSParseExceptionHandler ());
      return aNode != null;
    }
    finally
    {
      StreamUtils.close (aReader);
    }
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromString (sCSS, sCharset, eVersion, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromString (sCSS, aCharset, eVersion, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, sCharset), sCharset, eVersion, aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, aCharset), aCharset, eVersion, aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return readFromFile (aFile, sCharset, eVersion, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return readFromFile (aFile, aCharset, eVersion, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new FileSystemResource (aFile), sCharset, eVersion, aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new FileSystemResource (aFile), aCharset, eVersion, aCustomExceptionHandler);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}. If the CSS
   * contains an explicit charset, the whole CSS is parsed again, with the
   * charset found inside the file, so the passed {@link IInputStreamProvider}
   * must be able to create a new input stream on second invocation!
   * 
   * @param aISP
   *        The input stream provider to use. Must be able to create new input
   *        streams on every invocation, in case an explicit charset node was
   *        found. May not be <code>null</code>.
   * @param sCharset
   *        The charset name to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (aISP, sCharset, eVersion, null);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}. If the CSS
   * contains an explicit charset, the whole CSS is parsed again, with the
   * charset found inside the file, so the passed {@link IInputStreamProvider}
   * must be able to create a new input stream on second invocation!
   * 
   * @param aISP
   *        The input stream provider to use. Must be able to create new input
   *        streams on every invocation, in case an explicit charset node was
   *        found. May not be <code>null</code>.
   * @param aCharset
   *        The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (aISP, aCharset, eVersion, null);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}. If the CSS
   * contains an explicit charset, the whole CSS is parsed again, with the
   * charset found inside the file, so the passed {@link IInputStreamProvider}
   * must be able to create a new input stream on second invocation!
   * 
   * @param aISP
   *        The input stream provider to use. Must be able to create new input
   *        streams on every invocation, in case an explicit charset node was
   *        found. May not be <code>null</code>.
   * @param sCharset
   *        The charset name to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aCustomExceptionHandler
   *        An optional custom exception handler that can be used to collect the
   *        parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    final Charset aCharset = CharsetManager.getCharsetFromName (sCharset);
    if (aCharset == null)
      throw new IllegalArgumentException ("Failed to resolve charset '" + sCharset + "'");
    return readFromStream (aISP, aCharset, eVersion, aCustomExceptionHandler);
  }

  /**
   * Check if the CSS represented by the passed input stream provider has a
   * custom charset contained
   * 
   * @param aISP
   *        The input stream provider to read from. May not be <code>null</code>
   *        .
   * @return <code>null</code> if the CSS does not contain a custom CSS
   *         declaration, the declared charset otherwise
   */
  @Nullable
  public static Charset getCharsetDeclaredInCSS (@Nonnull final IInputStreamProvider aISP)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");

    // Open input stream
    final InputStream aIS = aISP.getInputStream ();
    if (aIS == null)
      return null;

    try
    {
      // Always read as ISO-8859-1 as everything contained in the CSS charset
      // declaration can be handled by this charset
      final JavaCharStream aCharStream = new JavaCharStream (aIS, CCharset.CHARSET_ISO_8859_1_OBJ);
      final ParserCSSCharsetDetectorTokenManager aTokenHdl = new ParserCSSCharsetDetectorTokenManager (aCharStream);
      final ParserCSSCharsetDetector aParser = new ParserCSSCharsetDetector (aTokenHdl);
      final String sCharsetName = aParser.styleSheetCharset ().getText ();
      if (sCharsetName == null)
        return null;
      // Remove leading and trailing quotes from value
      return CharsetManager.getCharsetFromName (ParseUtils.extractStringValue (sCharsetName));
    }
    catch (final ParseException ex)
    {
      // Should never occur, as the parse exception is caught inside!
      throw new IllegalStateException ("Failed to parse CSS charset definition", ex);
    }
    finally
    {
      StreamUtils.close (aIS);
    }
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}. If the CSS
   * contains an explicit charset, the whole CSS is parsed again, with the
   * charset found inside the file, so the passed {@link IInputStreamProvider}
   * must be able to create a new input stream on second invocation!
   * 
   * @param aISP
   *        The input stream provider to use. Must be able to create new input
   *        streams on every invocation, in case an explicit charset node was
   *        found. May not be <code>null</code>.
   * @param aCharset
   *        The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aCustomExceptionHandler
   *        An optional custom exception handler that can be used to collect the
   *        parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");
    if (aCharset == null)
      throw new NullPointerException ("charset");
    if (eVersion == null)
      throw new NullPointerException ("version");

    Charset aCharsetToUse = aCharset;

    // Check if the CSS contains a declared charset
    final Charset aDeclaredCharset = getCharsetDeclaredInCSS (aISP);
    if (aDeclaredCharset != null)
    {
      s_aLogger.info ("Reading CSS definition again with explicit charset '" + aDeclaredCharset.name () + "'");
      aCharsetToUse = aDeclaredCharset;
    }

    final InputStream aIS = aISP.getInputStream ();
    if (aIS == null)
      return null;

    try
    {
      final JavaCharStream aCharStream = new JavaCharStream (aIS, aCharsetToUse);
      final CSSNode aNode = _readFromStream (aCharStream, eVersion, aCustomExceptionHandler);

      // Failed to interprete content as CSS?
      if (aNode == null)
        return null;

      // Convert the AST to a domain object
      return CSSHandler.readFromNode (eVersion, aNode);
    }
    finally
    {
      StreamUtils.close (aIS);
    }
  }
}
