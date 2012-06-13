package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.media.ECSSMedium;

/**
 * Represents a single media query
 * 
 * @author philip
 */
public final class CSSMediaQuery
{
  private boolean m_bNot = false;
  private boolean m_bOnly = false;
  private String m_sMedium = ECSSMedium.ALL.getAttrValue ();
  private final List <CSSMediaExpression> m_aMediaExpressions = new ArrayList <CSSMediaExpression> ();

  public CSSMediaQuery ()
  {}

  public boolean isNot ()
  {
    return m_bNot;
  }

  public void setNot (final boolean bNot)
  {
    m_bNot = bNot;
  }

  public boolean isOnly ()
  {
    return m_bOnly;
  }

  public void setOnly (final boolean bOnly)
  {
    m_bOnly = bOnly;
  }

  @Nullable
  public String getMedium ()
  {
    return m_sMedium;
  }

  public void setMedium (@Nullable final String sMedium)
  {
    m_sMedium = sMedium;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSMediaExpression> getMediaExpressions ()
  {
    return ContainerHelper.newList (m_aMediaExpressions);
  }

  public void addMediaExpression (@Nonnull final CSSMediaExpression aMediaExpression)
  {
    if (aMediaExpression == null)
      throw new NullPointerException ("expression");
    m_aMediaExpressions.add (aMediaExpression);
  }

  @Nonnull
  public EChange removeMediaExpression (@Nonnull final CSSMediaExpression aMediaExpression)
  {
    return EChange.valueOf (m_aMediaExpressions.remove (aMediaExpression));
  }

  @Nonnull
  public EChange removeMediaExpression (@Nonnull final int nIndex)
  {
    if (nIndex < 0 || nIndex >= m_aMediaExpressions.size ())
      return EChange.UNCHANGED;
    return EChange.valueOf (m_aMediaExpressions.remove (nIndex) != null);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ();
    if (m_bOnly)
      aSB.append ("only ");
    else
      if (m_bNot)
        aSB.append ("not ");

    aSB.append (m_sMedium);
    if (!m_aMediaExpressions.isEmpty ())
    {
      for (final CSSMediaExpression aMediaExpression : m_aMediaExpressions)
      {
        // The leading blank is required in case this is the first expression
        // after a medium declaration!
        // The trailing blank is required, because otherwise it is considered a
        // function!
        aSB.append (" and ").append (aMediaExpression.getAsCSSString (eVersion, bOptimizedOutput));
      }
    }

    if (!bOptimizedOutput)
      aSB.append ('\n');

    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaQuery))
      return false;
    final CSSMediaQuery rhs = (CSSMediaQuery) o;
    return m_bNot == rhs.m_bNot &&
           m_bOnly == rhs.m_bOnly &&
           EqualsUtils.equals (m_sMedium, rhs.m_sMedium) &&
           m_aMediaExpressions.equals (rhs.m_aMediaExpressions);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_bNot)
                                       .append (m_bOnly)
                                       .append (m_sMedium)
                                       .append (m_aMediaExpressions)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("not", m_bNot)
                                       .append ("only", m_bOnly)
                                       .append ("medium", m_sMedium)
                                       .append ("expressions", m_aMediaExpressions)
                                       .toString ();
  }
}
