package com.common;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;

public class GitFactory {

    private Repository repository;
    private Git git;
    public GitFactory(String pathName){
        String locaGit = pathName+"/.git";
        try {
            repository = new FileRepository(locaGit);
            File file = new File(pathName);
            if (!file.exists()){
                repository.create();
            }
        } catch (IOException e) {
            System.out.println("创建git仓库出错");
            e.printStackTrace();
        }
    }
    public Git build(){
        git = new Git(repository);
        return this.git;
    }

}
