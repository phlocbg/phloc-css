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
package com.phloc.css.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.StringParser;
import com.phloc.css.ECSSUnit;
import com.phloc.css.propertyvalue.CSSSimpleValueWithUnit;

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
  public static ECSSUnit getMatchingUnitInclPercentage (@Nonnull final String sCSSValue)
  {
    // sValue cannot be null here!
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      if (sCSSValue.endsWith (eUnit.getName ()))
        return eUnit;
    return null;
  }

  @Nullable
  public static ECSSUnit getMatchingUnitExclPercentage (@Nonnull final String sCSSValue)
  {
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      if (eUnit != ECSSUnit.PERCENTAGE)
        if (sCSSValue.endsWith (eUnit.getName ()))
          return eUnit;
    return null;
  }

  public static boolean isNumberValue (@Nullable final String sCSSValue)
  {
    final String sRealValue = StringHelper.trim (sCSSValue);
    return StringHelper.hasText (sRealValue) && StringParser.isDouble (sRealValue);
  }

  public static boolean isValueWithUnit (@Nullable final String sCSSValue)
  {
    return getValueWithUnit (sCSSValue) != null;
  }

  public static boolean isValueWithUnit (@Nullable final String sCSSValue, final boolean bWithPerc)
  {
    return getValueWithUnit (sCSSValue, bWithPerc) != null;
  }

  @Nullable
  public static CSSSimpleValueWithUnit getValueWithUnit (@Nullable final String sCSSValue)
  {
    return getValueWithUnit (sCSSValue, true);
  }

  @Nullable
  public static CSSSimpleValueWithUnit getValueWithUnit (@Nullable final String sCSSValue, final boolean bWithPerc)
  {
    String sRealValue = StringHelper.trim (sCSSValue);
    if (StringHelper.hasText (sRealValue))
    {
      // Special case for 0!
      if (sRealValue.equals ("0"))
        return new CSSSimpleValueWithUnit (0, ECSSUnit.PX);

      final ECSSUnit eUnit = bWithPerc ? getMatchingUnitInclPercentage (sRealValue)
                                      : getMatchingUnitExclPercentage (sRealValue);
      if (eUnit != null)
      {
        // Cut the unit
        sRealValue = sRealValue.substring (0, sRealValue.length () - eUnit.getName ().length ()).trim ();
        final Double aValue = StringParser.parseDoubleObj (sRealValue);
        if (aValue != null)
          return new CSSSimpleValueWithUnit (aValue.doubleValue (), eUnit);
      }
    }
    return null;
  }
}