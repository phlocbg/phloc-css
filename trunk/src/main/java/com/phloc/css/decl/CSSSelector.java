package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSWriteable;

public final class CSSSelector implements ICSSWriteable
{
  private final List <ICSSSelectorMember> m_aMembers = new ArrayList <ICSSSelectorMember> ();

  public CSSSelector ()
  {}

  public void addMember (@Nonnull final ICSSSelectorMember aMember)
  {
    if (aMember == null)
      throw new NullPointerException ("member");
    m_aMembers.add (aMember);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <ICSSSelectorMember> getAllMembers ()
  {
    return ContainerHelper.makeUnmodifiable (m_aMembers);
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ();
    for (final ICSSSelectorMember aMember : m_aMembers)
      aSB.append (aMember.getAsCSSString (eVersion, bOptimizedOutput));
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSelector))
      return false;
    final CSSSelector rhs = (CSSSelector) o;
    return m_aMembers.equals (rhs.m_aMembers);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMembers).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("members", m_aMembers).toString ();
  }
}
