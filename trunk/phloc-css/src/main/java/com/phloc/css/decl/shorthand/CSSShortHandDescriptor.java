package com.phloc.css.decl.shorthand;

import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.property.ECSSProperty;

/**
 * A single descriptor for a short hand property (like font or border)
 * 
 * @author Philip Helger
 * @since 3.7.4
 */
public class CSSShortHandDescriptor
{
  private final ECSSProperty m_eProperty;
  private final List <CSSPropertyWithDefaultValue> m_aSubProperties;

  public CSSShortHandDescriptor (@Nonnull final ECSSProperty eProperty,
                                 @Nonnull @Nonempty final CSSPropertyWithDefaultValue... aSubProperties)
  {
    ValueEnforcer.notNull (eProperty, "Property");
    ValueEnforcer.notEmptyNoNullValue (aSubProperties, "SubProperties");
    m_eProperty = eProperty;
    m_aSubProperties = ContainerHelper.newList (aSubProperties);
  }

  @Nonnull
  public ECSSProperty getProperty ()
  {
    return m_eProperty;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSPropertyWithDefaultValue> getAllSubProperties ()
  {
    return ContainerHelper.newList (m_aSubProperties);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("property", m_eProperty)
                                       .append ("subProperties", m_aSubProperties)
                                       .toString ();
  }
}
