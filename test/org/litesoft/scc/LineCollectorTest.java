package org.litesoft.scc;

import org.junit.*;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

public class LineCollectorTest {

    public static final String REAL_LINE1 = "Real Line1";
    public static final String REAL_LINE2 = "Real Line2";

    @Test
    public void testAdd() {
        LineCollector zLineCollector = new LineCollector( "TEST" );
        zLineCollector.add( "" );
        zLineCollector.add( "    " );
        zLineCollector.add( REAL_LINE1 );
        zLineCollector.add( "" );
        zLineCollector.add( "    " );
        zLineCollector.add( REAL_LINE2 );
        zLineCollector.add( "" );
        zLineCollector.add( "    " );
        assertEquals( Arrays.asList(REAL_LINE1, "", REAL_LINE2), zLineCollector.getLines());
    }
}