package com.addusername.surv.interfaces;

import android.widget.EditText;

import com.addusername.surv.dtos.RegisterForm;

import java.util.HashMap;

public interface PresenterOpsView {
    boolean existUser();
    void login(String pin);
    String[] validate(HashMap<String, EditText> parseRegisterForm);
    void register(HashMap<String, EditText> parseRegisterForm);
    boolean isUserLogged();
}
