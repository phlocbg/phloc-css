package com.phloc.css.property;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSProperty;

public class CSSPropertyEnum extends AbstractCSSProperty
{
  private final Set <String> m_aEnumValues;

  public CSSPropertyEnum (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp);
    if (ArrayHelper.isEmpty (aEnumValues))
      throw new IllegalArgumentException ("At least one enumeration value needs to be passed!");
    m_aEnumValues = new HashSet <String> (aEnumValues.length);
    for (final String sPotentialValue : aEnumValues)
    {
      if (StringHelper.hasNoText (sPotentialValue))
        throw new IllegalArgumentException ("At least one enumeration value is empty");
      m_aEnumValues.add (sPotentialValue);
    }
  }

  private CSSPropertyEnum (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final Set <String> aEnumValues)
  {
    super (eProp);
    m_aEnumValues = new HashSet <String> (aEnumValues);
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return m_aEnumValues.contains (sValue);
  }

  @Nonnull
  public CSSPropertyEnum getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnum (eProp, m_aEnumValues);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("enumValues", m_aEnumValues).toString ();
  }
}
