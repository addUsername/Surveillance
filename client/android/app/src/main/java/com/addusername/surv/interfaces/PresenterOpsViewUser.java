package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.PiDTO;

import java.util.List;

public interface PresenterOpsViewUser {
    void doGetHome();
    void doAddRpi(PiDTO piDTO);
    void loadImgs(List<Integer> raspberryIds);
    void getFromRPi(int rpiId, String action);
}
