/**
 * Copyright (C) 2006-2013 phloc systems
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

import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.io.IInputStreamProvider;
import com.phloc.commons.io.IReadableResource;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.commons.io.streams.NonBlockingStringReader;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclarationList;
import com.phloc.css.handler.CSSHandler;
import com.phloc.css.handler.DoNothingCSSParseExceptionHandler;
import com.phloc.css.handler.ICSSParseExceptionHandler;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.CharStream;
import com.phloc.css.parser.JavaCharStream;
import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.ParserCSS21;
import com.phloc.css.parser.ParserCSS21TokenManager;
import com.phloc.css.parser.ParserCSS30;
import com.phloc.css.parser.ParserCSS30TokenManager;

/**
 * This is the central user class for reading and parsing partial CSS from
 * different sources. This class reads CSS style declarations as used in HTML
 * style attributes only.
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSReaderDeclarationList
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSReaderDeclarationList.class);

  private CSSReaderDeclarationList ()
  {}

  @Nullable
  private static CSSNode _readStyleDeclaration (@Nonnull final CharStream aStream,
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
          return aParser.styleDeclaration ();
        }
        catch (final ParseException ex)
        {
          if (aCustomExceptionHandler != null)
            aCustomExceptionHandler.onException (ex);
          else
            s_aLogger.error ("Failed to parse CSS 2.1 style declaration: " + ex.getMessage ());
          return null;
        }
      }
      case CSS30:
      {
        final ParserCSS30TokenManager aTokenHdl = new ParserCSS30TokenManager (aStream);
        final ParserCSS30 aParser = new ParserCSS30 (aTokenHdl);
        try
        {
          return aParser.styleDeclaration ();
        }
        catch (final ParseException ex)
        {
          if (aCustomExceptionHandler != null)
            aCustomExceptionHandler.onException (ex);
          else
            s_aLogger.error ("Failed to parse CSS 3.0 style declaration: " + ex.getMessage ());
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
  @Deprecated
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
   * This is similar to calling {@link #readFromString(String, ECSSVersion)} and
   * checking for a non-<code>null</code> result.
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
   * Check if the passed reader can be resembled to valid CSS content. This is
   * accomplished by fully parsing the CSS each time the method is called. This
   * is similar to calling
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
      final CSSNode aNode = _readStyleDeclaration (aCharStream,
                                                   eVersion,
                                                   DoNothingCSSParseExceptionHandler.getInstance ());
      return aNode != null;
    }
    finally
    {
      StreamUtils.close (aReader);
    }
  }

  @Nullable
  public static CSSDeclarationList readFromString (@Nonnull final String sCSS, @Nonnull final ECSSVersion eVersion)
  {
    return readFromReader (new NonBlockingStringReader (sCSS), eVersion, null);
  }

  @Nullable
  public static CSSDeclarationList readFromString (@Nonnull final String sCSS,
                                                   @Nonnull final ECSSVersion eVersion,
                                                   @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromReader (new NonBlockingStringReader (sCSS), eVersion, aCustomExceptionHandler);
  }

  @Nullable
  public static CSSDeclarationList readFromFile (@Nonnull final File aFile,
                                                 @Nonnull final String sCharset,
                                                 @Nonnull final ECSSVersion eVersion)
  {
    return readFromFile (aFile, sCharset, eVersion, null);
  }

  @Nullable
  public static CSSDeclarationList readFromFile (@Nonnull final File aFile,
                                                 @Nonnull final Charset aCharset,
                                                 @Nonnull final ECSSVersion eVersion)
  {
    return readFromFile (aFile, aCharset, eVersion, null);
  }

  @Nullable
  @Deprecated
  public static CSSDeclarationList readFromFile (@Nonnull final File aFile,
                                                 @Nonnull final String sCharset,
                                                 @Nonnull final ECSSVersion eVersion,
                                                 @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromReader (new FileSystemResource (aFile).getReader (sCharset), eVersion, aCustomExceptionHandler);
  }

  @Nullable
  public static CSSDeclarationList readFromFile (@Nonnull final File aFile,
                                                 @Nonnull final Charset aCharset,
                                                 @Nonnull final ECSSVersion eVersion,
                                                 @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromReader (new FileSystemResource (aFile).getReader (aCharset), eVersion, aCustomExceptionHandler);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}.
   * 
   * @param aISP
   *        The input stream provider to use. May not be <code>null</code>.
   * @param sCharset
   *        The charset name to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CSSDeclarationList readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                   @Nonnull final String sCharset,
                                                   @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (aISP, sCharset, eVersion, null);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}.
   * 
   * @param aISP
   *        The input stream provider to use. May not be <code>null</code>.
   * @param aCharset
   *        The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CSSDeclarationList readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                   @Nonnull final Charset aCharset,
                                                   @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (aISP, aCharset, eVersion, null);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}.
   * 
   * @param aISP
   *        The input stream provider to use. May not be <code>null</code>.
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
  @Deprecated
  public static CSSDeclarationList readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                   @Nonnull final String sCharset,
                                                   @Nonnull final ECSSVersion eVersion,
                                                   @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");

    final InputStream aIS = aISP.getInputStream ();
    if (aIS == null)
      return null;
    return readFromReader (StreamUtils.createReader (aIS, sCharset), eVersion, aCustomExceptionHandler);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}.
   * 
   * @param aISP
   *        The input stream provider to use. May not be <code>null</code>.
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
  public static CSSDeclarationList readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                   @Nonnull final Charset aCharset,
                                                   @Nonnull final ECSSVersion eVersion,
                                                   @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");

    final InputStream aIS = aISP.getInputStream ();
    if (aIS == null)
      return null;
    return readFromReader (StreamUtils.createReader (aIS, aCharset), eVersion, aCustomExceptionHandler);
  }

  /**
   * Read the CSS from the passed {@link Reader}.
   * 
   * @param aReader
   *        The reader to use. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CSSDeclarationList readFromReader (@Nonnull @WillClose final Reader aReader,
                                                   @Nonnull final ECSSVersion eVersion)
  {
    return readFromReader (aReader, eVersion, null);
  }

  /**
   * Read the CSS from the passed {@link Reader}.
   * 
   * @param aReader
   *        The reader to use. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aCustomExceptionHandler
   *        An optional custom exception handler that can be used to collect the
   *        parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CSSDeclarationList readFromReader (@Nonnull @WillClose final Reader aReader,
                                                   @Nonnull final ECSSVersion eVersion,
                                                   @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    if (aReader == null)
      throw new NullPointerException ("reader");
    if (eVersion == null)
      throw new NullPointerException ("version");

    try
    {
      final JavaCharStream aCharStream = new JavaCharStream (aReader);
      final CSSNode aNode = _readStyleDeclaration (aCharStream, eVersion, aCustomExceptionHandler);

      // Failed to interpret content as CSS?
      if (aNode == null)
        return null;

      // Convert the AST to a domain object
      return CSSHandler.readDeclarationListFromNode (eVersion, aNode);
    }
    finally
    {
      StreamUtils.close (aReader);
    }
  }
}
