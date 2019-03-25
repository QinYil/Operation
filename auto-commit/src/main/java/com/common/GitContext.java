package com.common;

import com.common.display.ParamInfo;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class GitContext {
   private static PropertiesWapper properties = new GitResourceFactory("config.properties").create();
   private static String localFile;
   private  static FileBuffer fileBuffer;
   private static ParamInfo info ;
   static {

        //gitFactory = new GitFactory(localFile);
   }
    public static void run(ParamInfo inf){
        info = inf;
        Field[] fields = info.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(i->{
            try {
                i.setAccessible(true);
                String name = (String)i.get(GitContext.info);
                if (name.isEmpty()){
                    i.set(GitContext.info,properties.getProperty(i.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        localFile =  GitContext.info.getLocalRepository();
        File file = new File(localFile);
        if(file.exists()&&!(file.length()==0)){
            try {
                throw new Exception("本地文件已存在");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }else if(!file.exists()){
            file.mkdirs();
        }
        String remotePath =  GitContext.info.getRemoteURL();
        String[] gitFile = remotePath.split("::");
        if (gitFile.length<2){
            try {
                throw new Exception("远程路劲格式错误");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            ;
        }
        if (gitFile[0].lastIndexOf("/")<gitFile[0].length()-1)
            gitFile[0]+="/";
        gitCloneAndChange(gitFile[0],gitFile[1],localFile);
    }
    private static void gitCloneAndChange(String remotePath, String branch, String localFile) {
            List<String> list = properties.getPaths(info.getFileNames());
            UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new UsernamePasswordCredentialsProvider(info.getUsername(),info.getPwd());
            fileBuffer  = new FileBuffer(info.getDocument());
            List<FutureTask> futures = new ArrayList<>();
            for (String name:list) {
                AwarCallable callable = new AwarCallable(fileBuffer,remotePath,branch,localFile,usernamePasswordCredentialsProvider,name);
                FutureTask task = new FutureTask(callable);
                Thread thread = new Thread(task);
                thread.start();
                futures.add(task);
            }
            futures.stream().forEach(i-> {
                try {
                    if (!i.isDone()) {
                        i.get();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
    }
}
