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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpression;
import com.phloc.css.decl.CSSExpressionMemberFunction;
import com.phloc.css.decl.CSSExpressionMemberMath;
import com.phloc.css.decl.CSSExpressionMemberMathProduct;
import com.phloc.css.decl.CSSExpressionMemberMathUnitProduct;
import com.phloc.css.decl.CSSExpressionMemberMathUnitSimple;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSExpressionMemberTermURI;
import com.phloc.css.decl.CSSFontFaceRule;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CSSKeyframesBlock;
import com.phloc.css.decl.CSSKeyframesRule;
import com.phloc.css.decl.CSSMediaExpression;
import com.phloc.css.decl.CSSMediaQuery;
import com.phloc.css.decl.CSSMediaQuery.EModifier;
import com.phloc.css.decl.CSSMediaRule;
import com.phloc.css.decl.CSSPageRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSSelectorAttribute;
import com.phloc.css.decl.CSSSelectorMemberFunctionLike;
import com.phloc.css.decl.CSSSelectorMemberNot;
import com.phloc.css.decl.CSSSelectorSimpleMember;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CSSURI;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ECSSAttributeOperator;
import com.phloc.css.decl.ECSSExpressionOperator;
import com.phloc.css.decl.ECSSMathOperator;
import com.phloc.css.decl.ECSSSelectorCombinator;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.decl.ICSSSelectorMember;
import com.phloc.css.media.ECSSMediaExpressionFeature;
import com.phloc.css.media.ECSSMedium;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.ParseUtils;

