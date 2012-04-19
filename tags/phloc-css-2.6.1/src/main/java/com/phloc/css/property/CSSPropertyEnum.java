/**
 * Copyright (C) 2006-2012 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phloc.css.property;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.ECSSProperty;

public class CSSPropertyEnum extends AbstractCSSProperty
{
  protected final Set <String> m_aEnumValues;

  public CSSPropertyEnum (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp);
    m_aEnumValues = new HashSet <String> (aEnumValues.length);
    for (final String sPotentialValue : aEnumValues)
    {
      if (StringHelper.hasNoText (sPotentialValue))
        throw new IllegalArgumentException ("At least one enumeration value is empty");
      m_aEnumValues.add (sPotentialValue);
    }
    if (m_aEnumValues.isEmpty ())
      throw new IllegalArgumentException ("At least one enumeration value needs to be passed!");
  }

  public CSSPropertyEnum (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    super (eProp);
    m_aEnumValues = new HashSet <String> ();
    for (final String sPotentialValue : aEnumValues)
    {
      if (StringHelper.hasNoText (sPotentialValue))
        throw new IllegalArgumentException ("At least one enumeration value is empty");
      m_aEnumValues.add (sPotentialValue);
    }
    if (m_aEnumValues.isEmpty ())
      throw new IllegalArgumentException ("At least one enumeration value needs to be passed!");
  }

  private CSSPropertyEnum (@Nonnull final ECSSProperty eProp, @Nonnull @Nonempty final Set <String> aEnumValues)
  {
    super (eProp);
    m_aEnumValues = new HashSet <String> (aEnumValues);
  }

  public boolean isValidValue (@Nullable final String sValue)
  {
    return m_aEnumValues.contains (sValue);
  }

  @Nonnull
  public CSSPropertyEnum getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnum (eProp, m_aEnumValues);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("enumValues", m_aEnumValues).toString ();
  }
}
