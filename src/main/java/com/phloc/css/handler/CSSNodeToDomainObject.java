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

  public CSSNodeToDomainObject (final ECSSVersion eVersion)
  {
    m_eVersion = eVersion;
  }

  private void _expectNodeType (@Nonnull final CSSNode aNode, @Nonnull final ECSSNodeType eExpected)
  {
    if (aNode.getNodeType () != eExpected.getNodeType (m_eVersion))
      throw new IllegalArgumentException ("Expected an " +
                                          eExpected.getNodeName (m_eVersion) +
                                          " but received a " +
                                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                                          ": " +
                                          aNode);
  }

  private CSSImportRule _createImportRule (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.IMPORTRULE);
    final int nChildCount = aNode.jjtGetNumChildren ();
    if (nChildCount != 0 && nChildCount != 1)
      throw new IllegalArgumentException ("Expected 0 or 1 children but got " + nChildCount + "!");

    final CSSImportRule ret = new CSSImportRule (ParseUtils.extractStringValue (aNode.getText ()));
    if (nChildCount == 1)
    {
      final CSSNode aMediaListNode = aNode.jjtGetChild (0);
      for (final CSSNode aMedium : aMediaListNode)
        ret.addMedium (aMedium.getText ());
    }
    return ret;
  }

  private CSSSelectorAttribute _createSelectorAttribute (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.ATTRIB);
    final int nChildren = aNode.jjtGetNumChildren ();
    if (nChildren == 0)
    {
      // Just check for existence
      return new CSSSelectorAttribute (aNode.getText ());
    }

    if (nChildren != 2)
      throw new IllegalArgumentException ("Illegal number of children present (" + nChildren + ")!");

    // With operator and value
    return new CSSSelectorAttribute (aNode.getText (),
                                     ECSSAttributeOperator.fromTextOrNull (aNode.jjtGetChild (0).getText ()),
                                     aNode.jjtGetChild (1).getText ());
  }

  @Nullable
  private ICSSSelectorMember _createSelectorMember (final CSSNode aNode)
  {
    final int nNodeType = aNode.getNodeType ();
    final int nChildCount = aNode.jjtGetNumChildren ();

    if (nNodeType == ECSSNodeType.NAMESPACEPREFIX.getNodeType (m_eVersion) ||
        nNodeType == ECSSNodeType.UNIVERSAL.getNodeType (m_eVersion) ||
        nNodeType == ECSSNodeType.ELEMENTNAME.getNodeType (m_eVersion) ||
        nNodeType == ECSSNodeType.HASH.getNodeType (m_eVersion) ||
        nNodeType == ECSSNodeType.CLASS.getNodeType (m_eVersion))
    {
      if (nChildCount != 0)
        s_aLogger.warn ("Expected 0 children and got " + nChildCount);
      return new CSSSelectorSimpleMember (aNode.getText ());
    }
    if (nNodeType == ECSSNodeType.ATTRIB.getNodeType (m_eVersion))
      return _createSelectorAttribute (aNode);

    if (nNodeType == ECSSNodeType.COMBINATOR.getNodeType (m_eVersion))
      return ECSSSelectorCombinator.fromTextOrNull (aNode.getText ());

    if (nNodeType == ECSSNodeType.NEGATION.getNodeType (m_eVersion))
    {
      if (nChildCount != 1)
        throw new IllegalArgumentException ("Illegal number of children present (" + nChildCount + ")!");

      final CSSNode aChildNode = aNode.jjtGetChild (0);
      final ICSSSelectorMember aNestedSelector = _createSelectorMember (aChildNode);
      return new CSSSelectorMemberNot (aNestedSelector);
    }

    if (nNodeType == ECSSNodeType.PSEUDO.getNodeType (m_eVersion))
    {
      if (nChildCount == 0)
        return new CSSSelectorSimpleMember (aNode.getText ());

      if (nChildCount == 1)
      {
        final CSSNode aChildNode = aNode.jjtGetChild (0);
        if (aChildNode.getNodeType () == ECSSNodeType.NTH.getNodeType (m_eVersion))
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
    if (aFuncNode.getNodeType () != ECSSNodeType.FUNCTION.getNodeType (m_eVersion))
      throw new IllegalStateException ("Expected a function but got " +
                                       ECSSNodeType.getNodeName (aFuncNode, m_eVersion));

    final String sFunctionName = aFuncNode.getText ();
    CSSExpression aFuncExpr = null;
    if (aFuncNode.jjtGetNumChildren () == 1)
      aFuncExpr = _createExpression (aFuncNode.jjtGetChild (0));
    return new CSSExpressionMemberFunction (sFunctionName, aFuncExpr);
  }

  private CSSExpression _createExpression (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.EXPR);
    final CSSExpression ret = new CSSExpression ();
    for (final CSSNode aChildNode : aNode)
    {
      final int nNodeType = aChildNode.getNodeType ();
      if (nNodeType == ECSSNodeType.TERM.getNodeType (m_eVersion))
        ret.addMember (_createExpressionTerm (aChildNode));
      else
        if (nNodeType == ECSSNodeType.OPERATOR.getNodeType (m_eVersion))
          ret.addMember (ECSSExpressionOperator.fromTextOrNull (aChildNode.getText ()));
        else
          s_aLogger.warn ("Unsupported child of " +
                          ECSSNodeType.getNodeName (aNode, m_eVersion) +
                          ": " +
                          ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  private CSSDeclaration _createDeclaration (final CSSNode aNode)
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
      if (aChildNode.getNodeType () == ECSSNodeType.IMPORTANT.getNodeType (m_eVersion))
        bImportant = true;
      else
        s_aLogger.warn ("Expected an " +
                        ECSSNodeType.IMPORTANT.getNodeName (m_eVersion) +
                        " token but got a " +
                        ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }

    return new CSSDeclaration (sProperty, aExpression, bImportant);
  }

  private CSSStyleRule _createStyleRule (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.STYLERULE);
    final CSSStyleRule ret = new CSSStyleRule ();
    boolean bSelectors = true;
    for (final CSSNode aChildNode : aNode)
    {
      final int nNodeType = aChildNode.getNodeType ();
      if (nNodeType == ECSSNodeType.SELECTOR.getNodeType (m_eVersion))
      {
        if (!bSelectors)
          s_aLogger.error ("Found a selector after a declaration!");

        ret.addSelector (_createSelector (aChildNode));
      }
      else
      {
        // OK, we're after the selectors
        bSelectors = false;
        if (nNodeType == ECSSNodeType.DECLARATION.getNodeType (m_eVersion))
          ret.addDeclaration (_createDeclaration (aChildNode));
        else
          if (nNodeType != ECSSNodeType.ERROR_SKIPTO.getNodeType (m_eVersion))
            s_aLogger.warn ("Unsupported child of " +
                            ECSSNodeType.getNodeName (aNode, m_eVersion) +
                            ": " +
                            ECSSNodeType.getNodeName (aChildNode, m_eVersion));
      }
    }
    return ret;
  }

  private CSSMediaRule _createMediaRule (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.MEDIARULE);
    final CSSMediaRule ret = new CSSMediaRule ();
    for (final CSSNode aChildNode : aNode)
    {
      final int nNodeType = aChildNode.getNodeType ();
      if (nNodeType == ECSSNodeType.MEDIALIST.getNodeType (m_eVersion))
      {
        for (final CSSNode aMedium : aChildNode)
          ret.addMedium (aMedium.getText ());
      }
      else
        if (nNodeType == ECSSNodeType.STYLERULE.getNodeType (m_eVersion))
          ret.addStyleRule (_createStyleRule (aChildNode));
        else
          if (nNodeType != ECSSNodeType.ERROR_SKIPTO.getNodeType (m_eVersion))
            s_aLogger.warn ("Unsupported media-rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  private CSSFontFaceRule _createFontFaceRule (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.FONTFACERULE);
    final CSSFontFaceRule ret = new CSSFontFaceRule ();
    for (final CSSNode aChildNode : aNode)
    {
      final int nNodeType = aChildNode.getNodeType ();
      if (nNodeType == ECSSNodeType.DECLARATION.getNodeType (m_eVersion))
        ret.addDeclaration (_createDeclaration (aChildNode));
      else
        if (nNodeType != ECSSNodeType.ERROR_SKIPTO.getNodeType (m_eVersion))
          s_aLogger.warn ("Unsupported font-face rule child: " + ECSSNodeType.getNodeName (aChildNode, m_eVersion));
    }
    return ret;
  }

  @Nonnull
  public CascadingStyleSheet createFromNode (final CSSNode aNode)
  {
    _expectNodeType (aNode, ECSSNodeType.ROOT);
    final CascadingStyleSheet ret = new CascadingStyleSheet ();
    for (final CSSNode aChildNode : aNode)
    {
      final int nNodeType = aChildNode.getNodeType ();
      if (nNodeType == ECSSNodeType.CHARSET.getNodeType (m_eVersion))
      {
        // Ignore because this was handled when reading!
      }
      else
        if (nNodeType == ECSSNodeType.IMPORTRULE.getNodeType (m_eVersion))
          ret.addImportRule (_createImportRule (aChildNode));
        else
          if (nNodeType == ECSSNodeType.STYLERULE.getNodeType (m_eVersion))
            ret.addRule (_createStyleRule (aChildNode));
          else
            if (nNodeType == ECSSNodeType.PAGERULE.getNodeType (m_eVersion))
            {
              // TODO page rule
              s_aLogger.warn ("Page rule object is currently ignored!");
            }
            else
              if (nNodeType == ECSSNodeType.MEDIARULE.getNodeType (m_eVersion))
              {
                ret.addRule (_createMediaRule (aChildNode));
              }
              else
                if (nNodeType == ECSSNodeType.FONTFACERULE.getNodeType (m_eVersion))
                {
                  ret.addRule (_createFontFaceRule (aChildNode));
                }
                else
                  if (nNodeType == ECSSNodeType.UNKNOWNRULE.getNodeType (m_eVersion))
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
