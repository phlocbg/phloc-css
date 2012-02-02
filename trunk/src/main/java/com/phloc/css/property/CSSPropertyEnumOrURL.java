package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;

public class CSSPropertyEnumOrURL extends CSSPropertyEnum
{
  public CSSPropertyEnumOrURL (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, aEnumValues);
  }

  @Override
  public boolean isValidValue (@Nullable final String sValue)
  {
    return super.isValidValue (sValue) || CCSS.isURLValue (sValue);
  }
}
