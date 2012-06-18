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
package com.phloc.css.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSUnit;

/**
 * Provides number handling sanity methods.
 * 
 * @author philip
 */
@Immutable
public final class CSSNumberHelper
{
  private CSSNumberHelper ()
  {}

  @Nullable
  private static ECSSUnit _findUnitWithPerc (@Nonnull final String sValue)
  {
    // sValue cannot be null here!
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      if (sValue.endsWith (eUnit.getName ()))
        return eUnit;
    return null;
  }

  @Nullable
  private static ECSSUnit _findUnitWithoutPerc (@Nonnull final String sValue)
  {
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      if (eUnit != ECSSUnit.PERCENTAGE)
        if (sValue.endsWith (eUnit.getName ()))
          return eUnit;
    return null;
  }

  public static boolean isNumberWithUnitValue (@Nullable final String sValue, final boolean bWithPerc)
  {
    String sRealValue = StringHelper.trim (sValue);
    if (StringHelper.hasText (sRealValue))
    {
      final ECSSUnit eUnit = bWithPerc ? _findUnitWithPerc (sRealValue) : _findUnitWithoutPerc (sRealValue);
      if (eUnit != null)
      {
        // Cut the unit
        sRealValue = sRealValue.substring (0, sRealValue.length () - eUnit.getName ().length ()).trim ();
      }
      return CSSNumberHelper.isNumberValue (sRealValue);
    }
    return false;
  }

  public static boolean isNumberValue (@Nullable final String sValue)
  {
    final String sRealValue = StringHelper.trim (sValue);
    return StringHelper.hasText (sRealValue) && StringHelper.isDouble (sRealValue);
  }
}
