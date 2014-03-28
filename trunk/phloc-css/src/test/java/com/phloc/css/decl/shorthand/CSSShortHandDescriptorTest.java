package com.phloc.css.decl.shorthand;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.property.ECSSProperty;
import com.phloc.css.reader.CSSReaderDeclarationList;
import com.phloc.css.writer.CSSWriterSettings;

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
    assertTrue (CSSShortHandRegistry.isShortHandProperty (ECSSProperty.MARGIN));
    assertFalse (CSSShortHandRegistry.isShortHandProperty (ECSSProperty.MARGIN_TOP));
  }

  @Test
  public void testBorder ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.BORDER);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("border:dashed 1px red", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);

    final CSSWriterSettings aCWS = new CSSWriterSettings (ECSSVersion.CSS30, false);
    System.out.println (aDecl.getAsCSSString (aCWS, 0));
    System.out.println ("---");
    for (final CSSDeclaration aSplitted : aSplittedDecls)
      System.out.println (aSplitted.getAsCSSString (aCWS, 0));
  }
}
