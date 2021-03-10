package com.addusername.surv.presenter;

import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiDTO;
import com.addusername.surv.interfaces.ModelOpsUser;
import com.addusername.surv.interfaces.PresenterOpsModelUser;
import com.addusername.surv.interfaces.PresenterOpsViewUser;
import com.addusername.surv.interfaces.ViewOpsHome;
import com.addusername.surv.model.user.UserModel;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class UserPresenter implements PresenterOpsViewUser, PresenterOpsModelUser {

    private ModelOpsUser mou;
    private ViewOpsHome voh;

    public UserPresenter(String token, ViewOpsHome voh, String host, File filesDir) {
        mou = new UserModel(this,token, host, filesDir);
        this.voh = voh;
    }

    @Override
    public void doGetHome() {
        mou.doGetHome();
    }
    @Override
    public void doAddRpi(PiDTO piDTO) {mou.doAddRpi(piDTO);}
    @Override
    public void loadImgs(List<Integer> raspberryIds) { mou.loadImgs(raspberryIds); }
    @Override
    public void homeReturn(HomeDTO home) {
        voh.printHome(home);
    }
    @Override
    public void setImg(InputStream img, Integer id) { voh.setImg(img,id);}

    @Override
    public void addRpiReturn(boolean added) {
        if(added){
            doGetHome();
        } else {
            voh.showMessage("Some error ocurred");
        }
    }


}
