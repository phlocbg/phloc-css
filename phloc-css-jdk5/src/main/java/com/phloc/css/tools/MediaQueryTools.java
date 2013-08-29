/**
 * Copyright (C) 2006-2013 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phloc.css.tools;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.PresentForCodeCoverage;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaQuery;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSNamespaceRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.reader.CSSReader;

/**
 * A small utility class to wrap an existing {@link CascadingStyleSheet} within
 * a specific media, if possible. {@link CascadingStyleSheet} can only be
 * wrapped, if they don't contain a media rule themselves.
 * 
 * @author Philip Helger
 */
@Immutable
public final class MediaQueryTools
{
  @PresentForCodeCoverage
  private static final MediaQueryTools s_aInstance = new MediaQueryTools ();

  private MediaQueryTools ()
  {}

  @Nullable
  public static List <CSSMediaQuery> parseToMediaQuery (@Nullable final String sMediaQuery,
                                                        @Nonnull final Charset aCharset,
                                                        @Nonnull final ECSSVersion eVersion)
  {
    if (StringHelper.hasNoText (sMediaQuery))
      return null;

    final String sCSS = "@media " + sMediaQuery + " {}";
    final CascadingStyleSheet aCSS = CSSReader.readFromString (sCSS, aCharset, eVersion);
    if (aCSS == null)
      return null;

    final CSSMediaRule aMediaRule = aCSS.getAllMediaRules ().get (0);
    return aMediaRule.getAllMediaQueries ();
  }

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
    return getWrappedInMediaQuery (aCSS, ContainerHelper.newList (aMediaQuery));
  }

  /**
   * Get the CSS wrapped in the specified media query. Note: all existing rule
   * objects are reused, so modifying them also modifies the original CSS!
   * 
   * @param aCSS
   *        The CSS to be wrapped. May not be <code>null</code>.
   * @param aMediaQueries
   *        The media queries to use. May neither be <code>null</code> nor empty
   *        nor may it contain <code>null</code> elements.
   * @return <code>null</code> if out CSS cannot be wrapped, the newly created
   *         {@link CascadingStyleSheet} object otherwise.
   */
  @Nullable
  public static CascadingStyleSheet getWrappedInMediaQuery (@Nonnull final CascadingStyleSheet aCSS,
                                                            @Nonnull @Nonempty final List <CSSMediaQuery> aMediaQueries)
  {
    if (aCSS == null)
      throw new NullPointerException ("CSS");
    if (ContainerHelper.isEmpty (aMediaQueries))
      throw new IllegalArgumentException ("no mediaQueries present");

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
        // Create a new rule and add the passed media queries
        final CSSImportRule aNewImportRule = new CSSImportRule (aImportRule.getLocation ());
        for (final CSSMediaQuery aMediaQuery : aMediaQueries)
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
    for (final CSSMediaQuery aMediaQuery : aMediaQueries)
      aNewMediaRule.addMediaQuery (aMediaQuery);
    for (final ICSSTopLevelRule aRule : aCSS.getAllRules ())
      aNewMediaRule.addRule (aRule);
    ret.addRule (aNewMediaRule);

    return ret;
  }
}
