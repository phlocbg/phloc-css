package com.phloc.css.decl.shorthand;

import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.css.property.ECSSProperty;

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
}
