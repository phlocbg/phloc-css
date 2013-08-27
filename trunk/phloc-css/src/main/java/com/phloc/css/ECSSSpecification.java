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
package com.phloc.css;

import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;

/**
 * Enumeration containing all W3C CSS specifications. Source:
 * http://www.w3.org/Style/CSS/current-work
 * 
 * @author Philip Helger
 */
public enum ECSSSpecification
{
  /** CSS Color Level 3 */
  CSS3_COLOR ("css3-color"),
  /** CSS Namespaces */
  CSS3_NAMESPACE ("css3-namespace"),
  /** Selectors Level 3 */
  SELECTORS ("selectors"),
  /** CSS Level 2 Revision 1 */
  CSS2 ("CSS2"),
  /** CSS Level 1 */
  CSS1 ("CSS"),
  /** CSS Print Profile */
  CSS_PRINT ("css-print"),
  /** Media Queries */
  CSS3_MEDIAQUERIES ("css3-mediaqueries"),
  /** CSS Style Attributes */
  CSS_STYLE_ATTR ("css-style-attr"),
  /** CSS Backgrounds and Borders Level 3 */
  CSS3_BACKGROUND ("css3-background"),
  /** CSS Conditional Rules Level 3 */
  CSS3_CONDITIONAL ("css3-conditional"),
  /** CSS Image Values and Replaced Content Level 3 */
  CSS3_IMAGES ("css3-images"),
  /** CSS Marquee */
  CSS3_MARQUEE ("css3-marquee"),
  /** CSS Multi-column Layout */
  CSS3_MULTICOL ("css3-multicol"),
  /** CSS Speech */
  CSS3_SPEECH ("css3-speech"),
  /** CSS Values and Units Level 3 */
  CSS3_VALUES ("css3-values"),
  /** CSS Flexible Box Layout */
  CSS3_FLEXBOX ("css3-flexbox"),
  /** CSS Text Decoration Module Level 3 */
  CSS_TEXT_DECOR_3 ("css-text-decor-3"),
  /** CSS Mobile Profile 2.0 */
  CSS_MOBILE ("css-mobile"),
  /** CSS TV Profile 1.0 */
  CSS_TV ("css-tv"),
  /** CSS Animations */
  CSS3_ANIMATIONS ("css3-animations"),
  /** CSS Fonts Level 3 */
  CSS3_FONTS ("css3-fonts"),
  /** CSS Counter Styles Level 3 */
  CSS_COUNTER_STYLES_3 ("css-counter-styles-3"),
  /** CSS Text Level 3 */
  CSS3_TEXT ("css3-text"),
  /** CSS Fragmentation Level 3 */
  CSS3_BREAK ("css3-break"),
  /** CSS Transforms */
  CSS3_TRANSFORMS ("css3-transforms"),
  /** CSS Transitions */
  CSS3_TRANSITIONS ("css3-transitions"),
  /** Cascading Variables */
  CSS_VARIABLES ("css-variables"),
  /** CSS Writing Modes Level 3 */
  CSS3_WRITING_MODES ("css3-writing-modes"),
  /** CSS Cascading and Inheritance Level 3 */
  CSS3_CASCADE ("css3-cascade"),
  /** CSS Paged Media Level 3 */
  CSS3_PAGE ("css3-page"),
  /** CSS Basic User Interface Level 3 */
  CSS3_UI ("css3-ui"),
  /** CSSOM View */
  CSSOM_VIEW ("cssom-view"),
  /** CSS Box Alignment Module Level 3 */
  CSS3_ALIGN ("css3-align"),
  /** CSS Backgrounds and Borders Level 4 */
  CSS4_BACKGROUND ("css4-background"),
  /** Compositing and Blending */
  COMPOSITING ("compositing"),
  /** CSS Device Adaptation */
  CSS_DEVICE_ADAPT ("css-device-adapt"),
  /** CSS Exclusions and Shapes */
  CSS3_EXCLUSIONS ("css3-exclusions"),
  /** Filter Effects */
  FILTER_EFFECTS ("filter-effects"),
  /** CSS Generated Content for Paged Media */
  CSS3_GCPM ("css3-gpcm"),
  /** CSS Grid Layout */
  CSS3_GRID_LAYOUT ("css3-grid-layout"),
  /** CSS (Grid) Template Layout */
  CSS3_LAYOUT ("css3-layout"),
  /** CSS Intrinsic & Extrinsic Sizing Module Level 3 */
  CSS3_SIZING ("css3-sizing"),
  /** CSS Line Grid */
  CSS_LINE_GRID ("css-line-grid"),
  /** CSS Lists Level 3 */
  CSS3_LISTS ("css3-lists"),
  /** CSS Positioned Layout Level 3 */
  CSS3_POSITIONING ("css3-positioning"),
  /** CSS Presentation Levels */
  CSS3_PRESLEV ("css3-preslev"),
  /** CSS Regions */
  CSS3_REGIONS ("css3-regions"),
  /** CSS Tables Level 3 */
  CSS3_TABLES ("css3-tables"),
  /** Selectors Level 4 */
  SELECTORS4 ("selectors4"),
  /** CSS Object Model */
  CSSOM ("cssom"),
  /** CSS Masking */
  CSS_MASKING ("css-masking"),
  /** CSS Overflow */
  CSS_OVERFLOW_3 ("css-overflow-3"),
  /** CSS Basic Box Model Level 3 */
  CSS3_BOX ("css3-box"),
  /** CSS Generated Content Level 3 */
  CSS3_CONTENT ("css3-content"),
  /** CSS Line Layout Level 3 */
  CSS3_LINEBOX ("css3-linebox"),
  /** CSS Ruby */
  CSS3_RUBY ("css3-ruby"),
  /** CSS Syntax Level 3 */
  CSS3_SYNTAX ("css3-syntax"),
  /** Behavioral Extensions to CSS - Abandoned */
  @Deprecated
  BECSS ("becss"),
  /** CSS Hyperlink Presentation - Abandoned */
  @Deprecated
  CSS3_HYPERLINKS ("css3-hyperlinks"),
  /** CSS Grid Positioning - Abandoned */
  @Deprecated
  CSS3_GRID ("css3-grid");

  private String m_sID;

  private ECSSSpecification (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }
}
