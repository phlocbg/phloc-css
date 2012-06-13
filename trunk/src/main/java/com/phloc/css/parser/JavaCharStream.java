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
package com.phloc.css.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.phloc.commons.io.streams.NonBlockingStringReader;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.commons.string.StringHelper;

/**
 * An implementation of interface CharStream, where the stream is assumed to
 * contain only ASCII characters (with java-like unicode escape processing).
 */
@edu.umd.cs.findbugs.annotations.SuppressWarnings ("NM_METHOD_NAMING_CONVENTION")
public final class JavaCharStream implements CharStream
{
  private static final int TAB_SIZE = 8;

  private final Reader m_aReader;
  private int m_nLine;
  private int m_nColumn;
  private int m_nAvailable;
  private int m_nBufsize;
  private char [] m_aBuffer;
  private int [] m_aBufLine;
  private int [] m_aBufColumn;
  private char [] m_aNextCharBuf;

  private boolean m_bPrevCharIsCR = false;
  private boolean m_bPrevCharIsLF = false;
  private int m_nTokenBegin = 0;
  private int m_nInBuf = 0;
  private int m_nMaxNextCharInd = 0;
  private int m_nNextCharInd = -1;
  /** Position in buffer. */
  private int m_nBufpos = -1;

  public JavaCharStream (@Nonnull final InputStream aIS, @Nonnull final String sCharset)
  {
    this (StreamUtils.createReader (aIS, sCharset), 1, 1, 4096);
  }

  public JavaCharStream (@Nonnull final InputStream aIS, @Nonnull final Charset aCharset)
  {
    this (StreamUtils.createReader (aIS, aCharset), 1, 1, 4096);
  }

  public JavaCharStream (@Nonnull final String sCSS)
  {
    this (new NonBlockingStringReader (sCSS), 1, 1, 4096);
  }

  public JavaCharStream (@Nonnull final Reader aReader)
  {
    this (aReader, 1, 1, 4096);
  }

  /** Constructor. */
  private JavaCharStream (@Nonnull final Reader aReader,
                          @Nonnegative final int nStartLine,
                          @Nonnegative final int nStartColumn,
                          @Nonnegative final int nBufferSize)
  {
    if (aReader == null)
      throw new NullPointerException ("reader");
    if (nStartLine < 0)
      throw new IllegalArgumentException ("startLine to small: " + nStartLine);
    if (nStartColumn < 0)
      throw new IllegalArgumentException ("startColumn to small: " + nStartColumn);
    if (nBufferSize < 0)
      throw new IllegalArgumentException ("bufferSize to small: " + nBufferSize);
    m_aReader = aReader;
    m_nLine = nStartLine;
    m_nColumn = nStartColumn - 1;

    m_nAvailable = m_nBufsize = nBufferSize;
    m_aBuffer = new char [nBufferSize];
    m_aBufLine = new int [nBufferSize];
    m_aBufColumn = new int [nBufferSize];
    m_aNextCharBuf = new char [4096];
  }

  private void _expandBuff (final boolean bWrapAround)
  {
    final char [] aNewBuffer = new char [m_nBufsize + 2048];
    final int [] aNewBufLine = new int [m_nBufsize + 2048];
    final int [] newbufcolumn = new int [m_nBufsize + 2048];

    try
    {
      if (bWrapAround)
      {
        System.arraycopy (m_aBuffer, m_nTokenBegin, aNewBuffer, 0, m_nBufsize - m_nTokenBegin);
        System.arraycopy (m_aBuffer, 0, aNewBuffer, m_nBufsize - m_nTokenBegin, m_nBufpos);
        m_aBuffer = aNewBuffer;

        System.arraycopy (m_aBufLine, m_nTokenBegin, aNewBufLine, 0, m_nBufsize - m_nTokenBegin);
        System.arraycopy (m_aBufLine, 0, aNewBufLine, m_nBufsize - m_nTokenBegin, m_nBufpos);
        m_aBufLine = aNewBufLine;

        System.arraycopy (m_aBufColumn, m_nTokenBegin, newbufcolumn, 0, m_nBufsize - m_nTokenBegin);
        System.arraycopy (m_aBufColumn, 0, newbufcolumn, m_nBufsize - m_nTokenBegin, m_nBufpos);
        m_aBufColumn = newbufcolumn;

        m_nBufpos += (m_nBufsize - m_nTokenBegin);
      }
      else
      {
        System.arraycopy (m_aBuffer, m_nTokenBegin, aNewBuffer, 0, m_nBufsize - m_nTokenBegin);
        m_aBuffer = aNewBuffer;

        System.arraycopy (m_aBufLine, m_nTokenBegin, aNewBufLine, 0, m_nBufsize - m_nTokenBegin);
        m_aBufLine = aNewBufLine;

        System.arraycopy (m_aBufColumn, m_nTokenBegin, newbufcolumn, 0, m_nBufsize - m_nTokenBegin);
        m_aBufColumn = newbufcolumn;

        m_nBufpos -= m_nTokenBegin;
      }
    }
    catch (final Throwable t)
    {
      throw new Error (t.getMessage ());
    }

    m_nAvailable = (m_nBufsize += 2048);
    m_nTokenBegin = 0;
  }

