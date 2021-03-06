/**
 * Copyright (C) 2006-2015 phloc systems
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.css.parser.ParseException;

/**
 * An implementation of {@link ICSSParseExceptionHandler} that silently ignores
 * all errors.
 * 
 * @author Philip Helger
 */
@Immutable
public final class DoNothingCSSParseExceptionHandler implements ICSSParseExceptionHandler
{
  private static final DoNothingCSSParseExceptionHandler s_aInstance = new DoNothingCSSParseExceptionHandler ();

  @Deprecated
  public DoNothingCSSParseExceptionHandler ()
  {}

  @Nonnull
  public static DoNothingCSSParseExceptionHandler getInstance ()
  {
    return s_aInstance;
  }

  public void onException (@Nonnull final ParseException ex)
  {
    // ignore
  }
}
