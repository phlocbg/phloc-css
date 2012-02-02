package com.phloc.css;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;


@Immutable
public final class CSSVersionHelper
{
  private CSSVersionHelper ()
  {}

  public static void checkVersionRequirements (@Nonnull final ECSSVersion eDesiredVersion,
                                               @Nonnull final ICSSVersionAware aCSSObject)
  {
    final ECSSVersion eMinCSSVersion = aCSSObject.getMinimumCSSVersion ();
    if (eDesiredVersion.compareTo (eMinCSSVersion) < 0)
      throw new IllegalStateException ("This object cannot be serialized to CSS version " +
                                       eDesiredVersion.getVersion ().getAsString () +
                                       " but requires at least " +
                                       eMinCSSVersion.getVersion ().getAsString ());
  }
}
