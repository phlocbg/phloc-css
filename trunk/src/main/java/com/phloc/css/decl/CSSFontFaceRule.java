package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;

/**
 * Represents a single @font-face rule.
 * 
 * @author philip
 */
public final class CSSFontFaceRule implements ICSSTopLevelRule
{
  private final List <CSSDeclaration> m_aDeclarations = new ArrayList <CSSDeclaration> ();

  public CSSFontFaceRule ()
  {}

  public void addDeclaration (@Nonnull final CSSDeclaration aDeclaration)
  {
    if (aDeclaration == null)
      throw new NullPointerException ("declaration");
    m_aDeclarations.add (aDeclaration);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <CSSDeclaration> getAllDeclarations ()
  {
    return ContainerHelper.makeUnmodifiable (m_aDeclarations);
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ("@font-face");
    aSB.append (bOptimizedOutput ? "{" : " {");
    if (!bOptimizedOutput && m_aDeclarations.size () > 1)
      aSB.append ('\n');

    final int nDeclCount = m_aDeclarations.size ();
    if (nDeclCount == 1)
      aSB.append (m_aDeclarations.get (0).getAsCSSString (eVersion, bOptimizedOutput));
    else
      if (nDeclCount > 1)
      {
        for (final CSSDeclaration aDeclaration : m_aDeclarations)
        {
          if (!bOptimizedOutput)
            aSB.append ("  ");
          aSB.append (aDeclaration.getAsCSSString (eVersion, bOptimizedOutput));
          if (!bOptimizedOutput)
            aSB.append ('\n');
        }
      }
    aSB.append (bOptimizedOutput ? "}" : "}\n");
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSFontFaceRule))
      return false;
    final CSSFontFaceRule rhs = (CSSFontFaceRule) o;
    return m_aDeclarations.equals (rhs.m_aDeclarations);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aDeclarations).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("declarations", m_aDeclarations).toString ();
  }
}
