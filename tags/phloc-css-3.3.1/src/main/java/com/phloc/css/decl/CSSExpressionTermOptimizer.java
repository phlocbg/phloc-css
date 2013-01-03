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
package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.ECSSUnit;
import com.phloc.css.propertyvalue.CCSSValue;

/**
 * This class is responsible for expression term optimization
 * 
 * @author philip
 */
public final class CSSExpressionTermOptimizer
{
  private static final List <String> s_aUnitValues0 = new ArrayList <String> ();

  static
  {
    // Save all "0" formatted unit values
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      s_aUnitValues0.add (eUnit.format (0));
  }

  private CSSExpressionTermOptimizer ()
  {}

  @Nonnull
  @Nonempty
  public static String getOptimizedValue (@Nonnull @Nonempty final String sValue)
  {
    // Replace e.g. "0px" with "0"
    for (final String sUnit0 : s_aUnitValues0)
      if (sValue.equals (sUnit0))
        return "0";

    // Check for optimized color values
    if (sValue.length () == CCSSValue.HEXVALUE_LENGTH &&
        sValue.charAt (0) == CCSSValue.PREFIX_HEX &&
        sValue.charAt (1) == sValue.charAt (2) &&
        sValue.charAt (3) == sValue.charAt (4) &&
        sValue.charAt (5) == sValue.charAt (6))
    {
      // #112233 => #123
      return Character.toString (CCSSValue.PREFIX_HEX) + sValue.charAt (1) + sValue.charAt (3) + sValue.charAt (5);
    }

    return sValue;
  }
}
