/**
 * Copyright (C) 2006-2014 phloc systems
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
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSDeclarationList;
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
import com.phloc.css.decl.CSSNamespaceRule;
import com.phloc.css.decl.CSSPageRule;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSSelectorAttribute;
import com.phloc.css.decl.CSSSelectorMemberFunctionLike;
import com.phloc.css.decl.CSSSelectorMemberNot;
import com.phloc.css.decl.CSSSelectorSimpleMember;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CSSSupportsConditionDeclaration;
import com.phloc.css.decl.CSSSupportsConditionNegation;
import com.phloc.css.decl.CSSSupportsConditionNested;
import com.phloc.css.decl.CSSSupportsRule;
import com.phloc.css.decl.CSSURI;
import com.phloc.css.decl.CSSUnknownRule;
import com.phloc.css.decl.CSSViewportRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ECSSAttributeOperator;
import com.phloc.css.decl.ECSSExpressionOperator;
import com.phloc.css.decl.ECSSMathOperator;
import com.phloc.css.decl.ECSSSelectorCombinator;
import com.phloc.css.decl.ECSSSupportsConditionOperator;
import com.phloc.css.decl.ICSSExpressionMember;
import com.phloc.css.decl.ICSSSelectorMember;
import com.phloc.css.decl.ICSSSupportsConditionMember;
import com.phloc.css.media.ECSSMediaExpressionFeature;
import com.phloc.css.media.ECSSMedium;
import com.phloc.css.parser.CSSNode;
import com.phloc.css.parser.ParseUtils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This class converts the jjtree node to a domain object.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
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

  private static void _throwUnexpectedChildrenCount (@Nonnull @Nonempty final String sMsg, @Nonnull final CSSNode aNode)
  {
    s_aLogger.error (sMsg);
    for (int i = 0; i < aNode.jjtGetNumChildren (); ++i)
      s_aLogger.error ("  " + aNode.jjtGetChild (i));
    throw new IllegalArgumentException (sMsg);
  }

  @Nonnull
  private CSSImportRule _createImportRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.IMPORTRULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount > 2)
      _throwUnexpectedChildrenCount ("Expected at last 2 children but got " + nChildCount + "!", aNode);

    CSSURI aImportURI = null;
    int nCurrentIndex = 0;
    if (nChildCount > 0)
    {
      final CSSNode aURINode = aNode.jjtGetChild (0);
      if (ECSSNodeType.URL.isNode (aURINode, m_eVersion))
      {
        aImportURI = new CSSURI (aURINode.getText ());
        aImportURI.setSourceLocation (aURINode.getSourceLocation ());
        ++nCurrentIndex;
      }
      else
        if (!ECSSNodeType.MEDIALIST.isNode (aURINode, m_eVersion))
          throw new IllegalStateException ("Expected an URI or MEDIALIST node but got " +
                                           ECSSNodeType.getNodeName (aURINode, m_eVersion));
    }

    if (aImportURI == null)
    {
      // No URI child node present, so the location is printed directly
      // E.g. @import "abc.css"
      aImportURI = new CSSURI (ParseUtils.extractStringValue (aNode.getText ()));
    }

    // Import rule
    final CSSImportRule ret = new CSSImportRule (aImportURI);
    ret.setSourceLocation (aNode.getSourceLocation ());
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
        ++nCurrentIndex;
      }
      else
        s_aLogger.error ("Expected an MEDIALIST node but got " + ECSSNodeType.getNodeName (aMediaListNode, m_eVersion));
    }

    if (nCurrentIndex < nChildCount)
      s_aLogger.error ("Import statement has children which are unhandled.");
    return ret;
  }

  @Nonnull
  private CSSSelectorAttribute _createSelectorAttribute (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.ATTRIB);
    final int nChildren = aNode.jjtGetNumChildren ();

    // Check if a namespace prefix is present
    String sNamespacePrefix = null;
    int nOperatorIndex = 0;
    if (nChildren > 0 && ECSSNodeType.NAMESPACEPREFIX.isNode (aNode.jjtGetChild (0), m_eVersion))
    {
      sNamespacePrefix = aNode.jjtGetChild (0).getText ();
      nOperatorIndex = 1;
    }
    final String sAttrName = aNode.getText ();

    CSSSelectorAttribute ret;
    if (nChildren == nOperatorIndex)
    {
      // Just check for existence of the attribute
      ret = new CSSSelectorAttribute (sNamespacePrefix, sAttrName);
    }
    else
    {
      final int nExpectedChildCount = nOperatorIndex + 2;
      if (nChildren != nExpectedChildCount)
        _throwUnexpectedChildrenCount ("Illegal number of children present (" +
                                       nChildren +
                                       ") - expected " +
                                       nExpectedChildCount, aNode);

      // With operator...
      final CSSNode aOperator = aNode.jjtGetChild (nOperatorIndex);
      _expectNodeType (aOperator, ECSSNodeType.ATTRIBOPERATOR);

      // ...and value
      final CSSNode aAttrValue = aNode.jjtGetChild (nOperatorIndex + 1);
      _expectNodeType (aAttrValue, ECSSNodeType.ATTRIBVALUE);

      ret = new CSSSelectorAttribute (sNamespacePrefix,
                                      sAttrName,
                                      ECSSAttributeOperator.getFromNameOrNull (aOperator.getText ()),
                                      aAttrValue.getText ());
    }
    ret.setSourceLocation (aNode.getSourceLocation ());
    return ret;
  }

  @Nullable
  private ICSSSelectorMember _createSelectorMember (final CSSNode aNode)
  {
    final int nChildCount = aNode.jjtGetNumChildren ();

    if (ECSSNodeType.NAMESPACEPREFIX.isNode (aNode, m_eVersion) ||
        ECSSNodeType.ELEMENTNAME.isNode (aNode, m_eVersion) ||
        ECSSNodeType.HASH.isNode (aNode, m_eVersion) ||
        ECSSNodeType.CLASS.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 0)
        _throwUnexpectedChildrenCount ("CSS simple selector member expected 0 children and got " + nChildCount, aNode);
      final CSSSelectorSimpleMember ret = new CSSSelectorSimpleMember (aNode.getText ());
      ret.setSourceLocation (aNode.getSourceLocation ());
      return ret;
    }

    if (ECSSNodeType.ATTRIB.isNode (aNode, m_eVersion))
      return _createSelectorAttribute (aNode);

    if (ECSSNodeType.COMBINATOR.isNode (aNode, m_eVersion))
    {
      final String sText = aNode.getText ();
      final ECSSSelectorCombinator eCombinator = ECSSSelectorCombinator.getFromNameOrNull (sText);
      if (eCombinator == null)
        s_aLogger.error ("Failed to parse CSS selector combinator '" + sText + "'");
      return eCombinator;
    }

    if (ECSSNodeType.NEGATION.isNode (aNode, m_eVersion))
    {
      // Note: no children don't make sense but are syntactically allowed!
      if (nChildCount > 2)
        _throwUnexpectedChildrenCount ("CSS Negation expected 1 or 2 children and got " + nChildCount, aNode);

      final List <ICSSSelectorMember> aNestedSelectors = new ArrayList <ICSSSelectorMember> (nChildCount);
      for (int i = 0; i < nChildCount; ++i)
      {
        final CSSNode aChildNode = aNode.jjtGetChild (i);
        aNestedSelectors.add (_createSelectorMember (aChildNode));
      }
      final CSSSelectorMemberNot ret = new CSSSelectorMemberNot (aNestedSelectors);
      ret.setSourceLocation (aNode.getSourceLocation ());
      return ret;
    }

    if (ECSSNodeType.PSEUDO.isNode (aNode, m_eVersion))
    {
      if (nChildCount == 0)
      {
        // E.g. ":focus" or ":hover"
        final CSSSelectorSimpleMember ret = new CSSSelectorSimpleMember (aNode.getText ());
        ret.setSourceLocation (aNode.getSourceLocation ());
        return ret;
      }

      if (nChildCount == 1)
      {
        final CSSNode aChildNode = aNode.jjtGetChild (0);
        if (ECSSNodeType.NTH.isNode (aChildNode, m_eVersion))
        {
          // Handle nth. E.g. ":nth-child(even)" or ":nth-child(3n+1)"
          final CSSSelectorSimpleMember ret = new CSSSelectorSimpleMember (aNode.getText () +
                                                                           aChildNode.getText () +
                                                                           ")");
          ret.setSourceLocation (aNode.getSourceLocation ());
          return ret;
        }

        // It's a function (e.g. ":lang(fr)")
        final CSSExpression aExpr = _createExpression (aChildNode);
        final CSSSelectorMemberFunctionLike ret = new CSSSelectorMemberFunctionLike (aNode.getText (), aExpr);
        ret.setSourceLocation (aNode.getSourceLocation ());
        return ret;
      }

      throw new UnsupportedOperationException ("Not supporting pseudo-selectors with functions and " +
                                               nChildCount +
                                               " args: " +
                                               aNode.toString ());
    }

    s_aLogger.error ("Unsupported selector child: " + ECSSNodeType.getNodeName (aNode, m_eVersion));
    return null;
  }

  @Nonnull
  private CSSSelector _createSelector (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.SELECTOR);
    final CSSSelector ret = new CSSSelector ();
    ret.setSourceLocation (aNode.getSourceLocation ());
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
    _expectNodeType (aNode, ECSSNodeType.MATHPRODUCT);

    final CSSExpressionMemberMathProduct ret = new CSSExpressionMemberMathProduct ();
    ret.setSourceLocation (aNode.getSourceLocation ());

    // read all sums
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.MATHUNIT.isNode (aChildNode, m_eVersion))
      {
        final int nChildCount = aChildNode.jjtGetNumChildren ();
        if (nChildCount == 0)
        {
          final CSSExpressionMemberMathUnitSimple aMember = new CSSExpressionMemberMathUnitSimple (aChildNode.getText ());
          aMember.setSourceLocation (aChildNode.getSourceLocation ());
          ret.addMember (aMember);
        }
        else
        {
          if (nChildCount != 1)
            _throwUnexpectedChildrenCount ("CSS math unit expected 1 child and got " + nChildCount, aChildNode);

          final CSSNode aChildChildNode = aChildNode.jjtGetChild (0);
          final CSSExpressionMemberMathProduct aNestedProduct = _createExpressionMathProduct (aChildChildNode);
          final CSSExpressionMemberMathUnitProduct aMember = new CSSExpressionMemberMathUnitProduct (aNestedProduct);
          // Source location is taken from aNestedProduct
          ret.addMember (aMember);
        }
      }
      else
        if (ECSSNodeType.MATHPRODUCTOPERATOR.isNode (aChildNode, m_eVersion))
        {
          final String sText = aChildNode.getText ();
          final ECSSMathOperator eMathOp = ECSSMathOperator.getFromNameOrNull (sText);
          if (eMathOp == null)
            s_aLogger.error ("Failed to parse math product operator '" + sText + "'");
          else
            ret.addMember (eMathOp);
        }
        else
          s_aLogger.error ("Unsupported child of " +
                           ECSSNodeType.getNodeName (aNode, m_eVersion) +
                           ": " +
                           ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    return ret;
  }

  @Nonnull
  private CSSExpressionMemberTermURI _createExpressionURL (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.URL);

    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount > 0)
      _throwUnexpectedChildrenCount ("Expected 0 children but got " + nChildCount + "!", aNode);

    final CSSURI aURI = new CSSURI (aNode.getText ());
    aURI.setSourceLocation (aNode.getSourceLocation ());
    return new CSSExpressionMemberTermURI (aURI);
  }

  @Nonnull
  private CSSExpressionMemberFunction _createExpressionFunction (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.FUNCTION);

    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount > 1)
      _throwUnexpectedChildrenCount ("Expected 0 or 1 children but got " + nChildCount + "!", aNode);

    final String sFunctionName = aNode.getText ();
    CSSExpressionMemberFunction aFunc;
    if (nChildCount == 1)
    {
      // Parameters present
      final CSSNode aFirstChild = aNode.jjtGetChild (0);
      final CSSExpression aFuncExpr = _createExpression (aFirstChild);
      aFunc = new CSSExpressionMemberFunction (sFunctionName, aFuncExpr);
    }
    else
    {
      // No parameters
      aFunc = new CSSExpressionMemberFunction (sFunctionName);
    }
    aFunc.setSourceLocation (aNode.getSourceLocation ());
    return aFunc;
  }

  @Nonnull
  private CSSExpressionMemberMath _createExpressionMathTerm (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MATH);

    final CSSExpressionMemberMath ret = new CSSExpressionMemberMath ();
    ret.setSourceLocation (aNode.getSourceLocation ());

    // read all sums
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.MATHPRODUCT.isNode (aChildNode, m_eVersion))
      {
        ret.addMember (_createExpressionMathProduct (aChildNode));
      }
      else
        if (ECSSNodeType.MATHSUMOPERATOR.isNode (aChildNode, m_eVersion))
        {
          final String sText = aChildNode.getText ();
          final ECSSMathOperator eMathOp = ECSSMathOperator.getFromNameOrNull (sText);
          if (eMathOp == null)
            s_aLogger.error ("Failed to parse math operator '" + sText + "'");
          else
            ret.addMember (eMathOp);
        }
        else
          s_aLogger.error ("Unsupported child of " +
                           ECSSNodeType.getNodeName (aNode, m_eVersion) +
                           ": " +
                           ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    return ret;
  }

  @Nonnull
  private ICSSExpressionMember _createExpressionTerm (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.EXPRTERM);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount > 1)
      _throwUnexpectedChildrenCount ("Expected 0 or 1 children but got " + nChildCount + "!", aNode);

    // Simple value
    if (nChildCount == 0)
    {
      final CSSExpressionMemberTermSimple ret = new CSSExpressionMemberTermSimple (aNode.getText ());
      ret.setSourceLocation (aNode.getSourceLocation ());
      return ret;
    }

    final CSSNode aChildNode = aNode.jjtGetChild (0);

    if (ECSSNodeType.URL.isNode (aChildNode, m_eVersion))
    {
      // URI value
      return _createExpressionURL (aChildNode);
    }
    else
      if (ECSSNodeType.FUNCTION.isNode (aChildNode, m_eVersion))
      {
        // function value
        return _createExpressionFunction (aChildNode);
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
    ret.setSourceLocation (aNode.getSourceLocation ());
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.EXPRTERM.isNode (aChildNode, m_eVersion))
        ret.addMember (_createExpressionTerm (aChildNode));
      else
        if (ECSSNodeType.EXPROPERATOR.isNode (aChildNode, m_eVersion))
        {
          final String sText = aChildNode.getText ();
          final ECSSExpressionOperator eOp = ECSSExpressionOperator.getFromNameOrNull (sText);
          if (eOp == null)
            s_aLogger.error ("Failed to parse expression operator '" + sText + "'");
          else
            ret.addMember (eOp);
        }
        else
        {
          s_aLogger.error ("Unsupported child of " +
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
    _expectNodeType (aNode, ECSSNodeType.STYLEDECLARATION);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 2 && nChildCount != 3)
      _throwUnexpectedChildrenCount ("Expected 2 or 3 children but got " + nChildCount + "!", aNode);

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
        s_aLogger.error ("Expected an " +
                         ECSSNodeType.IMPORTANT.getNodeName (m_eVersion) +
                         " token but got a " +
                         ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    final CSSDeclaration ret = new CSSDeclaration (sProperty, aExpression, bImportant);
    ret.setSourceLocation (aNode.getSourceLocation ());
    return ret;
  }

  @Nonnull
  private CSSStyleRule _createStyleRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.STYLERULE);
    final CSSStyleRule ret = new CSSStyleRule ();
    ret.setSourceLocation (aNode.getSourceLocation ());
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
        if (ECSSNodeType.STYLEDECLARATIONLIST.isNode (aChildNode, m_eVersion))
        {
          // Read all contained declarations
          final int nDecls = aChildNode.jjtGetNumChildren ();
          for (int nDecl = 0; nDecl < nDecls; ++nDecl)
          {
            final CSSNode aChildChildNode = aChildNode.jjtGetChild (nDecl);
            if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildChildNode, m_eVersion))
              ret.addDeclaration (_createDeclaration (aChildChildNode));
          }
        }
        else
          if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
            s_aLogger.error ("Unsupported child of " +
                             ECSSNodeType.getNodeName (aNode, m_eVersion) +
                             ": " +
                             ECSSNodeType.getNodeName (aChildNode, m_eVersion));
      }
    }
    return ret;
  }

  @Nonnull
  @SuppressFBWarnings ("IL_INFINITE_LOOP")
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
    ret.setSourceLocation (aNode.getSourceLocation ());
    for (int nIndex = nStartIndex; nIndex < nChildCount; ++nIndex)
    {
      final CSSNode aChildNode = aNode.jjtGetChild (nIndex);

      if (ECSSNodeType.STYLEDECLARATIONLIST.isNode (aChildNode, m_eVersion))
      {
        // Read all contained declarations
        final int nDecls = aChildNode.jjtGetNumChildren ();
        for (int nDecl = 0; nDecl < nDecls; ++nDecl)
          ret.addDeclaration (_createDeclaration (aChildNode.jjtGetChild (nDecl)));
      }
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.error ("Unsupported page rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSMediaRule _createMediaRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MEDIARULE);
    final CSSMediaRule ret = new CSSMediaRule ();
    ret.setSourceLocation (aNode.getSourceLocation ());
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
          if (ECSSNodeType.MEDIARULE.isNode (aChildNode, m_eVersion))
          {
            // Nested media rules are OK!
            ret.addRule (_createMediaRule (aChildNode));
          }
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
                  if (ECSSNodeType.VIEWPORTRULE.isNode (aChildNode, m_eVersion))
                    ret.addRule (_createViewportRule (aChildNode));
                  else
                    if (ECSSNodeType.SUPPORTSRULE.isNode (aChildNode, m_eVersion))
                      ret.addRule (_createSupportsRule (aChildNode));
                    else
                      if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
                        s_aLogger.error ("Unsupported media-rule child: " +
                                         ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  @SuppressFBWarnings ("IL_INFINITE_LOOP")
  private CSSMediaQuery _createMediaQuery (@Nonnull final CSSNode aNode)
  {
    if (ECSSNodeType.MEDIUM.isNode (aNode, m_eVersion))
    {
      // CSS 2.1 compatibility
      final String sMedium = aNode.getText ();
      if (ECSSMedium.getFromNameOrNull (sMedium) == null)
        s_aLogger.warn ("CSS " + m_eVersion.getVersionString () + " Media query uses unknown medium '" + sMedium + "'");
      final CSSMediaQuery ret = new CSSMediaQuery (EModifier.NONE, sMedium);
      ret.setSourceLocation (aNode.getSourceLocation ());
      return ret;
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
              s_aLogger.error ("Unsupported media modifier '" + sMediaModifier + "' found!");
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
    ret.setSourceLocation (aNode.getSourceLocation ());
    for (int i = nStartIndex; i < nChildCount; ++i)
    {
      final CSSNode aChildNode = aNode.jjtGetChild (i);
      if (ECSSNodeType.MEDIAEXPR.isNode (aChildNode, m_eVersion))
        ret.addMediaExpression (_createMediaExpr (aChildNode));
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.error ("Unsupported media query child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSMediaExpression _createMediaExpr (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MEDIAEXPR);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 1 && nChildCount != 2)
      _throwUnexpectedChildrenCount ("Expected 1 or 2 children but got " + nChildCount + "!", aNode);

    final CSSNode aFeatureNode = aNode.jjtGetChild (0);
    if (!ECSSNodeType.MEDIAFEATURE.isNode (aFeatureNode, m_eVersion))
      throw new IllegalStateException ("Expected a media feature but got " +
                                       ECSSNodeType.getNodeName (aFeatureNode, m_eVersion));
    final String sFeature = aFeatureNode.getText ();
    if (ECSSMediaExpressionFeature.getFromNameOrNull (sFeature) == null)
      s_aLogger.warn ("Media expression uses unknown feature '" + sFeature + "'");

    CSSMediaExpression ret;
    if (nChildCount == 1)
    {
      // Feature only
      ret = new CSSMediaExpression (sFeature);
    }
    else
    {
      // Feature + value
      final CSSNode aValueNode = aNode.jjtGetChild (1);
      ret = new CSSMediaExpression (sFeature, _createExpression (aValueNode));
    }
    ret.setSourceLocation (aNode.getSourceLocation ());
    return ret;
  }

  @Nonnull
  private CSSFontFaceRule _createFontFaceRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.FONTFACERULE);
    final CSSFontFaceRule ret = new CSSFontFaceRule ();
    ret.setSourceLocation (aNode.getSourceLocation ());
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.STYLEDECLARATIONLIST.isNode (aChildNode, m_eVersion))
      {
        // Read all contained declarations
        final int nDecls = aChildNode.jjtGetNumChildren ();
        for (int nDecl = 0; nDecl < nDecls; ++nDecl)
          ret.addDeclaration (_createDeclaration (aChildNode.jjtGetChild (nDecl)));
      }
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.error ("Unsupported font-face rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSKeyframesRule _createKeyframesRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.KEYFRAMESRULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount == 0)
      _throwUnexpectedChildrenCount ("Expected at least 1 child but got " + nChildCount + "!", aNode);

    // Get the identifier (e.g. the default "@keyframes" or the non-standard
    // "@-webkit-keyframes")
    final String sKeyframesDeclaration = aNode.getText ();

    // get the name of the animation
    final CSSNode aAnimationNameNode = aNode.jjtGetChild (0);
    _expectNodeType (aAnimationNameNode, ECSSNodeType.KEYFRAMESIDENTIFIER);
    final String sAnimationName = aAnimationNameNode.getText ();

    final CSSKeyframesRule ret = new CSSKeyframesRule (sKeyframesDeclaration, sAnimationName);
    ret.setSourceLocation (aNode.getSourceLocation ());

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
        aBlock.setSourceLocation (aChildNode.getSourceLocation ());
        ret.addBlock (aBlock);
      }
      else
        if (ECSSNodeType.STYLEDECLARATIONLIST.isNode (aChildNode, m_eVersion))
        {
          if (aBlock == null)
            throw new IllegalStateException ("No keyframes block present!");

          // Read all contained declarations
          final int nDecls = aChildNode.jjtGetNumChildren ();
          for (int nDecl = 0; nDecl < nDecls; ++nDecl)
            aBlock.addDeclaration (_createDeclaration (aChildNode.jjtGetChild (nDecl)));
        }
        else
          if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
            s_aLogger.error ("Unsupported keyframes rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));

      ++nIndex;
    }
    return ret;
  }

  @Nonnull
  private CSSViewportRule _createViewportRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.VIEWPORTRULE);

    // Get the identifier (e.g. the default "@viewport" or the non-standard
    // "@-ms-viewport")
    final String sViewportDeclaration = aNode.getText ();

    final CSSViewportRule ret = new CSSViewportRule (sViewportDeclaration);
    ret.setSourceLocation (aNode.getSourceLocation ());
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.STYLEDECLARATIONLIST.isNode (aChildNode, m_eVersion))
      {
        // Read all contained declarations
        final int nDecls = aChildNode.jjtGetNumChildren ();
        for (int nDecl = 0; nDecl < nDecls; ++nDecl)
          ret.addDeclaration (_createDeclaration (aChildNode.jjtGetChild (nDecl)));
      }
      else
        if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
          s_aLogger.error ("Unsupported viewport rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSNamespaceRule _createNamespaceRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.NAMESPACERULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount < 1 || nChildCount > 2)
      _throwUnexpectedChildrenCount ("Expected at least 1 child and at last 2 children but got " + nChildCount + "!",
                                     aNode);

    String sPrefix = null;
    int nURLIndex = 0;
    if (ECSSNodeType.NAMESPACERULEPREFIX.isNode (aNode.jjtGetChild (0), m_eVersion))
    {
      sPrefix = aNode.jjtGetChild (0).getText ();
      nURLIndex++;
    }

    final CSSNode aURLNode = aNode.jjtGetChild (nURLIndex);
    _expectNodeType (aURLNode, ECSSNodeType.NAMESPACERULEURL);
    final String sURL = ParseUtils.extractStringValue (aURLNode.getText ());

    final CSSNamespaceRule ret = new CSSNamespaceRule (sPrefix, sURL);
    ret.setSourceLocation (aNode.getSourceLocation ());
    return ret;
  }

  @Nullable
  private ICSSSupportsConditionMember _createSupportsConditionMemberRecursive (@Nonnull final CSSNode aNode)
  {
    final int nChildCount = aNode.jjtGetNumChildren ();

    if (ECSSNodeType.SUPPORTSCONDITIONOPERATOR.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 0)
        _throwUnexpectedChildrenCount ("Expected no children but got " + nChildCount + "!", aNode);

      return ECSSSupportsConditionOperator.getFromNameCaseInsensitiveOrNull (aNode.getText ());
    }

    if (ECSSNodeType.SUPPORTSNEGATION.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 1)
        _throwUnexpectedChildrenCount ("Expected at exactly 1 child but got " + nChildCount + "!", aNode);

      final ICSSSupportsConditionMember aNestedMember = _createSupportsConditionMemberRecursive (aNode.jjtGetChild (0));
      if (aNestedMember == null)
        return null;

      final CSSSupportsConditionNegation ret = new CSSSupportsConditionNegation (aNestedMember);
      ret.setSourceLocation (aNode.getSourceLocation ());
      return ret;
    }

    if (ECSSNodeType.SUPPORTSCONDITIONINPARENS.isNode (aNode, m_eVersion))
    {
      if (nChildCount != 1)
        _throwUnexpectedChildrenCount ("Expected at exactly 1 child but got " + nChildCount + "!", aNode);

      final CSSNode aChildNode = aNode.jjtGetChild (0);

      if (ECSSNodeType.STYLEDECLARATION.isNode (aChildNode, m_eVersion))
      {
        final CSSDeclaration aDeclaration = _createDeclaration (aChildNode);
        final CSSSupportsConditionDeclaration ret = new CSSSupportsConditionDeclaration (aDeclaration);
        ret.setSourceLocation (aNode.getSourceLocation ());
        return ret;
      }

      if (ECSSNodeType.SUPPORTSCONDITION.isNode (aChildNode, m_eVersion))
      {
        final CSSSupportsConditionNested ret = new CSSSupportsConditionNested ();
        for (final CSSNode aChildChildNode : aChildNode)
        {
          final ICSSSupportsConditionMember aMember = _createSupportsConditionMemberRecursive (aChildChildNode);
          if (aMember != null)
            ret.addMember (aMember);
        }
        return ret;
      }

      s_aLogger.error ("Unsupported supportsConditionInParents child: " +
                       ECSSNodeType.getNodeName (aChildNode, m_eVersion));
      return null;
    }

    if (!ECSSNodeType.ERROR_SKIPTO.isNode (aNode, m_eVersion))
      s_aLogger.error ("Unsupported supports-condition child: " + ECSSNodeType.getNodeName (aNode, m_eVersion));

    return null;
  }

  @Nonnull
  private CSSSupportsRule _createSupportsRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.SUPPORTSRULE);
    final CSSSupportsRule ret = new CSSSupportsRule ();
    ret.setSourceLocation (aNode.getSourceLocation ());
    for (final CSSNode aChildNode : aNode)
    {
      if (ECSSNodeType.SUPPORTSCONDITION.isNode (aChildNode, m_eVersion))
      {
        for (final CSSNode aChildChildNode : aChildNode)
        {
          final ICSSSupportsConditionMember aMember = _createSupportsConditionMemberRecursive (aChildChildNode);
          if (aMember != null)
            ret.addSupportConditionMember (aMember);
        }
      }
      else
        if (ECSSNodeType.STYLERULE.isNode (aChildNode, m_eVersion))
          ret.addRule (_createStyleRule (aChildNode));
        else
          if (ECSSNodeType.MEDIARULE.isNode (aChildNode, m_eVersion))
            ret.addRule (_createMediaRule (aChildNode));
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
                  if (ECSSNodeType.VIEWPORTRULE.isNode (aChildNode, m_eVersion))
                    ret.addRule (_createViewportRule (aChildNode));
                  else
                    if (ECSSNodeType.SUPPORTSRULE.isNode (aChildNode, m_eVersion))
                      ret.addRule (_createSupportsRule (aChildNode));
                    else
                      if (!ECSSNodeType.ERROR_SKIPTO.isNode (aChildNode, m_eVersion))
                        s_aLogger.error ("Unsupported supports-rule child: " +
                                         ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  private CSSUnknownRule _createUnknownRule (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.UNKNOWNRULE);

    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 2)
      _throwUnexpectedChildrenCount ("Expected 2 children but got " + nChildCount + "!", aNode);

    final CSSNode aParameterList = aNode.jjtGetChild (0);
    _expectNodeType (aParameterList, ECSSNodeType.UNKNOWNRULEPARAMETERLIST);

    final CSSNode aBody = aNode.jjtGetChild (1);
    _expectNodeType (aBody, ECSSNodeType.UNKNOWNRULEBODY);

    // Get the name of the rule
    final String sRuleDeclaration = aNode.getText ();

    final CSSUnknownRule ret = new CSSUnknownRule (sRuleDeclaration);
    ret.setSourceLocation (aNode.getSourceLocation ());
    ret.setParameterList (aParameterList.getText ());
    ret.setBody (aBody.getText ());
    return ret;
  }

  @Nonnull
  public CascadingStyleSheet createCascadingStyleSheetFromNode (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.ROOT);
    final CascadingStyleSheet ret = new CascadingStyleSheet ();
    ret.setSourceLocation (aNode.getSourceLocation ());
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
          if (ECSSNodeType.NAMESPACERULE.isNode (aChildNode, m_eVersion))
            ret.addNamespaceRule (_createNamespaceRule (aChildNode));
          else
            if (ECSSNodeType.STYLERULE.isNode (aChildNode, m_eVersion))
              ret.addRule (_createStyleRule (aChildNode));
            else
              if (ECSSNodeType.PAGERULE.isNode (aChildNode, m_eVersion))
                ret.addRule (_createPageRule (aChildNode));
              else
                if (ECSSNodeType.MEDIARULE.isNode (aChildNode, m_eVersion))
                  ret.addRule (_createMediaRule (aChildNode));
                else
                  if (ECSSNodeType.FONTFACERULE.isNode (aChildNode, m_eVersion))
                    ret.addRule (_createFontFaceRule (aChildNode));
                  else
                    if (ECSSNodeType.KEYFRAMESRULE.isNode (aChildNode, m_eVersion))
                      ret.addRule (_createKeyframesRule (aChildNode));
                    else
                      if (ECSSNodeType.VIEWPORTRULE.isNode (aChildNode, m_eVersion))
                        ret.addRule (_createViewportRule (aChildNode));
                      else
                        if (ECSSNodeType.SUPPORTSRULE.isNode (aChildNode, m_eVersion))
                          ret.addRule (_createSupportsRule (aChildNode));
                        else
                          if (ECSSNodeType.UNKNOWNRULE.isNode (aChildNode, m_eVersion))
                          {
                            // Unknown rule indicates either
                            // 1. a parsing error
                            // 2. a non-standard rule
                            ret.addRule (_createUnknownRule (aChildNode));
                          }
                          else
                            s_aLogger.error ("Unsupported child of " +
                                             ECSSNodeType.getNodeName (aNode, m_eVersion) +
                                             ": " +
                                             ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  public CSSDeclarationList createDeclarationListFromNode (@Nonnull final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.STYLEDECLARATIONLIST);
    final CSSDeclarationList ret = new CSSDeclarationList ();
    ret.setSourceLocation (aNode.getSourceLocation ());
    final int nDecls = aNode.jjtGetNumChildren ();
    for (int nDecl = 0; nDecl < nDecls; ++nDecl)
      ret.addDeclaration (_createDeclaration (aNode.jjtGetChild (nDecl)));
    return ret;
  }
}
