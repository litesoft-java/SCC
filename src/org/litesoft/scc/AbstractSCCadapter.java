package org.litesoft.scc;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.server.util.*;

import java.io.*;
import java.util.*;

public abstract class AbstractSCCadapter implements SCCadapter {
    private final String mAppName;

    protected AbstractSCCadapter( String pAppName ) {
        mAppName = Confirm.significant( "AppName", pAppName );
    }

    @Override
    public final String sccAppName() {
        return mAppName;
    }

    @Override
    public String sccDirectoryName() {
        return "." + mAppName;
    }

    protected void reportProgress() {
        System.out.print( " - " + mAppName );
    }

    protected List<String> runCommand( DirectoryResults pResults, String pParams ) {
        return new CommandExecutor( pResults ).invoke( mAppName + " " + pParams );
    }

    public static class StreamCollector extends Thread {
        private final InputStream mInputStream;
        private final LineCollector mLines;

        public StreamCollector( LineCollector pLines, InputStream pInputStream ) {
            mInputStream = pInputStream;
            mLines = pLines;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader( new InputStreamReader( mInputStream ) );
                for ( String line; (line = br.readLine()) != null; ) {
                    mLines.add( line );
                }
            }
            catch ( IOException ioe ) {
                ioe.printStackTrace();
            }
            finally {
                Closeables.dispose( mInputStream );
            }
        }
    }

    private static class CommandExecutor {
        private final LineCollector zOutput = new LineCollector( "OUTPUT" );
        private final LineCollector zErrors = new LineCollector( "ERROR" );
        private final DirectoryResults mResults;

        public CommandExecutor( DirectoryResults pResults ) {
            mResults = pResults;
        }

        public List<String> invoke( String pCommand ) {
//            warmup(); /// TODO: WTF

            try {
                Process proc = Runtime.getRuntime().exec( pCommand, null, new File( mResults.getRelativePath() ) );
                new StreamCollector( zOutput, proc.getInputStream() ).start();
                new StreamCollector( zErrors, proc.getErrorStream() ).start();

                int exitVal = proc.waitFor();
                if ( exitVal == 0 && zErrors.isEmpty() ) {
                    return zOutput.getLines();
                }
                dumpLines();
                mResults.addError( "ExitValue: " + exitVal );
            }
            catch ( IOException | InterruptedException e ) {
                dumpLines();
                mResults.addError( e );
            }
            return null;
        }

        private void warmup() {
            try {
                Process proc = Runtime.getRuntime().exec( "git", null, new File( mResults.getRelativePath() ) );
                new StreamCollector( new LineCollector( "OUTPUT" ), proc.getInputStream() ).start();
                new StreamCollector( new LineCollector( "ERROR" ), proc.getErrorStream() ).start();
                proc.waitFor();
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        private void dumpLines() {
            mResults.addErrors( zOutput );
            mResults.addErrors( zErrors );
        }
    }
}
