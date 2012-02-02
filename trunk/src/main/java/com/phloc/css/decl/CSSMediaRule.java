package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

/**
 * Represents a single @media rule: a list of style rules only valid for certain
 * media.
 * 
 * @author philip
 */
public final class CSSMediaRule implements ICSSTopLevelRule
{
  private final List <String> m_aMedia = new ArrayList <String> ();
  private final List <CSSStyleRule> m_aStyleRules = new ArrayList <CSSStyleRule> ();

  public CSSMediaRule ()
  {}

  public void addMedium (@Nonnull final String sMedium)
  {
    if (StringHelper.hasNoText (sMedium))
      throw new IllegalArgumentException ("medium");
    m_aMedia.add (sMedium);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <String> getAllMedia ()
  {
    return ContainerHelper.makeUnmodifiable (m_aMedia);
  }

  public void addStyleRule (@Nonnull final CSSStyleRule aStyleRule)
  {
    if (aStyleRule == null)
      throw new NullPointerException ("styleRule");
    m_aStyleRules.add (aStyleRule);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <CSSStyleRule> getAllStyleRules ()
  {
    return ContainerHelper.makeUnmodifiable (m_aStyleRules);
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ("@media ");
    boolean bFirst = true;
    for (final String sMedium : m_aMedia)
    {
      if (bFirst)
        bFirst = false;
      else
        aSB.append (bOptimizedOutput ? "," : ", ");
      aSB.append (sMedium);
    }
    aSB.append (bOptimizedOutput ? "{" : " {");
    if (!bOptimizedOutput && !m_aStyleRules.isEmpty ())
      aSB.append ('\n');
    for (final CSSStyleRule aStyleRule : m_aStyleRules)
    {
      if (!bOptimizedOutput)
        aSB.append ("  ");
      aSB.append (aStyleRule.getAsCSSString (eVersion, bOptimizedOutput));
      if (!bOptimizedOutput)
        aSB.append ('\n');
    }
    aSB.append (bOptimizedOutput ? "}" : "}\n");
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaRule))
      return false;
    final CSSMediaRule rhs = (CSSMediaRule) o;
    return m_aMedia.equals (rhs.m_aMedia) && m_aStyleRules.equals (rhs.m_aStyleRules);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMedia).append (m_aStyleRules).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("medias", m_aMedia).append ("styleRules", m_aStyleRules).toString ();
  }
}
