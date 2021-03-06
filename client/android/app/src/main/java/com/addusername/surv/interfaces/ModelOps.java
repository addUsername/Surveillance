package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;

public interface ModelOps {

    boolean existsUser();
    void doLogin(LoginForm loginForm);
    void doRegister(RegisterForm registerForm);
}
