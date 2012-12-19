package com.phloc.css.tools;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaQuery;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSNamespaceRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;

/**
 * A small utility class to wrap an existing {@link CascadingStyleSheet} within
 * a specific media, if possible. {@link CascadingStyleSheet} can only be
 * wrapped, if they don't contain a media rule themselves.
 * 
 * @author philip
 */
@Immutable
public final class MediaQueryTools
{
  private MediaQueryTools ()
  {}

  /**
   * Check if the passed CSS can be wrapped in an external media rule. It is not
   * possible, if a CSS already contains other media rules, as nested media
   * rules are not allowed.
   * 
   * @param aCSS
   *        The CSS to be checked for wrapping.
   * @return <code>true</code> if the CSS can be wrapped, <code>false</code> if
   *         it can't be wrapped.
   */
  public static boolean canWrapInMediaQuery (@Nullable final CascadingStyleSheet aCSS)
  {
    return aCSS != null && !aCSS.hasMediaRules ();
  }

  /**
   * Get the CSS wrapped in the specified media query. Note: all existing rule
   * objects are reused, so modifying them also modifies the original CSS!
   * 
   * @param aCSS
   *        The CSS to be wrapped. May not be <code>null</code>.
   * @param aMediaQuery
   *        The media query to use.
   * @return <code>null</code> if out CSS cannot be wrapped, the newly created
   *         {@link CascadingStyleSheet} object otherwise.
   */
  @Nullable
  public static CascadingStyleSheet getWrappedInMediaQuery (@Nonnull final CascadingStyleSheet aCSS,
                                                            @Nonnull final CSSMediaQuery aMediaQuery)
  {
    if (aCSS == null)
      throw new NullPointerException ("CSS");
    if (aMediaQuery == null)
      throw new NullPointerException ("mediaQuery");

    if (!canWrapInMediaQuery (aCSS))
      return null;

    final CascadingStyleSheet ret = new CascadingStyleSheet ();

    // Copy all import rules
    for (final CSSImportRule aImportRule : aCSS.getAllImportRules ())
    {
      if (aImportRule.hasMediaQueries ())
      {
        // import rule already has a media query - do not alter
        ret.addImportRule (aImportRule);
      }
      else
      {
        // Create a new rule and add the passed media query
        final CSSImportRule aNewImportRule = new CSSImportRule (aImportRule.getLocation ());
        aNewImportRule.addMediaQuery (aMediaQuery);
        ret.addImportRule (aNewImportRule);
      }
    }

    // Copy all namespace rules
    for (final CSSNamespaceRule aNamespaceRule : aCSS.getAllNamespaceRules ())
      ret.addNamespaceRule (aNamespaceRule);

    // Create a single top-level media rule and add the existing top-level rules
    // into this media rule
    final CSSMediaRule aNewMediaRule = new CSSMediaRule ();
    aNewMediaRule.addMediaQuery (aMediaQuery);
    for (final ICSSTopLevelRule aRule : aCSS.getAllRules ())
      aNewMediaRule.addRule (aRule);
    ret.addRule (aNewMediaRule);

    return ret;
  }
}
