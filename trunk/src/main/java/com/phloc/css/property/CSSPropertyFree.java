package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSProperty;

public class CSSPropertyFree extends AbstractCSSProperty
{
  public CSSPropertyFree (@Nonnull final ECSSProperty eProp)
  {
    super (eProp);
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return StringHelper.hasText (sValue);
  }

  @Nonnull
  public CSSPropertyFree getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyFree (eProp);
  }
}
