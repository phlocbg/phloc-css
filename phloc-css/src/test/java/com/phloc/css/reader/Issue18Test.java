package com.phloc.css.reader;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.io.IReadableResource;
import com.phloc.commons.io.resource.ClassPathResource;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.errorhandler.LoggingCSSParseErrorHandler;
import com.phloc.css.writer.CSSWriter;

/**
 * Test for issue 18: http://code.google.com/p/phloc-css/issues/detail?id=18
 * 
 * @author Philip Helger
 */
public final class Issue18Test
{
  @Test
  public void testIssue18 ()
  {
    final IReadableResource aRes = new ClassPathResource ("testfiles/css30/good/issue18.css");
    final CascadingStyleSheet aCSS = CSSReader.readFromStream (aRes,
                                                               CCharset.CHARSET_UTF_8_OBJ,
                                                               ECSSVersion.CSS30,
                                                               new LoggingCSSParseErrorHandler ());
    assertNotNull (aCSS);
    if (false)
      System.out.println (new CSSWriter (ECSSVersion.CSS30).getCSSAsString (aCSS));
  }
}
