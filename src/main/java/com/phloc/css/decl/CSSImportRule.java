package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSWriteable;

public final class CSSImportRule implements ICSSWriteable
{
  private String m_sLocation;
  private final List <String> m_aMedia = new ArrayList <String> ();

  public CSSImportRule (@Nonnull final String sLocation)
  {
    setLocation (sLocation);
  }

  public void addMedium (@Nonnull final String sMedium)
  {
    if (StringHelper.hasNoText (sMedium))
      throw new IllegalArgumentException ("medium");
    m_aMedia.add (sMedium);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <String> getAllMedia ()
  {
    return ContainerHelper.makeUnmodifiable (m_aMedia);
  }

  @Nonnull
  public String getLocation ()
  {
    return m_sLocation;
  }

  public void setLocation (@Nonnull final String sLocation)
  {
    if (StringHelper.hasNoText (sLocation))
      throw new IllegalArgumentException ("location may not be empty");
    m_sLocation = sLocation;
  }

  public String getAsCSSString (final ECSSVersion eVersion, final boolean bOptimizedOutput)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("@import '").append (m_sLocation).append ('\'');
    if (!m_aMedia.isEmpty ())
    {
      aSB.append (' ');
      boolean bFirst = true;
      for (final String sMedium : m_aMedia)
      {
        if (bFirst)
          bFirst = false;
        else
          aSB.append (bOptimizedOutput ? "," : ", ");
        aSB.append (sMedium);
      }
    }
    return aSB.append (";\n").toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSImportRule))
      return false;
    final CSSImportRule rhs = (CSSImportRule) o;
    return m_sLocation.equals (rhs.m_sLocation) && m_aMedia.equals (rhs.m_aMedia);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sLocation).append (m_aMedia).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("location", m_sLocation).append ("media", m_aMedia).toString ();
  }
}
