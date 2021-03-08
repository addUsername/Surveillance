package com.addusername.surv.interfaces;

import android.widget.EditText;

import java.io.Serializable;
import java.util.HashMap;

public interface PresenterOpsView extends Serializable {
    boolean existUser();
    void login(String pin);
    String[] validate(HashMap<String, EditText> parseRegisterForm);
    void register(HashMap<String, EditText> parseRegisterForm);
    boolean isUserLogged();
    String getToken();
}
