package org.litesoft.scc;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

/**
 * Starting from the current directory
 * Depth first traversal of directories looking for a git (".git") or svn (".svn") directory.
 * Foreach found run the appropriate command via exec. For git: "git pull", For svn: "svn update".
 * Show path where the potential update will occurred.
 * If any updates and updated successfully print "Updated"
 * If no updates print nothing.
 * Otherwise stream out any messages (errors) at the end of all the processing.
 * If there is no error than we run the status (git status or svn status).
 * If dirty than report at the end the directories needing committing/reverting.
 * <p/>
 * Created by markc on 9/14/14.
 */
public class Updater {

    public static final int EXIT_VALUE_SUCCESS = 0;
    public static final int EXIT_VALUE_ATTENTION_NEEDED = 1;
    public static final int EXIT_VALUE_EXCEPTION = 2;

    private int process( SCCadapter... pSCCadapters ) {
        List<DirectoryProcessor> zCollector = Lists.newArrayList();
        try {
            new DirectoryProcessor( pSCCadapters, "." ).process( zCollector );
        }
        catch ( Exception e ) {
            System.out.println();
            e.printStackTrace();
            return EXIT_VALUE_EXCEPTION;
        }
        if ( zCollector.isEmpty() ) {
            return EXIT_VALUE_SUCCESS;
        }
        dump( zCollector );
        return EXIT_VALUE_ATTENTION_NEEDED;
    }

    private void dump( List<DirectoryProcessor> pCollector ) {
        System.out.println( Strings.dupChars( '=', 64 ) );
        printErrors( pCollector );
        printDirties( pCollector );
    }

    private void printErrors( List<DirectoryProcessor> pCollector ) {
        for ( DirectoryProcessor zProcessor : pCollector ) {
            zProcessor.printError();
        }
    }

    private void printDirties( List<DirectoryProcessor> pCollector ) {
        for ( DirectoryProcessor zProcessor : pCollector ) {
            zProcessor.printDirty();
        }
    }

    public static void main( String[] args ) {
        System.exit( new Updater().process( new SCCadapterSVN(), new SCCadapterGit() ) );
    }
}
