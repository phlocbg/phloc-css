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
package com.phloc.css.handler;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.io.IInputStreamProvider;
import com.phloc.commons.io.IReadableResource;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.commons.io.streamprovider.StringInputStreamProvider;
import com.phloc.commons.io.streams.NonBlockingStringReader;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.CharStream;
import com.phloc.css.parser.JavaCharStream;
import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.ParseUtils;
import com.phloc.css.parser.ParserCSS21;
import com.phloc.css.parser.ParserCSS21TokenManager;
import com.phloc.css.parser.ParserCSS30;
import com.phloc.css.parser.ParserCSS30TokenManager;

/**
 * This is the central class for reading and parsing CSS from an input stream.
 * 
 * @author philip
 */
public final class CSSHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSHandler.class);

  private CSSHandler ()
  {}

  @Nullable
  private static CSSNode _readFromStream (@Nonnull final CharStream aStream, @Nonnull final ECSSVersion eVersion)
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
          s_aLogger.error ("Failed to parse CSS 3.0 definition: " + ex.getMessage ());
          return null;
        }
      }
      default:
        throw new IllegalArgumentException ("Unsupported CSS version " + eVersion);
    }
  }

  public static boolean isValidCSS (@Nonnull final File aFile,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return isValidCSS (new FileSystemResource (aFile), aCharset, eVersion);
  }

  public static boolean isValidCSS (@Nonnull final File aFile,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return isValidCSS (new FileSystemResource (aFile), sCharset, eVersion);
  }

  public static boolean isValidCSS (@Nonnull final IReadableResource aRes,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    final Charset aCharset = CharsetManager.getCharsetFromName (sCharset);
    if (aCharset == null)
      throw new IllegalArgumentException ("Illegal charset '" + sCharset + "'");
    return isValidCSS (aRes, aCharset, eVersion);
  }

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
   *          The input stream to use. May not be <code>null</code>.
   * @param sCharset
   *          The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version
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
   *          The input stream to use. May not be <code>null</code>.
   * @param aCharset
   *          The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version
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
   * Check if the passed input stream can be resembled to valid CSS content.
   * This is accomplished by fully parsing the CSS file each time the method is
   * called. This is similar to calling
   * {@link #readFromStream(IInputStreamProvider, Charset, ECSSVersion)} and
   * checking for a non-<code>null</code> result.
   * 
   * @param aReader
   *          The reader to use. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version
   */
  public static boolean isValidCSS (@Nonnull @WillClose final Reader aReader, @Nonnull final ECSSVersion eVersion)
  {
    if (aReader == null)
      throw new NullPointerException ("reader");
    if (eVersion == null)
      throw new NullPointerException ("version");

    try
    {
      return _readFromStream (new JavaCharStream (aReader), eVersion) != null;
    }
    finally
    {
      StreamUtils.close (aReader);
    }
  }

  /**
   * Check if the passed String can be resembled to valid CSS content. This is
   * accomplished by fully parsing the CSS file each time the method is called.
   * This is similar to calling
   * {@link #readFromString(String, Charset, ECSSVersion)} and checking for a
   * non-<code>null</code> result.
   * 
   * @param sCSS
   *          The CSS version to use. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version
   */
  public static boolean isValidCSS (@Nonnull final String sCSS, @Nonnull final ECSSVersion eVersion)
  {
    if (sCSS == null)
      throw new NullPointerException ("reader");

    return isValidCSS (new NonBlockingStringReader (sCSS), eVersion);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, sCharset), sCharset, eVersion);
  }

  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (new StringInputStreamProvider (sCSS, aCharset), aCharset, eVersion);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (new FileSystemResource (aFile), sCharset, eVersion);
  }

  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return readFromStream (new FileSystemResource (aFile), aCharset, eVersion);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}. If the CSS
   * contains an explicit charset, the whole CSS is parsed again, with the
   * charset found inside the file, so the passed {@link IInputStreamProvider}
   * must be able to create a new input stream on second invocation!
   * 
   * @param aISP
   *          The input stream provider to use. Must be able to create new input
   *          streams on every invocation, in case an explicit charset node was
   *          found. May not be <code>null</code>.
   * @param sCharset
   *          The charset name to be used. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    final Charset aCharset = CharsetManager.getCharsetFromName (sCharset);
    if (aCharset == null)
      throw new IllegalArgumentException ("Failed to resolve charset '" + sCharset + "'");
    return readFromStream (aISP, aCharset, eVersion);
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}. If the CSS
   * contains an explicit charset, the whole CSS is parsed again, with the
   * charset found inside the file, so the passed {@link IInputStreamProvider}
   * must be able to create a new input stream on second invocation!
   * 
   * @param aISP
   *          The input stream provider to use. Must be able to create new input
   *          streams on every invocation, in case an explicit charset node was
   *          found. May not be <code>null</code>.
   * @param aCharset
   *          The charset to be used. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");
    if (aCharset == null)
      throw new NullPointerException ("charset");
    if (eVersion == null)
      throw new NullPointerException ("version");

    CSSNode aNode = null;
    InputStream aIS = aISP.getInputStream ();
    if (aIS != null)
      try
      {
        aNode = _readFromStream (new JavaCharStream (aIS, aCharset), eVersion);
      }
      finally
      {
        StreamUtils.close (aIS);
      }

    if (aNode != null && aNode.jjtGetNumChildren () > 0)
    {
      // Is the first node a charset node?
      final CSSNode aFirstNode = aNode.jjtGetChild (0);
      if (ECSSNodeType.CHARSET.isNode (aFirstNode, eVersion))
      {
        // A charset was been specified -> re-read with that charset
        final String sCSSCharset = ParseUtils.extractStringValue (aFirstNode.getText ());
        s_aLogger.info ("Reading CSS definition again with explicit charset '" + sCSSCharset + "'");
        try
        {
          aIS = aISP.getInputStream ();
          aNode = _readFromStream (new JavaCharStream (aIS, sCSSCharset), eVersion);
        }
        finally
        {
          StreamUtils.close (aIS);
        }
      }
    }

    if (aNode == null)
    {
      // Failed to interprete content as CSS
      return null;
    }

    return readFromNode (eVersion, aNode);
  }

  /**
   * Create a {@link CascadingStyleSheet} object from a parsed object.
   * 
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @param aNode
   *          The parsed CSS object to read. May not be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @Nonnull
  public static CascadingStyleSheet readFromNode (@Nonnull final ECSSVersion eVersion, @Nonnull final CSSNode aNode)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    if (aNode == null)
      throw new NullPointerException ("node");
    if (!ECSSNodeType.ROOT.isNode (aNode, eVersion))
      throw new IllegalArgumentException ("Passed node is not a root node!");

    return new CSSNodeToDomainObject (eVersion).createFromNode (aNode);
  }
}
