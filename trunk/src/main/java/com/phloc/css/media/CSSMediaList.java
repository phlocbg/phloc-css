package com.phloc.css.media;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Manages a set of {@link ECSSMedium} objects.
 * 
 * @author philip
 */
@NotThreadSafe
public final class CSSMediaList implements Serializable
{
  // Ordered but unique
  private final Set <ECSSMedium> m_aMedia = new LinkedHashSet <ECSSMedium> ();

  public CSSMediaList ()
  {}

  public CSSMediaList (@Nonnull final ECSSMedium eMedium)
  {
    addMedium (eMedium);
  }

  public CSSMediaList (@Nonnull final ECSSMedium... aMedia)
  {
    for (final ECSSMedium eMedium : aMedia)
      addMedium (eMedium);
  }

  public CSSMediaList (@Nullable final Iterable <ECSSMedium> aMedia)
  {
    if (aMedia != null)
      for (final ECSSMedium eMedium : aMedia)
        addMedium (eMedium);
  }

  @Nonnull
  public CSSMediaList addMedium (@Nonnull final ECSSMedium eMedium)
  {
    if (eMedium == null)
      throw new NullPointerException ("medium");
    m_aMedia.add (eMedium);
    return this;
  }

  public boolean hasMedia ()
  {
    return !m_aMedia.isEmpty ();
  }

  public boolean isForScreen ()
  {
    return m_aMedia.isEmpty () || m_aMedia.contains (ECSSMedium.SCREEN) || m_aMedia.contains (ECSSMedium.ALL);
  }

  @Nonnull
  @ReturnsImmutableObject
  public Set <ECSSMedium> getMedia ()
  {
    return ContainerHelper.makeUnmodifiable (m_aMedia);
  }

  @Nonnull
  public String getMediaString ()
  {
    if (m_aMedia.isEmpty ())
      return "";

    final StringBuilder aSB = new StringBuilder ();
    for (final ECSSMedium eMedia : m_aMedia)
    {
      if (aSB.length () > 0)
        aSB.append (", ");
      aSB.append (eMedia.getAttrValue ());
    }
    return aSB.toString ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSMediaList))
      return false;
    final CSSMediaList rhs = (CSSMediaList) o;
    return m_aMedia.equals (rhs.m_aMedia);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMedia).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("media", m_aMedia).toString ();
  }
}
