package com.phloc.css.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.io.file.filter.FileFilterFileFromFilenameFilter;
import com.phloc.commons.io.file.filter.FilenameFilterFactory;
import com.phloc.commons.io.file.iterate.FileSystemRecursiveIterator;
import com.phloc.commons.io.streams.StreamUtils;

public final class FuncTestParserCSS21Iterate
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestParserCSS21Iterate.class);

  @Test
  public void scanTestResources () throws IOException
  {
    int nErrors = 0;
    for (final File aFile : FileSystemRecursiveIterator.create (new File ("src/test/resources/css"),
                                                                new FileFilterFileFromFilenameFilter (FilenameFilterFactory.getEndsWithFilter (".css"))))
    {
      s_aLogger.info (aFile.getAbsolutePath ());
      final InputStream aIS = new FileInputStream (aFile);
      try
      {
        final ParserCSS21TokenManager aTokenHdl = new ParserCSS21TokenManager (new JavaCharStream (aIS));
        aTokenHdl.setDebugStream (System.out);
        final ParserCSS21 aParser = new ParserCSS21 (aTokenHdl);
        aParser.disable_tracing ();
        final CSSNode aNode = aParser.styleSheet ();
        assertNotNull (aNode);
      }
      catch (final ParseException ex)
      {
        s_aLogger.error ("Error parsing " + aFile.getAbsolutePath () + ": " + ex.getMessage ());
        nErrors++;
      }
      finally
      {
        StreamUtils.close (aIS);
      }
    }
    assertEquals ("Found " + nErrors + " errors", 0, nErrors);
  }
}
