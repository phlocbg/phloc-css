package com.phloc.css.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.phloc.commons.string.ToStringGenerator;

public class CSSNode implements Node, Iterable <CSSNode>
{
  private final int m_nType;
  private CSSNode m_aParent;
  private CSSNode [] m_aChildren;
  private Object m_aValue;
  private String m_sText;

  public CSSNode (final int nType)
  {
    m_nType = nType;
  }

  public void jjtOpen ()
  {}

  public void jjtClose ()
  {}

  public void jjtSetParent (final Node aNode)
  {
    m_aParent = (CSSNode) aNode;
  }

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
        final CSSNode aTmpArray[] = new CSSNode [nIndex + 1];
        System.arraycopy (m_aChildren, 0, aTmpArray, 0, m_aChildren.length);
        m_aChildren = aTmpArray;
      }
    m_aChildren[nIndex] = (CSSNode) aNode;
  }

  public CSSNode jjtGetChild (final int nIndex)
  {
    return m_aChildren[nIndex];
  }

  public int jjtGetNumChildren ()
  {
    return m_aChildren == null ? 0 : m_aChildren.length;
  }

  public void setValue (final Object aValue)
  {
    m_aValue = aValue;
  }

  public Object getValue ()
  {
    return m_aValue;
  }

  public void setText (final String sText)
  {
    m_sText = sText;
  }

  public void appendText (final String sText)
  {
    if (m_sText == null)
      m_sText = sText;
    else
      m_sText += sText;
  }

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

  public Iterator <CSSNode> iterator ()
  {
    final List <CSSNode> aChildren = new ArrayList <CSSNode> (jjtGetNumChildren ());
    if (m_aChildren != null)
      for (final CSSNode aChildNode : m_aChildren)
        if (aChildNode != null)
          aChildren.add (aChildNode);
    return aChildren.iterator ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("type", m_nType)
                                       .appendIfNotNull ("parent", m_aParent == null ? null : m_aParent.m_nType)
                                       .appendIfNotNull ("value", m_aValue)
                                       .appendIfNotNull ("text", m_sText)
                                       .appendIfNotNull ("children", m_aChildren)
                                       .toString ();
  }
}
