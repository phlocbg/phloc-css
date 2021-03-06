/**
 * Copyright (C) 2006-2015 phloc systems
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
package com.phloc.css.supplementary.main;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.io.file.SimpleFileIO;
import com.phloc.commons.lang.CGStringHelper;
import com.phloc.commons.microdom.IMicroElement;
import com.phloc.commons.microdom.impl.MicroElement;
import com.phloc.commons.microdom.serialize.MicroWriter;
import com.phloc.commons.name.ComparatorHasName;
import com.phloc.commons.version.Version;
import com.phloc.commons.xml.serialize.EXMLSerializeIndent;
import com.phloc.commons.xml.serialize.XMLWriterSettings;
import com.phloc.css.ECSSSpecification;
import com.phloc.css.ECSSVersion;
import com.phloc.css.property.ECSSProperty;

public class MainCreateSupportedCSSPropertiesFile
{
  private static void _boolean (final IMicroElement td, final boolean bSet)
  {
    if (bSet)
      td.setAttribute ("class", "center").appendText ("X");
    else
      td.appendText ("");
  }

  public static void main (final String [] args)
  {
    final Locale aLocale = Locale.US;

    final IMicroElement html = new MicroElement ("html");
    final IMicroElement head = html.appendElement ("head");
    head.appendElement ("title").appendText ("Supported CSS properties in phloc-css");
    head.appendElement ("style")
        .appendText ("* {font-family:Arial,Helvetica;} table{border-collapse:collapse;} td,th { border:solid 1px black; } .odd{background-color:#ddd;} .center{text-align:center;} a, a:link, a:visited, a:hover, a:active{color:blue;}");

    final IMicroElement body = html.appendElement ("body");

    body.appendElement ("div")
        .appendText ("Automatically generated by " +
                     CGStringHelper.getClassLocalName (MainCreateSupportedCSSPropertiesFile.class) +
                     " on " +
                     new Date ().toString ());

    body.appendElement ("h1").appendText ("Generic properties");
    IMicroElement table = body.appendElement ("table");
    IMicroElement thead = table.appendElement ("thead");
    IMicroElement tr = thead.appendElement ("tr");
    tr.appendElement ("th").appendText ("Name");
    tr.appendElement ("th").appendText ("CSS10");
    tr.appendElement ("th").appendText ("CSS21");
    tr.appendElement ("th").appendText ("CSS30");
    tr.appendElement ("th").appendText ("Links");

    IMicroElement tbody = table.appendElement ("tbody");
    int nIndex = 0;
    for (final ECSSProperty eProperty : ContainerHelper.getSorted (ECSSProperty.values (),
                                                                   new ComparatorHasName <ECSSProperty> (aLocale)))
      if (!eProperty.isBrowserSpecific ())
      {
        final Version eMinVersion = eProperty.getMinimumCSSVersion ().getVersion ();
        final boolean bCSS10 = eMinVersion.isLowerOrEqualThan (ECSSVersion.CSS10.getVersion ());
        final boolean bCSS21 = eMinVersion.isLowerOrEqualThan (ECSSVersion.CSS21.getVersion ());
        final boolean bCSS30 = eMinVersion.isLowerOrEqualThan (ECSSVersion.CSS30.getVersion ());

        tr = tbody.appendElement ("tr");
        if ((nIndex & 1) == 1)
          tr.setAttribute ("class", "odd");
        tr.appendElement ("td").appendText (eProperty.getName ());
        _boolean (tr.appendElement ("td"), bCSS10);
        _boolean (tr.appendElement ("td"), bCSS21);
        _boolean (tr.appendElement ("td"), bCSS30);

        final IMicroElement td = tr.appendElement ("td");
        for (final ECSSSpecification eSpecs : eProperty.getAllSpecifications ())
          if (eSpecs.hasHandledURL ())
            td.appendElement ("div")
              .appendElement ("a")
              .setAttribute ("href", eSpecs.getHandledURL ())
              .setAttribute ("target", "_blank")
              .appendText (eSpecs.getID ());
          else
            td.appendElement ("div").appendText (eSpecs.getID ());

        ++nIndex;
      }

    body.appendElement ("h1").appendText ("Browser specific properties");
    table = body.appendElement ("table");
    thead = table.appendElement ("thead");
    tr = thead.appendElement ("tr");
    tr.appendElement ("th").appendText ("Name");
    tr.appendElement ("th").appendText ("KHTML");
    tr.appendElement ("th").appendText ("Microsoft");
    tr.appendElement ("th").appendText ("Mozilla");
    tr.appendElement ("th").appendText ("Opera");
    tr.appendElement ("th").appendText ("EPub");
    tr.appendElement ("th").appendText ("Webkit");

    tbody = table.appendElement ("tbody");
    nIndex = 0;
    for (final ECSSProperty eProperty : ContainerHelper.getSorted (ECSSProperty.values (),
                                                                   new ComparatorHasName <ECSSProperty> (aLocale)))
      if (eProperty.isBrowserSpecific ())
      {
        final boolean bKHTML = eProperty.isKHTMLSpecific ();
        final boolean bMS = eProperty.isMicrosoftSpecific ();
        final boolean bMoz = eProperty.isMozillaSpecific ();
        final boolean bOpera = eProperty.isOperaSpecific ();
        final boolean bEPub = eProperty.isEPubSpecific ();
        final boolean bWebkit = eProperty.isWebkitSpecific ();

        tr = tbody.appendElement ("tr");
        if ((nIndex & 1) == 1)
          tr.setAttribute ("class", "odd");
        tr.appendElement ("td").appendText (eProperty.getName ());
        _boolean (tr.appendElement ("td"), bKHTML);
        _boolean (tr.appendElement ("td"), bMS);
        _boolean (tr.appendElement ("td"), bMoz);
        _boolean (tr.appendElement ("td"), bOpera);
        _boolean (tr.appendElement ("td"), bEPub);
        _boolean (tr.appendElement ("td"), bWebkit);

        ++nIndex;
      }

    String sHTML = "<!--\r\n"
                   + "\r\n"
                   + "    Copyright (C) 2006-2013 phloc systems\r\n"
                   + "    http://www.phloc.com\r\n"
                   + "    office[at]phloc[dot]com\r\n"
                   + "\r\n"
                   + "    Licensed under the Apache License, Version 2.0 (the \"License\");\r\n"
                   + "    you may not use this file except in compliance with the License.\r\n"
                   + "    You may obtain a copy of the License at\r\n"
                   + "\r\n"
                   + "            http://www.apache.org/licenses/LICENSE-2.0\r\n"
                   + "\r\n"
                   + "    Unless required by applicable law or agreed to in writing, software\r\n"
                   + "    distributed under the License is distributed on an \"AS IS\" BASIS,\r\n"
                   + "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n"
                   + "    See the License for the specific language governing permissions and\r\n"
                   + "    limitations under the License.\r\n"
                   + "\r\n"
                   + "-->\r\n";
    sHTML += MicroWriter.getNodeAsString (html, new XMLWriterSettings ().setIndent (EXMLSerializeIndent.ALIGN_ONLY));

    SimpleFileIO.writeFile (new File ("src/main/resources/supported-css-properties.html"),
                            sHTML,
                            CCharset.CHARSET_UTF_8_OBJ);
  }
}
