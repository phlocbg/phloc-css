package com.phloc.css;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Represents a CSS value that has one property name, but multiple different
 * values. This is e.g. if the property <code>display</code> is used with the
 * value <code>inline-block</code> than the result coding should first emit
 * <code>display:-moz-inline-block;</code> and them
 * <code>display:inline-block;</code> for FireFox 2.x specific support.
 * 
 * @author philip
 */
public final class CSSValueMultiValue implements ICSSValue
{
  private final List <CSSValue> m_aValues = new ArrayList <CSSValue> ();

  public CSSValueMultiValue (@Nonnull final ICSSProperty aProperty,
                             @Nonnull @Nonempty final String [] aValues,
                             final boolean bIsImportant)
  {
    if (aProperty == null)
      throw new NullPointerException ("property");
    if (ArrayHelper.isEmpty (aValues))
      throw new IllegalArgumentException ("No value passed!");

    for (final String sValue : aValues)
      m_aValues.add (new CSSValue (aProperty, sValue, bIsImportant));
  }

  @Nonnull
  public ECSSProperty getProp ()
  {
    return m_aValues.get (0).getProp ();
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder ret = new StringBuilder ();
    for (final CSSValue aValue : m_aValues)
      ret.append (aValue.getAsCSSString (eVersion, bOptimizedOutput));
    return ret.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSValueMultiValue))
      return false;
    final CSSValueMultiValue rhs = (CSSValueMultiValue) o;
    return m_aValues.equals (rhs.m_aValues);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aValues).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("values", m_aValues).toString ();
  }
}
