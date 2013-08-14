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
package com.phloc.css.handler;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclarationList;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.parser.CSSNode;

/**
 * This is the central class for reading and parsing CSS from an input stream.
 * 
 * @author Philip Helger
 */
@Immutable
public final class CSSHandler
{
  private CSSHandler ()
  {}

  /**
   * Create a {@link CascadingStyleSheet} object from a parsed object.
   * 
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aNode
   *        The parsed CSS object to read. May not be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @Nonnull
  public static CascadingStyleSheet readCascadingStyleSheetFromNode (@Nonnull final ECSSVersion eVersion,
                                                                     @Nonnull final CSSNode aNode)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    if (aNode == null)
      throw new NullPointerException ("node");
    if (!ECSSNodeType.ROOT.isNode (aNode, eVersion))
      throw new IllegalArgumentException ("Passed node is not a root node!");

    return new CSSNodeToDomainObject (eVersion).createCascadingStyleSheetFromNode (aNode);
  }

  /**
   * Create a {@link CSSDeclarationList} object from a parsed object.
   * 
   * @param eVersion
   *        The CSS version to use. May not be <code>null</code>.
   * @param aNode
   *        The parsed CSS object to read. May not be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @Nonnull
  public static CSSDeclarationList readDeclarationListFromNode (@Nonnull final ECSSVersion eVersion,
                                                                @Nonnull final CSSNode aNode)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    if (aNode == null)
      throw new NullPointerException ("node");
    if (!ECSSNodeType.STYLEDECLARATION.isNode (aNode, eVersion))
      throw new IllegalArgumentException ("Passed node is not a style declaration node!");

    return new CSSNodeToDomainObject (eVersion).createDeclarationListFromNode (aNode);
  }
}
