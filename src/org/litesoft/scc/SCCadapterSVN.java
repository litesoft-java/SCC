package org.litesoft.scc;

import java.util.*;

import static org.litesoft.scc.CommandResults.*;

/**
 * For svn: "svn update".
 * If any updates and updated successfully print "Updated"
 * If no updates print nothing.
 * Otherwise stream out any messages (errors) at the end of all the processing.
 * If there is no error than we run the status (git status or svn status).
 * If dirty than report at the end the directories needing committing/reverting.
 * <p/>
 * Created by markc on 9/14/14.
 */
public class SCCadapterSVN extends AbstractSCCadapter {
    public SCCadapterSVN() {
        super( "svn" );
    }

    @Override
    public CommandResults status( DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "-q update" );
        if ( zLines == null ) {
            return Error;
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return Dirty;
    }

    @Override
    public CommandResults update( DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "-q update" );
        if ( zLines == null ) {
            return Error;
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return Dirty;
    }
}
