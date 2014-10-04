package org.litesoft.scc;

import java.util.*;

public class SCCadapterGit extends AbstractSCCadapter {
    public SCCadapterGit() {
        super( "git" );
    }

    @Override
    public boolean status( String pRelativePath, DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "status" );
        if ( zLines == null ) {
            return true;  // Error occurred
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return false;   // No Error
    }

    @Override
    public boolean update( String pRelativePath, DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "--no-pager pull" );
        if ( zLines == null ) {
            return true;  // Error occurred
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return false;   // No Error
    }
}
