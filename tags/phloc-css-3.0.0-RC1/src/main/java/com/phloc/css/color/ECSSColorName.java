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
package com.phloc.css.color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.lang.EnumHelper;
import com.phloc.commons.name.IHasName;
import com.phloc.css.annotations.DeprecatedInCSS30;

/**
 * Contains a list of predefined color values in CSS 2.1.<br>
 * These names are deprecated in CSS 3.0 in favour of the appearance property.<br>
 * Source: http://www.w3.org/TR/css3-color/ chapter 4.5.1
 * 
 * @author philip
 */
@DeprecatedInCSS30
public enum ECSSColorName implements IHasName
{
  ACTIVEBORDER ("ActiveBorder"),
  ACTIVECAPTION ("ActiveCaption"),
  APPWORKSPACE ("AppWorkspace"),
  BACKGROUND ("Background"),
  BUTTONFACE ("ButtonFace"),
  BUTTONHIGHLIGHT ("ButtonHighlight"),
  BUTTONSHADOW ("ButtonShadow"),
  BUTTONTEXT ("ButtonText"),
  CAPTIONTEXT ("CaptionText"),
  GRAYTEXT ("GrayText"),
  HIGHLIGHT ("Highlight"),
  HIGHLIGHTTEXT ("HighlightText"),
  INACTIVEBORDER ("InactiveBorder"),
  INACTIVECAPTION ("InactiveCaption"),
  INACTIVECAPTIONTEXT ("InactiveCaptionText"),
  INFOBACKGROUND ("InfoBackground"),
  INFOTEXT ("InfoText"),
  MENU ("Menu"),
  MENUTEXT ("MenuText"),
  SCROLLBAR ("Scrollbar"),
  THREEDDARKSHADOW ("ThreeDDarkShadow"),
  THREEDFACE ("ThreeDFace"),
  THREEDHIGHLIGHT ("ThreeDHighlight"),
  THREEDLIGHTSHADOW ("ThreeDLightShadow"),
  THREEDSHADOW ("ThreeDShadow"),
  WINDOW ("Window"),
  WINDOWFRAME ("WindowFrame"),
  WINDOWTEXT ("WindowText");

  private final String m_sName;

  private ECSSColorName (@Nonnull @Nonempty final String sName)
  {
    m_sName = sName;
  }

  @Nonnull
  public String getName ()
  {
    return m_sName;
  }

  @Nullable
  public static ECSSColorName getFromNameOrNullCaseInsensitive (@Nullable final String sName)
  {
    return EnumHelper.getFromNameCaseInsensitiveOrNull (ECSSColorName.class, sName);
  }

  public static boolean isDefaultColorName (@Nullable final String sName)
  {
    return getFromNameOrNullCaseInsensitive (sName) != null;
  }
}
