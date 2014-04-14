package com.phloc.css.decl.shorthand;

import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.OverrideOnDemand;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.property.ECSSProperty;

/**
 * A special {@link CSSShortHandDescriptor} implementation for margin and
 * padding
 *
 * @author Philip Helger
 */
public class CSSShortHandDescriptorMarginPadding extends CSSShortHandDescriptor
{
  public CSSShortHandDescriptorMarginPadding (@Nonnull final ECSSProperty eProperty,
                                              @Nonnull @Nonempty final CSSPropertyWithDefaultValue... aSubProperties)
  {
    super (eProperty, aSubProperties);
  }

  @Override
  @OverrideOnDemand
  protected void modifyExpressionMembers (@Nonnull final List <ICSSExpressionMember> aExpressionMembers)
  {
    final int nSize = aExpressionMembers.size ();
    if (nSize == 1)
    {
      // 4px -> 4px 4px 4px 4px
      final ICSSExpressionMember aMember = aExpressionMembers.get (0);
      for (int i = 0; i < 3; ++i)
        aExpressionMembers.add (aMember.getClone ());
    }
    else
      if (nSize == 2)
      {
        // 4px 10px -> 4px 10px 4px 10px
        final ICSSExpressionMember aMemberY = aExpressionMembers.get (0);
        final ICSSExpressionMember aMemberX = aExpressionMembers.get (1);
        aExpressionMembers.add (aMemberY.getClone ());
        aExpressionMembers.add (aMemberX.getClone ());
      }
      else
        if (nSize == 3)
        {
          // 4px 10px 6px -> 4px 10px 6px 10px
          final ICSSExpressionMember aMemberX = aExpressionMembers.get (1);
          aExpressionMembers.add (aMemberX.getClone ());
        }
    // else nothing to do
  }
}
