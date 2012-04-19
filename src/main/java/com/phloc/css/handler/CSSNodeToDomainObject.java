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

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberFunction;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSSelectorAttribute;
import com.phloc.css.decl.CSSSelectorMemberNot;
import com.phloc.css.decl.CSSSelectorSimpleMember;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ECSSAttributeOperator;
import com.phloc.css.decl.ECSSExpressionOperator;
import com.phloc.css.decl.ECSSSelectorCombinator;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.decl.ICSSSelectorMember;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.ParseUtils;

final class CSSNodeToDomainObject
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSNodeToDomainObject.class);
  private final ECSSVersion m_eVersion;

  public CSSNodeToDomainObject (@Nonnull final ECSSVersion eVersion)
  {
    if (eVersion == null)
      throw new NullPointerException ("version");
    m_eVersion = eVersion;
  }

  private void _expectNodeType (@Nonnull final CSSNode aNode, @Nonnull final ECSSNodeType eExpected)
  {
    if (!eExpected.isNode (aNode, m_eVersion))
      throw new IllegalArgumentException ("Expected an " +
                                          eExpected.getNodeName (m_eVersion) +
                                          " but received a " +
                                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                                          ": " +
                                          aNode);
  }

  @Nonnull
  private CSSImportRule _createImportRule (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.IMPORTRULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 0 && nChildCount != 1)
      throw new IllegalArgumentException ("Expected 0 or 1 children but got " + nChildCount + "!");

    // Import rule
    final CSSImportRule ret = new CSSImportRule (ParseUtils.extractStringValue (aNode.getText ()));
    if (nChildCount == 1)
    {
      // We have a medium present!
      final CSSNode aMediaListNode = aNode.jjtGetChild (0);
      for (final CSSNode aMedium : aMediaListNode)
        ret.addMedium (aMedium.getText ());
    }
    else
      if (nChildCount > 0)
        s_aLogger.warn ("Import statement has " + nChildCount + " children which are unhandled.");
    return ret;
  }

  @Nonnull
  private CSSSelectorAttribute _createSelectorAttribute (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.ATTRIB);
    final int nChildren = aNode.jjtGetNumChildren ();
    if (nChildren == 0)
    {
      // Just check for existence
      return new CSSSelectorAttribute (aNode.getText ());
    }

    if (nChildren != 2)
      throw new IllegalArgumentException ("Illegal number of children present (" + nChildren + ") - expected 2");

    // With operator and value
    return new CSSSelectorAttribute (aNode.getText (),
                                     ECSSAttributeOperator.fromTextOrNull (aNode.jjtGetChild (0).getText ()),
                                     aNode.jjtGetChild (1).getText ());
  }

  @Nullable
  private ICSSSelectorMember _createSelectorMember (final CSSNode aNode)
  {
    final int nChildCount = aNode.jjtGetNumChildren ();

    if (ECSSNodeType.NAMESPACEPREFIX.isNode (aNode, m_eVersion) ||
        ECSSNodeType.UNIVERSAL.isNode (aNode, m_eVersion) ||
        ECSSNodeType.ELEMENTNAME.isNode (aNode, m_eVersion) ||
        ECSSNodeType.HASH.isNode (aNode, m_eVersion) ||
        ECSSNodeType.CLASS.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 0)
        s_aLogger.warn ("Expected 0 children and got " + nChildCount);
      return new CSSSelectorSimpleMember (aNode.getText ());
    }
    if (ECSSNodeType.ATTRIB.isNode (aNode, m_eVersion))
      return _createSelectorAttribute (aNode);

    if (ECSSNodeType.COMBINATOR.isNode (aNode, m_eVersion))
      return ECSSSelectorCombinator.fromTextOrNull (aNode.getText ());

    if (ECSSNodeType.NEGATION.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 1)
        throw new IllegalArgumentException ("Illegal number of children present (" + nChildCount + ")!");

      final CSSNode aChildNode = aNode.jjtGetChild (0);
      final ICSSSelectorMember aNestedSelector = _createSelectorMember (aChildNode);
      return new CSSSelectorMemberNot (aNestedSelector);
    }

    if (ECSSNodeType.PSEUDO.isNode (aNode, m_eVersion))
    {
      if (nChildCount == 0)
        return new CSSSelectorSimpleMember (aNode.getText ());

      if (nChildCount == 1)
      {
        final CSSNode aChildNode = aNode.jjtGetChild (0);
        if (ECSSNodeType.NTH.isNode (aChildNode, m_eVersion))
        {
          // Handle nth
          return new CSSSelectorSimpleMember (aNode.getText () + aChildNode.getText () + ")");
        }

        final CSSExpression aExpr = _createExpression (aChildNode);
        return new CSSSelectorSimpleMember (aNode.getText () + aExpr.getAsCSSString (m_eVersion, true) + ")");
      }

      throw new UnsupportedOperationException ("Not supporting pseudo-selectors with functions and " +
                                               nChildCount +
                                               " args: " +
                                               aNode.toString ());
    }

    s_aLogger.warn ("Unsupported selector child: " + ECSSNodeType.getNodeName (aNode, m_eVersion));
    return null;
  }

  @Nonnull
  private CSSSelector _createSelector (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.SELECTOR);
    final CSSSelector ret = new CSSSelector ();
    for (final CSSNode aChildNode : aNode)
    {
      final ICSSSelectorMember aMember = _createSelectorMember (aChildNode);
      if (aMember != null)
        ret.addMember (aMember);
    }
    return ret;
  }

  @Nonnull
  private ICSSExpressionMember _createExpressionTerm (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.TERM);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 0 && nChildCount != 1)
      throw new IllegalArgumentException ("Expected 0 or 1  children but got " + nChildCount + "!");

    // Simple value
    if (nChildCount == 0)
      return new CSSExpressionMemberTermSimple (aNode.getText ());

    // function value
    final CSSNode aFuncNode = aNode.jjtGetChild (0);
    if (!ECSSNodeType.FUNCTION.isNode (aFuncNode, m_eVersion))
      throw new IllegalStateException ("Expected a function but got " +
                                       ECSSNodeType.getNodeName (aFuncNode, m_eVersion));

    final String sFunctionName = aFuncNode.getText ();
    if (aFuncNode.jjtGetNumChildren () == 1)
    {
      // Parameters present
      final CSSExpression aFuncExpr = _createExpression (aFuncNode.jjtGetChild (0));
      return new CSSExpressionMemberFunction (sFunctionName, aFuncExpr);
    }

    // No parameters
    return new CSSExpressionMemberFunction (sFunctionName);
  }

  @Nonnull
  private CSSExpression _createExpression (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.EXPR);
    final CSSExpression ret = new CSSExpression ();
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.TERM.isNode (aChildNode, m_eVersion))
        ret.addMember (_createExpressionTerm (aChildNode));
      else
        if (ECSSNodeType.OPERATOR.isNode (aChildNode, m_eVersion))
          ret.addMember (ECSSExpressionOperator.fromTextOrNull (aChildNode.getText ()));
        else
          s_aLogger.warn ("Unsupported child of " +
                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                          ": " +
                          ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSDeclaration _createDeclaration (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.DECLARATION);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 2 && nChildCount != 3)
      throw new IllegalArgumentException ("Expected 2 or 3 children but got " + nChildCount + ": " + aNode);

    final String sProperty = aNode.jjtGetChild (0).getText ();
    final CSSExpression aExpression = _createExpression (aNode.jjtGetChild (1));
    boolean bImportant = false;
    if (nChildCount == 3)
    {
      final CSSNode aChildNode = aNode.jjtGetChild (2);
      if (ECSSNodeType.IMPORTANT.isNode (aChildNode, m_eVersion))
        bImportant = true;
      else
        s_aLogger.warn ("Expected an " +
                        ECSSNodeType.IMPORTANT.getNodeName (m_eVersion) +
                        " token but got a " +
                        ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    return new CSSDeclaration (sProperty, aExpression, bImportant);
  }

  @Nonnull
  private CSSStyleRule _createStyleRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.STYLERULE);
    final CSSStyleRule ret = new CSSStyleRule ();
    boolean bSelectors = true;
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.SELECTOR.isNode (aChildNode, m_eVersion))
      {
        if (!bSelectors)
          s_aLogger.error ("Found a selector after a declaration!");

        ret.addSelector (_createSelector (aChildNode));
      }
      else
      {
        // OK, we're after the selectors
        bSelectors = false;
        if (ECSSNodeType.DECLARATION.isNode (aChildNode, m_eVersion))
          ret.addDeclaration (_createDeclaration (aChildNode));
        else
          if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
            s_aLogger.warn ("Unsupported child of " +
                            ECSSNodeType.getNodeName (aNode, m_eVersion) +
                            ": " +
                            ECSSNodeType.getNodeName (aChildNode, m_eVersion));
      }
    }
    return ret;
  }

  @Nonnull
  private CSSMediaRule _createMediaRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MEDIARULE);
    final CSSMediaRule ret = new CSSMediaRule ();
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.MEDIALIST.isNode (aChildNode, m_eVersion))
      {
        for (final CSSNode aMedium : aChildNode)
          ret.addMedium (aMedium.getText ());
      }
      else
        if (ECSSNodeType.STYLERULE.isNode (aChildNode, m_eVersion))
          ret.addStyleRule (_createStyleRule (aChildNode));
        else
          if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
            s_aLogger.warn ("Unsupported media-rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSFontFaceRule _createFontFaceRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.FONTFACERULE);
    final CSSFontFaceRule ret = new CSSFontFaceRule ();
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.DECLARATION.isNode (aChildNode, m_eVersion))
        ret.addDeclaration (_createDeclaration (aChildNode));
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.warn ("Unsupported font-face rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  public CascadingStyleSheet createFromNode (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.ROOT);
    final CascadingStyleSheet ret = new CascadingStyleSheet ();
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.CHARSET.isNode (aChildNode, m_eVersion))
      {
        // Ignore because this was handled when reading!
      }
      else
        if (ECSSNodeType.IMPORTRULE.isNode (aChildNode, m_eVersion))
          ret.addImportRule (_createImportRule (aChildNode));
        else
          if (ECSSNodeType.STYLERULE.isNode (aChildNode, m_eVersion))
            ret.addRule (_createStyleRule (aChildNode));
          else
            if (ECSSNodeType.PAGERULE.isNode (aChildNode, m_eVersion))
            {
              // TODO page rule
              s_aLogger.warn ("Page rule object is currently ignored!");
            }
            else
              if (ECSSNodeType.MEDIARULE.isNode (aChildNode, m_eVersion))
              {
                ret.addRule (_createMediaRule (aChildNode));
              }
              else
                if (ECSSNodeType.FONTFACERULE.isNode (aChildNode, m_eVersion))
                {
                  ret.addRule (_createFontFaceRule (aChildNode));
                }
                else
                  if (ECSSNodeType.UNKNOWNRULE.isNode (aChildNode, m_eVersion))
                  {
                    // TODO unknown rule
                    s_aLogger.warn ("Unknown rule object is currently ignored: " + aChildNode);
                  }
                  else
                    s_aLogger.warn ("Unsupported child of " +
                                    ECSSNodeType.getNodeName (aNode, m_eVersion) +
                                    ": " +
                                    ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }
}
