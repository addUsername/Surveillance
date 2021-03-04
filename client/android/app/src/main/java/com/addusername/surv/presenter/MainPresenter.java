package com.addusername.surv.presenter;

import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewOps;

public class MainPresenter implements PresenterOpsModel, PresenterOpsView {

    private ModelOps mo;
    private ViewOps vo;

    public MainPresenter(ModelOps mo, ViewOps vo){
        this.mo = mo;
        this.vo = vo;
    }
}
