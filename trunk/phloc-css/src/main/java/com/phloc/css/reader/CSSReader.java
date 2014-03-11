/**
 * Copyright (C) 2006-2014 phloc systems
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.PresentForCodeCoverage;
import com.phloc.commons.charset.CCharset;
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.charset.EUnicodeBOM;
import com.phloc.commons.collections.ArrayHelper;
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
import com.phloc.css.reader.errorhandler.ICSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.reader.errorhandler.ThrowingCSSParseErrorHandler;

/**
 * This is the central user class for reading and parsing CSS from different
 * sources. This class reads full CSS declarations only. To read only a
 * declaration list (like from an HTML <code>&lt;style&gt;</code> attribute) the
 * {@link CSSReaderDeclarationList} is available.
 * 
 * @author Philip Helger
 */
@ThreadSafe
public final class CSSReader
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSReader.class);
  private static final ReadWriteLock s_aRWLock = new ReentrantReadWriteLock ();

  @GuardedBy ("s_aRWLock")
  private static ICSSParseErrorHandler s_aDefaultParseErrorHandler = ThrowingCSSParseErrorHandler.getInstance ();

  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final CSSReader s_aInstance = new CSSReader ();

  private CSSReader ()
  {}

  /**
   * @return The default CSS parse error handler. May be <code>null</code>. For
   *         backwards compatibility reasons this is be default an instance of
   *         {@link ThrowingCSSParseErrorHandler}.
   */
  @Nullable
  public static ICSSParseErrorHandler getDefaultParseErrorHandler ()
  {
    s_aRWLock.readLock ().lock ();
    try
    {
      return s_aDefaultParseErrorHandler;
    }
    finally
    {
      s_aRWLock.readLock ().unlock ();
    }
  }

  /**
   * Set the default CSS parse error handler.
   * 
   * @param aDefaultParseErrorHandler
   *        The new default error handler to be used. May be <code>null</code>
   *        to indicate that no special error handler should be used.
   */
  public static void setDefaultParseErrorHandler (@Nullable final ICSSParseErrorHandler aDefaultParseErrorHandler)
  {
    s_aRWLock.writeLock ().lock ();
    try
    {
      s_aDefaultParseErrorHandler = aDefaultParseErrorHandler;
    }
    finally
    {
      s_aRWLock.writeLock ().unlock ();
    }
  }

  /**
   * Main reading of the CSS
   * 
   * @param aStream
   *        The stream to read from. May not be <code>null</code>.
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aCustomErrorHandler
   *        A custom handler for recoverable errors. May be <code>null</code>.
   * @param aCustomExceptionHandler
   *        A custom handler for unrecoverable errors. May be <code>null</code>.
   * @return <code>null</code> if parsing failed with an unrecoverable error, or
   *         <code>null</code> if a recoverable error occurred and a
   *         {@link com.phloc.css.reader.errorhandler.ThrowingCSSParseErrorHandler}
   *         was used or non-<code>null</code> if parsing succeeded.
   */
  @Nullable
  private static CSSNode _readStyleSheet (@Nonnull final CharStream aStream,
                                          @Nonnull final ECSSVersion eVersion,
                                          @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
                                          @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    switch (eVersion)
    {
      case CSS21:
      {
        final ParserCSS21TokenManager aTokenHdl = new ParserCSS21TokenManager (aStream);
        final ParserCSS21 aParser = new ParserCSS21 (aTokenHdl);
        aParser.setCustomErrorHandler (aCustomErrorHandler);
        try
        {
          // Main parsing
          return aParser.styleSheet ();
        }
        catch (final ParseException ex)
        {
          if (aCustomExceptionHandler != null)
            aCustomExceptionHandler.onException (ex);
          else
            s_aLogger.error ("Failed to parse CSS 2.1 style sheet: " +
                             LoggingCSSParseErrorHandler.createLoggingStringParseError (ex));
          return null;
        }
      }
      case CSS30:
      {
        final ParserCSS30TokenManager aTokenHdl = new ParserCSS30TokenManager (aStream);
        final ParserCSS30 aParser = new ParserCSS30 (aTokenHdl);
        aParser.setCustomErrorHandler (aCustomErrorHandler);
        try
        {
          // Main parsing
          return aParser.styleSheet ();
        }
        catch (final ParseException ex)
        {
          if (aCustomExceptionHandler != null)
            aCustomExceptionHandler.onException (ex);
          else
            s_aLogger.error ("Failed to parse CSS 3.0 style sheet: " +
                             LoggingCSSParseErrorHandler.createLoggingStringParseError (ex));
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
  @Deprecated
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
  @Deprecated
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
      final CSSNode aNode = _readStyleSheet (aCharStream,
                                             eVersion,
                                             getDefaultParseErrorHandler (),
                                             DoNothingCSSParseExceptionHandler.getInstance ());
      return aNode != null;
    }
    finally
    {
      StreamUtils.close (aReader);
    }
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromString (sCSS, sCharset, eVersion, null, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromString (sCSS, aCharset, eVersion, null, null);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler)
  {
    return readFromString (sCSS, sCharset, eVersion, aCustomErrorHandler, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler)
  {
    return readFromString (sCSS, aCharset, eVersion, aCustomErrorHandler, null);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, sCharset),
                           sCharset,
                           eVersion,
                           null,
                           aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, aCharset),
                           aCharset,
                           eVersion,
                           null,
                           aCustomExceptionHandler);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, sCharset),
                           sCharset,
                           eVersion,
                           aCustomErrorHandler,
                           aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, aCharset),
                           aCharset,
                           eVersion,
                           aCustomErrorHandler,
                           aCustomExceptionHandler);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return readFromFile (aFile, sCharset, eVersion, null, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return readFromFile (aFile, aCharset, eVersion, null, null);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseErrorHandler aCustomErrorHandler)
  {
    return readFromFile (aFile, sCharset, eVersion, aCustomErrorHandler, null);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseErrorHandler aCustomErrorHandler)
  {
    return readFromFile (aFile, aCharset, eVersion, aCustomErrorHandler, null);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new FileSystemResource (aFile), sCharset, eVersion, null, aCustomExceptionHandler);
  }

  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new FileSystemResource (aFile),
                           sCharset,
                           eVersion,
                           aCustomErrorHandler,
                           aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new FileSystemResource (aFile), aCharset, eVersion, null, aCustomExceptionHandler);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (new FileSystemResource (aFile),
                           aCharset,
                           eVersion,
                           aCustomErrorHandler,
                           aCustomExceptionHandler);
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
  @Deprecated
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (aISP, sCharset, eVersion, null, null);
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
   * @param aCustomErrorHandler
   *        An optional custom error handler that can be used to collect the
   *        recoverable parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler)
  {
    return readFromStream (aISP, sCharset, eVersion, aCustomErrorHandler, null);
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
    return readFromStream (aISP, aCharset, eVersion, null, null);
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
   * @param aCustomErrorHandler
   *        An optional custom error handler that can be used to collect the
   *        recoverable parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler)
  {
    return readFromStream (aISP, aCharset, eVersion, aCustomErrorHandler, null);
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
   *        unrecoverable parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (aISP, sCharset, eVersion, null, aCustomExceptionHandler);
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
   * @param aCustomErrorHandler
   *        An optional custom error handler that can be used to collect the
   *        recoverable parsing errors. May be <code>null</code>.
   * @param aCustomExceptionHandler
   *        An optional custom exception handler that can be used to collect the
   *        unrecoverable parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  @Deprecated
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    final Charset aCharset = CharsetManager.getCharsetFromName (sCharset);
    return readFromStream (aISP, aCharset, eVersion, aCustomErrorHandler, aCustomExceptionHandler);
  }

  @Nullable
  private static InputStream _getInputStreamWithoutBOM (@Nonnull final IInputStreamProvider aISP)
  {
    // Try to open input stream
    final InputStream aIS = aISP.getInputStream ();
    if (aIS == null)
      return null;

    // Check for BOM
    final int nMaxBOMBytes = EUnicodeBOM.getMaximumByteCount ();
    final PushbackInputStream aPIS = new PushbackInputStream (aIS, nMaxBOMBytes);
    try
    {
      final byte [] aBOM = new byte [nMaxBOMBytes];
      final int nReadBOMBytes = aPIS.read (aBOM);
      if (nReadBOMBytes > 0)
      {
        // Some byte BOMs were read
        final EUnicodeBOM eBOM = EUnicodeBOM.getFromBytesOrNull (ArrayHelper.getCopy (aBOM, 0, nReadBOMBytes));
        if (eBOM == null)
          aPIS.unread (aBOM, 0, nReadBOMBytes);
      }
      return aPIS;
    }
    catch (final IOException ex)
    {
      s_aLogger.error ("Failed to determine BOM", ex);
      return null;
    }
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
    final InputStream aIS = _getInputStreamWithoutBOM (aISP);
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
   *        unrecoverable parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return readFromStream (aISP, aCharset, eVersion, null, aCustomExceptionHandler);
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
   * @param aCustomErrorHandler
   *        An optional custom error handler that can be used to collect the
   *        recoverable parsing errors. May be <code>null</code>.
   * @param aCustomExceptionHandler
   *        An optional custom exception handler that can be used to collect the
   *        unrecoverable parsing errors. May be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseErrorHandler aCustomErrorHandler,
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
      if (s_aLogger.isDebugEnabled ())
        s_aLogger.debug ("Reading CSS definition again with explicit charset '" + aDeclaredCharset.name () + "'");
      aCharsetToUse = aDeclaredCharset;
    }

    // Try to open input stream
    final InputStream aIS = _getInputStreamWithoutBOM (aISP);
    if (aIS == null)
      return null;

    try
    {
      final JavaCharStream aCharStream = new JavaCharStream (aIS, aCharsetToUse);
      // Use the default CSS parse error handler if none is provided
      final ICSSParseErrorHandler aRealErrorHandler = aCustomErrorHandler == null ? getDefaultParseErrorHandler ()
                                                                                 : aCustomErrorHandler;
      final CSSNode aNode = _readStyleSheet (aCharStream, eVersion, aRealErrorHandler, aCustomExceptionHandler);

      // Failed to interpret content as CSS?
      if (aNode == null)
        return null;

      // Convert the AST to a domain object
      return CSSHandler.readCascadingStyleSheetFromNode (eVersion, aNode);
    }
    finally
    {
      StreamUtils.close (aIS);
    }
  }
}
