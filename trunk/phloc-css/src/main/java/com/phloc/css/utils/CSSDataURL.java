package com.phloc.css.utils;

import java.io.Serializable;
import java.nio.charset.Charset;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.annotations.WorkInProgress;
import com.phloc.commons.base64.Base64Helper;
import com.phloc.commons.charset.CCharset;
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.mime.CMimeType;
import com.phloc.commons.mime.EMimeQuoting;
import com.phloc.commons.mime.IMimeType;
import com.phloc.commons.mime.MimeType;
import com.phloc.commons.mime.MimeTypeParameter;
import com.phloc.commons.mime.MimeTypeParser;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;

/**
 * This class represents a single CSS data URL (RFC 2397)
 * 
 * @author Philip Helger
 */
@WorkInProgress
public class CSSDataURL implements Serializable
{
  /** The default charset to be used for Data URLs: US-ASCII */
  public static final Charset DEFAULT_CHARSET = CCharset.CHARSET_US_ASCII_OBJ;

  /** The default MIME type for Data URLs: text/plain;charset=US-ASCII */
  public static final IMimeType DEFAULT_MIME_TYPE = new MimeType (CMimeType.TEXT_PLAIN).addParameter (CMimeType.PARAMETER_NAME_CHARSET,
                                                                                                      DEFAULT_CHARSET.name ());

  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSDataURL.class);

  private final IMimeType m_aMimeType;
  private final boolean m_bBase64Encoded;
  private final byte [] m_aContent;

  /**
   * Default constructor. Default MIME type, no Base64 encoding and no content.
   */
  public CSSDataURL ()
  {
    this (DEFAULT_MIME_TYPE.getClone (), false, new byte [0]);
  }

  public CSSDataURL (@Nonnull final IMimeType aMimeType, final boolean bBase64Encoded, @Nonnull final byte [] aContent)
  {
    if (aMimeType == null)
      throw new NullPointerException ("MimeType");
    if (aContent == null)
      throw new NullPointerException ("Content");

    m_aMimeType = aMimeType;
    m_bBase64Encoded = bBase64Encoded;
    m_aContent = ArrayHelper.getCopy (aContent);
  }

  /**
   * @return The MIME type of the data URL. If none was specified, than the
   *         default
   */
  @Nonnull
  public IMimeType getMimeType ()
  {
    return m_aMimeType;
  }

  public boolean isBase64Encoded ()
  {
    return m_bBase64Encoded;
  }

  @Nonnegative
  public int getContentLength ()
  {
    return m_aContent.length;
  }

  @Nonnull
  @ReturnsMutableCopy
  public byte [] getContent ()
  {
    return ArrayHelper.getCopy (m_aContent);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("mimeType", m_aMimeType)
                                       .append ("base64Encoded", m_bBase64Encoded)
                                       .append ("content.length", m_aContent.length)
                                       .toString ();
  }

  /**
   * Parse a data URL into this type.
   * 
   * <pre>
   * Syntax
   *   dataurl    := "data:" [ mediatype ] [ ";base64" ] "," data
   *   mediatype  := [ type "/" subtype ] *( ";" parameter )
   *   data       := *urlchar
   *   parameter  := attribute "=" value
   * </pre>
   * 
   * @param sDataURL
   */
  @Nullable
  public static CSSDataURL parseDataURL (@Nullable final String sDataURL)
  {
    if (!CSSDataURLHelper.isDataURL (sDataURL))
      return null;

    // Skip the constant prefix
    final String sRest = StringHelper.trimStart (sDataURL.trim (), CSSDataURLHelper.PREFIX_DATA_URL);
    if (StringHelper.hasNoText (sRest))
    {
      // Plain "data:" URL - no content
      return new CSSDataURL ();
    }

    // comma is a special character and must be quoted in MIME type parameters
    final int nIndexComma = sRest.indexOf (',');
    int nIndexBase64 = sRest.indexOf (CSSDataURLHelper.BASE64_MARKER);
    boolean bBase64EncodingUsed = false;

    int nMIMETypeEnd;
    if (nIndexBase64 >= 0)
    {
      // We have Base64
      if (nIndexBase64 < nIndexComma || nIndexComma < 0)
      {
        // Base64 before comma or no comma
        // ==> check if it is a MIME type parameter name (in
        // which case it is followed by a '=' character before the comma) or if
        // it is really the base64-encoding flag (no further '=' or '=' after
        // the comma).
        while (true)
        {
          final int nIndexEquals = sRest.indexOf (CMimeType.SEPARATOR_PARAMETER_NAME_VALUE, nIndexBase64);
          if (nIndexEquals < 0 || nIndexEquals > nIndexComma || nIndexComma < 0)
          {
            // It's a real base64 indicator
            nMIMETypeEnd = nIndexBase64;
            bBase64EncodingUsed = true;
            break;
          }

          // base64 as a MIME type parameter - check for next ;base64
          nIndexBase64 = sRest.indexOf (CSSDataURLHelper.BASE64_MARKER,
                                        nIndexBase64 + CSSDataURLHelper.BASE64_MARKER.length ());
          if (nIndexBase64 < 0)
          {
            // Found no base64 encoding
            nMIMETypeEnd = nIndexComma;
            break;
          }

          // Found another base64 marker -> continue
        }
      }
      else
      {
        // Base64 as part of data!
        nMIMETypeEnd = nIndexComma;
      }
    }
    else
    {
      // No Base64 found
      nMIMETypeEnd = nIndexComma;
    }

    String sMimeType = nMIMETypeEnd < 0 ? null : sRest.substring (0, nMIMETypeEnd).trim ();
    IMimeType aMimeType;
    Charset aCharset = null;
    if (StringHelper.hasNoText (sMimeType))
    {
      // If no MIME type is specified, the default is used
      aMimeType = DEFAULT_MIME_TYPE.getClone ();
      aCharset = DEFAULT_CHARSET;
    }
    else
    {
      // A MIME type is present
      if (sMimeType.charAt (0) == CMimeType.SEPARATOR_PARAMETER)
      {
        // Weird stuff from the specs: if only ";charset=utf-8" is present than
        // text/plain should be used
        sMimeType = DEFAULT_MIME_TYPE.getAsStringWithoutParameters () + sMimeType;
      }

      // try to parse it
      aMimeType = MimeTypeParser.safeParseMimeType (sMimeType, EMimeQuoting.URL_ESCAPE);
      if (aMimeType == null)
      {
        s_aLogger.warn ("Data URL contains invalid MIME type: '" + sMimeType + "'");
        return null;
      }

      // Check if a "charset" MIME type parameter is present
      final MimeTypeParameter aCharsetParam = aMimeType.getParameterWithName (CMimeType.PARAMETER_NAME_CHARSET);
      if (aCharsetParam != null)
      {
        try
        {
          aCharset = CharsetManager.getCharsetFromName (aCharsetParam.getValue ());
        }
        catch (final IllegalArgumentException ex)
        {
          // Illegal charset
        }
        if (aCharset == null)
        {
          s_aLogger.warn ("Illegal charset '" +
                          aCharsetParam.getValue () +
                          "' contained. Defaulting to " +
                          DEFAULT_CHARSET.name ());
        }
      }
      if (aCharset == null)
        aCharset = DEFAULT_CHARSET;
    }

    // Get the main content data
    final String sContent = nIndexComma < 0 ? "" : sRest.substring (nIndexComma + 1).trim ();
    byte [] aContent = CharsetManager.getAsBytes (sContent, aCharset);

    if (bBase64EncodingUsed)
    {
      // Base64 decode the content data
      aContent = Base64Helper.safeDecode (aContent);
      if (aContent == null)
      {
        s_aLogger.warn ("Failed to decode Base64 value: " + sContent);
        return null;
      }
    }

    final CSSDataURL ret = new CSSDataURL (aMimeType, bBase64EncodingUsed, aContent);
    return ret;
  }
}
