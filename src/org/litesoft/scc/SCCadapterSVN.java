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
        List<String> zLines = runCommand( pResults, "-u status" ); // -u always gets some output if everything is working correctly.
        if ( zLines == null ) {
            return Error;
        }
//    *              975   Java/ScarPlus/src/com/esotericsoftware/scar/Scar.java
//    M              975   Java/KeyHole/KeyHole.ipr
//    M              975   Java/DATT/DATT.ipr
//    ?                    Java/DATT/src/DiagramTest
//    M              975   MongoDB.txt
//    Status against revision:    976
//
//    Status against revision:     32
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return Dirty;
    }

    @Override
    public CommandResults update( DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "update --accept postpone" );
        if ( zLines == null ) {
            return Error;
        }
// svn update --accept p
//        Updating '.':
//        C    ztest.txt
//        Updated to revision 980.
//        Summary of conflicts:
//        Text conflicts: 1
        // Creates 3 more files (*.mine, *.r{previous_version}, *.r{current_repo_version}) and the original file with merge conflict markers
        // Therefore: If we see the word "conflicts" then we need to dump the whole output to the error space.
        //            Errors will need to be fixed before running again. (Can use IntelliJ to fix the errors [merge the three files]). Then run again.
        // Otherwise: If we see "Updated to revision" then show the whole output to the update space.
        // Otherwise 2: If we anything other then
        // "Updating '.':
        //  At revision XXX."
        // then it is an error and the output should be added to the error space.
        // Otherwise 3: Report nothing! Yeah!

//    Updating '.':
//    At revision 32.
//
//    Updating '.':
//    U    Java/ScarPlus/src/com/esotericsoftware/scar/Scar.java
//    Updated to revision 976.
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return Dirty;
    }
}
