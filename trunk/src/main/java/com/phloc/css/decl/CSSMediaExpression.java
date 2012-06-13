package com.phloc.css.decl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

/**
 * Represents a single media expression
 */
public final class CSSMediaExpression
{
  private final String m_sFeature;
  private final String m_sValue;

  public CSSMediaExpression (@Nonnull @Nonempty final String sFeature)
  {
    this (sFeature, null);
  }

  public CSSMediaExpression (@Nonnull @Nonempty final String sFeature, @Nullable final String sValue)
  {
    if (StringHelper.hasNoText (sFeature))
      throw new IllegalArgumentException ("feature");
    m_sFeature = sFeature;
    m_sValue = sValue;
  }

  public String getFeature ()
  {
    return m_sFeature;
  }

  public String getValue ()
  {
    return m_sValue;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder (m_sFeature);
    if (StringHelper.hasText (m_sValue))
      aSB.append (':').append (m_sValue);
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaExpression))
      return false;
    final CSSMediaExpression rhs = (CSSMediaExpression) o;
    return m_sFeature.equals (rhs.m_sFeature) && EqualsUtils.equals (m_sValue, rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sFeature).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("feature", m_sFeature).appendIfNotNull ("value", m_sValue).toString ();
  }
}
