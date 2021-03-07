package com.addusername.surv.presenter;

import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.model.MainModel;

import java.io.File;

public class MainPresenter implements PresenterOpsModel, PresenterOpsView {

    private ModelOps mo;
    private ViewOps vo;

    public MainPresenter(ViewOps vo, File filedir){
        this.mo = new MainModel(this, filedir);
        this.vo = vo;
    }

    @Override
    public boolean existUser() {
        return mo.existsUser();
    }

    @Override
    public void login(String pin) {

    }

    @Override
    public String[] validate(RegisterForm rf) {
        return mo.validate(rf);
    }

    @Override
    public void register(RegisterForm rf) {
        mo.doRegister(rf);
    }
}
