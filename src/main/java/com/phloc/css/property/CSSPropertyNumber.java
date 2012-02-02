package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;

public class CSSPropertyNumber extends AbstractCSSProperty
{
  private final boolean m_bWithPercentage;

  public CSSPropertyNumber (@Nonnull final ECSSProperty eProp, final boolean bWithPercentage)
  {
    super (eProp);
    m_bWithPercentage = bWithPercentage;
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return CCSS.isNumberWithUnitValue (sValue, m_bWithPercentage);
  }

  @Nonnull
  public CSSPropertyNumber getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyNumber (eProp, m_bWithPercentage);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("withPercentage", m_bWithPercentage).toString ();
  }
}
