package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiSettingsDTO;

import java.io.InputStream;

public interface ViewOpsHome {
    void printHome(HomeDTO home);
    void showMessage(String mssg);
    void setImg(InputStream img, Integer id);
    void showImg(InputStream is);
    void showStream(String html);
    void setPiConfig(PiSettingsDTO settings);
}
