package com.common.display;

import com.common.GitContext;

import javax.swing.*;
import java.util.concurrent.Callable;

public class AutoProcessCallable implements Callable {
    private FrameWaite waite;
    private JButton okbtn;
    private ParamInfo info;
    public AutoProcessCallable(FrameWaite waite, JButton okbtn, ParamInfo info){
        this.waite = waite;
        this.okbtn = okbtn;
        this.info = info;
    }
    @Override
    public Object call() throws Exception {
        GitContext.run(info);
        waite.getInfo().setText("推送完成！");
        okbtn.setEnabled(true);
        return null;
    }
}
