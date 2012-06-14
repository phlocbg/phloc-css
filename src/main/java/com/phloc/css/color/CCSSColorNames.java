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

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.string.StringHelper;

/**
 * Contains a list of predefined colour values in CSS 2.1.<br>
 * These names are deprecated in CSS 3.0 in favour of the appearance property.<br>
 * Source: http://www.w3.org/TR/css3-color/ chapter 4.5.1
 * 
 * @author philip
 */
@Immutable
public final class CCSSColorNames
{
  // color constants
  public static final String ACTIVEBORDER = "ActiveBorder";
  public static final String ACTIVECAPTION = "ActiveCaption";
  public static final String APPWORKSPACE = "AppWorkspace";
  public static final String BACKGROUND = "Background";
  public static final String BUTTONFACE = "ButtonFace";
  public static final String BUTTONHIGHLIGHT = "ButtonHighlight";
  public static final String BUTTONSHADOW = "ButtonShadow";
  public static final String BUTTONTEXT = "ButtonText";
  public static final String CAPTIONTEXT = "CaptionText";
  public static final String GRAYTEXT = "GrayText";
  public static final String HIGHLIGHT = "Highlight";
  public static final String HIGHLIGHTTEXT = "HighlightText";
  public static final String INACTIVEBORDER = "InactiveBorder";
  public static final String INACTIVECAPTION = "InactiveCaption";
  public static final String INACTIVECAPTIONTEXT = "InactiveCaptionText";
  public static final String INFOBACKGROUND = "InfoBackground";
  public static final String INFOTEXT = "InfoText";
  public static final String MENU = "Menu";
  public static final String MENUTEXT = "MenuText";
  public static final String SCROLLBAR = "Scrollbar";
  public static final String THREEDDARKSHADOW = "ThreeDDarkShadow";
  public static final String THREEDFACE = "ThreeDFace";
  public static final String THREEDHIGHLIGHT = "ThreeDHighlight";
  public static final String THREEDLIGHTSHADOW = "ThreeDLightShadow";
  public static final String THREEDSHADOW = "ThreeDShadow";
  public static final String WINDOW = "Window";
  public static final String WINDOWFRAME = "WindowFrame";
  public static final String WINDOWTEXT = "WindowText";

  private CCSSColorNames ()
  {}

  public static boolean isDefaultColorName (@Nullable final String s)
  {
    if (StringHelper.hasNoText (s))
      return false;
    return s.equals (ACTIVEBORDER) ||
           s.equals (ACTIVECAPTION) ||
           s.equals (APPWORKSPACE) ||
           s.equals (BACKGROUND) ||
           s.equals (BUTTONFACE) ||
           s.equals (BUTTONHIGHLIGHT) ||
           s.equals (BUTTONSHADOW) ||
           s.equals (BUTTONTEXT) ||
           s.equals (CAPTIONTEXT) ||
           s.equals (GRAYTEXT) ||
           s.equals (HIGHLIGHT) ||
           s.equals (HIGHLIGHTTEXT) ||
           s.equals (INACTIVEBORDER) ||
           s.equals (INACTIVECAPTION) ||
           s.equals (INACTIVECAPTIONTEXT) ||
           s.equals (INFOBACKGROUND) ||
           s.equals (INFOTEXT) ||
           s.equals (MENU) ||
           s.equals (MENUTEXT) ||
           s.equals (SCROLLBAR) ||
           s.equals (THREEDDARKSHADOW) ||
           s.equals (THREEDFACE) ||
           s.equals (THREEDHIGHLIGHT) ||
           s.equals (THREEDLIGHTSHADOW) ||
           s.equals (THREEDSHADOW) ||
           s.equals (WINDOW) ||
           s.equals (WINDOWFRAME) ||
           s.equals (WINDOWTEXT);
  }
}
