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
package com.phloc.css;

import java.io.Serializable;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Defines the source location of a single token when reading CSS from a stream.
 * 
 * @author Philip Helger
 */
@Immutable
public class CSSSourceArea implements Serializable
{
  private final int m_nBeginLineNumber;
  private final int m_nBeginColumnNumber;
  private final int m_nEndLineNumber;
  private final int m_nEndColumnNumber;

  public CSSSourceArea (final int nBeginLineNumber,
                        final int nBeginColumnNumber,
                        final int nEndLineNumber,
                        final int nEndColumnNumber)
  {
    m_nBeginLineNumber = nBeginLineNumber;
    m_nBeginColumnNumber = nBeginColumnNumber;
    m_nEndLineNumber = nEndLineNumber;
    m_nEndColumnNumber = nEndColumnNumber;
  }

  /**
   * @return The line number where the token begins (incl.). May be -1 if not
   *         such token is available.
   */
  @CheckForSigned
  public int getTokenBeginLineNumber ()
  {
    return m_nBeginLineNumber;
  }

  /**
   * @return The column number where the token begins (incl.). May be -1 if not
   *         such token is available.
   */
  @CheckForSigned
  public int getTokenBeginColumnNumber ()
  {
    return m_nBeginColumnNumber;
  }

  /**
   * @return The line number where the token ends (incl.). May be -1 if not such
   *         token is available.
   */
  @CheckForSigned
  public int getTokenEndLineNumber ()
  {
    return m_nEndLineNumber;
  }

  /**
   * @return The column number where the token ends (incl.). May be -1 if not
   *         such token is available.
   */
  @CheckForSigned
  public int getTokenEndColumnNumber ()
  {
    return m_nEndColumnNumber;
  }

  /**
   * @return The location of the token as a simple string. Never
   *         <code>null</code>. Example: <code>(1:2/3:4)</code>
   */
  @Nonnull
  @Nonempty
  public String getTokenLocationAsString ()
  {
    return "(" +
           m_nBeginLineNumber +
           ":" +
           m_nBeginColumnNumber +
           "/" +
           m_nEndLineNumber +
           ":" +
           m_nEndColumnNumber +
           ")";
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSourceArea))
      return false;
    final CSSSourceArea rhs = (CSSSourceArea) o;
    return m_nBeginLineNumber == rhs.m_nBeginLineNumber &&
           m_nBeginColumnNumber == rhs.m_nBeginColumnNumber &&
           m_nEndLineNumber == rhs.m_nEndLineNumber &&
           m_nEndColumnNumber == rhs.m_nEndColumnNumber;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_nBeginLineNumber)
                                       .append (m_nBeginColumnNumber)
                                       .append (m_nEndLineNumber)
                                       .append (m_nEndColumnNumber)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("beginLine", m_nBeginLineNumber)
                                       .append ("beginColumn", m_nBeginColumnNumber)
                                       .append ("endLine", m_nEndLineNumber)
                                       .append ("endColumn", m_nEndColumnNumber)
                                       .toString ();
  }
}
