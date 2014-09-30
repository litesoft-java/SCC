package org.litesoft.scc;

/**
 * Created by markc on 9/14/14.
 */
public interface SCCadapter {
    String sccDirectoryName();

    /**
     * return true if there was an error
     */
    boolean process( String pRelativePath, DirectoryResults pResults );
}
