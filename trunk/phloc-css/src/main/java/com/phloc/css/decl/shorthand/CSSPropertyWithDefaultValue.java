package com.phloc.css.decl.shorthand;

import javax.annotation.Nonnull;

import com.phloc.commons.ValueEnforcer;
import com.phloc.css.property.ICSSProperty;

public final class CSSPropertyWithDefaultValue
{
  private final ICSSProperty m_aProperty;
  private final String m_sDefaultValue;

  public CSSPropertyWithDefaultValue (@Nonnull final ICSSProperty aProperty, @Nonnull final String sDefaultValue)
  {
    m_aProperty = ValueEnforcer.notNull (aProperty, "Property");
    m_sDefaultValue = ValueEnforcer.notNull (sDefaultValue, "DefaultValue");
    if (!aProperty.isValidValue (sDefaultValue))
      throw new IllegalArgumentException ("Default value '" +
                                          sDefaultValue +
                                          "' does not match property " +
                                          aProperty);
  }
}