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
package com.phloc.css.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.lang.EnumHelper;
import com.phloc.commons.name.IHasName;
import com.phloc.commons.string.StringHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSVersionAware;
import com.phloc.css.annotations.DeprecatedInCSS30;

/**
 * Contains a list of most CSS property names.<br>
 * Source of Webkit property names:
 * http://trac.webkit.org/export/0/trunk/Source/WebCore/css/CSSPropertyNames.in<br>
 * MS specific property names:
 * http://msdn.microsoft.com/en-us/library/ms531207(v=vs.85).aspx<br>
 * Mozilla specific property names:
 * https://developer.mozilla.org/en/CSS_Reference/Mozilla_Extensions<br>
 * <br>
 * CSS 3.0:<br>
 * <ul>
 * <li>http://www.w3.org/TR/2011/REC-css3-color-20110607/#property</li>
 * <li>http://www.w3.org/TR/2013/WD-css-fonts-3-20130711/#property-index</li>
 * <li>http://www.w3.org/TR/2011/WD-css-device-adapt-20110915/#property-index</li>
 * <li>http://www.w3.org/TR/2012/WD-css3-writing-modes-20121115/#property-index</li>
 * <li>
 * http://www.w3.org/TR/2012/WD-css3-text-20121113/#appendix-h-full-property-
 * index</li>
 * <li>TODO http://www.w3.org/TR/2011/WD-css3-speech-20110818/#property-index</li>
 * <li>http://www.w3.org/TR/2011/WD-css3-ruby-20110630/#properties</li>
 * <li>http://www.w3.org/TR/2011/WD-css3-regions-20110609/#property-index</li>
 * <li>http://www.w3.org/TR/2011/WD-css3-lists-20110524/#property-index</li>
 * <li>http://www.w3.org/TR/2011/CR-css3-multicol-20110412/#property-index</li>
 * <li>http://www.w3.org/TR/2011/WD-css3-flexbox-20110322/#property</li>
 * <li>http://www.w3.org/TR/2011/CR-css3-background-20110215/#property-index</li>
 * <li>TODO http://www.w3.org/TR/2010/WD-css3-gcpm-20100608/#property-index</li>
 * <li>http://www.w3.org/TR/css3-transitions/#property-index</li>
 * <li>http://www.w3.org/TR/2009/WD-css3-2d-transforms-20091201/#property-index</li>
 * <li>TODO
 * http://www.w3.org/TR/2009/WD-css3-animations-20090320/#property-index</li>
 * <li>TODO
 * http://www.w3.org/TR/2009/WD-css3-3d-transforms-20090320/#property-index</li>
 * <li>TODO http://www.w3.org/TR/2007/WD-css3-grid-20070905/#property-index</li>
 * <li>http://www.w3.org/TR/2008/CR-css3-marquee-20081205/#property</li>
 * </ul>
 * 
 * @author Philip Helger
 */
