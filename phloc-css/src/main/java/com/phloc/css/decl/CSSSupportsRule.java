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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.state.EChange;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CSSSourceLocation;
import com.phloc.css.ICSSSourceLocationAware;
import com.phloc.css.ICSSWriterSettings;

/**
 * Represents a single <code>@supports/code> rule: a list of style rules only
 * valid when a certain declaration is available.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public final class CSSSupportsRule implements ICSSTopLevelRule, ICSSSourceLocationAware
{
  private final List <ICSSSupportsConditionMember> m_aConditionMembers = new ArrayList <ICSSSupportsConditionMember> ();
  private final List <ICSSTopLevelRule> m_aRules = new ArrayList <ICSSTopLevelRule> ();
  private CSSSourceLocation m_aSourceLocation;

  public CSSSupportsRule ()
  {}

  public boolean hasSupportConditionMembers ()
  {
    return !m_aConditionMembers.isEmpty ();
  }

  @Nonnegative
  public int getSupportsConditionMemberCount ()
  {
    return m_aConditionMembers.size ();
  }

  public void addSupportConditionMember (@Nonnull final ICSSSupportsConditionMember aMember)
  {
    if (aMember == null)
      throw new NullPointerException ("member");
    m_aConditionMembers.add (aMember);
  }

  @Nonnull
  public EChange removeSupportsConditionMember (@Nonnull final ICSSSupportsConditionMember aMember)
  {
    return EChange.valueOf (m_aConditionMembers.remove (aMember));
  }

  @Nonnull
  public EChange removeSupportsConditionMember (@Nonnegative final int nIndex)
  {
    if (nIndex < 0 || nIndex >= m_aConditionMembers.size ())
      return EChange.UNCHANGED;
    m_aConditionMembers.remove (nIndex);
    return EChange.CHANGED;
  }

  @Nullable
  public ICSSSupportsConditionMember getSupportsConditionMemberAtIndex (@Nonnegative final int nIndex)
  {
    if (nIndex < 0 || nIndex >= m_aConditionMembers.size ())
      return null;
    return m_aConditionMembers.get (nIndex);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSSupportsConditionMember> getAllSupportConditionMembers ()
  {
    return ContainerHelper.newList (m_aConditionMembers);
  }

  public boolean hasRules ()
  {
    return !m_aRules.isEmpty ();
  }

  @Nonnegative
  public int getRuleCount ()
  {
    return m_aRules.size ();
  }

  public void addRule (@Nonnull final ICSSTopLevelRule aRule)
  {
    if (aRule == null)
      throw new NullPointerException ("rule");
    m_aRules.add (aRule);
  }

  @Nonnull
  public EChange removeRule (@Nonnull final ICSSTopLevelRule aRule)
  {
    return EChange.valueOf (m_aRules.remove (aRule));
  }

  @Nonnull
  public EChange removeRule (@Nonnegative final int nRuleIndex)
  {
    if (nRuleIndex < 0 || nRuleIndex >= m_aRules.size ())
      return EChange.UNCHANGED;
    m_aRules.remove (nRuleIndex);
    return EChange.CHANGED;
  }

  @Nullable
  public ICSSTopLevelRule getRule (@Nonnegative final int nRuleIndex)
  {
    if (nRuleIndex < 0 || nRuleIndex >= m_aRules.size ())
      return null;
    return m_aRules.get (nRuleIndex);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <ICSSTopLevelRule> getAllRules ()
  {
    return ContainerHelper.newList (m_aRules);
  }

  @Nonnull
  @Nonempty
  public String getAsCSSString (@Nonnull final ICSSWriterSettings aSettings, @Nonnegative final int nIndentLevel)
  {
    // Always ignore SupportsCondition rules?
    if (!aSettings.isWriteSupportsRules ())
      return "";

    final boolean bOptimizedOutput = aSettings.isOptimizedOutput ();
    final int nRuleCount = m_aRules.size ();

    if (aSettings.isRemoveUnnecessaryCode () && nRuleCount == 0)
      return "";

    final StringBuilder aSB = new StringBuilder ("@supports ");
    boolean bFirst = true;
    for (final ICSSSupportsConditionMember aCondition : m_aConditionMembers)
    {
      if (bFirst)
        bFirst = false;
      else
        aSB.append (bOptimizedOutput ? "," : ", ");
      aSB.append (aCondition.getAsCSSString (aSettings, nIndentLevel));
    }

    if (nRuleCount == 0)
    {
      aSB.append (bOptimizedOutput ? "{}" : " {}\n");
    }
    else
    {
      // At least one rule present
      aSB.append (bOptimizedOutput ? "{" : " {\n");
      bFirst = true;
      for (final ICSSTopLevelRule aRule : m_aRules)
      {
        final String sRuleCSS = aRule.getAsCSSString (aSettings, nIndentLevel + 1);
        if (StringHelper.hasText (sRuleCSS))
        {
          if (bFirst)
            bFirst = false;
          else
            if (!bOptimizedOutput)
              aSB.append ('\n');

          if (!bOptimizedOutput)
            aSB.append (aSettings.getIndent (nIndentLevel + 1));
          aSB.append (sRuleCSS);
        }
      }
      if (!bOptimizedOutput)
        aSB.append (aSettings.getIndent (nIndentLevel));
      aSB.append ('}');
      if (!bOptimizedOutput)
        aSB.append ('\n');
    }
    return aSB.toString ();
  }

  public void setSourceLocation (@Nullable final CSSSourceLocation aSourceLocation)
  {
    m_aSourceLocation = aSourceLocation;
  }

  @Nullable
  public CSSSourceLocation getSourceLocation ()
  {
    return m_aSourceLocation;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CSSSupportsRule))
      return false;
    final CSSSupportsRule rhs = (CSSSupportsRule) o;
    return m_aConditionMembers.equals (rhs.m_aConditionMembers) && m_aRules.equals (rhs.m_aRules);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aConditionMembers).append (m_aRules).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("conditionMembers", m_aConditionMembers)
                                       .append ("rules", m_aRules)
                                       .appendIfNotNull ("sourceLocation", m_aSourceLocation)
                                       .toString ();
  }
}
