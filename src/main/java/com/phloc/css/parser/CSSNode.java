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
package com.phloc.css.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;

/**
 * This class represents a simple node in the tree built by jjtree.
 * 
 * @author philip
 */
public final class CSSNode implements Node, Iterable <CSSNode>
{
  private final int m_nType;
  private CSSNode m_aParent;
  private CSSNode [] m_aChildren;
  private Object m_aValue;
  private String m_sText;
  private Token m_aFirstToken;
  private Token m_aLastToken;

  public CSSNode (final int nType)
  {
    m_nType = nType;
  }

  public void jjtOpen ()
  {}

  public void jjtClose ()
  {}

  public void jjtSetParent (@Nullable final Node aNode)
  {
    m_aParent = (CSSNode) aNode;
  }

  @Nullable
  public Node jjtGetParent ()
  {
    return m_aParent;
  }

  /**
   * Called from the highest index to the lowest index!
   */
  public void jjtAddChild (final Node aNode, final int nIndex)
  {
    if (m_aChildren == null)
      m_aChildren = new CSSNode [nIndex + 1];
    else
      if (nIndex >= m_aChildren.length)
      {
        // Does not really occur here
        final CSSNode [] aTmpArray = new CSSNode [nIndex + 1];
        System.arraycopy (m_aChildren, 0, aTmpArray, 0, m_aChildren.length);
        m_aChildren = aTmpArray;
      }
    m_aChildren[nIndex] = (CSSNode) aNode;
  }

  public CSSNode jjtGetChild (final int nIndex)
  {
    return m_aChildren[nIndex];
  }

  @Nonnegative
  public int jjtGetNumChildren ()
  {
    return m_aChildren == null ? 0 : m_aChildren.length;
  }

  // The following 4 methods are required for JJTree option TRACK_TOKENS=true

  @Nullable
  public Token jjtGetFirstToken ()
  {
    return m_aFirstToken;
  }

  public void jjtSetFirstToken (@Nonnull final Token aFirstToken)
  {
    m_aFirstToken = aFirstToken;
  }

  @Nullable
  public Token jjtGetLastToken ()
  {
    return m_aLastToken;
  }

  public void jjtSetLastToken (@Nonnull final Token aLastToken)
  {
    m_aLastToken = aLastToken;
  }

  public void setValue (@Nullable final Object aValue)
  {
    m_aValue = aValue;
  }

  @Nullable
  public Object getValue ()
  {
    return m_aValue;
  }

  public void setText (@Nullable final String sText)
  {
    m_sText = sText;
  }

  public void appendText (@Nonnull final String sText)
  {
    if (m_sText == null)
      m_sText = sText;
    else
      m_sText += sText;
  }

  @Nullable
  public String getText ()
  {
    return m_sText;
  }

  public boolean hasText ()
  {
    return m_sText != null;
  }

  public int getNodeType ()
  {
    return m_nType;
  }

  @Nonnull
  public Iterator <CSSNode> iterator ()
  {
    final List <CSSNode> aChildren = new ArrayList <CSSNode> (jjtGetNumChildren ());
    if (m_aChildren != null)
      for (final CSSNode aChildNode : m_aChildren)
        if (aChildNode != null)
          aChildren.add (aChildNode);
    return aChildren.iterator ();
  }

  @Nullable
  public CSSSourceLocation getSourceLocation ()
  {
    if (m_aFirstToken == null)
    {
      if (m_aLastToken == null)
        return null;

      System.out.println ("LastToken present first last token is not!");
      return new CSSSourceLocation (m_aLastToken.beginLine,
                                    m_aLastToken.beginColumn,
                                    m_aLastToken.endLine,
                                    m_aLastToken.endColumn);
    }

    if (m_aLastToken == null)
    {
      System.out.println ("FirstToken present but last token is not!");
      return new CSSSourceLocation (m_aFirstToken.beginLine,
                                    m_aFirstToken.beginColumn,
                                    m_aFirstToken.endLine,
                                    m_aFirstToken.endColumn);
    }

    return new CSSSourceLocation (m_aFirstToken.beginLine,
                                  m_aFirstToken.beginColumn,
                                  m_aLastToken.endLine,
                                  m_aLastToken.endColumn);
  }

  public void dump (final String prefix)
  {
    System.out.println (prefix + toString ());
    if (m_aChildren != null)
      for (final CSSNode element : m_aChildren)
      {
        final CSSNode n = element;
        if (n != null)
          n.dump (prefix + " ");
      }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("type", m_nType)
                                       .appendIfNotNull ("parentType",
                                                         m_aParent == null ? null : Integer.valueOf (m_aParent.m_nType))
                                       .appendIfNotNull ("value", m_aValue)
                                       .appendIfNotNull ("text", m_sText)
                                       .append ("children#", m_aChildren == null ? 0 : m_aChildren.length)
                                       .appendIfNotNull ("firstToken", m_aFirstToken)
                                       .appendIfNotNull ("lastToken", m_aLastToken)
                                       .toString ();
  }
}
