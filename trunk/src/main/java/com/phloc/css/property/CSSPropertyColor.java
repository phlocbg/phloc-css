package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;

public class CSSPropertyColor extends AbstractCSSProperty
{
  public CSSPropertyColor (@Nonnull final ECSSProperty eProp)
  {
    super (eProp);
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return CCSS.isColorValue (sValue);
  }

  @Nonnull
  public CSSPropertyColor getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyColor (eProp);
  }
}
