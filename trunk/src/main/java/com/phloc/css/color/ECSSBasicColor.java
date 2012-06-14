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
package com.phloc.css.color;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.name.IHasName;

/**
 * CSS basic colors. Source: http://www.w3.org/TR/css3-color/ chapter 4.1
 * 
 * @author philip
 */
public enum ECSSBasicColor implements IHasName
{
  BLACK ("black", 0, 0, 0),
  SILVER ("silver ", 192, 192, 192),
  GRAY ("gray ", 128, 128, 128),
  WHITE ("white", 255, 255, 255),
  MAROON ("maroon ", 128, 0, 0),
  RED ("red", 255, 0, 0),
  PURPLE ("purple ", 128, 0, 128),
  FUCHSIA ("fuchsia", 255, 0, 255),
  GREEN ("green", 0, 128, 0),
  LIME ("lime ", 0, 255, 0),
  OLIVE ("olive", 128, 128, 0),
  YELLOW ("yellow ", 255, 255, 0),
  NAVY ("navy ", 0, 0, 128),
  BLUE ("blue ", 0, 0, 255),
  TEAL ("teal ", 0, 128, 128),
  AQUA ("aqua ", 0, 255, 255);

  private final String m_sName;
  private final int m_nRed;
  private final int m_nGreen;
  private final int m_nBlue;

  private ECSSBasicColor (@Nonnull @Nonempty final String sName,
                          @Nonnegative final int nRed,
                          @Nonnegative final int nGreen,
                          @Nonnegative final int nBlue)
  {
    m_sName = sName;
    m_nRed = nRed;
    m_nGreen = nGreen;
    m_nBlue = nBlue;
  }

  @Nonnull
  public String getName ()
  {
    return m_sName;
  }

  @Nonnegative
  public int getRed ()
  {
    return m_nRed;
  }

  @Nonnegative
  public int getGreen ()
  {
    return m_nGreen;
  }

  @Nonnegative
  public int getBlue ()
  {
    return m_nBlue;
  }

  @Nonnull
  @Nonempty
  public String getAsRGBColorValue ()
  {
    return CSSColorHelper.getRGBColorValue (m_nRed, m_nGreen, m_nBlue);
  }

  @Nonnull
  @Nonempty
  public String getAsRGBAColorValue (@Nonnegative final double dOpacity)
  {
    return CSSColorHelper.getRGBAColorValue (m_nRed, m_nGreen, m_nBlue, dOpacity);
  }

  @Nonnull
  @Nonempty
  public String getAsHexColorValue ()
  {
    return CSSColorHelper.getHexColorValue (m_nRed, m_nGreen, m_nBlue);
  }
}
