package org.litesoft.scc;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class DirectoryResults {
    private final String mRelativePath;
    private LineTracker mDirties = new LineTracker();
    private LineTracker mErrors = new LineTracker();

    public DirectoryResults( String pRelativePath ) {
        mRelativePath = pRelativePath;
    }

    public String getRelativePath() {
        return mRelativePath;
    }

    public void printDirty() {
        mDirties.print( mRelativePath );
    }

    public void printError() {
        mErrors.print( mRelativePath );
    }

    public void addError( String pLine ) {
        mErrors.add( pLine );
    }

    public void addError( Exception pException ) {
        String zLines = Throwables.printStackTraceToString( pException );
        mErrors.add( Strings.stringToLines( zLines ) );
    }

    public void addErrors( LineCollector pCollector ) {
        pCollector.addTo( mErrors.mLines );
    }

    public void addDirties( List<String> pLines ) {
        mDirties.add( pLines );
    }

    private static class LineTracker {
        private final List<String> mLines = new ArrayList<>();
        private boolean mAdded;

        public void print( String mRelativePath ) {
            if ( mAdded ) {
                System.out.println( "From: " + mRelativePath );
                for ( String zLine : mLines ) {
                    System.out.println( zLine );
                }
            }
        }

        public void add( String... pLines ) {
            mAdded = true;
            for ( String zLine : pLines ) {
                mLines.add( zLine );
            }
        }

        public void add( List<String> pLines ) {
            mAdded = true;
            for ( String zLine : pLines ) {
                mLines.add( zLine );
            }
        }
    }
}
