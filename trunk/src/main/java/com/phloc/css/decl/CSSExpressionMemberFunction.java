package com.phloc.css.decl;

import javax.annotation.concurrent.Immutable;

import com.phloc.commons.compare.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

@Immutable
public final class CSSExpressionMemberFunction implements ICSSExpressionMember
{
  private final String m_sFunctionName;
  private final CSSExpression m_aExpression;

  private static String _skipBrackets (final String sName)
  {
    if (sName.length () > 2 && sName.endsWith ("()"))
      return sName.substring (0, sName.length () - 2);
    return sName;
  }

  public CSSExpressionMemberFunction (final String sFunctionName, final CSSExpression aExpression)
  {
    if (StringHelper.hasNoText (sFunctionName))
      throw new IllegalArgumentException ("Empty function name is not allowed");
    // expression may be null
    m_sFunctionName = _skipBrackets (sFunctionName);
    m_aExpression = aExpression;
  }

  public String getFunctionName ()
  {
    return m_sFunctionName;
  }

  public CSSExpression getExpression ()
  {
    return m_aExpression;
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    if (m_aExpression == null)
      return m_sFunctionName + "()";
    return m_sFunctionName + "(" + m_aExpression.getAsCSSString (eVersion, bOptimizedOutput) + ")";
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSExpressionMemberFunction))
      return false;
    final CSSExpressionMemberFunction rhs = (CSSExpressionMemberFunction) o;
    return m_sFunctionName.equals (rhs.m_sFunctionName) &&
           EqualsUtils.nullSafeEquals (m_aExpression, rhs.m_aExpression);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sFunctionName).append (m_aExpression).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("funcName", m_sFunctionName)
                                       .appendIfNotNull ("expression", m_aExpression)
                                       .toString ();
  }
}
