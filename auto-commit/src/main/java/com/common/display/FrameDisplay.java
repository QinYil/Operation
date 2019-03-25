package com.common.display;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.FutureTask;

public class FrameDisplay {
  private JFrame jFrame = new JFrame("参数信息录入");
  private Container c = jFrame.getContentPane();
  private JLabel userLable = new JLabel("远端用户名:");
  private JTextField userText = new JTextField();
  private JLabel pwdLable = new JLabel("远端密码:");
  private JPasswordField pwdField = new JPasswordField();
  private JLabel localGitRepository = new JLabel("本地GIT仓库:");
  private JTextField localRepositoryText = new JTextField();
  private JLabel remote = new JLabel("remote的https地址(以\"::\"隔开地址和");
  private JLabel remote2 = new JLabel("分支如:https://xxxxx::master):");
  private JTextField remoteText = new JTextField();
  private JLabel changeFiles = new JLabel("需修改的文件名:");
  private JTextField changeText = new JTextField();
  private JLabel  targetFile = new JLabel("目标文件路径:");
  private  JTextField targetText = new JTextField();
  private JButton okbtn = new JButton("确定");
  private JButton cancelbtn = new JButton("取消");
  public FrameDisplay(){
        jFrame.setBounds(600,400,600,600);
        c.setLayout(new BorderLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        jFrame.setVisible(true);
  }
  public static  void run(){
      new FrameDisplay();
  }
    private void init() {
      JPanel titlePanel = new JPanel();
      titlePanel.setLayout(new FlowLayout());
      titlePanel.add(new JLabel("参数录入入口"));
      c.add(titlePanel,"North");
      JPanel filePanel = new JPanel();
      filePanel.setLayout(null);
      userLable.setBounds(50,20,180,30);
      pwdLable.setBounds(50,80,180,30);
      localGitRepository.setBounds(50,140,180,30);
      remote.setBounds(20,200,210,30);
      remote2.setBounds(20,230,210,30);
      changeFiles.setBounds(50,260,180,30);
      targetFile.setBounds(50,320,180,30);
      filePanel.add(userLable);
      filePanel.add(pwdLable);
      filePanel.add(localGitRepository);
      filePanel.add(remote);
      filePanel.add(remote2);
      filePanel.add(changeFiles);
      filePanel.add(targetFile);
      userText.setBounds(260,20,220,30);
      pwdField.setBounds(260,80,220,30);
      localRepositoryText.setBounds(260,140,220,30);
      remoteText.setBounds(260,200,220,30);
      changeText.setBounds(260,260,220,30);
      targetText.setBounds(260,320,220,30);
      filePanel.add(userText);
      filePanel.add(pwdField);
      filePanel.add(localRepositoryText);
      filePanel.add(remoteText);
      filePanel.add(changeText);
      filePanel.add(targetText);
      c.add(filePanel,"Center");
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.add(okbtn);
      buttonPanel.add(cancelbtn);
      c.add(buttonPanel,"South");
      listener();
    }
    public void listener(){
      okbtn.addActionListener(e -> {
        ParamInfo info =  new ParamInfo();
        info.setUsername(userText.getText());
        info.setPwd(new String(pwdField.getPassword()));
        info.setLocalRepository(localRepositoryText.getText());
        info.setRemoteURL(remoteText.getText());
        info.setDocument(targetText.getText());
        info.setFileNames(changeText.getText());
        System.out.println(info.getUsername());
        okbtn.setEnabled(false);
        FrameWaite waite = FrameWaite.instance();
        FutureTask task = new FutureTask(new AutoProcessCallable(waite,okbtn,info));
        Thread thread = new Thread(task);
        thread.start();
      });
    }
}
