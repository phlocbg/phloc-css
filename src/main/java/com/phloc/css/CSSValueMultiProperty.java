package com.phloc.css;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Represents a CSS value that has several property names, but only one value.
 * This is e.g. if the property <code>border-radius</code> is used, as in this
 * case also <code>-moz-border-radius</code> should be emitted (with the same
 * value).<br>
 * For consistency issues,
 * 
 * @author philip
 */
public final class CSSValueMultiProperty implements ICSSValue
{
  private final List <CSSValue> m_aValues = new ArrayList <CSSValue> ();

  public CSSValueMultiProperty (@Nonnull final ICSSProperty [] aProperties,
                                @Nonnull @Nonempty final String sValue,
                                final boolean bIsImportant)
  {
    if (ArrayHelper.isEmpty (aProperties))
      throw new IllegalArgumentException ("No properties passed!");
    if (sValue == null)
      throw new NullPointerException ("value");

    for (final ICSSProperty aProperty : aProperties)
      m_aValues.add (new CSSValue (aProperty, sValue, bIsImportant));
  }

  @Nonnull
  public ECSSProperty getProp ()
  {
    // ... not necessarily right
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
    if (!(o instanceof CSSValueMultiProperty))
      return false;
    final CSSValueMultiProperty rhs = (CSSValueMultiProperty) o;
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
