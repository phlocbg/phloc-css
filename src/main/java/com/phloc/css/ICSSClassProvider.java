package com.phloc.css;

import javax.annotation.Nullable;

/**
 * Interface for a CSS class provider.
 * 
 * @author philip
 */
public interface ICSSClassProvider
{
  /**
   * @return The desired CSS class. May be <code>null</code> to indicate no
   *         class.
   */
  @Nullable
  String getCSSClass ();
}
