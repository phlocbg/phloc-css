package com.phloc.css.decl.shorthand;

import org.junit.Test;

import com.phloc.css.ECSSUnit;
import com.phloc.css.property.CCSSProperties;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.utils.ECSSColor;

/**
 * Test class for class {@link CSSShortHandDescriptor}.
 * 
 * @author Philip Helger
 */
public class CSSShortHandDescriptorTest
{
  @Test
  public void testBasic ()
  {
    final CSSShortHandDescriptor aBackground = new CSSShortHandDescriptor (ECSSProperty.BACKGROUND,
                                                                           new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_COLOR,
                                                                                                            CCSSValue.TRANSPARENT),
                                                                           new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_IMAGE,
                                                                                                            CCSSValue.NONE),
                                                                           new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_REPEAT,
                                                                                                            CCSSValue.REPEAT),
                                                                           new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_POSITION,
                                                                                                            "top left"),
                                                                           new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_ATTACHMENT,
                                                                                                            CCSSValue.SCROLL));
    final CSSShortHandDescriptor aFont = new CSSShortHandDescriptor (ECSSProperty.FONT,
                                                                     new CSSPropertyWithDefaultValue (CCSSProperties.FONT_STYLE,
                                                                                                      CCSSValue.NORMAL),
                                                                     new CSSPropertyWithDefaultValue (CCSSProperties.FONT_VARIANT,
                                                                                                      CCSSValue.NORMAL),
                                                                     new CSSPropertyWithDefaultValue (CCSSProperties.FONT_WEIGHT,
                                                                                                      CCSSValue.NORMAL),
                                                                     new CSSPropertyWithDefaultValue (CCSSProperties.FONT_SIZE,
                                                                                                      CCSSValue.INHERIT),
                                                                     new CSSPropertyWithDefaultValue (CCSSProperties.LINE_HEIGHT,
                                                                                                      CCSSValue.NORMAL),
                                                                     new CSSPropertyWithDefaultValue (CCSSProperties.FONT_FAMILY,
                                                                                                      CCSSValue.INHERIT));
    final CSSShortHandDescriptor aBorder = new CSSShortHandDescriptor (ECSSProperty.BORDER,
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.BORDER_WIDTH,
                                                                                                        ECSSUnit.px (3)),
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.BORDER_STYLE,
                                                                                                        CCSSValue.SOLID),
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.BORDER_COLOR,
                                                                                                        ECSSColor.BLACK.getName ()));
    final CSSShortHandDescriptor aMargin = new CSSShortHandDescriptor (ECSSProperty.MARGIN,
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_TOP,
                                                                                                        CCSSValue.AUTO),
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_RIGHT,
                                                                                                        CCSSValue.AUTO),
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_BOTTOM,
                                                                                                        CCSSValue.AUTO),
                                                                       new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_LEFT,
                                                                                                        CCSSValue.AUTO));
    final CSSShortHandDescriptor aPadding = new CSSShortHandDescriptor (ECSSProperty.PADDING,
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_TOP,
                                                                                                         CCSSValue.AUTO),
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_RIGHT,
                                                                                                         CCSSValue.AUTO),
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_BOTTOM,
                                                                                                         CCSSValue.AUTO),
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_LEFT,
                                                                                                         CCSSValue.AUTO));
    final CSSShortHandDescriptor aOutline = new CSSShortHandDescriptor (ECSSProperty.OUTLINE,
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.OUTLINE_WIDTH,
                                                                                                         ECSSUnit.px (3)),
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.OUTLINE_STYLE,
                                                                                                         CCSSValue.SOLID),
                                                                        new CSSPropertyWithDefaultValue (CCSSProperties.OUTLINE_COLOR,
                                                                                                         ECSSColor.BLACK.getName ()));
    final CSSShortHandDescriptor aListStyle = new CSSShortHandDescriptor (ECSSProperty.LIST_STYLE,
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.LIST_STYLE_TYPE,
                                                                                                           CCSSValue.DISC),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.LIST_STYLE_POSITION,
                                                                                                           CCSSValue.OUTSIDE),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.LIST_STYLE_IMAGE,
                                                                                                           CCSSValue.NONE));
  }
}
