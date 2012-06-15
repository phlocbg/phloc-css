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
package com.phloc.css.property;

import javax.annotation.Nonnull;

import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;
import com.phloc.css.ECSSVersion;
import com.phloc.css.propertyvalue.CSSValue;
import com.phloc.css.propertyvalue.CSSValueList;
import com.phloc.css.propertyvalue.CSSValueMultiProperty;
import com.phloc.css.propertyvalue.CSSValueMultiValue;
import com.phloc.css.propertyvalue.ICSSValue;

/**
 * Abstract base class for implementing {@link ICSSProperty}
 * 
 * @author philip
 */
public abstract class AbstractCSSProperty implements ICSSProperty
{
  private final ECSSProperty m_eProp;

  protected AbstractCSSProperty (@Nonnull final ECSSProperty eProp)
  {
    if (eProp == null)
      throw new NullPointerException ("prop");
    m_eProp = eProp;
  }

  @Nonnull
  public final ECSSVersion getMinimumCSSVersion ()
  {
    return m_eProp.getMinimumCSSVersion ();
  }

  @Nonnull
  public final ECSSProperty getProp ()
  {
    return m_eProp;
  }

  @Nonnull
  private ICSSValue _newValue (@Nonnull final String sValue, final boolean bIsImportant)
  {
    // Special handling for browser specific value creation
    switch (m_eProp)
    {
      case DISPLAY:
        if (sValue.equals (CCSS.INLINE_BLOCK))
          return new CSSValueMultiValue (this, new String [] { "-moz-inline-block", sValue }, bIsImportant);
        break;
      case OPACITY:
      {
        final double dValue = StringHelper.parseDouble (sValue, Double.NaN);
        if (!Double.isNaN (dValue))
        {
          final int nPerc = (int) (dValue * 100);
          return new CSSValueList (new ICSSProperty [] { new CSSPropertyFree (ECSSProperty._MS_FILTER),
                                                        new CSSPropertyFree (ECSSProperty.FILTER),
                                                        getClone (ECSSProperty._MOZ_OPACITY),
                                                        getClone (ECSSProperty._WEBKIT_OPACITY),
                                                        this },
                                   new String [] { "\"progid:DXImageTransform.Microsoft.Alpha(Opacity=" + nPerc + ")\"",
                                                  "alpha(opacity=" + nPerc + ")",
                                                  sValue,
                                                  sValue,
                                                  sValue },
                                   bIsImportant);
        }
        break;
      }
      case BORDER_RADIUS:
        return new CSSValueMultiProperty (new ICSSProperty [] { this,
                                                               getClone (ECSSProperty._MOZ_BORDER_RADIUS),
                                                               getClone (ECSSProperty._WEBKIT_BORDER_RADIUS),
                                                               getClone (ECSSProperty._KHTML_BORDER_RADIUS) },
                                          sValue,
                                          bIsImportant);
      case BORDER_TOP_LEFT_RADIUS:
        return new CSSValueMultiProperty (new ICSSProperty [] { this,
                                                               getClone (ECSSProperty._MOZ_BORDER_RADIUS_TOPLEFT),
                                                               getClone (ECSSProperty._WEBKIT_BORDER_TOP_LEFT_RADIUS),
                                                               getClone (ECSSProperty._KHTML_BORDER_TOP_LEFT_RADIUS) },
                                          sValue,
                                          bIsImportant);
      case BORDER_TOP_RIGHT_RADIUS:
        return new CSSValueMultiProperty (new ICSSProperty [] { this,
                                                               getClone (ECSSProperty._MOZ_BORDER_RADIUS_TOPRIGHT),
                                                               getClone (ECSSProperty._WEBKIT_BORDER_TOP_RIGHT_RADIUS),
                                                               getClone (ECSSProperty._KHTML_BORDER_TOP_RIGHT_RADIUS) },
                                          sValue,
                                          bIsImportant);
      case BORDER_BOTTOM_LEFT_RADIUS:
        return new CSSValueMultiProperty (new ICSSProperty [] { this,
                                                               getClone (ECSSProperty._MOZ_BORDER_RADIUS_BOTTOMLEFT),
                                                               getClone (ECSSProperty._WEBKIT_BORDER_BOTTOM_LEFT_RADIUS),
                                                               getClone (ECSSProperty._KHTML_BORDER_BOTTOM_LEFT_RADIUS) },
                                          sValue,
                                          bIsImportant);
      case BORDER_BOTTOM_RIGHT_RADIUS:
        return new CSSValueMultiProperty (new ICSSProperty [] { this,
                                                               getClone (ECSSProperty._MOZ_BORDER_RADIUS_BOTTOMRIGHT),
                                                               getClone (ECSSProperty._WEBKIT_BORDER_BOTTOM_RIGHT_RADIUS),
                                                               getClone (ECSSProperty._KHTML_BORDER_BOTTOM_RIGHT_RADIUS) },
                                          sValue,
                                          bIsImportant);
    }

    return new CSSValue (this, sValue, bIsImportant);
  }

  @Nonnull
  public final ICSSValue newValue (@Nonnull final String sValue)
  {
    return _newValue (sValue, false);
  }

  @Nonnull
  public final ICSSValue newImportantValue (@Nonnull final String sValue)
  {
    return _newValue (sValue, true);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("prop", m_eProp).toString ();
  }
}