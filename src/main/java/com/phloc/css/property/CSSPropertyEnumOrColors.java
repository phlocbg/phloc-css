package com.phloc.css.property;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.string.ToStringGenerator;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.regex.RegExHelper;
import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;

public class CSSPropertyEnumOrColors extends CSSPropertyEnum
{
  private final int m_nMinNumbers;
  private final int m_nMaxNumbers;

  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aEnumValues);
    if (nMinNumbers < 0)
      throw new IllegalArgumentException ("minNumbers: " + nMinNumbers);
    if (nMaxNumbers < 0 || nMaxNumbers < nMinNumbers)
      throw new IllegalArgumentException ("maxNumbers: " + nMaxNumbers);
    m_nMinNumbers = nMinNumbers;
    m_nMaxNumbers = nMaxNumbers;
  }

  @Override
  public boolean isValidValue (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;
    final String [] aParts = RegExHelper.split (sValue.trim (), "\\s+");
    if (aParts.length < m_nMinNumbers || aParts.length > m_nMaxNumbers)
      return false;
    for (int i = 0; i < aParts.length; ++i)
    {
      aParts[i] = aParts[i].trim ();
      if (!super.isValidValue (aParts[i]) && !CCSS.isColorValue (aParts[i]))
        return false;
    }
    return true;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("minNumbers", m_nMinNumbers)
                            .append ("maxNumbers", m_nMaxNumbers)
                            .toString ();
  }
}
