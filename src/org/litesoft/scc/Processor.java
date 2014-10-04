package org.litesoft.scc;

public interface Processor {
    /**
     * return true if there was an error
     */
    boolean process( SCCadapter pSCCadapter, String pRelativePath, DirectoryResults pResults );
}