public enum ECSSProperty implements IHasName, ICSSVersionAware
{
  APPEARANCE ("appearance", ECSSVersion.CSS30),
  BACKGROUND ("background"),
  BACKGROUND_ATTACHMENT ("background-attachment"),
  BACKGROUND_CLIP ("background-clip", ECSSVersion.CSS30),
  BACKGROUND_COLOR ("background-color"),
  BACKGROUND_IMAGE ("background-image"),
  BACKGROUND_ORIGIN ("background-origin", ECSSVersion.CSS30),
  BACKGROUND_POSITION ("background-position"),
  BACKGROUND_REPEAT ("background-repeat"),
  BACKGROUND_SIZE ("background-size", ECSSVersion.CSS30),
  BORDER ("border"),
  BORDER_BOTTOM ("border-bottom"),
  BORDER_BOTTOM_COLOR ("border-bottom-color"),
  BORDER_BOTTOM_LEFT_RADIUS ("border-bottom-left-radius", ECSSVersion.CSS30),
  BORDER_BOTTOM_RIGHT_RADIUS ("border-bottom-right-radius", ECSSVersion.CSS30),
  BORDER_BOTTOM_STYLE ("border-bottom-style"),
  BORDER_BOTTOM_WIDTH ("border-bottom-width"),
  BORDER_COLLAPSE ("border-collapse"),
  BORDER_COLOR ("border-color"),
  BORDER_IMAGE ("border-image", ECSSVersion.CSS30),
  BORDER_IMAGE_OUTSET ("border-image-outset", ECSSVersion.CSS30),
  BORDER_IMAGE_REPEAT ("border-image-repeat", ECSSVersion.CSS30),
  BORDER_IMAGE_SLICE ("border-image-slice", ECSSVersion.CSS30),
  BORDER_IMAGE_SOURCE ("border-image-source", ECSSVersion.CSS30),
  BORDER_IMAGE_WIDTH ("border-image-width", ECSSVersion.CSS30),
  BORDER_LEFT ("border-left"),
  BORDER_LEFT_COLOR ("border-left-color"),
  BORDER_LEFT_STYLE ("border-left-style"),
  BORDER_LEFT_WIDTH ("border-left-width"),
  BORDER_RADIUS ("border-radius", ECSSVersion.CSS30),
  BORDER_RIGHT ("border-right"),
  BORDER_RIGHT_COLOR ("border-right-color"),
  BORDER_RIGHT_STYLE ("border-right-style"),
  BORDER_RIGHT_WIDTH ("border-right-width"),
  BORDER_SPACING ("border-spacing"),
  BORDER_STYLE ("border-style"),
  BORDER_TOP ("border-top"),
  BORDER_TOP_COLOR ("border-top-color"),
  BORDER_TOP_LEFT_RADIUS ("border-top-left-radius", ECSSVersion.CSS30),
  BORDER_TOP_RIGHT_RADIUS ("border-top-right-radius", ECSSVersion.CSS30),
  BORDER_TOP_STYLE ("border-top-style"),
  BORDER_TOP_WIDTH ("border-top-width"),
  BORDER_WIDTH ("border-width"),
  BOTTOM ("bottom"),
  BOX_DECORATION_BREAK ("box-decoration-break", ECSSVersion.CSS30),
  BOX_SHADOW ("box-shadow", ECSSVersion.CSS30),
  BOX_SIZING ("box-sizing", ECSSVersion.CSS30),
  BREAK_AFTER ("break-after", ECSSVersion.CSS30),
  BREAK_BEFORE ("break-before", ECSSVersion.CSS30),
  BREAK_INSIDE ("break-inside", ECSSVersion.CSS30),
  CAPTION_SIDE ("caption-side"),
  CLEAR ("clear"),
  CLIP ("clip"),
  COLOR ("color"),
  COLUMN_COUNT ("column-count", ECSSVersion.CSS30),
  COLUMN_FILL ("column-fill", ECSSVersion.CSS30),
  COLUMN_GAP ("column-gap", ECSSVersion.CSS30),
  COLUMN_RULE ("column-rule", ECSSVersion.CSS30),
  COLUMN_RULE_COLOR ("column-rule-color", ECSSVersion.CSS30),
  COLUMN_RULE_STYLE ("column-rule-style", ECSSVersion.CSS30),
  COLUMN_RULE_WIDTH ("column-rule-width", ECSSVersion.CSS30),
  COLUMN_WIDTH ("column-width", ECSSVersion.CSS30),
  COLUMNS ("columns", ECSSVersion.CSS30),
  COLUMNS_SPAN ("columns-span", ECSSVersion.CSS30),
  COLUMNS_WIDTH ("columns-width", ECSSVersion.CSS30),
  CONTENT ("content"),
  CONTENT_ORDER ("content-order", ECSSVersion.CSS30),
  CURSOR ("cursor"),
  DIRECTION ("direction"),
  DISPLAY ("display"),
  EMPTY_CELLS ("empty-cells"),
  FILTER ("filter"),
  FLEX_ALIGN ("flex-align", ECSSVersion.CSS30),
  FLEX_DIRECTION ("flex-direction", ECSSVersion.CSS30),
  FLEX_ORDER ("flex-order", ECSSVersion.CSS30),
  FLEX_PACK ("flex-pack", ECSSVersion.CSS30),
  FLOAT ("float"),
  FLOW ("flow", ECSSVersion.CSS30),
  FONT ("font", ECSSVersion.CSS30),
  FONT_FAMILY ("font-family"),
  FONT_FEATURE_SETTINGS ("font-feature-settings", ECSSVersion.CSS30),
  FONT_KERNING ("font-kerning", ECSSVersion.CSS30),
  FONT_LANGUAGE_OVERRIDE ("font-language-override", ECSSVersion.CSS30),
  FONT_SIZE ("font-size"),
  FONT_SIZE_ADJUST ("font-size-adjust", ECSSVersion.CSS30),
  FONT_STRETCH ("font-stretch", ECSSVersion.CSS30),
  FONT_STYLE ("font-style"),
  FONT_SYNTHESIS ("font-synthesis", ECSSVersion.CSS30),
  FONT_VARIANT ("font-variant"),
  FONT_VARIANT_ALTERNATES ("font-variant-alternates", ECSSVersion.CSS30),
  FONT_VARIANT_CAPS ("font-variant-caps", ECSSVersion.CSS30),
  FONT_VARIANT_EAST_ASIAN ("font-variant-east-asian", ECSSVersion.CSS30),
  FONT_VARIANT_LIGATURES ("font-variant-ligatures", ECSSVersion.CSS30),
  FONT_VARIANT_NUMERIC ("font-variant-numeric", ECSSVersion.CSS30),
  FONT_VARIANT_POSITION ("font-variant-position", ECSSVersion.CSS30),
  FONT_WEIGHT ("font-weight"),
  HANGING_PUNCTUATION ("hanging-punctuation", ECSSVersion.CSS30),
  HEIGHT ("height"),
  HYPHENS ("hyphens", ECSSVersion.CSS30),
  LEFT ("left"),
  LETTER_SPACING ("letter-spacing"),
  LINE_BREAK ("line-break", ECSSVersion.CSS30),
  LINE_HEIGHT ("line-height"),
  LIST_STYLE ("list-style"),
  LIST_STYLE_IMAGE ("list-style-image"),
  LIST_STYLE_POSITION ("list-style-position"),
  LIST_STYLE_TYPE ("list-style-type"),
  MARGIN ("margin"),
  MARGIN_BOTTOM ("margin-bottom"),
  MARGIN_LEFT ("margin-left"),
  MARGIN_RIGHT ("margin-right"),
  MARGIN_TOP ("margin-top"),
  MARQUEE_DIRECTION ("marquee-direction", ECSSVersion.CSS30),
  MARQUEE_PLAY_COUNT ("marquee-play-count", ECSSVersion.CSS30),
  MARQUEE_SPEED ("marquee-speed", ECSSVersion.CSS30),
  MARQUEE_STYLE ("marquee-style", ECSSVersion.CSS30),
  MAX_HEIGHT ("max-height"),
  MAX_WIDTH ("max-width"),
  MAX_ZOOM ("max-zoom", ECSSVersion.CSS30),
  MIN_HEIGHT ("min-height"),
  MIN_WIDTH ("min-width"),
  MIN_ZOOM ("min-zoom", ECSSVersion.CSS30),
  OPACITY ("opacity", ECSSVersion.CSS30),
  ORIENTATION ("orientation", ECSSVersion.CSS30),
  ORPHANS ("orphans"),
  OUTLINE ("outline"),
  OUTLINE_COLOR ("outline-color"),
  OUTLINE_OFFSET ("outline-offset", ECSSVersion.CSS30),
  OUTLINE_STYLE ("outline-style"),
  OUTLINE_WIDTH ("outline-width"),
  OVERFLOW ("overflow"),
  OVERFLOW_STYLE ("overflow-style", ECSSVersion.CSS30),
  OVERFLOW_WRAP ("overflow-wrap", ECSSVersion.CSS30),
  OVERFLOW_X ("overflow-x", ECSSVersion.CSS30),
  OVERFLOW_Y ("overflow-y", ECSSVersion.CSS30),
  PADDING ("padding"),
  PADDING_BOTTOM ("padding-bottom"),
  PADDING_LEFT ("padding-left"),
  PADDING_RIGHT ("padding-right"),
  PADDING_TOP ("padding-top"),
  PAGE_BREAK_BEFORE ("page-break-before"),
  PAGE_BREAK_AFTER ("page-break-after"),
  PAGE_BREAK_INSIDE ("page-break-inside"),
  POSITION ("position"),
  QUOTES ("quotes", ECSSVersion.CSS30),
  REGION_OVERFLOW ("region-overflow", ECSSVersion.CSS30),
  RESIZE ("resize", ECSSVersion.CSS30),
  RESOLUTION ("resolution", ECSSVersion.CSS30),
  RIGHT ("right"),
  RUBY_ALIGN ("ruby-align", ECSSVersion.CSS30),
  RUBY_OVERHANG ("ruby-overhang", ECSSVersion.CSS30),
  RUBY_POSITION ("ruby-position", ECSSVersion.CSS30),
  RUBY_SPAN ("ruby-span", ECSSVersion.CSS30),
  @DeprecatedInCSS30
  _SCROLLBAR_3DLIGHT_COLOR ("scrollbar-3dlight-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_ARROW_COLOR ("scrollbar-arrow-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_BASE_COLOR ("scrollbar-base-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_DARKSHADOW_COLOR ("scrollbar-darkshadow-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_FACE_COLOR ("scrollbar-face-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_HIGHLIGHT_COLOR ("scrollbar-highlight-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_SHADOW_COLOR ("scrollbar-shadow-color"),
  @DeprecatedInCSS30
  _SCROLLBAR_TRACK_COLOR ("scrollbar-track-color"),
  SPEAK_HEADER ("speak-header"),
  SRC ("src", ECSSVersion.CSS30),
  TAB_SIZE ("tab-size", ECSSVersion.CSS30),
  TABLE_LAYOUT ("table-layout"),
  TEXT_ALIGN ("text-align"),
  TEXT_ALIGN_LAST ("text-align-last", ECSSVersion.CSS30),
  TEXT_COMBINE_HORIZONTAL ("text-combine-horizontal", ECSSVersion.CSS30),
  TEXT_COMBINE_MODE ("text-combine-mode", ECSSVersion.CSS30),
  TEXT_DECORATION ("text-decoration"),
  TEXT_FILL_COLOR ("text-fill-color", ECSSVersion.CSS30),
  TEXT_INDENT ("text-indent"),
  TEXT_JUSTIFY ("text-justify", ECSSVersion.CSS30),
  TEXT_ORIENTATION ("text-orientation", ECSSVersion.CSS30),
  TEXT_OVERFLOW ("text-overflow", ECSSVersion.CSS30),
  TEXT_SHADOW ("text-shadow", ECSSVersion.CSS30),
  TEXT_STROKE ("text-stroke", ECSSVersion.CSS30),
  TEXT_TRANSFORM ("text-transform"),
  TOP ("top"),
  TRANSFORM ("transform", ECSSVersion.CSS30),
  TRANSFORM_ORIGIN ("transform-origin", ECSSVersion.CSS30),
  TRANSITION ("transition", ECSSVersion.CSS30),
  TRANSITION_DELAY ("transition-delay", ECSSVersion.CSS30),
  TRANSITION_DURATION ("transition-duration", ECSSVersion.CSS30),
  TRANSITION_PROPERTY ("transition-property", ECSSVersion.CSS30),
  TRANSITION_TIMING_FUNCTION ("transition-timing-function", ECSSVersion.CSS30),
  VERTICAL_ALIGN ("vertical-align"),
  VISIBILITY ("visibility"),
  UNICODE_BIDI ("unicode-bidi", ECSSVersion.CSS30),
  USER_ZOOM ("user-zoom", ECSSVersion.CSS30),
  WHITE_SPACE ("white-space"),
  WIDOWS ("widows"),
  WIDTH ("width"),
  WORD_BREAK ("word-break", ECSSVersion.CSS30),
  WORD_SPACING ("word-spacing"),
  WORD_WRAP ("word-wrap", ECSSVersion.CSS30),
  WRITING_MODE ("writing-mode", ECSSVersion.CSS30),
  Z_INDEX ("z-index"),
  ZOOM ("zoom", ECSSVersion.CSS30),
  // Do not use the following manually:
  _KHTML_BORDER_RADIUS ("-khtml-border-radius"),
  _KHTML_BORDER_TOP_LEFT_RADIUS ("-khtml-border-top-left-radius"),
  _KHTML_BORDER_TOP_RIGHT_RADIUS ("-khtml-border-top-right-radius"),
  _KHTML_BORDER_BOTTOM_LEFT_RADIUS ("-khtml-border-bottom-left-radius"),
  _KHTML_BORDER_BOTTOM_RIGHT_RADIUS ("-khtml-border-bottom-right-radius"),
  _MOZ_APPEARANCE ("-moz-appearance"),
  _MOZ_BACKGROUND_CLIP ("-moz-background-clip"),
  _MOZ_BACKGROUND_INLINE_POLICY ("-moz-background-inline-policy"),
  _MOZ_BACKGROUND_ORIGIN ("-moz-background-origin"),
  _MOZ_BACKGROUND_SIZE ("-moz-background-size"),
  _MOZ_BINDING ("-moz-binding"),
  _MOZ_BORDER_BOTTOM_COLORS ("-moz-border-bottom-colors"),
  _MOZ_BORDER_END ("-moz-border-end"),
  _MOZ_BORDER_END_COLOR ("-moz-border-end-color"),
  _MOZ_BORDER_END_STYLE ("-moz-border-end-style"),
  _MOZ_BORDER_END_WIDTH ("-moz-border-end-width"),
  _MOZ_BORDER_IMAGE ("-moz-border-image"),
  _MOZ_BORDER_LEFT_COLORS ("-moz-border-left-colors"),
  _MOZ_BORDER_RADIUS ("-moz-border-radius"),
  _MOZ_BORDER_RADIUS_BOTTOMLEFT ("-moz-border-radius-bottomleft"),
  _MOZ_BORDER_RADIUS_BOTTOMRIGHT ("-moz-border-radius-bottomright"),
  _MOZ_BORDER_RADIUS_TOPLEFT ("-moz-border-radius-topleft"),
  _MOZ_BORDER_RADIUS_TOPRIGHT ("-moz-border-radius-topright"),
  _MOZ_BORDER_RIGHT_COLORS ("-moz-border-right-colors"),
  _MOZ_BORDER_START ("-moz-border-start"),
  _MOZ_BORDER_START_COLOR ("-moz-border-start-color"),
  _MOZ_BORDER_START_STYLE ("-moz-border-start-style"),
  _MOZ_BORDER_START_WIDTH ("-moz-border-start-width"),
  _MOZ_BORDER_TOP_COLORS ("-moz-border-top-colors"),
  _MOZ_BOX_ALIGN ("-moz-box-align"),
  _MOZ_BOX_DIRECTION ("-moz-box-direction"),
  _MOZ_BOX_FLEX ("-moz-box-flex"),
  _MOZ_BOX_FLEXGROUP ("-moz-box-flexgroup"),
  _MOZ_BOX_ORDINAL_GROUP ("-moz-box-ordinal-group"),
  _MOZ_BOX_ORIENT ("-moz-box-orient"),
  _MOZ_BOX_PACK ("-moz-box-pack"),
  _MOZ_BOX_SHADOW ("-moz-box-shadow"),
  _MOZ_BOX_SIZING ("-moz-box-sizing"),
  _MOZ_COLUMN_COUNT ("-moz-column-count"),
  _MOZ_COLUMN_GAP ("-moz-column-gap"),
  _MOZ_COLUMN_RULE ("-moz-column-rule"),
  _MOZ_COLUMN_RULE_COLOR ("-moz-column-rule-color"),
  _MOZ_COLUMN_RULE_STYLE ("-moz-column-rule-style"),
  _MOZ_COLUMN_RULE_WIDTH ("-moz-column-rule-width"),
  _MOZ_COLUMN_WIDTH ("-moz-column-width"),
  _MOZ_FILTER ("-moz-filter"),
  _MOZ_FLOAT_EDGE ("-moz-float-edge"),
  _MOZ_FONT_FEATURE_SETTINGS ("-moz-font-feature-settings"),
  _MOZ_FONT_LANGUAGE_OVERRIDE ("-moz-font-language-override"),
  _MOZ_FORCE_BROKEN_IMAGE_ICON ("-moz-force-broken-image-icon"),
  _MOZ_IMAGE_REGION ("-moz-image-region"),
  _MOZ_MARGIN_END ("-moz-margin-end"),
  _MOZ_MARGIN_START ("-moz-margin-start"),
  _MOZ_MASK ("-moz-mask"),
  _MOZ_OPACITY ("-moz-opacity"),
  _MOZ_OUTLINE ("-moz-outline"),
  _MOZ_OUTLINE_COLOR ("-moz-outline-color"),
  _MOZ_OUTLINE_OFFSET ("-moz-outline-offset"),
  _MOZ_OUTLINE_RADIUS ("-moz-outline-radius"),
  _MOZ_OUTLINE_RADIUS_BOTTOMLEFT ("-moz-outline-radius-bottomleft"),
  _MOZ_OUTLINE_RADIUS_BOTTOMRIGHT ("-moz-outline-radius-bottomright"),
  _MOZ_OUTLINE_RADIUS_TOPLEFT ("-moz-outline-radius-topleft"),
  _MOZ_OUTLINE_RADIUS_TOPRIGHT ("-moz-outline-radius-topright"),
  _MOZ_OUTLINE_STYLE ("-moz-outline-style"),
  _MOZ_OUTLINE_WIDTH ("-moz-outline-width"),
  _MOZ_PADDING_END ("-moz-padding-end"),
  _MOZ_PADDING_START ("-moz-padding-start"),
  _MOZ_STACK_SIZING ("-moz-stack-sizing"),
  _MOZ_TAB_SIZE ("-moz-tab-size"),
  _MOZ_TEXT_BLINK ("-moz-text-blink"),
  _MOZ_TEXT_DECORATION_COLOR ("-moz-text-decoration-color"),
  _MOZ_TEXT_DECORATION_LINE ("-moz-text-decoration-line"),
  _MOZ_TEXT_DECORATION_STYLE ("-moz-text-decoration-style"),
  _MOZ_TRANSFORM ("-moz-transform"),
  _MOZ_TRANSFORM_ORIGIN ("-moz-transform-origin"),
  _MOZ_TRANSITION ("-moz-transition"),
  _MOZ_TRANSITION_DELAY ("-moz-transition-delay"),
  _MOZ_TRANSITION_DURATION ("-moz-transition-duration"),
  _MOZ_TRANSITION_PROPERTY ("-moz-transition-property"),
  _MOZ_TRANSITION_TIMING_FUNCTION ("-moz-transition-timing-function"),
  _MOZ_USER_FOCUS ("-moz-user-focus"),
  _MOZ_USER_INPUT ("-moz-user-input"),
  _MOZ_USER_MODIFY ("-moz-user-modify"),
  _MOZ_USER_SELECT ("-moz-user-select"),
  _MOZ_WINDOW_SHADOW ("-moz-window-shadow"),
  _MS_BACKGROUND_POSITION_X ("-ms-background-position-x"),
  _MS_BACKGROUND_POSITION_Y ("-ms-background-position-y"),
  _MS_BOX_SHADOW ("-ms-box-shadow"),
  _MS_BOX_SIZING ("-ms-box-sizing"),
  _MS_FILTER ("-ms-filter"),
  _MS_IME_MODE ("-ms-ime-mode"),
  _MS_INTERPOLATION_MODE ("-ms-interpolation-mode"),
  _MS_LAYOUT_FLOW ("-ms-layout-flow"),
  _MS_LAYOUT_GRID ("-ms-layout-grid"),
  _MS_LAYOUT_GRID_CHAR ("-ms-layout-grid-char"),
  _MS_LAYOUT_GRID_LINE ("-ms-layout-grid-line"),
  _MS_LAYOUT_GRID_MODE ("-ms-layout-grid-mode"),
  _MS_LAYOUT_GRID_TYPE ("-ms-layout-grid-type"),
  _MS_OVERFLOW_X ("-ms-overflow-x"),
  _MS_OVERFLOW_Y ("-ms-overflow-y"),
  _MS_SCROLLBAR_3DLIGHT_COLOR ("-ms-scrollbar-3dlight-color"),
  _MS_SCROLLBAR_ARROW_COLOR ("-ms-scrollbar-arrow-color"),
  _MS_SCROLLBAR_BASE_COLOR ("-ms-scrollbar-base-color"),
  _MS_SCROLLBAR_DARKSHADOW_COLOR ("-ms-scrollbar-darkshadow-color"),
  _MS_SCROLLBAR_FACE_COLOR ("-ms-scrollbar-face-color"),
  _MS_SCROLLBAR_HIGHLIGHT_COLOR ("-ms-scrollbar-highlight-color"),
  _MS_SCROLLBAR_SHADOW_COLOR ("-ms-scrollbar-shadow-color"),
  _MS_TEXT_ALIGN_LAST ("-ms-text-align-last"),
  _MS_TEXT_AUTOSPACE ("-ms-text-autospace"),
  _MS_TEXT_JUSTIFY ("-ms-text-justify"),
  _MS_TEXT_KASHIDA_SPACE ("-ms-text-kashida-space"),
  _MS_TEXT_OVERFLOW ("-ms-text-overflow"),
  _MS_TEXT_SIZE_ADJUST ("-ms-text-size-adjust"),
  _MS_TEXT_UNDERLINE_POSITION ("-ms-text-underline-position"),
  _MS_WORD_BREAK ("-ms-word-break"),
  _MS_WORD_WRAP ("-ms-word-wrap"),
  _MS_WRITING_MODE ("-ms-writing-mode"),
  _MS_ZOOM ("-ms-zoom"),
  _O_BOX_SHADOW ("-o-box-shadow"),
  _O_BOX_SIZING ("-o-box-sizing"),
  _WEBKIT_ANIMATION ("-webkit-animation"),
  _WEBKIT_ANIMATION_DELAY ("-webkit-animation-delay"),
  _WEBKIT_ANIMATION_DIRECTION ("-webkit-animation-direction"),
  _WEBKIT_ANIMATION_DURATION ("-webkit-animation-duration"),
  _WEBKIT_ANIMATION_FILL_MODE ("-webkit-animation-fill-mode"),
  _WEBKIT_ANIMATION_ITERATION_COUNT ("-webkit-animation-iteration-count"),
  _WEBKIT_ANIMATION_NAME ("-webkit-animation-name"),
  _WEBKIT_ANIMATION_PLAY_STATE ("-webkit-animation-play-state"),
  _WEBKIT_ANIMATION_TIMING_FUNCTION ("-webkit-animation-timing-function"),
  _WEBKIT_APPEARANCE ("-webkit-appearance"),
  _WEBKIT_BACKFACE_VISIBILITY ("-webkit-backface-visibility"),
  _WEBKIT_BACKGROUND_CLIP ("-webkit-background-clip"),
  _WEBKIT_BACKGROUND_COMPOSITE ("-webkit-background-composite"),
  _WEBKIT_BACKGROUND_ORIGIN ("-webkit-background-origin"),
  _WEBKIT_BACKGROUND_SIZE ("-webkit-background-size"),
  _WEBKIT_BORDER_AFTER ("-webkit-border-after"),
  _WEBKIT_BORDER_AFTER_COLOR ("-webkit-border-after-color"),
  _WEBKIT_BORDER_AFTER_STYLE ("-webkit-border-after-style"),
  _WEBKIT_BORDER_AFTER_WIDTH ("-webkit-border-after-width"),
  _WEBKIT_BORDER_BEFORE ("-webkit-border-before"),
  _WEBKIT_BORDER_BEFORE_COLOR ("-webkit-border-before-color"),
  _WEBKIT_BORDER_BEFORE_STYLE ("-webkit-border-before-style"),
  _WEBKIT_BORDER_BEFORE_WIDTH ("-webkit-border-before-width"),
  _WEBKIT_BORDER_BOTTOM_LEFT_RADIUS ("-webkit-border-bottom-left-radius"),
  _WEBKIT_BORDER_BOTTOM_RIGHT_RADIUS ("-webkit-border-bottom-right-radius"),
  _WEBKIT_BORDER_END ("-webkit-border-end"),
  _WEBKIT_BORDER_END_COLOR ("-webkit-border-end-color"),
  _WEBKIT_BORDER_END_STYLE ("-webkit-border-end-style"),
  _WEBKIT_BORDER_END_WIDTH ("-webkit-border-end-width"),
  _WEBKIT_BORDER_FIT ("-webkit-border-fit"),
  _WEBKIT_BORDER_HORIZONTAL_SPACING ("-webkit-border-horizontal-spacing"),
  _WEBKIT_BORDER_IMAGE ("-webkit-border-image"),
  _WEBKIT_BORDER_RADIUS ("-webkit-border-radius"),
  _WEBKIT_BORDER_START ("-webkit-border-start"),
  _WEBKIT_BORDER_START_COLOR ("-webkit-border-start-color"),
  _WEBKIT_BORDER_START_STYLE ("-webkit-border-start-style"),
  _WEBKIT_BORDER_START_WIDTH ("-webkit-border-start-width"),
  _WEBKIT_BORDER_TOP_LEFT_RADIUS ("-webkit-border-top-left-radius"),
  _WEBKIT_BORDER_TOP_RIGHT_RADIUS ("-webkit-border-top-right-radius"),
  _WEBKIT_BORDER_VERTICAL_SPACING ("-webkit-border-vertical-spacing"),
  _WEBKIT_BOX_ALIGN ("-webkit-box-align"),
  _WEBKIT_BOX_DIRECTION ("-webkit-box-direction"),
  _WEBKIT_BOX_FLEX ("-webkit-box-flex"),
  _WEBKIT_BOX_FLEX_GROUP ("-webkit-box-flex-group"),
  _WEBKIT_BOX_LINES ("-webkit-box-lines"),
  _WEBKIT_BOX_ORDINAL_GROUP ("-webkit-box-ordinal-group"),
  _WEBKIT_BOX_ORIENT ("-webkit-box-orient"),
  _WEBKIT_BOX_PACK ("-webkit-box-pack"),
  _WEBKIT_BOX_REFLECT ("-webkit-box-reflect"),
  _WEBKIT_BOX_SHADOW ("-webkit-box-shadow"),
  _WEBKIT_BOX_SIZING ("-webkit-box-sizing"),
  _WEBKIT_COLOR_CORRECTION ("-webkit-color-correction"),
  _WEBKIT_COLUMNS ("-webkit-columns"),
  _WEBKIT_COLUMN_BREAK_AFTER ("-webkit-column-break-after"),
  _WEBKIT_COLUMN_BREAK_BEFORE ("-webkit-column-break-before"),
  _WEBKIT_COLUMN_BREAK_INSIDE ("-webkit-column-break-inside"),
  _WEBKIT_COLUMN_COUNT ("-webkit-column-count"),
  _WEBKIT_COLUMN_GAP ("-webkit-column-gap"),
  _WEBKIT_COLUMN_RULE ("-webkit-column-rule"),
  _WEBKIT_COLUMN_RULE_COLOR ("-webkit-column-rule-color"),
  _WEBKIT_COLUMN_RULE_STYLE ("-webkit-column-rule-style"),
  _WEBKIT_COLUMN_RULE_WIDTH ("-webkit-column-rule-width"),
  _WEBKIT_COLUMN_SPAN ("-webkit-column-span"),
  _WEBKIT_COLUMN_WIDTH ("-webkit-column-width"),
  _WEBKIT_FILTER ("-webkit-filter"),
  _WEBKIT_FLEX_ALIGN ("-webkit-flex-align"),
  _WEBKIT_FLEX_FLOW ("-webkit-flex-flow"),
  _WEBKIT_FLEX_ORDER ("-webkit-flex-order"),
  _WEBKIT_FLEX_PACK ("-webkit-flex-pack"),
  _WEBKIT_FLOW_FROM ("-webkit-flow-from"),
  _WEBKIT_FLOW_INTO ("-webkit-flow-into"),
  _WEBKIT_FONT_FEATURE_SETTINGS ("-webkit-font-feature-settings"),
  _WEBKIT_FONT_SIZE_DELTA ("-webkit-font-size-delta"),
  _WEBKIT_FONT_SMOOTHING ("-webkit-font-smoothing"),
  _WEBKIT_HIGHLIGHT ("-webkit-highlight"),
  _WEBKIT_HYPHENATE_CHARACTER ("-webkit-hyphenate-character"),
  _WEBKIT_HYPHENATE_LIMIT_AFTER ("-webkit-hyphenate-limit-after"),
  _WEBKIT_HYPHENATE_LIMIT_BEFORE ("-webkit-hyphenate-limit-before"),
  _WEBKIT_HYPHENATE_LIMIT_LINES ("-webkit-hyphenate-limit-lines"),
  _WEBKIT_HYPHENS ("-webkit-hyphens"),
  _WEBKIT_LINE_BOX_CONTAIN ("-webkit-line-box-contain"),
  _WEBKIT_LINE_BREAK ("-webkit-line-break"),
  _WEBKIT_LINE_CLAMP ("-webkit-line-clamp"),
  _WEBKIT_LOCALE ("-webkit-locale"),
  _WEBKIT_LOGICAL_HEIGHT ("-webkit-logical-height"),
  _WEBKIT_LOGICAL_WIDTH ("-webkit-logical-width"),
  _WEBKIT_MARGIN_AFTER ("-webkit-margin-after"),
  _WEBKIT_MARGIN_AFTER_COLLAPSE ("-webkit-margin-after-collapse"),
  _WEBKIT_MARGIN_BEFORE ("-webkit-margin-before"),
  _WEBKIT_MARGIN_BEFORE_COLLAPSE ("-webkit-margin-before-collapse"),
  _WEBKIT_MARGIN_BOTTOM_COLLAPSE ("-webkit-margin-bottom-collapse"),
  _WEBKIT_MARGIN_COLLAPSE ("-webkit-margin-collapse"),
  _WEBKIT_MARGIN_END ("-webkit-margin-end"),
  _WEBKIT_MARGIN_START ("-webkit-margin-start"),
  _WEBKIT_MARGIN_TOP_COLLAPSE ("-webkit-margin-top-collapse"),
  _WEBKIT_MARQUEE ("-webkit-marquee"),
  _WEBKIT_MARQUEE_DIRECTION ("-webkit-marquee-direction"),
  _WEBKIT_MARQUEE_INCREMENT ("-webkit-marquee-increment"),
  _WEBKIT_MARQUEE_REPETITION ("-webkit-marquee-repetition"),
  _WEBKIT_MARQUEE_SPEED ("-webkit-marquee-speed"),
  _WEBKIT_MARQUEE_STYLE ("-webkit-marquee-style"),
  _WEBKIT_MASK ("-webkit-mask"),
  _WEBKIT_MASK_ATTACHMENT ("-webkit-mask-attachment"),
  _WEBKIT_MASK_BOX_IMAGE ("-webkit-mask-box-image"),
  _WEBKIT_MASK_BOX_IMAGE_OUTSET ("-webkit-mask-box-image-outset"),
  _WEBKIT_MASK_BOX_IMAGE_REPEAT ("-webkit-mask-box-image-repeat"),
  _WEBKIT_MASK_BOX_IMAGE_SLICE ("-webkit-mask-box-image-slice"),
  _WEBKIT_MASK_BOX_IMAGE_SOURCE ("-webkit-mask-box-image-source"),
  _WEBKIT_MASK_BOX_IMAGE_WIDTH ("-webkit-mask-box-image-width"),
  _WEBKIT_MASK_CLIP ("-webkit-mask-clip"),
  _WEBKIT_MASK_COMPOSITE ("-webkit-mask-composite"),
  _WEBKIT_MASK_IMAGE ("-webkit-mask-image"),
  _WEBKIT_MASK_ORIGIN ("-webkit-mask-origin"),
  _WEBKIT_MASK_POSITION ("-webkit-mask-position"),
  _WEBKIT_MASK_POSITION_X ("-webkit-mask-position-x"),
  _WEBKIT_MASK_POSITION_Y ("-webkit-mask-position-y"),
  _WEBKIT_MASK_REPEAT ("-webkit-mask-repeat"),
  _WEBKIT_MASK_REPEAT_X ("-webkit-mask-repeat-x"),
  _WEBKIT_MASK_REPEAT_Y ("-webkit-mask-repeat-y"),
  _WEBKIT_MASK_SIZE ("-webkit-mask-size"),
  _WEBKIT_MATCH_NEAREST_MAIL_BLOCKQUOTE_COLOR ("-webkit-match-nearest-mail-blockquote-color"),
  _WEBKIT_MAX_LOGICAL_HEIGHT ("-webkit-max-logical-height"),
  _WEBKIT_MAX_LOGICAL_WIDTH ("-webkit-max-logical-width"),
  _WEBKIT_MIN_LOGICAL_HEIGHT ("-webkit-min-logical-height"),
  _WEBKIT_MIN_LOGICAL_WIDTH ("-webkit-min-logical-width"),
  _WEBKIT_NBSP_MODE ("-webkit-nbsp-mode"),
  _WEBKIT_OPACITY ("-webkit-opacity"),
  _WEBKIT_PADDING_AFTER ("-webkit-padding-after"),
  _WEBKIT_PADDING_BEFORE ("-webkit-padding-before"),
  _WEBKIT_PADDING_END ("-webkit-padding-end"),
  _WEBKIT_PADDING_START ("-webkit-padding-start"),
  _WEBKIT_PERSPECTIVE ("-webkit-perspective"),
  _WEBKIT_PERSPECTIVE_ORIGIN ("-webkit-perspective-origin"),
  _WEBKIT_PERSPECTIVE_ORIGIN_X ("-webkit-perspective-origin-x"),
  _WEBKIT_PERSPECTIVE_ORIGIN_Y ("-webkit-perspective-origin-y"),
  _WEBKIT_REGION_BREAK_AFTER ("-webkit-region-break-after"),
  _WEBKIT_REGION_BREAK_BEFORE ("-webkit-region-break-before"),
  _WEBKIT_REGION_BREAK_INSIDE ("-webkit-region-break-inside"),
  _WEBKIT_REGION_OVERFLOW ("-webkit-region-overflow"),
  _WEBKIT_RTL_ORDERING ("-webkit-rtl-ordering"),
  _WEBKIT_TAP_HIGHLIGHT_COLOR ("-webkit-tap-highlight-color"),
  _WEBKIT_TEXT_COMBINE ("-webkit-text-combine"),
  _WEBKIT_TEXT_DECORATIONS_IN_EFFECT ("-webkit-text-decorations-in-effect"),
  _WEBKIT_TEXT_EMPHASIS ("-webkit-text-emphasis"),
  _WEBKIT_TEXT_EMPHASIS_COLOR ("-webkit-text-emphasis-color"),
  _WEBKIT_TEXT_EMPHASIS_POSITION ("-webkit-text-emphasis-position"),
  _WEBKIT_TEXT_EMPHASIS_STYLE ("-webkit-text-emphasis-style"),
  _WEBKIT_TEXT_FILL_COLOR ("-webkit-text-fill-color"),
  _WEBKIT_TEXT_ORIENTATION ("-webkit-text-orientation"),
  _WEBKIT_TEXT_SECURITY ("-webkit-text-security"),
  _WEBKIT_TEXT_SIZE_ADJUST ("-webkit-text-size-adjust"),
  _WEBKIT_TEXT_STROKE ("-webkit-text-stroke"),
  _WEBKIT_TEXT_STROKE_COLOR ("-webkit-text-stroke-color"),
  _WEBKIT_TEXT_STROKE_WIDTH ("-webkit-text-stroke-width"),
  _WEBKIT_TRANSFORM ("-webkit-transform"),
  _WEBKIT_TRANSFORM_ORIGIN ("-webkit-transform-origin"),
  _WEBKIT_TRANSFORM_ORIGIN_X ("-webkit-transform-origin-x"),
  _WEBKIT_TRANSFORM_ORIGIN_Y ("-webkit-transform-origin-y"),
  _WEBKIT_TRANSFORM_ORIGIN_Z ("-webkit-transform-origin-z"),
  _WEBKIT_TRANSFORM_STYLE ("-webkit-transform-style"),
  _WEBKIT_TRANSITION ("-webkit-transition"),
  _WEBKIT_TRANSITION_DELAY ("-webkit-transition-delay"),
  _WEBKIT_TRANSITION_DURATION ("-webkit-transition-duration"),
  _WEBKIT_TRANSITION_PROPERTY ("-webkit-transition-property"),
  _WEBKIT_TRANSITION_TIMING_FUNCTION ("-webkit-transition-timing-function"),
  _WEBKIT_USER_DRAG ("-webkit-user-drag"),
  _WEBKIT_USER_MODIFY ("-webkit-user-modify"),
  _WEBKIT_USER_SELECT ("-webkit-user-select"),
  _WEBKIT_WRAP_SHAPE ("-webkit-wrap-shape"),
  _WEBKIT_WRITING_MODE ("-webkit-writing-mode");

  private final String m_sName;
  private final ECSSVersion m_eVersion;

  private ECSSProperty (@Nonnull @Nonempty final String sName)
  {
    this (sName, ECSSVersion.CSS21);
  }

  private ECSSProperty (@Nonnull @Nonempty final String sName, @Nonnull final ECSSVersion eVersion)
  {
    m_sName = sName;
    m_eVersion = eVersion;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nonnull
  public ECSSVersion getMinimumCSSVersion ()
  {
    return m_eVersion;
  }

  public boolean isKHTMLSpecific ()
  {
    return m_sName.startsWith ("-khtml-");
  }

  public boolean isMicrosoftSpecific ()
  {
    return m_sName.startsWith ("-ms-");
  }

  public boolean isMozillaSpecific ()
  {
    return m_sName.startsWith ("-moz-");
  }

  public boolean isOperaSpecific ()
  {
    return m_sName.startsWith ("-o-");
  }

  public boolean isWebkitSpecific ()
  {
    return m_sName.startsWith ("-webkit-");
  }

  public boolean isBrowserSpecific ()
  {
    return m_sName.startsWith ("-");
  }

  @Nullable
  public static ECSSProperty getFromNameOrNull (@Nullable final String sName)
  {
    return EnumHelper.getFromNameOrNull (ECSSProperty.class, sName);
  }

  @Nullable
  public static ECSSProperty getFromNameOrNullHandlingHacks (@Nullable final String sName)
  {
    if (StringHelper.hasText (sName))
    {
      String sRealName;
      // IE hacks
      if (sName.startsWith ("*") || sName.startsWith ("_") || sName.startsWith ("$"))
        sRealName = sName.substring (1);
      else
        sRealName = sName;
      return getFromNameOrNull (sRealName);
    }
    return null;
  }
}
