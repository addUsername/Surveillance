package com.addusername.surv.presenter;

import com.addusername.surv.interfaces.ModelOpsUser;
import com.addusername.surv.interfaces.PresenterOpsViewUser;
import com.addusername.surv.model.user.UserModel;

public class UserPresenter implements PresenterOpsViewUser {

    private ModelOpsUser mou;
    public UserPresenter(String token) {
        mou = new UserModel(token);
    }
}
