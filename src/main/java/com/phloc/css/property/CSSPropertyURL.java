package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;

public class CSSPropertyURL extends AbstractCSSProperty
{
  public CSSPropertyURL (@Nonnull final ECSSProperty eProp)
  {
    super (eProp);
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return CCSS.isURLValue (sValue);
  }

  @Nonnull
  public CSSPropertyURL getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyURL (eProp);
  }
}
