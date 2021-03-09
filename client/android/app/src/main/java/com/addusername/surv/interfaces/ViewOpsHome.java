package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.HomeDTO;

public interface ViewOpsHome {
    void printHome(HomeDTO home);
    void showMessage(String some_error_ocurred);
}
