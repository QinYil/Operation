package com.common;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;
import java.util.concurrent.Callable;

public class AwarCallable implements Callable {
    private FileBuffer fileBuffer;
    private String remotePath;
    private String branch;
    private String localFile;
    private UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider;
    private String name;
    public AwarCallable(FileBuffer fileBuffer, String remotePath, String branch, String localFile, UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider,String name) {
        this.fileBuffer = fileBuffer;
        this.remotePath = remotePath;
        this.branch = branch;
        this.localFile = localFile;
        this.usernamePasswordCredentialsProvider = usernamePasswordCredentialsProvider;
        this.name = name;
    }


    public Object call() {
        try {
            Git git = Git.cloneRepository().setURI(remotePath+name+".git").setBranch(branch).setDirectory(new File(localFile+"/"+name)).setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
            fileBuffer.setTpath(localFile+"/"+name);
            fileBuffer.write();
            git.add().addFilepattern(".").call();
            git.commit().setMessage("测试2").call();
            git.push().setRemote(remotePath+name+".git").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return null;

    }
}
