package com.addusername.surv.presenter;

import android.widget.EditText;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.interfaces.ViewOpsHome;
import com.addusername.surv.model.auth.MainModel;

import java.io.File;
import java.util.HashMap;

public class MainPresenter implements PresenterOpsModel, PresenterOpsView {

    private ModelOps mo;
    private ViewOps vo;
    private ViewOpsHome voh;


    public MainPresenter(ViewOps vo, File filedir){
        this.mo = new MainModel(this, filedir);
        this.vo = vo;
    }
    @Override
    public boolean existUser() {
        return mo.existsUser();
    }

    @Override
    public String[] validate(HashMap<String, EditText> rf) {
        return mo.validate(parseRegisterForm(rf));
    }
    @Override
    public void login(String pin) {
        mo.doLogin(new LoginForm(Integer.parseInt(pin)));
    }
    @Override
    public void register(HashMap<String, EditText> rf) {
        mo.doRegister(parseRegisterForm(rf));
    }
    @Override
    public boolean isUserLogged() { return mo.isUserLogged(); }
    @Override
    public String getToken() {return mo.getToken();}
    @Override
    public String getHost() {return mo.getHost(); }

    private RegisterForm parseRegisterForm(HashMap<String, EditText> components){
        RegisterForm rf = new RegisterForm();
        rf.setUsername(components.get("username").getText().toString());
        rf.setPass(components.get("pass").getText().toString());
        rf.setPass2(components.get("pass2").getText().toString());
        rf.setEmail(components.get("email").getText().toString());
        if(components.get("pin").getText().toString().equals("")){
            rf.setPin(0);
        }else{
            rf.setPin(Integer.parseInt(components.get("pin").getText().toString()));
        }
        return rf;
    }

    @Override
    public void loginReturn(boolean login) {
        if(login){
            vo.loadFragment();
        } else{
            vo.showMessage("something bad happen");
        }
    }

    @Override
    public void registerReturn(boolean register) {
        if(register){
            vo.loadFragment();
        }else{
            vo.showMessage("something bad happen");
        }
    }
}
