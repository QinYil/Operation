package com.common;

import java.io.IOException;
import java.io.InputStream;

public class GitResourceFactory {
    private static PropertiesWapper properties = new PropertiesWapper();
    public GitResourceFactory(String fileName){
        InputStream in =  this.getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("加载配置出错");
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public PropertiesWapper create(){
        return properties;
    }
}
