package com.phloc.css.utils;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.mime.EMimeQuoting;
import com.phloc.commons.mime.MimeType;
import com.phloc.commons.mime.MimeTypeParser;
import com.phloc.commons.string.StringHelper;

/**
 * This class represents a single CSS data URL (RFC 2397)
 * 
 * @author Philip Helger
 */
public class CSSDataURL implements Serializable
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSDataURL.class);

  public CSSDataURL ()
  {}

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

    // comma is a special character and must be quoted in MIME type parameters
    final int nIndexComma = sRest.indexOf (',');
    if (nIndexComma < 0)
    {
      s_aLogger.warn ("Data URL is missing comma: '" + sRest + "'");
      return null;
    }
    final int nIndexBase64 = sRest.indexOf (";base64");

    int nMIMETypeEnd;
    if (nIndexBase64 >= 0)
    {
      // We have Base64 and Comma
      if (nIndexBase64 < nIndexComma)
      {
        // Base64 before comma - valid
        nMIMETypeEnd = nIndexBase64;
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

    final String sMimeType = sRest.substring (0, nMIMETypeEnd).trim ();
    final MimeType aMimeType = MimeTypeParser.createFromString (sMimeType, EMimeQuoting.URL_ESCAPE);
    System.out.println ("'" + sMimeType + "' ==> " + aMimeType);

    final CSSDataURL ret = new CSSDataURL ();
    // TODO
    return ret;
  }
}
