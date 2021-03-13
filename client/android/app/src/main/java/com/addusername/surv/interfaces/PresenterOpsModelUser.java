package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.HomeDTO;

import java.io.InputStream;

public interface PresenterOpsModelUser {
    void homeReturn(HomeDTO doHome);
    void addRpiReturn(boolean doAddRpi);
    void setImg(InputStream img, Integer id);
    void showImg(InputStream is);

    void loadWebview(String html);
}
