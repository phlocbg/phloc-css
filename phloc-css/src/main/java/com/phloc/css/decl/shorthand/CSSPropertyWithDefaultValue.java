package com.phloc.css.decl.shorthand;

import javax.annotation.Nonnull;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.property.ICSSProperty;

/**
 * A descriptor for a property and a default value
 * 
 * @author Philip Helger
 * @since 3.7.4
 */
public final class CSSPropertyWithDefaultValue
{
  private final ICSSProperty m_aProperty;
  private final String m_sDefaultValue;

  public CSSPropertyWithDefaultValue (@Nonnull final ICSSProperty aProperty, @Nonnull final String sDefaultValue)
  {
    m_aProperty = ValueEnforcer.notNull (aProperty, "Property");
    m_sDefaultValue = ValueEnforcer.notNull (sDefaultValue, "DefaultValue");

    // Check that default value is valid for property
    if (!aProperty.isValidValue (sDefaultValue))
      throw new IllegalArgumentException ("Default value '" + sDefaultValue + "' does not match property " + aProperty);
  }

  @Nonnull
  public ICSSProperty getProperty ()
  {
    return m_aProperty;
  }

  @Nonnull
  public String getDefaultValue ()
  {
    return m_sDefaultValue;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("property", m_aProperty)
                                       .append ("defaultValue", m_sDefaultValue)
                                       .toString ();
  }
}
