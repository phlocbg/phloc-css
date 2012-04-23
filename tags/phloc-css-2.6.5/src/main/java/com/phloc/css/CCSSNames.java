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
