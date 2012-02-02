package com.phloc.css;

import javax.annotation.Nonnull;

import com.phloc.commons.version.IHasVersion;
import com.phloc.commons.version.Version;

/**
 * Contains the different CSS versions that may be of relevance.
 * 
 * @author philip
 */
public enum ECSSVersion implements IHasVersion
{
  // Sort fields according to the version!
  CSS21 (new Version (2, 1)),
  CSS30 (new Version (3, 0));

  public static final ECSSVersion LATEST = CSS30;

  private Version m_aVersion;

  private ECSSVersion (@Nonnull final Version aVersion)
  {
    m_aVersion = aVersion;
  }

  @Nonnull
  public Version getVersion ()
  {
    return m_aVersion;
  }
}
