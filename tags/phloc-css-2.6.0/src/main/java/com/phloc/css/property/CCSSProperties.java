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

import javax.annotation.concurrent.Immutable;

import com.phloc.css.CCSS;
import com.phloc.css.ECSSProperty;
import com.phloc.css.ICSSProperty;
import com.phloc.css.ICSSValue;

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
                                                                     CCSS.ITALIC,
                                                                     CCSS.OBLIQUE,
                                                                     CCSS.NORMAL);
  public static final ICSSProperty FONT_VARIANT = new CSSPropertyEnum (ECSSProperty.FONT_VARIANT,
                                                                       CCSS.SMALL_CAPS,
                                                                       CCSS.NORMAL);
  public static final ICSSProperty FONT_SIZE = new CSSPropertyEnumOrNumber (ECSSProperty.FONT_SIZE,
                                                                            true,
                                                                            CCSS.XX_SMALL,
                                                                            CCSS.X_SMALL,
                                                                            CCSS.SMALL,
                                                                            CCSS.MEDIUM,
                                                                            CCSS.LARGE,
                                                                            CCSS.X_LARGE,
                                                                            CCSS.XX_LARGE,
                                                                            CCSS.SMALLER,
                                                                            CCSS.LARGER);
  public static final ICSSProperty FONT_WEIGHT = new CSSPropertyEnum (ECSSProperty.FONT_WEIGHT,
                                                                      CCSS.BOLD,
                                                                      CCSS.BOLDER,
                                                                      CCSS.LIGHTER,
                                                                      CCSS.NORMAL,
                                                                      CCSS._100,
                                                                      CCSS._200,
                                                                      CCSS._300,
                                                                      CCSS._400,
                                                                      CCSS._500,
                                                                      CCSS._600,
                                                                      CCSS._700,
                                                                      CCSS._800,
                                                                      CCSS._900);
  public static final ICSSProperty WORD_SPACING = new CSSPropertyNumber (ECSSProperty.WORD_SPACING, false);
  public static final ICSSProperty LETTER_SPACING = new CSSPropertyNumber (ECSSProperty.LETTER_SPACING, false);
  public static final ICSSProperty TEXT_DECORATION = new CSSPropertyEnum (ECSSProperty.TEXT_DECORATION,
                                                                          CCSS.UNDERLINE,
                                                                          CCSS.OVERLINE,
                                                                          CCSS.LINE_THROUGH,
                                                                          CCSS.BLINK,
                                                                          CCSS.NONE);
  public static final ICSSProperty TEXT_TRANSFORM = new CSSPropertyEnum (ECSSProperty.TEXT_TRANSFORM,
                                                                         CCSS.CAPITALIZE,
                                                                         CCSS.UPPERCASE,
                                                                         CCSS.LOWERCASE,
                                                                         CCSS.NONE);
  public static final ICSSProperty COLOR = new CSSPropertyColor (ECSSProperty.COLOR);

  // Orientation and paragraph control
  public static final ICSSProperty TEXT_INDENT = new CSSPropertyNumber (ECSSProperty.TEXT_INDENT, true);
  public static final ICSSProperty LINE_HEIGHT = new CSSPropertyNumber (ECSSProperty.LINE_HEIGHT, true);
  public static final ICSSProperty VERTICAL_ALIGN = new CSSPropertyEnum (ECSSProperty.VERTICAL_ALIGN,
                                                                         CCSS.TOP,
                                                                         CCSS.MIDDLE,
                                                                         CCSS.BOTTOM,
                                                                         CCSS.BASELINE,
                                                                         CCSS.SUB,
                                                                         CCSS.SUPER,
                                                                         CCSS.TEXT_TOP,
                                                                         CCSS.TEXT_BOTTOM);
  public static final ICSSProperty TEXT_ALIGN = new CSSPropertyEnum (ECSSProperty.TEXT_ALIGN,
                                                                     CCSS.LEFT,
                                                                     CCSS.CENTER,
                                                                     CCSS.RIGHT,
                                                                     CCSS.JUSTIFY);
  public static final ICSSProperty WHITE_SPACE = new CSSPropertyEnum (ECSSProperty.WHITE_SPACE,
                                                                      CCSS.NORMAL,
                                                                      CCSS.PRE,
                                                                      CCSS.NOWRAP,
                                                                      CCSS.PRE_LINE,
                                                                      CCSS.PRE_WRAP);

  // margin
  public static final ICSSProperty MARGIN_TOP = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_TOP,
                                                                             true,
                                                                             CCSS.AUTO,
                                                                             CCSS.INHERIT);
  public static final ICSSProperty MARGIN_RIGHT = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_RIGHT,
                                                                               true,
                                                                               CCSS.AUTO,
                                                                               CCSS.INHERIT);
  public static final ICSSProperty MARGIN_BOTTOM = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_BOTTOM,
                                                                                true,
                                                                                CCSS.AUTO,
                                                                                CCSS.INHERIT);
  public static final ICSSProperty MARGIN_LEFT = new CSSPropertyEnumOrNumber (ECSSProperty.MARGIN_LEFT,
                                                                              true,
                                                                              CCSS.AUTO,
                                                                              CCSS.INHERIT);
  public static final ICSSProperty MARGIN = new CSSPropertyEnumOrNumbers (ECSSProperty.MARGIN,
                                                                          true,
                                                                          1,
                                                                          4,
                                                                          CCSS.AUTO,
                                                                          CCSS.INHERIT);
  // padding
  public static final ICSSProperty PADDING_TOP = new CSSPropertyNumber (ECSSProperty.PADDING_TOP, true);
  public static final ICSSProperty PADDING_RIGHT = new CSSPropertyNumber (ECSSProperty.PADDING_RIGHT, true);
  public static final ICSSProperty PADDING_BOTTOM = new CSSPropertyNumber (ECSSProperty.PADDING_BOTTOM, true);
  public static final ICSSProperty PADDING_LEFT = new CSSPropertyNumber (ECSSProperty.PADDING_LEFT, true);
  public static final ICSSProperty PADDING = new CSSPropertyNumbers (ECSSProperty.PADDING, true, 1, 4);

  // borders
  public static final ICSSProperty BORDER = new CSSPropertyFree (ECSSProperty.BORDER);
  public static final ICSSProperty BORDER_TOP_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_TOP_WIDTH,
                                                                                   false,
                                                                                   CCSS.THIN,
                                                                                   CCSS.MEDIUM,
                                                                                   CCSS.THICK);
  public static final ICSSProperty BORDER_RIGHT_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_RIGHT_WIDTH,
                                                                                     false,
                                                                                     CCSS.THIN,
                                                                                     CCSS.MEDIUM,
                                                                                     CCSS.THICK);
  public static final ICSSProperty BORDER_BOTTOM_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_BOTTOM_WIDTH,
                                                                                      false,
                                                                                      CCSS.THIN,
                                                                                      CCSS.MEDIUM,
                                                                                      CCSS.THICK);
  public static final ICSSProperty BORDER_LEFT_WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.BORDER_LEFT_WIDTH,
                                                                                    false,
                                                                                    CCSS.THIN,
                                                                                    CCSS.MEDIUM,
                                                                                    CCSS.THICK);
  public static final ICSSProperty BORDER_WIDTH = new CSSPropertyEnumOrNumbers (ECSSProperty.BORDER_WIDTH,
                                                                                false,
                                                                                1,
                                                                                4,
                                                                                CCSS.THIN,
                                                                                CCSS.MEDIUM,
                                                                                CCSS.THICK);
  public static final ICSSProperty BORDER_TOP_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_TOP_COLOR,
                                                                                  CCSS.TRANSPARENT);
  public static final ICSSProperty BORDER_RIGHT_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_RIGHT_COLOR,
                                                                                    CCSS.TRANSPARENT);
  public static final ICSSProperty BORDER_BOTTOM_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_BOTTOM_COLOR,
                                                                                     CCSS.TRANSPARENT);
  public static final ICSSProperty BORDER_LEFT_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BORDER_LEFT_COLOR,
                                                                                   CCSS.TRANSPARENT);
  public static final ICSSProperty BORDER_COLOR = new CSSPropertyEnumOrColors (ECSSProperty.BORDER_COLOR,
                                                                               1,
                                                                               4,
                                                                               CCSS.TRANSPARENT);
  public static final ICSSProperty BORDER_TOP_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_TOP_STYLE,
                                                                           CCSS.NONE,
                                                                           CCSS.HIDDEN,
                                                                           CCSS.DOTTED,
                                                                           CCSS.DASHED,
                                                                           CCSS.SOLID,
                                                                           CCSS.DOUBLE,
                                                                           CCSS.GROOVE,
                                                                           CCSS.RIDGE,
                                                                           CCSS.INSET,
                                                                           CCSS.OUTSET);
  public static final ICSSProperty BORDER_RIGHT_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_RIGHT_STYLE,
                                                                             CCSS.NONE,
                                                                             CCSS.HIDDEN,
                                                                             CCSS.DOTTED,
                                                                             CCSS.DASHED,
                                                                             CCSS.SOLID,
                                                                             CCSS.DOUBLE,
                                                                             CCSS.GROOVE,
                                                                             CCSS.RIDGE,
                                                                             CCSS.INSET,
                                                                             CCSS.OUTSET);
  public static final ICSSProperty BORDER_BOTTOM_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_BOTTOM_STYLE,
                                                                              CCSS.NONE,
                                                                              CCSS.HIDDEN,
                                                                              CCSS.DOTTED,
                                                                              CCSS.DASHED,
                                                                              CCSS.SOLID,
                                                                              CCSS.DOUBLE,
                                                                              CCSS.GROOVE,
                                                                              CCSS.RIDGE,
                                                                              CCSS.INSET,
                                                                              CCSS.OUTSET);
  public static final ICSSProperty BORDER_LEFT_STYLE = new CSSPropertyEnum (ECSSProperty.BORDER_LEFT_STYLE,
                                                                            CCSS.NONE,
                                                                            CCSS.HIDDEN,
                                                                            CCSS.DOTTED,
                                                                            CCSS.DASHED,
                                                                            CCSS.SOLID,
                                                                            CCSS.DOUBLE,
                                                                            CCSS.GROOVE,
                                                                            CCSS.RIDGE,
                                                                            CCSS.INSET,
                                                                            CCSS.OUTSET);
  public static final ICSSProperty BORDER_STYLE = new CSSPropertyEnums (ECSSProperty.BORDER_STYLE,
                                                                        1,
                                                                        4,
                                                                        CCSS.NONE,
                                                                        CCSS.HIDDEN,
                                                                        CCSS.DOTTED,
                                                                        CCSS.DASHED,
                                                                        CCSS.SOLID,
                                                                        CCSS.DOUBLE,
                                                                        CCSS.GROOVE,
                                                                        CCSS.RIDGE,
                                                                        CCSS.INSET,
                                                                        CCSS.OUTSET);
  public static final ICSSProperty BORDER_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_RADIUS);
  public static final ICSSProperty BORDER_TOP_LEFT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_TOP_LEFT_RADIUS);
  public static final ICSSProperty BORDER_TOP_RIGHT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_TOP_RIGHT_RADIUS);
  public static final ICSSProperty BORDER_BOTTOM_LEFT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_BOTTOM_LEFT_RADIUS);
  public static final ICSSProperty BORDER_BOTTOM_RIGHT_RADIUS = new CSSPropertyFree (ECSSProperty.BORDER_BOTTOM_RIGHT_RADIUS);
  public static final ICSSProperty OUTLINE_WIDTH = new CSSPropertyEnum (ECSSProperty.OUTLINE_WIDTH,
                                                                        CCSS.THIN,
                                                                        CCSS.MEDIUM,
                                                                        CCSS.THICK);
  public static final ICSSProperty OUTLINE_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.OUTLINE_COLOR, CCSS.INVERT);
  public static final ICSSProperty OUTLINE_STYLE = new CSSPropertyEnum (ECSSProperty.OUTLINE_STYLE,
                                                                        CCSS.NONE,
                                                                        CCSS.HIDDEN,
                                                                        CCSS.DOTTED,
                                                                        CCSS.DASHED,
                                                                        CCSS.SOLID,
                                                                        CCSS.DOUBLE,
                                                                        CCSS.GROOVE,
                                                                        CCSS.RIDGE,
                                                                        CCSS.INSET,
                                                                        CCSS.OUTSET);

  // background stuff
  public static final ICSSProperty BACKGROUND_COLOR = new CSSPropertyEnumOrColor (ECSSProperty.BACKGROUND_COLOR,
                                                                                  CCSS.TRANSPARENT);
  public static final ICSSProperty BACKGROUND_IMAGE = new CSSPropertyURL (ECSSProperty.BACKGROUND_IMAGE);
  public static final ICSSProperty BACKGROUND_REPEAT = new CSSPropertyEnum (ECSSProperty.BACKGROUND_REPEAT,
                                                                            CCSS.REPEAT,
                                                                            CCSS.REPEAT_X,
                                                                            CCSS.REPEAT_Y,
                                                                            CCSS.NO_REPEAT);
  public static final ICSSProperty BACKGROUND_ATTACHMENT = new CSSPropertyEnum (ECSSProperty.BACKGROUND_ATTACHMENT,
                                                                                CCSS.SCROLL,
                                                                                CCSS.FIXED);
  public static final ICSSProperty BACKGROUND_POSITION = new CSSPropertyEnum (ECSSProperty.BACKGROUND_POSITION,
                                                                              CCSS.TOP,
                                                                              CCSS.BOTTOM,
                                                                              CCSS.CENTER,
                                                                              CCSS.LEFT,
                                                                              CCSS.RIGHT);

  // list formatting
  public static final ICSSProperty LIST_STYLE_TYPE = new CSSPropertyEnum (ECSSProperty.LIST_STYLE_TYPE,
                                                                          CCSS.DECIMAL,
                                                                          CCSS.LOWER_ROMAN,
                                                                          CCSS.UPPER_ROMAN,
                                                                          CCSS.LOWER_ALPHA,
                                                                          CCSS.UPPER_ALPHA,
                                                                          CCSS.LOWER_LATIN,
                                                                          CCSS.UPPER_LATIN,
                                                                          CCSS.DISC,
                                                                          CCSS.CIRCLE,
                                                                          CCSS.SQUARE,
                                                                          CCSS.NONE);
  public static final ICSSProperty LIST_STYLE_POSITION = new CSSPropertyEnum (ECSSProperty.LIST_STYLE_POSITION,
                                                                              CCSS.INSIDE,
                                                                              CCSS.OUTSIDE);
  public static final ICSSProperty LIST_STYLE_IMAGE = new CSSPropertyEnumOrURL (ECSSProperty.LIST_STYLE_IMAGE,
                                                                                CCSS.NONE);

  // table formatting
  public static final ICSSProperty CAPTION_SIDE = new CSSPropertyEnum (ECSSProperty.CAPTION_SIDE, CCSS.TOP, CCSS.BOTTOM);
  public static final ICSSProperty TABLE_LAYOUT = new CSSPropertyEnum (ECSSProperty.TABLE_LAYOUT, CCSS.AUTO, CCSS.FIXED);
  public static final ICSSProperty BORDER_COLLAPSE = new CSSPropertyEnum (ECSSProperty.BORDER_COLLAPSE,
                                                                          CCSS.SEPARATE,
                                                                          CCSS.COLLAPSE);
  public static final ICSSProperty BORDER_SPACING = new CSSPropertyNumber (ECSSProperty.BORDER_SPACING, true);
  public static final ICSSProperty EMPTY_CELLS = new CSSPropertyEnum (ECSSProperty.EMPTY_CELLS, CCSS.SHOW, CCSS.HIDE);
  public static final ICSSProperty SPEAK_HEADER = new CSSPropertyEnum (ECSSProperty.SPEAK_HEADER,
                                                                       CCSS.ALWAYS,
                                                                       CCSS.ONCE);

  // positioning
  public static final ICSSProperty POSITION = new CSSPropertyEnum (ECSSProperty.POSITION,
                                                                   CCSS.STATIC,
                                                                   CCSS.RELATIVE,
                                                                   CCSS.ABSOLUTE,
                                                                   CCSS.FIXED);
  public static final ICSSProperty TOP = new CSSPropertyEnumOrNumber (ECSSProperty.TOP, true, CCSS.AUTO);
  public static final ICSSProperty LEFT = new CSSPropertyEnumOrNumber (ECSSProperty.LEFT, true, CCSS.AUTO);
  public static final ICSSProperty BOTTOM = new CSSPropertyEnumOrNumber (ECSSProperty.BOTTOM, true, CCSS.AUTO);
  public static final ICSSProperty RIGHT = new CSSPropertyEnumOrNumber (ECSSProperty.RIGHT, true, CCSS.AUTO);
  public static final ICSSProperty WIDTH = new CSSPropertyEnumOrNumber (ECSSProperty.WIDTH, true, CCSS.AUTO);
  public static final ICSSProperty MIN_WIDTH = new CSSPropertyNumber (ECSSProperty.MIN_WIDTH, true);
  public static final ICSSProperty MAX_WIDTH = new CSSPropertyNumber (ECSSProperty.MAX_WIDTH, true);
  public static final ICSSProperty HEIGHT = new CSSPropertyEnumOrNumber (ECSSProperty.HEIGHT, true, CCSS.AUTO);
  public static final ICSSProperty MIN_HEIGHT = new CSSPropertyNumber (ECSSProperty.MIN_HEIGHT, true);
  public static final ICSSProperty MAX_HEIGHT = new CSSPropertyNumber (ECSSProperty.MAX_HEIGHT, true);
  public static final ICSSProperty OVERFLOW = new CSSPropertyEnum (ECSSProperty.OVERFLOW,
                                                                   CCSS.VISIBLE,
                                                                   CCSS.HIDDEN,
                                                                   CCSS.SCROLL,
                                                                   CCSS.AUTO);
  public static final ICSSProperty DIRECTION = new CSSPropertyEnum (ECSSProperty.DIRECTION, CCSS.LTR, CCSS.RTL);
  public static final ICSSProperty FLOAT = new CSSPropertyEnum (ECSSProperty.FLOAT, CCSS.LEFT, CCSS.RIGHT, CCSS.NONE);
  public static final ICSSProperty CLEAR = new CSSPropertyEnum (ECSSProperty.CLEAR,
                                                                CCSS.LEFT,
                                                                CCSS.RIGHT,
                                                                CCSS.BOTH,
                                                                CCSS.NONE);
  public static final ICSSProperty Z_INDEX = new CSSPropertyNumber (ECSSProperty.Z_INDEX, false);
  public static final ICSSProperty DISPLAY = new CSSPropertyEnum (ECSSProperty.DISPLAY,
                                                                  CCSS.BLOCK,
                                                                  CCSS.INLINE,
                                                                  CCSS.INLINE_BLOCK,
                                                                  CCSS.LIST_ITEM,
                                                                  CCSS.RUN_IN,
                                                                  CCSS.NONE,
                                                                  CCSS.TABLE,
                                                                  CCSS.INLINE_TABLE,
                                                                  CCSS.TABLE_ROW,
                                                                  CCSS.TABLE_CELL,
                                                                  CCSS.TABLE_ROW_GROUP,
                                                                  CCSS.TABLE_HEADER_GROUP,
                                                                  CCSS.TABLE_FOOTER_GROUP,
                                                                  CCSS.TABLE_COLUMN,
                                                                  CCSS.TABLE_COLUMN_GROUP,
                                                                  CCSS.TABLE_CAPTION,
                                                                  "-moz-inline-block");
  public static final ICSSProperty VISIBILITY = new CSSPropertyEnum (ECSSProperty.VISIBILITY,
                                                                     CCSS.VISIBLE,
                                                                     CCSS.HIDDEN,
                                                                     CCSS.COLLAPSE);
  public static final ICSSProperty CLIP = new CSSPropertyEnumOrRect (ECSSProperty.CLIP, CCSS.AUTO);

  // display window
  public static final ICSSProperty CURSOR = new CSSPropertyEnum (ECSSProperty.CURSOR,
                                                                 CCSS.AUTO,
                                                                 CCSS.DEFAULT,
                                                                 CCSS.CROSSHAIR,
                                                                 CCSS.POINTER,
                                                                 CCSS.MOVE,
                                                                 CCSS.N_RESIZE,
                                                                 CCSS.NE_RESIZE,
                                                                 CCSS.E_RESIZE,
                                                                 CCSS.SE_RESIZE,
                                                                 CCSS.S_RESIZE,
                                                                 CCSS.SW_RESIZE,
                                                                 CCSS.W_RESIZE,
                                                                 CCSS.NW_RESIZE,
                                                                 CCSS.TEXT,
                                                                 CCSS.WAIT,
                                                                 CCSS.HELP,
                                                                 CCSS.PROGRESS);

  public static final ICSSProperty OPACITY = new CSSPropertyNumber (ECSSProperty.OPACITY, false);

  public static final ICSSValue DISPLAY_BLOCK = DISPLAY.newValue (CCSS.BLOCK);
  public static final ICSSValue DISPLAY_NONE = DISPLAY.newValue (CCSS.NONE);

  private CCSSProperties ()
  {}
}
