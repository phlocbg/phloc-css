/**
 * Copyright (C) 2006-2013 phloc systems
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a CSS calc element
 * 
 * @author philip
 */
@Immutable
public final class CSSExpressionMemberMathUnitProduct implements ICSSExpressionMathMember, ICSSSourceLocationAware
{
  private final CSSExpressionMemberMathProduct m_aProduct;

  public CSSExpressionMemberMathUnitProduct (@Nonnull @Nonempty final CSSExpressionMemberMathProduct aProduct)
  {
    if (aProduct == null)
      throw new NullPointerException ("product");
    m_aProduct = aProduct;
  }

  @Nonnull
  public CSSExpressionMemberMathProduct getProduct ()
  {
    return m_aProduct;
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    aSettings.checkVersionRequirements (this);
    return "(" + m_aProduct.getAsCSSString (aSettings, nIndentLevel) + ")";
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return ECSSVersion.CSS30;
  }

  @Nullable
  public CSSSourceLocation getSourceLocation ()
  {
    return m_aProduct.getSourceLocation ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSExpressionMemberMathUnitProduct))
      return false;
    final CSSExpressionMemberMathUnitProduct rhs = (CSSExpressionMemberMathUnitProduct) o;
    return m_aProduct.equals (rhs.m_aProduct);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aProduct).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("product", m_aProduct).toString ();
  }
}
