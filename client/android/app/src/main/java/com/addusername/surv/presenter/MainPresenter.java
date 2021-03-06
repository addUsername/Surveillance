package com.addusername.surv.presenter;

import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.model.MainModel;

public class MainPresenter implements PresenterOpsModel, PresenterOpsView {

    private ModelOps mo;
    private ViewOps vo;

    public MainPresenter(ViewOps vo){
        this.mo = new MainModel(this);
        this.vo = vo;
    }

    @Override
    public boolean loginFragment() {
        return mo.existsUser();
    }
}
