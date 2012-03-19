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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.IInputStreamProvider;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.parser.CSSNode;
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
  private static CSSNode _readFromStream (@Nonnull final JavaCharStream aStream, @Nonnull final ECSSVersion eVersion)
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

  public static boolean isValidCSS (@Nonnull final IInputStreamProvider aISP, @Nonnull final ECSSVersion eVersion)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");
    if (eVersion == null)
      throw new NullPointerException ("version");

    final InputStream aIS = aISP.getInputStream ();
    if (aIS == null)
    {
      s_aLogger.warn ("Failed to open CSS input stream " + aISP);
      return false;
    }
    return isValidCSS (aIS, eVersion);
  }

  /**
   * Check if the passed input stream can be resembled to valid CSS content.
   * This is accomplished by fully parsing the CSS file each time the method is
   * called. This is similar to calling
   * {@link #readFromStream(IInputStreamProvider, ECSSVersion)} and checking for
   * a non-<code>null</code> result.
   * 
   * @param aIS
   *          The input stream to use. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>true</code> if the CSS is valid according to the version
   */
  public static boolean isValidCSS (@Nonnull @WillClose final InputStream aIS, @Nonnull final ECSSVersion eVersion)
  {
    if (aIS == null)
      throw new NullPointerException ("inputStream");
    if (eVersion == null)
      throw new NullPointerException ("version");

    try
    {
      return _readFromStream (new JavaCharStream (aIS), eVersion) != null;
    }
    finally
    {
      StreamUtils.close (aIS);
    }
  }

  /**
   * Read the CSS from the passed {@link IInputStreamProvider}.
   * 
   * @param aISP
   *          The input stream provider to use. May not be <code>null</code>.
   * @param eVersion
   *          The CSS version to use. May not be <code>null</code>.
   * @return <code>null</code> if reading failed, the CSS declarations
   *         otherwise.
   */
  @Nullable
  public static CascadingStyleSheet readFromStream (@Nonnull final IInputStreamProvider aISP,
                                                    @Nonnull final ECSSVersion eVersion)
  {
    if (aISP == null)
      throw new NullPointerException ("inputStreamProvider");
    if (eVersion == null)
      throw new NullPointerException ("version");

    CSSNode aNode = null;
    InputStream aIS = aISP.getInputStream ();
    if (aIS != null)
      try
      {
        aNode = _readFromStream (new JavaCharStream (aIS), eVersion);
      }
      finally
      {
        StreamUtils.close (aIS);
      }
    if (aNode != null)
    {
      // Is the first node a charset node?
      if (aNode.jjtGetNumChildren () > 0 &&
          aNode.jjtGetChild (0).getNodeType () == ECSSNodeType.CHARSET.getNodeType (eVersion))
      {
        // A charset was been specified -> re-read with that charset
        final String sCharset = ParseUtils.extractStringValue (aNode.jjtGetChild (0).getText ());
        s_aLogger.info ("Reading CSS definition with explicit charset '" + sCharset + "'");
        try
        {
          aIS = aISP.getInputStream ();
          aNode = _readFromStream (new JavaCharStream (aIS, sCharset), eVersion);
        }
        catch (final UnsupportedEncodingException ex)
        {
          // Keep original node!
          s_aLogger.error ("Failed to read CSS with charset '" + sCharset + "'", ex);
        }
        finally
        {
          StreamUtils.close (aIS);
        }
      }
    }

    return aNode == null ? null : readFromNode (eVersion, aNode);
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
    if (aNode.getNodeType () != ECSSNodeType.ROOT.getNodeType (eVersion))
      throw new IllegalArgumentException ("Passed node is not a root node!");

    return new CSSNodeToDomainObject (eVersion).createFromNode (aNode);
  }
}
