package org.litesoft.scc;

public interface SCCadapter {
    public static final Processor STATUS = new Processor() {
        @Override
        public boolean process( SCCadapter pSCCadapter, String pRelativePath, DirectoryResults pResults ) {
            return pSCCadapter.status( pRelativePath, pResults );
        }
    };

    public static final Processor UPDATE = new Processor() {
        @Override
        public boolean process( SCCadapter pSCCadapter, String pRelativePath, DirectoryResults pResults ) {
            return pSCCadapter.update( pRelativePath, pResults );
        }
    };

    String sccAppName();

    String sccDirectoryName();

    /**
     * return true if there was an error
     */
    boolean status( String pRelativePath, DirectoryResults pResults );

    /**
     * return true if there was an error
     */
    boolean update( String pRelativePath, DirectoryResults pResults );
}
