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

import javax.annotation.concurrent.Immutable;

import com.phloc.css.ECSSUnit;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.propertyvalue.ICSSValue;

/**
 * Contains the most commonly used CSS properties plus the available value
 * ranges.
 * 
 * @author philip
 */
@Immutable
public final class CCSSProperties
{
  // Text formatting
  public static final ICSSProperty FONT_FAMILY = new CSSPropertyFree (ECSSProperty.FONT_FAMILY);
  public static final ICSSProperty FONT_STYLE = new CSSPropertyEnum (ECSSProperty.FONT_STYLE,
                                                                     CCSSValue.ITALIC,
                                                                     CCSSValue.OBLIQUE,
                                                                     CCSSValue.NORMAL);
  public static final ICSSProperty FONT_VARIANT = new CSSPropertyEnum (ECSSProperty.FONT_VARIANT,
                                                                       CCSSValue.SMALL_CAPS,
                                                                       CCSSValue.NORMAL);
  public static final ICSSProperty FONT_SIZE = new CSSPropertyEnumOrNumber (ECSSProperty.FONT_SIZE,
                                                                            true,
                                                                            CCSSValue.XX_SMALL,
                                                                            CCSSValue.X_SMALL,
                                                                            CCSSValue.SMALL,
                                                                            CCSSValue.MEDIUM,
                                                                            CCSSValue.LARGE,
                                                                            CCSSValue.X_LARGE,
                                                                            CCSSValue.XX_LARGE,
                                                                            CCSSValue.SMALLER,
                                                                            CCSSValue.LARGER);
  public static final ICSSProperty FONT_WEIGHT = new CSSPropertyEnum (ECSSProperty.FONT_WEIGHT,
                                                                      CCSSValue.BOLD,
                                                                      CCSSValue.BOLDER,
                                                                      CCSSValue.LIGHTER,
                                                                      CCSSValue.NORMAL,
                                                                      CCSSValue._100,
                                                                      CCSSValue._200,
                                                                      CCSSValue._300,
                                                                      CCSSValue._400,
                                                                      CCSSValue._500,
                                                                      CCSSValue._600,
                                                                      CCSSValue._700,
                                                                      CCSSValue._800,
                                                                      CCSSValue._900);
  public static final ICSSProperty WORD_SPACING = new CSSPropertyNumber (ECSSProperty.WORD_SPACING, false);
  public static final ICSSProperty LETTER_SPACING = new CSSPropertyNumber (ECSSProperty.LETTER_SPACING, false);
  public static final ICSSProperty TEXT_DECORATION = new CSSPropertyEnum (ECSSProperty.TEXT_DECORATION,
                                                                          CCSSValue.UNDERLINE,
                                                                          CCSSValue.OVERLINE,
                                                                          CCSSValue.LINE_THROUGH,
                                                                          CCSSValue.BLINK,
                                                                          CCSSValue.NONE);
  public static final ICSSProperty TEXT_TRANSFORM = new CSSPropertyEnum (ECSSProperty.TEXT_TRANSFORM,
                                                                         CCSSValue.CAPITALIZE,
                                                                         CCSSValue.UPPERCASE,
                                                                         CCSSValue.LOWERCASE,
                                                                         CCSSValue.NONE);
  public static final ICSSProperty COLOR = new CSSPropertyColor (ECSSProperty.COLOR);

  // Orientation and paragraph control
  public static final ICSSProperty TEXT_INDENT = new CSSPropertyNumber (ECSSProperty.TEXT_INDENT, true);
  public static final ICSSProperty LINE_HEIGHT = new CSSPropertyNumber (ECSSProperty.LINE_HEIGHT, true);
  public static final ICSSProperty VERTICAL_ALIGN = new CSSPropertyEnum (ECSSProperty.VERTICAL_ALIGN,
                                                                         CCSSValue.TOP,
                                                                         CCSSValue.MIDDLE,
                                                                         CCSSValue.BOTTOM,
                                                                         CCSSValue.BASELINE,
                                                                         CCSSValue.SUB,
                                                                         CCSSValue.SUPER,
                                                                         CCSSValue.TEXT_TOP,
                                                                         CCSSValue.TEXT_BOTTOM);
  public static final ICSSProperty TEXT_ALIGN = new CSSPropertyEnum (ECSSProperty.TEXT_ALIGN,
                                                                     CCSSValue.LEFT,
                                                                     CCSSValue.CENTER,
                                                                     CCSSValue.RIGHT,
                                                                     CCSSValue.JUSTIFY);
  public static final ICSSProperty WHITE_SPACE = new CSSPropertyEnum (ECSSProperty.WHITE_SPACE,
                                                                      CCSSValue.NORMAL,
                                                                      CCSSValue.PRE,
                                                                      CCSSValue.NOWRAP,
                                                                      CCSSValue.PRE_LINE,
                                                                      CCSSValue.PRE_WRAP);

