package org.litesoft.scc;

import java.util.*;

/**
 * Created by markc on 9/14/14.
 */
public class SCCadapterGit extends AbstractSCCadapter {
    @Override
    public String sccDirectoryName() {
        return ".git";
    }

    @Override
    public boolean process( String pRelativePath, DirectoryResults pResults ) {
        System.out.print( " - git" );
        List<String> zLines = runCommand( pResults, "git --no-pager pull" );
        if ( zLines == null ) {
            return true;  // Error occurred
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return false;   // No Error
    }
}
