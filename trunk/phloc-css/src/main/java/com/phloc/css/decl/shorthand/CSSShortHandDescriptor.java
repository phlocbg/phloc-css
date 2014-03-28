package com.phloc.css.decl.shorthand;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.property.CSSPropertyFree;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.property.ICSSProperty;
import com.phloc.css.writer.CSSWriterSettings;

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
  private final int m_nMinArgCount;
  private final int m_nMaxArgCount;

  public CSSShortHandDescriptor (@Nonnull final ECSSProperty eProperty,
                                 @Nonnull @Nonempty final CSSPropertyWithDefaultValue... aSubProperties)
  {
    ValueEnforcer.notNull (eProperty, "Property");
    ValueEnforcer.notEmptyNoNullValue (aSubProperties, "SubProperties");
    m_eProperty = eProperty;
    m_aSubProperties = ContainerHelper.newList (aSubProperties);

    // Check that a free text property may only be at the end
    int nMinArgs = 0;
    int nMaxArgs = 0;
    final int nMax = aSubProperties.length;
    for (int i = 0; i < nMax; ++i)
    {
      final CSSPropertyWithDefaultValue aSubProperty = aSubProperties[i];
      final ICSSProperty aProp = aSubProperty.getProperty ();
      if (aProp instanceof CSSPropertyFree && i < nMax - 1)
        throw new IllegalArgumentException ("The SubProperty " +
                                            aSubProperty +
                                            " may not use an unspecified CSSPropertyFree except for the last element!");

      nMinArgs += aProp.getMinimumArgumentCount ();
      nMaxArgs += aProp.getMaximumArgumentCount ();
    }
    m_nMinArgCount = nMinArgs;
    m_nMaxArgCount = nMaxArgs;
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

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSDeclaration> getSplitIntoPieces (@Nonnull final CSSDeclaration aDeclaration)
  {
    ValueEnforcer.notNull (aDeclaration, "Declaration");

    // Check that declaration matches this property
    if (!aDeclaration.getProperty ().equals (m_eProperty.getName ()))
      throw new IllegalArgumentException ("Cannot split a '" +
                                          aDeclaration.getProperty () +
                                          "' as a '" +
                                          m_eProperty.getName () +
                                          "'");

    // Result list initially contains all the default values
    final int nSubProps = m_aSubProperties.size ();
    final List <CSSDeclaration> ret = new ArrayList <CSSDeclaration> ();
    final List <ICSSExpressionMember> aExpressionMembers = aDeclaration.getExpression ().getAllMembers ();
    int nExpressionMemberIndex = 0;
    final CSSWriterSettings aCWS = new CSSWriterSettings (ECSSVersion.CSS30, false);
    for (final CSSPropertyWithDefaultValue aSubProp : m_aSubProperties)
    {
      final ICSSProperty aProperty = aSubProp.getProperty ();
      final int nMinArgs = aProperty.getMinimumArgumentCount ();
      // Always use minimum number of arguments
      final StringBuilder aSB = new StringBuilder ();
      for (int i = 0; i < nMinArgs; ++i)
      {
        final ICSSExpressionMember aMember = aExpressionMembers.get (nExpressionMemberIndex);
        final String sValue = aMember.getAsCSSString (aCWS, 0);
        if (aSB.length () > 0)
          aSB.append (' ');
        aSB.append (sValue);
      }

      if (aProperty.isValidValue (aSB.toString ()))
      {
        // We found a match
        final CSSExpression aExpr = new CSSExpression ();
        for (int i = 0; i < nMinArgs; ++i)
          aExpr.addMember (aExpressionMembers.get (nExpressionMemberIndex + i));
        ret.add (new CSSDeclaration (aSubProp.getProperty ().getProp ().getName (), aExpr));
        nExpressionMemberIndex += nMinArgs;
      }
      else
      {
        // not a match for this property
        // assign default value
        final CSSExpression aExpr = new CSSExpression ();
        aExpr.addMember (new CSSExpressionMemberTermSimple (aSubProp.getDefaultValue ()));
        ret.add (new CSSDeclaration (aSubProp.getProperty ().getProp ().getName (), aExpr));
      }
    }

    return ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("property", m_eProperty)
                                       .append ("subProperties", m_aSubProperties)
                                       .toString ();
  }
}