  private void _fillBuff () throws IOException
  {
    int i;
    if (m_nMaxNextCharInd == 4096)
      m_nMaxNextCharInd = m_nNextCharInd = 0;

    try
    {
      if ((i = m_aReader.read (m_aNextCharBuf, m_nMaxNextCharInd, 4096 - m_nMaxNextCharInd)) == -1)
      {
        m_aReader.close ();
        throw new IOException ();
      }
      m_nMaxNextCharInd += i;
      return;
    }
    catch (final IOException e)
    {
      if (m_nBufpos != 0)
      {
        --m_nBufpos;
        backup (0);
      }
      else
      {
        m_aBufLine[m_nBufpos] = m_nLine;
        m_aBufColumn[m_nBufpos] = m_nColumn;
      }
      throw e;
    }
  }

  private char _readByte () throws IOException
  {
    if (++m_nNextCharInd >= m_nMaxNextCharInd)
      _fillBuff ();

    return m_aNextCharBuf[m_nNextCharInd];
  }

  /** @return starting character for token. */
  public char BeginToken () throws IOException
  {
    if (m_nInBuf > 0)
    {
      --m_nInBuf;

      if (++m_nBufpos == m_nBufsize)
        m_nBufpos = 0;

      m_nTokenBegin = m_nBufpos;
      return m_aBuffer[m_nBufpos];
    }

    m_nTokenBegin = 0;
    m_nBufpos = -1;

    return readChar ();
  }

  private void _adjustBuffSize ()
  {
    if (m_nAvailable == m_nBufsize)
    {
      if (m_nTokenBegin > 2048)
      {
        m_nBufpos = 0;
        m_nAvailable = m_nTokenBegin;
      }
      else
        _expandBuff (false);
    }
    else
      if (m_nAvailable > m_nTokenBegin)
        m_nAvailable = m_nBufsize;
      else
        if ((m_nTokenBegin - m_nAvailable) < 2048)
          _expandBuff (true);
        else
          m_nAvailable = m_nTokenBegin;
  }

  private void _updateLineColumn (final char c)
  {
    m_nColumn++;

    if (m_bPrevCharIsLF)
    {
      m_bPrevCharIsLF = false;
      m_nLine += (m_nColumn = 1);
    }
    else
      if (m_bPrevCharIsCR)
      {
        m_bPrevCharIsCR = false;
        if (c == '\n')
          m_bPrevCharIsLF = true;
        else
          m_nLine += (m_nColumn = 1);
      }

    switch (c)
    {
      case '\r':
        m_bPrevCharIsCR = true;
        break;
      case '\n':
        m_bPrevCharIsLF = true;
        break;
      case '\t':
        m_nColumn--;
        m_nColumn += (TAB_SIZE - (m_nColumn % TAB_SIZE));
        break;
      default:
        break;
    }

    m_aBufLine[m_nBufpos] = m_nLine;
    m_aBufColumn[m_nBufpos] = m_nColumn;
  }

  private static int _hexval (final char c) throws IOException
  {
    final int ret = StringHelper.getHexValue (c);
    if (ret < 0)
      throw new IOException ("Illegal hex char '" + c + "'");
    return ret;
  }

