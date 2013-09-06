package com.phloc.css.supplementary.main;

import com.phloc.css.property.CCSSProperties;
import com.phloc.css.property.ECSSProperty;

public class MainAddMissingProperties
{
  public static void main (final String [] args) throws Exception
  {
    final Class <?> aCClass = CCSSProperties.class;
    for (final ECSSProperty e : ECSSProperty.values ())
      if (!e.isBrowserSpecific ())
      {
        boolean bHasField = false;
        try
        {
          bHasField = aCClass.getField (e.name ()) != null;
        }
        catch (final Exception ex)
        {}
        if (!bHasField)
        {
          if (e.getClass ().getField (e.name ()).getAnnotation (Deprecated.class) != null)
            System.out.println ("@Deprecated");
          System.out.println ("public static final ICSSProperty " +
                              e.name () +
                              " = new CSSPropertyFree (ECSSProperty." +
                              e.name () +
                              ");");
        }
      }
  }
}
