package org.litesoft.scc;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

/**
 * Starting from the current directory
 * Depth first traversal of directories looking for a SCC based directory.
 * Foreach found run the appropriate command via exec.
 * Show path where the potential update will occurred.
 * Based on the Processor show the appropriate results.
 * Otherwise stream out any messages (errors) at the end of all the processing.
 * If dirty than report at the end the directories needing committing/reverting.
 */
public abstract class AbstractSCC_CLI {

    public static final int EXIT_VALUE_SUCCESS = 0;
    public static final int EXIT_VALUE_ATTENTION_NEEDED = 1;
    public static final int EXIT_VALUE_EXCEPTION = 2;

    private final Processor mProcessor;
    private final SCCadapter[] mSCCadapters;

    protected AbstractSCC_CLI( Processor pProcessor, SCCadapter... pSCCadapters ) {
        mProcessor = Confirm.isNotNull( "Processor", pProcessor );
        mSCCadapters = Confirm.isNotNullAndElementsNotNull( "SCCadapters", pSCCadapters );
    }

    protected int process() {
        List<DirectoryProcessor> zCollector = Lists.newArrayList();
        try {
            new DirectoryProcessor( mProcessor, mSCCadapters, "." ).process( zCollector );
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
}
