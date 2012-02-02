package com.phloc.css.decl.visit;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.file.filter.FileFilterFileFromFilenameFilter;
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSExpressionMemberTermSimple;
import com.phloc.css.decl.CSSImportRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.decl.ICSSTopLevelRule;
import com.phloc.css.handler.CSSHandler;

/**
 * Test class for class {@link CSSVisitor}.
 * 
 * @author philip
 */
public final class CSSVisitorTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (CSSVisitorTest.class);

  private static final class SysOutVisitor extends DefaultCSSUrlVisitor
  {
    @Override
    public void onImport (final CSSImportRule aImportRule)
    {
      s_aLogger.info ("import: " + aImportRule.getLocation ());
    }

    @Override
    public void onUrlDeclaration (final ICSSTopLevelRule aTopLevelRule,
                                  final CSSDeclaration aDeclaration,
                                  final CSSExpressionMemberTermSimple aExprTerm)
    {
      s_aLogger.info ("Expression: " + aExprTerm.getValue ());
    }
  }

  @Test
  public void testVisitContent ()
  {
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css"),
                                                                new FileFilterFileFromFilenameFilter (FilenameFilterFactory.getEndsWithFilter (".css"))))
    {
      s_aLogger.info (aFile.getAbsolutePath ());
      final CascadingStyleSheet aCSS = CSSHandler.readFromStream (new FileSystemResource (aFile), ECSSVersion.CSS21);
      CSSVisitor.visitCSSUrl (aCSS, new SysOutVisitor ());
    }
  }
}
