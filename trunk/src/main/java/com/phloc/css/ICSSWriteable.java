package com.phloc.css;

import javax.annotation.Nonnull;


/**
 * Interface for objects that can be written to CSS.
 * 
 * @author philip
 */
public interface ICSSWriteable
{
  /**
   * @param eVersion
   *          The version for which the output is indented. May not be
   *          <code>null</code>.
   * @param bOptimizedOutput
   *          if <code>true</code> the minimum number of bytes should be
   *          written.
   * @return The content of this object as CSS string.
   */
  @Nonnull
  String getAsCSSString (@Nonnull ECSSVersion eVersion, boolean bOptimizedOutput);
}
