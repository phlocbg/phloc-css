package com.phloc.css;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.css.property.CCSSProperties;

/**
 * Test class for class {@link CSSValue}.
 * 
 * @author philip
 */
public final class CSSValueTest
{
  @Test
  public void testBasic ()
  {
    final ICSSValue v1 = CCSSProperties.DISPLAY.newValue (CCSS.BLOCK);
    final ICSSValue v21 = CCSSProperties.DISPLAY.newValue (CCSS.BLOCK);
    final ICSSValue v22 = CCSSProperties.DISPLAY.newValue (CCSS.INLINE);
    final ICSSValue v3 = CCSSProperties.DISPLAY.newValue (CCSS.INLINE_BLOCK);
    final ICSSValue v4 = CCSSProperties.OPACITY.newValue (Double.toString (0.39));
    assertNotNull (v1);
    assertNotNull (v21);
    assertNotNull (v22);
    assertTrue (v1 instanceof CSSValue);
    assertTrue (v3 instanceof CSSValueMultiValue);
    assertTrue (v4 instanceof CSSValueList);
    assertEquals (v1, v1);
    assertEquals (v1, v21);
    assertEquals (v21, v1);
    assertFalse (v1.equals (v22));
    assertFalse (v22.equals (v1));
    assertEquals (v1.hashCode (), v21.hashCode ());
  }
}
