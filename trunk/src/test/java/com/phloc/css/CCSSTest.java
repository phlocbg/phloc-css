package com.phloc.css;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public final class CCSSTest
{
  @Test
  public void testUnits ()
  {
    assertEquals ("5pt", CCSS.pt (5));
    assertEquals ("0pt", CCSS.pt (0));
    assertEquals ("-5pt", CCSS.pt (-5));
    assertEquals ("5pc", CCSS.pc (5));
    assertEquals ("0pc", CCSS.pc (0));
    assertEquals ("-5pc", CCSS.pc (-5));
    assertEquals ("5in", CCSS.in (5));
    assertEquals ("0in", CCSS.in (0));
    assertEquals ("-5in", CCSS.in (-5));
    assertEquals ("5mm", CCSS.mm (5));
    assertEquals ("0mm", CCSS.mm (0));
    assertEquals ("-5mm", CCSS.mm (-5));
    assertEquals ("5cm", CCSS.cm (5));
    assertEquals ("0cm", CCSS.cm (0));
    assertEquals ("-5cm", CCSS.cm (-5));
    assertEquals ("5px", CCSS.px (5));
    assertEquals ("0px", CCSS.px (0));
    assertEquals ("-5px", CCSS.px (-5));
    assertEquals ("5em", CCSS.em (5));
    assertEquals ("0em", CCSS.em (0));
    assertEquals ("-5em", CCSS.em (-5));
    assertEquals ("5em", CCSS.em (5.0));
    assertEquals ("0em", CCSS.em (0.0));
    assertEquals ("-5em", CCSS.em (-5.0));
    assertEquals ("5ex", CCSS.ex (5));
    assertEquals ("0ex", CCSS.ex (0));
    assertEquals ("-5ex", CCSS.ex (-5));
    assertEquals ("5%", CCSS.perc (5));
    assertEquals ("0%", CCSS.perc (0));
    assertEquals ("-5%", CCSS.perc (-5));
  }

  @Test
  public void testColorRGB ()
  {
    assertEquals ("rgb(0,0,0)", CCSS.colorRGB (0, 0, 0));
    assertEquals ("rgb(13,123,145)", CCSS.colorRGB (13, 123, 145));
    try
    {
      // illegal red
      CCSS.colorRGB (-1, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal red
      CCSS.colorRGB (256, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CCSS.colorRGB (0, -1, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CCSS.colorRGB (0, 256, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CCSS.colorRGB (0, 0, -1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CCSS.colorRGB (0, 0, 256);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testColorHex ()
  {
    assertEquals ("#000000", CCSS.colorHex (0, 0, 0));
    assertEquals ("#0f80ff", CCSS.colorHex (15, 128, 255));
    try
    {
      // illegal red
      CCSS.colorHex (-1, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal red
      CCSS.colorHex (256, 0, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CCSS.colorHex (0, -1, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal green
      CCSS.colorHex (0, 256, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CCSS.colorHex (0, 0, -1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // illegal blue
      CCSS.colorHex (0, 0, 256);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testGetURLValue ()
  {
    assertEquals ("a.gif", CCSS.getURLValue ("url(a.gif)"));
    assertNull (CCSS.getURLValue ("url(a.gif"));
  }

  @Test
  public void testIsNumberWithUnitValue ()
  {
    assertTrue (CCSS.isNumberWithUnitValue ("50%", true));
    assertTrue (CCSS.isNumberWithUnitValue ("50mm", true));
    assertTrue (CCSS.isNumberWithUnitValue ("50cm", true));
    assertTrue (CCSS.isNumberWithUnitValue ("50ex", true));
    assertTrue (CCSS.isNumberWithUnitValue (" 50ex ", true));
    assertTrue (CCSS.isNumberWithUnitValue (" 50ex ", false));
    assertTrue (CCSS.isNumberWithUnitValue (" 50 ex ", false));
    assertTrue (CCSS.isNumberWithUnitValue (" 50 s ", false));
    assertTrue (CCSS.isNumberWithUnitValue (" 50 ms ", false));

    assertFalse (CCSS.isNumberWithUnitValue (" 50 xs ", false));
    assertFalse (CCSS.isNumberWithUnitValue ("50%", false));
    assertFalse (CCSS.isNumberWithUnitValue ("", false));
    assertFalse (CCSS.isNumberWithUnitValue ("", true));
    assertFalse (CCSS.isNumberWithUnitValue (" ", false));
    assertFalse (CCSS.isNumberWithUnitValue (" ", true));
    assertFalse (CCSS.isNumberWithUnitValue (" mm ", true));
    assertFalse (CCSS.isNumberWithUnitValue (" % ", true));
  }
}
