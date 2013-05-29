package com.phloc.css.supplementary.main;

import java.io.File;
import java.io.IOException;

import com.phloc.commons.jdk5.CreateJava5Version;

/**
 * This file is responsible for creating the JDK 5 version of this project!<br>
 * Just copy this file to each project which should be available in a JDK5
 * version
 * 
 * @author Philip Helger
 */
// SKIPJDK5
public class MainCreateJava5Version
{
  public static void main (final String [] args) throws IOException
  {
    CreateJava5Version.createJDK5Version (new File (""));
  }
}
