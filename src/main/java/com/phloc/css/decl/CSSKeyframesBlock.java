package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.ICSSWriteable;

@NotThreadSafe
public final class CSSKeyframesBlock implements ICSSWriteable, ICSSVersionAware
{
  private final List <String> m_aKeyframesSelectors;
  private final List <CSSDeclaration> m_aDeclarations = new ArrayList <CSSDeclaration> ();

  public CSSKeyframesBlock (@Nonnull @Nonempty final List <String> aKeyframesSelectors)
  {
    if (ContainerHelper.isEmpty (aKeyframesSelectors))
      throw new IllegalArgumentException ("keyframesSelectors");
    if (ContainerHelper.containsAnyNullElement (aKeyframesSelectors))
      throw new IllegalArgumentException ("keyframesSelectors contains at least one null element");
    m_aKeyframesSelectors = ContainerHelper.newList (aKeyframesSelectors);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <String> getAllKeyframesSelectors ()
  {
    return ContainerHelper.newList (m_aKeyframesSelectors);
  }

  public void addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    if (aDeclaration == null)
      throw new NullPointerException ("declaration");
    m_aDeclarations.add (aDeclaration);
  }

  @Nonnull
  public EChange removeDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    return EChange.valueOf (m_aDeclarations.remove (aDeclaration));
  }

  @Nonnull
  public EChange removeDeclaration (@Nonnegative final int nDeclarationIndex)
  {
    if (nDeclarationIndex < 0 || nDeclarationIndex >= m_aDeclarations.size ())
      return EChange.UNCHANGED;
    return EChange.valueOf (m_aDeclarations.remove (nDeclarationIndex) != null);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <CSSDeclaration> getAllDeclarations ()
  {
    return ContainerHelper.newList (m_aDeclarations);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final int nDeclCount = m_aDeclarations.size ();

    final StringBuilder aSB = new StringBuilder ();

    // Emit all selectors
    for (final String sSelector : m_aKeyframesSelectors)
    {
      if (aSB.length () > 0)
        aSB.append (bOptimizedOutput ? "," : ", ");
      aSB.append (sSelector);
    }

    if (nDeclCount == 0)
    {
      // Skip the whole rule
      // Leads to different results in comparison!
      if (false && bOptimizedOutput)
        return "";
      aSB.append (bOptimizedOutput ? "{}" : " {}");
    }
    else
    {
      if (nDeclCount == 1)
      {
        // A single declaration
        aSB.append (bOptimizedOutput ? "{" : " { ");
        aSB.append (ContainerHelper.getFirstElement (m_aDeclarations).getAsCSSString (eVersion, bOptimizedOutput));
        aSB.append (bOptimizedOutput ? "}" : " }");
      }
      else
      {
        // More than one declaration
        aSB.append (bOptimizedOutput ? "{" : " {\n");
        for (final CSSDeclaration aDeclaration : m_aDeclarations)
        {
          if (!bOptimizedOutput)
            aSB.append ("    ");
          aSB.append (aDeclaration.getAsCSSString (eVersion, bOptimizedOutput));
          if (!bOptimizedOutput)
            aSB.append ('\n');
        }
        aSB.append (bOptimizedOutput ? "}" : "  }");
      }
    }
    return aSB.toString ();
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSKeyframesBlock))
      return false;
    final CSSKeyframesBlock rhs = (CSSKeyframesBlock) o;
    return m_aKeyframesSelectors.equals (rhs.m_aKeyframesSelectors) && m_aDeclarations.equals (rhs.m_aDeclarations);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aKeyframesSelectors).append (m_aDeclarations).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("keyframesSelectors", m_aKeyframesSelectors)
                                       .append ("declarations", m_aDeclarations)
                                       .toString ();
  }
}
