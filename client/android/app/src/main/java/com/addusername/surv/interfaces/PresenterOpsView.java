package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.RegisterForm;

public interface PresenterOpsView {
    boolean existUser();
    void login(String pin);
    String[] validate(RegisterForm parseRegisterForm);
}
