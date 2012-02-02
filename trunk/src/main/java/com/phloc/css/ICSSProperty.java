package com.phloc.css;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base interface for a single CSS property.
 * 
 * @see com.phloc.css.property.CCSSProperties for a list of default CSS
 *      properties
 * @author philip
 */
public interface ICSSProperty
{
  @Nonnull
  ECSSProperty getProp ();

  boolean isValidValue (@Nullable String sValue);

  @Nonnull
  ICSSValue newValue (String sValue);

  @Nonnull
  ICSSValue newImportantValue (String sValue);

  @Nonnull
  ICSSProperty getClone (@Nonnull ECSSProperty eProp);
}
