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
package com.phloc.css.decl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;

public final class CascadingStyleSheet
{
  private final List <CSSImportRule> m_aImportRules = new ArrayList <CSSImportRule> ();
  private final List <ICSSTopLevelRule> m_aRules = new ArrayList <ICSSTopLevelRule> ();

  public CascadingStyleSheet ()
  {}

  public void addImportRule (final CSSImportRule aImportRule)
  {
    if (aImportRule == null)
      throw new NullPointerException ("importRule");
    m_aImportRules.add (aImportRule);
  }

  public void addRule (@Nonnull final ICSSTopLevelRule aStyleRule)
  {
    if (aStyleRule == null)
      throw new NullPointerException ("styleRule");
    m_aRules.add (aStyleRule);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <CSSImportRule> getAllImportRules ()
  {
    return ContainerHelper.makeUnmodifiable (m_aImportRules);
  }

  @Nonnull
  @ReturnsImmutableObject
  public List <ICSSTopLevelRule> getAllRules ()
  {
    return ContainerHelper.makeUnmodifiable (m_aRules);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CascadingStyleSheet))
      return false;
    final CascadingStyleSheet rhs = (CascadingStyleSheet) o;
    return m_aImportRules.equals (rhs.m_aImportRules) && m_aRules.equals (rhs.m_aRules);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aImportRules).append (m_aRules).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("importRules", m_aImportRules).append ("rules", m_aRules).toString ();
  }
}
