package com.phloc.css.decl.shorthand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.phloc.commons.ValueEnforcer;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.css.ECSSUnit;
import com.phloc.css.property.CCSSProperties;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.propertyvalue.CCSSValue;
import com.phloc.css.utils.ECSSColor;

/**
 * A static registry for all CSS short hand declarations (like
 * <code>border</code> or <code>margin</code>).
 *
 * @author Philip Helger
 * @since 3.7.4
 */
@ThreadSafe
public final class CSSShortHandRegistry
{
  private static final ReadWriteLock s_aRWLock = new ReentrantReadWriteLock ();
  @GuardedBy ("s_aRWLock")
  private static final Map <ECSSProperty, CSSShortHandDescriptor> s_aMap = new HashMap <ECSSProperty, CSSShortHandDescriptor> ();

  static
  {
    // Register default short hands
    registerShortHandDescriptor (new CSSShortHandDescriptor (ECSSProperty.BACKGROUND,
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_COLOR,
                                                                                              CCSSValue.TRANSPARENT),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_IMAGE,
                                                                                              CCSSValue.NONE),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_REPEAT,
                                                                                              CCSSValue.REPEAT),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_POSITION,
                                                                                              "top left"),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BACKGROUND_ATTACHMENT,
                                                                                              CCSSValue.SCROLL)));
    registerShortHandDescriptor (new CSSShortHandDescriptor (ECSSProperty.FONT,
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
                                                                                              CCSSValue.INHERIT)));
    registerShortHandDescriptor (new CSSShortHandDescriptor (ECSSProperty.BORDER,
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BORDER_WIDTH,
                                                                                              ECSSUnit.px (3)),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BORDER_STYLE,
                                                                                              CCSSValue.SOLID),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.BORDER_COLOR,
                                                                                              ECSSColor.BLACK.getName ())));
    registerShortHandDescriptor (new CSSShortHandDescriptorMarginPadding (ECSSProperty.MARGIN,
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_TOP,
                                                                                                           CCSSValue.AUTO),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_RIGHT,
                                                                                                           CCSSValue.AUTO),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_BOTTOM,
                                                                                                           CCSSValue.AUTO),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.MARGIN_LEFT,
                                                                                                           CCSSValue.AUTO)));
    registerShortHandDescriptor (new CSSShortHandDescriptorMarginPadding (ECSSProperty.PADDING,
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_TOP,
                                                                                                           CCSSValue.AUTO),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_RIGHT,
                                                                                                           CCSSValue.AUTO),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_BOTTOM,
                                                                                                           CCSSValue.AUTO),
                                                                          new CSSPropertyWithDefaultValue (CCSSProperties.PADDING_LEFT,
                                                                                                           CCSSValue.AUTO)));
    registerShortHandDescriptor (new CSSShortHandDescriptor (ECSSProperty.OUTLINE,
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.OUTLINE_WIDTH,
                                                                                              ECSSUnit.px (3)),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.OUTLINE_STYLE,
                                                                                              CCSSValue.SOLID),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.OUTLINE_COLOR,
                                                                                              ECSSColor.BLACK.getName ())));
    registerShortHandDescriptor (new CSSShortHandDescriptor (ECSSProperty.LIST_STYLE,
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.LIST_STYLE_TYPE,
                                                                                              CCSSValue.DISC),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.LIST_STYLE_POSITION,
                                                                                              CCSSValue.OUTSIDE),
                                                             new CSSPropertyWithDefaultValue (CCSSProperties.LIST_STYLE_IMAGE,
                                                                                              CCSSValue.NONE)));
  }

  private CSSShortHandRegistry ()
  {}

  public static void registerShortHandDescriptor (@Nonnull final CSSShortHandDescriptor aDescriptor)
  {
    ValueEnforcer.notNull (aDescriptor, "Descriptor");

    final ECSSProperty eProperty = aDescriptor.getProperty ();
    s_aRWLock.writeLock ().lock ();
    try
    {
      if (s_aMap.containsKey (eProperty))
        throw new IllegalStateException ("A short hand for property '" +
                                         eProperty.getName () +
                                         "' is already registered!");
      s_aMap.put (eProperty, aDescriptor);
    }
    finally
    {
      s_aRWLock.writeLock ().unlock ();
    }
  }

  @Nonnull
  @ReturnsMutableCopy
  public static Set <ECSSProperty> getAllShortHandProperties ()
  {
    s_aRWLock.readLock ().lock ();
    try
    {
      return ContainerHelper.newSet (s_aMap.keySet ());
    }
    finally
    {
      s_aRWLock.readLock ().unlock ();
    }
  }

  public static boolean isShortHandProperty (@Nullable final ECSSProperty eProperty)
  {
    if (eProperty == null)
      return false;

    s_aRWLock.readLock ().lock ();
    try
    {
      return s_aMap.containsKey (eProperty);
    }
    finally
    {
      s_aRWLock.readLock ().unlock ();
    }
  }

  @Nullable
  public static CSSShortHandDescriptor getShortHandDescriptor (@Nullable final ECSSProperty eProperty)
  {
    if (eProperty == null)
      return null;

    s_aRWLock.readLock ().lock ();
    try
    {
      return s_aMap.get (eProperty);
    }
    finally
    {
      s_aRWLock.readLock ().unlock ();
    }
  }
}
