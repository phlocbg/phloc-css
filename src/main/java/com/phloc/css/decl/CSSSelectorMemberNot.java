package com.phloc.css.decl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSVersionHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;

@Immutable
public final class CSSSelectorMemberNot implements ICSSSelectorMember, ICSSVersionAware
{
  private final ICSSSelectorMember m_aNestedSelectorMember;

  public CSSSelectorMemberNot (@Nonnull @Nonempty final ICSSSelectorMember aNestedSelectorMember)
  {
    if (aNestedSelectorMember == null)
      throw new NullPointerException ("nestedSelector");
    m_aNestedSelectorMember = aNestedSelectorMember;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    CSSVersionHelper.checkVersionRequirements (eVersion, this);
    return ":not(" + m_aNestedSelectorMember.getAsCSSString (eVersion, bOptimizedOutput) + ")";
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSelectorMemberNot))
      return false;
    final CSSSelectorMemberNot rhs = (CSSSelectorMemberNot) o;
    return m_aNestedSelectorMember.equals (rhs.m_aNestedSelectorMember);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aNestedSelectorMember).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("nestedSelector", m_aNestedSelectorMember).toString ();
  }
}
