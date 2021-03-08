package com.addusername.surv.presenter;

import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.interfaces.ModelOpsUser;
import com.addusername.surv.interfaces.PresenterOpsModelUser;
import com.addusername.surv.interfaces.PresenterOpsViewUser;
import com.addusername.surv.interfaces.ViewOpsHome;
import com.addusername.surv.model.user.UserModel;
import com.addusername.surv.view.user.UserActivity;

public class UserPresenter implements PresenterOpsViewUser, PresenterOpsModelUser {

    private ModelOpsUser mou;
    private ViewOpsHome voh;

    public UserPresenter(String token, ViewOpsHome voh) {
        mou = new UserModel(this,token);
        this.voh = voh;
    }

    @Override
    public void doGetHome() {
        mou.doGetHome();
    }

    @Override
    public void homeReturn(HomeDTO home) {
        voh.printHome(home);
    }
}
