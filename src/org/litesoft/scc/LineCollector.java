package org.litesoft.scc;

import org.litesoft.commonfoundation.base.*;

import java.io.*;
import java.util.*;

/**
 * Created by markc on 9/29/14.
 */
public class LineCollector {

    private final String mType;
    private final List<String> mLines = new ArrayList<>();
    private boolean mLastInsignificant = false;

    public LineCollector( String pType ) {
        mType = pType;
    }

    public void add( String pLine ) {
        if ( Currently.insignificant( pLine ) ) {
            mLastInsignificant = !mLines.isEmpty();
            return;
        }
        if ( mLastInsignificant ) {
            mLines.add( "" );
            mLastInsignificant = false;
        }
        mLines.add( pLine );
    }

    public List<String> getLines() {
        return mLines;
    }

    public boolean isEmpty() {
        return mLines.isEmpty();
    }

    public void addTo( List<String> pList ) {
        pList.add( mType );
        for ( String zLine : mLines ) {
            pList.add( zLine );
        }
    }
}
