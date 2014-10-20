package org.litesoft.scc;

/**
 * Starting from the current directory
 * Depth first traversal of directories looking for a git (".git") or svn (".svn") directory.
 * Foreach found run the appropriate command via exec. For git: "git --no-pager pull", For svn: "svn -q update".
 * Show path where the potential update will occurred.
 * If any updates and updated successfully print "Updated"
 * If no updates print nothing.
 * Otherwise stream out any messages (errors) at the end of all the processing.
 * If there is no error than we run the status (git status or svn status).
 * If dirty than report at the end the directories needing committing/reverting.
 */
public class Updater extends AbstractSCC_CLI {

    public Updater() {
        super( SCCadapter.UPDATE
//                , new SCCadapterSVN()
                , new SCCadapterGit()
        );
    }

    public static void main( String[] args ) {
        System.exit( new Updater().process() );
    }
}
