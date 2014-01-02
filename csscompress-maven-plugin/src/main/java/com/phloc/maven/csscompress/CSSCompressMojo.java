/**
 * Copyright (C) 2006-2014 phloc systems
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
package com.phloc.maven.csscompress;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.slf4j.impl.StaticLoggerBinder;

import com.phloc.commons.charset.CCharset;
import com.phloc.commons.charset.CharsetManager;
import com.phloc.commons.io.EAppend;
import com.phloc.commons.io.file.FileUtils;
import com.phloc.commons.io.file.FilenameHelper;
import com.phloc.commons.io.resource.FileSystemResource;
import com.phloc.css.CCSS;
import com.phloc.css.CSSFilenameHelper;
import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.handler.ICSSParseExceptionHandler;
import com.phloc.css.parser.ParseException;
import com.phloc.css.reader.CSSReader;
import com.phloc.css.writer.CSSWriter;
import com.phloc.css.writer.CSSWriterSettings;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * @goal csscompress
 * @phase generate-resources
 * @description Compress existing CSS file using phloc-css compressor.
 */
@SuppressFBWarnings (value = { "UWF_UNWRITTEN_FIELD", "NP_UNWRITTEN_FIELD" }, justification = "set via maven property")
public final class CSSCompressMojo extends AbstractMojo
{
  private static final String [] EXTENSIONS_CSS_COMPRESSED = new String [] { CCSS.FILE_EXTENSION_MIN_CSS,
                                                                            "-min.css",
                                                                            ".minified.css",
                                                                            "-minified.css" };

  /**
   * The Maven Project.
   * 
   * @parameter property="project"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * The directory where the CSS files reside. It must be an existing directory.
   * 
   * @required
   * @parameter property="sourceDirectory"
   *            default-value="${basedir}/src/main/resources"
   */
  private File sourceDirectory;

  /**
   * Should all directories be scanned recursively for CSS files to compress?
   * 
   * @parameter property="recursive" default-value="true"
   */
  private boolean recursive = true;

  /**
   * Should unnecessary code be removed (e.g. rules without declarations)
   * 
   * @parameter property="removeUnnecessaryCode" default-value="false"
   */
  private boolean removeUnnecessaryCode = false;

  /**
   * Should URLs always be quoted? If false they are only quoted on demand.
   * 
   * @parameter property="quoteURLs" default-value="false"
   */
  private boolean quoteURLs = false;

  /**
   * Should <code>@namespace</code> rules be written?
   * 
   * @parameter property="writeNamespaceRules" default-value="true"
   * @since 1.0.18
   */
  private boolean writeNamespaceRules = true;

  /**
   * Should <code>@font-face</code> rules be written?
   * 
   * @parameter property="writeFontFaceRules" default-value="true"
   */
  private boolean writeFontFaceRules = true;

  /**
   * Should <code>@keyframes</code> rules be written?
   * 
   * @parameter property="writeKeyframesRules" default-value="true"
   */
  private boolean writeKeyframesRules = true;

  /**
   * Should <code>@media</code> rules be written?
   * 
   * @parameter property="writeMediaRules" default-value="true"
   */
  private boolean writeMediaRules = true;

  /**
   * Should <code>@page</code> rules be written?
   * 
   * @parameter property="writePageRules" default-value="true"
   */
  private boolean writePageRules = true;

  /**
   * Should <code>@viewport</code> rules be written?
   * 
   * @parameter property="writeViewportRules" default-value="true"
   * @since 1.0.18
   */
  private boolean writeViewportRules = true;

  /**
   * Should <code>@supports</code> rules be written?
   * 
   * @parameter property="writeSupportsRules" default-value="true"
   * @since 1.0.18
   */
  private boolean writeSupportsRules = true;

  /**
   * Should the CSS files be compressed, even if the timestamp of the compressed
   * file is newer than the timestamp of the original CSS file?
   * 
   * @parameter property="forceCompress" default-value="false"
   */
  private boolean forceCompress = false;

  /**
   * If true some debug output is emitted?
   * 
   * @parameter property="verbose" default-value="false"
   */
  private boolean verbose = false;

  /**
   * The encoding of the source CSS files.
   * 
   * @parameter property="sourceEncoding" default-value="UTF-8"
   */
  private String sourceEncoding = CCharset.CHARSET_UTF_8;

  /**
   * The extension that should be supplied to the minified/compressed CSS file.
   * 
   * @parameter property="targetFileExtension" default-value=".min.css"
   */
  private String targetFileExtension = CCSS.FILE_EXTENSION_MIN_CSS;

  @SuppressFBWarnings ({ "NP_UNWRITTEN_FIELD", "UWF_UNWRITTEN_FIELD" })
  public void setSourceDirectory (final File aDir)
  {
    sourceDirectory = aDir;
    if (!sourceDirectory.isAbsolute ())
      sourceDirectory = new File (project.getBasedir (), aDir.getPath ());
    if (!sourceDirectory.exists ())
      getLog ().error ("CSS source directory '" + sourceDirectory + "' does not exist!");
  }

  public void setRecursive (final boolean bRecursive)
  {
    recursive = bRecursive;
  }

  public void setRemoveUnnecessaryCode (final boolean bRemoveUnnecessaryCode)
  {
    removeUnnecessaryCode = bRemoveUnnecessaryCode;
  }

  public void setQuoteURLs (final boolean bQuoteURLs)
  {
    quoteURLs = bQuoteURLs;
  }

  public void setWriteNamespaceRules (final boolean bWriteNamespaceRules)
  {
    writeNamespaceRules = bWriteNamespaceRules;
  }

