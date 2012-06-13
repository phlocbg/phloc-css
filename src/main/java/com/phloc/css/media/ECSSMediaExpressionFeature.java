package com.phloc.css.media;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.name.IHasName;
import com.phloc.commons.string.StringHelper;

public enum ECSSMediaExpressionFeature implements IHasName
{
  WIDTH ("width"),
  MIN_WIDTH ("min-width"),
  MAX_WIDTH ("max-width"),
  HEIGHT ("height"),
  MIN_HEIGHT ("min-height"),
  MAX_HEIGHT ("max-height"),
  DEVICE_WIDTH ("device-width"),
  MIN_DEVICE_WIDTH ("min-device-width"),
  MAX_DEVICE_WIDTH ("max-device-width"),
  DEVICE_HEIGHT ("device-height"),
  MIN_DEVICE_HEIGHT ("min-device-height"),
  MAX_DEVICE_HEIGHT ("max-device-height"),
  ASPECT_RATIO ("aspect-ratio"),
  MIN_ASPECT_RATIO ("min-aspect-ratio"),
  MAX_ASPECT_RATIO ("max-aspect-ratio"),
  DEVICE_ASPECT_RATIO ("device-aspect-ratio"),
  MIN_DEVICE_ASPECT_RATIO ("min-device-aspect-ratio"),
  MAX_DEVICE_ASPECT_RATIO ("max-device-aspect-ratio"),
  COLOR ("color"),
  MIN_COLOR ("min-color"),
  MAX_COLOR ("max-color"),
  COLOR_INDEX ("color-index"),
  MIN_COLOR_INDEX ("min-color-index"),
  MAX_COLOR_INDEX ("max-color-index"),
  MONOCHROME ("monochrome"),
  MIN_MONOCHROME ("min-monochrome"),
  MAX_MONOCHROME ("max-monochrome"),
  RESOLUTION ("resolution"),
  MIN_RESOLUTION ("min-resolution"),
  MAX_RESOLUTION ("max-resolution"),
  SCAN ("scan"),
  GRID ("grid");

  private final String m_sName;

  private ECSSMediaExpressionFeature (@Nonnull @Nonempty final String sName)
  {
    m_sName = sName;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nullable
  public static ECSSMediaExpressionFeature getFromNameOrNull (@Nullable final String sName)
  {
    if (StringHelper.hasText (sName))
      for (final ECSSMediaExpressionFeature eProperty : values ())
        if (eProperty.m_sName.equals (sName))
          return eProperty;
    return null;
  }

}
