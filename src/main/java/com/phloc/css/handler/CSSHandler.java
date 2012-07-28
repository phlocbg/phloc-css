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
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.IInputStreamProvider;
import com.phloc.commons.io.IReadableResource;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.CharStream;
import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.ParserCSS21;
import com.phloc.css.parser.ParserCSS21TokenManager;
import com.phloc.css.parser.ParserCSS30;
import com.phloc.css.parser.ParserCSS30TokenManager;
import com.phloc.css.reader.CSSReader;

/**
 * This is the central class for reading and parsing CSS from an input stream.
 * 
 * @author philip
 */
@Immutable
public final class CSSHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSHandler.class);

  private CSSHandler ()
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

  @Deprecated
  public static boolean isValidCSS (@Nonnull final File aFile,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aFile, aCharset, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull final File aFile,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aFile, sCharset, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull final IReadableResource aRes,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aRes, sCharset, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull final IReadableResource aRes,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aRes, aCharset, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull @WillClose final InputStream aIS,
                                    @Nonnull final String sCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aIS, sCharset, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull @WillClose final InputStream aIS,
                                    @Nonnull final Charset aCharset,
                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aIS, aCharset, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull @WillClose final Reader aReader, @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (aReader, eVersion);
  }

  @Deprecated
  public static boolean isValidCSS (@Nonnull final String sCSS, @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.isValidCSS (sCSS, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.readFromString (sCSS, sCharset, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.readFromString (sCSS, aCharset, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return CSSReader.readFromString (sCSS, sCharset, eVersion, aCustomExceptionHandler);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromString (@Nonnull final String sCSS,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return CSSReader.readFromString (sCSS, aCharset, eVersion, aCustomExceptionHandler);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.readFromFile (aFile, sCharset, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.readFromFile (aFile, aCharset, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final String sCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return CSSReader.readFromFile (aFile, sCharset, eVersion, aCustomExceptionHandler);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromFile (@Nonnull final File aFile,
                                                  @Nonnull final Charset aCharset,
                                                  @Nonnull final ECSSVersion eVersion,
                                                  @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return CSSReader.readFromFile (aFile, aCharset, eVersion, aCustomExceptionHandler);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.readFromStream (aISP, sCharset, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    return CSSReader.readFromStream (aISP, aCharset, eVersion);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final String sCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return CSSReader.readFromStream (aISP, sCharset, eVersion, aCustomExceptionHandler);
  }

  @Deprecated
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final Charset aCharset,
                                                    @Nonnull final ECSSVersion eVersion,
                                                    @Nullable final ICSSParseExceptionHandler aCustomExceptionHandler)
  {
    return CSSReader.readFromStream (aISP, aCharset, eVersion, aCustomExceptionHandler);
  }

  /**
   * Create a {@link CascadingStyleSheet} object from a parsed object.
   * 
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aNode
   *        The parsed CSS object to read. May not be <code>null</code>.
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
