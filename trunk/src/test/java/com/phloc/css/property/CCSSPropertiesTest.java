package com.phloc.css.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class CCSSPropertiesTest
{
  @Test
  public void test ()
  {
    assertTrue (CCSSProperties.BORDER_LEFT_STYLE.isValidValue ("none"));
    assertFalse (CCSSProperties.BORDER_LEFT_STYLE.isValidValue ("any"));
    assertTrue (CCSSProperties.BORDER_STYLE.isValidValue ("solid none"));
    assertTrue (CCSSProperties.BORDER_STYLE.isValidValue ("      solid           none       "));
    assertFalse (CCSSProperties.BORDER_STYLE.isValidValue ("      solid           any       "));
    assertTrue (CCSSProperties.BORDER_STYLE.isValidValue ("solid none none none"));
    assertFalse (CCSSProperties.BORDER_STYLE.isValidValue ("solid none none none solid"));
    assertFalse (CCSSProperties.BORDER_STYLE.isValidValue (""));
    assertTrue (CCSSProperties.COLOR.isValidValue ("rgb(0,0,0)"));
    assertTrue (CCSSProperties.COLOR.isValidValue ("rgb ( 0% , 0% , 50% )"));
    assertTrue (CCSSProperties.COLOR.isValidValue ("rgb(0%,0%,50%)"));
    assertTrue (CCSSProperties.COLOR.isValidValue ("#ffffff"));
    assertTrue (CCSSProperties.COLOR.isValidValue (" #ffffff "));
    assertTrue (CCSSProperties.COLOR.isValidValue ("#fff"));
    assertTrue (CCSSProperties.COLOR.isValidValue (" #fff   "));
    assertFalse (CCSSProperties.COLOR.isValidValue ("#fffffff"));
    assertTrue (CCSSProperties.CLIP.isValidValue ("auto"));
    assertTrue (CCSSProperties.CLIP.isValidValue ("rect(5px,10in,33px,456em)"));
  }
}
