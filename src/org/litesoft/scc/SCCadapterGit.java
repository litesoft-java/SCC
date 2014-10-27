package org.litesoft.scc;

import java.util.*;

import static org.litesoft.scc.CommandResults.*;
import static org.litesoft.scc.CommandResults.Error;

public class SCCadapterGit extends AbstractSCCadapter {
    public SCCadapterGit() {
        super( "git" );
    }

    @Override
    public CommandResults status( DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "status" );
        if ( zLines == null ) {
            return Error;
        }
        pResults.addDirties( zLines ); // TODO: Process Output to create Dirties in Results
        return Dirty;
    }

    @Override
    public CommandResults update( DirectoryResults pResults ) {
        reportProgress();
        List<String> zLines = runCommand( pResults, "--no-pager pull" );
        if ( zLines == null ) {
            return Error;
        }
        if ( alreadyUpToDate( zLines ) ) {
            return Success;
        }
        pResults.addDirties( zLines );
        return Dirty;
    }

    private boolean alreadyUpToDate( List<String> pLines ) {
        return (pLines.size() == 1) && "Already up-to-date.".equals( pLines.get( 0 ) );
    }
//    LOCAL=$(git rev-parse @)$(echo -e "\tHEAD")
//    REMOTE=$(git ls-remote origin -h HEAD)
//    STATUS=$(git status)
//
//    if [ "$LOCAL" = "$REMOTE" ]; then
//    echo "Up-to-date"
//    echo "$STATUS" | grep -s "working directory clean" > /dev/null
//            if [ "$?" != 0 ]; then
//    echo "$STATUS"
//    fi
//    else
//    echo -n "Diverged"
//    echo "$STATUS" | grep -s "working directory clean" > /dev/null
//            if [ "$?" = 0 ]; then
//    echo "$STATUS" | grep -s "is up-to-date" > /dev/null
//            if [ "$?" = 0 ]; then
//    echo " - You are behind: Do pull"
//    exit
//            fi
//    fi
//    echo " - Possibly behind"
//    echo "$STATUS"
//    fi

}
