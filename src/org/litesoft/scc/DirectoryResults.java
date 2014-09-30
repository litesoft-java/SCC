package org.litesoft.scc;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class DirectoryResults {
    private List<String> mDirties = new ArrayList<>();
    private List<String> mErrors = new ArrayList<>();

    public void printDirty() {
        print( mDirties );
    }

    private void print( List<String> pList ) {
        for ( String zLine : pList ) {
            System.out.println( zLine );
        }
    }

    public void printError() {
        print( mErrors );
    }

    public void addError( String pLine ) {
        mErrors.add( pLine );
    }

    public void addError( Exception pException ) {
        String zLines = Throwables.printStackTraceToString( pException );
        Lists.append( mErrors, Strings.stringToLines( zLines ) );
    }

    public void addErrors( LineCollector pCollector ) {
        pCollector.addTo( mErrors );
    }

    public void addDirties( List<String> pLines ) {
        for ( String zLine : pLines ) {
            mDirties.add( zLine );
        }
    }
}
