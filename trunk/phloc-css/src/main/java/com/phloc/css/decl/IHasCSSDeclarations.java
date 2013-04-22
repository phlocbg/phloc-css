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

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.state.EChange;
import com.phloc.css.ICSSWriteable;

/**
 * Sanity interface for all objects having CSS declarations.
 * 
 * @author Philip Helger
 */
public interface IHasCSSDeclarations extends ICSSWriteable
{
  /**
   * Add a new declaration.
   * 
   * @param aDeclaration
   *        The declaration to be added. May not be <code>null</code>.
   */
  void addDeclaration (@Nonnull CSSDeclaration aDeclaration);

  /**
   * Remove the given declaration
   * 
   * @param aDeclaration
   *        The declaration to be removed. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if the declaration was successfully removed
   */
  @Nonnull
  EChange removeDeclaration (@Nonnull CSSDeclaration aDeclaration);

  /**
   * Remove the declaration at the specified index
   * 
   * @param nDeclarationIndex
   *        The index of the declaration to be removed. Must be &ge; 0.
   * @return {@link EChange#CHANGED} if the declaration was successfully
   *         removed, {@link EChange#UNCHANGED} if the index was invalid.
   */
  @Nonnull
  EChange removeDeclaration (@Nonnegative int nDeclarationIndex);

  /**
   * @return A mutable, non-<code>null</code> copy of all contained
   *         declarations.
   */
  @Nonnull
  @ReturnsMutableCopy
  List <CSSDeclaration> getAllDeclarations ();

  /**
   * @param nIndex
   *        The index to retrieve
   * @return The declaration at the specified index or <code>null</code> if the
   *         index is invalid
   */
  @Nullable
  CSSDeclaration getDeclarationAtIndex (@Nonnegative int nIndex);

  /**
   * @return <code>true</code> if at least one declaration is present,
   *         <code>false</code> if no declaration is present.
   */
  boolean hasDeclarations ();

  /**
   * @return The number of contained declarations. Always &ge; 0.
   */
  @Nonnegative
  int getDeclarationCount ();
}
