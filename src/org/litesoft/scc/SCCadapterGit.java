package org.litesoft.scc;

/**
 * Created by markc on 9/14/14.
 */
public class SCCadapterGit implements SCCadapter {
    @Override
    public String sccDirectoryName() {
        return ".git";
    }

    @Override
    public boolean process( String pRelativePath ) {
        System.out.print( " - git" );
        return false;   // todo: XXX
    }
}
