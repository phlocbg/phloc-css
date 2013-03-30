package com.phloc.css;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Defines the source location of an object when reading CSS from a stream.
 * 
 * @author philip
 */
@Immutable
public final class CSSSourceLocation
{
  private final int m_nBeginLineNumber;
  private final int m_nBeginColumnNumber;
  private final int m_nEndLineNumber;
  private final int m_nEndColumnNumber;

  public CSSSourceLocation (final int nBeginLineNumber,
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
   * @return The line number where the element begins (incl.)
   */
  public int getBeginLineNumber ()
  {
    return m_nBeginLineNumber;
  }

  /**
   * @return The column number where the element begins (incl.)
   */
  public int getBeginColumnNumber ()
  {
    return m_nBeginColumnNumber;
  }

  /**
   * @return The line number where the element ends (incl.)
   */
  public int getEndLineNumber ()
  {
    return m_nEndLineNumber;
  }

  /**
   * @return The column number where the element end (incl.)
   */
  public int getEndColumnNumber ()
  {
    return m_nEndColumnNumber;
  }

  @Nonnull
  @Nonempty
  public String getAsString ()
  {
    return m_nBeginLineNumber + ":" + m_nBeginColumnNumber + "-" + m_nEndLineNumber + ":" + m_nEndColumnNumber;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSourceLocation))
      return false;
    final CSSSourceLocation rhs = (CSSSourceLocation) o;
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
