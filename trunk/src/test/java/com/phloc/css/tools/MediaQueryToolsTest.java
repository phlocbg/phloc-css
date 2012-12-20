package com.phloc.css.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

import com.phloc.commons.charset.CCharset;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CSSMediaQuery;

/**
 * Test class for class {@link MediaQueryTools}.
 * 
 * @author philip
 */
public final class MediaQueryToolsTest
{
  @Test
  public void testParseMediaQuery ()
  {
    final Charset aCharset = CCharset.CHARSET_UTF_8_OBJ;
    final ECSSVersion eVersion = ECSSVersion.CSS30;

    List <CSSMediaQuery> aMQs = MediaQueryTools.parseToMediaQuery ("screen", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("screen and (color)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("not screen and (color)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("only screen and (color)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("screen and (color), projection and (color)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (2, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("aural and (device-aspect-ratio: 16/9)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("speech and (min-device-width: 800px)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("screen and (max-weight: 3kg) and (color), (color)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (2, aMQs.size ());

    aMQs = MediaQueryTools.parseToMediaQuery ("print and (min-width: 25cm)", aCharset, eVersion);
    assertNotNull (aMQs);
    assertEquals (1, aMQs.size ());
  }
}