  public void setWriteFontFaceRules (final boolean bWriteFontFaceRules)
  {
    writeFontFaceRules = bWriteFontFaceRules;
  }

  public void setWriteKeyframesRules (final boolean bWriteKeyframesRules)
  {
    writeKeyframesRules = bWriteKeyframesRules;
  }

  public void setWriteMediaRules (final boolean bWriteMediaRules)
  {
    writeMediaRules = bWriteMediaRules;
  }

  public void setWritePageRules (final boolean bWritePageRules)
  {
    writePageRules = bWritePageRules;
  }

  public void setWriteViewportRules (final boolean bWriteViewportRules)
  {
    writeViewportRules = bWriteViewportRules;
  }

  public void setWriteSupportsRules (final boolean bWriteSupportsRules)
  {
    writeSupportsRules = bWriteSupportsRules;
  }

  public void setForceCompress (final boolean bForceCompress)
  {
    forceCompress = bForceCompress;
  }

  public void setVerbose (final boolean bVerbose)
  {
    verbose = bVerbose;
  }

  public void setSourceEncoding (final String sSourceEncoding)
  {
    // Throws an exception on an illegal charset
    CharsetManager.getCharsetFromName (sSourceEncoding);
    sourceEncoding = sSourceEncoding;
  }

  public void setTargetFileExtension (final String sTargetFileExtension)
  {
    targetFileExtension = sTargetFileExtension;
  }

  /**
   * Check if the passed file is already compressed. The check is only done
   * using the file extension of the file name.
   * 
   * @param sFilename
   *        The filename to be checked.
   * @return <code>true</code> if the file is already compressed.
   */
  private static boolean _isAlreadyCompressed (final String sFilename)
  {
    for (final String sExt : EXTENSIONS_CSS_COMPRESSED)
      if (sFilename.endsWith (sExt))
        return true;
    return false;
  }

  @Nonnull
  private String _getRelativePath (@Nonnull final File aFile)
  {
    return aFile.getAbsolutePath ().substring (sourceDirectory.getAbsolutePath ().length () + 1);
  }

  private void _compressCSSFile (@Nonnull final File aChild)
  {
    // Compress the file only if the compressed file is older than the original
    // file. Note: lastModified on a non-existing file returns 0L
    final File aCompressed = new File (FilenameHelper.getWithoutExtension (aChild.getAbsolutePath ()) +
                                       targetFileExtension);
    if (aCompressed.lastModified () < aChild.lastModified () || forceCompress)
    {
      if (verbose)
        getLog ().info ("Start compressing CSS file " + _getRelativePath (aChild));
      else
        getLog ().debug ("Start compressing CSS file " + _getRelativePath (aChild));
      final ICSSParseExceptionHandler aExHdl = new ICSSParseExceptionHandler ()
      {
        public void onException (@Nonnull final ParseException ex)
        {
          // Ensure the file name is printed
          getLog ().error ("Failed to parse CSS file " + _getRelativePath (aChild), ex);
        }
      };
      final Charset aSourceEncoding = CharsetManager.getCharsetFromName (sourceEncoding);
      final CascadingStyleSheet aCSS = CSSReader.readFromFile (aChild, aSourceEncoding, ECSSVersion.CSS30, aExHdl);
      if (aCSS != null)
      {
        // We read it!
        final FileSystemResource aDestFile = new FileSystemResource (aCompressed);
        try
        {
          final CSSWriterSettings aWriterSettings = new CSSWriterSettings (ECSSVersion.CSS30, true);
          aWriterSettings.setRemoveUnnecessaryCode (removeUnnecessaryCode);
          aWriterSettings.setQuoteURLs (quoteURLs);
          aWriterSettings.setWriteNamespaceRules (writeNamespaceRules);
          aWriterSettings.setWriteFontFaceRules (writeFontFaceRules);
          aWriterSettings.setWriteKeyframesRules (writeKeyframesRules);
          aWriterSettings.setWriteMediaRules (writeMediaRules);
          aWriterSettings.setWritePageRules (writePageRules);
          aWriterSettings.setWriteViewportRules (writeViewportRules);
          aWriterSettings.setWriteSupportsRules (writeSupportsRules);
          new CSSWriter (aWriterSettings).writeCSS (aCSS, aDestFile.getWriter (aSourceEncoding, EAppend.TRUNCATE));
        }
        catch (final IOException ex)
        {
          getLog ().error ("Failed to write compressed CSS file '" + aCompressed.toString () + "' to disk", ex);
        }
      }
    }
    else
    {
      if (verbose)
        getLog ().info ("Ignoring already compressed CSS file " + _getRelativePath (aChild));
      else
        getLog ().debug ("Ignoring already compressed CSS file " + _getRelativePath (aChild));
    }
  }

  private void _scanDirectory (@Nonnull final File aDir)
  {
    for (final File aChild : FileUtils.getDirectoryContent (aDir))
    {
      if (aChild.isDirectory ())
      {
        // Shall we recurse into sub-directories?
        if (recursive)
          _scanDirectory (aChild);
      }
      else
        if (aChild.isFile () &&
            CSSFilenameHelper.isCSSFilename (aChild.getName ()) &&
            !_isAlreadyCompressed (aChild.getName ()))
        {
          // We're ready to rumble!
          _compressCSSFile (aChild);
        }
    }
  }

  public void execute () throws MojoExecutionException
  {
    StaticLoggerBinder.getSingleton ().setMavenLog (getLog ());
    if (verbose)
      getLog ().info ("Start compressing CSS files in directory " + sourceDirectory.getPath ());
    _scanDirectory (sourceDirectory);
  }
}
