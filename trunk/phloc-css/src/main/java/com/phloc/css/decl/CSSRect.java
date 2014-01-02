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
package com.phloc.css.decl;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ICSSWriteable;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.propertyvalue.CCSSValue;

/**
 * Represents a single CSS rectangle
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSRect implements ICSSWriteable
{
  private String m_sTop;
  private String m_sRight;
  private String m_sBottom;
  private String m_sLeft;

  /**
   * Copy constructor
   * 
   * @param aOther
   *        The object to copy the data from. May not be <code>null</code>.
   */
  public CSSRect (@Nonnull final CSSRect aOther)
  {
    this (aOther.getTop (), aOther.getRight (), aOther.getBottom (), aOther.getLeft ());
  }

  /**
   * Constructor
   * 
   * @param sTop
   *        Top coordinate. May neither be <code>null</code> nor empty.
   * @param sRight
   *        Tight coordinate. May neither be <code>null</code> nor empty.
   * @param sBottom
   *        Bottom coordinate. May neither be <code>null</code> nor empty.
   * @param sLeft
   *        Left coordinate. May neither be <code>null</code> nor empty.
   */
  public CSSRect (@Nonnull @Nonempty final String sTop,
                  @Nonnull @Nonempty final String sRight,
                  @Nonnull @Nonempty final String sBottom,
                  @Nonnull @Nonempty final String sLeft)
  {
    setTop (sTop);
    setRight (sRight);
    setBottom (sBottom);
    setLeft (sLeft);
  }

  /**
   * @return top part. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getTop ()
  {
    return m_sTop;
  }

  /**
   * Set the top coordinate.
   * 
   * @param sTop
   *        May neither be <code>null</code> nor empty.
   */
  public void setTop (@Nonnull @Nonempty final String sTop)
  {
    if (StringHelper.hasNoText (sTop))
      throw new IllegalArgumentException ("top");
    m_sTop = sTop;
  }

  /**
   * @return right part. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getRight ()
  {
    return m_sRight;
  }

  /**
   * Set the right coordinate.
   * 
   * @param sRight
   *        May neither be <code>null</code> nor empty.
   */
  public void setRight (@Nonnull @Nonempty final String sRight)
  {
    if (StringHelper.hasNoText (sRight))
      throw new IllegalArgumentException ("right");
    m_sRight = sRight;
  }

  /**
   * @return bottom part. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getBottom ()
  {
    return m_sBottom;
  }

  /**
   * Set the bottom coordinate.
   * 
   * @param sBottom
   *        May neither be <code>null</code> nor empty.
   */
  public void setBottom (@Nonnull @Nonempty final String sBottom)
  {
    if (StringHelper.hasNoText (sBottom))
      throw new IllegalArgumentException ("bottom");
    m_sBottom = sBottom;
  }

  /**
   * @return left part. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getLeft ()
  {
    return m_sLeft;
  }

  /**
   * Set the left coordinate.
   * 
   * @param sLeft
   *        May neither be <code>null</code> nor empty.
   */
  public void setLeft (@Nonnull @Nonempty final String sLeft)
  {
    if (StringHelper.hasNoText (sLeft))
      throw new IllegalArgumentException ("left");
    m_sLeft = sLeft;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    return CCSSValue.PREFIX_RECT_OPEN +
           m_sTop +
           ',' +
           m_sRight +
           ',' +
           m_sBottom +
           ',' +
           m_sLeft +
           CCSSValue.SUFFIX_RECT_CLOSE;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSRect))
      return false;
    final CSSRect rhs = (CSSRect) o;
    return m_sTop.equals (rhs.m_sTop) &&
           m_sRight.equals (rhs.m_sRight) &&
           m_sBottom.equals (rhs.m_sBottom) &&
           m_sLeft.equals (rhs.m_sLeft);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sTop)
                                       .append (m_sRight)
                                       .append (m_sBottom)
                                       .append (m_sLeft)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("top", m_sTop)
                                       .append ("right", m_sRight)
                                       .append ("bottom", m_sBottom)
                                       .append ("left", m_sLeft)
                                       .toString ();
  }
}
