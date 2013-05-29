/**
 * Copyright (C) 2006-2013 phloc systems
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
package com.phloc.css.reader.errorhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.parser.ParseException;
import com.phloc.css.parser.Token;

/**
 * A collecting implementation of {@link ICSSParseErrorHandler}
 * 
 * @author Philip Helger
 */
@ThreadSafe
public class CollectingCSSParseErrorHandler implements ICSSParseErrorHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CollectingCSSParseErrorHandler.class);

  private final ReadWriteLock m_aRWLock = new ReentrantReadWriteLock ();
  @GuardedBy ("m_aRWLock")
  private final List <CSSParseError> m_aErrors = new ArrayList <CSSParseError> ();
  private final ICSSParseErrorHandler m_aNestedErrorHandler;

  public CollectingCSSParseErrorHandler ()
  {
    this (null);
  }

  public CollectingCSSParseErrorHandler (@Nullable final ICSSParseErrorHandler aNestedErrorHandler)
  {
    m_aNestedErrorHandler = aNestedErrorHandler;
  }

  public void onCSSParseError (@Nonnull final Token aLastValidToken,
                               @Nonnull final int [][] aExpectedTokenSequencesVal,
                               @Nonnull final String [] aTokenImageVal,
                               @Nonnull final Token aLastSkippedToken) throws ParseException
  {
    m_aRWLock.writeLock ().lock ();
    try
    {
      m_aErrors.add (new CSSParseError (aLastValidToken, aExpectedTokenSequencesVal, aTokenImageVal, aLastSkippedToken));
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }
    if (m_aNestedErrorHandler != null)
      m_aNestedErrorHandler.onCSSParseError (aLastValidToken,
                                             aExpectedTokenSequencesVal,
                                             aTokenImageVal,
                                             aLastSkippedToken);
  }

  /**
   * @return <code>true</code> if at least one parse error is contained,
   *         <code>false</code> otherwise.
   */
  @Nonnegative
  public boolean hasParseErrors ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return !m_aErrors.isEmpty ();
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  /**
   * @return The number of contained parse errors.
   */
  @Nonnegative
  public int getParseErrorCount ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return m_aErrors.size ();
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSParseError> getAllParseErrors ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return ContainerHelper.newList (m_aErrors);
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("errors", m_aErrors).toString ();
  }
}
