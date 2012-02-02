package com.phloc.css;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.MustImplementEqualsAndHashcode;

/**
 * Represents a single CSS value.
 * 
 * @author philip
 */
@MustImplementEqualsAndHashcode
public interface ICSSValue extends ICSSWriteable
{
  /**
   * @return The underlying CSS property.
   */
  @Nonnull
  ECSSProperty getProp ();
}
