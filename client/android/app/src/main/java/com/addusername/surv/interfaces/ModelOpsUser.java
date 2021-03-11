package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.PiDTO;

import java.util.List;

public interface ModelOpsUser {
    void doGetHome();
    void doAddRpi(PiDTO piDTO);
    void loadImgs(List<Integer> raspberryIds);
    void getScreenShot(int rpiId);
    void getStream(int rpiId);
}
