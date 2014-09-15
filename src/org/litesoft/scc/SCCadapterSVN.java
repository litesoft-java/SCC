package org.litesoft.scc;

import java.io.*;
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
// todo: SCCadapter convert to abstract class adding the runCommand and Gobbler stuff
public class SCCadapterSVN implements SCCadapter {
    @Override
    public String sccDirectoryName() {
        return ".svn";
    }

    @Override
    public boolean process( String pRelativePath ) {
        System.out.print( " - svn" );
        List<String> zLines = runCommand( "svn -q update" );
        return false;   // todo: XXX
    }

    private List<String> runCommand( String pCommand ) {
        try {
            Process proc = Runtime.getRuntime().exec( pCommand );
            // any error message?
            StreamGobbler errorGobbler = new
                    StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new
                    StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // todo: Collect and not print out
    static class StreamGobbler extends Thread {
        InputStream is;
        String type;

        StreamGobbler( InputStream is, String type ) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader( is );
                BufferedReader br = new BufferedReader( isr );
                String line = null;
                while ( (line = br.readLine()) != null ) {
                    System.out.println( type + ">" + line );
                }
            }
            catch ( IOException ioe ) {
                ioe.printStackTrace();
            }
        }
    }
}
