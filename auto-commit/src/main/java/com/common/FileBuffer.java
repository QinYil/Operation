package com.common;

import java.io.*;


public class FileBuffer {
    private byte[] buffer = new byte[1024];
    private byte[] fileBuffer;
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private InputStream in;
    private OutputStream out;
    private File sFile;
    private File tFile;
    public FileBuffer(String sPath){
        sFile = new File(sPath);
        if (!sFile.exists()){
            try {
                throw new Exception("文件不存在");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            in = new FileInputStream(sFile);
            while (in.read(buffer,0,1024)>0){
                byteArrayOutputStream.write(buffer,0,1024);
            }
            in.close();
            fileBuffer = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public FileBuffer(String sPath,String tPath){
         sFile = new File(sPath);
         tFile = new File(tPath);
        if (!sFile.exists()){
            try {
                throw new Exception("目标文件不存在");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            in = new FileInputStream(sFile);
            out = new FileOutputStream(tFile);
            while (in.read(buffer,0,1024)>0){
                byteArrayOutputStream.write(buffer,0,1024);
            }
            fileBuffer = byteArrayOutputStream.toByteArray();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){

        }
    }
    public void write(){
        if (tFile.exists()){
            tFile.delete();
        }
        try {
            out.write(fileBuffer);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Boolean isTarget(File file){
        return tFile.getName().equals(file.getName())?true:false;
    }
    public void searchFile(File file){
            if (isTarget(file)){
                    tFile = file;
            }else if ("target".equals(file)||"node_modules".equals(file)){
                return;
            }else {
                File[] files = file.listFiles();
                if (files==null||files.length==0)
                    return;
                for (File cFile : files) {
                    searchFile(cFile);
                }
            }
    }
    public void setTpath(String tpath){
        System.out.println(sFile.getPath());
        tpath = tpath+"/"+sFile.getPath().substring(sFile.getPath().lastIndexOf("\\"));
        this.tFile = new File(tpath);
            if (!tFile.exists()){
                File pFile = tFile.getParentFile();
                searchFile(pFile);
            }
        try {
            out = new FileOutputStream(tFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
