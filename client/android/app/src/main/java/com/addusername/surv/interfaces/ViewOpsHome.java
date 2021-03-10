package com.addusername.surv.interfaces;

import android.graphics.Bitmap;

import com.addusername.surv.dtos.HomeDTO;

import java.io.InputStream;

public interface ViewOpsHome {
    void printHome(HomeDTO home);
    void showMessage(String mssg);
    void setImg(InputStream img, Integer id);
}