/**
 * This class converts the jjtree node to a domain object
 * 
 * @author philip
 */
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
      throw new IllegalArgumentException ("Expected a '" +
                                          eExpected.getNodeName (m_eVersion) +
                                          "' node but received a '" +
                                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                                          "' node: " +
                                          aNode);
  }

  @Nonnull
  private CSSImportRule _createImportRule (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.IMPORTRULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount > 2)
      throw new IllegalArgumentException ("Expected at last 2 children but got " + nChildCount + "!");

    CSSURI aImportURI = null;
    int nCurrentIndex = 0;
    if (nChildCount > 0)
    {
      final CSSNode aURINode = aNode.jjtGetChild (0);
      if (ECSSNodeType.URI.isNode (aURINode, m_eVersion))
      {
        aImportURI = new CSSURI (aURINode.getText ());
        ++nCurrentIndex;
      }
      else
        if (!ECSSNodeType.MEDIALIST.isNode (aURINode, m_eVersion))
          throw new IllegalStateException ("Expected an URI node but got " +
                                           ECSSNodeType.getNodeName (aURINode, m_eVersion));
    }

    if (aImportURI == null)
    {
      // No URI child node present, so the location is printed directly
      aImportURI = new CSSURI (ParseUtils.extractStringValue (aNode.getText ()));
    }

    // Import rule
    final CSSImportRule ret = new CSSImportRule (aImportURI);
    if (nChildCount > nCurrentIndex)
    {
      // We have a media query present!
      final CSSNode aMediaListNode = aNode.jjtGetChild (nCurrentIndex);
      if (ECSSNodeType.MEDIALIST.isNode (aMediaListNode, m_eVersion))
      {
        for (final CSSNode aMediaQueryNode : aMediaListNode)
        {
          ret.addMediaQuery (_createMediaQuery (aMediaQueryNode));
        }
      }
      else
        throw new IllegalStateException ("Expected an mediaList node but got " +
                                         ECSSNodeType.getNodeName (aMediaListNode, m_eVersion));
      ++nCurrentIndex;
    }

    if (nCurrentIndex < nChildCount)
      s_aLogger.warn ("Import statement has children which are unhandled.");
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
                                     ECSSAttributeOperator.getFromNameOrNull (aNode.jjtGetChild (0).getText ()),
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
        s_aLogger.warn ("CSS simple selector member expected 0 children and got " + nChildCount);
      return new CSSSelectorSimpleMember (aNode.getText ());
    }

    if (ECSSNodeType.ATTRIB.isNode (aNode, m_eVersion))
      return _createSelectorAttribute (aNode);

    if (ECSSNodeType.COMBINATOR.isNode (aNode, m_eVersion))
    {
      final String sText = aNode.getText ();
      final ECSSSelectorCombinator eCombinator = ECSSSelectorCombinator.getFromNameOrNull (sText);
      if (eCombinator == null)
        s_aLogger.warn ("Failed to parse CSS selector combinator '" + sText + "'");
      return eCombinator;
    }

    if (ECSSNodeType.NEGATION.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 1)
        throw new IllegalArgumentException ("CSS Negation expected 1 child and got " + nChildCount);

      final CSSNode aChildNode = aNode.jjtGetChild (0);
      final ICSSSelectorMember aNestedSelector = _createSelectorMember (aChildNode);
      return new CSSSelectorMemberNot (aNestedSelector);
    }

    if (ECSSNodeType.PSEUDO.isNode (aNode, m_eVersion))
    {
      if (nChildCount == 0)
      {
        // E.g. ":focus" or ":hover"
        return new CSSSelectorSimpleMember (aNode.getText ());
      }

      if (nChildCount == 1)
      {
        final CSSNode aChildNode = aNode.jjtGetChild (0);
        if (ECSSNodeType.NTH.isNode (aChildNode, m_eVersion))
        {
          // Handle nth. E.g. ":nth-child(even)" or ":nth-child(3n+1)"
          return new CSSSelectorSimpleMember (aNode.getText () + aChildNode.getText () + ")");
        }

        // It's a function (e.g. ":lang(fr)")
        final CSSExpression aExpr = _createExpression (aChildNode);
        return new CSSSelectorMemberFunctionLike (aNode.getText (), aExpr);
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
  private CSSSelector _createSelector (@Nonnull final CSSNode aNode)
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
  private CSSExpressionMemberMathProduct _createExpressionMathProduct (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MATH_PRODUCT);

    final CSSExpressionMemberMathProduct ret = new CSSExpressionMemberMathProduct ();

    // read all sums
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.MATH_UNIT.isNode (aChildNode, m_eVersion))
      {
        final int nChildCount = aChildNode.jjtGetNumChildren ();
        if (nChildCount == 0)
          ret.addMember (new CSSExpressionMemberMathUnitSimple (aChildNode.getText ()));
        else
        {
          if (nChildCount != 1)
            throw new IllegalArgumentException ("CSS math unit expected 1 child and got " + nChildCount);

          final CSSExpressionMemberMathProduct aNestedProduct = _createExpressionMathProduct (aChildNode.jjtGetChild (0));
          ret.addMember (new CSSExpressionMemberMathUnitProduct (aNestedProduct));
        }
      }
      else
        if (ECSSNodeType.MATH_PRODUCTOPERATOR.isNode (aChildNode, m_eVersion))
        {
          final String sText = aChildNode.getText ();
          final ECSSMathOperator eMathOp = ECSSMathOperator.getFromNameOrNull (sText);
          if (eMathOp == null)
            s_aLogger.warn ("Failed to parse math product operator '" + sText + "'");
          else
            ret.addMember (eMathOp);
        }
        else
          s_aLogger.warn ("Unsupported child of " +
                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                          ": " +
                          ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    return ret;
  }

  @Nonnull
  private CSSExpressionMemberMath _createExpressionMathTerm (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MATH);

    final CSSExpressionMemberMath ret = new CSSExpressionMemberMath ();

    // read all sums
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.MATH_PRODUCT.isNode (aChildNode, m_eVersion))
      {
        ret.addMember (_createExpressionMathProduct (aChildNode));
      }
      else
        if (ECSSNodeType.MATH_SUMOPERATOR.isNode (aChildNode, m_eVersion))
        {
          final String sText = aChildNode.getText ();
          final ECSSMathOperator eMathOp = ECSSMathOperator.getFromNameOrNull (sText);
          if (eMathOp == null)
            s_aLogger.warn ("Failed to parse math operator '" + sText + "'");
          else
            ret.addMember (eMathOp);
        }
        else
          s_aLogger.warn ("Unsupported child of " +
                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                          ": " +
                          ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    return ret;
  }

  @Nonnull
  private ICSSExpressionMember _createExpressionTerm (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.TERM);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 0 && nChildCount != 1)
      throw new IllegalArgumentException ("Expected 0 or 1 children but got " + nChildCount + "!");

    // Simple value
    if (nChildCount == 0)
      return new CSSExpressionMemberTermSimple (aNode.getText ());

    final CSSNode aChildNode = aNode.jjtGetChild (0);
    final int nChildChildren = aChildNode.jjtGetNumChildren ();

    if (ECSSNodeType.URI.isNode (aChildNode, m_eVersion))
    {
      // URI value
      if (nChildChildren > 0)
        throw new IllegalArgumentException ("Expected 0 children but got " + nChildChildren + "!");

      final CSSURI aURI = new CSSURI (aChildNode.getText ());
      return new CSSExpressionMemberTermURI (aURI);
    }
    else
      if (ECSSNodeType.FUNCTION.isNode (aChildNode, m_eVersion))
      {
        // function value
        if (nChildChildren > 1)
          throw new IllegalArgumentException ("Expected 0 or 1 children but got " + nChildChildren + "!");

        final String sFunctionName = aChildNode.getText ();
        if (nChildChildren == 1)
        {
          // Parameters present
          final CSSExpression aFuncExpr = _createExpression (aChildNode.jjtGetChild (0));
          return new CSSExpressionMemberFunction (sFunctionName, aFuncExpr);
        }

        // No parameters
        return new CSSExpressionMemberFunction (sFunctionName);
      }
      else
        if (ECSSNodeType.MATH.isNode (aChildNode, m_eVersion))
        {
          // Math value
          return _createExpressionMathTerm (aChildNode);
        }
        else
          throw new IllegalStateException ("Expected an expression term but got " +
                                           ECSSNodeType.getNodeName (aChildNode, m_eVersion));
  }

  @Nonnull
  private CSSExpression _createExpression (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.EXPR);
    final CSSExpression ret = new CSSExpression ();
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.TERM.isNode (aChildNode, m_eVersion))
        ret.addMember (_createExpressionTerm (aChildNode));
      else
        if (ECSSNodeType.OPERATOR.isNode (aChildNode, m_eVersion))
        {
          final String sText = aChildNode.getText ();
          final ECSSExpressionOperator eOp = ECSSExpressionOperator.getFromNameOrNull (sText);
          if (eOp == null)
            s_aLogger.warn ("Failed to parse expression operator '" + sText + "'");
          else
            ret.addMember (eOp);
        }
        else
        {
          s_aLogger.warn ("Unsupported child of " +
                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                          ": " +
                          ECSSNodeType.getNodeName (aChildNode, m_eVersion));
        }
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
      // Must be an "!important" node
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
  @edu.umd.cs.findbugs.annotations.SuppressWarnings ("IL_INFINITE_LOOP")
  private CSSPageRule _createPageRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.PAGERULE);

    final int nChildCount = aNode.jjtGetNumChildren ();
    String sPseudoPage = null;
    int nStartIndex = 0;
    if (nChildCount > 0)
    {
      final CSSNode aFirstChild = aNode.jjtGetChild (0);
      if (ECSSNodeType.PSEUDOPAGE.isNode (aFirstChild, m_eVersion))
      {
        sPseudoPage = aFirstChild.getText ();
        nStartIndex++;
      }
    }

    final CSSPageRule ret = new CSSPageRule (sPseudoPage);
    for (int nIndex = nStartIndex; nIndex < nChildCount; ++nIndex)
    {
      final CSSNode aChildNode = aNode.jjtGetChild (nIndex);

      if (ECSSNodeType.DECLARATION.isNode (aChildNode, m_eVersion))
        ret.addDeclaration (_createDeclaration (aChildNode));
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.warn ("Unsupported page rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
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
        for (final CSSNode aMediaListChildNode : aChildNode)
          ret.addMediaQuery (_createMediaQuery (aMediaListChildNode));
      }
      else
        if (ECSSNodeType.STYLERULE.isNode (aChildNode, m_eVersion))
          ret.addRule (_createStyleRule (aChildNode));
        else
          if (ECSSNodeType.PAGERULE.isNode (aChildNode, m_eVersion))
            ret.addRule (_createPageRule (aChildNode));
          else
            if (ECSSNodeType.FONTFACERULE.isNode (aChildNode, m_eVersion))
              ret.addRule (_createFontFaceRule (aChildNode));
            else
              if (ECSSNodeType.KEYFRAMESRULE.isNode (aChildNode, m_eVersion))
                ret.addRule (_createKeyframesRule (aChildNode));
              else
                if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
                  s_aLogger.warn ("Unsupported media-rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  @edu.umd.cs.findbugs.annotations.SuppressWarnings ("IL_INFINITE_LOOP")
  private CSSMediaQuery _createMediaQuery (@Nonnull final CSSNode aNode)
  {
    if (ECSSNodeType.MEDIUM.isNode (aNode, m_eVersion))
    {
      // CSS 2.1 compatibility
      final String sMedium = aNode.getText ();
      if (ECSSMedium.getFromNameOrNull (sMedium) == null)
        s_aLogger.warn ("CSS " + m_eVersion.getVersionString () + " Media query uses unknown medium '" + sMedium + "'");
      return new CSSMediaQuery (EModifier.NONE, sMedium);
    }

    // CSS 3.0 media query
    _expectNodeType (aNode, ECSSNodeType.MEDIAQUERY);
    final int nChildCount = aNode.jjtGetNumChildren ();

    int nStartIndex = 0;
    EModifier eModifier = EModifier.NONE;

    // Check if a media modifier is present
    if (nChildCount > 0)
    {
      final CSSNode aFirstChildNode = aNode.jjtGetChild (0);
      if (ECSSNodeType.MEDIAMODIFIER.isNode (aFirstChildNode, m_eVersion))
      {
        final String sMediaModifier = aFirstChildNode.getText ();
        // The "mediaModifier" token might be present, but without text!!!
        if (sMediaModifier != null)
        {
          if ("not".equalsIgnoreCase (sMediaModifier))
            eModifier = EModifier.NOT;
          else
            if ("only".equalsIgnoreCase (sMediaModifier))
              eModifier = EModifier.ONLY;
            else
              s_aLogger.warn ("Unsupported media modifier '" + sMediaModifier + "' found!");
        }
        ++nStartIndex;
      }
    }

    // Next check if a medium is present
    String sMedium = null;
    if (nChildCount > nStartIndex)
    {
      final CSSNode aNextChild = aNode.jjtGetChild (nStartIndex);
      if (ECSSNodeType.MEDIUM.isNode (aNextChild, m_eVersion))
      {
        sMedium = aNextChild.getText ();
        if (ECSSMedium.getFromNameOrNull (sMedium) == null)
          s_aLogger.warn ("CSS " +
                          m_eVersion.getVersionString () +
                          " media query uses unknown medium '" +
                          sMedium +
                          "'");
        ++nStartIndex;
      }
    }

    final CSSMediaQuery ret = new CSSMediaQuery (eModifier, sMedium);
    for (int i = nStartIndex; i < nChildCount; ++i)
    {
      final CSSNode aChildNode = aNode.jjtGetChild (i);
      if (ECSSNodeType.MEDIAEXPR.isNode (aChildNode, m_eVersion))
        ret.addMediaExpression (_createMediaExpr (aChildNode));
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.warn ("Unsupported media query child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSMediaExpression _createMediaExpr (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MEDIAEXPR);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 1 && nChildCount != 2)
      throw new IllegalArgumentException ("Expected 1 or 2 children but got " + nChildCount + ": " + aNode);

    final CSSNode aFeatureNode = aNode.jjtGetChild (0);
    if (!ECSSNodeType.MEDIAFEATURE.isNode (aFeatureNode, m_eVersion))
      throw new IllegalStateException ("Expected a media feature but got " +
                                       ECSSNodeType.getNodeName (aFeatureNode, m_eVersion));
    final String sFeature = aFeatureNode.getText ();
    if (ECSSMediaExpressionFeature.getFromNameOrNull (sFeature) == null)
      s_aLogger.warn ("Media expression uses unsupported feature '" + sFeature + "'");

    if (nChildCount == 1)
    {
      // Feature only
      return new CSSMediaExpression (sFeature);
    }

    // Feature + value
    final CSSNode aValueNode = aNode.jjtGetChild (1);
    return new CSSMediaExpression (sFeature, _createExpression (aValueNode));
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
  private CSSKeyframesRule _createKeyframesRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.KEYFRAMESRULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount == 0)
      throw new IllegalArgumentException ("Expected at least 1 child but got " + nChildCount + ": " + aNode);

    // Get the identifier (e.g. the default "@keyframes" or the non-standard
    // "@-webkit-keyframes")
    final String sKeyframesDeclaration = aNode.getText ();

    // get the name of the animation
    final CSSNode aAnimationNameNode = aNode.jjtGetChild (0);
    _expectNodeType (aAnimationNameNode, ECSSNodeType.KEYFRAMESIDENTIFIER);
    final String sAnimationName = aAnimationNameNode.getText ();

    final CSSKeyframesRule ret = new CSSKeyframesRule (sKeyframesDeclaration, sAnimationName);

    // Get the key frame blocks
    int nIndex = 1;
    CSSKeyframesBlock aBlock = null;
    while (nIndex < nChildCount)
    {
      final CSSNode aChildNode = aNode.jjtGetChild (nIndex);
      if (ECSSNodeType.KEYFRAMESSELECTOR.isNode (aChildNode, m_eVersion))
      {
        // Read all single selectors
        final List <String> aKeyframesSelectors = new ArrayList <String> ();
        for (final CSSNode aSelectorChild : aChildNode)
        {
          _expectNodeType (aSelectorChild, ECSSNodeType.SINGLEKEYFRAMESELECTOR);
          aKeyframesSelectors.add (aSelectorChild.getText ());
        }
        aBlock = new CSSKeyframesBlock (aKeyframesSelectors);
        ret.addBlock (aBlock);
      }
      else
      {
        // Must be a declaration
        if (aBlock == null)
          throw new IllegalStateException ("No keyframes block present!");
        aBlock.addDeclaration (_createDeclaration (aChildNode));
      }

      ++nIndex;
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
        {
          ret.addImportRule (_createImportRule (aChildNode));
        }
        else
          if (ECSSNodeType.STYLERULE.isNode (aChildNode, m_eVersion))
          {
            ret.addRule (_createStyleRule (aChildNode));
          }
          else
            if (ECSSNodeType.PAGERULE.isNode (aChildNode, m_eVersion))
            {
              ret.addRule (_createPageRule (aChildNode));
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
                  if (ECSSNodeType.KEYFRAMESRULE.isNode (aChildNode, m_eVersion))
                  {
                    ret.addRule (_createKeyframesRule (aChildNode));
                  }
                  else
                    if (ECSSNodeType.UNKNOWNRULE.isNode (aChildNode, m_eVersion))
                    {
                      // Unknown rule most likely indicates a parsing error
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
