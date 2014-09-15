package org.litesoft.scc;

/**
 * Created by markc on 9/14/14.
 */
public interface SCCadapter {
    String sccDirectoryName();

    boolean process( String pRelativePath );
}
