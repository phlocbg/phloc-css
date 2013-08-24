package com.phloc.css.utils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillNotClose;
import javax.annotation.concurrent.NotThreadSafe;

import org.omg.CORBA_2_3.portable.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.IHasStringRepresentation;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.base64.Base64;
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.mime.CMimeType;
import com.phloc.commons.mime.IMimeType;
import com.phloc.commons.mime.MimeType;
import com.phloc.commons.mime.MimeTypeUtils;
import com.phloc.commons.string.ToStringGenerator;

/**
 * This class represents a single CSS data URL (RFC 2397)
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSDataURL implements IHasStringRepresentation, Serializable
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSDataURL.class);

  private final IMimeType m_aMimeType;
  private final boolean m_bBase64Encoded;
  private final byte [] m_aContent;
  private final Charset m_aCharset;
  private String m_sContent;

  @Nonnull
  public static Charset getCharsetFromMimeTypeOrDefault (@Nullable final IMimeType aMimeType)
  {
    final Charset ret = MimeTypeUtils.getCharsetFromMimeType (aMimeType);
    return ret != null ? ret : CSSDataURLHelper.DEFAULT_CHARSET;
  }

  /**
   * Default constructor. Default MIME type, no Base64 encoding and no content.
   */
  public CSSDataURL ()
  {
    this (CSSDataURLHelper.DEFAULT_MIME_TYPE.getClone (), false, new byte [0], CSSDataURLHelper.DEFAULT_CHARSET, "");
  }

  /**
   * Constructor
   * 
   * @param aMimeType
   *        The MIME type to be used. If it contains a charset, this charset
   *        will be used otherwise the default charset will be used.
   * @param bBase64Encoded
   *        <code>true</code> if the content of this data should be Base64
   *        encoded. It is recommended to set this to <code>true</code> if you
   *        have binary data like images.
   * @param aContent
   *        The content od the data URL as a byte array. May not be
   *        <code>null</code>.
   */
  public CSSDataURL (@Nonnull final IMimeType aMimeType, final boolean bBase64Encoded, @Nonnull final byte [] aContent)
  {
    this (aMimeType, bBase64Encoded, aContent, getCharsetFromMimeTypeOrDefault (aMimeType), null);
  }

  /**
   * Full constructor
   * 
   * @param aMimeType
   *        The MIME type to be used. May not be <code>null</code>. If you don't
   *        know provide the default MIME type from
   *        {@link CSSDataURLHelper#DEFAULT_MIME_TYPE}.
   * @param bBase64Encoded
   *        <code>true</code> if the data URL String representation should be
   *        Base64 encoded, <code>false</code> if not. It is recommended to set
   *        this to <code>true</code> if you have binary data like images.
   * @param aContent
   *        The content of the data URL as a byte array. May not be
   *        <code>null</code> but may be empty.
   * @param aCharset
   *        The charset to be used to encode the String. May not be
   *        <code>null</code>. The default is
   *        {@link CSSDataURLHelper#DEFAULT_CHARSET}.
   * @param sContent
   *        The String representation of the content. It must match the byte
   *        array in the specified charset. If this parameter is
   *        <code>null</code> than the String content representation is lazily
   *        created in {@link #getStringContent()}.
   */
  public CSSDataURL (@Nonnull final IMimeType aMimeType,
                     final boolean bBase64Encoded,
                     @Nonnull final byte [] aContent,
                     @Nonnull final Charset aCharset,
                     @Nullable final String sContent)
  {
    if (aMimeType == null)
      throw new NullPointerException ("MimeType");
    if (aContent == null)
      throw new NullPointerException ("Content");
    if (aCharset == null)
      throw new NullPointerException ("Charset");

    // Check if a charset is contained in the MIME type and if it matches the
    // provided charset
    final Charset aMimeTypeCharset = MimeTypeUtils.getCharsetFromMimeType (aMimeType);
    if (aMimeTypeCharset == null)
    {
      // No charset found in MIME type
      if (!aCharset.equals (CSSDataURLHelper.DEFAULT_CHARSET))
      {
        // append charset only if it is not the default charset
        m_aMimeType = ((MimeType) aMimeType.getClone ()).addParameter (CMimeType.PARAMETER_NAME_CHARSET,
                                                                       aCharset.name ());
      }
      else
      {
        // Default charset provided
        m_aMimeType = aMimeType;
      }
    }
    else
    {
      // MIME type has a charset - check if it matches the passed one
      if (!aMimeTypeCharset.equals (aCharset))
        throw new IllegalArgumentException ("The provided charset '" +
                                            aCharset.name () +
                                            "' differs from the charset in the MIME type: '" +
                                            aMimeTypeCharset.name () +
                                            "'");
      m_aMimeType = aMimeType;
    }
    m_bBase64Encoded = bBase64Encoded;
    m_aContent = ArrayHelper.getCopy (aContent);
    m_aCharset = aCharset;
    m_sContent = sContent;
  }

  /**
   * @return The MIME type of the data URL. If none was specified, than the
   *         default MIME Type {@link CSSDataURLHelper#DEFAULT_MIME_TYPE} must
   *         be used.
   */
  @Nonnull
  public IMimeType getMimeType ()
  {
    return m_aMimeType;
  }

  /**
   * @return <code>true</code> if the parsed data URL was Base64 encoded or if
   *         this data URL should be Base64 encoded.
   */
  public boolean isBase64Encoded ()
  {
    return m_bBase64Encoded;
  }

  /**
   * @return The length of the content in bytes. Always &ge; 0.
   */
  @Nonnegative
  public int getContentLength ()
  {
    return m_aContent.length;
  }

  /**
   * @return A copy of the binary data of the data URL
   */
  @Nonnull
  @ReturnsMutableCopy
  public byte [] getContent ()
  {
    return ArrayHelper.getCopy (m_aContent);
  }

  /**
   * Write all the binary content to the passed output stream.
   * 
   * @param aOS
   *        The output stream to write to. May not be <code>null</code>.
   * @throws IOException
   */
  public void writeTo (@Nonnull @WillNotClose final OutputStream aOS) throws IOException
  {
    aOS.write (m_aContent, 0, m_aContent.length);
  }

  /**
   * @return The charset to be used for String encoding. May not be
   *         <code>null</code>. The default is
   *         {@link CSSDataURLHelper#DEFAULT_CHARSET}.
   */
  @Nonnull
  public Charset getCharset ()
  {
    return m_aCharset;
  }

  /**
   * Get the data content of this Data URL as String. If no String
   * representation was provided in the constructor, than it is lazily created
   * inside this method in which case instances of this class are not
   * thread-safe. If a non-<code>null</code> String was provided in the
   * constructor, this object is immutable.
   * 
   * @return The content in a String representation using the charset of this
   *         object. Never <code>null</code>.
   */
  @Nonnull
  public String getStringContent ()
  {
    if (m_sContent == null)
      m_sContent = CharsetManager.getAsString (m_aContent, m_aCharset);
    return m_sContent;
  }

  /**
   * Get the data content of this Data URL as String in the specified charset.
   * 
   * @param aCharset
   *        The charset to be used. May not be <code>null</code>.
   * @return The content in a String representation using the provided charset.
   *         Never <code>null</code>.
   */
  @Nonnull
  public String getStringContent (@Nonnull final Charset aCharset)
  {
    return CharsetManager.getAsString (m_aContent, aCharset);
  }

  /**
   * @return The complete representation of the data URL, starting with "data:".
   *         All data is emitted, even if it is the default value.
   */
  @Nonnull
  public String getAsString ()
  {
    // Return the non-optimized version
    return getAsString (false);
  }

  /**
   * @return The complete representation of the data URL, starting with "data:".
   *         All data is emitted, even if it is the default value.
   */
  @Nonnull
  public String getAsString (final boolean bOptimizedVersion)
  {
    final StringBuilder aSB = new StringBuilder (CSSDataURLHelper.PREFIX_DATA_URL);

    if (bOptimizedVersion)
    {
      // Do not emit the default, if it is the optimized version
      if (!m_aMimeType.equals (CSSDataURLHelper.DEFAULT_MIME_TYPE))
        if (m_aMimeType.getAsStringWithoutParameters ()
                       .equals (CSSDataURLHelper.DEFAULT_MIME_TYPE.getAsStringWithoutParameters ()))
        {
          // Emit only the parameters
          aSB.append (m_aMimeType.getParametersAsString (CSSDataURLHelper.MIME_QUOTING));
        }
        else
        {
          // Non-default MIME type
          aSB.append (m_aMimeType.getAsString (CSSDataURLHelper.MIME_QUOTING));
        }
    }
    else
    {
      // Use URL escaping to quote MIME type parameter values!
      aSB.append (m_aMimeType.getAsString (CSSDataURLHelper.MIME_QUOTING));
    }

    // Base64 marker
    if (m_bBase64Encoded)
    {
      // Avoid the ";base64" if the content is empty
      if (m_aContent.length > 0 || !bOptimizedVersion)
        aSB.append (CSSDataURLHelper.BASE64_MARKER);
    }

    // Start content
    aSB.append (CSSDataURLHelper.SEPARATOR_CONTENT);
    if (m_aContent.length > 0)
    {
      if (m_bBase64Encoded)
      {
        // Add Base64 encoded String
        final byte [] aEncoded = Base64.encodeBytesToBytes (m_aContent);
        // Print the string in the specified charset
        aSB.append (CharsetManager.getAsString (aEncoded, m_aCharset));
      }
      else
      {
        // Append String as is
        aSB.append (getStringContent ());
      }
    }
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSDataURL rhs = (CSSDataURL) o;
    return m_aMimeType.equals (rhs.m_aMimeType) &&
           m_bBase64Encoded == rhs.m_bBase64Encoded &&
           EqualsUtils.equals (m_aContent, rhs.m_aContent) &&
           m_aCharset.equals (rhs.m_aCharset);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMimeType)
                                       .append (m_bBase64Encoded)
                                       .append (m_aContent)
                                       .append (m_aCharset)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("mimeType", m_aMimeType)
                                       .append ("base64Encoded", m_bBase64Encoded)
                                       .append ("content.length", m_aContent.length)
                                       .append ("charset", m_aCharset)
                                       .append ("hasStringContent", m_sContent != null)
                                       .toString ();
  }
}
