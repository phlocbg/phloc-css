package com.phloc.css;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Represents the combination of a single CSS property and it's according value.
 * 
 * @author philip
 */
@Immutable
public final class CSSValue implements ICSSValue
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSValue.class);

  private final ICSSProperty m_aProperty;
  private final String m_sValue;
  private final boolean m_bIsImportant;

  public CSSValue (@Nonnull final ICSSProperty aProperty, @Nonnull final String sValue, final boolean bIsImportant)
  {
    if (aProperty == null)
      throw new NullPointerException ("property");
    if (!aProperty.isValidValue (sValue))
      s_aLogger.warn ("CSS: the value '" +
                      sValue +
                      "' is not valid for property '" +
                      aProperty.getProp ().getName () +
                      "'");

    m_aProperty = aProperty;
    m_sValue = sValue + (bIsImportant ? CCSS.IMPORTANT_SUFFIX : "");
    m_bIsImportant = bIsImportant;
  }

  @Nonnull
  public ICSSProperty getProperty ()
  {
    return m_aProperty;
  }

  @Nonnull
  public ECSSProperty getProp ()
  {
    return m_aProperty.getProp ();
  }

  public String getValue ()
  {
    return m_sValue;
  }

  public boolean isImportant ()
  {
    return m_bIsImportant;
  }

  @Nonnull
  public String getAsCSSString (@Nonnull final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    CSSVersionHelper.checkVersionRequirements (eVersion, m_aProperty.getProp ());
    return m_aProperty.getProp ().getName () + CCSS.SEPARATOR_PROPERTY_VALUE + m_sValue + CCSS.DEFINITION_END;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSValue))
      return false;
    final CSSValue rhs = (CSSValue) o;
    // Important flag is contained in the value!
    return m_aProperty.getProp ().equals (rhs.m_aProperty.getProp ()) && m_sValue.equals (rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aProperty.getProp ()).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("property", m_aProperty)
                                       .append ("value", m_sValue)
                                       .append ("important", m_bIsImportant)
                                       .toString ();
  }
}
