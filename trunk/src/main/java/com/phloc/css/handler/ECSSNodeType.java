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
package com.phloc.css.handler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.CGlobal;
import com.phloc.css.ECSSVersion;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.ParserCSS21TreeConstants;
import com.phloc.css.parser.ParserCSS30TreeConstants;

/**
 * Maps the different parser tokens between the 2.1 and the 3.0 parser.
 * 
 * @author philip
 */
enum ECSSNodeType
{
  ROOT (ParserCSS21TreeConstants.JJTROOT, ParserCSS30TreeConstants.JJTROOT),
  // top level
  CHARSET (ParserCSS21TreeConstants.JJTCHARSET, ParserCSS30TreeConstants.JJTCHARSET),
  UNKNOWNRULE (ParserCSS21TreeConstants.JJTUNKNOWNRULE, ParserCSS30TreeConstants.JJTUNKNOWNRULE),
  STYLERULE (ParserCSS21TreeConstants.JJTSTYLERULE, ParserCSS30TreeConstants.JJTSTYLERULE),
  IMPORTRULE (ParserCSS21TreeConstants.JJTIMPORTRULE, ParserCSS30TreeConstants.JJTIMPORTRULE),
  PAGERULE (ParserCSS21TreeConstants.JJTPAGERULE, ParserCSS30TreeConstants.JJTPAGERULE),
  MEDIARULE (ParserCSS21TreeConstants.JJTMEDIARULE, ParserCSS30TreeConstants.JJTMEDIARULE),
  FONTFACERULE (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTFONTFACERULE),
  // top level -- style rule
  SELECTOR (ParserCSS21TreeConstants.JJTSELECTOR, ParserCSS30TreeConstants.JJTSELECTOR),
  DECLARATION (ParserCSS21TreeConstants.JJTDECLARATION, ParserCSS30TreeConstants.JJTDECLARATION),
  // style rule -- selector
  NAMESPACEPREFIX (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTNAMESPACEPREFIX),
  UNIVERSAL (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTUNIVERSAL),
  ELEMENTNAME (ParserCSS21TreeConstants.JJTELEMENTNAME, ParserCSS30TreeConstants.JJTELEMENTNAME),
  HASH (ParserCSS21TreeConstants.JJTHASH, ParserCSS30TreeConstants.JJTHASH),
  CLASS (ParserCSS21TreeConstants.JJTCLASS, ParserCSS30TreeConstants.JJTCLASS),
  PSEUDO (ParserCSS21TreeConstants.JJTPSEUDO, ParserCSS30TreeConstants.JJTPSEUDO),
  NEGATION (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTNEGATION),
  ATTRIB (ParserCSS21TreeConstants.JJTATTRIB, ParserCSS30TreeConstants.JJTATTRIB),
  ATTRIBOPERATOR (ParserCSS21TreeConstants.JJTATTRIBOPERATOR, ParserCSS30TreeConstants.JJTATTRIBOPERATOR),
  ATTRIBVALUE (ParserCSS21TreeConstants.JJTATTRIBVALUE, ParserCSS30TreeConstants.JJTATTRIBVALUE),
  COMBINATOR (ParserCSS21TreeConstants.JJTCOMBINATOR, ParserCSS30TreeConstants.JJTCOMBINATOR),
  NTH (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTNTH),
  // style rule -- declaration
  PROPERTY (ParserCSS21TreeConstants.JJTPROPERTY, ParserCSS30TreeConstants.JJTPROPERTY),
  IMPORTANT (ParserCSS21TreeConstants.JJTIMPORTANT, ParserCSS30TreeConstants.JJTIMPORTANT),
  // declaration -- expression
  EXPR (ParserCSS21TreeConstants.JJTEXPR, ParserCSS30TreeConstants.JJTEXPR),
  TERM (ParserCSS21TreeConstants.JJTTERM, ParserCSS30TreeConstants.JJTTERM),
  OPERATOR (ParserCSS21TreeConstants.JJTOPERATOR, ParserCSS30TreeConstants.JJTOPERATOR),
  FUNCTION (ParserCSS21TreeConstants.JJTFUNCTION, ParserCSS30TreeConstants.JJTFUNCTION),
  // media stuff
  MEDIALIST (ParserCSS21TreeConstants.JJTMEDIALIST, ParserCSS30TreeConstants.JJTMEDIALIST),
  MEDIUM (ParserCSS21TreeConstants.JJTMEDIUM, ParserCSS30TreeConstants.JJTMEDIUM),
  // page stuff
  PSEUDOPAGE (ParserCSS21TreeConstants.JJTPSEUDOPAGE, ParserCSS30TreeConstants.JJTPSEUDOPAGE),
  // rest
  ERROR_SKIPTO (ParserCSS21TreeConstants.JJTERROR_SKIPTO, ParserCSS30TreeConstants.JJTERROR_SKIPTO);

  private static final Logger s_aLogger = LoggerFactory.getLogger (ECSSNodeType.class);
  private int m_nType21;
  private int m_nType30;

  private ECSSNodeType (final int nType21, final int nType30)
  {
    m_nType21 = nType21;
    m_nType30 = nType30;
  }

  /**
   * Get the internal node type for the specified CSS version
   * 
   * @param eVersion
   *          CSS version to use
   * @return The internal node type for this node type
   */
  public int getNodeType (@Nonnull final ECSSVersion eVersion)
  {
    switch (eVersion)
    {
      case CSS21:
        return m_nType21;
      case CSS30:
        return m_nType30;
      default:
        throw new IllegalStateException ("Illegal version provided: " + eVersion);
    }
  }

  public boolean isNode (@Nonnull final CSSNode aNode, @Nonnull final ECSSVersion eVersion)
  {
    return aNode.getNodeType () == getNodeType (eVersion);
  }

  @Nonnull
  public String getNodeName (@Nonnull final ECSSVersion eVersion)
  {
    switch (eVersion)
    {
      case CSS21:
        return ParserCSS21TreeConstants.jjtNodeName[m_nType21];
      case CSS30:
        return ParserCSS30TreeConstants.jjtNodeName[m_nType30];
      default:
        throw new IllegalStateException ("Illegal version provided: " + eVersion);
    }
  }

  @Nullable
  public static String getNodeName (@Nonnull final CSSNode aNode, @Nonnull final ECSSVersion eVersion)
  {
    for (final ECSSNodeType eNodeType : values ())
      if (eNodeType.isNode (aNode, eVersion))
        return eNodeType.getNodeName (eVersion);
    s_aLogger.warn ("Unsupported node type " + aNode.getNodeType () + " in version " + eVersion);
    return null;
  }

  private static String _nodeString (@Nonnull final CSSNode aNode, @Nonnull final ECSSVersion eVersion)
  {
    final String s = getNodeName (aNode, eVersion);
    if (aNode.hasText ())
      return s + "[" + aNode.getText () + "]";
    return s;
  }

  private static void _recursiveDump (@Nonnull final CSSNode aNode,
                                      @Nonnull final ECSSVersion eVersion,
                                      @Nonnull final StringBuilder aSB,
                                      @Nonnull final String sPrefix)
  {
    aSB.append (sPrefix).append (_nodeString (aNode, eVersion)).append ('\n');
    for (final CSSNode aChildNode : aNode)
      _recursiveDump (aChildNode, eVersion, aSB, sPrefix + "  ");
  }

  public static void dump (@Nonnull final CSSNode aNode, @Nonnull final ECSSVersion eVersion)
  {
    final StringBuilder aSB = new StringBuilder ();
    _recursiveDump (aNode, eVersion, aSB, "");
    s_aLogger.info (aSB.toString ());
  }
}
