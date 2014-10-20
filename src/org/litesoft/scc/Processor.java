package org.litesoft.scc;

public interface Processor {
    CommandResults process( SCCadapter pSCCadapter, DirectoryResults pResults );
}
