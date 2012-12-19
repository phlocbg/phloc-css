package com.phloc.css.tools;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
public class WrapInMediaQuery
{
  private final CascadingStyleSheet m_aCSS;

  public WrapInMediaQuery (@Nonnull final CascadingStyleSheet aCSS)
  {
    if (aCSS == null)
      throw new NullPointerException ("CSS");
    m_aCSS = aCSS;
  }

  /**
   * Check if the CSS can be wrapped in an external media rule. It is not
   * possible, if a CSS already contains other media rules, as nested media
   * rules are not allowed.
   * 
   * @return <code>true</code> if the CSS can be wrapped, <code>false</code> if
   *         it can't be wrapped.
   */
  public boolean canWrapInMediaQuery ()
  {
    return !m_aCSS.hasMediaRules ();
  }

  /**
   * Get the CSS wrapped in the specified media query. Note: all existing rule
   * objects are reused, so modifying them also modifies the original CSS!
   * 
   * @param aMediaQuery
   *        The media query to use.
   * @return <code>null</code> if out CSS cannot be wrapped, the newly created
   *         {@link CascadingStyleSheet} object otherwise.
   */
  @Nullable
  public CascadingStyleSheet getWrappedInMediaQuery (@Nonnull final CSSMediaQuery aMediaQuery)
  {
    if (aMediaQuery == null)
      throw new NullPointerException ("mediaQuery");

    if (!canWrapInMediaQuery ())
      return null;

    final CascadingStyleSheet ret = new CascadingStyleSheet ();

    // Copy all import rules
    for (final CSSImportRule aImportRule : m_aCSS.getAllImportRules ())
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
    for (final CSSNamespaceRule aNamespaceRule : m_aCSS.getAllNamespaceRules ())
      ret.addNamespaceRule (aNamespaceRule);

    // Create a single top-level media rule and add the existing top-level rules
    // into this media rule
    final CSSMediaRule aNewMediaRule = new CSSMediaRule ();
    aNewMediaRule.addMediaQuery (aMediaQuery);
    for (final ICSSTopLevelRule aRule : m_aCSS.getAllRules ())
      aNewMediaRule.addRule (aRule);
    ret.addRule (aNewMediaRule);

    return ret;
  }
}