  // margin
  public static final ICSSProperty MARGIN_TOP = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_TOP,
                                                                             true,
                                                                             CCSSValue.AUTO,
                                                                             CCSSValue.INHERIT);
  public static final ICSSProperty MARGIN_RIGHT = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_RIGHT,
                                                                               true,
                                                                               CCSSValue.AUTO,
                                                                               CCSSValue.INHERIT);
  public static final ICSSProperty MARGIN_BOTTOM = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_BOTTOM,
                                                                                true,
                                                                                CCSSValue.AUTO,
                                                                                CCSSValue.INHERIT);
  public static final ICSSProperty MARGIN_LEFT = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_LEFT,
                                                                              true,
                                                                              CCSSValue.AUTO,
                                                                              CCSSValue.INHERIT);
  public static final ICSSProperty MARGIN = new CSSPropertyEnumOrNumbers (ECSSProperty.MARGIN,
                                                                          true,
                                                                          1,
                                                                          4,
                                                                          CCSSValue.AUTO,
                                                                          CCSSValue.INHERIT);
  // padding
  public static final ICSSProperty PADDING_TOP = new CSSPropertyNumber (ECSSProperty.PADDING_TOP, true);
  public static final ICSSProperty PADDING_RIGHT = new CSSPropertyNumber (ECSSProperty.PADDING_RIGHT, true);
  public static final ICSSProperty PADDING_BOTTOM = new CSSPropertyNumber (ECSSProperty.PADDING_BOTTOM, true);
  public static final ICSSProperty PADDING_LEFT = new CSSPropertyNumber (ECSSProperty.PADDING_LEFT, true);
  public static final ICSSProperty PADDING = new CSSPropertyNumbers (ECSSProperty.PADDING, true, 1, 4);

  // borders
  public static final ICSSProperty BORDER = new CSSPropertyFree (ECSSProperty.BORDER);
  public static final ICSSProperty BORDER_TOP = new CSSPropertyFree (ECSSProperty.BORDER_TOP);
  public static final ICSSProperty BORDER_RIGHT = new CSSPropertyFree (ECSSProperty.BORDER_RIGHT);
  public static final ICSSProperty BORDER_BOTTOM = new CSSPropertyFree (ECSSProperty.BORDER_BOTTOM);
  public static final ICSSProperty BORDER_LEFT = new CSSPropertyFree (ECSSProperty.BORDER_LEFT);
  public static final ICSSProperty BORDER_TOP_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_TOP_WIDTH,
                                                                                   false,
                                                                                   CCSSValue.THIN,
                                                                                   CCSSValue.MEDIUM,
                                                                                   CCSSValue.THICK);
  public static final ICSSProperty BORDER_RIGHT_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_RIGHT_WIDTH,
                                                                                     false,
                                                                                     CCSSValue.THIN,
                                                                                     CCSSValue.MEDIUM,
                                                                                     CCSSValue.THICK);
  public static final ICSSProperty BORDER_BOTTOM_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_BOTTOM_WIDTH,
                                                                                      false,
                                                                                      CCSSValue.THIN,
                                                                                      CCSSValue.MEDIUM,
                                                                                      CCSSValue.THICK);
  public static final ICSSProperty BORDER_LEFT_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_LEFT_WIDTH,
                                                                                    false,
                                                                                    CCSSValue.THIN,
                                                                                    CCSSValue.MEDIUM,
                                                                                    CCSSValue.THICK);
  public static final ICSSProperty BORDER_WIDTH = new CSSPropertyEnumOrNumbers (ECSSProperty.BORDER_WIDTH,
                                                                                false,
                                                                                1,
                                                                                4,
                                                                                CCSSValue.THIN,
                                                                                CCSSValue.MEDIUM,
                                                                                CCSSValue.THICK);
  public static final ICSSProperty BORDER_TOP_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_TOP_COLOR,
                                                                                  CCSSValue.TRANSPARENT);
  public static final ICSSProperty BORDER_RIGHT_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_RIGHT_COLOR,
                                                                                    CCSSValue.TRANSPARENT);
  public static final ICSSProperty BORDER_BOTTOM_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_BOTTOM_COLOR,
                                                                                     CCSSValue.TRANSPARENT);
  public static final ICSSProperty BORDER_LEFT_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_LEFT_COLOR,
                                                                                   CCSSValue.TRANSPARENT);
  public static final ICSSProperty BORDER_COLOR = new CSSPropertyEnumOrColors (ECSSProperty.BORDER_COLOR,
                                                                               1,
                                                                               4,
                                                                               CCSSValue.TRANSPARENT);
  public static final ICSSProperty BORDER_TOP_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_TOP_STYLE,
                                                                           CCSSValue.NONE,
                                                                           CCSSValue.HIDDEN,
                                                                           CCSSValue.DOTTED,
                                                                           CCSSValue.DASHED,
                                                                           CCSSValue.SOLID,
                                                                           CCSSValue.DOUBLE,
                                                                           CCSSValue.GROOVE,
                                                                           CCSSValue.RIDGE,
                                                                           CCSSValue.INSET,
                                                                           CCSSValue.OUTSET);
  public static final ICSSProperty BORDER_RIGHT_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_RIGHT_STYLE,
                                                                             CCSSValue.NONE,
                                                                             CCSSValue.HIDDEN,
                                                                             CCSSValue.DOTTED,
                                                                             CCSSValue.DASHED,
                                                                             CCSSValue.SOLID,
                                                                             CCSSValue.DOUBLE,
                                                                             CCSSValue.GROOVE,
                                                                             CCSSValue.RIDGE,
                                                                             CCSSValue.INSET,
                                                                             CCSSValue.OUTSET);
  public static final ICSSProperty BORDER_BOTTOM_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_BOTTOM_STYLE,
                                                                              CCSSValue.NONE,
                                                                              CCSSValue.HIDDEN,
                                                                              CCSSValue.DOTTED,
                                                                              CCSSValue.DASHED,
                                                                              CCSSValue.SOLID,
                                                                              CCSSValue.DOUBLE,
                                                                              CCSSValue.GROOVE,
                                                                              CCSSValue.RIDGE,
                                                                              CCSSValue.INSET,
                                                                              CCSSValue.OUTSET);
  public static final ICSSProperty BORDER_LEFT_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_LEFT_STYLE,
                                                                            CCSSValue.NONE,
                                                                            CCSSValue.HIDDEN,
                                                                            CCSSValue.DOTTED,
                                                                            CCSSValue.DASHED,
                                                                            CCSSValue.SOLID,
                                                                            CCSSValue.DOUBLE,
                                                                            CCSSValue.GROOVE,
                                                                            CCSSValue.RIDGE,
                                                                            CCSSValue.INSET,
                                                                            CCSSValue.OUTSET);
  public static final ICSSProperty BORDER_STYLE = new CSSPropertyEnums (ECSSProperty.BORDER_STYLE,
                                                                        1,
                                                                        4,
                                                                        CCSSValue.NONE,
                                                                        CCSSValue.HIDDEN,
                                                                        CCSSValue.DOTTED,
                                                                        CCSSValue.DASHED,
                                                                        CCSSValue.SOLID,
                                                                        CCSSValue.DOUBLE,
                                                                        CCSSValue.GROOVE,
                                                                        CCSSValue.RIDGE,
                                                                        CCSSValue.INSET,
                                                                        CCSSValue.OUTSET);
  public static final ICSSProperty BORDER_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_RADIUS);
  public static final ICSSProperty BORDER_TOP_LEFT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_TOP_LEFT_RADIUS);
  public static final ICSSProperty BORDER_TOP_RIGHT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_TOP_RIGHT_RADIUS);
  public static final ICSSProperty BORDER_BOTTOM_LEFT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_BOTTOM_LEFT_RADIUS);
  public static final ICSSProperty BORDER_BOTTOM_RIGHT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_BOTTOM_RIGHT_RADIUS);
  public static final ICSSProperty OUTLINE_WIDTH = new CSSPropertyEnum (ECSSProperty.OUTLINE_WIDTH,
                                                                        CCSSValue.THIN,
                                                                        CCSSValue.MEDIUM,
                                                                        CCSSValue.THICK);
  public static final ICSSProperty OUTLINE_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.OUTLINE_COLOR,
                                                                               CCSSValue.INVERT);
  public static final ICSSProperty OUTLINE_STYLE = new CSSPropertyEnum (ECSSProperty.OUTLINE_STYLE,
                                                                        CCSSValue.NONE,
                                                                        CCSSValue.HIDDEN,
                                                                        CCSSValue.DOTTED,
                                                                        CCSSValue.DASHED,
                                                                        CCSSValue.SOLID,
                                                                        CCSSValue.DOUBLE,
                                                                        CCSSValue.GROOVE,
                                                                        CCSSValue.RIDGE,
                                                                        CCSSValue.INSET,
                                                                        CCSSValue.OUTSET);

  // background stuff
  public static final ICSSProperty BACKGROUND = new CSSPropertyFree (ECSSProperty.BACKGROUND);
  public static final ICSSProperty BACKGROUND_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BACKGROUND_COLOR,
                                                                                  CCSSValue.TRANSPARENT);
  public static final ICSSProperty BACKGROUND_IMAGE = new CSSPropertyURL (ECSSProperty.BACKGROUND_IMAGE);
  public static final ICSSProperty BACKGROUND_REPEAT = new CSSPropertyEnum (ECSSProperty.BACKGROUND_REPEAT,
                                                                            CCSSValue.REPEAT,
                                                                            CCSSValue.REPEAT_X,
                                                                            CCSSValue.REPEAT_Y,
                                                                            CCSSValue.NO_REPEAT);
  public static final ICSSProperty BACKGROUND_ATTACHMENT = new CSSPropertyEnum (ECSSProperty.BACKGROUND_ATTACHMENT,
                                                                                CCSSValue.SCROLL,
                                                                                CCSSValue.FIXED);
  public static final ICSSProperty BACKGROUND_POSITION = new CSSPropertyEnum (ECSSProperty.BACKGROUND_POSITION,
                                                                              CCSSValue.TOP,
                                                                              CCSSValue.BOTTOM,
                                                                              CCSSValue.CENTER,
                                                                              CCSSValue.LEFT,
                                                                              CCSSValue.RIGHT);

  // list formatting
  public static final ICSSProperty LIST_STYLE_TYPE = new CSSPropertyEnum (ECSSProperty.LIST_STYLE_TYPE,
                                                                          CCSSValue.DECIMAL,
                                                                          CCSSValue.LOWER_ROMAN,
                                                                          CCSSValue.UPPER_ROMAN,
                                                                          CCSSValue.LOWER_ALPHA,
                                                                          CCSSValue.UPPER_ALPHA,
                                                                          CCSSValue.LOWER_LATIN,
                                                                          CCSSValue.UPPER_LATIN,
                                                                          CCSSValue.DISC,
                                                                          CCSSValue.CIRCLE,
                                                                          CCSSValue.SQUARE,
                                                                          CCSSValue.NONE);
  public static final ICSSProperty LIST_STYLE_POSITION = new CSSPropertyEnum (ECSSProperty.LIST_STYLE_POSITION,
                                                                              CCSSValue.INSIDE,
                                                                              CCSSValue.OUTSIDE);
  public static final ICSSProperty LIST_STYLE_IMAGE = new CSSPropertyEnumOrURL (ECSSProperty.LIST_STYLE_IMAGE,
                                                                                CCSSValue.NONE);

  // table formatting
  public static final ICSSProperty CAPTION_SIDE = new CSSPropertyEnum (ECSSProperty.CAPTION_SIDE,
                                                                       CCSSValue.TOP,
                                                                       CCSSValue.BOTTOM);
  public static final ICSSProperty TABLE_LAYOUT = new CSSPropertyEnum (ECSSProperty.TABLE_LAYOUT,
                                                                       CCSSValue.AUTO,
                                                                       CCSSValue.FIXED);
  public static final ICSSProperty BORDER_COLLAPSE = new CSSPropertyEnum (ECSSProperty.BORDER_COLLAPSE,
                                                                          CCSSValue.SEPARATE,
                                                                          CCSSValue.COLLAPSE);
  public static final ICSSProperty BORDER_SPACING = new CSSPropertyNumber (ECSSProperty.BORDER_SPACING, true);
  public static final ICSSProperty EMPTY_CELLS = new CSSPropertyEnum (ECSSProperty.EMPTY_CELLS,
                                                                      CCSSValue.SHOW,
                                                                      CCSSValue.HIDE);
  public static final ICSSProperty SPEAK_HEADER = new CSSPropertyEnum (ECSSProperty.SPEAK_HEADER,
                                                                       CCSSValue.ALWAYS,
                                                                       CCSSValue.ONCE);

  // positioning
  public static final ICSSProperty POSITION = new CSSPropertyEnum (ECSSProperty.POSITION,
                                                                   CCSSValue.STATIC,
                                                                   CCSSValue.RELATIVE,
                                                                   CCSSValue.ABSOLUTE,
                                                                   CCSSValue.FIXED);
  public static final ICSSProperty TOP = new CSSPropertyEnumOrNumber (ECSSProperty.TOP, true, CCSSValue.AUTO);
  public static final ICSSProperty LEFT = new CSSPropertyEnumOrNumber (ECSSProperty.LEFT, true, CCSSValue.AUTO);
  public static final ICSSProperty BOTTOM = new CSSPropertyEnumOrNumber (ECSSProperty.BOTTOM, true, CCSSValue.AUTO);
  public static final ICSSProperty RIGHT = new CSSPropertyEnumOrNumber (ECSSProperty.RIGHT, true, CCSSValue.AUTO);
  public static final ICSSProperty WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.WIDTH, true, CCSSValue.AUTO);
  public static final ICSSProperty MIN_WIDTH = new CSSPropertyNumber (ECSSProperty.MIN_WIDTH, true);
  public static final ICSSProperty MAX_WIDTH = new CSSPropertyNumber (ECSSProperty.MAX_WIDTH, true);
  public static final ICSSProperty HEIGHT = new CSSPropertyEnumOrNumber (ECSSProperty.HEIGHT, true, CCSSValue.AUTO);
  public static final ICSSProperty MIN_HEIGHT = new CSSPropertyNumber (ECSSProperty.MIN_HEIGHT, true);
  public static final ICSSProperty MAX_HEIGHT = new CSSPropertyNumber (ECSSProperty.MAX_HEIGHT, true);
  public static final ICSSProperty OVERFLOW = new CSSPropertyEnum (ECSSProperty.OVERFLOW,
                                                                   CCSSValue.VISIBLE,
                                                                   CCSSValue.HIDDEN,
                                                                   CCSSValue.SCROLL,
                                                                   CCSSValue.AUTO);
  public static final ICSSProperty DIRECTION = new CSSPropertyEnum (ECSSProperty.DIRECTION,
                                                                    CCSSValue.LTR,
                                                                    CCSSValue.RTL);
  public static final ICSSProperty FLOAT = new CSSPropertyEnum (ECSSProperty.FLOAT,
                                                                CCSSValue.LEFT,
                                                                CCSSValue.RIGHT,
                                                                CCSSValue.NONE);
  public static final ICSSProperty CLEAR = new CSSPropertyEnum (ECSSProperty.CLEAR,
                                                                CCSSValue.LEFT,
                                                                CCSSValue.RIGHT,
                                                                CCSSValue.BOTH,
                                                                CCSSValue.NONE);
  public static final ICSSProperty Z_INDEX = new CSSPropertyNumber (ECSSProperty.Z_INDEX, false);
  public static final ICSSProperty DISPLAY = new CSSPropertyEnum (ECSSProperty.DISPLAY,
                                                                  CCSSValue.BLOCK,
                                                                  CCSSValue.INLINE,
                                                                  CCSSValue.INLINE_BLOCK,
                                                                  CCSSValue.LIST_ITEM,
                                                                  CCSSValue.RUN_IN,
                                                                  CCSSValue.NONE,
                                                                  CCSSValue.TABLE,
                                                                  CCSSValue.INLINE_TABLE,
                                                                  CCSSValue.TABLE_ROW,
                                                                  CCSSValue.TABLE_CELL,
                                                                  CCSSValue.TABLE_ROW_GROUP,
                                                                  CCSSValue.TABLE_HEADER_GROUP,
                                                                  CCSSValue.TABLE_FOOTER_GROUP,
                                                                  CCSSValue.TABLE_COLUMN,
                                                                  CCSSValue.TABLE_COLUMN_GROUP,
                                                                  CCSSValue.TABLE_CAPTION,
                                                                  "-moz-inline-block");
  public static final ICSSProperty VISIBILITY = new CSSPropertyEnum (ECSSProperty.VISIBILITY,
                                                                     CCSSValue.VISIBLE,
                                                                     CCSSValue.HIDDEN,
                                                                     CCSSValue.COLLAPSE);
  public static final ICSSProperty CLIP = new CSSPropertyEnumOrRect (ECSSProperty.CLIP,
                                                                     CCSSValue.AUTO,
                                                                     CCSSValue.INHERIT);

  // display window
  public static final ICSSProperty CURSOR = new CSSPropertyEnum (ECSSProperty.CURSOR,
                                                                 CCSSValue.AUTO,
                                                                 CCSSValue.DEFAULT,
                                                                 CCSSValue.CROSSHAIR,
                                                                 CCSSValue.POINTER,
                                                                 CCSSValue.MOVE,
                                                                 CCSSValue.N_RESIZE,
                                                                 CCSSValue.NE_RESIZE,
                                                                 CCSSValue.E_RESIZE,
                                                                 CCSSValue.SE_RESIZE,
                                                                 CCSSValue.S_RESIZE,
                                                                 CCSSValue.SW_RESIZE,
                                                                 CCSSValue.W_RESIZE,
                                                                 CCSSValue.NW_RESIZE,
                                                                 CCSSValue.TEXT,
                                                                 CCSSValue.WAIT,
                                                                 CCSSValue.HELP,
                                                                 CCSSValue.PROGRESS);

  public static final ICSSProperty OPACITY = new CSSPropertyNumber (ECSSProperty.OPACITY, false);

  // form stuff
  public static final ICSSProperty CONTENT = new CSSPropertyFree (ECSSProperty.CONTENT);

  // Special predefined properties that are used quite often
  public static final ICSSValue DISPLAY_BLOCK = DISPLAY.newValue (CCSSValue.BLOCK);
  public static final ICSSValue DISPLAY_INLINE = DISPLAY.newValue (CCSSValue.INLINE);
  public static final ICSSValue DISPLAY_INLINE_BLOCK = DISPLAY.newValue (CCSSValue.INLINE_BLOCK);
  public static final ICSSValue DISPLAY_NONE = DISPLAY.newValue (CCSSValue.NONE);
  public static final ICSSValue VISIBILITY_VISIBLE = VISIBILITY.newValue (CCSSValue.VISIBLE);
  public static final ICSSValue VISIBILITY_HIDDEN = VISIBILITY.newValue (CCSSValue.HIDDEN);
  public static final ICSSValue WIDTH_0 = WIDTH.newValue ("0");
  public static final ICSSValue WIDTH_100PERC = WIDTH.newValue (ECSSUnit.px (100));
  public static final ICSSValue HEIGHT_0 = HEIGHT.newValue ("0");
  public static final ICSSValue HEIGHT_100PERC = HEIGHT.newValue (ECSSUnit.px (100));
  public static final ICSSValue PADDING_0 = PADDING.newValue ("0");
  public static final ICSSValue MARGIN_0 = MARGIN.newValue ("0");

  private CCSSProperties ()
  {}
}
