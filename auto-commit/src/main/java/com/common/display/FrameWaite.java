package com.common.display;

import javax.swing.*;
import java.awt.*;

public class FrameWaite extends JFrame{
    private static FrameWaite waite;
    private Container c = this.getContentPane();
    private JLabel info = new JLabel("正在修改文件上传中..." );
    private FrameWaite(){
        this.setBounds(700,500,300,200);
        c.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.setVisible(true);
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        info.setBounds(60,35,120,80);
        panel.add(info);
        c.add(panel,"Center");
    }

    public static FrameWaite instance(){
       if (waite==null){
           waite = new FrameWaite();
       }
       return waite;
    }
    public Container getC() {
        return c;
    }

    public void setC(Container c) {
        this.c = c;
    }

    public JLabel getInfo() {
        return info;
    }

    public void setInfo(JLabel info) {
        this.info = info;
    }
}
