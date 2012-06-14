package com.phloc.css;

import javax.annotation.Nonnull;

/**
 * This class represents the options required for writing
 * 
 * @author philip
 */
public final class CSSWriterSettings
{
  private final ECSSVersion m_eVersion;
  private final boolean m_bOptimizedOutput;

  /**
   * @param eVersion
   *          CSS version to emit
   * @param bOptimizedOutput
   *          if <code>true</code> the output will be optimized for space, else
   *          for readability
   */
  public CSSWriterSettings (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    m_eVersion = eVersion;
    m_bOptimizedOutput = bOptimizedOutput;
  }

  @Nonnull
  public ECSSVersion getVersion ()
  {
    return m_eVersion;
  }

  public boolean isOptimizedOutput ()
  {
    return m_bOptimizedOutput;
  }
}
