package org.litesoft.scc;

import org.litesoft.server.file.*;

import java.io.*;
import java.util.*;

/**
 * Check the passed in directory.
 * Look for a git (".git") or svn (".svn") directory.
 * If found run the appropriate command via exec. For git: "git pull", For svn: "svn update".
 * Show path where the potential update will occurred.
 * If any updates and updated successfully print "Updated"
 * If no updates print nothing.
 * Otherwise store  any messages (errors) for reporting.
 * If there is no error than we run the status (git status or svn status).
 * If dirty than store the fact this directory needs committing/reverting.
 * <p/>
 * If no .git or .svn directory found, apply this process to all the children directories.
 */
public class DirectoryProcessor {
    private final Processor mProcessor;
    private final SCCadapter[] mSCCadapters;
    private final String mRelativePath;
    private final DirectoryResults mResults;

    public DirectoryProcessor( Processor pProcessor, SCCadapter[] pSCCadapters, String pRelativePath ) {
        mProcessor = pProcessor;
        mSCCadapters = pSCCadapters;
        mRelativePath = pRelativePath;
        mResults = new DirectoryResults( pRelativePath );
    }

    public void process( List<DirectoryProcessor> pCollector ) {
        for ( SCCadapter zSCCadapter : mSCCadapters ) {
            if ( has( zSCCadapter.sccDirectoryName() ) ) {
                storeIfUnsuccessful( pCollector, mProcessor.process( zSCCadapter, mResults ) );
                return;
            }
        }
        processChildren( pCollector );
    }

    private void processChildren( List<DirectoryProcessor> pCollector ) {
        String[] zDirectoryNames = new File( mRelativePath ).list( FileUtils.DIRECTORIES_ONLY );
        for ( String zDirectoryName : zDirectoryNames ) {
            new DirectoryProcessor( mProcessor, mSCCadapters, mRelativePath + "/" + zDirectoryName ).process( pCollector );
        }
    }

    private boolean has( String pDirectoryName ) {
        boolean isFound = new File( mRelativePath, pDirectoryName ).isDirectory();
        if ( isFound ) {
            System.out.print( mRelativePath );
        }
        return isFound;
    }

    private void storeIfUnsuccessful( List<DirectoryProcessor> pCollector, CommandResults pCommandResults ) {
        System.out.println();
        if ( CommandResults.Success != pCommandResults ) {
            pCollector.add( this );
        }
    }

    public void printDirty() {
        mResults.printDirty();
    }

    public void printError() {
        mResults.printError();
    }
}
