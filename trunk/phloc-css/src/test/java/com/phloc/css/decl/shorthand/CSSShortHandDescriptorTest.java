package com.phloc.css.decl.shorthand;

import static org.junit.Assert.assertEquals;
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
  private static final CSSWriterSettings CWS = new CSSWriterSettings (ECSSVersion.CSS30, false);

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
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (3, aSplittedDecls.size ());
    assertEquals ("border-style:dashed", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("border-width:1px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("border-color:red", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
  }

  @Test
  public void testMargin1 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.MARGIN);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("margin:1px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("margin-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("margin-right:1px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("margin-bottom:1px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("margin-left:1px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testMargin2 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.MARGIN);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("margin:1px 3px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("margin-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("margin-right:3px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("margin-bottom:1px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("margin-left:3px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testMargin3 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.MARGIN);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("margin:1px 3px 5px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("margin-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("margin-right:3px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("margin-bottom:5px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("margin-left:3px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testMargin4 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.MARGIN);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("margin:1px 3px 5px 7px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("margin-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("margin-right:3px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("margin-bottom:5px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("margin-left:7px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testPadding1 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.PADDING);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("padding:1px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("padding-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("padding-right:1px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("padding-bottom:1px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("padding-left:1px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testPadding2 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.PADDING);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("padding:1px 3px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("padding-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("padding-right:3px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("padding-bottom:1px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("padding-left:3px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testPadding3 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.PADDING);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("padding:1px 3px 5px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("padding-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("padding-right:3px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("padding-bottom:5px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("padding-left:3px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }

  @Test
  public void testPadding4 ()
  {
    final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.PADDING);
    assertNotNull (aSHD);

    final CSSDeclaration aDecl = CSSReaderDeclarationList.readFromString ("padding:1px 3px 5px 7px", ECSSVersion.CSS30)
                                                         .getDeclarationAtIndex (0);
    assertNotNull (aDecl);

    final List <CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (aDecl);
    assertNotNull (aSplittedDecls);
    assertEquals (4, aSplittedDecls.size ());

    assertEquals ("padding-top:1px", aSplittedDecls.get (0).getAsCSSString (CWS, 0));
    assertEquals ("padding-right:3px", aSplittedDecls.get (1).getAsCSSString (CWS, 0));
    assertEquals ("padding-bottom:5px", aSplittedDecls.get (2).getAsCSSString (CWS, 0));
    assertEquals ("padding-left:7px", aSplittedDecls.get (3).getAsCSSString (CWS, 0));
  }
}
