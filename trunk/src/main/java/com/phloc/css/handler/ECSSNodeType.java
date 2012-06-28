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
import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.ECSSVersion;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.ParserCSS21TreeConstants;
import com.phloc.css.parser.ParserCSS30TreeConstants;

/**
 * Maps the different parser tokens between the 2.1 and the 3.0 parser.
 * 
 * @author philip
 */
public enum ECSSNodeType
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
  MATH (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTMATH),
  MATH_SUMOPERATOR (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTSUMOPERATOR),
  MATH_PRODUCT (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTPRODUCT),
  MATH_PRODUCTOPERATOR (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTPRODUCTOPERATOR),
  MATH_UNIT (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTUNIT),
  // media stuff
  MEDIALIST (ParserCSS21TreeConstants.JJTMEDIALIST, ParserCSS30TreeConstants.JJTMEDIALIST),
  MEDIUM (ParserCSS21TreeConstants.JJTMEDIUM, ParserCSS30TreeConstants.JJTMEDIUM),
  MEDIAQUERY (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTMEDIAQUERY),
  MEDIAMODIFIER (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTMEDIAMODIFIER),
  MEDIAEXPR (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTMEDIAEXPR),
  MEDIAFEATURE (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTMEDIAFEATURE),
  // page stuff
  PSEUDOPAGE (ParserCSS21TreeConstants.JJTPSEUDOPAGE, ParserCSS30TreeConstants.JJTPSEUDOPAGE),
  // animations
  KEYFRAMESRULE (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTKEYFRAMESRULE),
  KEYFRAMESIDENTIFIER (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTKEYFRAMESIDENTIFIER),
  KEYFRAMESSELECTOR (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTKEYFRAMESSELECTOR),
  SINGLEKEYFRAMESELECTOR (CGlobal.ILLEGAL_UINT, ParserCSS30TreeConstants.JJTSINGLEKEYFRAMESELECTOR),
  // rest
  ERROR_SKIPTO (ParserCSS21TreeConstants.JJTERROR_SKIPTO, ParserCSS30TreeConstants.JJTERROR_SKIPTO);

  private static final Logger s_aLogger = LoggerFactory.getLogger (ECSSNodeType.class);

  private final int m_nParserType21;
  private final int m_nParserType30;

  private ECSSNodeType (final int nParserType21, final int nParserType30)
  {
    m_nParserType21 = nParserType21;
    m_nParserType30 = nParserType30;
  }

  /**
   * Get the internal node type for the specified CSS version
   * 
   * @param eVersion
   *          CSS version to use
   * @return The internal node type for this node type or
   *         {@link CGlobal#ILLEGAL_UINT} if this node type is not supported by
   *         the passed version
   */
  public int getParserNodeType (@Nonnull final ECSSVersion eVersion)
  {
    switch (eVersion)
    {
      case CSS21:
        return m_nParserType21;
      case CSS30:
        return m_nParserType30;
      default:
        throw new IllegalStateException ("Illegal version provided: " + eVersion);
    }
  }

  /**
   * Check if the passed parser node is of <code>this</code> type.
   * 
   * @param aParserNode
   *          The parser node to be checked.
   * @param eVersion
   *          The desired version.
   * @return <code>true</code> if <code>this</code> is the type of the passed
   *         parser node in the given version
   */
  public boolean isNode (@Nonnull final CSSNode aParserNode, @Nonnull final ECSSVersion eVersion)
  {
    return aParserNode.getNodeType () == getParserNodeType (eVersion);
  }

  @Nonnull
  public String getNodeName (@Nonnull final ECSSVersion eVersion)
  {
    switch (eVersion)
    {
      case CSS21:
        // Special handling in case a CSS 3.0 node is requested, and this node
        // type is not supported in CSS 2.1
        return m_nParserType21 == CGlobal.ILLEGAL_UINT ? name ()
                                                      : ParserCSS21TreeConstants.jjtNodeName[m_nParserType21];
      case CSS30:
        return ParserCSS30TreeConstants.jjtNodeName[m_nParserType30];
      default:
        throw new IllegalStateException ("Illegal version provided: " + eVersion);
    }
  }

  @Nullable
  public static ECSSNodeType getNodeType (@Nonnull final CSSNode aParserNode, @Nonnull final ECSSVersion eVersion)
  {
    for (final ECSSNodeType eNodeType : values ())
      if (eNodeType.isNode (aParserNode, eVersion))
        return eNodeType;
    return null;
  }

  @Nullable
  public static String getNodeName (@Nonnull final CSSNode aParserNode, @Nonnull final ECSSVersion eVersion)
  {
    final ECSSNodeType eNodeType = getNodeType (aParserNode, eVersion);
    if (eNodeType != null)
      return eNodeType.getNodeName (eVersion);
    s_aLogger.warn ("Unsupported node type " + aParserNode.getNodeType () + " in version " + eVersion);
    return null;
  }

  private static void _dumpRecursive (@Nonnull final CSSNode aParserNode,
                                      @Nonnull final ECSSVersion eVersion,
                                      @Nonnull final StringBuilder aSB,
                                      @Nonnull final String sPrefix)
  {
    aSB.append (sPrefix).append (getNodeName (aParserNode, eVersion));
    if (aParserNode.hasText ())
      aSB.append ('[').append (aParserNode.getText ()).append (']');
    aSB.append ('\n');
    for (final CSSNode aChildNode : aParserNode)
      _dumpRecursive (aChildNode, eVersion, aSB, sPrefix + "  ");
  }

  @Nonnull
  @Nonempty
  public static String getDump (@Nonnull final CSSNode aParserNode, @Nonnull final ECSSVersion eVersion)
  {
    final StringBuilder aSB = new StringBuilder ();
    _dumpRecursive (aParserNode, eVersion, aSB, "");
    return aSB.toString ();
  }
}