  /** Read a character. */
  public char readChar () throws IOException
  {
    if (m_nInBuf > 0)
    {
      --m_nInBuf;
      if (++m_nBufpos == m_nBufsize)
        m_nBufpos = 0;
      return m_aBuffer[m_nBufpos];
    }

    if (++m_nBufpos == m_nAvailable)
      _adjustBuffSize ();

    char c;
    if ((m_aBuffer[m_nBufpos] = c = _readByte ()) == '\\')
    {
      _updateLineColumn (c);

      int backSlashCnt = 1;

      for (;;) // Read all the backslashes
      {
        if (++m_nBufpos == m_nAvailable)
          _adjustBuffSize ();

        try
        {
          if ((m_aBuffer[m_nBufpos] = c = _readByte ()) != '\\')
          {
            _updateLineColumn (c);
            // found a non-backslash char.
            if ((c == 'u') && ((backSlashCnt & 1) == 1))
            {
              if (--m_nBufpos < 0)
                m_nBufpos = m_nBufsize - 1;

              break;
            }

            backup (backSlashCnt);
            return '\\';
          }
        }
        catch (final IOException e)
        {
          // We are returning one backslash so we should only backup (count-1)
          if (backSlashCnt > 1)
            backup (backSlashCnt - 1);

          return '\\';
        }

        _updateLineColumn (c);
        backSlashCnt++;
      }

      // Here, we have seen an odd number of backslash's followed by a 'u'
      try
      {
        while ((c = _readByte ()) == 'u')
          ++m_nColumn;

        m_aBuffer[m_nBufpos] = c = (char) (_hexval (c) << 12 |
                                           _hexval (_readByte ()) << 8 |
                                           _hexval (_readByte ()) << 4 | _hexval (_readByte ()));
        m_nColumn += 4;
      }
      catch (final IOException e)
      {
        throw new Error ("Invalid escape character at line " + m_nLine + " column " + m_nColumn + ".");
      }

      if (backSlashCnt == 1)
        return c;

      backup (backSlashCnt - 1);
      return '\\';
    }

    _updateLineColumn (c);
    return c;
  }

  @Deprecated
  public int getColumn ()
  {
    return getEndColumn ();
  }

  @Deprecated
  public int getLine ()
  {
    return getEndLine ();
  }

  /** Get end column. */
  public int getEndColumn ()
  {
    return m_aBufColumn[m_nBufpos];
  }

  /** Get end line. */
  public int getEndLine ()
  {
    return m_aBufLine[m_nBufpos];
  }

  /** @return column of token start */
  public int getBeginColumn ()
  {
    return m_aBufColumn[m_nTokenBegin];
  }

  /** @return line number of token start */
  public int getBeginLine ()
  {
    return m_aBufLine[m_nTokenBegin];
  }

  /** Retreat. */
  public void backup (final int nAmount)
  {
    m_nInBuf += nAmount;
    if ((m_nBufpos -= nAmount) < 0)
      m_nBufpos += m_nBufsize;
  }

  /** @return token image as String */
  public String GetImage ()
  {
    if (m_nBufpos >= m_nTokenBegin)
      return new String (m_aBuffer, m_nTokenBegin, m_nBufpos - m_nTokenBegin + 1);

    return new String (m_aBuffer, m_nTokenBegin, m_nBufsize - m_nTokenBegin) + new String (m_aBuffer, 0, m_nBufpos + 1);
  }

  /** @return suffix */
  public char [] GetSuffix (final int len)
  {
    final char [] ret = new char [len];

    if ((m_nBufpos + 1) >= len)
      System.arraycopy (m_aBuffer, m_nBufpos - len + 1, ret, 0, len);
    else
    {
      System.arraycopy (m_aBuffer, m_nBufsize - (len - m_nBufpos - 1), ret, 0, len - m_nBufpos - 1);
      System.arraycopy (m_aBuffer, 0, ret, len - m_nBufpos - 1, m_nBufpos + 1);
    }

    return ret;
  }

  /** Set buffers back to null when finished. */
  public void Done ()
  {
    m_aNextCharBuf = null;
    m_aBuffer = null;
    m_aBufLine = null;
    m_aBufColumn = null;
  }
}
