package org.litesoft.scc;

import java.util.*;

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
    @Override
    public String sccDirectoryName() {
        return ".svn";
    }

    @Override
    public boolean process( String pRelativePath, DirectoryResults pResults ) {
        System.out.print( " - svn" );
        List<String> zLines = runCommand( pResults, "svn -q update" );
        if ( zLines == null ) {
            return true;  // Error occurred
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return false;   // No Error
    }
}
