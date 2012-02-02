package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.string.ToStringGenerator;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;

public class CSSPropertyEnumOrNumber extends CSSPropertyEnum
{
  private final boolean m_bWithPercentage;

  public CSSPropertyEnumOrNumber (@Nonnull final ECSSProperty eProp,
                                  final boolean bWithPercentage,
                                  @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aEnumValues);
    m_bWithPercentage = bWithPercentage;
  }

  @Override
  public boolean isValidValue (@Nullable final String sValue)
  {
    return super.isValidValue (sValue) || CCSS.isNumberWithUnitValue (sValue, m_bWithPercentage);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("withPercentage", m_bWithPercentage).toString ();
  }
}
