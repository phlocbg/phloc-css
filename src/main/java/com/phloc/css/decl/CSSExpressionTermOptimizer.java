package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.ECSSUnit;

/**
 * This class is responsible for expression term optimization
 * 
 * @author philip
 */
public final class CSSExpressionTermOptimizer
{
  private static List <String> UNIT_VALUES_0 = new ArrayList <String> ();

  static
  {
    // Save all "0" formatted unit values
    for (final ECSSUnit eUnit : ECSSUnit.values ())
      UNIT_VALUES_0.add (eUnit.format (0));
  }

  private CSSExpressionTermOptimizer ()
  {}

  @Nonnull
  @Nonempty
  public static String getOptimizedValue (@Nonnull @Nonempty final String sValue)
  {
    // Replace e.g. "0px" with "0"
    for (final String sUnit0 : UNIT_VALUES_0)
      if (sValue.equals (sUnit0))
        return "0";

    // Check for optimized color values
    if (sValue.length () == 7 &&
        sValue.charAt (0) == '#' &&
        sValue.charAt (1) == sValue.charAt (2) &&
        sValue.charAt (3) == sValue.charAt (4) &&
        sValue.charAt (5) == sValue.charAt (6))
    {
      // #112233 => #123
      return "#" + sValue.charAt (1) + sValue.charAt (3) + sValue.charAt (5);
    }

    return sValue;
  }
}
