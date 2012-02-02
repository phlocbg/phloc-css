package com.phloc.css;

import javax.annotation.Nonnull;

/**
 * Interface for objects indicating that they are supported by a minimum CSS
 * version that may be higher than the default (CSS 2.1).
 * 
 * @author philip
 */
public interface ICSSVersionAware
{
  /**
   * @return The minimum supported CSS version. May not be <code>null</code>.
   */
  @Nonnull
  ECSSVersion getMinimumCSSVersion ();
}
