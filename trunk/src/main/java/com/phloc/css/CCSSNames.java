package com.phloc.css;

import javax.annotation.concurrent.Immutable;

/**
 * This class contains constants for CSS values that occur recurrently but are
 * not part of the CSS specification.
 * 
 * @author philip
 */
@Immutable
public final class CCSSNames
{
  public static final String FONT_ARIAL = "Arial";
  public static final String FONT_COURIER_NEW = "Courier New";
  public static final String FONT_HELVETICA = "Helvetica";
  public static final String FONT_TAHOMA = "Tahoma";
  public static final String FONT_VERDANA = "Verdana";

  public static final String FONT_MONOSPACE = FONT_COURIER_NEW;

  private CCSSNames ()
  {}
}
