package org.litesoft.scc;

public interface SCCadapter {
    public static final Processor STATUS = new Processor() {
        @Override
        public CommandResults process( SCCadapter pSCCadapter, DirectoryResults pResults ) {
            return pSCCadapter.status( pResults );
        }
    };

    public static final Processor UPDATE = new Processor() {
        @Override
        public CommandResults process( SCCadapter pSCCadapter, DirectoryResults pResults ) {
            return pSCCadapter.update( pResults );
        }
    };

    String sccAppName();

    String sccDirectoryName();

    CommandResults status( DirectoryResults pResults );

    CommandResults update( DirectoryResults pResults );
}
