package com.phloc.css;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Represents a CSS value that has both different property names and multiple
 * different values. This is e.g. if the property <code>display</code> is used
 * with the value <code>inline-block</code> than the result coding should first
 * emit <code>display:-moz-inline-block;</code> and them
 * <code>display:inline-block;</code> for FireFox 2.x specific support.
 * 
 * @author philip
 */
public final class CSSValueList implements ICSSValue
{
  private final List <CSSValue> m_aValues = new ArrayList <CSSValue> ();

  public CSSValueList (@Nonnull final ICSSProperty [] aProperties,
                       @Nonnull final String [] aValues,
                       final boolean bIsImportant)
  {
    if (aProperties == null || aProperties.length == 0)
      throw new IllegalArgumentException ("No properties passed!");
    if (aValues == null || aValues.length == 0)
      throw new IllegalArgumentException ("No value passed!");
    if (aProperties.length != aValues.length)
      throw new IllegalArgumentException ("Different number of properties and values passed");

    for (int i = 0; i < aProperties.length; ++i)
      m_aValues.add (new CSSValue (aProperties[i], aValues[i], bIsImportant));
  }

  public ECSSProperty getProp ()
  {
    return ContainerHelper.getLastElement (m_aValues).getProp ();
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
    if (!(o instanceof CSSValueList))
      return false;
    final CSSValueList rhs = (CSSValueList) o;
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
