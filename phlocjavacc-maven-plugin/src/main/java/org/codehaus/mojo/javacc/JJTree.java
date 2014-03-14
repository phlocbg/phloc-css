package org.codehaus.mojo.javacc;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file 
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied.  See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

/**
 * Provides a facade for the mojos to invoke JJTree.
 * 
 * @author Benjamin Bentmann
 * @version $Id: JJTree.java 10603 2009-09-06 15:05:08Z bentmann $
 * @see <a href="https://javacc.dev.java.net/doc/JJTree.html">JJTree Reference</a>
 */
class JJTree
    extends ToolFacade
{

    /**
     * The input grammar.
     */
    private File inputFile;

    /**
     * The option OUTPUT_DIRECTORY.
     */
    private File outputDirectory;

    /**
     * The option GRAMMAR_ENCODING.
     */
    private String grammarEncoding;

    /**
     * The option JDK_VERSION.
     */
    private String jdkVersion;

    /**
     * The option STATIC.
     */
    private Boolean isStatic;

    /**
     * The option BUILD_NODE_FILES.
     */
    private Boolean buildNodeFiles;

    /**
     * The option MULTI.
     */
    private Boolean multi;

    /**
     * The option NODE_DEFAULT_VOID.
     */
    private Boolean nodeDefaultVoid;

    /**
     * The option NODE_CLASS.
     */
    private String nodeClass;

    /**
     * The option NODE_FACTORY.
     */
    private String nodeFactory;

    /**
     * The option NODE_PACKAGE.
     */
    private String nodePackage;

    /**
     * The option NODE_PREFIX.
     */
    private String nodePrefix;

    /**
     * The option NODE_SCOPE_HOOK.
     */
    private Boolean nodeScopeHook;

    /**
     * The option NODE_USES_PARSER.
     */
    private Boolean nodeUsesParser;

    /**
     * The option TRACK_TOKENS.
     */
    private Boolean trackTokens;

    /**
     * The option VISITOR.
     */
    private Boolean visitor;

    /**
     * The option VISITOR_DATA_TYPE.
     */
    private String visitorDataType;

    /**
     * The option VISITOR_RETURN_TYPE.
     */
    private String visitorReturnType;

    /**
     * The option VISITOR_EXCEPTION.
     */
    private String visitorException;

    /**
     * Sets the absolute path to the grammar file to pass into JJTree for preprocessing.
     * 
     * @param value The absolute path to the grammar file to pass into JJTree for preprocessing.
     */
    public void setInputFile( File value )
    {
        if ( value != null && !value.isAbsolute() )
        {
            throw new IllegalArgumentException( "path is not absolute: " + value );
        }
        this.inputFile = value;
    }

    /**
     * Sets the absolute path to the output directory.
     * 
     * @param value The absolute path to the output directory for the generated grammar file. If this directory does not
     *            exist yet, it is created. Note that this path should already include the desired package hierarchy
     *            because JJTree will not append the required sub directories automatically.
     */
    public void setOutputDirectory( File value )
    {
        if ( value != null && !value.isAbsolute() )
        {
            throw new IllegalArgumentException( "path is not absolute: " + value );
        }
        this.outputDirectory = value;
    }

    /**
     * Gets the absolute path to the enhanced grammar file generated by JJTree.
     * 
     * @return The absolute path to the enhanced grammar file generated by JJTree or <code>null</code> if either the
     *         input file or the output directory have not been set.
     */
    public File getOutputFile()
    {
        File outputFile = null;
        if ( this.outputDirectory != null && this.inputFile != null )
        {
            String fileName = FileUtils.removeExtension( this.inputFile.getName() ) + ".jj";
            outputFile = new File( this.outputDirectory, fileName );
        }
        return outputFile;
    }

    /**
     * Sets the option GRAMMAR_ENCODING.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setGrammarEncoding( String value )
    {
        this.grammarEncoding = value;
    }

    /**
     * Sets the option JDK_VERSION.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setJdkVersion( String value )
    {
        this.jdkVersion = value;
    }

    /**
     * Sets the option STATIC.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setStatic( Boolean value )
    {
        this.isStatic = value;
    }

    /**
     * Sets the option value BUILD_NODE_FILES.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setBuildNodeFiles( Boolean value )
    {
        this.buildNodeFiles = value;
    }

    /**
     * Sets the option value MULTI.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setMulti( Boolean value )
    {
        this.multi = value;
    }

    /**
     * Sets the option value NODE_DEFAULT_VOID.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodeDefaultVoid( Boolean value )
    {
        this.nodeDefaultVoid = value;
    }

    /**
     * Sets the option value NODE_CLASS.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodeClass( String value )
    {
        this.nodeClass = value;
    }

    /**
     * Sets the option value NODE_FACTORY.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodeFactory( String value )
    {
        this.nodeFactory = value;
    }

    /**
     * Sets the option value NODE_PACKAGE.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodePackage( String value )
    {
        this.nodePackage = value;
    }

    /**
     * Sets the option value NODE_PREFIX.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodePrefix( String value )
    {
        this.nodePrefix = value;
    }

    /**
     * Sets the option value NODE_SCOPE_HOOK.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodeScopeHook( Boolean value )
    {
        this.nodeScopeHook = value;
    }

    /**
     * Sets the option value NODE_USES_PARSER.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setNodeUsesParser( Boolean value )
    {
        this.nodeUsesParser = value;
    }

    /**
     * Sets the option value TRACK_TOKENS.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setTrackTokens( Boolean value )
    {
        this.trackTokens = value;
    }

    /**
     * Sets the option value VISITOR.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setVisitor( Boolean value )
    {
        this.visitor = value;
    }

    /**
     * Sets the option value VISITOR_DATA_TYPE.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setVisitorDataType( String value )
    {
        this.visitorDataType = value;
    }

    /**
     * Sets the option value VISITOR_RETURN_TYPE.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setVisitorReturnType( String value )
    {
        this.visitorReturnType = value;
    }

    /**
     * Sets the option value VISITOR_EXCEPTION.
     * 
     * @param value The option value, may be <code>null</code> to use the value provided in the grammar or the default.
     */
    public void setVisitorException( String value )
    {
        this.visitorException = value;
    }

    /**
     * {@inheritDoc}
     */
    protected int execute()
        throws Exception
    {
        String[] args = generateArguments();

        if ( this.outputDirectory != null && !this.outputDirectory.exists() )
        {
            this.outputDirectory.mkdirs();
        }

        org.javacc.jjtree.JJTree jjtree = new org.javacc.jjtree.JJTree();
        return jjtree.main( args );
    }

    /**
     * Assembles the command line arguments for the invocation of JJTree according to the configuration.<br/><br/>
     * <strong>Note:</strong> To prevent conflicts with JavaCC options that might be set directly in the grammar file,
     * only those parameters that have been explicitly set are passed on the command line.
     * 
     * @return A string array that represents the arguments to use for JJTree.
     */
    private String[] generateArguments()
    {
        List argsList = new ArrayList();

        if ( StringUtils.isNotEmpty( this.grammarEncoding ) )
        {
            argsList.add( "-GRAMMAR_ENCODING=" + this.grammarEncoding );
        }

        if ( StringUtils.isNotEmpty( jdkVersion ) )
        {
            argsList.add( "-JDK_VERSION=" + this.jdkVersion );
        }

        if ( this.buildNodeFiles != null )
        {
            argsList.add( "-BUILD_NODE_FILES=" + this.buildNodeFiles );
        }

        if ( this.multi != null )
        {
            argsList.add( "-MULTI=" + this.multi );
        }

        if ( this.nodeDefaultVoid != null )
        {
            argsList.add( "-NODE_DEFAULT_VOID=" + this.nodeDefaultVoid );
        }

        if ( StringUtils.isNotEmpty( this.nodeClass ) )
        {
            argsList.add( "-NODE_CLASS=" + this.nodeClass );
        }

        if ( StringUtils.isNotEmpty( this.nodeFactory ) )
        {
            argsList.add( "-NODE_FACTORY=" + this.nodeFactory );
        }

        if ( StringUtils.isNotEmpty( this.nodePackage ) )
        {
            argsList.add( "-NODE_PACKAGE=" + this.nodePackage );
        }

        if ( StringUtils.isNotEmpty( this.nodePrefix ) )
        {
            argsList.add( "-NODE_PREFIX=" + this.nodePrefix );
        }

        if ( this.nodeScopeHook != null )
        {
            argsList.add( "-NODE_SCOPE_HOOK=" + this.nodeScopeHook );
        }

        if ( this.nodeUsesParser != null )
        {
            argsList.add( "-NODE_USES_PARSER=" + this.nodeUsesParser );
        }

        if ( this.isStatic != null )
        {
            argsList.add( "-STATIC=" + this.isStatic );
        }

        if ( this.trackTokens != null )
        {
            argsList.add( "-TRACK_TOKENS=" + this.trackTokens );
        }

        if ( this.visitor != null )
        {
            argsList.add( "-VISITOR=" + this.visitor );
        }

        if ( StringUtils.isNotEmpty( this.visitorDataType ) )
        {
            argsList.add( "-VISITOR_DATA_TYPE=" + this.visitorDataType );
        }

        if ( StringUtils.isNotEmpty( this.visitorReturnType ) )
        {
            argsList.add( "-VISITOR_RETURN_TYPE=" + this.visitorReturnType );
        }

        if ( StringUtils.isNotEmpty( this.visitorException ) )
        {
            argsList.add( "-VISITOR_EXCEPTION=" + this.visitorException );
        }

        if ( this.outputDirectory != null )
        {
            argsList.add( "-OUTPUT_DIRECTORY=" + this.outputDirectory.getAbsolutePath() );
        }

        if ( this.inputFile != null )
        {
            argsList.add( this.inputFile.getAbsolutePath() );
        }

        return (String[]) argsList.toArray( new String[argsList.size()] );
    }

    /**
     * Gets a string representation of the command line arguments.
     * 
     * @return A string representation of the command line arguments.
     */
    public String toString()
    {
        return Arrays.asList( generateArguments() ).toString();
    }

}
