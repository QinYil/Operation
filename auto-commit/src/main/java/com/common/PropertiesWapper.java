package com.common;

import com.common.display.ParamInfo;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class PropertiesWapper extends Properties {

    public List getPaths(String paths){
        if (paths.contains(",")){
            return Arrays.asList(paths.split(","));
        }else {
            return Collections.singletonList(paths);
        }
    }
    public void push(String proName) throws FileNotFoundException {
        File file = new File(this.getClass().getClassLoader().getResource(proName).getFile());
        OutputStream out = new FileOutputStream(file);
        try {
            this.store(out,null);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
