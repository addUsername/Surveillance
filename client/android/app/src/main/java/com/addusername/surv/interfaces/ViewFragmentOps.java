package com.addusername.surv.interfaces;

import android.widget.EditText;

import com.addusername.surv.dtos.RegisterForm;

import java.util.HashMap;

public interface ViewFragmentOps {
    void login(String pin);
    boolean validateComponents(HashMap<String, EditText> components);
    void register(HashMap<String, EditText> components);
}
